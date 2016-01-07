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
package ovh.tgrhavoc.aibot.wrapper.backend;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.event.*;
import ovh.tgrhavoc.aibot.event.protocol.server.ChatReceivedEvent;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;
import ovh.tgrhavoc.aibot.wrapper.commands.CommandException;

public class ChatBackend implements Backend, EventListener {
	private final MinecraftBotWrapper bot;

	private String activator = "!";

	public ChatBackend(MinecraftBotWrapper bot) {
		this.bot = bot;
	}

	@Override
	public void enable() {
		MinecraftBot mcbot = bot.getBot();
		mcbot.getEventBus().register(this);
	}

	@Override
	public void say(String message) {
		bot.getBot().say(message);
	}

	@Override
	public void disable() {
		MinecraftBot mcbot = bot.getBot();
		mcbot.getEventBus().unregister(this);
	}

	@EventHandler
	public void onChatReceived(ChatReceivedEvent event) {
		String message = Util.stripColors(event.getMessage());
		String executor = null;
		for(String owner : bot.getOwners()) {
			int index = message.indexOf(owner);
			if(index == -1)
				continue;
			if(executor == null || index < message.indexOf(executor))
				executor = owner;
		}
		if(executor == null)
			return;
		message = message.substring(message.indexOf(executor) + executor.length());
		int index = message.indexOf(activator);
		if(index == -1)
			return;
		message = message.substring(index + activator.length());
		try {
			bot.getCommandManager().execute(message);
		} catch(CommandException e) {
			StringBuilder error = new StringBuilder("Error: ");
			if(e.getCause() != null)
				error.append(e.getCause().toString());
			else if(e.getMessage() == null)
				error.append("null");
			if(e.getMessage() != null) {
				if(e.getCause() != null)
					error.append(": ");
				error.append(e.getMessage());
			}
			bot.getBot().say(error.toString());
		}
	}

	public String getActivator() {
		return activator;
	}

	public void setActivator(String activator) {
		this.activator = activator;
	}
}
