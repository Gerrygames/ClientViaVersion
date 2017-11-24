package de.gerrygames.the5zig.clientviaversion.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import us.myles.ViaVersion.api.type.Type;

import java.util.zip.Deflater;

public class CompressionEncoder extends MessageToByteEncoder<ByteBuf> {
	private final byte[] field_179302_a = new byte[8192];
	private final Deflater field_179300_b;
	private int field_179301_c;

	public CompressionEncoder(int var1) {
		this.field_179301_c = var1;
		this.field_179300_b = new Deflater();
	}

	protected void encode(ChannelHandlerContext var1, ByteBuf var2, ByteBuf var3) throws Exception {
		int var4 = var2.readableBytes();
		if (var4 < this.field_179301_c) {
			Type.VAR_INT.write(var3, 0);
			var3.writeBytes(var2);
		} else {
			byte[] var6 = new byte[var4];
			var2.readBytes(var6);
			Type.VAR_INT.write(var3, var6.length);
			this.field_179300_b.setInput(var6, 0, var4);
			this.field_179300_b.finish();

			while(!this.field_179300_b.finished()) {
				int var7 = this.field_179300_b.deflate(this.field_179302_a);
				var3.writeBytes(this.field_179302_a, 0, var7);
			}

			this.field_179300_b.reset();
		}

	}

	public void func_179299_a(int var1) {
		this.field_179301_c = var1;
	}
}