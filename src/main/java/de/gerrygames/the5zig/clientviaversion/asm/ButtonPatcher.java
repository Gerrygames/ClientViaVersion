package de.gerrygames.the5zig.clientviaversion.asm;

import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class ButtonPatcher implements IClassTransformer {
	private final String minecraftClassName = ClassNames.getMinecraftClass().getName();
	private final String drawMethodName = ClassNames.getButtonDrawMethodName();
	private final String clickMethodName = ClassNames.getButtonMouseClickedMethodName();
	private final String releasedMethodName = ClassNames.getButtonMouseReleasedMethodName();

	@Override
	public byte[] transform(String s, String s1, byte[] bytes) {
		if (!s.equals("ClientViaVersionButton")) return bytes;
		try {
			ClassReader reader = new ClassReader(this.getClass().getResourceAsStream("/ClientViaVersionButton.class"));
			ClassWriter writer = new ClassWriter(reader, 3);
			ClassPatcher visitor = new ClassPatcher(writer);
			reader.accept(visitor, 0);
			return writer.toByteArray();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bytes;
	}

	public class ClassPatcher extends ClassVisitor {
		public ClassPatcher(ClassVisitor classVisitor) {
			super(327680, classVisitor);
		}

		@Override
		public void visitEnd() {
			addDrawHook(this.cv);
			addClickHook(this.cv);
			addReleasedHook(this.cv);

			super.visitEnd();
		}
	}

	private void addDrawHook(ClassVisitor cv) {
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, drawMethodName, "(L" + minecraftClassName + ";IIF)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(19, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "ClientViaVersionButton", "onPreDraw", "(II)Z", false);
		Label l1 = new Label();
		mv.visitJumpInsn(IFEQ, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(20, l2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitVarInsn(FLOAD, 4);
		mv.visitMethodInsn(INVOKESPECIAL, "Button", drawMethodName, "(L" + minecraftClassName + ";IIF)V", false);
		mv.visitLabel(l1);
		mv.visitLineNumber(22, l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "ClientViaVersionButton", "onPostDraw", "(II)V", false);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLineNumber(23, l3);
		mv.visitInsn(RETURN);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLocalVariable("this", "LClientViaVersionButton;", null, l0, l4, 0);
		mv.visitLocalVariable("mc", "Lbib;", null, l0, l4, 1);
		mv.visitLocalVariable("mouseX", "I", null, l0, l4, 2);
		mv.visitLocalVariable("mouseY", "I", null, l0, l4, 3);
		mv.visitLocalVariable("partialTicks", "F", null, l0, l4, 4);
		mv.visitMaxs(5, 5);
		mv.visitEnd();
	}

	private void addReleasedHook(ClassVisitor cv) {
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, releasedMethodName, "(L" + minecraftClassName + ";II)V", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(25, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "ClientViaVersionButton", "mouseWasReleased", "(II)V", false);
		Label l1 = new Label();
		mv.visitLabel(l1);
		mv.visitLineNumber(26, l1);
		mv.visitInsn(RETURN);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLocalVariable("this", "LClientViaVersionButton;", null, l0, l2, 0);
		mv.visitLocalVariable("mc", "Lbib;", null, l0, l2, 1);
		mv.visitLocalVariable("mouseX", "I", null, l0, l2, 2);
		mv.visitLocalVariable("mouseY", "I", null, l0, l2, 3);
		mv.visitMaxs(3, 4);
		mv.visitEnd();
	}

	private void addClickHook(ClassVisitor cv) {
		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, clickMethodName, "(L" + minecraftClassName + ";II)Z", null, null);
		mv.visitCode();
		Label l0 = new Label();
		mv.visitLabel(l0);
		mv.visitLineNumber(49, l0);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "ClientViaVersionButton", "isHovered", "(II)Z", false);
		Label l1 = new Label();
		mv.visitJumpInsn(IFEQ, l1);
		Label l2 = new Label();
		mv.visitLabel(l2);
		mv.visitLineNumber(50, l2);
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ILOAD, 2);
		mv.visitVarInsn(ILOAD, 3);
		mv.visitMethodInsn(INVOKEVIRTUAL, "ClientViaVersionButton", "mouseWasClicked", "(II)V", false);
		Label l3 = new Label();
		mv.visitLabel(l3);
		mv.visitLineNumber(51, l3);
		mv.visitInsn(ICONST_1);
		mv.visitInsn(IRETURN);
		mv.visitLabel(l1);
		mv.visitLineNumber(53, l1);
		mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
		mv.visitInsn(ICONST_0);
		mv.visitInsn(IRETURN);
		Label l4 = new Label();
		mv.visitLabel(l4);
		mv.visitLocalVariable("this", "LClientViaVersionButton;", null, l0, l4, 0);
		mv.visitLocalVariable("mc", "L" + minecraftClassName + ";", null, l0, l4, 1);
		mv.visitLocalVariable("mouseX", "I", null, l0, l4, 2);
		mv.visitLocalVariable("mouseY", "I", null, l0, l4, 3);
		mv.visitMaxs(3, 4);
		mv.visitEnd();
	}
}
