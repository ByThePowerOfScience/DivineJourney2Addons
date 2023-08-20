package btpos.dj2addons;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class DJ2ATest {
	public static World world;
	
	public static IBlockState blockAt(int x, int y, int z) {
		return world.getBlockState(new BlockPos(x, y, z));
	}
	
	public static IBlockState blockAt(BlockPos pos) {
		return world.getBlockState(pos);
	}
	
	public static void assertTrue(boolean b) {
		assert(b);//TODO
	}
	
	public static void assertBlock(int x, int y, int z, Block target) {
		assertBlock(new BlockPos(x, y, z), target);
	}
	
	public static void assertBlock(BlockPos pos, Block target) {
		assertTrue(world.getBlockState(pos).getBlock() == target);
	}
	
	public static void runTests() {
		Set<Method> methodsAnnotatedWith = new Reflections("btpos.dj2addons").getMethodsAnnotatedWith(Test.class);
		methodsAnnotatedWith.forEach(m -> {
			try {
				m.invoke(m.getDeclaringClass().newInstance(), (Object) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}