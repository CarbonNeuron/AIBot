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
import java.util.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet209SetPlayerTeam extends AbstractPacket implements
		ReadablePacket {
	private String string1 = "";
	private String string2 = "";
	private String string3 = "";
	private String string4 = "";
	private List<String> list = new ArrayList<String>();
	private int int1 = 0;
	private int int2;

	@Override
	public void readData(DataInputStream in) throws IOException {
		string1 = readString(in, 16);
		int1 = in.readByte();

		if(int1 == 0 || int1 == 2) {
			string2 = readString(in, 32);
			string3 = readString(in, 16);
			string4 = readString(in, 16);
			int2 = in.readByte();
		}

		if(int1 == 0 || int1 == 3 || int1 == 4) {
			short length = in.readShort();

			for(int i = 0; i < length; i++)
				list.add(readString(in, 16));
		}
	}

	@Override
	public int getId() {
		return 209;
	}

	public String getString1() {
		return string1;
	}

	public String getString2() {
		return string2;
	}

	public String getString3() {
		return string3;
	}

	public String getString4() {
		return string4;
	}

	public int getInt1() {
		return int1;
	}

	public int getInt2() {
		return int2;
	}

	public List<String> getList() {
		return list;
	}
}
