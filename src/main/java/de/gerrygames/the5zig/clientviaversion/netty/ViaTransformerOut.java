package de.gerrygames.the5zig.clientviaversion.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.ReferenceCountUtil;
import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.utils.Utils;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.exception.CancelException;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

import java.util.ArrayList;
import java.util.List;

public class ViaTransformerOut extends MessageToByteEncoder<ByteBuf> {
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf in, ByteBuf out) throws Exception {
		int packetId = Type.VAR_INT.read(in);
		State state = Utils.getConnectionState(ctx.channel());
		ClientViaVersion.user.get(ProtocolInfo.class).setState(state);

		if (state==State.HANDSHAKE || state==State.STATUS) {
			Type.VAR_INT.write(out, packetId);
			if (packetId==0 && in.readableBytes()>0) {
				int protocolversion = Type.VAR_INT.read(in);
				String address = Type.STRING.read(in);
				short port = Type.SHORT.read(in);
				int nextstate = Type.VAR_INT.read(in);
				Type.VAR_INT.write(out, ClientViaVersion.spoofedVersion);
				Type.STRING.write(out, address);
				Type.SHORT.write(out, port);
				Type.VAR_INT.write(out, nextstate);
			} else if (packetId==1) {
				Type.LONG.write(out, Type.LONG.read(in));
			}
			return;
		}

		UserConnection user = ClientViaVersion.user;
		PacketWrapper packetWrapper = new PacketWrapper(packetId, in, user);

		ProtocolPipeline pipeline = user.get(ProtocolInfo.class).getPipeline();
		try {
			pipeline.transform(Direction.INCOMING, state, packetWrapper);
		} catch (CancelException e) {
			packetWrapper.clearInputBuffer();
			return;
		}

		packetWrapper.writeToBuffer(out);
	}

	public static void sendToServer(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline, boolean currentThread) throws Exception {
		if (packet.isCancelled()) return;
		ByteBuf raw = constructPacket(packet, packetProtocol, skipCurrentPipeline);

		final SocketChannel channel = packet.user().getChannel();
		final ChannelHandler handler = channel.pipeline().get("viatransformerout");
		if (currentThread) {
			channel.pipeline().context(handler).writeAndFlush(raw);
		} else {
			channel.eventLoop().submit(new Runnable() {
				public void run() {
					channel.pipeline().context(handler).writeAndFlush(raw);
				}
			});
		}
	}

	private static ByteBuf constructPacket(PacketWrapper packet, Class<? extends Protocol> packetProtocol, boolean skipCurrentPipeline) throws Exception {
		List<Protocol> protocols = new ArrayList(packet.user().get(ProtocolInfo.class).getPipeline().pipes());
		int index = 0;

		for(int i = 0; i < protocols.size(); ++i) {
			if (((Protocol)protocols.get(i)).getClass().equals(packetProtocol)) {
				index = skipCurrentPipeline ? i + 1 : i;
				break;
			}
		}

		packet.resetReader();
		packet.apply(Direction.INCOMING, packet.user().get(ProtocolInfo.class).getState(), index, protocols);
		ByteBuf output = Unpooled.buffer();
		packet.writeToBuffer(output);
		return output;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		ByteBuf buf = null;
		try {
				ByteBuf cast = (ByteBuf) msg;
				buf = this.allocateBuffer(ctx, cast, true);

				try {
					this.encode(ctx, cast, buf);
				} finally {
					ReferenceCountUtil.release(msg);
				}

				if(buf.isReadable() && buf.readableBytes()>0) {
					ctx.write(buf, promise);
				} else {
					buf.release();
				}
				buf = null;
		} catch (EncoderException var17) {
			throw var17;
		} catch (Throwable var18) {
			throw new EncoderException(var18);
		} finally {
			if(buf != null) {
				buf.release();
			}

		}
	}
}