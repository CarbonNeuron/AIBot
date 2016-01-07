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

public class Packet208SetDisplayObjective extends AbstractPacket implements
		ReadablePacket {
	private int int1;
	private String string1;

	@Override
	public void readData(DataInputStream in) throws IOException {
		int1 = in.readByte();
		string1 = readString(in, 16);
	}

	@Override
	public int getId() {
		return 208;
	}

	public int getInt1() {
		return int1;
	}

	public String getString1() {
		return string1;
	}
}
