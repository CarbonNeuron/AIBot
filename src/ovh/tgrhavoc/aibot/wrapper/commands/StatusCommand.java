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
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class StatusCommand extends AbstractCommand {

	public StatusCommand(MinecraftBotWrapper bot) {
		super(bot, "status", "Display bot state information");
	}

	@Override
	public void execute(String[] args) {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return;

		controller.say("Health: [" + player.getHealth() + "/20] Hunger: [" + player.getHunger() + "/20] Level " + player.getExperienceLevel() + " (" + player.getExperienceTotal() + " total exp.)");
		try {
			StringBuilder activeTasks = new StringBuilder();
			TaskManager manager = bot.getTaskManager();
			for(Task task : manager.getRegisteredTasks()) {
				if(task.isActive()) {
					if(activeTasks.length() > 0)
						activeTasks.append(", ");
					activeTasks.append(task.getClass().getSimpleName());
				}
			}
			bot.say("Active tasks: [" + activeTasks + "]");
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
}
