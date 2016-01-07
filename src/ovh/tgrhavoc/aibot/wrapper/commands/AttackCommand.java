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
import ovh.tgrhavoc.aibot.ai.AttackTask;
import ovh.tgrhavoc.aibot.world.entity.*;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class AttackCommand extends AbstractCommand {

	public AttackCommand(MinecraftBotWrapper bot) {
		super(bot, "attack", "Attack a player by name", "<name>", "[\\w]{1,16}");
	}

	@Override
	public void execute(String[] args) {
		String name = args[0];
		AttackTask attackTask = bot.getTaskManager().getTaskFor(
				AttackTask.class);
		for(Entity entity : bot.getWorld().getEntities()) {
			if(entity instanceof PlayerEntity
					&& Util.stripColors(((PlayerEntity) entity).getName())
							.equalsIgnoreCase(name)) {
				attackTask.setAttackEntity(entity);
				controller.say("Attacking "
						+ Util.stripColors(((PlayerEntity) entity).getName())
						+ "!");
				return;
			}
		}
		controller.say("Player " + name + " not found.");
	}
}
