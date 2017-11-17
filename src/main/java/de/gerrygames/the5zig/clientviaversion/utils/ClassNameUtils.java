package de.gerrygames.the5zig.clientviaversion.utils;

import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.The5zigMod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.UUID;

public class ClassNameUtils {
	private static HashMap<String, String> CLASSES_FIELDS_METHODS_CUR = null;
	private static HashMap<String, HashMap<String, String>> CLASSES_FIELDS_METHODS = new HashMap<>();
	static {
		//f_ClassName_FieldName
		//m_ClassName_MethodName
		//c_ClassName

		HashMap<String, String> mc1_8_9 = new HashMap<>();
		mc1_8_9.put("f_NetworkManager_Logger", "g");
		mc1_8_9.put("f_NetworkManager_Channel", "k");
		mc1_8_9.put("f_NetworkManager_ConnectionState", "c");
		mc1_8_9.put("c_NetHandlerLoginClient", "bcx");
		mc1_8_9.put("c_Item", "zw");
		mc1_8_9.put("f_ItemStack_Item", "d");
		mc1_8_9.put("c_GuiMultiplayer", "azh");
		mc1_8_9.put("c_GuiDisconnected", "axh");
		mc1_8_9.put("f_GuiDisconnected_Message", "f");
		mc1_8_9.put("f_GuiDisconnected_TextHeight", "i");
		mc1_8_9.put("m_ITextComponent_getFormattedText", "d");
		mc1_8_9.put("f_GuiScreen_Width", "l");
		mc1_8_9.put("f_GuiScreen_Height", "m");
		mc1_8_9.put("c_NettyCompressionDecoder", "ei");
		mc1_8_9.put("c_NettyCompressionEncoder", "ej");
		mc1_8_9.put("f_GuiScreen_buttons", "n");
		mc1_8_9.put("m_Item_getIdFromItem", "b");
		mc1_8_9.put("c_VersionButton", "VersionButton");
		mc1_8_9.put("c_VersionReconnectButton", "VersionReconnectButton");
		mc1_8_9.put("c_ShieldBlockingButton", "ShieldBlockingButton");
		mc1_8_9.put("m_World_getEntityByUUID", "b");
		mc1_8_9.put("f_EntityPlayer_ItemStackMainHand", "g");
		mc1_8_9.put("c_Button", "avs");
		mc1_8_9.put("f_Entity_EntityId", "c");
		mc1_8_9.put("f_Minecraft_displayWidth", "d");
		mc1_8_9.put("f_Minecraft_displayHeight", "e");
		CLASSES_FIELDS_METHODS.put("1.8.9", mc1_8_9);

		HashMap<String, String> mc1_12 = new HashMap<>();
		mc1_12.put("f_NetworkManager_Logger", "g");
		mc1_12.put("f_NetworkManager_Channel", "k");
		mc1_12.put("f_NetworkManager_ConnectionState", "c");
		mc1_12.put("c_NetHandlerLoginClient", "brw");
		mc1_12.put("c_Item", "ail");
		mc1_12.put("f_ItemStack_Item", "e");
		mc1_12.put("c_GuiMultiplayer", "bnd");
		mc1_12.put("c_GuiDisconnected", "bkw");
		mc1_12.put("f_GuiDisconnected_Message", "f");
		mc1_12.put("f_GuiDisconnected_TextHeight", "i");
		mc1_12.put("m_ITextComponent_getFormattedText", "d");
		mc1_12.put("f_GuiScreen_Width", "l");
		mc1_12.put("f_GuiScreen_Height", "m");
		mc1_12.put("c_NettyCompressionDecoder", "gu");
		mc1_12.put("c_NettyCompressionEncoder", "gv");
		mc1_12.put("m_GuiScreen_addButton", "b");
		mc1_12.put("m_Item_getIdFromItem", "a");
		mc1_12.put("c_VersionButton", "VersionButton");
		mc1_12.put("c_VersionReconnectButton", "VersionReconnectButton");
		mc1_12.put("c_ShieldBlockingButton", "ShieldBlockingButton");
		mc1_12.put("m_World_getEntityByUUID", "b");
		mc1_12.put("f_EntityPlayer_ItemStackMainHand", "bV");
		mc1_12.put("c_Button", "biy");
		mc1_12.put("f_Entity_EntityId", "h");
		mc1_12.put("c_EntitySelectors", "vi$7");
		mc1_12.put("c_ItemSword", "ajw");
		mc1_12.put("c_EnumAction", "aka");
		mc1_12.put("c_ToolMaterial", "a");
		mc1_12.put("m_ItemSword_GetEnumAction", "f");
		mc1_12.put("m_ItemSword_getMaxItemUseDuration", "e");
		mc1_12.put("m_Item_RegisterItem", "a");
		mc1_12.put("m_Item_GetItemByName", "b");
		mc1_12.put("f_ItemSword_ToolMaterial", "b");
		mc1_12.put("m_Item_setName", "c");
		mc1_12.put("c_EnumHand", "tz");
		mc1_12.put("c_ActionResult", "uc");
		mc1_12.put("c_EnumActionResult", "ub");
		mc1_12.put("m_EntityPlayer_getHeldItem", "b");
		mc1_12.put("m_EntityPlayer_setActiveHand", "c");
		mc1_12.put("m_Item_onItemRightClick", "a");
		mc1_12.put("f_EnumActionResult_Success", "a");
		mc1_12.put("c_IItemPropertyGetter", "aio");
		mc1_12.put("m_IItemPropertyGetter_apply", "a");
		mc1_12.put("m_Item_addPropertyOverride", "a");
		mc1_12.put("m_EntityLiving_isHandActive", "cG");
		mc1_12.put("m_EntityLiving_getActiveItemStack", "cJ");
		mc1_12.put("m_Minecraft_populateSearchTreeManager", "ar");
		mc1_12.put("c_RecipeBookClient", "cif");
		mc1_12.put("f_RecipeBookClient_ALL_RECIPES", "f");
		mc1_12.put("m_RecipeList_getRecipes", "d");
		mc1_12.put("c_ShapedRecipes", "aku");
		mc1_12.put("f_ShapedRecipes_recipeOutput", "d");
		mc1_12.put("f_Minecraft_searchTreeManager", "ae");
		mc1_12.put("f_SearchTreeManager_trees", "c");
		mc1_12.put("m_SearchTree_recalculate", "a");
		mc1_12.put("f_Minecraft_displayWidth", "d");
		mc1_12.put("f_Minecraft_displayHeight", "e");
		CLASSES_FIELDS_METHODS.put("1.12", mc1_12);

		HashMap<String, String> mc1_12_2 = new HashMap<>(mc1_12);
		mc1_12_2.put("c_Item", "ain");
		mc1_12_2.put("c_NetHandlerLoginClient", "bry");
		mc1_12_2.put("c_GuiMultiplayer", "bnf");
		mc1_12_2.put("c_GuiDisconnected", "bky");
		mc1_12_2.put("c_Button", "bja");
		mc1_12_2.put("c_EntitySelectors", "vk$7");
		mc1_12_2.put("c_ItemSword", "ajy");
		mc1_12_2.put("c_EnumAction", "akc");
		mc1_12_2.put("c_EnumHand", "ub");
		mc1_12_2.put("c_ActionResult", "ue");
		mc1_12_2.put("c_EnumActionResult", "ud");
		mc1_12_2.put("c_IItemPropertyGetter", "aiq");
		mc1_12_2.put("c_RecipeBookClient", "cih");
		mc1_12_2.put("c_ShapedRecipes", "akw");
		CLASSES_FIELDS_METHODS.put("1.12.2", mc1_12_2);
		
		HashMap<String, String> forge1_8_9 = new HashMap<>();
		forge1_8_9.put("f_NetworkManager_Logger", "field_150735_g");
		forge1_8_9.put("f_NetworkManager_Channel", "field_150746_k");
		forge1_8_9.put("f_NetworkManager_ConnectionState", "field_150739_c");
		forge1_8_9.put("c_NetHandlerLoginClient", "net.minecraft.client.network.NetHandlerLoginClient");
		forge1_8_9.put("c_Item", "net.minecraft.item.Item");
		forge1_8_9.put("f_ItemStack_Item", "field_151002_e");
		forge1_8_9.put("c_GuiMultiplayer", "net.minecraft.client.gui.GuiMultiplayer");
		forge1_8_9.put("c_GuiDisconnected", "net.minecraft.client.gui.GuiDisconnected");
		forge1_8_9.put("f_GuiDisconnected_Message", "field_146304_f");
		forge1_8_9.put("f_GuiDisconnected_TextHeight", "field_175353_i");
		forge1_8_9.put("m_ITextComponent_getFormattedText", "func_150254_d");
		forge1_8_9.put("f_GuiScreen_Width", "field_146294_l");
		forge1_8_9.put("f_GuiScreen_Height", "field_146295_m");
		forge1_8_9.put("c_NettyCompressionEncoder", "net.minecraft.network.NettyCompressionEncoder");
		forge1_8_9.put("c_NettyCompressionDecoder", "net.minecraft.network.NettyCompressionDecoder");
		forge1_8_9.put("f_GuiScreen_buttons", "field_146292_n");
		forge1_8_9.put("m_Item_getIdFromItem", "func_150891_b");
		forge1_8_9.put("c_VersionButton", "VersionButtonForge");
		forge1_8_9.put("c_VersionReconnectButton", "VersionReconnectButtonForge");
		forge1_8_9.put("c_ShieldBlockingButton", "ShieldBlockingButtonForge");
		forge1_8_9.put("m_World_getEntityByUUID", "func_152378_a");
		forge1_8_9.put("f_EntityPlayer_ItemStackMainHand", "field_184831_bT");
		forge1_8_9.put("c_Button", "net.minecraft.client.gui.GuiButton");
		forge1_8_9.put("f_Entity_EntityId", "field_145783_c");
		CLASSES_FIELDS_METHODS.put("forge1.8.9", forge1_8_9);
		
		HashMap<String, String> forge1_12  = new HashMap<>();
		forge1_12.put("f_NetworkManager_Logger", "field_150735_g");
		forge1_12.put("f_NetworkManager_Channel", "field_150746_k");
		forge1_12.put("f_NetworkManager_ConnectionState", "field_150739_c");
		forge1_12.put("c_NetHandlerLoginClient", "net.minecraft.client.network.NetHandlerLoginClient");
		forge1_12.put("c_Item", "net.minecraft.item.Item");
		forge1_12.put("f_ItemStack_Item", "field_151002_e");
		forge1_12.put("c_GuiMultiplayer", "net.minecraft.client.gui.GuiMultiplayer");
		forge1_12.put("c_GuiDisconnected", "net.minecraft.client.gui.GuiDisconnected");
		forge1_12.put("f_GuiDisconnected_Message", "field_146304_f");
		forge1_12.put("f_GuiDisconnected_TextHeight", "field_175353_i");
		forge1_12.put("m_ITextComponent_getFormattedText", "func_150254_d");
		forge1_12.put("f_GuiScreen_Width", "field_146294_l");
		forge1_12.put("f_GuiScreen_Height", "field_146295_m");
		forge1_12.put("c_NettyCompressionEncoder", "net.minecraft.network.NettyCompressionEncoder");
		forge1_12.put("c_NettyCompressionDecoder", "net.minecraft.network.NettyCompressionDecoder");
		forge1_12.put("m_GuiScreen_addButton", "func_189646_b");
		forge1_12.put("m_Item_getIdFromItem", "func_150891_b");
		forge1_12.put("c_VersionButton", "VersionButtonForge");
		forge1_12.put("c_VersionReconnectButton", "VersionReconnectButtonForge");
		forge1_12.put("c_ShieldBlockingButton", "ShieldBlockingButtonForge");
		forge1_12.put("m_World_getEntityByUUID", "func_152378_a");
		forge1_12.put("f_EntityPlayer_ItemStackMainHand", "field_184831_bT");
		forge1_12.put("c_Button", "net.minecraft.client.gui.GuiButton");
		forge1_12.put("f_Entity_EntityId", "field_145783_c");
		forge1_12.put("c_EntitySelectors", mc1_12.get("c_EntitySelectors"));
		forge1_12.put("c_ItemSword", "net.minecraft.item.ItemSword");
		forge1_12.put("c_EnumAction", "net.minecraft.item.EnumAction");
		forge1_12.put("c_ToolMaterial", "ToolMaterial");
		forge1_12.put("m_ItemSword_GetEnumAction", "func_77661_b");
		forge1_12.put("m_ItemSword_getMaxItemUseDuration", "func_77626_a");
		forge1_12.put("m_Item_RegisterItem", "func_179217_a");
		forge1_12.put("m_Item_GetItemByName", "func_111206_d");
		forge1_12.put("f_ItemSword_ToolMaterial", "field_150933_b");
		forge1_12.put("m_Item_setName", "func_77655_b");
		forge1_12.put("c_EnumHand", "net.minecraft.util.EnumHand");
		forge1_12.put("c_ActionResult", "net.minecraft.util.ActionResult");
		forge1_12.put("c_EnumActionResult", "net.minecraft.util.EnumActionResult");
		forge1_12.put("m_EntityPlayer_getHeldItem", "func_184586_b");
		forge1_12.put("m_EntityPlayer_setActiveHand", "func_184598_c");
		forge1_12.put("m_Item_onItemRightClick", "func_77659_a");
		forge1_12.put("f_EnumActionResult_Success", "SUCCESS");
		forge1_12.put("c_IItemPropertyGetter", "net.minecraft.item.IItemPropertyGetter");
		forge1_12.put("m_IItemPropertyGetter_apply", "func_185085_a");
		forge1_12.put("m_Item_addPropertyOverride", "func_185043_a");
		forge1_12.put("f_Item_REGISTRY", "field_150901_e");
		forge1_12.put("m_EntityLiving_isHandActive", "func_184587_cr");
		forge1_12.put("m_EntityLiving_getActiveItemStack", "func_184607_cu");

		forge1_12.put("c_IForgeRegistryEntry", "net.minecraftforge.registries.IForgeRegistryEntry");
		forge1_12.put("m_IForgeRegistryEntry_setRegistryName", "setRegistryName");
		CLASSES_FIELDS_METHODS.put("forge1.12", forge1_12);

		HashMap<String, String> forge1_12_2 = new HashMap<>(forge1_12);
		forge1_12_2.put("c_EntitySelectors", mc1_12_2.get("c_EntitySelectors"));
		CLASSES_FIELDS_METHODS.put("forge1.12.2", forge1_12_2);
	}

