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
import ovh.tgrhavoc.aibot.ai.FarmingTask.StorageAction;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class FarmCommand extends AbstractCommand {

	public FarmCommand(MinecraftBotWrapper bot) {
		super(bot, "farm", "Activate the farming task", "[<x1> <y1> <z1> <x2> <y2> <z2>] [STORE|sell]", "(?i)([=]?[-]?[0-9]+( [=]?[-]?[0-9]+){5})?( (sell|store))?");
	}

	@Override
	public void execute(String[] args) {
		FarmingTask task = bot.getTaskManager().getTaskFor(FarmingTask.class);
		int length = args.length;
		if(args.length > 0 && (args[args.length - 1].equalsIgnoreCase("store") || args[args.length - 1].equalsIgnoreCase("sell"))) {
			task.setStorageAction(args[args.length - 1].equalsIgnoreCase("store") ? StorageAction.STORE : StorageAction.SELL);
			length--;
		}
		for(int i = 0; i < length; i++)
			if(args[i].startsWith("="))
				args[i] = args[i].substring(1);
		task.start(args);
		controller.say("Now farming!");
	}
}
