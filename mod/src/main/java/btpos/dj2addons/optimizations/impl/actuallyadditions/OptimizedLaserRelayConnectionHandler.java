package btpos.dj2addons.optimizations.impl.actuallyadditions;

import btpos.dj2addons.DJ2Addons;
import btpos.dj2addons.optimizations.impl.actuallyadditions.GraphNetwork.RelayNode;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class OptimizedLaserRelayConnectionHandler implements ILaserRelayConnectionHandler {
	
	private static Map<BlockPos, GraphNetwork> networkLookupMap = new ConcurrentHashMap<>();
	
	@Override
	public ConcurrentSet<IConnectionPair> getConnectionsFor(BlockPos relay, World world) {
		ConcurrentSet<IConnectionPair> ret = new ConcurrentSet<>();
		
		GraphNetwork network = networkLookupMap.get(relay);
		if (network == null)
			return ret;
		
		RelayNode node = network.nodeLookupMap.get(relay);
		for (RelayNode connection : node.connections) {
			ret.add(node.makeConnectionPairWith(connection));
		}
		return ret;
	}
	
	/**
	 * Called to delete the relay entirely.
	 * @param toDelete The position of the relay to remove.
	 * @param world The world of the relay to remove.
	 */
	@Override
	public void removeRelayFromNetwork(BlockPos toDelete, World world) {
		final GraphNetwork networkOfTD = networkLookupMap.remove(toDelete);
		if (networkOfTD == null) {
			//DEBUG
			DJ2Addons.LOGGER.debug("[BAD!!!] network was null for " + toDelete);
			return;
		}
		
		final RelayNode nodeToDelete = networkOfTD.nodeLookupMap.remove(toDelete);
		if (nodeToDelete == null) {
			//DEBUG
			DJ2Addons.LOGGER.debug("[ALERT!!!] relay node was null for " + toDelete);
			return;
		}
		
		// allocating this here so we don't have to reallocate and can just clear
		final Set<RelayNode> alreadyTraversed = new HashSet<>(networkOfTD.nodeLookupMap.size());
		
		
		// for each node, remove their connections to this one and handle new graphs that could be created from the split
		nodeToDelete.connections.forEach(otherNode -> {
			// Remove all other nodes' connection to this one,
			otherNode.connections.remove(nodeToDelete);
			
			// if the other node was ONLY connected to this relay:
			if (otherNode.connections.isEmpty()) {
				// isolate that other node as well
				removeNodeFromOldNetwork(otherNode);
			} else {
				// form a new network for this branch since they're no longer connected to the old one
				// actually, by law nodes cannot connect to other nodes in the same network, so we don't need to do a search!!!
				GraphNetwork newNetworkForBranch = new GraphNetwork();
				alreadyTraversed.clear();
				
				GraphNetwork.forEachNodeRecursive(otherNode, n -> {
					moveNodeToNetwork(newNetworkForBranch, n);
				}, alreadyTraversed);
			} // TODO keep the largest branch in the original network
		});
		
		nodeToDelete.connections.clear();
	}
	
	private void moveNodeToNetwork(GraphNetwork newNetwork, RelayNode node) {
		removeNodeFromOldNetwork(node);
		addNodeToNetwork(newNetwork, node);
	}
	
	private void removeNodeFromOldNetwork(RelayNode node) {
		if (node.network == null)
			return;
		node.network.changeAmount++;
		node.network.nodeLookupMap.remove(node.pos);
		networkLookupMap.remove(node.pos);
		node.network = null;
	}
	
	private void addNodeToNetwork(GraphNetwork newNetwork, RelayNode node) {
		if (node.network == newNetwork)
			return;
		newNetwork.nodeLookupMap.put(node.pos, node);
		networkLookupMap.put(node.pos, newNetwork);
		
		node.network = newNetwork;
		newNetwork.changeAmount++;
	}
	
	@Override
	public final GraphNetwork getNetworkFor(BlockPos relay, World world) {
		// TODO get from world data
		return networkLookupMap.getOrDefault(relay, null);
	}
	
	public void mergeNetworks(GraphNetwork superNetwork, GraphNetwork toBeRemoved) {
		// update the network lookup map to the new network
		networkLookupMap.replaceAll((blockPos, mappedNetwork) -> {
			if (mappedNetwork.equals(toBeRemoved))
				return superNetwork;
			return mappedNetwork;
		});
		
		toBeRemoved.mergeIntoOtherNetwork(superNetwork);
		//TODO update world data
	}
	
	@Override
	public boolean addConnection(BlockPos firstRelayPos, BlockPos secondRelayPos, LaserType type, World world, boolean suppressConnectionRender, boolean removeIfConnected) {
		if (firstRelayPos == null
		    || secondRelayPos == null
		    || firstRelayPos.equals(secondRelayPos))
		{
			DJ2Addons.LOGGER.debug("Bad positions given: " + firstRelayPos + " " + secondRelayPos);
			return false;
		}
		
		
		GraphNetwork firstNetwork = this.getNetworkFor(firstRelayPos, world);
		GraphNetwork secondNetwork = this.getNetworkFor(secondRelayPos, world);
		
		
		if (firstNetwork == null && secondNetwork == null) {
			// neither has a network, so create a new one
			GraphNetwork newNetwork = new GraphNetwork();
			addNodeToNetwork(newNetwork, new RelayNode(newNetwork, firstRelayPos, type));
			addNodeToNetwork(newNetwork, new RelayNode(newNetwork, secondRelayPos, type));
		} else if (Objects.equals(firstNetwork, secondNetwork)) {
			// if they're the same network:
			if (!removeIfConnected) {
				return false; // this is the only short-circuit return in this block
			}
			
			this.removeConnection(world, firstRelayPos, secondRelayPos);
		} else if (firstNetwork == null) {
			// add it to the second network
			addNodeToNetwork(secondNetwork, new RelayNode(secondNetwork, firstRelayPos, type));
		} else if (secondNetwork == null) {
			// add it to the first network
			addNodeToNetwork(firstNetwork, new RelayNode(firstNetwork, firstRelayPos, type));
		} else {
			mergeNetworks(firstNetwork, secondNetwork);
		}
		
		return true;
	}
	
	@Override
	public void removeConnection(World world, BlockPos firstRelayPos, BlockPos secondRelayPos) {
		if (firstRelayPos == null
		    || secondRelayPos == null
		    || firstRelayPos.equals(secondRelayPos))
		{
			DJ2Addons.LOGGER.debug("Bad positions given: " + firstRelayPos + " " + secondRelayPos);
			return;
		}
		
		
		GraphNetwork network = getNetworkFor(firstRelayPos, world);
		
		if (!Objects.equals(network, getNetworkFor(secondRelayPos, world))) {
			DJ2Addons.LOGGER.debug("Not the same network: " + firstRelayPos + " " + secondRelayPos);
			return;
		}
		
		RelayNode firstNode = network.nodeLookupMap.get(firstRelayPos);
		RelayNode secondNode = network.nodeLookupMap.get(secondRelayPos);
		
		firstNode.connections.remove(secondNode);
		secondNode.connections.remove(firstNode);
		
		// Add all other nodes to a new network
		GraphNetwork newNetworkForSecondBranch = new GraphNetwork();
		GraphNetwork.traverseFromNode(secondNode, node -> moveNodeToNetwork(newNetworkForSecondBranch, node), network.nodeLookupMap.size());
	}
	
	
	
	public static Network readNetworkFromNBT(NBTTagCompound tag) {
		return GraphNetwork.fromNBT(tag);
	}
	
	
	public static NBTTagCompound writeNetworkToNBT(Network network) {
		if (network instanceof GraphNetwork)
			return GraphNetwork.toNBT((GraphNetwork) network);
		else // DEBUG
			throw new RuntimeException("[ActuallyAdditions] A non-GraphNetwork somehow found its way into the system.");
	}
	
	
	@Override
	public LaserType getTypeFromLaser(BlockPos pos, World world) {
		return getTypeFromLaser(world.getTileEntity(pos));
	}
	
	@Override
	public LaserType getTypeFromLaser(TileEntity tile) {
		if (tile instanceof TileEntityLaserRelay) {
			return ((TileEntityLaserRelay) tile).type;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world) {
		return this.addConnection(firstRelay, secondRelay, type, world, false);
	}
	
	@Override
	public boolean addConnection(BlockPos firstRelay, BlockPos secondRelay, LaserType type, World world, boolean suppressConnectionRender) {
		return this.addConnection(firstRelay, secondRelay, type, world, suppressConnectionRender, false);
	}
	
}
