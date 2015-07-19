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
package ovh.tgrhavoc.aibot.protocol.v4x.play.server;

import java.io.*;
import java.util.zip.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.protocol.v4x.play.server.PacketS21_ChunkData.ChunkData;

public class PacketS26_MultiChunkData extends AbstractPacketX implements ReadablePacket {
	private ChunkData[] chunks;
	private boolean skylight;

	public PacketS26_MultiChunkData() {
		super(0x26, State.PLAY, Direction.DOWNSTREAM);
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		ChunkData[] chunks = new ChunkData[in.readShort()];
		int chunkDataLength = in.readInt();
		skylight = in.readBoolean();

		byte[] compressedChunkData = new byte[chunkDataLength];
		in.readFully(compressedChunkData, 0, chunkDataLength);
		byte[] chunkData = new byte[196864 * chunks.length];
		Inflater inflater = new Inflater();
		inflater.setInput(compressedChunkData, 0, chunkDataLength);

		try {
			inflater.inflate(chunkData);
		} catch(DataFormatException exception) {
			compressedChunkData = null;
			chunkData = null;
			return;
		} finally {
			inflater.end();
		}

		int dataPosition = 0;
		for(int i = 0; i < chunks.length; i++) {
			int x = in.readInt();
			int z = in.readInt();
			int primaryBitmask = in.readShort();
			int secondaryBitmask = in.readShort();

			int primarySize = 0, secondarySize = 0;
			for(int chunkIndex = 0; chunkIndex < 16; ++chunkIndex) {
				primarySize += primaryBitmask >> chunkIndex & 1;
				secondarySize += secondaryBitmask >> chunkIndex & 1;
			}

			int dataLength = 2048 * 4 * primarySize + 256;
			dataLength += 2048 * secondarySize;

			if(skylight)
				dataLength += 2048 * primarySize;

			byte[] data = new byte[dataLength];
			System.arraycopy(chunkData, dataPosition, data, 0, dataLength);
			dataPosition += dataLength;

			chunks[i] = new ChunkData(x, z, primaryBitmask, secondaryBitmask, data);
		}
		this.chunks = chunks;
	}

	public ChunkData[] getChunks() {
		return chunks.clone();
	}

	public boolean hasSkylight() {
		return skylight;
	}
}
