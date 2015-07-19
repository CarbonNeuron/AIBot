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
package ovh.tgrhavoc.aibot.event.protocol.client;

import ovh.tgrhavoc.aibot.world.block.BlockLocation;
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class BlockPlaceEvent extends BlockFaceEvent {
	private final ItemStack item;
	private final float xOffset, yOffset, zOffset;

	public BlockPlaceEvent(ItemStack item, BlockLocation location, int face) {
		this(item, location, face, 0F, 0F, 0F);
	}

	public BlockPlaceEvent(ItemStack item, BlockLocation location, int face, float xOffset, float yOffset, float zOffset) {
		super(location, face);

		this.item = item;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	public BlockPlaceEvent(ItemStack item, int x, int y, int z, int face) {
		this(item, x, y, z, face, 0F, 0F, 0F);
	}

	public BlockPlaceEvent(ItemStack item, int x, int y, int z, int face, float xOffset, float yOffset, float zOffset) {
		super(x, y, z, face);

		this.item = item;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.zOffset = zOffset;
	}

	public ItemStack getItem() {
		return item;
	}

	public float getXOffset() {
		return xOffset;
	}

	public float getYOffset() {
		return yOffset;
	}

	public float getZOffset() {
		return zOffset;
	}
}
