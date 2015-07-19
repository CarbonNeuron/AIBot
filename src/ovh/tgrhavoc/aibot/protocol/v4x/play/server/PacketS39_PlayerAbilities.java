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
package ovh.tgrhavoc.aibot.protocol.v4x.play.server;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;

public class PacketS39_PlayerAbilities extends AbstractPacketX implements ReadablePacket {
	private static final int CREATIVE_MODE = 0x1, FLYING = 0x2, CAN_FLY = 0x4, GOD_MODE = 0x8;

	private boolean creative, flying, ableToFly, invincible;
	private double walkingSpeed, flyingSpeed;

	public PacketS39_PlayerAbilities() {
		super(0x39, State.PLAY, Direction.DOWNSTREAM);
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		int flags = in.read();
		creative = (flags & CREATIVE_MODE) != 0;
		flying = (flags & FLYING) != 0;
		ableToFly = (flags & CAN_FLY) != 0;
		invincible = (flags & GOD_MODE) != 0;

		flyingSpeed = in.readInt() / 250D;
		walkingSpeed = in.readInt() / 250D;
	}

	public boolean isCreative() {
		return creative;
	}

	public boolean isFlying() {
		return flying;
	}

	public boolean isAbleToFly() {
		return ableToFly;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public double getWalkingSpeed() {
		return walkingSpeed;
	}

	public double getFlyingSpeed() {
		return flyingSpeed;
	}
}
