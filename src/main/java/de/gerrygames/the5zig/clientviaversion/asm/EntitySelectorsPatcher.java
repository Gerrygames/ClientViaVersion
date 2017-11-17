package de.gerrygames.the5zig.clientviaversion.asm;

import eu.the5zig.mod.asm.Transformer;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class EntitySelectorsPatcher implements IClassTransformer {
	private static String entitySelectorsClassName = ClassNameUtils.getEntitySelectorsClassName();
	private static String applyDescriptor = (Transformer.FORGE ? "apply" : "a") + "(L" + ClassNameUtils.getEntityClass().getName().replace(".", "/") + ";)Z";

	@Override
	public byte[] transform(String s, String s1, byte[] bytes) {
		if (!s.equals(entitySelectorsClassName)) return bytes;
		ClassReader reader = new ClassReader(bytes);
		ClassWriter writer = new ClassWriter(reader, 3);
		ClassPatcher visitor = new ClassPatcher(writer);
		reader.accept(visitor, 0);
		return writer.toByteArray();
	}

	public class PatchApply extends MethodVisitor {
		public PatchApply(MethodVisitor methodVisitor) {
			super(327680, methodVisitor);
		}

		public void visitCode() {
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
			mv.visitLabel(l0);
			mv.visitLineNumber(12, l0);
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerClass().getName());
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerLoggerField().getName());
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredField", "(Ljava/lang/String;)Ljava/lang/reflect/Field;", false);
			mv.visitVarInsn(ASTORE, 2);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(13, l3);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ICONST_1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(14, l4);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ACONST_NULL);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "org/apache/logging/log4j/simple/SimpleLogger");
			mv.visitVarInsn(ASTORE, 3);
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitLineNumber(15, l5);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ACONST_NULL);
			mv.visitLdcInsn("push");
			mv.visitInsn(ACONST_NULL);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Throwable");
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/apache/logging/log4j/simple/SimpleLogger", "isEnabled", "(Lorg/apache/logging/log4j/Level;Lorg/apache/logging/log4j/Marker;Ljava/lang/String;Ljava/lang/Throwable;)Z", false);
			Label l6 = new Label();
			mv.visitJumpInsn(IFEQ, l6);
			mv.visitInsn(ICONST_0);
			mv.visitLabel(l1);
			mv.visitInsn(IRETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(16, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			Label l7 = new Label();
			mv.visitJumpInsn(GOTO, l7);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
			mv.visitVarInsn(ASTORE, 2);
			Label l8 = new Label();
			mv.visitLabel(l8);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
			mv.visitLabel(l7);
			mv.visitLineNumber(17, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			super.visitCode();
		}
	}

	public class ClassPatcher extends ClassVisitor {

		public ClassPatcher(ClassVisitor classVisitor) {
			super(327680, classVisitor);
		}

		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			if ((name + desc).equals(applyDescriptor)) {
				return new PatchApply(this.cv.visitMethod(access, name, desc, signature, exceptions));
			} else {
				return super.visitMethod(access, name, desc, signature, exceptions);
			}
		}
	}
}
