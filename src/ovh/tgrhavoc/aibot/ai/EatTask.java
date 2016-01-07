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
import ovh.tgrhavoc.aibot.event.EventHandler;
import ovh.tgrhavoc.aibot.event.protocol.client.ItemUseEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityStopEatingEvent;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.world.item.*;

public class EatTask implements Task {
	private static final int[] FOOD_LIST;

	static {
		FOOD_LIST = new int[] { 260, 282, 297, 319, 320, 322, 349, 350, 357, 360, 363, 364, 365, 366, 367 };
	}

	private final MinecraftBot bot;

	private boolean active;

	private int lastHealth = -1, lastHunger = -1, eatingTicks, lastSlot;

	public EatTask(MinecraftBot bot) {
		this.bot = bot;
	}

	@Override
	public boolean isPreconditionMet() {
		MainPlayerEntity player = bot.getPlayer();
		if(player == null)
			return false;
		PlayerInventory inventory = player.getInventory();
		if(lastHealth != player.getHealth() && player.getHealth() == 20)
			lastHealth = 20;
		if(lastHunger != player.getHunger() && player.getHunger() == 20)
			lastHunger = 20;
		if(player != null && (player.getHunger() < 20 && player.getHunger() != lastHunger))
			for(int i = 0; i < FOOD_LIST.length; i++)
				if(inventory.contains(FOOD_LIST[i]))
					return true;
		return false;
	}

	@Override
	public boolean start(String... options) {
		if(isPreconditionMet()) {
			active = true;
			return true;
		} else
			return false;
	}

	@Override
	public void stop() {
		active = false;
	}

	@Override
	public void run() {
		MainPlayerEntity player = bot.getPlayer();
		PlayerInventory inventory = player.getInventory();
		if(eatingTicks > 0) {
			eatingTicks--;
			if(eatingTicks == 0) {
				if(inventory.getCurrentHeldSlot() != lastSlot)
					inventory.setCurrentHeldSlot(lastSlot);
				stop();
			}
			return;
		}
		int foodIndex = -1;
		for(int i = 0; i < 36; i++) {
			ItemStack item = inventory.getItemAt(i);
			if(item == null || !isFood(item.getId()))
				continue;
			foodIndex = i;
			break;
		}
		if(foodIndex == -1 || !player.switchHeldItems(foodIndex)) {
			stop();
			return;
		}
		bot.getEventBus().fire(new ItemUseEvent(inventory.getItemAt(foodIndex)));
		eatingTicks = 32;
		lastHealth = player.getHealth();
		lastHunger = player.getHunger();
	}

	@EventHandler
	public void onEntityStopEating(EntityStopEatingEvent event) {
		if(event.getEntityId() != bot.getPlayer().getId())
			return;
		if(eatingTicks > 0) {
			MainPlayerEntity player = bot.getPlayer();
			PlayerInventory inventory = player.getInventory();
			if(inventory.getCurrentHeldSlot() != lastSlot)
				inventory.setCurrentHeldSlot(lastSlot);
			eatingTicks = 0;
		}
		if(active)
			stop();
	}

	private boolean isFood(int id) {
		for(int i = 0; i < FOOD_LIST.length; i++)
			if(FOOD_LIST[i] == id)
				return true;
		return false;
	}

	@Override
	public boolean isActive() {
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
		return "Eat";
	}

	@Override
	public String getOptionDescription() {
		return "";
	}
}
