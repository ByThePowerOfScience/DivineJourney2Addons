package btpos.dj2addons;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Set;

@Mod(modid="dj2addons_test", dependencies = "required-after:dj2addons")
public class DJ2ATest {
	public static final Logger LOGGER = LogManager.getLogger("DJ2A TEST");
	
	
	
	public static void runTests(World w) {
		final Asserter asserter = new Asserter();
		asserter.world = w;
		Set<Method> methodsAnnotatedWith = new Reflections("btpos.dj2addons").getMethodsAnnotatedWith(Test.class);
		methodsAnnotatedWith.forEach(m -> {
			try {
				Class<?> declaringClass = m.getDeclaringClass();
				asserter.className = declaringClass.getName();
				asserter.methodName = m.getName();
				m.invoke(declaringClass.newInstance(), asserter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public static class Asserter {
		public String className;
		public String methodName;
		public World world;
		
		public void assertTrue(boolean b) {
			assertTrue("", b);
		}
		
		public void assertTrue(String note, boolean b) {
			if (b) {
				LOGGER.info("[{}.{}] {}: {}", className, methodName, note, String.valueOf(b));
			} else {
				LOGGER.error("[{}.{}] {}: {}", className, methodName, note, String.valueOf(b));
			}
		}
		
		public IBlockState blockAt(int x, int y, int z) {
			return blockAt(new BlockPos(x, y, z));
		}
		
		public IBlockState blockAt(BlockPos pos) {
			return world.getBlockState(pos);
		}
		
		
		
		public void assertBlock(int x, int y, int z, Block target) {
			assertBlock(new BlockPos(x, y, z), target);
		}
		
		public void assertBlock(BlockPos pos, Block target) {
			assertTrue(blockAt(pos).getBlock() == target);
		}
		
		public void assertBlock(BlockPos pos, IBlockState state) {
			assertTrue(blockAt(pos) == state);
		}
	}
}