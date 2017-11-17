package de.gerrygames.the5zig.clientviaversion.utils;

import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.gui.ingame.ItemStack;
import eu.the5zig.mod.manager.AutoReconnectManager;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.packets.State;

import java.lang.reflect.Field;

public class Utils {
	public static State currentState = null;

	public static Item FiveZigItemStackToItem(ItemStack itemStack) {
		if (itemStack==null) return airItem();
		try {
			//noinspection JavaReflectionMemberAccess
			Field field = itemStack.getClass().getDeclaredField("item");
			field.setAccessible(true);
			return (nmsItemStackToItem(field.get(itemStack)));
		} catch (Exception ex) {ex.printStackTrace();}
		return airItem();
	}

	public static Item nmsItemStackToItem(Object itemStack) {
		if (itemStack==null) return airItem();
		try {
			int id = (int) ClassNameUtils.getItemGetIdFromItemMethod().invoke(null, ClassNameUtils.getItemStackItemField().get(itemStack));
			return new Item((short)id, (byte)1, (short)0, null);  //TODO
		} catch (Exception ex) {ex.printStackTrace();}
		return airItem();
	}

	private static Item airItem() {
		return new Item((short)0, (byte)0, (short)0, null);
	}

	public static State getConnectionState(Channel channel) {
		try {
			Enum state = (Enum) channel.attr((AttributeKey) ClassNameUtils.getNetworkManagerConnectionStateField().get(null)).get();

			State viaState = State.PLAY;
			switch (state.ordinal()) {
				case 0: {
					viaState = State.HANDSHAKE;
					break;
				}
				case 3: {
					viaState = State.LOGIN;
					break;
				}
				case 2: {
					return State.STATUS;
				}
				case 1: {
					viaState = State.PLAY;
					break;
				}
			}
			if (viaState!=currentState) {
				ClientViaVersion.switchState(currentState, viaState);
				currentState = viaState;
			}
			return viaState;
		} catch (Exception ex) {ex.printStackTrace();}
		return State.PLAY;
	}

	public static Object getLastServerData() {
		try {
			Field f = AutoReconnectManager.class.getDeclaredField("lastServerData");
			f.setAccessible(true);
			return f.get(The5zigMod.getDataManager().getAutoReconnectManager());
		} catch (Exception ex) {ex.printStackTrace();}
		return null;
	}

	public static int getPlayerEntityId() {
		try {
			Object player = Class.forName("Variables").getMethod("getPlayer").invoke(The5zigMod.getVars());
			return (int) ClassNameUtils.getEntityEntityIdField().get(player);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}
}