	public static HashMap<String, String> getHashMap() {
		if (CLASSES_FIELDS_METHODS_CUR!=null) return CLASSES_FIELDS_METHODS_CUR;
		return CLASSES_FIELDS_METHODS_CUR = CLASSES_FIELDS_METHODS.get((The5zigAPI.getAPI().isForgeEnvironment() ? "forge" : "") + The5zigAPI.getAPI().getMinecraftVersion());
	}

	public static Class getClass(String key) {
		String classname = getHashMap().get(key);
		try {
			return Class.forName(classname);
		} catch (ClassNotFoundException ex) {
			The5zigMod.logger.error("Could not find class " + classname + " for key: " + key);
		}
		return null;
	}

	public static Field getField(Class clazz, String key) {
		String fieldname = getHashMap().get(key);
		try {
			Field field = clazz.getDeclaredField(fieldname);
			field.setAccessible(true);
			return field;
		} catch (NoSuchFieldException ex) {
			The5zigMod.logger.error("Could not find field " + fieldname + " for key: " + key + " in class " + clazz.getName());
		}
		return null;
	}

	public static Method getMethod(Class clazz, String key, Class... args) {
		String methodname = getHashMap().get(key);
		try {
			return clazz.getDeclaredMethod(methodname, args);
		} catch (NoSuchMethodException ex) {
			The5zigMod.logger.error("Could not find method " + methodname + " for key: " + key + " in class " + clazz.getName());
		}
		return null;
	}

