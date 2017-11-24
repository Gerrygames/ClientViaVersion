package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.map;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.Protocol1_7_6_10TO1_8;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.exception.CancelException;

import java.util.ArrayList;

public class MapPacketCache extends StoredObject {
	private ArrayList<PacketWrapper> cache = new ArrayList<>();
	private int max = 1;

	public MapPacketCache(UserConnection user) {
		super(user);

		Via.getPlatform().runRepeatingSync(new Runnable() {
			@Override
			public void run() {
				if (cache.isEmpty()) return;
				for (int i = 0; i<max; i++) {
					PacketWrapper packetWrapper = cache.remove(0);
					try {
						packetWrapper.send(Protocol1_7_6_10TO1_8.class);
					} catch (CancelException ignored) {
						;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}, 1L);
	}

	public void add(PacketWrapper packetWrapper) {
		cache.add(packetWrapper);
	}
}
