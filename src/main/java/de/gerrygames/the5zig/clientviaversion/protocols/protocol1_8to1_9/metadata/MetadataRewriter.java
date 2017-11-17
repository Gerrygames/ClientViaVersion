package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.metadata;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.items.ItemRewriter;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.EulerAngle;
import us.myles.ViaVersion.api.minecraft.Vector;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_8;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_9;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.metadata.MetaIndex;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetadataRewriter {
	public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
		for (Metadata entry : new ArrayList<>(list)) {
			MetaIndex metaIndex = MetaIndex1_8to1_9.searchIndex(type, entry.getId());
			try {
				if (metaIndex != null) {
					if (metaIndex.getOldType() == MetaType1_8.NonExistent || metaIndex.getNewType()==MetaType1_9.Discontinued) {
						list.remove(entry);
						continue;
					}
					Object value = entry.getValue();
					entry.setMetaType(metaIndex.getOldType());
					entry.setId(metaIndex.getIndex());
					switch (metaIndex.getNewType()) {
						case Byte:
							// convert from int, byte
							if (metaIndex.getOldType() == MetaType1_8.Byte) {
								entry.setValue(value);
							}
							if (metaIndex.getOldType() == MetaType1_8.Int) {
								entry.setValue(((Byte) value).intValue());
							}
							// After writing the last one
							/*if (metaIndex == MetaIndex.ENTITY_STATUS && type == Entity1_10Types.EntityType.PLAYER) {

								Byte val = 0;
								if ((((Byte) value) & 0x10) == 0x10) { // Player eating/aiming/drinking
									val = 1;
								}
								int newIndex = MetaIndex.PLAYER_HAND.getNewIndex();
								MetaType metaType = MetaIndex.PLAYER_HAND.getNewType();
								Metadata metadata = new Metadata(newIndex, metaType, val);
								list.add(metadata);
							}*/
							break;
						case OptUUID:
							UUID owner = (UUID) value;
							if (owner == null) entry.setValue("");
							else entry.setValue(owner.toString());
							break;
						case BlockID:
							int combined = (Integer) value;
							int id = combined >> 4;
							int data = combined & 0xF;
							list.remove(entry);
							list.add(new Metadata(metaIndex.getIndex(), MetaType1_8.Short, id));
							list.add(new Metadata(metaIndex.getIndex(), MetaType1_8.Byte, data));
							break;
						case VarInt:
							// convert from int, short, byte
							if (metaIndex.getOldType() == MetaType1_8.Byte) {
								entry.setValue(((Integer) value).byteValue());
							}
							if (metaIndex.getOldType() == MetaType1_8.Short) {
								entry.setValue(((Integer) value).shortValue());
							}
							if (metaIndex.getOldType() == MetaType1_8.Int) {
								entry.setValue(value);
							}
							break;
						case Float:
							entry.setValue(value);
							break;
						case String:
							entry.setValue(value);
							break;
						case Boolean:
							if (metaIndex == MetaIndex.AGEABLE_AGE) entry.setValue((byte)((Boolean) value ? -1 : 0));
							else entry.setValue((byte)((Boolean) value ? 1 : 0));
							break;
						case Slot:
							entry.setValue(ItemRewriter.toClient((Item) value));
							break;
						case Position:
							Vector vector = (Vector) value;
							entry.setValue(vector);
							break;
						case Vector3F:
							EulerAngle angle = (EulerAngle) value;
							entry.setValue(angle);
							break;
						case Chat:
							//value = Protocol1_9TO1_8.fixJson((String) value);
							entry.setValue(value);
							break;
						default:
							Via.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
							list.remove(entry);
							break;
					}
					//System.out.println("old: " + metaIndex.getOldType() + " new: " + metaIndex.getNewType() + " real: " + entry.getMetaType() + " value:" + entry.getValue() + " class: " + entry.getValue().getClass());
				} else {
					throw new Exception("Could not find valid metadata");
				}
			} catch (Exception e) {
				list.remove(entry);
			}
		}
	}

}
