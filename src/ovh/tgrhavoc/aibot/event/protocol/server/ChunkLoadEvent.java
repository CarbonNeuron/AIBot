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
package ovh.tgrhavoc.aibot.event.protocol.server;

import ovh.tgrhavoc.aibot.event.protocol.ProtocolEvent;

public class ChunkLoadEvent extends ProtocolEvent {
	private final int x, y, z;
	private final byte[] blocks, metadata, light, skylight, biomes;

	public ChunkLoadEvent(int x, int y, int z, byte[] blocks, byte[] metadata, byte[] light, byte[] skylight, byte[] biomes) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.blocks = blocks;
		this.metadata = metadata;
		this.light = light;
		this.skylight = skylight;
		this.biomes = biomes;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public byte[] getBlocks() {
		return blocks;
	}

	public byte[] getMetadata() {
		return metadata;
	}

	public byte[] getLight() {
		return light;
	}

	public byte[] getSkylight() {
		return skylight;
	}

	public byte[] getBiomes() {
		return biomes;
	}
}
