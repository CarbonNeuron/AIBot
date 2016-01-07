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
package ovh.tgrhavoc.aibot.protocol.v5x.play.server;

import java.io.*;

import ovh.tgrhavoc.aibot.IntHashMap;
import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.world.entity.WatchableObject;

public class PacketS0C_SpawnPlayer extends AbstractPacketX implements ReadablePacket {
	private int entityId;
	private String uuid, name;
	private PlayerProperty[] properties;

	private double x, y, z, yaw, pitch;
	private int heldItemId;
	private IntHashMap<WatchableObject> metadata;

	public PacketS0C_SpawnPlayer() {
		super(0x0C, State.PLAY, Direction.DOWNSTREAM);
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		entityId = readVarInt(in);
		uuid = readString(in);
		name = readString(in);

		properties = new PlayerProperty[readVarInt(in)];
		for(int i = 0; i < properties.length; i++) {
			String name = readString(in);
			String value = readString(in);
			String signature = readString(in);
			properties[i] = new PlayerProperty(name, value, signature);
		}

		x = in.readInt() / 32D;
		y = in.readInt() / 32D;
		z = in.readInt() / 32D;
		yaw = (in.readByte() * 360) / 256D;
		pitch = (in.readByte() * 360) / 256D;
		heldItemId = in.readShort();
		metadata = readWatchableObjects(in);
	}

	public int getEntityId() {
		return entityId;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	public PlayerProperty[] getProperties() {
		return properties.clone();
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

	public int getHeldItemId() {
		return heldItemId;
	}

	public IntHashMap<WatchableObject> getMetadata() {
		return metadata;
	}

	public static class PlayerProperty {
		private final String name, value, signature;

		private PlayerProperty(String name, String value, String signature) {
			this.name = name;
			this.value = value;
			this.signature = signature;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}

		public String getSignature() {
			return signature;
		}
	}
}
