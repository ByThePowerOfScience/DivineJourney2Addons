package btpos.dj2addonstest.optimizations.mixin.industrialforegoing;

import btpos.dj2addonstest.FakeWorld;
import com.agricraft.agricore.lang.AgriString;
import com.agricraft.agricore.plant.*;
import com.buuz135.industrial.tile.agriculture.PlantInteractorTile;
import com.buuz135.industrial.tile.block.PlantInteractorBlock;
import com.infinityraider.agricraft.api.v1.seed.AgriSeed;
import com.infinityraider.agricraft.blocks.BlockCrop;
import com.infinityraider.agricraft.core.JsonPlant;
import com.infinityraider.agricraft.farming.PlantStats;
import com.infinityraider.agricraft.tiles.TileEntityCrop;
import com.jjtparadox.barometer.tester.BarometerTester;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static btpos.dj2addonstest.DJ2AddonsTest.LOGGER;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(BarometerTester.class)
public class MPlantInteractorTest {
	
	public static PlantInteractorTile plantInteractorTile;
	public static TileEntityCrop crop;
	public static FakeWorld fakeWorld;
	
	public static final BlockPos interactorpos = new BlockPos(0, 0, 0);
	public static final BlockPos croppos = new BlockPos(0, 0, 1);
	
	public static void dotestmanually() {
//		MPlantInteractorTest.before();
		new MPlantInteractorTest().handleAgricraftCrops();
	}
	
//	@BeforeAll
//	public static void before() {
//		CommonProxy.random = new Random();
//		final PlantInteractorBlock plantInteractorBlock = new PlantInteractorBlock();
//		final BlockCrop blockCrop = new BlockCrop();
//
//
//
//		fakeWorld = new FakeWorld().setGetBlockStateMethod(pos -> {
//			if (pos.equals(interactorpos)) {
//				return plantInteractorBlock.getDefaultState();
//			}
//			else if (pos.equals(croppos)) {
//				return blockCrop.getDefaultState();
//			}
//			return Blocks.AIR.getDefaultState();
//		});
//
//		plantInteractorTile = new PlantInteractorTile();
//		crop = new TileEntityCrop();
//
//		plantInteractorTile.setPos(interactorpos);
//		plantInteractorTile.setWorld(fakeWorld);
//
//		/// have to set this as early as posible because otherwise something else will call it
//		fakeWorld.setGetTileEntityMethod(pos -> {
//			if (pos.equals(interactorpos)) {
//				log("Requested interactor pos");
//				return plantInteractorTile;
//			}
//			else if (pos.equals(croppos)) {
//				log("Requested crop pos");
//				return crop;
//			}
//			return null;
//		});
//
//		crop.setWorld(fakeWorld);
//		crop.setPos(croppos);
//
//		AgriPlant agriPlant = new TestPlant().plant;
//		JsonPlant jsonPlant = new JsonPlant(agriPlant);
//
//		AgriSeed seed = new AgriSeed(jsonPlant, new PlantStats(1, 1, 1));
//		crop.setSeed(seed);
//		crop.setGrowthStage(8);
//	}
	
/*
	private static TileEntityCrop getCrop(World world, BlockPos pos) {
		TileEntityCrop tileEntityCrop = new TileEntityCrop();
		tileEntityCrop.setWorld(world);
		tileEntityCrop.setPos(pos);

//		Optional<AgriPlant> optionalplant = AgriCore.getPlants().getAll().stream().findFirst();
//		assertTrue(optionalplant.isPresent(), "Could not find any registered plants!");
//
		AgriPlant agriPlant = new TestPlant().plant;
		JsonPlant jsonPlant = new JsonPlant(agriPlant);
		
		AgriSeed seed = new AgriSeed(jsonPlant, new PlantStats(1, 1, 1));
		tileEntityCrop.setSeed(seed);
		tileEntityCrop.setGrowthStage(8);
		return tileEntityCrop;
	}
*/
	
