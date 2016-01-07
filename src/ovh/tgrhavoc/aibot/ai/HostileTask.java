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
import ovh.tgrhavoc.aibot.world.entity.*;

public class HostileTask implements Task {
	private final MinecraftBot bot;

	private boolean active = false;

	private int attackCooldown = 0;

	public HostileTask(MinecraftBot bot) {
		this.bot = bot;
	}

	@Override
	public synchronized boolean isPreconditionMet() {
		return false;
	}

	@Override
	public synchronized boolean start(String... options) {
		active = true;
		attackCooldown = 5;
		return true;
	}

	@Override
	public synchronized void stop() {
		active = false;
	}

	@Override
	public synchronized void run() {
		MainPlayerEntity player = bot.getPlayer();
		World world = bot.getWorld();
		if(world == null || player == null)
			return;
		if(attackCooldown > 0)
			attackCooldown--;
		Entity entity = null;
		int closestDistance = Integer.MAX_VALUE;
		for(Entity e : world.getEntities()) {
			if(!(e instanceof LivingEntity) || e.equals(bot.getPlayer()))
				continue;
			int distance = player.getDistanceToSquared(e);
			if(distance < closestDistance) {
				entity = e;
				closestDistance = distance;
			}
		}
		if(closestDistance > 500)
			return;
		player.face(entity.getX(), entity.getY() + 1, entity.getZ());
		if(closestDistance > 16) {
			BlockLocation location = new BlockLocation(entity.getLocation());
			BlockLocation original = location;
			BlockLocation below = location.offset(0, -1, 0);
			while(!BlockType.getById(world.getBlockIdAt(below)).isSolid() && !world.getPathFinder().getWorldPhysics().canClimb(below)) {
				location = below;
				below = below.offset(0, -1, 0);
				if(original.getY() - location.getY() >= 5)
					return;
			}
			if(bot.hasActivity() && bot.getActivity() instanceof WalkActivity) {
				WalkActivity activity = (WalkActivity) bot.getActivity();
				if(location.getDistanceTo(activity.getTarget()) <= 3)
					return;
			}
			bot.setActivity(new WalkActivity(bot, location, true));
			return;
		} else {
			if(closestDistance < 9 && bot.hasActivity() && bot.getActivity() instanceof WalkActivity)
				bot.setActivity(null);
			if(attackCooldown > 0)
				return;
			player.hit(entity);
			attackCooldown = 5;
		}
	}

	@Override
	public synchronized boolean isActive() {
		return active;
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
		return true;
	}

	@Override
	public String getName() {
		return "AttackAll";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
}
