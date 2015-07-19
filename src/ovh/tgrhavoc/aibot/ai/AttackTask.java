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
import ovh.tgrhavoc.aibot.world.item.*;

public class AttackTask implements Task {
	private static final int[] SWORDS = new int[3200];

	private final MinecraftBot bot;

	private Entity attackEntity;

	private int attackCooldown = 0;

	static {
		SWORDS[268] = 1;
		SWORDS[272] = 2;
		SWORDS[276] = 4;
		SWORDS[283] = 3;
	}

	public AttackTask(MinecraftBot bot) {
		this.bot = bot;
	}

	public Entity getAttackEntity() {
		return attackEntity;
	}

	public void setAttackEntity(Entity attackEntity) {
		this.attackEntity = attackEntity;
	}

	@Override
	public synchronized boolean isPreconditionMet() {
		return attackEntity != null;
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
					attackEntity = entity;
		}
		if(attackEntity == null)
			return false;
		attackCooldown = 5;
		return true;
	}

	@Override
	public synchronized void stop() {
		attackEntity = null;
	}

	@Override
	public synchronized void run() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return;
		if(player.getDistanceTo(attackEntity) > 4) {
			BlockLocation location = new BlockLocation(attackEntity.getLocation());
			World world = bot.getWorld();
			BlockLocation original = location;
			BlockLocation below = location.offset(0, -1, 0);
			while(!BlockType.getById(world.getBlockIdAt(below)).isSolid() && !world.getPathFinder().getWorldPhysics().canClimb(below)) {
				location = below;
				below = below.offset(0, -1, 0);
				if(original.getY() - location.getY() >= 5)
					return;
			}
			bot.setActivity(new WalkActivity(bot, location, true));
			return;
		} else {
			if(attackCooldown > 0) {
				attackCooldown--;
				return;
			}
			if(!bot.getTaskManager().getTaskFor(EatTask.class).isActive())
				switchToBestSword();
			player.hit(attackEntity);
			attackCooldown = 5;
		}
	}

	private void switchToBestSword() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return;
		PlayerInventory inventory = player.getInventory();
		ItemStack bestTool = null;
		int bestToolSlot = -1, bestToolValue = -1;
		for(int i = 0; i < 36; i++) {
			ItemStack item = inventory.getItemAt(i);
			if(item != null) {
				int toolValue = SWORDS[item.getId()];
				if(bestTool != null ? toolValue > bestToolValue : toolValue > 0) {
					bestTool = item;
					bestToolSlot = i;
					bestToolValue = toolValue;
				}
			}
		}
		if(bestTool != null) {
			if(inventory.getCurrentHeldSlot() != bestToolSlot) {
				if(bestToolSlot > 8) {
					int hotbarSpace = 9;
					for(int hotbarIndex = 0; hotbarIndex < 9; hotbarIndex++) {
						if(inventory.getItemAt(hotbarIndex) == null) {
							hotbarSpace = hotbarIndex;
							break;
						}
					}
					if(hotbarSpace == 9)
						return;
					inventory.selectItemAt(bestToolSlot);
					inventory.selectItemAt(hotbarSpace);
					if(inventory.getSelectedItem() != null)
						inventory.selectItemAt(bestToolSlot);
					inventory.close();
					bestToolSlot = hotbarSpace;
				}
				inventory.setCurrentHeldSlot(bestToolSlot);
			}
		}
	}

	@Override
	public synchronized boolean isActive() {
		boolean active = attackEntity != null && !attackEntity.isDead();
		if(active) {
			MainPlayerEntity player = bot.getPlayer();
			if(player == null)
				return true;
			player.face(attackEntity.getX(), attackEntity.getY() + 1.5, attackEntity.getZ());
			Activity activity = bot.getActivity();
			if(activity == null || !(activity instanceof WalkActivity))
				return active;
			WalkActivity walkActivity = (WalkActivity) activity;
			if(walkActivity.isActive() && (player.getDistanceTo(attackEntity) < 3 || attackEntity.getDistanceTo(walkActivity.getTarget()) > 3)
					&& player.isOnGround())
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
		return "Attack";
	}

	@Override
	public String getOptionDescription() {
		return "[player]";
	}
}
