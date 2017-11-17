package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_7_1_5;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_7_1_5.types.Types1_7_1_5;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueCreator;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.types.Types1_7_6_10;

public class Protocol1_7_6_10to1_7_1_5 extends Protocol {
	@Override
	protected void registerPackets() {
		//Login Success
		this.registerOutgoing(State.LOGIN, 0x02, 0x02, new PacketRemapper() {
			@Override
			public void registerMap() {
				map(Types1_7_1_5.UUID, Type.STRING);
				map(Type.STRING);
			}
		});

		//Spawn Player
		this.registerOutgoing(State.PLAY, 0x0C, 0x0C, new PacketRemapper() {
			@Override
			public void registerMap() {
				map(Type.VAR_INT);
				map(Types1_7_1_5.UUID, Type.STRING);
				map(Type.STRING);
				create(new ValueCreator() {
					@Override
					public void write(PacketWrapper packetWrapper) throws Exception {
						packetWrapper.write(Type.VAR_INT, 0);
					}
				});
				map(Type.INT);
				map(Type.INT);
				map(Type.INT);
				map(Type.BYTE);
				map(Type.BYTE);
				map(Type.SHORT);
				map(Types1_7_6_10.METADATA_LIST);
			}
		});
	}

	@Override
	public void init(UserConnection userConnection) {}
}