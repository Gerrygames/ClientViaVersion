package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_7_6_10to1_8.items;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.chunks.BlockStorage;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public enum ItemReplacement {
	STANDING_BANNER(176, 63, "Banner"),
	HANGING_BANNER(177, 68, "Banner"),
	BANNER(425, 323, "Banner"),
	BARRIER(166, 20, "Barrier"),
	IRON_TRAPDOOR(167, 96, "Iron Trapdoor"),
	GRANITE(1, 1, 1, 0, "Granite"),
	POLISHED_GRANITE(1, 2, 1, 0, "Polished Granite"),
	DIORITE(1, 3, 1, 0, "Diorite"),
	POLISHED_DIORITE(1, 4, 1, 0, "Polished Diorite"),
	ANDESITE(1, 5, 1, 0, "Andsite"),
	POLISHED_ANDESITE(1, 6, 1, 0, "Polished Andsite"),
	PRISMARINE(168, 0, 1, 0, "Prismarine"),
	PRISMARINE_BRICKS(168, 1, 98, 0, "Prismarine Bricks"),
	DARK_PRISMARINE(168, 2, 98, 1, "Dark Prismarine"),
	PRISMARINE_SHARD(409, 406, "Prismarine Shard"),
	PRISMARINE_CRYSTAL(410, 406, "Prismarine Crystal"),
	SEA_LANTERN(169, 89, "Sea Lantern"),
	SLIME_BLOCK(165, -1, 95, 5, "Slime Block"),
	RED_SANDSTONE(179, 0, 24, 0, "Red Sandstone"),
	CHISELED_RED_SANDSTONE(179, 1, 24, 1, "Chiseled Red Sandstone"),
	SMOOTH_RED_SANDSTONE(179, 2, 24, 2, "Chiseled Red Sandstone"),
	RED_SANDSTONE_SLAB(182, 0, 44, 1, "Red Sandstone Slab"),
	DOUBLE_RED_SANDSTONE_SLAB(181, 0, 43, 1, "Double Red Sandstone Slab"),
	RED_SANDSTONE_STAIRS(180, 0, 128, 0, "Red Sandstone Stairs"),
	ARMOR_STAND(416, 280, "Armor Stand"),
	SPRUCE_FENCE(188, 85, "Spruce Fence"),
	BIRCH_FENCE(189, 85, "Birch Fence"),
	JUNGLE_FENCE(190, 85, "Jungle Fence"),
	DARK_OAK_FENCE(191, 85, "Dark Oak Fence"),
	ACACIA_FENCE(192, 85, "Acacia Fence"),
	SPRUCE_FENCE_GATE(183, 107, "Spruce Fence Gate"),
	BIRCH_FENCE_GATE(184, 107, "Birch Fence Gate"),
	JUNGLE_FENCE_GATE(185, 107, "Jungle Fence Gate"),
	DARK_OAK_FENCE_GATE(186, 107, "Dark Oak Fence Gate"),
	ACACIA_FENCE_GATE(187, 107, "Acacia Fence Gate"),
	SPRUCE_DOOR_BLOCK(193, 64, "Spruce Door"),
	BIRCH_DOOR_BLOCK(194, 64, "Birch Door"),
	JUNGLE_DOOR_BLOCK(195, 64, "Jungle Door"),
	DARK_OAK_DOOR_BLOCK(196, 64, "Dark Oak Door"),
	ACACIA_DOOR_BLOCK(197, 64, "Acacia Door"),
	SPRUCE_DOOR(427, 324, "Spruce Door"),
	BIRCH_DOOR(428, 324, "Birch Door"),
	JUNGLE_DOOR(429, 324, "Jungle Door"),
	DARK_OAK_DOOR(430, 324, "Dark Oak Door"),
	ACACIA_DOOR(431, 324, "Acacia Door"),
	ACTIVATOR_RAIL(157, 28, "Activator Rail"),
	;

	private int oldId, replacementId, oldData, replacementData;
	private String name, resetName, bracketName;

	ItemReplacement(int oldId, int replacementId) {
		this(oldId, replacementId, null);
	}

	ItemReplacement(int oldId, int replacementId, String name) {
		this(oldId, -1, replacementId, -1, name);
	}

	ItemReplacement(int oldId, int oldData, int replacementId, int replacementData) {
		this(oldId, -1, replacementId, -1, null);
	}

	ItemReplacement(int oldId, int oldData, int replacementId, int replacementData, String name) {
		this.oldId = oldId;
		this.oldData = oldData;
		this.replacementId = replacementId;
		this.replacementData = replacementData;
		this.name = name;
		this.resetName = "§r" + name;
		this.bracketName = " §r§7(" + name + "§r§7)";
	}

	public static BlockStorage.BlockState replaceBlock(BlockStorage.BlockState block) {
		for (ItemReplacement replacement : ItemReplacement.values()) {
			if (replacement.oldId==block.getId() && (replacement.oldData==-1 || replacement.oldData==block.getData())) {
				return new BlockStorage.BlockState(replacement.replacementId, replacement.replacementData==-1 ? block.getData() : replacement.replacementData);
			}
		}
		return block;
	}

	public static ItemReplacement findReplacement(Item oldItem) {
		if (oldItem==null) return null;
		for (ItemReplacement replacement : ItemReplacement.values()) {
			if (replacement.canReplace(oldItem)) return replacement;
		}
		return null;
	}

	public static void toClient(Item item) {
		if (item==null) return;
		ItemReplacement replacement = findReplacement(item);
		if (replacement!=null) replacement.toClientInternal(item);
	}

	public void toClientInternal(Item item) {
		if (item==null) return;
		item.setId((short)replacementId);
		if (replacementData!=-1) item.setData((short)replacementData);
		if (name!=null) {
			CompoundTag compoundTag = item.getTag()==null ? new CompoundTag("") : item.getTag();
			if (!compoundTag.contains("display")) compoundTag.put(new CompoundTag("display"));
			CompoundTag display = compoundTag.get("display");
			if (display.contains("Name")) {
				StringTag name = display.get("Name");
				if (!name.getValue().equals(resetName) && !name.getValue().endsWith(bracketName))
					name.setValue(name.getValue() + bracketName);
			} else {
				display.put(new StringTag("Name", resetName));
			}
			item.setTag(compoundTag);
		}
	}

	public boolean canReplace(Item item) {
		return item != null && item.getId() == oldId && (oldData == -1 || item.getData() == oldData);
	}

	public boolean isReplacement(Item item) {
		return item != null && item.getId() == replacementId && (replacementData == -1 || item.getData() == replacementData) && checkName(item);
	}

	private boolean checkName(Item item) {
		String name = item.getTag() != null && item.getTag().contains("display") && ((CompoundTag)item.getTag().get("display")).contains("Name") ? (String) ((CompoundTag)item.getTag().get("display")).get("Name").getValue() : null;
		return (name==null && this.name==null) || (name!=null && (name.equals(resetName) || name.endsWith(bracketName)));
	}

	public int getOldId() {
		return oldId;
	}

	public int getReplacementId() {
		return replacementId;
	}

	public int getOldData() {
		return oldData;
	}

	public int getReplacementData() {
		return replacementData;
	}

	public String getName() {
		return name;
	}
}
