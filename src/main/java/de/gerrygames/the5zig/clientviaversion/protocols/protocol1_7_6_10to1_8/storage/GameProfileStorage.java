package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.storage;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameProfileStorage extends StoredObject {
	private Map<UUID, GameProfile> properties = new HashMap<>();

	public GameProfileStorage(UserConnection user) {
		super(user);
	}

	public GameProfile put(UUID uuid, String name) {
		GameProfile gameProfile = new GameProfile(uuid, name);
		properties.put(uuid, gameProfile);
		return gameProfile;
	}

	public void putProperty(UUID uuid, Property property) {
		properties.computeIfAbsent(uuid, profile -> new GameProfile(uuid, null)).properties.add(property);
	}

	public void putProperty(UUID uuid, String name, String value, String signature) {
		putProperty(uuid, new Property(name, value, signature));
	}

	public GameProfile get(UUID uuid) {
		return properties.get(uuid);
	}


	public static class GameProfile {
		public String name;
		public String displayName;
		public int ping;
		public UUID uuid;
		public List<Property> properties = new ArrayList<>();

		public GameProfile(UUID uuid, String name) {
			this.name = name;
			this.uuid = uuid;
		}

		public String getDisplayName() {
			String displayName = this.displayName==null ? name : this.displayName;
			if (displayName.length()>16) displayName = displayName.substring(0, 16);
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = fixDisplayName(displayName);
		}

		public static String fixDisplayName(String displayName) {
			if (displayName!=null && displayName.startsWith("{")) {
				displayName = TextComponent.toLegacyText(ComponentSerializer.parse(displayName));
			}
			return displayName;
		}
	}

	public static class Property {
		public String name;
		public String value;
		public String signature;

		public Property(String name, String value, String signature) {
			this.name = name;
			this.value = value;
			this.signature = signature;
		}
	}
}
