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

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import ovh.tgrhavoc.aibot.event.EventListener;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class BasicCommandManager implements CommandManager {
	private final MinecraftBotWrapper bot;
	private final List<Command> commands = new CopyOnWriteArrayList<>();

	public BasicCommandManager(MinecraftBotWrapper bot) {
		this.bot = bot;
	}

	@Override
	public void register(Command command) {
		commands.add(command);
		if(command instanceof EventListener)
			bot.getBot().getEventBus().register((EventListener) command);
	}

	@Override
	public void unregister(Command command) {
		if(command instanceof EventListener)
			bot.getBot().getEventBus().unregister((EventListener) command);
		commands.remove(command);
	}

	@Override
	public void execute(String descriptor) throws CommandException {
		descriptor = descriptor.trim();
		int spaceIndex = descriptor.indexOf(' ');
		String name, args = "";
		if(spaceIndex != -1)
			args = descriptor.substring(spaceIndex + 1, descriptor.length());
		else
			spaceIndex = descriptor.length();
		name = descriptor.substring(0, spaceIndex);

		String[] argsSplit = new String[0];
		if(!args.isEmpty()) {
			List<String> argsList = new ArrayList<>();
			boolean quoted = false, escaped = false;
			char last = ' ';
			StringBuilder part = null;
			for(char c : args.toCharArray()) {
				if(part == null && last == ' ' && c != ' ') {
					if(c == '\"') {
						quoted = true;
						part = new StringBuilder();
					} else if(c == '\\') {
						escaped = true;
						part = new StringBuilder();
					} else
						part = new StringBuilder().append(c);
				} else if(part != null) {
					if(c == '"' && !escaped) {
						if(quoted) {
							quoted = false;
							argsList.add(part.toString());
							part = null;
						} else if(last != '\\')
							throw new CommandException("Syntax error.");
					} else if(c == '\\' && !escaped) {
						escaped = true;
					} else if(escaped && c != '"' && c != '\\') {
						throw new CommandException("Syntax error.");
					} else if(c == ' ') {
						if(!quoted) {
							argsList.add(part.toString());
							part = null;
						} else
							part.append(c);
					} else {
						escaped = false;
						part.append(c);
					}
				} else if(c != ' ')
					throw new CommandException("Syntax error.");
				last = c;
			}
			if(part != null)
				argsList.add(part.toString());
			if(quoted || escaped)
				throw new CommandException("Syntax error.");
			argsSplit = argsList.toArray(argsSplit);
			if(argsSplit.length > 0) {
				StringBuilder argsBuilder = new StringBuilder(argsSplit[0]);
				for(int i = 1; i < argsSplit.length; i++)
					argsBuilder.append(' ').append(argsSplit[i]);
				args = argsBuilder.toString();
			} else
				args = "";
		}

		for(Command command : commands) {
			if(!name.equalsIgnoreCase(command.getName()))
				continue;
			if(!args.matches(command.getOptionRegex()))
				throw new CommandException(command, "Incorrect command syntax.");
			try {
				command.execute(argsSplit);
			} catch(Exception exception) {
				throw new CommandException(command, exception);
			}
			break;
		}
	}

	@Override
	public Command[] getCommands() {
		return commands.toArray(new Command[commands.size()]);
	}

	public MinecraftBotWrapper getBot() {
		return bot;
	}
}
