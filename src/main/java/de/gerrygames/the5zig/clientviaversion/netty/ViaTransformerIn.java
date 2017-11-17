package de.gerrygames.the5zig.clientviaversion.netty;

import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParser;

import java.util.List;

public class ViaTransformerIn extends ByteToMessageDecoder {
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes()==0 || ClientViaVersion.user==null) return;
		int packetId =  Type.VAR_INT.read(in);
		State state = Utils.getConnectionState(ctx.channel());
		ClientViaVersion.user.get(ProtocolInfo.class).setState(state);

		if (state==State.STATUS) {
			ByteBuf buffer = Unpooled.buffer();
			Type.VAR_INT.write(buffer, packetId);
			if (packetId==0) {
				String json = Type.STRING.read(in);
				JsonElement jsonElement = new JsonParser().parse(json);
				if (jsonElement instanceof JsonObject) {
					JsonObject jsonObject = (JsonObject) jsonElement;
					JsonObject version = jsonObject.getAsJsonObject("version");
					if (version.get("protocol").getAsInt()==ClientViaVersion.spoofedVersion)
						version.addProperty("protocol", ClientViaVersion.CLIENT_PROTOCOL_VERSION);
					json = jsonObject.toString();
				}
				Type.STRING.write(buffer, json);
			} else if (packetId==1) {
				Type.LONG.write(buffer, Type.LONG.read(in));
			}
			out.add(buffer);
			return;
		}

		if (state==State.LOGIN && packetId==0x03) {
			int threshold = Type.VAR_INT.read(in);
			ctx.pipeline().addBefore("viatransformerin", "decompress", (ChannelHandler) ClassNameUtils.getNettyCompressionDecoderClass().getConstructor(int.class).newInstance(threshold));
			ctx.pipeline().addBefore("viatransformerout", "compress", (ChannelHandler) ClassNameUtils.getNettyCompressionEncoderClass().getConstructor(int.class).newInstance(threshold));
			return;
		}

		UserConnection user = ClientViaVersion.user;
		PacketWrapper packetWrapper = new PacketWrapper(packetId, in, user);

		ProtocolPipeline pipeline = user.get(ProtocolInfo.class).getPipeline();
		try {
			pipeline.transform(Direction.OUTGOING, state, packetWrapper);
		} catch (CancelException e) {
			packetWrapper.clearInputBuffer();
			return;
		}
		ByteBuf buffer = Unpooled.buffer();
		packetWrapper.writeToBuffer(buffer);
		buffer.readerIndex(0);
		out.add(buffer);
	}
}