	@Test
	public void handleAgricraftCrops() {
		final PlantInteractorBlock plantInteractorBlock = new PlantInteractorBlock();
		final BlockCrop blockCrop = new BlockCrop();
		
		
		
//		fakeWorld = new FakeWorld().setGetBlockStateMethod(pos -> {
//			if (pos.equals(interactorpos)) {
//				return plantInteractorBlock.getDefaultState();
//			}
//			else if (pos.equals(croppos)) {
//				return blockCrop.getDefaultState();
//			}
//			return Blocks.AIR.getDefaultState();
//		});
		
		plantInteractorTile = new PlantInteractorTile();
		crop = new TileEntityCrop();
		
		plantInteractorTile.setPos(interactorpos);
//		plantInteractorTile.setWorld(fakeWorld);
		
		/// have to set this as early as posible because otherwise something else will call it
//		fakeWorld.setGetTileEntityMethod(pos -> {
//			if (pos.equals(interactorpos)) {
//				log("Requested interactor pos");
//				return plantInteractorTile;
//			}
//			else if (pos.equals(croppos)) {
//				log("Requested crop pos");
//				return crop;
//			}
//			return null;
//		});
		
//		crop.setWorld(fakeWorld);
		crop.setPos(croppos);
		
		AgriPlant agriPlant = new TestPlant().plant;
		JsonPlant jsonPlant = new JsonPlant(agriPlant);
		
		AgriSeed seed = new AgriSeed(jsonPlant, new PlantStats(1, 1, 1));
		crop.setSeed(seed);
		crop.setGrowthStage(8);
		crop.setPos(plantInteractorTile.getPos().offset(plantInteractorTile.getFacing()));
		crop.setGrowthStage(8);
		
		for (int i = 0; i < 20; i++) {
			plantInteractorTile.work();
		}
		
		// assert that we've gotten items in the inventory
		IItemHandler iItemHandler = Objects.requireNonNull(plantInteractorTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
		boolean foundItem = false;
		for (int i = 0; i < iItemHandler.getSlots(); i++) {
			if (!iItemHandler.getStackInSlot(i).isEmpty()) {
				foundItem = true;
				break;
			}
		}
		assertTrue(foundItem);
	}
	
	static void log(String message) {
		LOGGER.debug("[handleAgriCraftCrops] " + message);
	}
	
	/**
	 * Copied from com.agricraft.agricore.test.TestPlant
	 */
	@SuppressWarnings("ALL")
	public static class TestPlant {
		
		public final AgriPlant plant;
		
		public TestPlant() {
			
			// Setup Product
			AgriProduct item = new AgriProduct("wheat", 0, 1, 3, .5, true);
			
			// Setup Seed
			AgriStack seed = new AgriStack("minecraft:wheat_seeds", 0, true, true, "", "*");
			
			// Setup Products
			List<AgriProduct> items = new ArrayList<>();
			items.add(item);
			AgriProductList products = new AgriProductList(items);
			
			// Setup Condition
			AgriCondition condition = new AgriCondition(1, 0, -2, 0, 0, -2, 0, "minecraft:stone", 0, true, true, "");
			
			// Setup Requirement
			AgriRequirement requirement = new AgriRequirement(Arrays.asList("dirt"), Arrays.asList(condition), 0, 10);
			
			// Setup Icon
			AgriTexture texture = new AgriTexture(AgriRenderType.CROSS, "seed_wheat", new String[0]);
			
			// Setup Description
			AgriString description = new AgriString("Wheat, the gluten that founded human society.");
			
			// Setup Plant
			plant = new AgriPlant("wheat_plant", "Wheat", "Wheat Seeds", Arrays.asList(seed), description, false, 1, 1.0, 0.01, false, false, 0.1, 0, 0, 1, 0, products, requirement, texture, "default/wheat_plant.json", true);
		}
	}
}