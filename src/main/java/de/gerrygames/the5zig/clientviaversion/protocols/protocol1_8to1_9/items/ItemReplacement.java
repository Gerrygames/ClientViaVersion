package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.items;

import de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.chunks.BlockStorage;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public enum ItemReplacement {
	END_ROD(198, 85, "End Rod"),
	CHORUS_PLANT(199, 0, 35, 10, "Chorus Plant"),
	CHORUS_FLOWER(200, 0, 35, 2, "Chorus Flower"),
	PURPUR_BLOCK(201, 155, "Purpur Block"),
	PURPUR_PILLAR(202, 0, 155, 2, "Purpur Pillar"),
	PURPUR_STAIRS(203, 156, "Purpur Stairs"),
	PURPUR_DOUBLE_SLAB(204, 0, 43, 7, "Purpur Double Slab"),
	PURPUR_SLAB(205, 0, 44, 7, "Purpur Slab"),
	ENDSTONE_BRICKS(206, 121, "Endstone Bricks"),
	BEETROOT_BLOCK(207, 141, "Beetroot Block"),
	GRASS_PATH(208, 2, "Grass Path"),
	END_GATEWAY(209, 90, "End Gateway"),
	REPEATING_COMMAND_BLOCK(210, 137, "Repeating Command Block"),
	CHAIN_COMMAND_BLOCK(211, 137, "Chain Command Block"),
	FROSTED_ICE(212, 79, "Frosted Ice"),
	//MAGMA_BLOCK(213, 87, "Magma Block"),
	NETHER_WART_BLOCK(214, 87, "Nether Wart Block"),
	RED_NETHER_BRICK(215, 112, "Red Nether Brick"),
	//BONE_BLOCK(216, 155, "Bone Block"),
	STRUCTURE_VOID(217, 166, "Structure Void"),
	//OBSERVER(218, 61, "Observer"),
	/*WHITE_SHULKER_BOX(219, 54, "White Shulker Box"),
	ORANGE_SHULKER_BOX(220, 54, "Orange Shulker Box"),
	MAGENTA_SHULKER_BOX(221, 54, "Magenta Shulker Box"),
	LIGHT_BLUE_SHULKER_BOX(222, 54, "Light Blue Shulker Box"),
	YELLOW_SHULKER_BOX(223, 54, "Yellow Shulker Box"),
	LIME_SHULKER_BOX(224, 54, "Lime Shulker Box"),
	PINK_SHULKER_BOX(225, 54, "Pink Shulker Box"),
	GRAY_SHULKER_BOX(226, 54, "Gray Shulker Box"),
	LIGHT_GRAY_SHULKER_BOX(227, 54, "Light Gray Shulker Box"),
	CYAN_SHULKER_BOX(228, 54, "Cyan Shulker Box"),
	PURPLE_SHULKER_BOX(229, 54, "Purple Shulker Box"),
	BLUE_SHULKER_BOX(230, 54, "Blue Shulker Box"),
	BROWN_SHULKER_BOX(231, 54, "Brown Shulker Box"),
	GREEN_SHULKER_BOX(232, 54, "Green Shulker Box"),
	RED_SHULKER_BOX(233, 54, "Red Shulker Box"),
	BLACK_SHULKER_BOX(234, 54, "Black Shulker Box"),
	WHITE_GLAZED_TERRACOTTA(235, 0, 159, 0, "White Glazed Terracotta"),
	ORANGE_GLAZED_TERRACOTTA(236, 0, 159, 1, "Orange Glazed Terracotta"),
	MAGENTA_GLAZED_TERRACOTTA(237, 0, 159, 2, "Magenta Glazed Terracotta"),
	LIGHT_BLUE_GLAZED_TERRACOTTA(238, 0, 159, 3, "Light Blue Glazed Terracotta"),
	YELLOW_GLAZED_TERRACOTTA(239, 0, 159, 4, "Yellow Glazed Terracotta"),
	LIME_GLAZED_TERRACOTTA(240, 0, 159, 5, "Lime Glazed Terracotta"),
	PINK_GLAZED_TERRACOTTA(241, 0, 159, 6, "Pink Glazed Terracotta"),
	GRAY_GLAZED_TERRACOTTA(242, 0, 159, 7, "Gray Glazed Terracotta"),
	LIGHT_GRAY_GLAZED_TERRACOTTA(243, 0, 159, 8, "Light Gray Glazed Terracotta"),
	CYAN_GLAZED_TERRACOTTA(244, 0, 159, 9, "Cyan Glazed Terracotta"),
	PURPLE_GLAZED_TERRACOTTA(245, 0, 159, 10, "Purple Glazed Terracotta"),
	BLUE_GLAZED_TERRACOTTA(246, 0, 159, 11, "Blue Glazed Terracotta"),
	BROWN_GLAZED_TERRACOTTA(247, 0, 159, 12, "Brown Glazed Terracotta"),
	GREEN_GLAZED_TERRACOTTA(248, 0, 159, 13, "Green Glazed Terracotta"),
	RED_GLAZED_TERRACOTTA(249, 0, 159, 14, "Red Glazed Terracotta"),
	BLACK_GLAZED_TERRACOTTA(250, 0, 159, 15, "Black Glazed Terracotta"),
	WHITE_CONCRETE(251, 0, 159, 0, "White Concrete"),
	ORANGE_CONCRETE(251, 1, 159, 1, "Orange Concrete"),
	MAGENTA_CONCRETE(251, 2, 159, 2, "Magenta Concrete"),
	LIGHT_BLUE_CONCRETE(251, 3, 159, 3, "Light Blue Concrete"),
	YELLOW_CONCRETE(251, 4, 159, 4, "Yellow Concrete"),
	LIME_CONCRETE(251, 5, 159, 5, "Lime Concrete"),
	PINK_CONCRETE(251, 6, 159, 6, "Pink Concrete"),
	GRAY_CONCRETE(251, 7, 159, 7, "Gray Concrete"),
	LIGHT_GRAY_CONCRETE(251, 8, 159, 8, "Light Gray Concrete"),
	CYAN_CONCRETE(251, 9, 159, 9, "Cyan Concrete"),
	PURPLE_CONCRETE(251, 10, 159, 10, "Purple Concrete"),
	BLUE_CONCRETE(251, 11, 159, 11, "Blue Concrete"),
	BROWN_CONCRETE(251, 12, 159, 12, "Brown Concrete"),
	GREEN_CONCRETE(251, 13, 159, 13, "Green Concrete"),
	RED_CONCRETE(251, 14, 159, 14, "Red Concrete"),
	BLACK_CONCRETE(251, 15, 159, 15, "Black Concrete"),
	WHITE_CONCRETE_POWDER(252, 0, 12, 0, "White Conrete Powder"),
	ORANGE_CONCRETE_POWDER(252, 1, 12, 0, "Orange Conrete Powder"),
	MAGENTA_CONCRETE_POWDER(252, 2, 12, 0, "Magenta Conrete Powder"),
	LIGHT_BLUE_CONCRETE_POWDER(252, 3, 12, 0, "Light Blue Conrete Powder"),
	YELLOW_CONCRETE_POWDER(252, 4, 12, 0, "Yellow Conrete Powder"),
	LIME_CONCRETE_POWDER(252, 5, 12, 0, "Lime Conrete Powder"),
	PINK_CONCRETE_POWDER(252, 6, 12, 0, "Pink Conrete Powder"),
	GRAY_CONCRETE_POWDER(252, 7, 12, 0, "Gray Conrete Powder"),
	LIGHT_GRAY_CONCRETE_POWDER(252, 8, 12, 0, "Light Gray Conrete Powder"),
	CYAN_CONCRETE_POWDER(252, 9, 12, 0, "Cyan Conrete Powder"),
	PURPLE_CONCRETE_POWDER(252, 10, 12, 0, "Purple Conrete Powder"),
	BLUE_CONCRETE_POWDER(252, 11, 12, 0, "Blue Conrete Powder"),
	BROWN_CONCRETE_POWDER(252, 12, 12, 0, "Brown Conrete Powder"),
	GREEN_CONCRETE_POWDER(252, 13, 12, 0, "Green Conrete Powder"),
	RED_CONCRETE_POWDER(252, 14, 12, 0, "Red Conrete Powder"),
	BLACK_CONCRETE_POWDER(252, 15, 12, 0, "Black Conrete Powder"),*/
	STRUCTURE_BLOCK(255, 137, "Structure Block"),
	DRAGON_HEAD(397, 5, 397, 0, "Dragon Head"),
	BEETROOT(434, 391, "Beetroot"),
	BEETROOT_SOUP(436, 282, "Beetroot Soup"),
	BEETROOT_SEEDS(435, 361, "Beetroot Seeds"),
	CHORUS_FRUIT(432, 392, "Chorus Fruit"),
	POPPED_CHORUS_FRUIT(433, 393, "Popped Chorus Fruit"),
	DRAGONS_BREATH(437, 373, "Dragons Breath"),
	ELYTRA(443, 299, "Elytra"),
	END_CRYSTAL(426, 410, "End Crystal"),
	LINGERING_POTION(441, 438, "Lingering Potion"),
	SHIELD(442, 425, "Shield"),
	SPECTRAL_ARROW(439, 262, "Spectral Arrow"),
	TIPPED_ARROW(440, 262, "Tipped Arrow"),
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
