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
package ovh.tgrhavoc.aibot.world;

import ovh.tgrhavoc.aibot.world.block.*;

public final class WorldLocation {
	private final double x, y, z;

	public WorldLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public WorldLocation(BlockLocation location) {
		x = location.getX() + 0.5;
		y = location.getY();
		z = location.getZ() + 0.5;
	}

	public WorldLocation(ChunkLocation location) {
		x = location.getX() * 16;
		y = location.getY() * 16;
		z = location.getZ() * 16;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof WorldLocation))
			return false;
		WorldLocation location = (WorldLocation) obj;
		return location.getX() == x && location.getY() == y && location.getZ() == z;
	}

	@Override
	public String toString() {
		return "WorldLocation[" + x + "," + y + "," + z + "]";
	}

	public double getDistanceTo(WorldLocation other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2));
	}

	public double getDistanceToSquared(WorldLocation other) {
		return Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2) + Math.pow(z - other.z, 2);
	}

	public WorldLocation offset(WorldLocation location) {
		return offset(location.x, location.y, location.z);
	}

	public WorldLocation offset(double x, double y, double z) {
		return new WorldLocation(this.x + x, this.y + y, this.z + z);
	}
}
