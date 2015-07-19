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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.event.EventBus;
import ovh.tgrhavoc.aibot.event.EventHandler;
import ovh.tgrhavoc.aibot.event.EventListener;
import ovh.tgrhavoc.aibot.event.general.TickEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.BlockChangeEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EditTileEntityEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityCollectEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityDespawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityDismountEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityHeadRotateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityMetadataUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityMountEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityMoveEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityRotateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.EntityTeleportEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.LivingEntitySpawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.LivingEntitySpawnEvent.LivingEntitySpawnData;
import ovh.tgrhavoc.aibot.event.protocol.server.LivingEntitySpawnEvent.LivingEntitySpawnLocation;
import ovh.tgrhavoc.aibot.event.protocol.server.ObjectEntitySpawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.ObjectEntitySpawnEvent.ObjectSpawnData;
import ovh.tgrhavoc.aibot.event.protocol.server.ObjectEntitySpawnEvent.ThrownObjectSpawnData;
import ovh.tgrhavoc.aibot.event.protocol.server.PaintingSpawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.PaintingSpawnEvent.PaintingSpawnLocation;
import ovh.tgrhavoc.aibot.event.protocol.server.PlayerEquipmentUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.PlayerSpawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.RespawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.RotatedEntitySpawnEvent.RotatedSpawnLocation;
import ovh.tgrhavoc.aibot.event.protocol.server.SignUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.TileEntityUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.TimeUpdateEvent;
import ovh.tgrhavoc.aibot.event.world.ChunkLoadEvent;
import ovh.tgrhavoc.aibot.event.world.EditSignEvent;
import ovh.tgrhavoc.aibot.nbt.NBTTagCompound;
import ovh.tgrhavoc.aibot.world.block.ArtType;
import ovh.tgrhavoc.aibot.world.block.Block;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;
import ovh.tgrhavoc.aibot.world.block.Chunk;
import ovh.tgrhavoc.aibot.world.block.ChunkLocation;
import ovh.tgrhavoc.aibot.world.block.SignTileEntity;
import ovh.tgrhavoc.aibot.world.block.TileEntity;
import ovh.tgrhavoc.aibot.world.entity.Entity;
import ovh.tgrhavoc.aibot.world.entity.EntityList;
import ovh.tgrhavoc.aibot.world.entity.LivingEntity;
import ovh.tgrhavoc.aibot.world.entity.PaintingEntity;
import ovh.tgrhavoc.aibot.world.entity.PlayerEntity;
import ovh.tgrhavoc.aibot.world.entity.ThrownEntity;
import ovh.tgrhavoc.aibot.world.pathfinding.EuclideanHeuristic;
import ovh.tgrhavoc.aibot.world.pathfinding.PathSearchProvider;
import ovh.tgrhavoc.aibot.world.pathfinding.SimpleWorldPhysics;
import ovh.tgrhavoc.aibot.world.pathfinding.astar.AStarPathSearchProvider;

public final class BasicWorld implements World, EventListener {
	private final MinecraftBot bot;
	private final WorldType type;
	private final Dimension dimension;
	private final Difficulty difficulty;
	private final int height;
	private final Map<ChunkLocation, Chunk> chunks;
	private final List<Entity> entities;
	private PathSearchProvider pathFinder;

	private long time, age;

	public BasicWorld(MinecraftBot bot, WorldType type, Dimension dimension, Difficulty difficulty, int height) {
		this.bot = bot;
		this.type = type;
		this.height = height;
		this.dimension = dimension;
		this.difficulty = difficulty;
		chunks = new HashMap<ChunkLocation, Chunk>();
		entities = new ArrayList<Entity>();
		pathFinder = new AStarPathSearchProvider(new EuclideanHeuristic(), new SimpleWorldPhysics(this));
		EventBus eventBus = bot.getEventBus();
		eventBus.register(this);
	}
	
	@EventHandler
	public void onTick(TickEvent event){
		for(Entity ent: this.getEntities())
			if (!ent.isDead())
				ent.update();
	}

