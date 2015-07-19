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

public class PacketC08_BlockPlace extends AbstractPacketX implements WriteablePacket {
	private int x, y, z, direction;
	private float offsetX, offsetY, offsetZ;
	private ItemStack item;

	public PacketC08_BlockPlace(int x, int y, int z, int direction, ItemStack item, float offsetX, float offsetY, float offsetZ) {
		super(0x08, State.PLAY, Direction.UPSTREAM);

		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = direction;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;

		this.item = item;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.writeInt(x);
		out.writeByte(y);
		out.writeInt(z);
		out.writeByte(direction);
		writeItemStack(item, out);
		out.writeByte((int) (offsetX * 16F));
		out.writeByte((int) (offsetY * 16F));
		out.writeByte((int) (offsetZ * 16F));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public Direction getDirection() {
		return super.getDirection();
	}

	public ItemStack getItem() {
		return item;
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public float getOffsetZ() {
		return offsetZ;
	}
}
