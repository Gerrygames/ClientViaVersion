package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_9to1_8.items;

import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.IntTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.ListTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;
import us.myles.ViaVersion.api.minecraft.item.Item;

import java.util.Arrays;
import java.util.HashMap;

public class ItemRewriter {

	private static final HashMap<Integer, Double> WEAPON_DAMAGES = new HashMap<>();

	static {
		/* Weapons*/
		WEAPON_DAMAGES.put(276, 7.0);
		WEAPON_DAMAGES.put(267, 6.0);
		WEAPON_DAMAGES.put(272, 5.0);
		WEAPON_DAMAGES.put(283, 4.0);
		WEAPON_DAMAGES.put(268, 4.0);

		WEAPON_DAMAGES.put(278, 5.0);
		WEAPON_DAMAGES.put(257, 4.0);
		WEAPON_DAMAGES.put(274, 3.0);
		WEAPON_DAMAGES.put(270, 2.0);
		WEAPON_DAMAGES.put(285, 2.0);

		WEAPON_DAMAGES.put(279, 6.0);
		WEAPON_DAMAGES.put(258, 5.0);
		WEAPON_DAMAGES.put(275, 4.0);
		WEAPON_DAMAGES.put(271, 3.0);
		WEAPON_DAMAGES.put(286, 3.0);

		WEAPON_DAMAGES.put(277, 4.0);
		WEAPON_DAMAGES.put(256, 3.0);
		WEAPON_DAMAGES.put(273, 2.0);
		WEAPON_DAMAGES.put(269, 1.0);
		WEAPON_DAMAGES.put(284, 1.0);

		WEAPON_DAMAGES.put(293, 1.0);
		WEAPON_DAMAGES.put(292, 1.0);
		WEAPON_DAMAGES.put(291, 1.0);
		WEAPON_DAMAGES.put(290, 1.0);
		WEAPON_DAMAGES.put(294, 1.0);
	}

	public static void fixWeaponToServer(Item item) {
		if (!WEAPON_DAMAGES.containsKey((int)item.getId())) return;

		CompoundTag tag = item.getTag();

		if (tag==null) return;

		if (!tag.contains("HideFlags") || (((int)tag.get("HideFlags").getValue()) & 2) != 2) return;

		CompoundTag display = tag.get("display");
		if (display==null || !display.contains("Lore")) return;

		ListTag lore = display.get("Lore");
		if (lore.size()<2 || !((String)lore.get(lore.size()-1).getValue()).endsWith("Attack Damage")) return;

		lore.remove(lore.get(lore.size()-1));
		lore.remove(lore.get(lore.size()-1));
		if (lore.size()==0) {
			display.remove("Lore");
			if (display.isEmpty()) tag.remove("display");
		}

		IntTag hideFlags = tag.get("HideFlags");
		hideFlags.setValue(hideFlags.getValue() ^ 2);
		if (hideFlags.getValue()==0) tag.remove("HideFlags");

		if (tag.isEmpty()) item.setTag(null);
	}

	public static void fixWeaponToClient(Item item) {
		if (!WEAPON_DAMAGES.containsKey((int)item.getId())) return;
		CompoundTag tag = item.getTag();
		if (tag!=null && tag.contains("HideFlags") && (((int)tag.get("HideFlags").getValue()) & 2) == 2) return;
		if (tag==null) {
			tag = new CompoundTag("tag");
			item.setTag(tag);
		}
		if (tag.contains("HideFlags")) {
			IntTag hideFlags = tag.get("HideFlags");
			hideFlags.setValue(hideFlags.getValue() ^ 2);
		} else {
			tag.put(new IntTag("HideFlags", 2));
		}
		double damage = getWeaponDamage(item);
		if (damage<=1) return;
		String damageString = "§9+" + damage + " Attack Damage";
		if (!tag.contains("display")) tag.put(new CompoundTag("display"));
		CompoundTag display = tag.get("display");
		if (display.contains("Lore")) {
			ListTag lore = display.get("Lore");
			lore.add(new StringTag(null, ""));
			lore.add(new StringTag(null, damageString));
		} else {
			display.put(new ListTag("Lore", Arrays.asList(new StringTag(null, ""), new StringTag(null, damageString))));
		}
	}

	public static double getWeaponDamage(Item item) {
		double damage = WEAPON_DAMAGES.getOrDefault((int)item.getId(), 0d);
		if (damage<=1) return 1;
		if (item.getTag()!=null && item.getTag().contains("ench")) {
			ListTag ench = item.getTag().get("ench");
			for (int i = 0; i<ench.size(); i++) {
				CompoundTag tag = ench.get(i);
				if ((short)tag.get("id").getValue()==(short)16) {
					short level = (short)tag.get("lvl").getValue();
					return damage + (level >= 1 ? level * 1.25D : 0.0D);
				}
			}
		}
		return damage;
	}
}
