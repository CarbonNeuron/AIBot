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

public final class BlockLocation {
	private final int x, y, z, hashcode;
	private final String string;

	public BlockLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		string = "Block[" + x + "," + y + "," + z + "]";
		hashcode = string.hashCode();
	}

	public BlockLocation(WorldLocation worldLocation) {
		x = (int) Math.floor(worldLocation.getX());
		y = (int) Math.floor(worldLocation.getY());
		z = (int) Math.floor(worldLocation.getZ());
		string = "Block[" + x + "," + y + "," + z + "]";
		hashcode = string.hashCode();
	}

	public BlockLocation(ChunkLocation location) {
		x = location.getX() << 4;
		y = location.getY() << 4;
		z = location.getZ() << 4;
		string = "Block[" + x + "," + y + "," + z + "]";
		hashcode = string.hashCode();
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

	public double getDistanceTo(BlockLocation other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2)
				+ Math.pow(z - other.z, 2));
	}

	public int getDistanceToSquared(BlockLocation other) {
		return (int) (Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math
				.pow(z - other.z, 2));
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof BlockLocation))
			return false;
		BlockLocation location = (BlockLocation) obj;
		return location.getX() == x && location.getY() == y
				&& location.getZ() == z;
	}

	public BlockLocation offset(BlockLocation location) {
		return offset(location.x, location.y, location.z);
	}

	public BlockLocation offset(int x, int y, int z) {
		return new BlockLocation(this.x + x, this.y + y, this.z + z);
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
