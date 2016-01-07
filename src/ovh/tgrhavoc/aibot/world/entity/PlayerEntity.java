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
package ovh.tgrhavoc.aibot.world.entity;

import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class PlayerEntity extends LivingEntity {
	private String name;
	private ItemStack[] armor = new ItemStack[4];
	private ItemStack heldItem = null;

	public PlayerEntity(World world, int id, String name) {
		super(world, id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public ItemStack getWornItemAt(int slot) {
		return slot == 0 ? heldItem
				: slot > 0 && slot <= armor.length ? armor[slot - 1] : null;
	}

	@Override
	public void setWornItemAt(int slot, ItemStack item) {
		if(slot == 0)
			heldItem = item;
		else if(slot > 0 && slot <= armor.length)
			armor[slot - 1] = item;
	}
}
