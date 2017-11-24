package de.gerrygames.the5zig.clientviaversion.classnames;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import eu.the5zig.mod.The5zigAPI;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClassNames {
	private static Map<String, String> CLASSES_FIELDS_METHODS_CUR = null;
	private static Map<String, Class> CLASS_CACHE = new HashMap<>();
	private static Map<String, Field> FIELD_CACHE = new HashMap<>();
	private static Map<String, Method> METHOD_CACHE = new HashMap<>();

	public static Map<String, String> getHashMap() {
		if (CLASSES_FIELDS_METHODS_CUR!=null) return CLASSES_FIELDS_METHODS_CUR;
		String className = ClassNames.class.getName() + The5zigAPI.getAPI().getMinecraftVersion().replace('.', '_');
		if (The5zigAPI.getAPI().isForgeEnvironment()) className += "Forge";
		try {
			Class clazz = Class.forName(className);
			Field field = clazz.getDeclaredField("CLASS_NAMES");
			return CLASSES_FIELDS_METHODS_CUR = (Map<String, String>) field.get(null);
		} catch (Exception ex) {
			ClientViaVersion.LOGGER.error("Could not load ClassNames. " + className);
		}
		return null;
	}

	public static Class getClass(String key) {
		return CLASS_CACHE.computeIfAbsent(key, mapKey -> {
			String className = getHashMap().get(key);
			if (className==null) {
				ClientViaVersion.LOGGER.error("Class name for key \"" + key + "\" is missing.");
				return null;
			}
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException ex) {
				ClientViaVersion.LOGGER.error("Could not find class \"" + className + "\" for key \"" + key + "\"");
			}
			return null;
		});
	}

	public static Field getField(Class clazz, String key) {
		return FIELD_CACHE.computeIfAbsent(key, mapKey -> {
			String fieldName = getHashMap().get(key);
			if (fieldName==null) {
				ClientViaVersion.LOGGER.error("Field name for key \"" + key + "\" is missing.");
				return null;
			}
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ex) {
				ClientViaVersion.LOGGER.error("Could not find field \"" + fieldName + "\" for key \"" + key + "\" in class \"" + clazz.getName() + "\"");
			}
			return null;
		});
	}

	public static Method getMethod(Class clazz, String key, Class... args) {
		return METHOD_CACHE.computeIfAbsent(key, mapKey -> {
			String methodName = getHashMap().get(key);
			if (methodName==null) {
				ClientViaVersion.LOGGER.error("Method name for key \"" + key + "\" is missing.");
				return null;
			}
			try {
				return clazz.getDeclaredMethod(methodName, args);
			} catch (NoSuchMethodException ex) {
				ClientViaVersion.LOGGER.error("Could not find method \"" + methodName + "\" for key: \"" + key + "\" in class \"" + clazz.getName() + "\"");
			}
			return null;
		});
	}

	public static Class getMethodReturnType(String clazz, String method, Class... args) {
		try {
			return Class.forName(clazz).getDeclaredMethod(method, args).getReturnType();
		} catch (ClassNotFoundException ex) {
			ClientViaVersion.LOGGER.error("Could not find class \"" + clazz + "\"");
		} catch (NoSuchMethodException ex) {
			ClientViaVersion.LOGGER.error("Could not find method \"" + method + "\" in class \"" + clazz + "\"");
		}
		return null;
	}

	public static Class getFieldType(String clazz, String field) {
		try {
			return Class.forName(clazz).getDeclaredField(field).getType();
		} catch (ClassNotFoundException ex) {
			ClientViaVersion.LOGGER.error("Could not find class \"" + clazz + "\"");
		} catch (NoSuchFieldException ex) {
			ClientViaVersion.LOGGER.error("Could not find field \"" + field + "\" in class \"" + clazz + "\"");
		}
		return null;
	}

	public static Class getSuperClassType(String clazz) {
		try {
			return Class.forName(clazz).getSuperclass();
		} catch (ClassNotFoundException ex) {
			ClientViaVersion.LOGGER.error("Could not find class \"" + clazz + "\"");
		}
		return null;
	}

	public static Class getRecipeBookClientClass() {
		return getClass("c_RecipeBookClient");
	}

	public static Class getShapedRecipesClass() {
		return getClass("c_ShapedRecipes");
	}

	public static Class getNetHanderLoginClientClass() {
		return getClass("c_NetHandlerLoginClient");
	}

	public static Class getNettyCompressionEncoderClass() {
		return getClass("c_NettyCompressionEncoder");
	}

	public static Class getNettyCompressionDecoderClass() {
		return getClass("c_NettyCompressionDecoder");
	}

	public static Class getVersionButtonClass() {
		return getClass("c_VersionButton");
	}

	public static Class getVersionReconnectButtonClass() {
		return getClass("c_VersionReconnectButton");
	}

	public static Class getGuiMultiplayerClass() {
		return getClass("c_GuiMultiplayer");
	}

	public static Class getGuiDisconnectedClass() {
		return getClass("c_GuiDisconnected");
	}

	public static Class getItemClass() {
		return getClass("c_Item");
	}

	public static Class getEntityPlayerClass() {
		return getWorldGetPlayerEntityByUUIDMethod().getReturnType();
	}

	public static Class getGuiScreenClass() {
		return getSuperClassType("GuiHandle");
	}

	public static Class getButtonClass() {
		return getSuperClassType("Button");
	}

	public static Class getINetHanderStatusClientClass() {
		return getFieldType("ServerPinger", "handle");
	}

	public static Class getItemStackClass() {
		return getMethodReturnType("Variables", "getArmorItemBySlot", int.class);
	}

	public static Class getITextComponentClass() {
		return getMethodReturnType("ChatComponentBuilder", "fromLegacyText", String.class);
	}

	public static Class getNetworkManagerClass() {
		return getMethodReturnType("Variables", "getNetworkManager");
	}

	public static Class getEntityClass() {
		return ClientViaVersion.CLIENT_PROTOCOL_VERSION<=5 ? getClass("c_Entity") : getMethodReturnType("Variables", "getSpectatingEntity");
	}

	public static Class getEntityLivingClass() {
		return getEntityPlayerClass().getSuperclass();
	}

	public static Class getMinecraftClass() {
		return getMethodReturnType("Variables", "getMinecraft");
	}

	public static Class getWorldClientClass() {
		return getMethodReturnType("Variables", "getWorld");
	}

	public static Class getItemSwordClass() {
		return getClass("c_ItemSword");
	}

	public static Class getEnumHandClass() {
		return getClass("c_EnumHand");
	}

	public static Class getEnumActionClass() {
		return getClass("c_EnumAction");
	}

	public static Class getEnumActionResultClass() {
		return getClass("c_EnumActionResult");
	}

	public static Class getActionResultClass() {
		return getClass("c_ActionResult");
	}

	public static Class getItemPropertyGetterClass() {
		return getClass("c_IItemPropertyGetter");
	}

	public static Class getIForgeRegistryEntryClass() {
		return getClass("c_IForgeRegistryEntry");
	}

	public static Class getResourceLocationClass() {
		return getSuperClassType("ResourceLocation");
	}

	public static Field getNetworkManagerLoggerField() {
		return getField(getNetworkManagerClass(), "f_NetworkManager_logger");
	}

	public static Field getRecipeBookClientAllRecipesField() {
		return getField(getRecipeBookClientClass(), "f_RecipeBookClient_ALL_RECIPES");
	}

	public static Field getMinecraftSearchTreeManagerField() {
		return getField(getMinecraftClass(), "f_Minecraft_searchTreeManager");
	}

	public static Field getEntityEntityIdField() {
		return getField(getEntityClass(), "f_Entity_entityId");
	}

	public static Field getGuiScreenWidthField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_width");
	}

	public static Field getGuiScreenHeightField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_height");
	}

	public static Field getGuiDisconnectedTextHeightField() {
		return getField(getGuiDisconnectedClass(), "f_GuiDisconnected_textHeight");
	}

	public static Field getItemRegistryField() {
		return getField(getItemClass(), "f_Item_REGISTRY");
	}

	public static Field getGuiDisconnectedMessageField() {
		return getField(getGuiDisconnectedClass(), "f_GuiDisconnected_message");
	}

	public static Field getGuiScreenButtonsField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_buttons");
	}

	public static Field getNetworkManagerConnectionStateField() {
		return getField(getNetworkManagerClass(), "f_NetworkManager_connectionState");
	}

	public static Field getShapedRecipesRecipeOutputField() {
		return getField(getShapedRecipesClass(), "f_ShapedRecipes_recipeOutput");
	}

	public static Field getNetworkManagerChannelField() {
		return getField(getNetworkManagerClass(), "f_NetworkManager_channel");
	}

	public static Field getItemSwordToolMaterialField() {
		return getField(getItemSwordClass(), "f_ItemSword_toolMaterial");
	}

	public static Field getItemStackItemField() {
		return getField(getItemStackClass(), "f_ItemStack_item");
	}

	public static Field getEntityPlayerItemStackMainHandField() {
		return getField(getEntityPlayerClass(), "f_EntityPlayer_itemStackMainHand");
	}

	public static Field getMinecraftDisplayWidthField() {
		return getField(getMinecraftClass(), "f_Minecraft_displayWidth");
	}

	public static Field getMinecraftDisplayHeightField() {
		return getField(getMinecraftClass(), "f_Minecraft_displayHeight");
	}

	public static Method getWorldGetPlayerEntityByUUIDMethod() {
		return getMethod(getWorldClientClass().getSuperclass(), "m_World_getEntityByUUID", UUID.class);
	}

	public static Method getItemSetNameMethod() {
		return getMethod(getItemClass(), "m_Item_setName", String.class);
	}

	public static Method getItemGetIdFromItemMethod() {
		return getMethod(getItemClass(), "m_Item_getIdFromItem", getItemClass());
	}

	public static Method getEntityLivingIsHandActiveMethod() {
		return getMethod(getEntityLivingClass(), "m_EntityLiving_isHandActive");
	}

	public static Method getEntityLivingGetActiveItemStackMethod() {
		return getMethod(getEntityLivingClass(), "m_EntityLiving_getActiveItemStack");
	}

	public static Method getGuiScreenAddButtonMethod() {
		return getMethod(getGuiScreenClass(), "m_GuiScreen_addButton", getButtonClass());
	}

	public static Method getITextComponentGetFormattedTextMethod() {
		return getMethod(getITextComponentClass(), "m_ITextComponent_getFormattedText");
	}

	public static Method getItemRegisterItemMethod() {
		return getMethod(getItemClass(), "m_Item_registerItem", int.class, String.class, getItemClass());
	}

	public static Method getItemGetItemByNameMethod() {
		return getMethod(getItemClass(), "m_Item_getItemByName", String.class);
	}

	public static Method getEntityPlayerGetHeldItemMethod() {
		return getMethod(getEntityLivingClass(), "m_EntityPlayer_getHeldItem", getEnumHandClass());
	}

	public static Method getEntityPlayerSetActiveHandMethod() {
		return getMethod(getEntityLivingClass(), "m_EntityPlayer_setActiveHand", getEnumHandClass());
	}

	public static Method getItemOnItemRightClickMethod() {
		return getMethod(getItemClass(), "m_Item_onItemRightClick", getWorldClientClass().getSuperclass(), getEntityPlayerClass(), getEnumHandClass());
	}

	public static Method getIForgeRegistryEntrySetRegistryName() {
		return getMethod(getIForgeRegistryEntryClass(), "m_IForgeRegistryEntry_setRegistryName", getResourceLocationClass());
	}

	public static Method getMinecraftPopulateSearchTreeManagerMethod() {
		return getMethod(getMinecraftClass(), "m_Minecraft_populateSearchTreeManager");
	}

	public static String getEntitySelectorsClassName() {
		return getHashMap().get("c_EntitySelectors");
	}

	public static String getToolMaterialClassName() {
		return getHashMap().get("c_ToolMaterial");
	}

	public static String getItemSwordGetEnumActionMethodName() {
		return getHashMap().get("m_ItemSword_getEnumAction");
	}

	public static String getItemSwordGetMaxItemUseDurationName() {
		return getHashMap().get("m_ItemSword_getMaxItemUseDuration");
	}

	public static String getEnumActionResultSuccessName() {
		return getHashMap().get("f_EnumActionResult_success");
	}

	public static String getItemAddPropertyOverrideMethodName() {
		return getHashMap().get("m_Item_addPropertyOverride");
	}

	public static String getItemPropertyGetterApplyMethodName() {
		return getHashMap().get("m_IItemPropertyGetter_apply");
	}

	public static String getRecipeListGetRecipesMethodName() {
		return getHashMap().get("m_RecipeList_getRecipes");
	}

	public static String getSearchTreeManagerTreesFieldName() {
		return getHashMap().get("f_SearchTreeManager_trees");
	}

	public static String getSearchTreeRecalculateMethodName() {
		return getHashMap().get("m_SearchTree_recalculate");
	}
}