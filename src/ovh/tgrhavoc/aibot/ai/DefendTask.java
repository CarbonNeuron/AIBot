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
import ovh.tgrhavoc.aibot.world.entity.*;
import ovh.tgrhavoc.aibot.world.item.*;

public class DefendTask implements Task {
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

	public DefendTask(MinecraftBot bot) {
		this.bot = bot;
	}

	@Override
	public synchronized boolean isPreconditionMet() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return false;
		int closestDistance = Integer.MAX_VALUE;
		for(Entity entity : bot.getWorld().getEntities()) {
			if(!entity.isDead() && entity instanceof AggressiveEntity) {
				int distance = player.getDistanceToSquared(entity);
				if(distance < closestDistance) {
					attackEntity = entity;
					closestDistance = distance;
				}
			}
		}
		if(closestDistance >= 16)
			attackEntity = null;
		return attackEntity != null;
	}

	@Override
	public synchronized boolean start(String... options) {
		attackCooldown = 1;
		return attackEntity != null;
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
		player.face(attackEntity.getX(), attackEntity.getY() + 1.5, attackEntity.getZ());
		if(attackCooldown > 0) {
			attackCooldown--;
			return;
		}
		if(!bot.getTaskManager().getTaskFor(EatTask.class).isActive())
			switchToBestSword();
		player.hit(attackEntity);
		attackCooldown = 5;
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
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return false;
		return attackEntity != null && !attackEntity.isDead() && attackEntity.getDistanceToSquared(player) < 16;
	}

	@Override
	public TaskPriority getPriority() {
		return TaskPriority.HIGH;
	}

	@Override
	public boolean isExclusive() {
		return true;
	}

	@Override
	public boolean ignoresExclusive() {
		return false;
	}

	@Override
	public String getName() {
		return "Defend";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
}
