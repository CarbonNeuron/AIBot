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
import java.util.zip.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet56MapChunks extends AbstractPacket implements ReadablePacket {
	public int[] chunkX;
	public int[] chunkZ;
	public int[] primaryBitmap;
	public int[] secondaryBitmap;
	public byte[][] chunkData;
	public int chunkDataLength;
	public boolean skylight;

	public Packet56MapChunks() {
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		short chunkLength = in.readShort();
		chunkDataLength = in.readInt();
		skylight = in.readBoolean();
		chunkX = new int[chunkLength];
		chunkZ = new int[chunkLength];
		primaryBitmap = new int[chunkLength];
		secondaryBitmap = new int[chunkLength];
		chunkData = new byte[chunkLength][];

		byte[] compressedChunkData = new byte[chunkDataLength];
		in.readFully(compressedChunkData, 0, chunkDataLength);
		byte[] chunkData = new byte[196864 * chunkLength];
		Inflater inflater = new Inflater();
		inflater.setInput(compressedChunkData, 0, chunkDataLength);

		try {
			inflater.inflate(chunkData);
		} catch(DataFormatException var11) {
			chunkData = null;
			// throw new IOException("Bad compressed data format");
		} finally {
			inflater.end();
		}

		if(chunkData == null)
			return;

		int index = 0;

		for(int var6 = 0; var6 < chunkLength; ++var6) {
			chunkX[var6] = in.readInt();
			chunkZ[var6] = in.readInt();
			primaryBitmap[var6] = in.readShort();
			secondaryBitmap[var6] = in.readShort();
			int primarySize = 0, secondarySize = 0;

			for(int chunkIndex = 0; chunkIndex < 16; ++chunkIndex) {
				primarySize += primaryBitmap[var6] >> chunkIndex & 1;
				secondarySize += secondaryBitmap[var6] >> chunkIndex & 1;
			}

			int dataLength = 2048 * 4 * primarySize + 256;
			dataLength += 2048 * secondarySize;

			if(skylight)
				dataLength += 2048 * primarySize;

			this.chunkData[var6] = new byte[dataLength];
			System.arraycopy(chunkData, index, this.chunkData[var6], 0, dataLength);
			index += dataLength;
		}
	}

	@Override
	public int getId() {
		return 56;
	}
}