package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_9to1_8.providers;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import eu.the5zig.mod.The5zigMod;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.HandItemProvider;

public class ClientHandItemProvider extends HandItemProvider {
	@Override
	public Item getHandItem(UserConnection info) {
		if (ClientViaVersion.user.get(ProtocolInfo.class).getUuid().equals(The5zigMod.getVars().getGameProfile().getId())) {
			return Utils.FiveZigItemStackToItem(The5zigMod.getVars().getItemInMainHand());
		} else {
			try {
				Object world = Class.forName("Variables").getDeclaredMethod("getWorld").invoke(The5zigMod.getVars());
				Object player = ClassNameUtils.getWorldGetPlayerEntityByUUIDMethod().invoke(world);
				if (player==null) return new Item((short)0, (byte)0, (short)0, null);
				Object nmsitemstack = ClassNameUtils.getEntityPlayerItemStackMainHandField().get(player);
				return Utils.nmsItemStackToItem(nmsitemstack);
			} catch (Exception ex) {ex.printStackTrace();}
		}
		return new Item((short)0, (byte)0, (short)0, null);
	}
}
