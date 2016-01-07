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
package ovh.tgrhavoc.aibot.protocol.v74.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet106Transaction extends AbstractPacket implements
		ReadablePacket, WriteablePacket {
	public int windowId;
	public short shortWindowId;
	public boolean accepted;

	public Packet106Transaction() {
	}

	public Packet106Transaction(int par1, short par2, boolean par3) {
		windowId = par1;
		shortWindowId = par2;
		accepted = par3;
	}

	public void readData(DataInputStream in) throws IOException {
		windowId = in.readByte();
		shortWindowId = in.readShort();
		accepted = in.readByte() != 0;
	}

	public void writeData(DataOutputStream out) throws IOException {
		out.writeByte(windowId);
		out.writeShort(shortWindowId);
		out.writeByte(accepted ? 1 : 0);
	}

	public int getId() {
		return 106;
	}
}
