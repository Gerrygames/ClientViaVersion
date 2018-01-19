package de.gerrygames.the5zig.clientviaversion.viaversion;

import de.gerrygames.viarewind.ViaRewind;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import us.myles.ViaVersion.api.Via;

import java.util.logging.Logger;

public class CustomViaRewindPlatform implements ViaRewindPlatform {

	public CustomViaRewindPlatform() {
		ViaRewind.init(this);
		init();
	}

	@Override
	public Logger getLogger() {
		return Via.getPlatform().getLogger();
	}

	@Override
	public void disable() { }
}
