package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.storage;

import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.Protocol1_8TO1_9;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.type.Type;

public class Levitation extends StoredObject {
	private int amplifier;
	private boolean active = false;

	public Levitation(UserConnection user) {
		super(user);
		Via.getPlatform().runRepeatingSync(new Runnable() {
			@Override
			public void run() {
				if (!active) {
					return;
				}
				int vY = (amplifier+1) * 360;
				PacketWrapper packet = new PacketWrapper(0x12, null, Levitation.this.getUser());
				packet.write(Type.VAR_INT, Utils.getPlayerEntityId());
				packet.write(Type.SHORT, (short)0);
				packet.write(Type.SHORT, (short)vY);
				packet.write(Type.SHORT, (short)0);
				try {
					packet.send(Protocol1_8TO1_9.class, true, false);
				} catch (Exception ex) {ex.printStackTrace();}
			}
		}, 1L);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setAmplifier(int amplifier) {
		this.amplifier = amplifier;
	}
}
