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
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.*;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;

public class FallTask implements Task {
	private final MinecraftBot bot;

	public FallTask(MinecraftBot bot) {
		this.bot = bot;
	}

	@Override
	public synchronized boolean isPreconditionMet() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return false;
		return !player.isOnGround();
	}

	@Override
	public synchronized boolean start(String... options) {
		return isPreconditionMet();
	}

	@Override
	public synchronized void stop() {
	}

	@Override
	public synchronized void run() {
		MainPlayerEntity player = bot.getPlayer();
		World world = bot.getWorld();
		if(player == null || world == null)
			return;
		double speed = WalkActivity.getDefaultSpeed();
		BlockLocation location = new BlockLocation(player.getLocation());
		if(player.isInLiquid())
			speed *= WalkActivity.getDefaultLiquidFactor();
		else if(!bot.getWorld().getPathFinder().getWorldPhysics().canClimb(location))
			speed *= WalkActivity.getDefaultFallFactor();
		int lowestY = location.getY();
		while(true) {
			int id = world.getBlockIdAt(location.getX(), (lowestY - 1), location.getZ());
			BlockType type = BlockType.getById(id);
			if(type.isSolid() || lowestY <= 0)
				break;
			lowestY--;
		}
		player.setY(player.getY() + Math.max(-speed, lowestY - player.getY()));
	}

	@Override
	public synchronized boolean isActive() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return false;
		return !player.isOnGround();
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
		if(bot.hasActivity() && bot.getActivity() instanceof WalkActivity)
			return false;
		return true;
	}

	@Override
	public String getName() {
		return "Fall";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
}
