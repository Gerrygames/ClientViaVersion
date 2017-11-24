package de.gerrygames.the5zig.clientviaversion.classnames;

import java.util.HashMap;
import java.util.Map;

public class ClassNames1_7_10Forge {
	static Map<String, String> CLASS_NAMES = new HashMap<>();

	static {
		CLASS_NAMES.put("c_Entity", "net.minecraft.entity.Entity");
		CLASS_NAMES.put("c_GuiDisconnected", "net.minecraft.client.gui.GuiDisconnected");
		CLASS_NAMES.put("c_GuiMultiplayer", "net.minecraft.client.gui.GuiMultiplayer");
		CLASS_NAMES.put("c_Item", "net.minecraft.item.Item");
		CLASS_NAMES.put("c_NetHandlerLoginClient", "net.minecraft.client.network.NetHandlerLoginClient");
		CLASS_NAMES.put("c_NettyCompressionDecoder", "de.gerrygames.the5zig.clientviaversion.netty.CompressionDecoder");
		CLASS_NAMES.put("c_NettyCompressionEncoder", "de.gerrygames.the5zig.clientviaversion.netty.CompressionEncoder");
		CLASS_NAMES.put("c_VersionButton", "VersionButtonForge");
		CLASS_NAMES.put("c_VersionReconnectButton", "VersionReconnectButtonForge");

		CLASS_NAMES.put("f_Entity_entityId", "field_145783_c");
		CLASS_NAMES.put("f_EntityPlayer_itemStackMainHand", "field_71074_e");
		CLASS_NAMES.put("f_GuiDisconnected_message", "field_146304_f");
		CLASS_NAMES.put("f_GuiScreen_buttons", "field_146292_n");
		CLASS_NAMES.put("f_GuiScreen_height", "field_146295_m");
		CLASS_NAMES.put("f_GuiScreen_width", "field_146294_l");
		CLASS_NAMES.put("f_ItemStack_item", "field_151002_e");
		CLASS_NAMES.put("f_NetworkManager_channel", "field_150746_k");
		CLASS_NAMES.put("f_NetworkManager_connectionState", "field_150739_c");
		CLASS_NAMES.put("f_NetworkManager_logger", "field_150735_g");

		CLASS_NAMES.put("m_Item_getIdFromItem", "func_150891_b");
		CLASS_NAMES.put("m_ITextComponent_getFormattedText", "func_150254_d");
		CLASS_NAMES.put("m_World_getEntityByUUID", "func_152378_a");
	}
}