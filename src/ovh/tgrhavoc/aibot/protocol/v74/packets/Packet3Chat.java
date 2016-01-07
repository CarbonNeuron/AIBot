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

public class Packet3Chat extends AbstractPacket implements ReadablePacket, WriteablePacket {
	public static final int MAX_CHAT_LENGTH = 100;

	public String message;

	public Packet3Chat() {
	}

	public Packet3Chat(String par1Str) {
		if(par1Str.length() > MAX_CHAT_LENGTH)
			par1Str = par1Str.substring(0, MAX_CHAT_LENGTH);

		message = par1Str;
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		message = readString(in, Integer.MAX_VALUE);
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeString(message, out);
	}

	@Override
	public int getId() {
		return 3;
	}
}
