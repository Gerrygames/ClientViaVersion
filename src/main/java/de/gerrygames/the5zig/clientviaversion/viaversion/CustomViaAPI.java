package de.gerrygames.the5zig.clientviaversion.viaversion;

import eu.the5zig.mod.The5zigMod;
import io.netty.buffer.ByteBuf;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.ViaAPI;
import us.myles.ViaVersion.api.boss.BossBar;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossStyle;

import java.util.SortedSet;
import java.util.UUID;

public class CustomViaAPI implements ViaAPI {
	@Override
	public int getPlayerVersion(Object o) {
		return -1;
	}

	@Override
	public int getPlayerVersion(UUID uuid) {
		return uuid.equals(The5zigMod.getVars().getGameProfile().getId()) ? ClientViaVersion.CLIENT_PROTOCOL_VERSION : ClientViaVersion.spoofedVersion;
	}

	@Override
	public boolean isPorted(UUID uuid) {
		return Via.getManager().getPortedPlayers().containsKey(uuid);
	}

	@Override
	public String getVersion() {
		return ClientViaVersion.selected.getName();
	}

	@Override
	public void sendRawPacket(Object o, ByteBuf byteBuf) throws IllegalArgumentException {}

	@Override
	public void sendRawPacket(UUID uuid, ByteBuf byteBuf) throws IllegalArgumentException {
		if (uuid.equals(The5zigMod.getVars().getGameProfile().getId())) {
			ClientViaVersion.user.sendRawPacket(byteBuf);
		}
	}

	@Override
	public BossBar createBossBar(String title, BossColor bossColor, BossStyle bossStyle) {
		return new de.gerrygames.the5zig.clientviaversion.viaversion.BossBar(title, bossColor, bossStyle);
	}

	@Override
	public BossBar createBossBar(String title, float health, BossColor bossColor, BossStyle bossStyle) {
		return new de.gerrygames.the5zig.clientviaversion.viaversion.BossBar(title, health, bossColor, bossStyle);
	}

	@Override
	public SortedSet<Integer> getSupportedVersions() {
		return null;
	}
}
