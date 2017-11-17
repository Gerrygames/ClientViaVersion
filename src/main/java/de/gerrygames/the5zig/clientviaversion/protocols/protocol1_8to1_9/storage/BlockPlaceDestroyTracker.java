package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.storage;

import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

public class BlockPlaceDestroyTracker extends StoredObject {
	private long blockPlaced, lastMining;
	boolean mining;

	public BlockPlaceDestroyTracker(UserConnection user) {
		super(user);
	}

	public long getBlockPlaced() {
		return blockPlaced;
	}

	public void place() {
		blockPlaced = System.currentTimeMillis();
	}

	public boolean isMining() {
		return mining;
	}

	public void setMining(boolean mining) {
		this.mining = mining;
		lastMining = System.currentTimeMillis();
	}

	public long getLastMining() {
		return lastMining;
	}
}
