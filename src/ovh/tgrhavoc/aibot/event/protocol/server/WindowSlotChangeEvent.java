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
package ovh.tgrhavoc.aibot.event.protocol.server;

import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class WindowSlotChangeEvent extends WindowEvent {
	private final int slot;
	private final ItemStack newItem;

	public WindowSlotChangeEvent(int windowId, int slot, ItemStack newItem) {
		super(windowId);

		this.slot = slot;
		this.newItem = newItem;
	}

	public int getSlot() {
		return slot;
	}

	public ItemStack getNewItem() {
		return newItem;
	}
}
