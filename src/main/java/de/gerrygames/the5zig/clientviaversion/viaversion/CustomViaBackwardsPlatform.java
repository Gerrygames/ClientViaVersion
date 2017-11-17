package de.gerrygames.the5zig.clientviaversion.viaversion;

import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.ViaBackwardsPlatform;
import us.myles.ViaVersion.api.Via;

import java.util.logging.Logger;

public class CustomViaBackwardsPlatform implements ViaBackwardsPlatform {

	public CustomViaBackwardsPlatform() {
		ViaBackwards.init(this);
		init();
	}

	@Override
	public Logger getLogger() {return Via.getPlatform().getLogger();}

	@Override
	public boolean isOutdated() {return false;}

	@Override
	public void disable() {}
}