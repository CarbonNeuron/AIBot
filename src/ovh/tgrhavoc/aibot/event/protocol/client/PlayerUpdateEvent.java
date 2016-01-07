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
package ovh.tgrhavoc.aibot.event.protocol.client;

import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;

public class PlayerUpdateEvent extends PlayerEvent {
	private final double x, y, z, yaw, pitch;
	private final boolean onGround;

	public PlayerUpdateEvent(MainPlayerEntity entity) {
		this(entity, entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch(), entity.isOnGround());
	}

	public PlayerUpdateEvent(MainPlayerEntity entity, boolean onGround) {
		this(entity, entity.getX(), entity.getY(), entity.getZ(), entity.getYaw(), entity.getPitch(), onGround);
	}

	protected PlayerUpdateEvent(MainPlayerEntity entity, double x, double y, double z, double yaw, double pitch, boolean onGround) {
		super(entity);

		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
		this.onGround = onGround;
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

	public boolean isOnGround() {
		return onGround;
	}
}
