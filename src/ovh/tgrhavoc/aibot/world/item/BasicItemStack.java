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
package ovh.tgrhavoc.aibot.world.item;

import ovh.tgrhavoc.aibot.nbt.NBTTagCompound;

public class BasicItemStack implements ItemStack {
	private int id, stackSize, damage;
	private NBTTagCompound stackTagCompound;

	public BasicItemStack(int id, int stackSize, int damage) {
		this.id = id;
		this.stackSize = stackSize;
		this.damage = damage;
	}

	public int getId() {
		return id;
	}

	public int getStackSize() {
		return stackSize;
	}

	public void setStackSize(int stackSize) {
		this.stackSize = stackSize;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public NBTTagCompound getStackTagCompound() {
		return stackTagCompound;
	}

	public void setStackTagCompound(NBTTagCompound stackTagCompound) {
		this.stackTagCompound = stackTagCompound;
	}

	@Override
	public ItemStack clone() {
		return new BasicItemStack(id, stackSize, damage);
	}

	@Override
	public String toString() {
		return "ItemStack[" + id + ":" + damage + "x" + stackSize + "]";
	}
}
