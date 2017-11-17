package de.gerrygames.the5zig.clientviaversion.providers;

import com.mojang.authlib.GameProfile;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.providers.GameProfileProvider;
import eu.the5zig.mod.The5zigMod;

import java.util.UUID;

public class ClientGameProfileProvider extends GameProfileProvider {
	@Override
	public GameProfile getGameProfile(UUID uuid) {
		GameProfile gameProfile = The5zigMod.getVars().getGameProfile();
		return gameProfile.getId().equals(uuid) ? gameProfile : null;
	}

	@Override
	public GameProfile getGameProfile(String name) {
		GameProfile gameProfile = The5zigMod.getVars().getGameProfile();
		return gameProfile.getName().equals(name) ? gameProfile : null;
	}
}
