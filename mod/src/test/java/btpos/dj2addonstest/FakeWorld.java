package btpos.dj2addonstest;

import mekanism.common.world.DummyWorld.DummyWorldInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.profiler.Profiler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.function.Function;

/**
 * Lets us run tests in PreInit instead of having to wait the full 10 minutes for the game to load
 */
public class FakeWorld extends World {
	
	
	public FakeWorld() {
		super(new SaveHandler(new File("fakeworld"), "fakeworld", false, FMLCommonHandler.instance().getDataFixer()) {
			      @Override
			      public void saveWorldInfo(WorldInfo worldInformation) {}
			      
			      @Override
			      public WorldInfo loadWorldInfo() {
				      return new DummyWorldInfo();
			      }
		      },
				new DummyWorldInfo(),
				new WorldProviderSurface(),
				new Profiler(),
				true);
	}
	
	private Function<BlockPos, IBlockState> getBlockStateMethod;
	
	public FakeWorld setGetBlockStateMethod(Function<BlockPos, IBlockState> getBlockStateMethod) {
		this.getBlockStateMethod = getBlockStateMethod;
		return this;
	}
	
	@Override
	public IBlockState getBlockState(BlockPos pos) {
		if (getBlockStateMethod != null)
			return getBlockStateMethod.apply(pos);
		throw new UnsupportedOperationException("getBlockState called with none specified");
	}
	
	
	
	private Function<BlockPos, TileEntity> getTileEntityMethod;
	
	public FakeWorld setGetTileEntityMethod(Function<BlockPos, TileEntity> getTileEntityMethod) {
		this.getTileEntityMethod = getTileEntityMethod;
		return this;
	}
	
	@Nullable
	@Override
	public TileEntity getTileEntity(BlockPos pos) {
		if (getTileEntityMethod != null) {
			return getTileEntityMethod.apply(pos);
		}
		throw new UnsupportedOperationException("getTileEntity called with no method specified");
	}
	
	
	
	
	
	
	
	
	@Override
	protected IChunkProvider createChunkProvider() {
		return new ChunkProviderClient(this);
	}
	
	@Override
	protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
		return true;
	}
	
	@Override
	public boolean isAirBlock(BlockPos pos) {
		return false;
	}
	
	@Override
	public void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {}
	
	@Override
	public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {}
	
	@Override
	public void updateComparatorOutputLevel(BlockPos pos, Block blockIn) {}
}

