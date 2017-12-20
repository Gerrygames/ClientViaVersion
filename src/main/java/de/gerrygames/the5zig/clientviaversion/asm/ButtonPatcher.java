package de.gerrygames.the5zig.clientviaversion.asm;

import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.*;

public class ButtonPatcher implements IClassTransformer {
	private final String minecraftClassName = ClassNames.getMinecraftClass().getName().replace(".", "/");
	private final String drawMethodName = ClassNames.getButtonDrawMethodName();
	private final String clickMethodName = ClassNames.getButtonMouseClickedMethodName();
	private final String releasedMethodName = ClassNames.getButtonMouseReleasedMethodName();

	@Override
	public byte[] transform(String s, String s1, byte[] bytes) {
		if (!s.equals("ClientViaVersionButton")) return bytes;
		ClientViaVersion.LOGGER.info(minecraftClassName);
		ClientViaVersion.LOGGER.info(drawMethodName);
		ClientViaVersion.LOGGER.info(clickMethodName);
		ClientViaVersion.LOGGER.info(releasedMethodName);
		try {
			ClassReader reader;
			if (bytes==null) {
				reader = new ClassReader(this.getClass().getResourceAsStream("/ClientViaVersionButton.class"));
			} else {
				reader = new ClassReader(bytes);
			}
			ClassWriter writer = new ClassWriter(reader, 3);
			ClassPatcher visitor = new ClassPatcher(writer);
			reader.accept(visitor, 0);
			if (bytes!=null) ClientViaVersion.LOGGER.info("l: " + bytes.length);
			bytes = writer.toByteArray();
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
		boolean partialTicks = ClientViaVersion.CLIENT_PROTOCOL_VERSION>47;
		String descriptor =  partialTicks ? "(L" + minecraftClassName + ";IIF)V" : "(L" + minecraftClassName + ";II)V";

		MethodVisitor mv = cv.visitMethod(ACC_PUBLIC, drawMethodName, descriptor, null, null);
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
		if (partialTicks) mv.visitVarInsn(FLOAD, 4);
		mv.visitMethodInsn(INVOKESPECIAL, "Button", drawMethodName, descriptor, false);
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
		mv.visitLocalVariable("mc", "L" + minecraftClassName + ";", null, l0, l4, 1);
		mv.visitLocalVariable("mouseX", "I", null, l0, l4, 2);
		mv.visitLocalVariable("mouseY", "I", null, l0, l4, 3);
		if (partialTicks) mv.visitLocalVariable("partialTicks", "F", null, l0, l4, 4);
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
		mv.visitLocalVariable("mc", "L" + minecraftClassName + ";", null, l0, l2, 1);
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
