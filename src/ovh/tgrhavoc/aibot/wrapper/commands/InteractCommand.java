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
package ovh.tgrhavoc.aibot.wrapper.commands;

import ovh.tgrhavoc.aibot.ai.*;
import ovh.tgrhavoc.aibot.event.EventBus;
import ovh.tgrhavoc.aibot.event.protocol.client.*;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class InteractCommand extends AbstractCommand {

	public InteractCommand(MinecraftBotWrapper bot) {
		super(bot, "interact", "Interact with a block", "<hit|break|use> <x> <y> <z>", "(?i)(hit|break|use) [-]?[0-9]+ [-]?[0-9]+ [-]?[0-9]+");
	}

	@Override
	public void execute(String[] args) {
		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		int z = Integer.parseInt(args[3]);
		MainPlayerEntity player = bot.getPlayer();
		EventBus eventBus = bot.getEventBus();

		if(args[0].equalsIgnoreCase("hit")) {
			player.face(x, y, z);
			eventBus.fire(new PlayerRotateEvent(player));
			eventBus.fire(new ArmSwingEvent());
			eventBus.fire(new BlockBreakStartEvent(x, y, z, 0));
		} else if(args[0].equalsIgnoreCase("break")) // Non-blocking
			new BlockBreakActivity(bot, new BlockLocation(x, y, z));
		else if(args[0].equalsIgnoreCase("use"))
			new BlockPlaceActivity(bot, new BlockLocation(x, y, z));
	}
}
