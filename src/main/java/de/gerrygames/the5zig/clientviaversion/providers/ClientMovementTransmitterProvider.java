package de.gerrygames.the5zig.clientviaversion.providers;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.netty.ViaTransformerOut;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9TO1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.MovementTracker;

public class ClientMovementTransmitterProvider extends MovementTransmitterProvider {
	static {
	}

	@Override
	public PacketWrapper getFlyingPacket() {
		return getFlyingPacket(ClientViaVersion.user);
	}

	private PacketWrapper getFlyingPacket(UserConnection userConnection) {
		PacketWrapper flyingPacket = new PacketWrapper(0x03, null, ClientViaVersion.user);
		flyingPacket.write(Type.BOOLEAN, false);
		return flyingPacket;
	}

	@Override
	public PacketWrapper getGroundPacket() {
		return getGroundPacket(ClientViaVersion.user);
	}

	private PacketWrapper getGroundPacket(UserConnection userConnection) {
		PacketWrapper groundPacket = new PacketWrapper(0x03, null, ClientViaVersion.user);
		groundPacket.write(Type.BOOLEAN, true);
		return groundPacket;
	}

	@Override
	public void sendPlayer(UserConnection userConnection) {
		if (userConnection.get(ProtocolInfo.class).getState()!=State.PLAY) return;
		try {
			if (userConnection.get(MovementTracker.class).isGround()) {
				ViaTransformerOut.sendToServer(this.getGroundPacket(userConnection), Protocol1_9TO1_8.class, true, false);
			} else {
				ViaTransformerOut.sendToServer(this.getFlyingPacket(userConnection), Protocol1_9TO1_8.class, true, false);
			}

			userConnection.get(MovementTracker.class).incrementIdlePacket();
		} catch (Exception ex) {ex.printStackTrace();}
	}
}
