package de.gerrygames.the5zig.clientviaversion.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import us.myles.ViaVersion.api.type.Type;

import java.util.List;
import java.util.zip.Inflater;

public class CompressionDecoder extends ByteToMessageDecoder {
	private final Inflater field_179305_a;
	private int field_179304_b;

	public CompressionDecoder(int var1) {
		this.field_179304_b = var1;
		this.field_179305_a = new Inflater();
	}

	protected void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception {
		if (var2.readableBytes() != 0) {
			int var5 = Type.VAR_INT.read(var2);
			if (var5 == 0) {
				var3.add(var2.readBytes(var2.readableBytes()));
			} else {
				if (var5 < this.field_179304_b) {
					throw new DecoderException("Badly compressed packet - size of " + var5 + " is below server threshold of " + this.field_179304_b);
				}

				if (var5 > 2097152) {
					throw new DecoderException("Badly compressed packet - size of " + var5 + " is larger than protocol maximum of " + 2097152);
				}

				byte[] var6 = new byte[var2.readableBytes()];
				var2.readBytes(var6);
				this.field_179305_a.setInput(var6);
				byte[] var7 = new byte[var5];
				this.field_179305_a.inflate(var7);
				var3.add(Unpooled.wrappedBuffer(var7));
				this.field_179305_a.reset();
			}

		}
	}

	public void func_179303_a(int var1) {
		this.field_179304_b = var1;
	}
}