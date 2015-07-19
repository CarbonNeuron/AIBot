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
package ovh.tgrhavoc.aibot.world.block;

import ovh.tgrhavoc.aibot.world.*;

public class Block {
	private final World world;
	private final Chunk chunk;
	private final BlockLocation location;
	private final int id, metadata;

	public Block(World world, Chunk chunk, BlockLocation location, int id,
			int metadata) {
		this.world = world;
		this.chunk = chunk;
		this.location = location;
		this.id = id;
		this.metadata = metadata;
	}

	public World getWorld() {
		return world;
	}

	public Chunk getChunk() {
		return chunk;
	}

	public BlockLocation getLocation() {
		return location;
	}

	public int getId() {
		return id;
	}

	public int getMetadata() {
		return metadata;
	}
}
