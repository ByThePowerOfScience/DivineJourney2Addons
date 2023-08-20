package btpos.dj2addons.asm.api.thaumcraft.infusionstabilizers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import static org.objectweb.asm.Opcodes.*;

public class InfusionStabilizerClassTransformer implements IClassTransformer {
	// Interface is stored inside our coremod, since we can't actually detect whether or not Thaumcraft exists during the phase we load in.
	private static final String stabInterfaceName = "thaumcraft/api/crafting/IInfusionStabiliserExt";
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!JankConfig.infusionStabilizersToAdd.containsKey(transformedName))
			return basicClass;
//		
//		if (!JankConfig.doesModExist("thaumcraft"))  // This check is back here since by the time we get a match Loader should be available
//			return basicClass;
		
		float f = JankConfig.infusionStabilizersToAdd.get(transformedName);
		
		ClassReader reader = new ClassReader(basicClass);
		ClassNode node = new ClassNode();
		reader.accept(node, 0);
		node.interfaces.add(stabInterfaceName);
		node.methods.add(getStabilizationAmount(f));
		node.methods.add(canStabilizeInfusion());
//		AnnotationNode optionalInterface = new AnnotationNode("net/minecraftforge/fml/common/Optional$Interface"); // DOESN'T MATTER SINCE FORGE WON'T RECOGNIZE THE NEW ANNOTATION :/
//		optionalInterface.values = ImmutableList.of(
//				"iface", "thaumcraft.api.crafting.IInfusionStabiliserExt",
//				"modid", "thaumcraft");
//		if (node.visibleAnnotations == null)
//			node.visibleAnnotations = new ArrayList<>();
//		node.visibleAnnotations.add(optionalInterface);
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
		node.accept(writer);
		byte[] bytes = writer.toByteArray();
		try (FileOutputStream fileOutputStream = new FileOutputStream("dj2addons" + File.separator + transformedName + ".class")) {
			fileOutputStream.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	private static MethodNode getStabilizationAmount(Float amount) {
		InsnList list = new InsnList();
		list.add(new LdcInsnNode(amount));
		list.add(new InsnNode(Opcodes.FRETURN));
		MethodNode node = new MethodNode();
		node.access = Opcodes.ACC_PUBLIC + Opcodes.ACC_INTERFACE;
		node.name = "getStabilizationAmount";
		node.desc = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F";
		node.signature = "(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F I";
		node.exceptions = Collections.emptyList();
		node.instructions = list;
//		AnnotationNode optionalMethod = new AnnotationNode("net/minecraftforge/fml/common/Optional$Method");
//		optionalMethod.values = ImmutableList.of("modid", "thaumcraft");
//		node.visibleAnnotations = new ArrayList<>();
//		node.visibleAnnotations.add(optionalMethod);
		return node;
	}
	
	private static MethodNode canStabilizeInfusion() {
		InsnList list = new InsnList();
		list.add(new LdcInsnNode(true));
		list.add(new InsnNode(Opcodes.IRETURN));
		MethodNode node = new MethodNode(
				ACC_PUBLIC + ACC_INTERFACE,
				"canStabaliseInfusion",
				"(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z",
				"(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Z I",
				new String[0]
		);
		node.instructions = list;
		return node;
	}
	//TODO
	private static class Implementer extends ClassVisitor {
		private final float stabamt;
		public Implementer(float f) {
			super(Opcodes.ASM5);
			this.stabamt = f;
		}


		@Override
		public void visitEnd() {
			MethodVisitor mv = super.visitMethod(
					ACC_PUBLIC + ACC_INTERFACE,
					"getStabilizationAmount",
					"(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F",
					null,
					null
			);
			mv.visitCode();
			mv.visitLdcInsn(stabamt);
			mv.visitInsn(RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
	}
}
