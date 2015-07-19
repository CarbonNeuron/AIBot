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

import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.ai.FollowTask;
import ovh.tgrhavoc.aibot.world.entity.*;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class FollowCommand extends AbstractCommand {

	public FollowCommand(MinecraftBotWrapper bot) {
		super(bot, "follow", "Follow a player or yourself", "[player]", "([\\w]{1,16})?");
	}

	@Override
	public void execute(String[] args) {
		FollowTask followTask = bot.getTaskManager().getTaskFor(FollowTask.class);
		if(followTask.isActive())
			followTask.stop();
		for(Entity entity : bot.getWorld().getEntities()) {
			if(entity instanceof PlayerEntity && isFollowable(args, ((PlayerEntity) entity).getName())) {
				followTask.follow(entity);
				controller.say("Now following " + (args.length > 0 ? Util.stripColors(((PlayerEntity) entity).getName()) : "you") + ".");
				return;
			}
		}
		if(args.length > 0)
			controller.say("Player " + args[0] + " not found.");
		else
			controller.say("Owner not found.");
	}

	private boolean isFollowable(String[] args, String name) {
		name = Util.stripColors(name);
		if(args.length > 0)
			return args[0].equalsIgnoreCase(name);
		for(String owner : controller.getOwners())
			if(owner.equalsIgnoreCase(name))
				return true;
		return false;
	}
}
