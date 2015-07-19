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

import ovh.tgrhavoc.aibot.IntHashMap;
import ovh.tgrhavoc.aibot.world.entity.WatchableObject;

public class LivingEntitySpawnEvent extends MetaEntitySpawnEvent {
	private final LivingEntitySpawnData spawnData;

	public LivingEntitySpawnEvent(int entityId, LivingEntitySpawnLocation location, LivingEntitySpawnData spawnData, IntHashMap<WatchableObject> metadata) {
		super(entityId, location, metadata);

		this.spawnData = spawnData;
	}

	@Override
	public LivingEntitySpawnLocation getLocation() {
		return (LivingEntitySpawnLocation) super.getLocation();
	}

	public LivingEntitySpawnData getSpawnData() {
		return spawnData;
	}

	public static class LivingEntitySpawnLocation extends RotatedSpawnLocation {
		private final double headYaw;

		public LivingEntitySpawnLocation(double x, double y, double z, double yaw, double pitch, double headYaw) {
			super(x, y, z, yaw, pitch);

			this.headYaw = headYaw;
		}

		public double getHeadYaw() {
			return headYaw;
		}
	}

	public static class LivingEntitySpawnData {
		private final int type;
		private final double velocityX, velocityY, velocityZ;

		public LivingEntitySpawnData(int type, double velocityX, double velocityY, double velocityZ) {
			this.type = type;
			this.velocityX = velocityX;
			this.velocityY = velocityY;
			this.velocityZ = velocityZ;
		}

		public int getType() {
			return type;
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
	}
}
