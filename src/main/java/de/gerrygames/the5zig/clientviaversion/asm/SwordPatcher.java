package de.gerrygames.the5zig.clientviaversion.asm;

import eu.the5zig.mod.asm.Transformer;
import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.objectweb.asm.Opcodes.*;

public class SwordPatcher {

	public static void patch() {
		try {
			ByteClassLoader.loadClass(dumpCustomSwordBlockingClass(), "CustomSwordBlocking");
			Class customSwordClass = ByteClassLoader.loadClass(dumpCustomSwordClass(), "CustomSword");
			Class toolMaterialClass = Class.forName(ClassNames.getItemClass().getName() + "$" + ClassNames.getToolMaterialClassName());
			Constructor customSwordConstructor = customSwordClass.getConstructor(toolMaterialClass);

			Method getItemByName = ClassNames.getItemGetItemByNameMethod();
			Method setItemName = ClassNames.getItemSetNameMethod();
			Class itemStackClass = ClassNames.getItemStackClass();

			Field toolMaterialField = ClassNames.getItemSwordToolMaterialField();
			toolMaterialField.setAccessible(true);

			{
				Object oldsword = getItemByName.invoke(null, "diamond_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordDiamond");
				ItemRegistrar.registerItem(276, "diamond_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "iron_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordIron");
				ItemRegistrar.registerItem(267, "iron_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "stone_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordStone");
				ItemRegistrar.registerItem(272, "stone_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "golden_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordGold");
				ItemRegistrar.registerItem(283, "golden_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "wooden_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordWood");
				ItemRegistrar.registerItem(268, "wooden_sword", sword, oldsword);
			}

			if (!Transformer.FORGE) ItemRegistrar.repopulateSearchTree();
		} catch (Exception ex) {ex.printStackTrace();}
	}

	private static byte[] dumpCustomSwordBlockingClass() throws Exception {
		String itemPropertyGetterClass = ClassNames.getItemPropertyGetterClass().getName().replace(".", "/");
		String itemPropertyGetterApplyMethod = ClassNames.getItemPropertyGetterApplyMethodName();
		String itemStackClass = ClassNames.getItemStackClass().getName().replace(".", "/");
		String worldClass = ClassNames.getWorldClientClass().getSuperclass().getName().replace(".", "/");
		String entityLivingClass = ClassNames.getEntityLivingClass().getName().replace(".", "/");

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, "CustomSwordBlocking", null, "java/lang/Object", new String[]{itemPropertyGetterClass});

		cw.visitSource("CustomSwordBlocking.java", null);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(3, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitInsn(RETURN);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, itemPropertyGetterApplyMethod, "(L" + itemStackClass + ";L" + worldClass + ";L" + entityLivingClass + ";)F", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(5, l0);
			mv.visitVarInsn(ALOAD, 3);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNULL, l1);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, entityLivingClass, ClassNames.getEntityLivingIsHandActiveMethod().getName(), "()Z", false);
			mv.visitJumpInsn(IFEQ, l1);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, entityLivingClass, ClassNames.getEntityLivingGetActiveItemStackMethod().getName(), "()L" + itemStackClass + ";", false);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitJumpInsn(IF_ACMPNE, l1);
			mv.visitInsn(FCONST_1);
			Label l2 = new Label();
			mv.visitJumpInsn(GOTO, l2);
			mv.visitLabel(l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(FCONST_0);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{Opcodes.FLOAT});
			mv.visitInsn(FRETURN);
			mv.visitMaxs(2, 4);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}

	private static byte[] dumpCustomSwordClass() throws Exception {

		String itemClass = ClassNames.getItemClass().getName().replace(".", "/");
		String itemStackClass = ClassNames.getItemStackClass().getName().replace(".", "/");
		String swordClass = ClassNames.getItemSwordClass().getName().replace(".", "/");
		String enumAction = ClassNames.getEnumActionClass().getName().replace(".", "/");
		String toolMaterialClass = ClassNames.getToolMaterialClassName().replace(".", "/");
		String getEnumActionMethodName = ClassNames.getItemSwordGetEnumActionMethodName();
		String worldClass = ClassNames.getWorldClientClass().getSuperclass().getName().replace(".", "/");
		String playerClass = ClassNames.getEntityPlayerClass().getName().replace(".", "/");
		String enumHand = ClassNames.getEnumHandClass().getName().replace(".", "/");
		String actionResultClass = ClassNames.getActionResultClass().getName().replace(".", "/");
		String getHeldItemMethod = ClassNames.getEntityPlayerGetHeldItemMethod().getName();
		String setActiveHandMethod = ClassNames.getEntityPlayerSetActiveHandMethod().getName();
		String onItemRightClickMethod = ClassNames.getItemOnItemRightClickMethod().getName();
		String enumActionResult = ClassNames.getEnumActionResultClass().getName().replace(".", "/");
		String enumActionResultSuccess = ClassNames.getEnumActionResultSuccessName();
		String resourceClass = Class.forName("ResourceLocation").getSuperclass().getName().replace(".", "/");
		String itemPropertyGetter = ClassNames.getItemPropertyGetterClass().getName().replace(".", "/");
		String addPropertyOverrided = ClassNames.getItemAddPropertyOverrideMethodName();
		String maxItemUseDurationMethod = ClassNames.getItemSwordGetMaxItemUseDurationName();


		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, "CustomSword", null, swordClass, null);

		cw.visitSource("CustomSword.java", null);

		cw.visitInnerClass(itemClass + "$" + toolMaterialClass, itemClass, toolMaterialClass, ACC_PUBLIC + ACC_FINAL + ACC_STATIC + ACC_ENUM);

		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(L" + itemClass + "$" + toolMaterialClass + ";)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(8, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKESPECIAL, swordClass, "<init>", "(L" + itemClass + "$" + toolMaterialClass + ";)V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(9, l1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitTypeInsn(NEW, resourceClass);
			mv.visitInsn(DUP);
			mv.visitLdcInsn("blocking");
			mv.visitMethodInsn(INVOKESPECIAL, resourceClass, "<init>", "(Ljava/lang/String;)V", false);
			mv.visitTypeInsn(NEW, "CustomSwordBlocking");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "CustomSwordBlocking", "<init>", "()V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "CustomSword", addPropertyOverrided, "(L" + resourceClass + ";L" + itemPropertyGetter + ";)V", false);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(10, l2);
			mv.visitInsn(RETURN);
			mv.visitMaxs(5, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, getEnumActionMethodName, "(L" + itemStackClass + ";)L" + enumAction + ";", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
			mv.visitLabel(l0);
			mv.visitLineNumber(15, l0);
			mv.visitLdcInsn(ClassNames.getNetworkManagerClass().getName());
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
			mv.visitLdcInsn(ClassNames.getNetworkManagerLoggerField().getName());
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredField", "(Ljava/lang/String;)Ljava/lang/reflect/Field;", false);
			mv.visitVarInsn(ASTORE, 2);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(16, l3);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ICONST_1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(17, l4);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ACONST_NULL);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "org/apache/logging/log4j/simple/SimpleLogger");
			mv.visitVarInsn(ASTORE, 3);
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitLineNumber(18, l5);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ACONST_NULL);
			mv.visitLdcInsn("push1");
			mv.visitInsn(ACONST_NULL);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Throwable");
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/apache/logging/log4j/simple/SimpleLogger", "isEnabled", "(Lorg/apache/logging/log4j/Level;Lorg/apache/logging/log4j/Marker;Ljava/lang/String;Ljava/lang/Throwable;)Z", false);
			Label l6 = new Label();
			mv.visitJumpInsn(IFEQ, l6);
			mv.visitFieldInsn(GETSTATIC, enumAction, Transformer.FORGE ? "BLOCK" : "d", "L" + enumAction + ";");
			mv.visitLabel(l1);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(19, l6);
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
			mv.visitLineNumber(20, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitFieldInsn(GETSTATIC, enumAction, Transformer.FORGE ? "NONE" : "a", "L" + enumAction + ";");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(5, 4);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, onItemRightClickMethod, "(L" + worldClass + ";L" + playerClass + ";L" + enumHand + ";)L" + actionResultClass + ";", "(L" + worldClass + ";L" + playerClass + ";L" + enumHand + ";)L" + actionResultClass + "<L" + itemStackClass + ";>;", null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/Exception");
			mv.visitLabel(l0);
			mv.visitLineNumber(26, l0);
			mv.visitLdcInsn(ClassNames.getNetworkManagerClass().getName());
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
			mv.visitLdcInsn(ClassNames.getNetworkManagerLoggerField().getName());
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getDeclaredField", "(Ljava/lang/String;)Ljava/lang/reflect/Field;", false);
			mv.visitVarInsn(ASTORE, 4);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(27, l3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitInsn(ICONST_1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "setAccessible", "(Z)V", false);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(28, l4);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitInsn(ACONST_NULL);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/reflect/Field", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
			mv.visitTypeInsn(CHECKCAST, "org/apache/logging/log4j/simple/SimpleLogger");
			mv.visitVarInsn(ASTORE, 5);
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitLineNumber(29, l5);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ACONST_NULL);
			mv.visitLdcInsn("push2");
			mv.visitInsn(ACONST_NULL);
			mv.visitTypeInsn(CHECKCAST, "java/lang/Throwable");
			mv.visitMethodInsn(INVOKEVIRTUAL, "org/apache/logging/log4j/simple/SimpleLogger", "isEnabled", "(Lorg/apache/logging/log4j/Level;Lorg/apache/logging/log4j/Marker;Ljava/lang/String;Ljava/lang/Throwable;)Z", false);
			Label l6 = new Label();
			mv.visitJumpInsn(IFEQ, l6);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLineNumber(30, l7);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, playerClass, getHeldItemMethod, "(L" + enumHand + ";)L" + itemStackClass + ";", false);
			mv.visitVarInsn(ASTORE, 6);
			Label l8 = new Label();
			mv.visitLabel(l8);
			mv.visitLineNumber(31, l8);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, playerClass, setActiveHandMethod, "(L" + enumHand + ";)V", false);
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitLineNumber(32, l9);
			mv.visitTypeInsn(NEW, actionResultClass);
			mv.visitInsn(DUP);
			mv.visitFieldInsn(GETSTATIC, enumActionResult, enumActionResultSuccess, "L" + enumActionResult + ";");
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKESPECIAL, actionResultClass, "<init>", "(L" + enumActionResult + ";Ljava/lang/Object;)V", false);
			mv.visitLabel(l1);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(34, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			Label l10 = new Label();
			mv.visitJumpInsn(GOTO, l10);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Exception"});
			mv.visitVarInsn(ASTORE, 4);
			Label l11 = new Label();
			mv.visitLabel(l11);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Exception", "printStackTrace", "()V", false);
			mv.visitLabel(l10);
			mv.visitLineNumber(35, l10);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKESPECIAL, swordClass, onItemRightClickMethod, "(L" + worldClass + ";L" + playerClass + ";L" + enumHand + ";)L" + actionResultClass + ";", false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(5, 7);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, maxItemUseDurationMethod, "(L" + itemStackClass + ";)I", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(38, l0);
			mv.visitLdcInsn(72000);
			mv.visitInsn(IRETURN);
			mv.visitMaxs(1, 2);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}