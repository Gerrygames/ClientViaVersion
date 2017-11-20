package de.gerrygames.the5zig.clientviaversion.gui;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import eu.the5zig.mod.The5zigMod;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuiPatcher {
	public static Object prevScreen;
	private static Method addButton;
	private static Field buttons;
	private static boolean liquidBouncePresent = false;
	static {
		if (ClientViaVersion.CLIENT_PROTOCOL_VERSION > 47) {
			addButton = ClassNameUtils.getGuiScreenAddButtonMethod();
			addButton.setAccessible(true);
		} else {
			buttons = ClassNameUtils.getGuiScreenButtonsField();
		}
		try {
			Class.forName("net.ccbluex.LiquidBounce.injection.MixinLoader");
			The5zigMod.logger.info("[ClientViaVersion] Found LiquidBounce. Cheaters are bad!");
			liquidBouncePresent = true;
		} catch (ClassNotFoundException ignored) {}
	}

	private static void addButton(Object gui, Object button) throws Exception {
		if (addButton!=null) {
			addButton.invoke(gui, button);
		} else if (buttons!=null) {
			((List)buttons.get(gui)).add(button);
		}
	}

	public static void tick() {
		try {
			Object gui = Class.forName("Variables").getDeclaredMethod("getMinecraftScreen").invoke(The5zigMod.getVars());
			if (gui==null || prevScreen==gui) return;
			prevScreen = gui;
			ButtonManager.buttons.clear();
			if (ClassNameUtils.getGuiMultiplayerClass().isInstance(gui)) {
				GuiPatcher.patchGuiMultiplayer(gui);
			} else if (ClassNameUtils.getGuiDisconnectedClass().isInstance(gui)) {
				GuiPatcher.patchGuiDisconnect(gui);
			}
		} catch (Exception ex) {
			The5zigMod.logger.error("[ClientViaVersion] Could not add buttons to gui.");
			ex.printStackTrace();
		}
	}

	public static void patchGuiMultiplayer(Object gui) throws Exception {
		addButton(gui, ClassNameUtils.getVersionButtonClass().getConstructor(int.class, int.class, int.class, int.class, int.class, String.class).newInstance(420, liquidBouncePresent ? 206 : 8, liquidBouncePresent ? 8 : 6, 60, 20, ClientViaVersion.selected.getName()));
	}

	private static HashMap<String, Integer> versions = new HashMap<>();
	static {
		versions.put("1.12.2", 340);
		versions.put("1.12.1", 338);
		versions.put("1.12", 335);
		versions.put("1.11.2", 316);
		versions.put("1.11.1", 316);
		versions.put("1.11", 315);
		versions.put("1.10.x", 210);
		versions.put("1.9.4", 110);
		versions.put("1.9.3", 110);
		versions.put("1.9.2", 109);
		versions.put("1.9.1", 108);
		versions.put("1.9", 107);
		versions.put("1.8.x", 47);
		versions.put("1.7.10", 5);
		versions.put("1.7.9", 5);
		versions.put("1.7.8", 5);
		versions.put("1.7.7", 5);
		versions.put("1.7.6", 5);
		versions.put("1.7.5", 4);
		versions.put("1.7.4", 4);
		versions.put("1.7.3", 4);
		versions.put("1.7.2", 4);
		versions.put("1.7.1", 4);
		versions.put("1.7", 4);
	}

	public static void patchGuiDisconnect(Object gui) throws Exception {
		Object messageComponent = ClassNameUtils.getGuiDisconnectedMessageField().get(gui);
		String message = (String) ClassNameUtils.getITextComponentGetFormattedTextMethod().invoke(messageComponent);
		message = message.toLowerCase();
		ProtocolVersion protocolVersion = null;
		for (Map.Entry<String, Integer> version : versions.entrySet()) {
			if (!contains(message, version.getKey())) continue;
			int id = version.getValue();
			if (!ProtocolVersion.isRegistered(id)) continue;
			ProtocolVersion pv = ProtocolVersion.getProtocol(id);
			if (id==ClientViaVersion.CLIENT_PROTOCOL_VERSION) {
				protocolVersion = pv;
				break;
			}
			if (protocolVersion!=null && isBetween(ClientViaVersion.CLIENT_PROTOCOL_VERSION, protocolVersion.getId(), id)) {
				protocolVersion = ProtocolVersion.getProtocol(ClientViaVersion.CLIENT_PROTOCOL_VERSION);
				break;
			}
			if (!ClientViaVersion.supportedVersion.contains(pv) || (protocolVersion!=null && Math.abs(protocolVersion.getId()-ClientViaVersion.CLIENT_PROTOCOL_VERSION)<Math.abs(pv.getId()-ClientViaVersion.CLIENT_PROTOCOL_VERSION)) || pv==ClientViaVersion.selected) continue;
			protocolVersion = pv;
		}
		if (protocolVersion==null) return;
		int width = (int) ClassNameUtils.getGuiScreenWidthField().get(gui);
		int height = (int) ClassNameUtils.getGuiScreenHeightField().get(gui);
		int textHeight = (int) ClassNameUtils.getGuiDisconnectedTextHeightField().get(gui);
		addButton(gui, ClassNameUtils.getVersionReconnectButtonClass().getConstructor(int.class, int.class, int.class, int.class, int.class, String.class, ProtocolVersion.class).newInstance(420, width / 2 - 100, Math.min(height / 2 + textHeight / 2 + 9, height - 30) + (liquidBouncePresent ? 85 : 25), 200, 20, "Reconnect with " + protocolVersion.getName() + " Protocol", protocolVersion));
	}

	private static boolean contains(String message, String version) {
		message = message.toLowerCase().replace("*", "x");
		if (version.split("\\.").length==2) {
			return message.contains(version) && (message.contains(version + ".x") || message.charAt(message.indexOf(version) + version.length())!='.');
		} else {
			if (message.contains(version)) return true;
			if (version.endsWith(".x")) {
				return message.contains(version.replace(".x", ""));
			} else {
				version = version.substring(0, version.length()-1);
				return message.contains(version + "x");
			}
		}
	}

	private static boolean isBetween(int x, int a, int b) {
		return (a<=x && b>=x) || (a>=x && b<=x);
	}
}