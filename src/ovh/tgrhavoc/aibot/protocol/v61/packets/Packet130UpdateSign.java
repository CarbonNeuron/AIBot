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
package ovh.tgrhavoc.aibot.protocol.v61.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet130UpdateSign extends AbstractPacket implements
		ReadablePacket, WriteablePacket {
	public int x;
	public int y;
	public int z;
	public String[] text;

	public Packet130UpdateSign() {
	}

	public Packet130UpdateSign(int x, int y, int z, String[] text) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.text = text;
	}

	public void readData(DataInputStream in) throws IOException {
		x = in.readInt();
		y = in.readShort();
		z = in.readInt();
		text = new String[4];

		for(int i = 0; i < 4; i++)
			text[i] = readString(in, 15);
	}

	public void writeData(DataOutputStream out) throws IOException {
		out.writeInt(x);
		out.writeShort(y);
		out.writeInt(z);

		for(int i = 0; i < 4; i++)
			writeString(text[i], out);
	}

	public int getId() {
		return 130;
	}
}
