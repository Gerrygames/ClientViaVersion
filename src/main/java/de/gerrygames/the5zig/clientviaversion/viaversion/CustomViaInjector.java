package de.gerrygames.the5zig.clientviaversion.viaversion;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;

public class CustomViaInjector implements us.myles.ViaVersion.api.platform.ViaInjector {
	@Override
	public void inject() throws Exception {}

	@Override
	public void uninject() throws Exception {}

	@Override
	public int getServerProtocolVersion() throws Exception {
		return ClientViaVersion.spoofedVersion;
	}

	@Override
	public String getEncoderName() {
		return "decoder";
	}

	@Override
	public String getDecoderName() {return "prepender";}
}
