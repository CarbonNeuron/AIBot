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
package ovh.tgrhavoc.aibot.protocol.v5x.play.client;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class PacketC0E_ClickWindow extends AbstractPacketX implements WriteablePacket {
	private int windowId, slot, button, actionId, mode;
	private ItemStack item;

	public PacketC0E_ClickWindow(int windowId, int slot, int button, int actionId, int mode, ItemStack item) {
		super(0x0E, State.PLAY, Direction.UPSTREAM);

		this.windowId = windowId;
		this.slot = slot;
		this.button = button;
		this.actionId = actionId;
		this.mode = mode;
		this.item = item;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.writeByte(windowId);
		out.writeShort(slot);
		out.writeByte(button);
		out.writeShort(actionId);
		out.writeByte(mode);
		writeItemStack(item, out);
	}

	public int getWindowId() {
		return windowId;
	}

	public int getSlot() {
		return slot;
	}

	public int getButton() {
		return button;
	}

	public int getActionId() {
		return actionId;
	}

	public int getMode() {
		return mode;
	}

	public ItemStack getItem() {
		return item;
	}
}