	@EventHandler
	public void onPlayerEquipmentUpdate(PlayerEquipmentUpdateEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null || !(entity instanceof LivingEntity))
			return;
		LivingEntity livingEntity = (LivingEntity) entity;
		livingEntity.setWornItemAt(event.getSlot().getId(), event.getItem());
	}

	@EventHandler
	public void onTimeUpdate(TimeUpdateEvent event) {
		time = event.getTime();
		age = event.getWorldAge();
	}

	@EventHandler
	public void onRespawn(RespawnEvent event) {
		synchronized(chunks) {
			chunks.clear();
		}
	}

	@EventHandler
	public void onPlayerSpawn(PlayerSpawnEvent event) {
		RotatedSpawnLocation location = event.getLocation();
		PlayerEntity entity = new PlayerEntity(this, event.getEntityId(), event.getPlayerName());
		entity.setX(location.getX());
		entity.setY(location.getY());
		entity.setZ(location.getZ());
		entity.setYaw(location.getYaw());
		entity.setPitch(location.getPitch());
		entity.setWornItemAt(0, event.getHeldItem());
		if(event.getMetadata() != null)
			entity.updateMetadata(event.getMetadata());
		spawnEntity(entity);
	}

	@EventHandler
	public void onEntityCollect(EntityCollectEvent event) {
		Entity entity = getEntityById(event.getCollectedId());
		if(entity != null)
			despawnEntity(entity);
	}

	@EventHandler
	public void onObjectEntitySpawn(ObjectEntitySpawnEvent event) {
		RotatedSpawnLocation spawnLocation = event.getLocation();
		ObjectSpawnData spawnData = event.getSpawnData();
		Class<? extends Entity> entityClass = EntityList.getObjectEntityClass(spawnData.getType());
		if(entityClass == null)
			return;
		Entity entity;
		try {
			Constructor<? extends Entity> constructor = entityClass.getConstructor(World.class, Integer.TYPE);
			entity = constructor.newInstance(this, event.getEntityId());
		} catch(Exception exception) {
			exception.printStackTrace();
			return;
		}
		entity.setX(spawnLocation.getX());
		entity.setY(spawnLocation.getY());
		entity.setZ(spawnLocation.getZ());
		entity.setYaw(spawnLocation.getYaw());
		entity.setPitch(spawnLocation.getPitch());
		if(entity instanceof ThrownEntity && event.getSpawnData() instanceof ThrownObjectSpawnData) {
			Entity thrower = getEntityById(((ThrownObjectSpawnData) event.getSpawnData()).getThrowerId());
			if(thrower != null)
				((ThrownEntity) entity).setThrower(thrower);
		}
		spawnEntity(entity);
	}

	@EventHandler
	public void onLivingEntitySpawn(LivingEntitySpawnEvent event) {
		LivingEntitySpawnData spawnData = event.getSpawnData();
		LivingEntitySpawnLocation spawnLocation = event.getLocation();
		Class<? extends LivingEntity> entityClass = EntityList.getLivingEntityClass(spawnData.getType());
		if(entityClass == null)
			return;
		LivingEntity entity;
		try {
			Constructor<? extends LivingEntity> constructor = entityClass.getConstructor(World.class, Integer.TYPE);
			entity = constructor.newInstance(this, event.getEntityId());
		} catch(Exception exception) {
			exception.printStackTrace();
			return;
		}
		entity.setX(spawnLocation.getX());
		entity.setY(spawnLocation.getY());
		entity.setZ(spawnLocation.getZ());
		entity.setYaw(spawnLocation.getYaw());
		entity.setPitch(spawnLocation.getPitch());
		entity.setHeadYaw(spawnLocation.getHeadYaw());

		if(event.getMetadata() != null)
			entity.updateMetadata(event.getMetadata());
		spawnEntity(entity);
	}

	@EventHandler
	public void onPaintingSpawn(PaintingSpawnEvent event) {
		PaintingSpawnLocation spawnLocation = event.getLocation();
		PaintingEntity entity = new PaintingEntity(this, event.getEntityId(), ArtType.getArtTypeByName(event.getTitle()));
		entity.setX(spawnLocation.getX());
		entity.setY(spawnLocation.getY());
		entity.setZ(spawnLocation.getZ());
		entity.setDirection(spawnLocation.getDirection());
		spawnEntity(entity);
	}

	@EventHandler
	public void onEntityDespawn(EntityDespawnEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity != null) {
			despawnEntity(entity);
			entity.setDead(true);
		}
	}

	@EventHandler
	public void onEntityMove(EntityMoveEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null)
			return;
		entity.setX(entity.getX() + event.getX());
		entity.setY(entity.getY() + event.getY());
		entity.setZ(entity.getZ() + event.getZ());
	}

	@EventHandler
	public void onEntityRotate(EntityRotateEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null)
			return;
		entity.setYaw(event.getYaw());
		entity.setPitch(event.getPitch());
	}

	@EventHandler
	public void onEntityTeleport(EntityTeleportEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null)
			return;
		entity.setX(event.getX());
		entity.setY(event.getY());
		entity.setZ(event.getZ());
		entity.setYaw(event.getYaw());
		entity.setPitch(event.getPitch());
	}

	@EventHandler
	public void onEntityHeadRotate(EntityHeadRotateEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null || !(entity instanceof LivingEntity))
			return;
		((LivingEntity) entity).setHeadYaw(event.getHeadYaw());
	}

	@EventHandler
	public void onEntityMount(EntityMountEvent event) {
		Entity rider = getEntityById(event.getEntityId());
		Entity riding = getEntityById(event.getMountedEntityId());
		if(rider == null || riding == null)
			return;
		rider.setRiding(riding);
		riding.setRider(rider);
	}

	@EventHandler
	public void onEntityDismount(EntityDismountEvent event) {
		Entity rider = getEntityById(event.getEntityId());
		if(rider == null)
			return;
		if(rider.getRiding() != null) {
			rider.getRiding().setRider(null);
			rider.setRiding(null);
		}
	}

	@EventHandler
	public void onEntityMetadataUpdate(EntityMetadataUpdateEvent event) {
		Entity entity = getEntityById(event.getEntityId());
		if(entity == null)
			return;
		entity.updateMetadata(event.getMetadata());
	}

	@EventHandler
	public void onChunkLoad(ovh.tgrhavoc.aibot.event.protocol.server.ChunkLoadEvent event) {
		ChunkLocation location = new ChunkLocation(event.getX(), event.getY(), event.getZ());
		Chunk chunk = new Chunk(this, location, event.getBlocks(), event.getMetadata(), event.getLight(), event.getSkylight(), event.getBiomes());
		synchronized(chunks) {
			chunks.put(location, chunk);
		}
		bot.getEventBus().fire(new ChunkLoadEvent(this, chunk));
	}

	@EventHandler
	public void onBlockChange(BlockChangeEvent event) {
		setBlockIdAt(event.getId(), event.getX(), event.getY(), event.getZ());
		setBlockMetadataAt(event.getMetadata(), event.getX(), event.getY(), event.getZ());
	}

	@EventHandler
	public void onTileEntityUpdate(TileEntityUpdateEvent event) {
		BlockLocation location = new BlockLocation(event.getX(), event.getY(), event.getZ());
		Class<? extends TileEntity> entityClass = EntityList.getTileEntityClass(event.getType());
		if(entityClass == null)
			return;
		TileEntity entity;
		try {
			Constructor<? extends TileEntity> constructor = entityClass.getConstructor(NBTTagCompound.class);
			entity = constructor.newInstance(event.getCompound());
		} catch(Exception exception) {
			exception.printStackTrace();
			return;
		}
		setTileEntityAt(entity, location);
	}

	@EventHandler
	public void onSignUpdate(SignUpdateEvent event) {
		BlockLocation location = new BlockLocation(event.getX(), event.getY(), event.getZ());
		setTileEntityAt(new SignTileEntity(location.getX(), location.getY(), location.getZ(), event.getText()), location);
	}

	@EventHandler
	public void onEditTileEntity(EditTileEntityEvent event) {
		TileEntity entity = getTileEntityAt(event.getX(), event.getY(), event.getZ());
		if(entity != null)
			if(entity instanceof SignTileEntity)
				bot.getEventBus().fire(new EditSignEvent(entity.getLocation()));
	}

	@Override
	public void destroy() {
		EventBus eventBus = bot.getEventBus();
		eventBus.unregister(this);
		synchronized(entities) {
			for(Entity entity : entities)
				entity.setDead(true);
			entities.clear();
		}
		synchronized(chunks) {
			chunks.clear();
		}
		System.gc();
	}

	@Override
	public MinecraftBot getBot() {
		return bot;
	}

	@Override
	public Block getBlockAt(int x, int y, int z) {
		return getBlockAt(new BlockLocation(x, y, z));
	}

	@Override
	public Block getBlockAt(BlockLocation location) {
		ChunkLocation chunkLocation = new ChunkLocation(location);
		Chunk chunk = getChunkAt(chunkLocation);
		if(chunk == null)
			return null;
		BlockLocation chunkBlockOffset = new BlockLocation(chunkLocation);
		int chunkOffsetX = location.getX() - chunkBlockOffset.getX();
		int chunkOffsetY = location.getY() - chunkBlockOffset.getY();
		int chunkOffsetZ = location.getZ() - chunkBlockOffset.getZ();
		int id = chunk.getBlockIdAt(chunkOffsetX, chunkOffsetY, chunkOffsetZ);
		int metadata = chunk.getBlockMetadataAt(chunkOffsetX, chunkOffsetY, chunkOffsetZ);
		return new Block(this, chunk, location, id, metadata);
	}

	@Override
	public int getBlockIdAt(int x, int y, int z) {
		return getBlockIdAt(new BlockLocation(x, y, z));
	}

	@Override
	public int getBlockIdAt(BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return 0;
		int id = chunk.getBlockIdAt(blockLocation.getX() - chunkBlockOffset.getX(), blockLocation.getY() - chunkBlockOffset.getY(), blockLocation.getZ()
				- chunkBlockOffset.getZ());
		return id;
	}

	@Override
	public void setBlockIdAt(int id, int x, int y, int z) {
		setBlockIdAt(id, new BlockLocation(x, y, z));
	}

	@Override
	public void setBlockIdAt(int id, BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return;
		chunk.setBlockIdAt(id, blockLocation.getX() - chunkBlockOffset.getX(), blockLocation.getY() - chunkBlockOffset.getY(), blockLocation.getZ()
				- chunkBlockOffset.getZ());
	}

	@Override
	public int getBlockMetadataAt(int x, int y, int z) {
		return getBlockMetadataAt(new BlockLocation(x, y, z));
	}

	@Override
	public int getBlockMetadataAt(BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return 0;
		int metadata = chunk.getBlockMetadataAt(blockLocation.getX() - chunkBlockOffset.getX(),
												blockLocation.getY() - chunkBlockOffset.getY(),
												blockLocation.getZ() - chunkBlockOffset.getZ());
		return metadata;
	}

	@Override
	public void setBlockMetadataAt(int metadata, int x, int y, int z) {
		setBlockMetadataAt(metadata, new BlockLocation(x, y, z));
	}

	@Override
	public void setBlockMetadataAt(int metadata, BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return;
		chunk.setBlockMetadataAt(metadata, blockLocation.getX() - chunkBlockOffset.getX(), blockLocation.getY() - chunkBlockOffset.getY(), blockLocation.getZ()
				- chunkBlockOffset.getZ());
	}

	@Override
	public TileEntity getTileEntityAt(int x, int y, int z) {
		return getTileEntityAt(new BlockLocation(x, y, z));
	}

	@Override
	public TileEntity getTileEntityAt(BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return null;
		TileEntity tileEntity = chunk.getTileEntityAt(	blockLocation.getX() - chunkBlockOffset.getX(),
														blockLocation.getY() - chunkBlockOffset.getY(),
														blockLocation.getZ() - chunkBlockOffset.getZ());
		return tileEntity;
	}

	@Override
	public void setTileEntityAt(TileEntity tileEntity, int x, int y, int z) {
		setTileEntityAt(tileEntity, new BlockLocation(x, y, z));
	}

	@Override
	public void setTileEntityAt(TileEntity tileEntity, BlockLocation blockLocation) {
		ChunkLocation location = new ChunkLocation(blockLocation);
		BlockLocation chunkBlockOffset = new BlockLocation(location);
		Chunk chunk = getChunkAt(location);
		if(chunk == null)
			return;
		chunk.setTileEntityAt(tileEntity, blockLocation.getX() - chunkBlockOffset.getX(), blockLocation.getY() - chunkBlockOffset.getY(), blockLocation.getZ()
				- chunkBlockOffset.getZ());
	}

	@Override
	public Chunk getChunkAt(int x, int y, int z) {
		return getChunkAt(new ChunkLocation(x, y, z));
	}

	@Override
	public Chunk getChunkAt(ChunkLocation location) {
		synchronized(chunks) {
			return chunks.get(location);
		}
	}

	@Override
	public Entity[] getEntities() {
		synchronized(entities) {
			return entities.toArray(new Entity[entities.size()]);
		}
	}

	@Override
	public void spawnEntity(Entity entity) {
		if(entity == null)
			throw new NullPointerException();
		synchronized(entities) {
			if(!entities.contains(entity))
				entities.add(entity);
		}
	}

	@Override
	public Entity getEntityById(int id) {
		synchronized(entities) {
			for(Entity entity : entities)
				if(id == entity.getId())
					return entity;
			return null;
		}
	}

	@Override
	public void despawnEntity(Entity entity) {
		if(entity == null)
			throw new NullPointerException();
		synchronized(entities) {
			entities.remove(entity);
		}
	}

	@Override
	public Dimension getDimension() {
		return dimension;
	}

	@Override
	public Difficulty getDifficulty() {
		return difficulty;
	}

	@Override
	public WorldType getType() {
		return type;
	}

	@Override
	public int getMaxHeight() {
		return height;
	}

	@Override
	public PathSearchProvider getPathFinder() {
		return pathFinder;
	}

	@Override
	public void setPathFinder(PathSearchProvider pathFinder) {
		this.pathFinder = pathFinder;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public long getAge() {
		return age;
	}
}