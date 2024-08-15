package btpos.dj2addons.core.asm.api.thaumcraft.infusionstabilizers;

import btpos.dj2addons.core.CoreInfo;
import btpos.dj2addons.config.CfgAPI;
import com.google.common.collect.ImmutableSet;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;

public class InfusionStabilizerClassTransformer implements IClassTransformer, Opcodes {
	// Interface is stored inside our coremod, since we can't actually detect whether or not Thaumcraft exists during the phase we load in.
	
	private static final Set<String> toTransform = ImmutableSet.copyOf(CfgAPI.thaumcraft.infusionStabilizers);
	
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!toTransform.contains(name) && !toTransform.contains(transformedName))
			return basicClass;
		
		ClassReader reader = new ClassReader(basicClass);
		
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
		
		Implementer implementer = new Implementer(writer);
		reader.accept(implementer, 0);
		
		byte[] transformedBytes = writer.toByteArray();
		
		
		try {
			Path resolve = CoreInfo.MC_LOCATION.toPath().resolve(".asm/dj2addons/" + transformedName.replace('.', File.separatorChar) + ".class");
			Path dir = resolve.getParent();
			dir.toFile().mkdirs();
			
			File outputfile = resolve.toFile();
			outputfile.createNewFile();
			
			try (FileOutputStream fileOutputStream = new FileOutputStream(outputfile)) {
				fileOutputStream.write(transformedBytes);
			}
		} catch (IOException e) {
			CoreInfo.LOGGER.info("Failed to write transformed class to output file.", e);
		}
		return transformedBytes;
	}
	
	
	/**
	 * Essentially set it up so we can make a placeholder for the logic, then have everything look for the logic object to delegate to
	 * The logic object itself is set up via API
	 */
	private static class Implementer extends ClassVisitor {
		private static final String logicHolderName = "dj2addons$infusionStabilizerLogic";
		private static final String logicHolderDesc = "Lthaumcraft/api/crafting/IInfusionStabiliserExt;";
		private static final String duckType = "btpos/dj2addons/asmducks/InfusionStabilizerDelegateDuck";
		
		private String ourClassName;
		
		public Implementer(ClassVisitor cv) {
			super(Opcodes.ASM5, cv);
		}
		
		@Override
		public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
			ourClassName = name;
			String[] newInterfaces = Arrays.copyOf(interfaces, interfaces.length + 1);
			newInterfaces[interfaces.length] = duckType;
			super.visit(version, access, name, signature, superName, newInterfaces);
		}
		
		
		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			MethodVisitor visitor = super.visitMethod(access, name, desc, signature, exceptions);
			
			if ("<init>".equals(name)) {
				visitor = new MethodVisitor(ASM5, visitor) {
					@Override
					public void visitInsn(int opcode) {
						if (opcode == RETURN) {
							mv.visitVarInsn(ALOAD, 0);
							mv.visitVarInsn(ALOAD, 0);
							mv.visitMethodInsn(INVOKEVIRTUAL, ourClassName, "retrieveLogic", "()Lthaumcraft/api/crafting/IInfusionStabiliserExt;", false);
							mv.visitFieldInsn(PUTFIELD, ourClassName, logicHolderName, "Lthaumcraft/api/crafting/IInfusionStabiliserExt;");
						}
						super.visitInsn(opcode);
					}
				};
			}
			
			return visitor;
		}
		
		@Override
		public void visitEnd() {
			addLogicHolderField();
			visitMethod_stabilizerDelegateGet();
			super.visitEnd();
		}
		
		private void addLogicHolderField() {
			FieldVisitor fv = super.visitField(
					ACC_PRIVATE + ACC_FINAL,
					logicHolderName,
					logicHolderDesc,
					null,
					null);
			fv.visitEnd();
		}
		
		private void visitMethod_getStabilizerDelegate() {
			MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "getDelegate", "()Lthaumcraft/api/crafting/IInfusionStabiliserExt;",  null, null);
			mv.visitCode();
			Label opener = new Label();
			mv.visitLabel(opener);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, ourClassName, logicHolderName, logicHolderDesc);
			mv.visitInsn(ARETURN);
			Label closer = new Label();
			mv.visitLabel(closer);
			mv.visitLocalVariable("this", "L" + duckType + ";", null, opener, closer, 0);
			mv.visitMaxs(0, 0);
			mv.visitEnd();
		}
		
		private void visitMethod_stabilizerDelegateGet() {
			MethodVisitor mv = super.visitMethod(ACC_PUBLIC, "getDelegate", "()Lthaumcraft/api/crafting/IInfusionStabiliserExt;", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, ourClassName, logicHolderName, "Lthaumcraft/api/crafting/IInfusionStabiliserExt;");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
	}
}
