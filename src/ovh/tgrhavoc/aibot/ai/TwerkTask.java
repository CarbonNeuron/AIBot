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

import java.util.Random;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;

public class TwerkTask implements Task {
	private final MinecraftBot bot;
	
	private final Random random = new Random();
	
	private boolean isActive;
	private int timer;
	
	public TwerkTask(MinecraftBot bot){
		this.bot = bot;
	}
	
	@Override
	public boolean isPreconditionMet(){
		return isActive;
	}

	@Override
	public boolean start(String... options) {
		isActive = true;
		return true;
	}

	@Override
	public void stop() {
		isActive = false;
	}

	@Override
	public void run() {
		if (timer > 0){
			timer --;
			return;
		}
		timer += 1 + random.nextInt(3);
		MainPlayerEntity player = bot.getPlayer();
		player.setCrouching(!player.isCrouching());
	}

	@Override
	public synchronized boolean isActive() {
		return isActive;
	}

	@Override
	public TaskPriority getPriority() {
		return TaskPriority.NORMAL;
	}

	@Override
	public boolean isExclusive() {
		return false;
	}

	@Override
	public boolean ignoresExclusive() {
		return false;
	}

	@Override
	public String getName() {
		return "Twerk";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
	
}
