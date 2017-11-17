package de.gerrygames.the5zig.clientviaversion.protocols;

import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.protocol.ProtocolRegistry;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.remapper.ValueTransformer;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_12to1_11_1.ChatItemRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.ItemRewriter;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9TO1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonParser;

public class ProtocolPatcher {
	public static void patch() {
		ProtocolRegistry.getProtocolPath(107, 47).get(0).getValue().registerOutgoing(State.PLAY, 71, 72, new PacketRemapper() {
			@Override
			public void registerMap() {
				handler(new PacketHandler() {
					@Override
					public void handle(PacketWrapper packetWrapper) throws Exception {
						String header = packetWrapper.read(Type.STRING);
						String footer = packetWrapper.read(Type.STRING);
						header = Protocol1_9TO1_8.fixJson(header.replace("text:", "\"text\":"));
						footer = Protocol1_9TO1_8.fixJson(footer.replace("text:", "\"text\":"));
						packetWrapper.write(Type.STRING, header);
						packetWrapper.write(Type.STRING, footer);
					}
				});

			}
		});

		ProtocolRegistry.getProtocolPath(335, 316).get(0).getValue().registerOutgoing(State.PLAY, 15, 15, new PacketRemapper() {
			@Override
			public void registerMap() {
				this.handler(new PacketHandler() {
					public void handle(PacketWrapper packetWrapper) throws Exception {
						try {
							JsonElement obj = new JsonParser().parse(packetWrapper.read(Type.STRING));
							ChatItemRewriter.toClient(obj, packetWrapper.user());
							packetWrapper.write(Type.STRING, obj.toString());
						} catch (Exception ex) {ex.printStackTrace();}
					}
				});
				this.map(Type.BYTE);
			}
		});

		ProtocolRegistry.getProtocolPath(107, 47).get(0).getValue().registerIncoming(State.PLAY, 15, 5, new PacketRemapper() {
			@Override
			public void registerMap() {
				map(Type.BYTE);
				map(Type.SHORT);
				map(Type.BOOLEAN);
				handler(new PacketHandler() {
					@Override
					public void handle(PacketWrapper packetWrapper) throws Exception {
						int windowId = packetWrapper.get(Type.BYTE, 0);
						int action = packetWrapper.get(Type.SHORT, 0);
						boolean accepted = packetWrapper.get(Type.BOOLEAN, 0);
						if (action==-89) packetWrapper.cancel(); //pls dont break anything
					}
				});
			}
		});


		ProtocolRegistry.getProtocolPath(107, 47).get(0).getValue().registerOutgoing(State.PLAY, 4, 60, new PacketRemapper() {
			public void registerMap() {
				this.map(Type.VAR_INT);
				this.map(Type.SHORT, new ValueTransformer<Short, Integer>(Type.VAR_INT) {
					public Integer transform(PacketWrapper wrapper, Short slot) {
						return slot > 0 ? slot.intValue() + 1 : slot.intValue();
					}
				});
				this.map(Type.ITEM);
				this.handler(new PacketHandler() {
					@Override
					public void handle(PacketWrapper wrapper) throws Exception {
						int slot = wrapper.get(Type.VAR_INT, 1);
						if (slot>5) wrapper.cancel();
					}
				});
				this.handler(new PacketHandler() {
					public void handle(PacketWrapper wrapper) throws Exception {
						Item stack = wrapper.get(Type.ITEM, 0);
						ItemRewriter.toClient(stack);
					}
				});
				this.handler(new PacketHandler() {
					public void handle(PacketWrapper wrapper) throws Exception {
						EntityTracker entityTracker = wrapper.user().get(EntityTracker.class);
						int entityID = wrapper.get(Type.VAR_INT, 0);
						Item stack = wrapper.get(Type.ITEM, 0);
						int slot = wrapper.get(Type.VAR_INT, 1);
						if (slot==0 && stack != null && Protocol1_9TO1_8.isSword(stack.getId())) {
							entityTracker.getValidBlocking().add(entityID);
						} else {
							entityTracker.getValidBlocking().remove(entityID);
						}
					}
				});
			}
		});
	}
}
