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
package ovh.tgrhavoc.aibot.world.entity;

import ovh.tgrhavoc.aibot.IntHashMap;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.WorldLocation;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;

public abstract class Entity {
	protected final World world;
	protected final int id;
	protected double x, y, z, yaw, pitch;
	protected double velocityX, velocityY, velocityZ;
	protected double sizeX, sizeY, sizeZ;
	protected Entity rider, riding;
	protected boolean dead;

	public Entity(World world, int id) {
		this.world = world;
		this.id = id;
	}
	
	public void update() {
		
		if(velocityX >= -1E-6 && velocityX <= 1E-6) velocityX = 0;
		if(velocityY >= -1E-6 && velocityY <= 1E-6) velocityY = 0;
		if(velocityZ >= -1E-6 && velocityZ <= 1E-6) velocityZ = 0;
		
		x += velocityX;
		y += velocityY;
		z += velocityZ;
	}
	
	protected void move() {
	}

	public World getWorld() {
		return world;
	}

	public int getId() {
		return id;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getYaw() {
		return yaw;
	}

	public double getPitch() {
		return pitch;
	}
	
	public double getVelocityX() {
		return velocityX;
	}
	
	public double getVelocityY() {
		return velocityY;
	}
	
	public double getVelocityZ() {
		return velocityZ;
	}
	
	public double getVelocity() {
		return Math.sqrt(velocityX * velocityX + velocityY * velocityY + velocityZ * velocityZ);
	}
	
	public double getVelocityHorizontalAngle() {
		return Math.atan2(velocityZ, velocityX);
	}
	
	public double getVelocityVerticalAngle() {
		return Math.atan2(velocityY, Math.hypot(velocityX, velocityZ));
	}
	
	public double getSizeX() {
		return sizeX;
	}
	
	public double getSizeY() {
		return sizeY;
	}
	
	public double getSizeZ() {
		return sizeZ;
	}

	public Entity getRider() {
		return rider;
	}

	public Entity getRiding() {
		return riding;
	}

	public boolean isDead() {
		return dead;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	
	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}
	
	public void setVelocityZ(double velocityZ) {
		this.velocityZ = velocityZ;
	}

	public void setRider(Entity rider) {
		this.rider = rider;
	}

	public void setRiding(Entity riding) {
		this.riding = riding;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public WorldLocation getLocation() {
		return new WorldLocation(x, y, z);
	}

	public void setLocation(WorldLocation location) {
		x = location.getX();
		y = location.getY();
		z = location.getZ();
	}

	public void updateMetadata(IntHashMap<WatchableObject> metadata) {
	}
	
	public void accelerate(double horizAngle, double vertAngle, double accel) {
		velocityX += accel * Math.cos(horizAngle) * Math.cos(vertAngle);
		velocityZ += accel * Math.sin(horizAngle) * Math.cos(vertAngle);
		velocityY += accel * Math.sin(vertAngle);
	}
	
	public void accelerate(double horizAngle, double vertAngle, double accel, double velocityBound) {
		double ax = Math.abs(accel * Math.cos(horizAngle) * Math.cos(vertAngle));
		double az = Math.abs(accel * Math.sin(horizAngle) * Math.cos(vertAngle));
		double ay = Math.abs(accel * Math.sin(vertAngle));
		
		double vxb = velocityBound * Math.cos(horizAngle) * Math.cos(vertAngle);
		double vzb = velocityBound * Math.sin(horizAngle) * Math.cos(vertAngle);
		double vyb = velocityBound * Math.sin(vertAngle);
		
		if(vxb < 0 && velocityX > vxb)
			velocityX = Math.max(vxb, velocityX - ax);
		else if(vxb > 0 && velocityX < vxb)
			velocityX = Math.min(vxb, velocityX + ax);

		if(vzb < 0 && velocityZ > vzb)
			velocityZ = Math.max(vzb, velocityZ - az);
		else if(vzb > 0 && velocityZ < vzb)
			velocityZ = Math.min(vzb, velocityZ + az);

		if(vyb < 0 && velocityY > vyb)
			velocityY = Math.max(vyb, velocityY - ay);
		else if(vyb > 0 && velocityY < vyb)
			velocityY = Math.min(vyb, velocityY + ay);
	}

	public double getDistanceTo(double x, double y, double z) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
	}

	public int getDistanceToSquared(double x, double y, double z) {
		return (int) (Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2) + Math.pow(this.z - z, 2));
	}

	public double getDistanceTo(Entity other) {
		return getDistanceTo(other.getX(), other.getY(), other.getZ());
	}

	public int getDistanceToSquared(Entity other) {
		return getDistanceToSquared(other.getX(), other.getY(), other.getZ());
	}

	public double getDistanceTo(WorldLocation location) {
		return getDistanceTo(location.getX(), location.getY(), location.getZ());
	}

	public double getDistanceToSquared(WorldLocation location) {
		return getDistanceToSquared(location.getX(), location.getY(), location.getZ());
	}

	public double getDistanceTo(BlockLocation location) {
		return getDistanceTo(location.getX() + 0.5, location.getY(), location.getZ() + 0.5);
	}

	public double getDistanceToSquared(BlockLocation location) {
		return getDistanceToSquared(location.getX() + 0.5, location.getY(), location.getZ() + 0.5);
	}
}