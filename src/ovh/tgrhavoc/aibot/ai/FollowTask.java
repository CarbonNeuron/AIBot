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
import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.*;
import ovh.tgrhavoc.aibot.world.entity.*;

public class FollowTask implements Task {
	private final MinecraftBot bot;
	private Entity following = null;
	private BlockLocation lastLocation;

	public FollowTask(MinecraftBot bot) {
		this.bot = bot;
	}

	public synchronized void follow(Entity entity) {
		following = entity;
	}

	public synchronized Entity following() {
		return following;
	}

	@Override
	public synchronized boolean isPreconditionMet() {
		return following != null;
	}

	@Override
	public synchronized boolean start(String... options) {
		if(options.length > 0) {
			String name = options[0];
			World world = bot.getWorld();
			if(world == null)
				return false;
			for(Entity entity : world.getEntities())
				if(entity instanceof PlayerEntity && name.equalsIgnoreCase(Util.stripColors(((PlayerEntity) entity).getName())))
					following = entity;
		}
		return following != null;
	}

	@Override
	public void stop() {
		following = null;
	}

	@Override
	public void run() {
		MainPlayerEntity player = bot.getPlayer();
		if(following == null || player == null)
			return;
		BlockLocation location = new BlockLocation(following.getLocation());
		if(lastLocation == null || !lastLocation.equals(location)) {
			lastLocation = location;
			System.out.println("Checking location " + location);
			World world = bot.getWorld();
			System.out.println(world.getChunkAt(new ChunkLocation(location)));
			BlockLocation original = location;
			BlockLocation below = location.offset(0, -1, 0);
			while(!BlockType.getById(world.getBlockIdAt(below)).isSolid() && !world.getPathFinder().getWorldPhysics().canClimb(below)) {
				location = below;
				below = below.offset(0, -1, 0);
				if(original.getY() - location.getY() >= 5)
					return;
			}
			bot.setActivity(new WalkActivity(bot, location, true));
		}
	}

	@Override
	public boolean isActive() {
		boolean active = following != null;
		if(active) {
			MainPlayerEntity player = bot.getPlayer();
			if(player == null)
				return true;
			player.face(following.getX(), following.getY() + 1, following.getZ());
			Activity activity = bot.getActivity();
			if(activity == null || !(activity instanceof WalkActivity))
				return active;
			FallTask fallTask = bot.getTaskManager().getTaskFor(FallTask.class);
			WalkActivity walkActivity = (WalkActivity) activity;
			if(walkActivity.isActive()
					&& ((player.getDistanceTo(following) < 2 && (fallTask == null || !fallTask.isPreconditionMet())) || following.getDistanceTo(walkActivity
							.getTarget()) > 3) && player.isOnGround())
				bot.setActivity(null);
		}
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
		return false;
	}

	@Override
	public String getName() {
		return "Follow";
	}

	@Override
	public String getOptionDescription() {
		return "[player]";
	}
}
