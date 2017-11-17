package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_7_1_5.types;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;

public class UUIDType extends Type<String> {
	public UUIDType() {
		super(String.class);
	}

	@Override
	public String read(ByteBuf buffer) throws Exception {
		StringBuilder uuidBuff = new StringBuilder(Type.STRING.read(buffer));
		uuidBuff.insert(20, '-');
		uuidBuff.insert(16, '-');
		uuidBuff.insert(12, '-');
		uuidBuff.insert(8, '-');
		return uuidBuff.toString();
	}

	@Override
	public void write(ByteBuf buffer, String uuid) throws Exception {
		Type.STRING.write(buffer, uuid.replace("-", ""));
	}
}
