package btpos.dj2addons.optimizations.impl.actuallyadditions;

import btpos.dj2addons.optimizations.impl.actuallyadditions.GraphNetwork.Node;
import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.IConnectionPair;
import de.ellpeck.actuallyadditions.api.laser.ILaserRelayConnectionHandler;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.ConnectionPair;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase.NBTType;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class OptimizedLaserRelayConnectionHandler implements ILaserRelayConnectionHandler {
	
	public static final Logger LOGGER = LogManager.getLogger("LaserHandler");
	
	public Map<BlockPos, GraphNetwork> networkLookupMap = new ConcurrentHashMap<>();
	
	public static void onNetworkSync(NBTTagCompound compound, NBTType type, BlockPos pos, World world) {
		if (type == NBTType.SYNC) {
			ILaserRelayConnectionHandler genericConnectionHandler = ActuallyAdditionsAPI.connectionHandler;
			if (!(genericConnectionHandler instanceof OptimizedLaserRelayConnectionHandler))
				return;
			
			OptimizedLaserRelayConnectionHandler connectionHandler = ((OptimizedLaserRelayConnectionHandler) genericConnectionHandler);
			
			NBTTagList incomingPairs = compound.getTagList("Connections", 10);
			LOGGER.debug("[readSyncableNBT] incoming pairs: " + incomingPairs);
			if (incomingPairs.isEmpty()) {
				connectionHandler.removeRelayFromNetwork(pos, world);
				return;
			}
			
			Set<IConnectionPair> previousConnected = connectionHandler.getConnectionsFor(pos, world);
			
			for (int i = 0; i < incomingPairs.tagCount(); i++) {
				ConnectionPair newPair = new ConnectionPair(); // TODO remove this extra object instantiation
				newPair.readFromNBT(incomingPairs.getCompoundTagAt(i));
				
				// remove it from the set if it's there, so all that's left in previousConnections is the ones we USED to have but don't anymore
				if (previousConnected.remove(newPair) == false) {
					// if it wasn't there, it's a new one, so add it
					BlockPos[] positions = newPair.getPositions();
					connectionHandler.addConnection(positions[0], positions[1], newPair.getType(), world);
				}
			}
			
			// anything left in the set is connections that aren't there anymore
			previousConnected.forEach(connectionPair -> { //! This actually broke it; need to figure out what is synced and what isn't
				BlockPos[] positions = connectionPair.getPositions();
				connectionHandler.removeConnection(world, positions[0], positions[1]);
			});
		}
	}
	
	public void addNetworkNodesToNetworkLookupMap(Network network) {
		if (!(network instanceof GraphNetwork)) {
			LOGGER.debug("[initNetMap] VANILLA NETWORK FOUND");
			return;
		}
		GraphNetwork gnetwork = (GraphNetwork) network;
		gnetwork.nodeLookupMap.keySet()
		                      .forEach(pos -> {
									LOGGER.debug("[initNetMap] added " + pos);
									networkLookupMap.put(pos, gnetwork);
							  });
		LOGGER.debug("[initNetMap] Loaded network: " + gnetwork);
	}
	
	public Node getNodeFor(BlockPos pos, World world) {
		GraphNetwork graphNetwork = getNetworkFor(pos, world);
		if (graphNetwork == null) {
			return null;
		}
		
		return graphNetwork.getNodeFor(pos);
	}
	
	public Set<BlockPos> getPosConnectionsFor(BlockPos relay, World world) {
		Set<BlockPos> ret = new HashSet<>();
		
		for (Node connectedNode : getNodeFor(relay, world).connections) {
			ret.add(connectedNode.pos);
		}
		
		return ret;
	}
	
	@Override
	public ConcurrentSet<IConnectionPair> getConnectionsFor(BlockPos relay, World world) {
		Node node = getNodeFor(relay, world);
		if (node == null) {
			return null;
		}
		
		return node.getConnectionsAsPairs();
	}
	
	
	/**
	 * Called to delete the relay entirely.
	 * @param toDelete The position of the relay to remove.
	 * @param world The world of the relay to remove.
	 */
	@Override
	public void removeRelayFromNetwork(BlockPos toDelete, World world) {
		final GraphNetwork networkForDeleted = getNetworkFor(toDelete, world);
		if (networkForDeleted == null) {
			//DEBUG
			LOGGER.debug("[removeRelayFromNetwork] network was null for " + toDelete);
			return;
		}
		
		final Node nodeToDelete = networkForDeleted.removeNode(toDelete);
		if (nodeToDelete == null) {
			//DEBUG
			LOGGER.debug("[removeRelayFromNetwork] relay node was null for " + toDelete);
			return;
		}
		
		
		// for each node, remove their connections to this one and handle new graphs that could be created from the split
		nodeToDelete.connections.forEach(otherNode -> {
			otherNode.connections.remove(nodeToDelete);
			
			// if the other node was ONLY connected to this relay: isolate that other node as well
			if (otherNode.connections.isEmpty()) {
				LOGGER.debug("[removeRelayFromNetwork | forEach] {} empty, removing", otherNode);
				removeNodeFromOldNetwork(otherNode, world);
			} else {
				// form a new network for this branch since they're no longer connected to the old one
				GraphNetwork newNetworkForBranch = makeNewNetwork(world);
				
				GraphNetwork.forEachNodeRecursive(
						otherNode,
						n -> moveNodeToNetwork(newNetworkForBranch, n, world),
						new HashSet<>(networkForDeleted.getNodeCount()));
				LOGGER.debug("[removeRelayFromNetwork | forEach] {} created new branch {}",
				             otherNode,
				             newNetworkForBranch);
			}
			// TODO keep the largest branch in the original network
		});
		
		nodeToDelete.connections.clear();
		removeNodeFromOldNetwork(nodeToDelete, world);
		WorldData.get(world).markDirty();
	}
	
	private void moveNodeToNetwork(GraphNetwork newNetwork, Node node, World world) {
		LOGGER.debug("[moveNodeToNetwork] {} -> {}", node, newNetwork);
		removeNodeFromOldNetwork(node, world);
		addNodeToNetwork(newNetwork, node, world);
		WorldData.get(world).markDirty();
	}
	
	/**
	 * Nullifies a node's references to its old network, updates the lookup maps, and updates the world data if this was the last node in the network.
	 */
	private void removeNodeFromOldNetwork(Node node, World world) {
//		LOGGER.catching(new Throwable());
		GraphNetwork nodeNetwork = node.network;
		if (nodeNetwork == null)
			return;
		
		nodeNetwork.changeAmount++;
		
		nodeNetwork.removeNode(node.pos);
		
		networkLookupMap.remove(node.pos);
		
		if (nodeNetwork.getNodeCount() <= 0) {
			WorldData.get(world).laserRelayNetworks.remove(nodeNetwork);
			LOGGER.debug("[removeNodeFromOldNetwork] Last node {} removed from network {}", node.pos, nodeNetwork);
		}
		else {
			LOGGER.debug("[removeNodeFromOldNetwork] Removed " + node.pos + " from network " + nodeNetwork);
			
			node.network = null;
		}
		WorldData.get(world).markDirty();
	}
	
	private void addNodeToNetwork(GraphNetwork newNetwork, Node node, World world) {
		if (node.network == newNetwork) {
			LOGGER.debug("[addToNetwork] networks equal: " + node.network + " " + newNetwork);
			return;
		}
		newNetwork.nodeLookupMap.put(node.pos, node);
		networkLookupMap.put(node.pos, newNetwork);
		
		node.network = newNetwork;
		newNetwork.changeAmount++;
		LOGGER.debug("[addToNetwork] Added " + node.pos + " to network " + newNetwork);
		WorldData.get(world).markDirty();
	}
	
	@Override
	public final GraphNetwork getNetworkFor(BlockPos relay, World world) {
		return networkLookupMap.get(relay);
	}
	
	public void mergeNetworks(GraphNetwork superNetwork, GraphNetwork toBeRemoved, World world) {
		LOGGER.debug("[mergeNetworks] super: " + superNetwork + " | " + toBeRemoved);
		// update the network lookup map to the new network
		networkLookupMap.replaceAll((blockPos, mappedNetwork) -> {
			if (mappedNetwork.equals(toBeRemoved))
				return superNetwork;
			return mappedNetwork;
		});
		
		toBeRemoved.mergeIntoOtherNetwork(superNetwork);
		
		WorldData.get(world).laserRelayNetworks.remove(toBeRemoved);
		WorldData.get(world).markDirty();
	}
	
	@Override
	public boolean addConnection(BlockPos firstRelayPos, BlockPos secondRelayPos, LaserType type, World world, boolean suppressConnectionRender, boolean removeIfConnected) {
		if (firstRelayPos == null
		    || secondRelayPos == null
		    || firstRelayPos.equals(secondRelayPos))
		{
			LOGGER.debug("[addConnection] Bad positions given: " + firstRelayPos + " " + secondRelayPos);
			return false;
		}
		
		
		GraphNetwork firstNetwork = getNetworkFor(firstRelayPos, world);
		GraphNetwork secondNetwork = getNetworkFor(secondRelayPos, world);
		
		
		if (firstNetwork == null && secondNetwork == null) {
			// neither has a network, so create a new one
			GraphNetwork newNetwork = makeNewNetwork(world);
			
			Node firstNode = new Node(firstRelayPos, type);
			Node secondNode = new Node(secondRelayPos, type);
			
			addNodeToNetwork(newNetwork, firstNode, world);
			addNodeToNetwork(newNetwork, secondNode, world);
			
			firstNode.linkTo(secondNode);
			
			LOGGER.debug("[addConnection] Both null, created {}", newNetwork);
		} else if (firstNetwork == secondNetwork) {
			// if they're the same network:
			if (!removeIfConnected) {
				LOGGER.debug("[addConnection] Same, no remove: {}", firstNetwork);
				return false; // this is the only short-circuit return in this block
			}
			if (firstNetwork.getNodeFor(firstRelayPos).connections.contains(firstNetwork.getNodeFor(secondRelayPos))) {
				LOGGER.debug("[addConnection] Same, remove: {}", firstNetwork);
				removeConnection(world, firstRelayPos, secondRelayPos);
			}
		} else if (firstNetwork == null) {
			LOGGER.debug("[addConnection | 1 -> 2] {} -> {}", firstRelayPos, secondNetwork);
			// add it to the second network
			Node node = new Node(firstRelayPos, type);
			addNodeToNetwork(secondNetwork, node, world);
			
			Node first = secondNetwork.nodeLookupMap.get(secondRelayPos);
			first.linkTo(node);
			
		} else if (secondNetwork == null) {
			// add it to the first network
			LOGGER.debug("[addConnection | 2 -> 1] {} -> {}", secondRelayPos, firstNetwork);
			Node node = new Node(secondRelayPos, type);
			addNodeToNetwork(firstNetwork, node, world);
			node.linkTo(firstNetwork.nodeLookupMap.get(firstRelayPos));
		} else {
			LOGGER.debug("[addConnection|merge] {} into {}", secondNetwork, firstNetwork);
			mergeNetworks(firstNetwork, secondNetwork, world);
			Node first = firstNetwork.nodeLookupMap.get(firstRelayPos);
			first.linkTo(secondNetwork.nodeLookupMap.get(secondRelayPos));
		}
		WorldData.get(world).markDirty();
		return true;
	}
	
	@Override
	public void removeConnection(World world, BlockPos firstRelayPos, BlockPos secondRelayPos) {
		if (firstRelayPos == null
		    || secondRelayPos == null
		    || firstRelayPos.equals(secondRelayPos))
		{
			LOGGER.debug("[removeConnection] Bad positions given: " + firstRelayPos + " " + secondRelayPos);
			return;
		}
		
		
		GraphNetwork network = getNetworkFor(firstRelayPos, world);
		
		if (network != getNetworkFor(secondRelayPos, world)) {
			LOGGER.debug("[removeConnection] Not the same network: " + firstRelayPos + " " + secondRelayPos + "\n{} {}", network, getNetworkFor(secondRelayPos, world));
			return;
		}
		
		Node firstNode = network.nodeLookupMap.get(firstRelayPos);
		Node secondNode = network.nodeLookupMap.get(secondRelayPos);
		
		// remove each other's connection to the other
		firstNode.unlinkTo(secondNode);
		
		// if either is now isolated, remove it from the network
		boolean haveIsolatedNode = false;
		if (firstNode.connections.isEmpty()) {
			haveIsolatedNode = true;
			removeNodeFromOldNetwork(firstNode, world);
		}
		if (secondNode.connections.isEmpty()) {
			haveIsolatedNode = true;
			removeNodeFromOldNetwork(secondNode, world);
		}
		if (!haveIsolatedNode) {
			LOGGER.debug("[removeConnection] moving nodes starting from {}", secondNode);
			// Add all of new split-off branch to new network
			GraphNetwork newNetworkForSecondBranch = makeNewNetwork(world);
			GraphNetwork.traverseFromNode(
					secondNode,
					node -> moveNodeToNetwork(newNetworkForSecondBranch, node, world),
					network.getNodeCount());
		}
		WorldData.get(world).markDirty();
	}
	
	@NotNull
	private static GraphNetwork makeNewNetwork(World world) {
		GraphNetwork graphNetwork = new GraphNetwork();
		WorldData worldData = WorldData.get(world);
		worldData.laserRelayNetworks.add(graphNetwork);
		LOGGER.debug("[makeNewNetwork] " + graphNetwork);
		worldData.markDirty();
		return graphNetwork;
	}
	
	
	public static Network readNetworkFromNBT(NBTTagCompound tag) {
		return GraphNetwork.fromNBT(tag);
	}
	
	
	public static NBTTagCompound writeNetworkToNBT(Network network) {
		if (network instanceof GraphNetwork)
			return GraphNetwork.toNBT((GraphNetwork) network);
		else // DEBUG
			throw new RuntimeException("[ActuallyAdditions] A non-GraphNetwork somehow found its way into the system." + network);
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
