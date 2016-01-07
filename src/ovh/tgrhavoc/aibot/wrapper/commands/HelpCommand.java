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

import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.wrapper.cli.CLIBotWrapper;

/*
 * @author Sayaad
 */
public class HelpCommand extends AbstractCommand {

	public HelpCommand(CLIBotWrapper bot) {
		super(bot, "help", "Displays OptionDescription and Description for all registered commands", "[command]", ".*");
	}

	@Override
	public void execute(String[] args) {
		if(args.length == 0) {
			String newLine = System.getProperty("line.separator");
			int commandName = 0, commandUsage = 0, commandDesc = 0;
			for(Command c : getBot().getCommandManager().getCommands()) {
				if(commandName < c.getName().length())
					commandName = Math.max((c.getName().length() - 12), 1);
				if(commandUsage < c.getOptionDescription().length())
					commandUsage = Math.max((c.getOptionDescription().length() - 13), 1);
				if(commandDesc < c.getDescription().length())
					commandDesc = Math.max((c.getDescription().length() - 19), 1);
			}
			System.out.println();
			for(int i = 0; i < (((commandName + 12) + (commandUsage + 13) + (commandDesc + 19)) / 2); i++)
				System.out.print("=-");
			System.out.print(newLine + String.format("%-" + commandName + "s%-" + (commandUsage + 15) + "s%-" + commandDesc + "s\n", "| Command Name", " | Command Usage", "| Command Description"));
			for(int i = 0; i < (((commandName + 12) + (commandUsage + 13) + (commandDesc + 19)) / 2); i++)
				System.out.print("=-");
			System.out.print(newLine);
			ArrayList<String> commands = new ArrayList<>();
			for(Command c : getBot().getCommandManager().getCommands()) {
				commands.add(" " + c.getName());
				System.out.println((String.format("%-" + (commandName + 13) + "s%-" + (commandUsage + 14) + "s%s\n", "| !" + c.getName(), " | " + c.getOptionDescription(), " | " + c.getDescription())));
			}
			System.out.println();
			bot.say(Util.join(toArray(commands), ","));
		} else
			for(Command c : getBot().getCommandManager().getCommands())
				if(c.getName().equalsIgnoreCase(args[0]))
					bot.say("!" + c.getName() + " " + c.getOptionDescription() + " - " + c.getDescription());
	}

	private String[] toArray(ArrayList<String> array) {
		String[] S = new String[array.size()];
		int i = 0;
		for(String s : array) {
			S[i] = s;
			i++;
		}
		return S;
	}
}