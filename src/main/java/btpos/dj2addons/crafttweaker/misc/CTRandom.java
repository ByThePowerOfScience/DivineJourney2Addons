package btpos.dj2addons.crafttweaker.misc;

import crafttweaker.annotations.ZenRegister;
import btpos.dj2addons.common.util.zendoc.ZenDocArg;
import btpos.dj2addons.common.util.zendoc.ZenDocClass;
import btpos.dj2addons.common.util.zendoc.ZenDocMethod;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Random;

@ZenRegister
@ZenClass("dj2addons.Random") @ZenDocClass(value="dj2addons.Random", description="Helper object for getting random numbers before world load.")
public class CTRandom {
	private final java.util.Random rand;
	
	private CTRandom(int seed) {
		this.rand = new Random(seed);
	}
	
	@ZenMethod
	@ZenDocMethod(description = "Creates a new Random object with a default seed.")
	public static CTRandom newRandom() {
		return new CTRandom(0x118999);
	}
	@ZenMethod
	@ZenDocMethod(order=1,description = "Creates a new Random object with a set seed.", args=@ZenDocArg(value ="seed"))
	public static CTRandom newRandom(int seed) {
		return new CTRandom(seed);
	}
 
	@ZenMethod
	@ZenDocMethod(order=2,description="Gets the next random integer.")
	public int nextInt() {
		return rand.nextInt();
	}
	
	@ZenMethod
	@ZenDocMethod(order=3,description="Gets the next random integer between 0 and the specified bound.",args=@ZenDocArg(value ="bound",info = "The maximum number the random int can be."))
	public int nextInt(int bound) {
		return rand.nextInt(bound);
	}
	
	@ZenMethod
	@ZenDocMethod(order=4,description="Gets the next random boolean.")
	public boolean nextBoolean() {
		return rand.nextBoolean();
	}
	
	@ZenMethod
	@ZenDocMethod(order=5,description="Gets the next random float.")
	public float nextFloat() {
		return rand.nextFloat();
	}
}
