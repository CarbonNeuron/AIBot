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


public abstract class RotatedEntitySpawnEvent extends EntitySpawnEvent {
	public RotatedEntitySpawnEvent(int entityId, RotatedSpawnLocation location) {
		super(entityId, location);
	}

	@Override
	public RotatedSpawnLocation getLocation() {
		return (RotatedSpawnLocation) super.getLocation();
	}

	public static class RotatedSpawnLocation extends SpawnLocation {
		private final double yaw, pitch;

		public RotatedSpawnLocation(double x, double y, double z, double yaw, double pitch) {
			super(x, y, z);

			this.yaw = yaw;
			this.pitch = pitch;
		}

		public double getYaw() {
			return yaw;
		}

		public double getPitch() {
			return pitch;
		}
	}
}
