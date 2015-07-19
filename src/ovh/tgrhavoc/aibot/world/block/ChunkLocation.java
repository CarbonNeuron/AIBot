/*******************************************************************************
 *     Copyright (C) 2015 Jordan Dalton (jordan.8474@gmail.com)
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License along
 *     with this program; if not, write to the Free Software Foundation, Inc.,
 *     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *******************************************************************************/
package ovh.tgrhavoc.aibot.world.block;

import ovh.tgrhavoc.aibot.world.WorldLocation;

public final class ChunkLocation {
	private final int x, y, z, hashcode;
	private final String string;

	public ChunkLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		string = "Chunk[" + x + "," + y + "," + z + "]";
		hashcode = string.hashCode();
	}

	public ChunkLocation(BlockLocation location) {
		this(location.getX() >> 4, location.getY() >> 4, location.getZ() >> 4);
	}

	public ChunkLocation(WorldLocation worldLocation) {
		this((int) (worldLocation.getX()) >> 4,
				(int) (worldLocation.getY()) >> 4,
				(int) (worldLocation.getZ()) >> 4);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ChunkLocation))
			return false;
		ChunkLocation location = (ChunkLocation) obj;
		return location.getX() == x && location.getY() == y
				&& location.getZ() == z;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public int hashCode() {
		return hashcode;
	}
}
