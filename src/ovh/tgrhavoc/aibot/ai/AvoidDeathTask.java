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
package ovh.tgrhavoc.aibot.ai;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.protocol.ConnectionHandler;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;

public class AvoidDeathTask implements Task {
	private final MinecraftBot bot;

	private boolean enabled;
	private int lastHealth;

	public AvoidDeathTask(MinecraftBot bot) {
		this.bot = bot;
	}

	@Override
	public boolean isPreconditionMet() {
		return enabled;
	}

	@Override
	public boolean start(String... options) {
		enabled = true;
		return true;
	}

	@Override
	public void stop() {
		enabled = false;
		lastHealth = 0;
	}

	@Override
	public void run() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null) {
			lastHealth = 0;
			return;
		}
		if(lastHealth == 0) {
			lastHealth = player.getHealth();
			return;
		}
		if(player.getHealth() < lastHealth) {
			ConnectionHandler connectionHandler = bot.getConnectionHandler();
			connectionHandler.disconnect("Damaged! Disconnecting! Position: "
					+ player.getLocation());
			enabled = false;
		}
	}

	@Override
	public boolean isActive() {
		return enabled;
	}

	@Override
	public TaskPriority getPriority() {
		return TaskPriority.HIGHEST;
	}

	@Override
	public boolean isExclusive() {
		return false;
	}

	@Override
	public boolean ignoresExclusive() {
		return true;
	}

	@Override
	public String getName() {
		return "Avoid Death";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
}
