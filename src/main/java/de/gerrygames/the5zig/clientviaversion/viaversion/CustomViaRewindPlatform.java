package de.gerrygames.the5zig.clientviaversion.viaversion;

import de.gerrygames.viarewind.api.ViaRewindConfig;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import us.myles.ViaVersion.api.Via;

import java.util.logging.Logger;

public class CustomViaRewindPlatform implements ViaRewindPlatform {

	public CustomViaRewindPlatform() {
		init(new ViaRewindConfig() {
			@Override
			public CooldownIndicator getCooldownIndicator() {
				return CooldownIndicator.TITLE;
			}

			@Override
			public boolean isReplaceAdventureMode() {
				return true;
			}
		});
	}

	@Override
	public Logger getLogger() {
		return Via.getPlatform().getLogger();
	}

	@Override
	public void disable() { }
}
