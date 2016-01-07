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

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public abstract class AbstractCommand implements Command {
	protected final MinecraftBotWrapper controller;
	protected final MinecraftBot bot;

	private final String name, description, optionDescription, optionRegex;

	public AbstractCommand(MinecraftBotWrapper bot, String name, String description) {
		this(bot, name, description, "", "");
	}

	public AbstractCommand(MinecraftBotWrapper bot, String name, String description, String optionDescription, String optionRegex) {
		controller = bot;
		this.bot = bot.getBot();
		this.name = name;
		this.description = description;
		this.optionDescription = optionDescription;
		this.optionRegex = optionRegex;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getOptionDescription() {
		return optionDescription;
	}

	@Override
	public String getOptionRegex() {
		return optionRegex;
	}

	public MinecraftBotWrapper getBot() {
		return controller;
	}
}
