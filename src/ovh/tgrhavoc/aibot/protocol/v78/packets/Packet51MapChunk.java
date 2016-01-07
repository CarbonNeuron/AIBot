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
import java.util.zip.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet51MapChunk extends AbstractPacket implements ReadablePacket {
	public int x;
	public int z;
	public int bitmask;
	public int additionalBitmask;
	public boolean biomes;
	public byte[] chunkData;

	public Packet51MapChunk() {
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		x = in.readInt();
		z = in.readInt();
		biomes = in.readBoolean();
		bitmask = in.readShort();
		additionalBitmask = in.readShort();
		int tempLength = in.readInt();

		byte[] compressedChunkData = new byte[tempLength];
		in.readFully(compressedChunkData, 0, tempLength);
		int i = 0;

		for(int j = 0; j < 16; j++)
			i += bitmask >> j & 1;

		int k = 12288 * i;

		if(biomes)
			k += 256;

		chunkData = new byte[k];
		Inflater inflater = new Inflater();
		inflater.setInput(compressedChunkData, 0, tempLength);

		try {
			inflater.inflate(chunkData);
		} catch(DataFormatException dataformatexception) {
			chunkData = null;
		} catch(OutOfMemoryError error) {
			System.gc();
			try {
				inflater.end();

				inflater = new Inflater();
				inflater.setInput(compressedChunkData, 0, tempLength);

				inflater.inflate(chunkData);
			} catch(DataFormatException dataformatexception) {
				chunkData = null;
			} catch(OutOfMemoryError error2) {
				chunkData = null;
			}
		} finally {
			inflater.end();
		}
	}

	@Override
	public int getId() {
		return 51;
	}
}
