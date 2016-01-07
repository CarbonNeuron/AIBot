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

import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.world.item.*;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class EquipCommand extends AbstractCommand {

	public EquipCommand(MinecraftBotWrapper bot) {
		super(bot, "equip", "Equip armor in inventory or remove current armor");
	}

	@Override
	public void execute(String[] args) {
		MainPlayerEntity player = bot.getPlayer();
		PlayerInventory inventory = player.getInventory();
		boolean helmet = inventory.getArmorAt(0) != null;
		boolean chestplate = inventory.getArmorAt(1) != null;
		boolean leggings = inventory.getArmorAt(2) != null;
		boolean boots = inventory.getArmorAt(3) != null;
		boolean changed = false;
		for(int i = 0; i < 36; i++) {
			ItemStack item = inventory.getItemAt(i);
			if(item == null)
				continue;
			int armorSlot;
			int id = item.getId();
			if(!helmet
					&& (id == 86 || id == 298 || id == 302 || id == 306
							|| id == 310 || id == 314)) {
				armorSlot = 0;
				helmet = true;
			} else if(!chestplate
					&& (id == 299 || id == 303 || id == 307 || id == 311 || id == 315)) {
				armorSlot = 1;
				chestplate = true;
			} else if(!leggings
					&& (id == 300 || id == 304 || id == 308 || id == 312 || id == 316)) {
				armorSlot = 2;
				leggings = true;
			} else if(!boots
					&& (id == 301 || id == 305 || id == 309 || id == 313 || id == 317)) {
				armorSlot = 3;
				boots = true;
			} else if(helmet && chestplate && leggings && boots)
				break;
			else
				continue;
			inventory.selectItemAt(i);
			inventory.selectArmorAt(armorSlot);
			changed = true;
		}
		if(!changed) {
			for(int i = 0; i < 36; i++) {
				ItemStack item = inventory.getItemAt(i);
				if(item != null)
					continue;
				int armorSlot;
				if(helmet) {
					armorSlot = 0;
					helmet = false;
				} else if(chestplate) {
					armorSlot = 1;
					chestplate = false;
				} else if(leggings) {
					armorSlot = 2;
					leggings = false;
				} else if(boots) {
					armorSlot = 3;
					boots = false;
				} else if(!helmet && !chestplate && !leggings && !boots)
					break;
				else
					continue;
				inventory.selectArmorAt(armorSlot);
				inventory.selectItemAt(i);
			}
		}
		inventory.close();
		if(changed)
			controller.say("Equipped armor.");
		else
			controller.say("Removed armor.");
	}
}
