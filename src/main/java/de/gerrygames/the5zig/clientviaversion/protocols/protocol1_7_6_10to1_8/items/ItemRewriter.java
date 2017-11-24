package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.items;

import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ShortTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class ItemRewriter {

	public static Item toClient(Item item) {
		if (item==null) return null;

		CompoundTag tag = item.getTag();
		if (tag==null) item.setTag(tag = new CompoundTag(""));

		CompoundTag viaVersionTag = new CompoundTag("ClientViaVersion");
		tag.put(viaVersionTag);

		viaVersionTag.put(new ShortTag("id", item.getId()));
		viaVersionTag.put(new ShortTag("data", item.getData()));

		CompoundTag display = tag.get("display");
		if (display!=null && display.contains("Name")) {
			viaVersionTag.put(new StringTag("displayName", (String) display.get("Name").getValue()));
		}

		ItemReplacement.toClient(item);

		return item;
	}

	public static Item toServer(Item item) {
		if (item==null) return null;

		CompoundTag tag = item.getTag();

		 if (tag==null || !item.getTag().contains("ClientViaVersion")) return item;


		CompoundTag viaVersionTag = tag.remove("ClientViaVersion");

		item.setId((Short) viaVersionTag.get("id").getValue());
		item.setData((Short) viaVersionTag.get("data").getValue());

		if (viaVersionTag.contains("displayName")) {
			CompoundTag display = tag.get("display");
			if (display==null) tag.put(display = new CompoundTag("display"));
			StringTag name = display.get("Name");
			if (name==null) display.put(new StringTag("Name", (String) viaVersionTag.get("displayName").getValue()));
			else name.setValue((String) viaVersionTag.get("displayName").getValue());
		} else if (tag.contains("display")) {
			((CompoundTag)tag.get("display")).remove("Name");
		}

		return item;
	}
}
