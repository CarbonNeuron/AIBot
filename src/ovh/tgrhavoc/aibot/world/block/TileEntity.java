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

import ovh.tgrhavoc.aibot.nbt.NBTTagCompound;

public abstract class TileEntity {
	protected final BlockLocation location;

	public TileEntity(NBTTagCompound nbt) {
		this(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
	}

	public TileEntity(int x, int y, int z) {
		this(new BlockLocation(x, y, z));
	}

	public TileEntity(BlockLocation location) {
		this.location = location;
	}

	public int getX() {
		return location.getX();
	}

	public int getY() {
		return location.getY();
	}

	public int getZ() {
		return location.getZ();
	}

	public BlockLocation getLocation() {
		return location;
	}
}
