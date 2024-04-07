package btpos.dj2addons.optimizations.impl.actuallyadditions;

import btpos.dj2addons.DJ2Addons;
import de.ellpeck.actuallyadditions.api.laser.LaserType;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.misc.apiimpl.ConnectionPair;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class GraphNetwork extends Network {
	
	/**
	 * Quick reverse lookup to get a BlockPos' representation in the graph.
	 * ?Could maybe use an {IntMap : Int2ObjectMap} for x and y coords to save memory? probably doesn't matter but whatever
	 */
	public Map<BlockPos, RelayNode> nodeLookupMap = new ConcurrentHashMap<>();
	
	/**
	 * Adds all of this network's endpoints and nodes to the other network and clears all of this network's trackers.
	 * @param otherNetwork The network to join.
	 */
	public void mergeIntoOtherNetwork(GraphNetwork otherNetwork) {
		// update every node's internal network reference
		forEach(node -> node.network = otherNetwork);
		otherNetwork.nodeLookupMap.putAll(this.nodeLookupMap);
		this.nodeLookupMap.clear();
	}
	
	/**
	 * Performs an action on every node in the graph. Used in {@link #mergeIntoOtherNetwork} to set every node's network reference to the new network.
	 * @param action The action to
	 */
	public void forEach(Consumer<RelayNode> action) {
		if (action == null || nodeLookupMap.isEmpty()) {
			return;
		}
		
		// get an arbitrary node to start with since we know it'll access every single node in the graph
		traverseFromNode(nodeLookupMap.values().iterator().next(), action, nodeLookupMap.size());
	}
	
	public static void traverseFromNode(RelayNode currentNode, Consumer<RelayNode> action, int estimatedSize) {
		Set<RelayNode> alreadyTraversed = new HashSet<>(estimatedSize);
		
		forEachNodeRecursive(currentNode, action, alreadyTraversed);
	}
	
	/**
	 * Put logic in another method because I don't want the recursive logic inside my forEach method.
	 * ...I could probably just put it inside a class to avoid pushing everything onto the stack...
	 * @param currentNode The current node being checked.
	 * @param action The action to perform on every node.
	 * @param alreadyChecked Set of nodes we've already looked at.
	 */
	public static void forEachNodeRecursive(RelayNode currentNode, Consumer<RelayNode> action, Set<RelayNode> alreadyChecked) {
		action.accept(currentNode);
		alreadyChecked.add(currentNode);
		
		for (RelayNode connectedNode : currentNode.connections) {
			if (!alreadyChecked.contains(connectedNode)) {
				forEachNodeRecursive(connectedNode, action, alreadyChecked);
			}
		}
	}
	
	public static void forEachEdgeRecursive(RelayNode currentNode, BiConsumer<RelayNode, RelayNode> action, Set<RelayNode> alreadyChecked) {
		alreadyChecked.add(currentNode);
		for (RelayNode connectedNode : currentNode.connections) {
			if (!alreadyChecked.contains(connectedNode)) {
				action.accept(currentNode, connectedNode);
				forEachEdgeRecursive(connectedNode, action, alreadyChecked);
			}
		}
	}
	
	public static NBTTagCompound toNBT(GraphNetwork network) {
		NBTTagList list = new NBTTagList();
		
		forEachEdgeRecursive(network.nodeLookupMap.values().iterator().next(), (source, dest) -> {
			NBTTagCompound tag = new NBTTagCompound();
			source.makeConnectionPairWith(dest).writeToNBT(tag);
			list.appendTag(tag);
		}, new HashSet<>(network.nodeLookupMap.size()));
		
		NBTTagCompound compound = new NBTTagCompound();
		compound.setTag("Network", list);
		return compound;
	}
	
	public static GraphNetwork fromNBT(NBTTagCompound tag) {
		NBTTagList list = tag.getTagList("Network", 10);
		GraphNetwork network = new GraphNetwork();
		for (int i = 0; i < list.tagCount(); i++) {
			ConnectionPair pair = new ConnectionPair();
			pair.readFromNBT(list.getCompoundTagAt(i));
			BlockPos[] positions = pair.getPositions();
			
			// no reason to generate two classes for the same thing, shrug
			Function<BlockPos, RelayNode> nodeGenerator = (k) -> new RelayNode(network, k, pair.getType());
			RelayNode first = network.nodeLookupMap.computeIfAbsent(positions[0], nodeGenerator);
			RelayNode second = network.nodeLookupMap.computeIfAbsent(positions[1], nodeGenerator);
			
			first.connections.add(second);
			second.connections.add(first);
		}
		return network;
	}
	
	
	/**
	 * Node that represents a relay in the Network.
	 */
	public static final class RelayNode {
		/**
		 * Reference to the network that owns this node
		 */
		public GraphNetwork network; //TODO rename
		/**
		 * All nodes that are directly linked to this node. For use in rendering lasers and traversing the graph.
		 */
		public Set<RelayNode> connections = new HashSet<>();
		
		/**
		 * The value being encapsulated.
		 */
		public BlockPos pos; // TODO rename
		
		/**
		 * The type of this relay.
		 */
		public LaserType type;
		
		
		public RelayNode(GraphNetwork network, BlockPos pos, LaserType type) {
			this.network = network;
			this.pos = pos;
			this.type = type;
		}
		
		public ConnectionPair makeConnectionPairWith(RelayNode other) {
			return new ConnectionPair(
					this.pos,
					other.pos,
					this.type,
					false // always false in code fsr, instead just put as infrared if you're wearing goggles
			);
		}
		
		@Override
		protected void finalize() throws Throwable {
			super.finalize();
			DJ2Addons.LOGGER.debug("Freed Node for " + pos); // DEBUG
		}
	}
}
