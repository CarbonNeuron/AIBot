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
package ovh.tgrhavoc.aibot.protocol.v74.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.ReadablePacket;

public class Packet13PlayerLookMove extends Packet10Flying implements ReadablePacket {
	public double x;
	public double y;
	public double z;
	public double stance;
	public float yaw;
	public float pitch;

	public Packet13PlayerLookMove() {
		super(false);
	}

	public Packet13PlayerLookMove(double x, double y, double stance, double z, float yaw, float pitch, boolean onGround) {
		super(onGround);
		this.x = x;
		this.y = y;
		this.stance = stance;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		x = in.readDouble();
		stance = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		yaw = in.readFloat();
		pitch = in.readFloat();
		onGround = in.readBoolean();
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(stance);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		super.writeData(out);
	}

	@Override
	public int getId() {
		return 13;
	}
}
