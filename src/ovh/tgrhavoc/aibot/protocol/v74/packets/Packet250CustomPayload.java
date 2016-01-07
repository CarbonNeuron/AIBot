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

public class Packet250CustomPayload extends AbstractPacket implements
		ReadablePacket, WriteablePacket {

	public String channel;

	public int length;
	public byte data[];

	public Packet250CustomPayload() {
	}

	public void readData(DataInputStream in) throws IOException {
		channel = readString(in, 16);
		length = in.readShort();

		if(length > 0 && length < 32767) {
			data = new byte[length];
			in.readFully(data);
		}
	}

	public void writeData(DataOutputStream out) throws IOException {
		writeString(channel, out);
		out.writeShort((short) length);

		if(data != null) {
			out.write(data);
		}
	}

	public int getId() {
		return 250;
	}
}
