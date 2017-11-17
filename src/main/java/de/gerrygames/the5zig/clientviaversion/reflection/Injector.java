package de.gerrygames.the5zig.clientviaversion.reflection;

import de.gerrygames.the5zig.clientviaversion.main.ClientViaVersion;
import de.gerrygames.the5zig.clientviaversion.netty.ViaTransformerIn;
import de.gerrygames.the5zig.clientviaversion.netty.ViaTransformerOut;
import eu.the5zig.mod.The5zigMod;
import eu.the5zig.mod.gui.GuiCredits;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import de.gerrygames.the5zig.clientviaversion.utils.ClassNameUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.simple.SimpleLogger;
import org.apache.logging.log4j.util.PropertiesUtil;
import us.myles.ViaVersion.api.Pair;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class Injector {

	public static void patchCredits() throws NoSuchFieldException, IllegalAccessException {
		Field creditsf = GuiCredits.class.getDeclaredField("credits");
		creditsf.setAccessible(true);
		LinkedHashMap<String, List<String>> credits = (LinkedHashMap<String, List<String>>) creditsf.get(null);
		credits.put("ClientViaVersion", Arrays.asList("Plugin by Gerrygames", "ViaVersion API by Mylescomputer", "https://github.com/MylesIsCool/ViaVersion", "ViaBackwards API by Matsv", "https://github.com/Matsv/ViaBackwards"));
	}

	public static void injectListener() throws NoSuchFieldException, IllegalAccessException {
		Field logger = ClassNameUtils.getNetworkManagerLoggerField();
		logger.setAccessible(true);
		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.set(logger, logger.getModifiers() & ~Modifier.FINAL);

		Logger oldlogger = (Logger) logger.get(null);

		logger.set(null, new SimpleLogger(oldlogger.getName(), Level.OFF, false, false, false, false, "", oldlogger.getMessageFactory(), PropertiesUtil.getProperties(), System.out) {
			private final Class networkmanagerclass = ClassNameUtils.getNetworkManagerClass();
			private final Class nethandlerloginclass = ClassNameUtils.getNetHanderLoginClientClass();
			private final Class inethandlerstatusclass = ClassNameUtils.getINetHanderStatusClientClass();

			@Override
			public void debug(String message, Object... params) {
				if (params.length==2) bridge(message, params[0], params[1]);
				super.debug(message, params);
			}

			@Override
			public boolean isEnabled(Level testLevel, Marker marker, String msg, Throwable t) {
				if (msg.startsWith("push")) {
					return ClientViaVersion.spoofedVersion<=47;
				}
				return super.isEnabled(testLevel, marker, msg, t);
			}

			//@Override ?
			public void debug(String message, Object param1, Object param2) {
				bridge(message, param1, param2);
				super.debug(message, param1, param2);
			}

			private void bridge(String message, Object param1, Object param2) {
				if (networkmanagerclass!=null) try {
					if (networkmanagerclass.isInstance(param1) && (nethandlerloginclass.isInstance(param2) || param2.getClass().getName().split("\\$")[0].equals(inethandlerstatusclass.getName()))) {
						Injector.injectViaHandler(param1);
					}
				} catch (Exception ex) {ex.printStackTrace();}
			}
		});
	}

	public static void injectViaHandler(Object networkManager) {
		if (ClientViaVersion.CLIENT_PROTOCOL_VERSION ==ClientViaVersion.spoofedVersion) return;
		ClientViaVersion.networkManager = networkManager;
		try {
			Field channelf = ClassNameUtils.getNetworkManagerChannelField();
			channelf.setAccessible(true);
			Channel channel = (Channel)channelf.get(networkManager);
			if (channel==null) throw new NullPointerException("Channel is null?");

			UserConnection user = ClientViaVersion.user = new UserConnection((SocketChannel) channel) {
				@Override
				public void sendRawPacket(final ByteBuf packet, boolean currentThread) {
					Channel channel = this.getChannel();
					channel.eventLoop().submit(new Runnable() {
						public void run() {
							try {
								channel.pipeline().get("decoder").channelRead(channel.pipeline().context("decoder"), packet);
							} catch (Exception e) {e.printStackTrace();}
						}
					});
				}
				@Override
				public ChannelFuture sendRawPacketFuture(ByteBuf packet) {
					this.getChannel().pipeline().context("decoder").fireChannelRead(packet);
					return null;
				}
			};
			new ProtocolPipeline(user);

			ProtocolInfo info = user.get(ProtocolInfo.class);
			info.setUsername(The5zigMod.getVars().getUsername());
			info.setUuid(The5zigMod.getVars().getGameProfile().getId());
			ProtocolPipeline pipeline = info.getPipeline();

			List<Pair<Integer, Protocol>> path = ProtocolRegistry.getProtocolPath(ClientViaVersion.CLIENT_PROTOCOL_VERSION, ClientViaVersion.spoofedVersion);
			if (path==null) return;
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i<path.size(); i++)	{
				pipeline.add(path.get(i).getValue());
			}
			for (Protocol protocol : pipeline.pipes()) {
				protocol.init(user);
			}
			channel.pipeline().addBefore("decoder", "viatransformerin", new ViaTransformerIn());
			channel.pipeline().addBefore("encoder", "viatransformerout", new ViaTransformerOut());
			Via.getManager().addPortedClient(user);
		} catch (Exception ex) {
			The5zigMod.logger.error("[ClientViaVersion] Could not inject ViaVersion into pipeline.");
			ex.printStackTrace();
		}
	}
}
