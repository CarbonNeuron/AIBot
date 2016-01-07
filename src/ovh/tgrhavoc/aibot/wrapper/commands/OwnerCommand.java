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

import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class OwnerCommand extends AbstractCommand {

	public OwnerCommand(MinecraftBotWrapper bot) {
		super(bot, "owner", "Set, add, or remove a bot owner", "<add|remove|set> <owner>", "(?i)(add|remove|set) [\\w]{1,16}");
	}

	@Override
	public void execute(String[] args) {
		switch(args[0].toLowerCase()) {
		case "add":
			controller.addOwner(args[1]);
			controller.say("Added owner " + args[1] + ".");
			break;
		case "remove":
			if(!controller.isOwner(args[1])) {
				controller.say("No such owner " + args[1] + ".");
			} else if(controller.getOwners().length == 1) {
				controller.say("Must have at least one owner.");
			} else {
				controller.removeOwner(args[1]);
				controller.say("Removed owner " + args[1]);
			}
			break;
		case "set":
			for(String owner : controller.getOwners())
				controller.removeOwner(owner);
			controller.addOwner(args[1]);
			controller.say("Set owner to " + args[1] + ".");
		}
	}
}
