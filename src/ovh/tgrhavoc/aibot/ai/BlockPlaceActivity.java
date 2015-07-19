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
package ovh.tgrhavoc.aibot.ai;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.event.EventBus;
import ovh.tgrhavoc.aibot.event.protocol.client.*;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.*;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.world.item.PlayerInventory;

public class BlockPlaceActivity implements Activity {
	public static final int DEFAULT_TIMEOUT = 100;

	private final MinecraftBot bot;

	private BlockLocation location;
	private int lastId, ticksWait;

	public BlockPlaceActivity(MinecraftBot bot, BlockLocation location) {
		this(bot, location, DEFAULT_TIMEOUT);
	}

	public BlockPlaceActivity(MinecraftBot bot, BlockLocation location, int timeout) {
		this(bot, location, timeout, getPlacementBlockFaceAt(bot.getWorld(), location));
	}

	public BlockPlaceActivity(MinecraftBot bot, BlockLocation location, byte face) {
		this(bot, location, DEFAULT_TIMEOUT, face);
	}

	public BlockPlaceActivity(MinecraftBot bot, BlockLocation location, int timeout, byte face) {
		this.location = location;
		lastId = bot.getWorld().getBlockIdAt(location);
		this.bot = bot;
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return;
		PlayerInventory inventory = player.getInventory();
		int originalX = location.getX(), originalY = location.getY(), originalZ = location.getZ();
		System.out.println("Placing with face: " + face);
		if(face == -1)
			return;
		location = getOffsetBlock(location, face);
		if(location == null)
			return;
		int x = location.getX(), y = location.getY(), z = location.getZ();
		player.face(x + ((originalX - x) / 2.0D) + 0.5, y + ((originalY - y) / 2.0D), z + ((originalZ - z) / 2.0D) + 0.5);
		EventBus eventBus = bot.getEventBus();
		eventBus.fire(new PlayerRotateEvent(player));
		eventBus.fire(new ArmSwingEvent());
		eventBus.fire(new BlockPlaceEvent(inventory.getCurrentHeldItem(), x, y, z, face));
		ticksWait = timeout;
	}

	@Override
	public void run() {
		if(ticksWait > 0) {
			if(lastId != bot.getWorld().getBlockIdAt(location))
				ticksWait = 1;
			ticksWait--;
		}
	}

	@Override
	public boolean isActive() {
		return ticksWait > 0;
	}

	@Override
	public void stop() {
		ticksWait = 0;
	}

	private static byte getPlacementBlockFaceAt(World world, BlockLocation location) {
		// Stack<Integer> blockFaces = new Stack<>();
		int x = location.getX(), y = location.getY(), z = location.getZ();
		if(isPlaceable(world.getBlockIdAt(x, y - 1, z)))
			return 1;
		else if(isPlaceable(world.getBlockIdAt(x, y, z + 1)))
			return 2;
		else if(isPlaceable(world.getBlockIdAt(x, y, z - 1)))
			return 3;
		else if(isPlaceable(world.getBlockIdAt(x + 1, y, z)))
			return 4;
		else if(isPlaceable(world.getBlockIdAt(x - 1, y, z)))
			return 5;
		else if(isPlaceable(world.getBlockIdAt(x, y + 1, z)))
			return 0;
		return -1;
		/*if(z > z1) {
			
		}
		if(z == z1) {
			if(isPlaceable(world.getBlockIdAt(x, y, z - 1)))
				blockFaces.push(3);
			if(isPlaceable(world.getBlockIdAt(x, y, z + 1)))
				blockFaces.push(2);
		}
		if(x == x1) {
			if(isPlaceable(world.getBlockIdAt(x - 1, y, z)))
				blockFaces.push(5);
			if(isPlaceable(world.getBlockIdAt(x + 1, y, z)))
				blockFaces.push(4);
		}
		if(z > z1) {
			if(isPlaceable(world.getBlockIdAt(x, y, z - 1)))
				blockFaces.push(3);
			if(isPlaceable(world.getBlockIdAt(x, y, z + 1)))
				blockFaces.push(2);
		} else if(z < z1) {
			if(isPlaceable(world.getBlockIdAt(x, y, z + 1)))
				blockFaces.push(2);
			if(isPlaceable(world.getBlockIdAt(x, y, z - 1)))
				blockFaces.push(3);
		}
		if(y > y1) {
			if(isPlaceable(world.getBlockIdAt(x, y - 1, z)))
				blockFaces.push(1);
			if(isPlaceable(world.getBlockIdAt(x, y + 1, z)))
				blockFaces.push(0);
		} else {
			if(isPlaceable(world.getBlockIdAt(x, y + 1, z)))
				blockFaces.push(0);
			if(isPlaceable(world.getBlockIdAt(x, y - 1, z)))
				blockFaces.push(1);
		}
		if(x > x1) {
			if(isPlaceable(world.getBlockIdAt(x - 1, y, z)))
				blockFaces.push(5);
			if(isPlaceable(world.getBlockIdAt(x + 1, y, z)))
				blockFaces.push(4);
		}
		if(x < x1) {
			if(isPlaceable(world.getBlockIdAt(x + 1, y, z)))
				blockFaces.push(4);
			if(isPlaceable(world.getBlockIdAt(x - 1, y, z)))
				blockFaces.push(5);
		}
		if(blockFaces.isEmpty())
			return -1;
		return blockFaces.pop();*/
		/*if(isPlaceable(world.getBlockIdAt(x, y - 1, z))) {
			return 1;
		} else if(isPlaceable(world.getBlockIdAt(x, y, z + 1))) {
			return 2;
		} else if(isPlaceable(world.getBlockIdAt(x + 1, y, z))) {
			return 4;
		} else if(isPlaceable(world.getBlockIdAt(x, y, z - 1))) {
			return 3;
		} else if(isPlaceable(world.getBlockIdAt(x - 1, y, z))) {
			return 5;
		} else if(isPlaceable(world.getBlockIdAt(x, y + 1, z))) {
			return 0;
		} else
			return -1;*/
	}

	private static boolean isPlaceable(int id) {
		BlockType type = BlockType.getById(id);
		return type.isPlaceable() && !type.isInteractable();
	}

	private BlockLocation getOffsetBlock(BlockLocation location, int face) {
		int x = location.getX(), y = location.getY(), z = location.getZ();
		switch(face) {
		case 0:
			y++;
			break;
		case 1:
			y--;
			break;
		case 2:
			z++;
			break;
		case 3:
			z--;
			break;
		case 4:
			x++;
			break;
		case 5:
			x--;
			break;
		default:
			return null;
		}
		return new BlockLocation(x, y, z);
	}
}
