package de.gerrygames.the5zig.clientviaversion.classnames;

import java.util.HashMap;
import java.util.Map;

public class ClassNames1_8Forge {
	static Map<String, String> CLASS_NAMES = new HashMap<>();

	static {
		CLASS_NAMES.put("c_Button", "net.minecraft.client.gui.GuiButton");
		CLASS_NAMES.put("c_GuiDisconnected", "net.minecraft.client.gui.GuiDisconnected");
		CLASS_NAMES.put("c_GuiMultiplayer", "net.minecraft.client.gui.GuiMultiplayer");
		CLASS_NAMES.put("c_Item", "net.minecraft.item.Item");
		CLASS_NAMES.put("c_NetHandlerLoginClient", "net.minecraft.client.network.NetHandlerLoginClient");
		CLASS_NAMES.put("c_NettyCompressionDecoder", "net.minecraft.network.NettyCompressionDecoder");
		CLASS_NAMES.put("c_NettyCompressionEncoder", "net.minecraft.network.NettyCompressionEncoder");

		CLASS_NAMES.put("f_Entity_entityId", "field_145783_c");
		CLASS_NAMES.put("f_EntityPlayer_itemStackMainHand", "field_184831_bT");
		CLASS_NAMES.put("f_GuiDisconnected_message", "field_146304_f");
		CLASS_NAMES.put("f_GuiDisconnected_textHeight", "field_175353_i");
		CLASS_NAMES.put("f_GuiScreen_buttons", "field_146292_n");
		CLASS_NAMES.put("f_GuiScreen_height", "field_146295_m");
		CLASS_NAMES.put("f_GuiScreen_width", "field_146294_l");
		CLASS_NAMES.put("f_ItemStack_item", "field_151002_e");
		CLASS_NAMES.put("f_Minecraft_displayHeight", "field_71440_d");
		CLASS_NAMES.put("f_Minecraft_displayWidth", "field_71443_c");
		CLASS_NAMES.put("f_NetworkManager_channel", "field_150746_k");
		CLASS_NAMES.put("f_NetworkManager_connectionState", "field_150739_c");
		CLASS_NAMES.put("f_NetworkManager_logger", "field_150735_g");

		CLASS_NAMES.put("m_Button_draw", "func_146112_a");
		CLASS_NAMES.put("m_Button_mouseClicked", "func_146116_c");
		CLASS_NAMES.put("m_Button_mouseReleased", "func_146119_b");
		CLASS_NAMES.put("m_Item_getIdFromItem", "func_150891_b");
		CLASS_NAMES.put("m_ITextComponent_getFormattedText", "func_150254_d");
		CLASS_NAMES.put("m_World_getEntityByUUID", "func_152378_a");
	}
}
