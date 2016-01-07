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

import ovh.tgrhavoc.aibot.ai.WalkActivity;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.*;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class WalkCommand extends AbstractCommand {

	public WalkCommand(MinecraftBotWrapper bot) {
		super(bot, "walk", "Walk to coordinates within the loaded world, with + to indicate relative movement", "<[+]x> [y] <[+]z>", "[+]?[-]?[0-9]+ ([0-9]+ )?[+]?[-]?[0-9]+");
	}

	@Override
	public void execute(String[] args) {
		MainPlayerEntity player = bot.getPlayer();
		BlockLocation location = new BlockLocation(player.getLocation());
		boolean relativeX = args[0].charAt(0) == '+', relativeZ = args[args.length - 1].charAt(0) == '+';
		int x, y, z;

		if(relativeX)
			x = location.getX() + Integer.parseInt(args[0].substring(1));
		else
			x = Integer.parseInt(args[0]);

		if(relativeZ)
			z = location.getZ() + Integer.parseInt(args[args.length - 1].substring(1));
		else
			z = Integer.parseInt(args[args.length - 1]);

		if(args.length < 3) {
			World world = bot.getWorld();
			for(y = 256; y > 0; y--) {
				int id = world.getBlockIdAt(x, y - 1, z);
				if(BlockType.getById(id).isSolid())
					break;
			}
			if(y <= 0) {
				controller.say("No appropriate walkable y value!");
				return;
			}
		} else
			y = Integer.parseInt(args[1]);

		BlockLocation target = new BlockLocation(x, y, z);
		bot.setActivity(new WalkActivity(bot, target));
		controller.say("Walking to (" + x + ", " + y + ", " + z + ").");
	}
}
