package de.gerrygames.the5zig.clientviaversion.viaversion;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.boss.BossColor;
import us.myles.ViaVersion.api.boss.BossFlag;
import us.myles.ViaVersion.api.boss.BossStyle;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9TO1_8;

import java.util.*;

public class BossBar extends us.myles.ViaVersion.api.boss.BossBar {
	private String title;
	private float health;
	private BossColor bossColor;
	private BossStyle bossStyle;
	private List<BossFlag> flags = new ArrayList<>();
	private Set<UUID> players = new HashSet<>();
	private UUID uuid = UUID.randomUUID();

	private boolean visible = false;

	public BossBar(String title, BossColor bossColor, BossStyle bossStyle, BossFlag... flags) {
		this(title, 1.0f, bossColor, bossStyle, flags);
	}

	public BossBar(String title, float health, BossColor bossColor, BossStyle bossStyle, BossFlag... flags) {
		this.title = title;
		this.health = health;
		this.bossColor = bossColor;
		this.bossStyle = bossStyle;
		for (BossFlag flag : flags) this.addFlag(flag);
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar setTitle(String title) {
		this.title = title;
		if (this.visible && this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 3);
			wrapper.write(Type.STRING, "{\"text\": \"" + this.title + "\"}");
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace(); }
		}
		return this;
	}

	@Override
	public float getHealth() {
		return this.health;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar setHealth(float health) {
		this.health = health;
		if (this.visible && this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 2);
			wrapper.write(Type.FLOAT, this.health);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace(); }
		}
		return this;
	}

	@Override
	public BossColor getColor() {
		return this.bossColor;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar setColor(BossColor bossColor) {
		this.bossColor = bossColor;
		if (this.visible && this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 4);
			wrapper.write(Type.VAR_INT, this.bossColor.getId());
			wrapper.write(Type.VAR_INT, this.bossStyle.getId());
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace(); }
		}
		return this;
	}

	@Override
	public BossStyle getStyle() {
		return this.bossStyle;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar setStyle(BossStyle bossStyle) {
		this.bossStyle = bossStyle;
		if (this.visible && this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 4);
			wrapper.write(Type.VAR_INT, this.bossColor.getId());
			wrapper.write(Type.VAR_INT, this.bossStyle.getId());
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
		return this;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar addPlayer(UUID uuid) {
		this.players.add(uuid);
		if (visible && uuid.equals(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 0);
			wrapper.write(Type.STRING, "{\"text\": \"" + this.title + "\"}");
			wrapper.write(Type.FLOAT, this.health);
			wrapper.write(Type.VAR_INT, this.bossColor.getId());
			wrapper.write(Type.VAR_INT, this.bossStyle.getId());
			byte flags = 0;
			for (BossFlag flag : this.flags) flags |= flag.getId();
			wrapper.write(Type.BYTE, flags);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
		return this;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar removePlayer(UUID uuid) {
		this.players.remove(uuid);
		if (visible && uuid.equals(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 1);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
		return this;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar addFlag(BossFlag bossFlag) {
		if (this.flags.contains(bossFlag)) flags.add(bossFlag);
		updateFlags();
		return this;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar removeFlag(BossFlag bossFlag) {
		this.flags.remove(bossFlag);
		updateFlags();
		return this;
	}

	private void updateFlags() {
		if (this.visible && this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 5);
			byte flags = 0;
			for (BossFlag flag : this.flags) flags |= flag.getId();
			wrapper.write(Type.BYTE, flags);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
	}

	@Override
	public boolean hasFlag(BossFlag bossFlag) {
		return this.flags.contains(bossFlag);
	}

	@Override
	public Set<UUID> getPlayers() {
		return this.players;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar show() {
		if (visible) return this;
		this.visible = true;
		if (this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 0);
			wrapper.write(Type.STRING, "{\"text\": \"" + this.title + "\"}");
			wrapper.write(Type.FLOAT, this.health);
			wrapper.write(Type.VAR_INT, this.bossColor.getId());
			wrapper.write(Type.VAR_INT, this.bossStyle.getId());
			byte flags = 0;
			for (BossFlag flag : this.flags) flags |= flag.getId();
			wrapper.write(Type.BYTE, flags);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
		return this;
	}

	@Override
	public us.myles.ViaVersion.api.boss.BossBar hide() {
		if (!visible) return this;
		this.visible = false;
		if (this.players.contains(ClientViaVersion.user.get(ProtocolInfo.class).getUuid())) {
			PacketWrapper wrapper = new PacketWrapper(0x0C, null, ClientViaVersion.user);
			wrapper.write(Type.UUID, this.uuid);
			wrapper.write(Type.VAR_INT, 1);
			try {
				wrapper.send(Protocol1_9TO1_8.class);
			} catch (Exception ex) { ex.printStackTrace();}
		}
		return this;
	}

	@Override
	public boolean isVisible() {
		return this.visible;
	}

	@Override
	public UUID getId() {
		return this.uuid;
	}
}
