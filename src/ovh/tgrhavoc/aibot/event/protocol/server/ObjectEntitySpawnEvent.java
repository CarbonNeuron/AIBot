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

public class ObjectEntitySpawnEvent extends RotatedEntitySpawnEvent {
	private final ObjectSpawnData spawnData;

	public ObjectEntitySpawnEvent(int entityId, RotatedSpawnLocation location, ObjectSpawnData spawnData) {
		super(entityId, location);

		this.spawnData = spawnData;
	}

	public ObjectSpawnData getSpawnData() {
		return spawnData;
	}

	public static class ObjectSpawnData {
		private final int type;

		public ObjectSpawnData(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	public static class ThrownObjectSpawnData extends ObjectSpawnData {
		private final int throwerId;
		private final double speedX, speedY, speedZ;

		public ThrownObjectSpawnData(int type, int throwerId, double speedX, double speedY, double speedZ) {
			super(type);

			this.throwerId = throwerId;
			this.speedX = speedX;
			this.speedY = speedY;
			this.speedZ = speedZ;
		}

		public int getThrowerId() {
			return throwerId;
		}

		public double getSpeedX() {
			return speedX;
		}

		public double getSpeedY() {
			return speedY;
		}

		public double getSpeedZ() {
			return speedZ;
		}
	}
}
