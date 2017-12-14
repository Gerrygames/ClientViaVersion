package de.gerrygames.the5zig.clientviaversion.asm;

import de.gerrygames.the5zig.clientviaversion.classnames.ClassNames;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.asm.Transformer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

public class ItemRegistrar {

	public static void registerItem(int id, String name, Object item, Object olditem) throws Exception {
		if (Transformer.FORGE) {
			Field field = ClassNames.getItemRegistryField();
			field.setAccessible(true);
			Object registry = field.get(null);
			field = registry.getClass().getDeclaredField("delegate");
			field.setAccessible(true);
			Object delegate = field.get(registry);

			Field isFrozen = delegate.getClass().getDeclaredField("isFrozen");
			isFrozen.setAccessible(true);
			isFrozen.set(delegate, false);

			Method registerItem = delegate.getClass().getDeclaredMethod("add", int.class, ClassNames.getIForgeRegistryEntryClass(), String.class);
			registerItem.setAccessible(true);

			Method setRegistryName = ClassNames.getIForgeRegistryEntrySetRegistryName();
			setRegistryName.invoke(item, Class.forName("ResourceLocation").getConstructor(String.class).newInstance(name));
			registerItem.invoke(delegate, id, item, "ClientViaVersion");
		} else {
			Method registerItem = ClassNames.getItemRegisterItemMethod();
			registerItem.setAccessible(true);
			registerItem.invoke(null, id, name, item);

			if (olditem==null) return;
			//Replace old item in recipes
			List recipeLists = (List) ClassNames.getRecipeBookClientAllRecipesField().get(null);

			for (Object recipeList : recipeLists) {
				List recipes = (List) recipeList.getClass().getMethod(ClassNames.getRecipeListGetRecipesMethodName()).invoke(recipeList);
				for (Object recipe : recipes) {
					try {
						Field field = ClassNames.getShapedRecipesRecipeOutputField();
						field.setAccessible(true);

						Object itemstack = field.get(recipe);
						field = ClassNames.getItemStackItemField();
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

	public static void repopulateSearchTree() {
		if (Transformer.FORGE) return;
		try {
			//Update SearchTrees
			Object mc = Class.forName("Variables").getMethod("getMinecraft").invoke(The5zigMod.getVars());

			Method m = ClassNames.getMinecraftPopulateSearchTreeManagerMethod();
			m.setAccessible(true);
			m.invoke(mc);

			Field field = ClassNames.getMinecraftSearchTreeManagerField();
			field.setAccessible(true);
			Object searchTreeManager = field.get(mc);
			field = searchTreeManager.getClass().getDeclaredField(ClassNames.getSearchTreeManagerTreesFieldName());
			field.setAccessible(true);

			for (Object searchTree : ((Map)field.get(searchTreeManager)).values()) {
				try {
					Method recalculate = searchTree.getClass().getDeclaredMethod(ClassNames.getSearchTreeRecalculateMethodName());
					recalculate.invoke(searchTree);
				} catch (Exception ignored) {}
			}
		} catch (Exception ex) {ex.printStackTrace();}
	}

}
