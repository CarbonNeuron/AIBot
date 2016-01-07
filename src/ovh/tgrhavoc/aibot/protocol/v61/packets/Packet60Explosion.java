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

import java.util.*;
import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;

public class Packet60Explosion extends AbstractPacket implements ReadablePacket {
	public double explosionX, explosionY, explosionZ;
	public float explosionSize;
	public Set<BlockLocation> destroyedBlockPositions;

	public float unknownX, unknownY, unknownZ;

	public Packet60Explosion() {
	}

	public void readData(DataInputStream in) throws IOException {
		explosionX = in.readDouble();
		explosionY = in.readDouble();
		explosionZ = in.readDouble();
		explosionSize = in.readFloat();
		int i = in.readInt();
		destroyedBlockPositions = new HashSet<BlockLocation>();
		int j = (int) explosionX;
		int k = (int) explosionY;
		int l = (int) explosionZ;

		for(int i1 = 0; i1 < i; i1++) {
			int j1 = in.readByte() + j;
			int k1 = in.readByte() + k;
			int l1 = in.readByte() + l;
			destroyedBlockPositions.add(new BlockLocation(j1, k1, l1));
		}

		unknownX = in.readFloat();
		unknownY = in.readFloat();
		unknownZ = in.readFloat();
	}

	public int getId() {
		return 60;
	}
}
