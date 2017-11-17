package org.spigotmc;

import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class SpigotDebreakifier {
	private static final boolean[] validBlocks = new boolean[3168];
	private static final int[] correctedValues = new int[198];

	public static int getCorrectedData(int id, int data) {
		if (id > 197) {
			return data;
		} else {
			if (id == 175 && data > 8) {
				data = 8;
			}

			return validBlocks[id << 4 | data] ? data : correctedValues[id] & 15;
		}
	}

	static {
		Arrays.fill(correctedValues, -1);
		InputStream in = SpigotDebreakifier.class.getResourceAsStream("/blocks.json");

		try {
			JsonArray json = (new JsonParser()).parse(new InputStreamReader(in, "UTF-8")).getAsJsonArray();

			for (JsonElement entry : json) {
				String[] parts = entry.getAsString().split(":");
				int id = Integer.parseInt(parts[0]);
				int data = Integer.parseInt(parts[1]);
				validBlocks[id << 4 | data] = true;
				if (correctedValues[id] == -1 || data < correctedValues[id]) {
					correctedValues[id] = data;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
