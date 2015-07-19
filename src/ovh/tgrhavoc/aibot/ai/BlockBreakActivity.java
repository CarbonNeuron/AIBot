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

public class BlockBreakActivity implements Activity {
	private final MinecraftBot bot;

	private BlockLocation location;
	private int lastId, timeout, wait;

	public BlockBreakActivity(MinecraftBot bot, BlockLocation location) {
		this(bot, location, 10 * 20);
	}

	public BlockBreakActivity(MinecraftBot bot, BlockLocation location, int timeout) {
		this.location = location;
		lastId = bot.getWorld().getBlockIdAt(location);
		this.bot = bot;
		int x = location.getX(), y = location.getY(), z = location.getZ();
		MainPlayerEntity player = bot.getPlayer();
		World world = bot.getWorld();
		if(player == null)
			return;
		int face = getBreakBlockFaceAt(location);
		if(face == -1)
			return;
		player.face(x + 0.5, y + 0.5, z + 0.5);
		int idAbove = world.getBlockIdAt(x, y + 1, z);
		if(idAbove == 12 || idAbove == 13)
			wait = 30;
		player.switchTools(BlockType.getById(world.getBlockIdAt(location)).getToolType());
		EventBus eventBus = bot.getEventBus();
		eventBus.fire(new PlayerRotateEvent(player));
		eventBus.fire(new ArmSwingEvent());
		eventBus.fire(new BlockBreakStartEvent(x, y, z, face));
		eventBus.fire(new BlockBreakCompleteEvent(x, y, z, face));
		this.timeout = timeout;
	}

	@Override
	public void run() {
		if(timeout > 0) {
			if(lastId != bot.getWorld().getBlockIdAt(location))
				timeout = 1;
			timeout--;
		} else if(wait > 0)
			wait--;
	}

	@Override
	public boolean isActive() {
		return timeout > 0 || wait > 0;
	}

	@Override
	public void stop() {
		wait = 0;
		timeout = 0;
	}

	private int getBreakBlockFaceAt(BlockLocation location) {
		int x = location.getX(), y = location.getY(), z = location.getZ();
		World world = bot.getWorld();
		if(isEmpty(world.getBlockIdAt(x - 1, y, z)))
			return 4;
		else if(isEmpty(world.getBlockIdAt(x, y, z + 1)))
			return 3;
		else if(isEmpty(world.getBlockIdAt(x, y, z - 1)))
			return 2;
		else if(isEmpty(world.getBlockIdAt(x, y + 1, z)))
			return 1;
		else if(isEmpty(world.getBlockIdAt(x, y - 1, z)))
			return 0;
		else if(isEmpty(world.getBlockIdAt(x + 1, y, z)))
			return 5;
		return -1;
	}

	private boolean isEmpty(int id) {
		BlockType type = BlockType.getById(id);
		return !type.isSolid() && !type.isInteractable() && !type.isPlaceable();
	}
}