	public static Class getMethodReturnType(String clazz, String method, Class... args) {
		try {
			return Class.forName(clazz).getDeclaredMethod(method, args).getReturnType();
		} catch (ClassNotFoundException ex) {
			The5zigMod.logger.error("Could not find class " + clazz);
		} catch (NoSuchMethodException ex) {
			The5zigMod.logger.error("Could not find method " + method + " in class " + clazz);
		}
		return null;
	}

	public static Class getFieldType(String clazz, String field) {
		try {
			return Class.forName(clazz).getDeclaredField(field).getType();
		} catch (ClassNotFoundException ex) {
			The5zigMod.logger.error("Could not find class " + clazz);
		} catch (NoSuchFieldException ex) {
			The5zigMod.logger.error("Could not find field " + field + " in class " + clazz);
		}
		return null;
	}

	public static Class getSuperClassType(String clazz) {
		try {
			return Class.forName(clazz).getSuperclass();
		} catch (ClassNotFoundException ex) {
			The5zigMod.logger.error("Could not find class " + clazz);
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

	public static Class getShieldBlockingButtonClass() {
		return getClass("c_ShieldBlockingButton");
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
		return getMethodReturnType("Variables", "getSpectatingEntity");
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

	public static Class getEntitySelectorsClass() {
		return getClass("c_EntitySelectors");
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
		return getField(getNetworkManagerClass(), "f_NetworkManager_Logger");
	}

	public static Field getRecipeBookClientAllRecipesField() {
		return getField(getRecipeBookClientClass(), "f_RecipeBookClient_ALL_RECIPES");
	}

	public static Field getMinecraftSearchTreeManagerField() {
		return getField(getMinecraftClass(), "f_Minecraft_searchTreeManager");
	}

	public static Field getEntityEntityIdField() {
		return getField(getEntityClass(), "f_Entity_EntityId");
	}

	public static Field getGuiScreenWidthField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_Width");
	}

	public static Field getGuiScreenHeightField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_Height");
	}

	public static Field getGuiDisconnectedTextHeightField() {
		return getField(getGuiDisconnectedClass(), "f_GuiDisconnected_TextHeight");
	}

	public static Field getItemRegistryField() {
		return getField(getItemClass(), "f_Item_REGISTRY");
	}

	public static Field getGuiDisconnectedMessageField() {
		return getField(getGuiDisconnectedClass(), "f_GuiDisconnected_Message");
	}

	public static Field getGuiScreenButtonsField() {
		return getField(getGuiScreenClass(), "f_GuiScreen_buttons");
	}

	public static Field getNetworkManagerConnectionStateField() {
		return getField(getNetworkManagerClass(), "f_NetworkManager_ConnectionState");
	}

	public static Field getShapedRecipesRecipeOutputField() {
		return getField(getShapedRecipesClass(), "f_ShapedRecipes_recipeOutput");
	}

	public static Field getNetworkManagerChannelField() {
		return getField(getNetworkManagerClass(), "f_NetworkManager_Channel");
	}

	public static Field getItemSwordToolMaterialField() {
		return getField(getItemSwordClass(), "f_ItemSword_ToolMaterial");
	}

	public static Field getItemStackItemField() {
		return getField(getItemStackClass(), "f_ItemStack_Item");
	}

	public static Field getEntityPlayerItemStackMainHandField() {
		return getField(getEntityPlayerClass(), "f_EntityPlayer_ItemStackMainHand");
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
		return getMethod(getItemClass(), "m_Item_RegisterItem", int.class, String.class, getItemClass());
	}

	public static Method getItemGetItemByNameMethod() {
		return getMethod(getItemClass(), "m_Item_GetItemByName", String.class);
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

	public static String getButtonClassName() {
		return getHashMap().get("c_Button");
	}

	public static String getEntitySelectorsClassName() {
		return getHashMap().get("c_EntitySelectors");
	}

	public static String getToolMaterialClassName() {
		return getHashMap().get("c_ToolMaterial");
	}

	public static String getItemSwordGetEnumActionMethodName() {
		return getHashMap().get("m_ItemSword_GetEnumAction");
	}

	public static String getItemSwordGetMaxItemUseDurationName() {
		return getHashMap().get("m_ItemSword_getMaxItemUseDuration");
	}

	public static String getEnumActionResultSuccessName() {
		return getHashMap().get("f_EnumActionResult_Success");
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