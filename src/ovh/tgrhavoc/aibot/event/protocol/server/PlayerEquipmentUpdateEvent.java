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

public class PlayerEquipmentUpdateEvent extends EntityEvent {
	private final EquipmentSlot slot;
	private final ItemStack item;

	public PlayerEquipmentUpdateEvent(int playerId, int slot, ItemStack item) {
		this(playerId, EquipmentSlot.fromId(slot), item);
	}

	public PlayerEquipmentUpdateEvent(int playerId, EquipmentSlot slot, ItemStack item) {
		super(playerId);

		this.slot = slot;
		this.item = item;
	}

	public EquipmentSlot getSlot() {
		return slot;
	}

	public ItemStack getItem() {
		return item;
	}

	public enum EquipmentSlot {
		HELD(0),
		HELMET(1),
		CHESTPLATE(2),
		LEGGINGS(3),
		BOOTS(4);

		private final int id;

		private EquipmentSlot(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		public static EquipmentSlot fromId(int id) {
			for(EquipmentSlot slot : values())
				if(id == slot.id)
					return slot;
			return null;
		}
	}
}
