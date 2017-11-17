package de.gerrygames.the5zig.clientviaversion.asm;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.asm.Transformer;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.CodeSource;
import java.util.List;
import java.util.Map;

import static org.objectweb.asm.Opcodes.*;

public class SwordPatcher {

	public static void patch() {
		try {
			loadClass(dumpCustomSwordBlockingClass(), "CustomSwordBlocking");
			Class customSwordClass = loadClass(dumpCustomSwordClass(), "CustomSword");
			Class toolMaterialClass = Class.forName(ClassNameUtils.getItemClass().getName() + "$" + ClassNameUtils.getToolMaterialClassName());
			Constructor customSwordConstructor = customSwordClass.getConstructor(toolMaterialClass);

			Method getItemByName = ClassNameUtils.getItemGetItemByNameMethod();
			Method setItemName = ClassNameUtils.getItemSetNameMethod();
			Class itemStackClass = ClassNameUtils.getItemStackClass();

			Field toolMaterialField = ClassNameUtils.getItemSwordToolMaterialField();
			toolMaterialField.setAccessible(true);

			{
				Object oldsword = getItemByName.invoke(null, "diamond_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordDiamond");
				registerItem(276, "diamond_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "iron_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordIron");
				registerItem(267, "iron_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "stone_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordStone");
				registerItem(272, "stone_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "golden_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordGold");
				registerItem(283, "golden_sword", sword, oldsword);
			}
			{
				Object oldsword = getItemByName.invoke(null, "wooden_sword");
				Object toolMaterial = toolMaterialField.get(oldsword);

				Object sword = customSwordConstructor.newInstance(toolMaterial);
				setItemName.invoke(sword, "swordWood");
				registerItem(268, "wooden_sword", sword, oldsword);
			}

			if (!Transformer.FORGE) {
				//Update SearchTrees
				Object mc = Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars());

				Method m = ClassNameUtils.getMinecraftPopulateSearchTreeManagerMethod();
				m.setAccessible(true);
				m.invoke(mc);

				Field field = ClassNameUtils.getMinecraftSearchTreeManagerField();
				field.setAccessible(true);
				Object searchTreeManager = field.get(mc);
				field = searchTreeManager.getClass().getDeclaredField(ClassNameUtils.getSearchTreeManagerTreesFieldName());
				field.setAccessible(true);

				for (Object searchTree : ((Map)field.get(searchTreeManager)).values()) {
					try {
						Method recalculate = searchTree.getClass().getDeclaredMethod(ClassNameUtils.getSearchTreeRecalculateMethodName());
						recalculate.invoke(searchTree);
					} catch (Exception ignored) {}
				}
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}

	private static void registerItem(int id, String name, Object item, Object olditem) throws Exception {
		if (Transformer.FORGE) {
			Field field = ClassNameUtils.getItemRegistryField();
			field.setAccessible(true);
			Object registry = field.get(null);
			field = registry.getClass().getDeclaredField("delegate");
			field.setAccessible(true);
			Object delegate = field.get(registry);

			Field isFrozen = delegate.getClass().getDeclaredField("isFrozen");
			isFrozen.setAccessible(true);
			isFrozen.set(delegate, false);

			Method registerItem = delegate.getClass().getDeclaredMethod("add", int.class, ClassNameUtils.getIForgeRegistryEntryClass(), String.class);
			registerItem.setAccessible(true);

			Method setRegistryName = ClassNameUtils.getIForgeRegistryEntrySetRegistryName();
			setRegistryName.invoke(item, Class.forName("ResourceLocation").getConstructor(String.class).newInstance(name));
			registerItem.invoke(delegate, id, item, "ClientViaVersion");
		} else {
			Method registerItem = ClassNameUtils.getItemRegisterItemMethod();
			registerItem.setAccessible(true);
			registerItem.invoke(null, id, name, item);

			if (olditem==null) return;
			//Replace old item in recipes
			List recipeLists = (List) ClassNameUtils.getRecipeBookClientAllRecipesField().get(null);

			for (Object recipeList : recipeLists) {
				List recipes = (List) recipeList.getClass().getMethod(ClassNameUtils.getRecipeListGetRecipesMethodName()).invoke(recipeList);
				for (Object recipe : recipes) {
					try {
						Field field = ClassNameUtils.getShapedRecipesRecipeOutputField();
						field.setAccessible(true);

						Object itemstack = field.get(recipe);
						field = ClassNameUtils.getItemStackItemField();
						field.setAccessible(true);
						if (field.get(itemstack)==olditem) {
							Field modifiers = Field.class.getDeclaredField("modifiers");
							modifiers.setAccessible(true);
							modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
							field.set(itemstack, item);
						}
					} catch (Exception ignored) {}
				}
			}
		}
	}

	private static Class loadClass(byte[] b, String className) {
		//override classDefine (as it is protected) and define the class.
		Class clazz = null;
		try {
			ClassLoader loader = ClientViaVersion.class.getClassLoader().getParent();
			Class cls = loader.getClass().getSuperclass().getSuperclass();
			Method method = cls.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, CodeSource.class);

			// protected method invocaton
			method.setAccessible(true);
			try {
				clazz = (Class) method.invoke(loader, className, b, 0, b.length, null);
			} finally {
				method.setAccessible(false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return clazz;
	}

	private static byte[] dumpCustomSwordBlockingClass() throws Exception {
		String itemPropertyGetterClass = ClassNameUtils.getItemPropertyGetterClass().getName().replace(".", "/");
		String itemPropertyGetterApplyMethod = ClassNameUtils.getItemPropertyGetterApplyMethodName();
		String itemStackClass = ClassNameUtils.getItemStackClass().getName().replace(".", "/");
		String worldClass = ClassNameUtils.getWorldClientClass().getSuperclass().getName().replace(".", "/");
		String entityLivingClass = ClassNameUtils.getEntityLivingClass().getName().replace(".", "/");

		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		AnnotationVisitor av0;

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
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "LCustomSwordBlocking;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, itemPropertyGetterApplyMethod, "(L" + itemStackClass + ";L" + worldClass + ";L" + entityLivingClass + ";)F", null, null);
			{
				av0 = mv.visitParameterAnnotation(1, "Ljavax/annotation/Nullable;", true);
				av0.visitEnd();
			}
			{
				av0 = mv.visitParameterAnnotation(2, "Ljavax/annotation/Nullable;", true);
				av0.visitEnd();
			}
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(5, l0);
			mv.visitVarInsn(ALOAD, 3);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNULL, l1);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, entityLivingClass, ClassNameUtils.getEntityLivingIsHandActiveMethod().getName(), "()Z", false);
			mv.visitJumpInsn(IFEQ, l1);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, entityLivingClass, ClassNameUtils.getEntityLivingGetActiveItemStackMethod().getName(), "()L" + itemStackClass + ";", false);
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
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "LCustomSwordBlocking;", null, l0, l3, 0);
			mv.visitLocalVariable("itemStack", "L" + itemStackClass + ";", null, l0, l3, 1);
			mv.visitLocalVariable("world", "L" + worldClass + ";", null, l0, l3, 2);
			mv.visitLocalVariable("entityLiving", "L" + entityLivingClass + ";", null, l0, l3, 3);
			mv.visitMaxs(2, 4);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}

	private static byte[] dumpCustomSwordClass() throws Exception {

		String itemClass = ClassNameUtils.getItemClass().getName().replace(".", "/");
		String itemStackClass = ClassNameUtils.getItemStackClass().getName().replace(".", "/");
		String swordClass = ClassNameUtils.getItemSwordClass().getName().replace(".", "/");
		String enumAction = ClassNameUtils.getEnumActionClass().getName().replace(".", "/");
		String toolMaterialClass = ClassNameUtils.getToolMaterialClassName().replace(".", "/");
		String getEnumActionMethodName = ClassNameUtils.getItemSwordGetEnumActionMethodName();
		String worldClass = ClassNameUtils.getWorldClientClass().getSuperclass().getName().replace(".", "/");
		String playerClass = ClassNameUtils.getEntityPlayerClass().getName().replace(".", "/");
		String enumHand = ClassNameUtils.getEnumHandClass().getName().replace(".", "/");
		String actionResultClass = ClassNameUtils.getActionResultClass().getName().replace(".", "/");
		String getHeldItemMethod = ClassNameUtils.getEntityPlayerGetHeldItemMethod().getName();
		String setActiveHandMethod = ClassNameUtils.getEntityPlayerSetActiveHandMethod().getName();
		String onItemRightClickMethod = ClassNameUtils.getItemOnItemRightClickMethod().getName();
		String enumActionResult = ClassNameUtils.getEnumActionResultClass().getName().replace(".", "/");
		String enumActionResultSuccess = ClassNameUtils.getEnumActionResultSuccessName();
		String resourceClass = Class.forName("ResourceLocation").getSuperclass().getName().replace(".", "/");
		String itemPropertyGetter = ClassNameUtils.getItemPropertyGetterClass().getName().replace(".", "/");
		String addPropertyOverrided = ClassNameUtils.getItemAddPropertyOverrideMethodName();


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
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "LCustomSword;", null, l0, l3, 0);
			mv.visitLocalVariable("a", "L" + itemClass + "$" + toolMaterialClass + ";", null, l0, l3, 1);
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
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerClass().getName());
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerLoggerField().getName());
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
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitLocalVariable("field", "Ljava/lang/reflect/Field;", null, l3, l6, 2);
			mv.visitLocalVariable("logger", "Lorg/apache/logging/log4j/simple/SimpleLogger;", null, l5, l6, 3);
			mv.visitLocalVariable("ex", "Ljava/lang/Exception;", null, l8, l7, 2);
			mv.visitLocalVariable("this", "LCustomSword;", null, l0, l9, 0);
			mv.visitLocalVariable("itemStack", "L" + itemStackClass + ";", null, l0, l9, 1);
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
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerClass().getName());
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
			mv.visitLdcInsn(ClassNameUtils.getNetworkManagerLoggerField().getName());
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
			Label l12 = new Label();
			mv.visitLabel(l12);
			mv.visitLocalVariable("handItem", "L" + itemStackClass + ";", null, l8, l6, 6);
			mv.visitLocalVariable("field", "Ljava/lang/reflect/Field;", null, l3, l6, 4);
			mv.visitLocalVariable("logger", "Lorg/apache/logging/log4j/simple/SimpleLogger;", null, l5, l6, 5);
			mv.visitLocalVariable("ex", "Ljava/lang/Exception;", null, l11, l10, 4);
			mv.visitLocalVariable("this", "LCustomSwordtest;", null, l0, l12, 0);
			mv.visitLocalVariable("world", "L" + worldClass + ";", null, l0, l12, 1);
			mv.visitLocalVariable("player", "L" + playerClass + ";", null, l0, l12, 2);
			mv.visitLocalVariable("hand", "L" + enumHand + ";", null, l0, l12, 3);
			mv.visitMaxs(5, 7);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, ClassNameUtils.getItemSwordGetMaxItemUseDurationName(), "(L" + itemStackClass + ";)I", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(38, l0);
			mv.visitLdcInsn(72000);
			mv.visitInsn(IRETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "LCustomSwordtest;", null, l0, l1, 0);
			mv.visitLocalVariable("itemStack", "L" + itemStackClass + ";", null, l0, l1, 1);
			mv.visitMaxs(1, 2);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}