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
package ovh.tgrhavoc.aibot.protocol.v78.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet54PlayNoteBlock extends AbstractPacket implements
		ReadablePacket {
	public int xLocation;
	public int yLocation;
	public int zLocation;

	public int instrumentType;
	public int pitch;
	public int blockId;

	public Packet54PlayNoteBlock() {
	}

	public void readData(DataInputStream in) throws IOException {
		xLocation = in.readInt();
		yLocation = in.readShort();
		zLocation = in.readInt();
		instrumentType = in.read();
		pitch = in.read();
		blockId = in.readShort() & 0xfff;
	}

	public int getId() {
		return 54;
	}
}
