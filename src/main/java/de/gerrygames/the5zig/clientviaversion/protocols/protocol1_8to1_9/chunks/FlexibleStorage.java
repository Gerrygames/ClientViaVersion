package de.gerrygames.the5zig.clientviaversion.protocols.protocol1_8to1_9.chunks;

/*
 *source: https://github.com/Steveice10/MCProtocolLib/blob/4ed72deb75f2acb0a81d641717b7b8074730f701/src/main/java/org/spacehq/mc/protocol/data/game/chunk/FlexibleStorage.java
 */

public class FlexibleStorage {
	private final long[] data;
	private final int bitsPerEntry;
	private final int size;
	private final long maxEntryValue;

	public FlexibleStorage(int bitsPerEntry, int size) {
		this(bitsPerEntry, new long[roundToNearest(size * bitsPerEntry, 64) / 64]);
	}

	public FlexibleStorage(int bitsPerEntry, long[] data) {
		if(bitsPerEntry < 1 || bitsPerEntry > 32) {
			throw new IllegalArgumentException("BitsPerEntry cannot be outside of accepted range.");
		}

		this.bitsPerEntry = bitsPerEntry;
		this.data = data;

		this.size = this.data.length * 64 / this.bitsPerEntry;
		this.maxEntryValue = (1L << this.bitsPerEntry) - 1;
	}

	public long[] getData() {
		return this.data;
	}

	public int getBitsPerEntry() {
		return this.bitsPerEntry;
	}

	public int getSize() {
		return this.size;
	}

	public int get(int index) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}

		int bitIndex = index * this.bitsPerEntry;
		int startIndex = bitIndex / 64;
		int endIndex = ((index + 1) * this.bitsPerEntry - 1) / 64;
		int startBitSubIndex = bitIndex % 64;
		if(startIndex == endIndex) {
			return (int) (this.data[startIndex] >>> startBitSubIndex & this.maxEntryValue);
		} else {
			int endBitSubIndex = 64 - startBitSubIndex;
			return (int) ((this.data[startIndex] >>> startBitSubIndex | this.data[endIndex] << endBitSubIndex) & this.maxEntryValue);
		}
	}

	public void set(int index, int value) {
		if(index < 0 || index > this.size - 1) {
			throw new IndexOutOfBoundsException();
		}

		if(value < 0 || value > this.maxEntryValue) {
			throw new IllegalArgumentException("Value cannot be outside of accepted range.");
		}

		int bitIndex = index * this.bitsPerEntry;
		int startIndex = bitIndex / 64;
		int endIndex = ((index + 1) * this.bitsPerEntry - 1) / 64;
		int startBitSubIndex = bitIndex % 64;
		this.data[startIndex] = this.data[startIndex] & ~(this.maxEntryValue << startBitSubIndex) | ((long) value & this.maxEntryValue) << startBitSubIndex;
		if(startIndex != endIndex) {
			int endBitSubIndex = 64 - startBitSubIndex;
			this.data[endIndex] = this.data[endIndex] >>> endBitSubIndex << endBitSubIndex | ((long) value & this.maxEntryValue) >> endBitSubIndex;
		}
	}

	private static int roundToNearest(int value, int roundTo) {
		if(roundTo == 0) {
			return 0;
		} else if(value == 0) {
			return roundTo;
		} else {
			if(value < 0) {
				roundTo *= -1;
			}

			int remainder = value % roundTo;
			return remainder != 0 ? value + roundTo - remainder : value;
		}
	}
}
