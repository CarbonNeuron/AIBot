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
package ovh.tgrhavoc.aibot.world;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.world.block.Block;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;
import ovh.tgrhavoc.aibot.world.block.Chunk;
import ovh.tgrhavoc.aibot.world.block.ChunkLocation;
import ovh.tgrhavoc.aibot.world.block.TileEntity;
import ovh.tgrhavoc.aibot.world.entity.Entity;
import ovh.tgrhavoc.aibot.world.pathfinding.PathSearchProvider;

public interface World {
	public MinecraftBot getBot();

	public Block getBlockAt(int x, int y, int z);
	public Block getBlockAt(BlockLocation location);

	public int getBlockIdAt(int x, int y, int z);
	public int getBlockIdAt(BlockLocation location);

	public void setBlockIdAt(int id, int x, int y, int z);
	public void setBlockIdAt(int id, BlockLocation location);

	public int getBlockMetadataAt(int x, int y, int z);
	public int getBlockMetadataAt(BlockLocation location);

	public void setBlockMetadataAt(int metadata, int x, int y, int z);
	public void setBlockMetadataAt(int metadata, BlockLocation location);

	public TileEntity getTileEntityAt(int x, int y, int z);
	public TileEntity getTileEntityAt(BlockLocation location);

	public void setTileEntityAt(TileEntity tileEntity, int x, int y, int z);
	public void setTileEntityAt(TileEntity tileEntity, BlockLocation location);

	public Chunk getChunkAt(int x, int y, int z);
	public Chunk getChunkAt(ChunkLocation location);
	
	public Entity[] getEntities();
	public Entity getEntityById(int id);
	public void spawnEntity(Entity entity);
	public void despawnEntity(Entity entity);

	public Dimension getDimension();
	public Difficulty getDifficulty();
	public WorldType getType();
	public int getMaxHeight();

	public PathSearchProvider getPathFinder();
	public void setPathFinder(PathSearchProvider pathFinder) throws UnsupportedOperationException;

	public long getTime();
	public long getAge();

	public void destroy();
}
