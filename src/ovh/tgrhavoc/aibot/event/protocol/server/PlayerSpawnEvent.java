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

import ovh.tgrhavoc.aibot.IntHashMap;
import ovh.tgrhavoc.aibot.world.entity.WatchableObject;
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class PlayerSpawnEvent extends MetaEntitySpawnEvent {
	private final String playerName;
	private final ItemStack heldItem;

	public PlayerSpawnEvent(int playerId, String playerName, ItemStack heldItem, RotatedSpawnLocation location, IntHashMap<WatchableObject> metadata) {
		super(playerId, location, metadata);

		this.playerName = playerName;
		this.heldItem = heldItem;
	}

	public String getPlayerName() {
		return playerName;
	}

	public ItemStack getHeldItem() {
		return heldItem;
	}
}
