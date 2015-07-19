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


import java.util.ArrayList;
import java.util.List;

import ovh.tgrhavoc.aibot.event.EventHandler;
import ovh.tgrhavoc.aibot.event.EventListener;
import ovh.tgrhavoc.aibot.event.protocol.server.PlayerListRemoveEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.PlayerListUpdateEvent;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class PlayersCommand extends AbstractCommand implements EventListener {
	private final List<String> users = new ArrayList<>();

	public PlayersCommand(MinecraftBotWrapper bot) {
		super(bot, "players", "List all players on the server");
	}

	@Override
	public void execute(String[] args) {
		String players;
		synchronized(users) {
			players = users.toString();
		}
		players = players.substring(1, players.length() - 1);
		List<String> lines = new ArrayList<String>();
		String[] parts = players.split(", ");
		String current = "";
		for(int i = 0; i < parts.length; i++) {
			if(current.length() + parts[i].length() + 2 >= 100) {
				lines.add(current);
				current = parts[i] + ", ";
			} else
				current += parts[i] + ", ";
		}
		if(!current.isEmpty()) {
			current = current.substring(0, current.length() - 2);
			lines.add(current);
		}

		bot.say("Players:");
		for(String line : lines)
			bot.say(line);
	}

	@EventHandler
	public void onPlayerListUpdate(PlayerListUpdateEvent event) {
		synchronized(users) {
			if(!users.contains(event.getPlayerName()))
				users.add(event.getPlayerName());
		}
	}

	@EventHandler
	public void onPlayerListRemove(PlayerListRemoveEvent event) {
		synchronized(users) {
			users.remove(event.getPlayerName());
		}
	}
}
