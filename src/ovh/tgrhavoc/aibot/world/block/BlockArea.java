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

public final class BlockArea implements Cloneable {
	private final int x, y, z, width, length, height;
	private final BlockLocation origin, center, endpoint;

	public BlockArea(BlockLocation corner1, BlockLocation corner2) {
		this(Math.min(corner1.getX(), corner2.getX()), Math.min(corner1.getY(), corner2.getY()), Math.min(corner1.getZ(), corner2.getZ()), Math.max(corner1.getX(), corner2.getX()) - Math.min(corner1.getX(), corner2.getX()), Math.max(corner1.getY(), corner2.getY()) - Math.min(corner1.getY(), corner2.getY()), Math.max(corner1.getZ(), corner2.getZ()) - Math.min(corner1.getZ(), corner2.getZ()));
	}

	public BlockArea(BlockLocation origin, int width, int height, int length) {
		this(origin.getX(), origin.getY(), origin.getZ(), width, height, length);
	}

	public BlockArea(BlockArea area) {
		this(area.getX(), area.getY(), area.getZ(), area.getWidth(), area.getHeight(), area.getLength());
	}

	public BlockArea(int x, int y, int z, int width, int height, int length) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.length = length;
		this.height = height;

		origin = new BlockLocation(x, y, z);
		center = new BlockLocation(x + width / 2, y + height / 2, z + length / 2);
		endpoint = new BlockLocation(x + width, y + height, z + length);
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLength() {
		return length;
	}

	public BlockLocation getOrigin() {
		return origin;
	}

	public BlockLocation getCenter() {
		return center;
	}

	public BlockLocation getEndpoint() {
		return endpoint;
	}

	public BlockLocation getRandomBlockLocationInside() {
		return new BlockLocation(x + (int) (Math.random() * width), y + (int) (Math.random() * height), z + (int) (Math.random() * length));
	}

	public boolean contains(BlockLocation location) {
		return contains(location.getX(), location.getY(), location.getZ());
	}

	public boolean contains(int x, int y, int z) {
		return x >= this.x && y >= this.y && x < this.x + width && y < this.y + height && z >= this.z && z < this.z + length;
	}

	@Override
	public BlockArea clone() {
		return new BlockArea(this);
	}

	@Override
	public String toString() {
		return "BlockArea{x=" + x + ",y=" + y + ",w=" + width + ",h=" + height + "}";
	}
}
