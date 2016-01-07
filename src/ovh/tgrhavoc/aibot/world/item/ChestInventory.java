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
package ovh.tgrhavoc.aibot.world.item;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.event.EventHandler;
import ovh.tgrhavoc.aibot.event.protocol.client.*;
import ovh.tgrhavoc.aibot.event.protocol.server.WindowCloseEvent;

public class ChestInventory implements Inventory {
	private final MinecraftBot bot;
	private final ItemStack[] items;
	private final ItemStack[] inventory = new ItemStack[36];
	private final int id;

	private ItemStack selectedItem = null;

	public ChestInventory(MinecraftBot bot, int id, boolean large) {
		this.bot = bot;
		this.id = id;
		items = new ItemStack[large ? 54 : 27];
	}

	@EventHandler
	public synchronized void onWindowClose(WindowCloseEvent event) {
		if(id == event.getWindowId())
			selectedItem = null;
	}

	@Override
	public synchronized int getSize() {
		return items.length;
	}

	@Override
	public synchronized ItemStack getItemAt(int slot) {
		return slot < items.length ? items[slot] : inventory[slot - items.length];
	}

	@Override
	public synchronized void setItemAt(int slot, ItemStack item) {
		System.out.println("Set chest item at " + slot + ": " + item);
		if(slot < items.length)
			items[slot] = item;
		else
			inventory[slot - items.length] = item;
	}

	@Override
	public void setItemFromServerAt(int serverSlot, ItemStack item) {
		setItemAt(serverSlot, item);
	}

	public synchronized void selectItemAt(int slot) {
		selectItemAt(slot, true);
	}

	@Override
	public synchronized void selectItemAt(int slot, boolean leftClick) {
		delay();
		ItemStack item = getItemAt(slot);
		ItemStack oldSelected = selectedItem;
		if(leftClick) {
			if(selectedItem != null) {
				if(item != null) {
					if(item.getId() == selectedItem.getId()) {
						if(item.getStackSize() != 64) {
							int newStackSize = item.getStackSize() + selectedItem.getStackSize();
							item.setStackSize(Math.min(64, newStackSize));
							newStackSize -= 64;
							if(newStackSize > 0)
								selectedItem.setStackSize(newStackSize);
							else
								selectedItem = null;
						}
					} else {
						setItemAt(slot, selectedItem);
						selectedItem = item;
					}
				} else {
					setItemAt(slot, selectedItem);
					selectedItem = null;
				}
			} else if(item != null) {
				setItemAt(slot, null);
				selectedItem = item;
			}
		} else {
			if(selectedItem != null) {
				if(item != null) {
					if(item.getId() == selectedItem.getId()) {
						if(item.getStackSize() != 64) {
							item.setStackSize(item.getStackSize() + 1);
							if(selectedItem.getStackSize() > 1)
								selectedItem.setStackSize(selectedItem.getStackSize() - 1);
							else
								selectedItem = null;
						}
					} else {
						setItemAt(slot, selectedItem);
						selectedItem = item;
					}
				} else {
					ItemStack newItem = selectedItem.clone();
					newItem.setStackSize(1);
					setItemAt(slot, newItem);
					if(selectedItem.getStackSize() > 1)
						selectedItem.setStackSize(selectedItem.getStackSize() - 1);
					else
						selectedItem = null;
				}
			} else if(item != null) {
				if(item.getStackSize() == 1) {
					selectedItem = item;
					setItemAt(slot, null);
				} else {
					int stackSize = item.getStackSize();
					item.setStackSize(stackSize / 2);
					ItemStack newSelectedItem = item.clone();
					newSelectedItem.setStackSize(newSelectedItem.getStackSize() + (stackSize % 2));
					selectedItem = newSelectedItem;
				}
			}
		}
		System.out.println("Clicked at " + slot + " | left: " + leftClick + " item: " + item + " selected: " + oldSelected);
		bot.getEventBus().fire(new InventoryChangeEvent(this, slot, leftClick ? 0 : 1, (short) 0, item, false));
	}

	public synchronized void selectArmorAt(int slot) {
		selectItemAt(slot + 36, true);
	}

	public synchronized void selectCraftingAt(int slot) {
		selectCraftingAt(slot, true);
	}

	public synchronized void selectCraftingAt(int slot, boolean leftClick) {
		selectItemAt(slot + 40, leftClick);
	}

	@Override
	public synchronized void selectItemAtWithShift(int slot) {
		delay();
		ItemStack item = getItemAt(slot);
		int rangeStart, rangeEnd;
		if(item == null)
			return;
		if(slot < items.length) {
			rangeStart = items.length;
			rangeEnd = items.length + inventory.length;
		} else {
			rangeStart = 0;
			rangeEnd = items.length;
		}
		boolean slotFound = false;
		for(int i = rangeStart; i < rangeEnd; i++) {
			if(items[i] == null) {
				if(slot < items.length) {
					items[slot] = null;
					inventory[slot - items.length] = item;
				} else {
					items[i] = item;
					inventory[slot - items.length] = null;
				}
				slotFound = true;
				break;
			}
		}
		if(!slotFound)
			return;
		bot.getEventBus().fire(new InventoryChangeEvent(this, slot, 0, (short) 0, item, true));
	}

	@Override
	public synchronized ItemStack getSelectedItem() {
		return selectedItem;
	}

	@Override
	public synchronized void dropSelectedItem() {
		delay();
		selectedItem = null;
		bot.getEventBus().fire(new InventoryChangeEvent(this, -999, 0, (short) 0, null, true));
	}

	@Override
	public synchronized void close() {
		bot.getEventBus().fire(new InventoryCloseEvent(this));
	}

	private void delay() {
		int delay = bot.getPlayer().getInventory().getDelay();
		if(delay > 0) {
			try {
				Thread.sleep(delay);
			} catch(InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}

	public MinecraftBot getBot() {
		return bot;
	}

	@Override
	public int getWindowId() {
		return id;
	}
}
