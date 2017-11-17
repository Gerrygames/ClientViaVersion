package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.types;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.opennbt.NBTIO;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

import java.io.*;

public class NBTType extends Type<CompoundTag> {
	public NBTType() {
		super(CompoundTag.class);
	}

	@Override
	public CompoundTag read(ByteBuf buffer) {
		short length = buffer.readShort();
		if (length < 0) {return null;}
		ByteBufInputStream byteBufInputStream = new ByteBufInputStream(buffer);
		DataInputStream dataInputStream = new DataInputStream(byteBufInputStream);
		try {
			return (CompoundTag) NBTIO.readTag((DataInput) dataInputStream);
		} catch (Throwable throwable) {throwable.printStackTrace();}
		finally {
			try {
				dataInputStream.close();
			} catch (IOException e) {e.printStackTrace();}
		}
		return null;
	}

	@Override
	public void write(ByteBuf buffer, CompoundTag nbt) throws Exception {
		if (nbt == null) {
			buffer.writeShort(-1);
		} else {
			ByteBuf buf = Unpooled.buffer();
			ByteBufOutputStream bytebufStream = new ByteBufOutputStream(buf);
			DataOutputStream dataOutputStream = new DataOutputStream(bytebufStream);
			NBTIO.writeTag((DataOutput) dataOutputStream, nbt);
			dataOutputStream.close();
			buffer.writeShort(buf.readableBytes());
			buffer.writeBytes(buf);
		}
	}
}