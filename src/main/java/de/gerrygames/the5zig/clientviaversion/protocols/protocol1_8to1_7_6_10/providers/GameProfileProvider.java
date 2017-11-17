package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.providers;

import com.mojang.authlib.GameProfile;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.platform.providers.Provider;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

import java.util.UUID;

public abstract class GameProfileProvider implements Provider {

	public GameProfile getGameProfile(UserConnection user) {
		return getGameProfile(user.get(ProtocolInfo.class).getUuid());
	}

	abstract public GameProfile getGameProfile(UUID uuid);

	abstract public GameProfile getGameProfile(String name);

}