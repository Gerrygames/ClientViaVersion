package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_9to1_8;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import eu.the5zig.mod.The5zigAPI;
import eu.the5zig.mod.gui.ingame.ArmorSlot;
import eu.the5zig.mod.gui.ingame.ItemStack;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ArmorType;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9TO1_8;

import java.util.UUID;

public class AttributeManager {
	private static final UUID ARMOR_ATTRIBUTE = UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150");

	public static void sendAttackSpeed() {
		PacketWrapper packet = new PacketWrapper(0x4B, null, ClientViaVersion.user);
		packet.write(Type.VAR_INT, Utils.getPlayerEntityId());
		packet.write(Type.INT, 1);
		packet.write(Type.STRING, "generic.attackSpeed");
		packet.write(Type.DOUBLE, 1024.0);
		packet.write(Type.VAR_INT, 0);
		try {
			packet.send(Protocol1_9TO1_8.class);
		} catch (Exception ex) {ex.printStackTrace();}
	}

	public static void sendArmorUpdate() {
		double armor = 0;
		for (ArmorSlot slot : ArmorSlot.values()) {
			ItemStack item = The5zigAPI.getAPI().getItemInArmorSlot(slot);
			if (item!=null) armor += ArmorType.findById(Utils.FiveZigItemStackToItem(item).getId()).getArmorPoints();
		}

		PacketWrapper packet = new PacketWrapper(0x4B, null, ClientViaVersion.user);
		packet.write(Type.VAR_INT, Utils.getPlayerEntityId());
		packet.write(Type.INT, 1);
		packet.write(Type.STRING, "generic.armor");
		packet.write(Type.DOUBLE, 0.0);
		packet.write(Type.VAR_INT, 1);
		packet.write(Type.UUID, ARMOR_ATTRIBUTE);
		packet.write(Type.DOUBLE, armor);
		packet.write(Type.BYTE, (byte)0);
		try {
			packet.send(Protocol1_9TO1_8.class);
		} catch (Exception ex) {ex.printStackTrace();}

	}
}
