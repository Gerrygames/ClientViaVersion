package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.metadata;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.items.ItemRewriter;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.metadata.MetaIndex1_8to1_7_6_10;
import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_7_6_10.types.MetaType1_7_6_10;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.entities.Entity1_10Types;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.minecraft.metadata.types.MetaType1_8;

import java.util.ArrayList;
import java.util.List;

public class MetadataRewriter {

	public static void transform(Entity1_10Types.EntityType type, List<Metadata> list) {
		for (Metadata entry : new ArrayList<>(list)) {
			MetaIndex1_8to1_7_6_10 metaIndex = MetaIndex1_7_6_10to1_8.searchIndex(type, entry.getId());
			try {
				if (metaIndex == null) throw new Exception("Could not find valid metadata");
				if (metaIndex.getOldType() == MetaType1_7_6_10.NonExistent) {
					list.remove(entry);
					continue;
				}
				Object value = entry.getValue();
				if (!value.getClass().isAssignableFrom(metaIndex.getNewType().getType().getOutputClass())) {
					list.remove(entry);
					continue;
				}
				entry.setMetaType(metaIndex.getOldType());
				entry.setId(metaIndex.getIndex());
				switch (metaIndex.getOldType()) {
					case Int:
						if (metaIndex.getNewType() == MetaType1_8.Byte) {
							entry.setValue(((Byte) value).intValue());
						}
						if (metaIndex.getNewType() == MetaType1_8.Short) {
							entry.setValue(((Short) value).intValue());
						}
						if (metaIndex.getNewType() == MetaType1_8.Int) {
							entry.setValue(value);
						}
						break;
					case Byte:
						if (metaIndex.getNewType() == MetaType1_8.Int) {
							entry.setValue(((Integer) value).byteValue());
						}
						if (metaIndex.getNewType() == MetaType1_8.Byte) {
							entry.setValue(value);
						}
						if (metaIndex==MetaIndex1_8to1_7_6_10.HUMAN_SKIN_FLAGS) {
							byte flags = (byte) value;
							boolean cape = (flags & 2) == 2;
							flags = (byte) (cape ? 2 : 0);
							entry.setValue(flags);
						}
						break;
					case Slot:
						entry.setValue(ItemRewriter.toClient((Item) value));
						break;
					case Float:
						entry.setValue(value);
						break;
					case Short:
						entry.setValue(value);
						break;
					case String:
						entry.setValue(value);
						break;
					case Position:
						entry.setValue(value);
						break;
					default:
						Via.getPlatform().getLogger().warning("[Out] Unhandled MetaDataType: " + metaIndex.getNewType());
						list.remove(entry);
						break;
				}
			} catch (Exception e) {
				list.remove(entry);
			}
		}
	}
}
