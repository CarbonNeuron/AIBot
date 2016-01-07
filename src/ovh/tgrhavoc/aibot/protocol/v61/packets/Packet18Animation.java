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
package ovh.tgrhavoc.aibot.protocol.v61.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet18Animation extends AbstractPacket implements
		ReadablePacket, WriteablePacket {
	public static enum Animation {
		NONE(0),
		SWING_ARM(1),
		DAMAGE_ENTITY(2),
		LEAVE_BED(3),
		EAT_FOOD(5),
		UNKNOWN1(102),
		CROUCH(104),
		UNCROUCH(105);

		private final int id;

		private Animation(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}

		private static Animation parseAnimation(int id) {
			for(Animation animation : values())
				if(animation.getId() == id)
					return animation;
			return null;
		}
	}

	public int entityId;
	public Animation animation;

	public Packet18Animation() {
	}

	public Packet18Animation(int entityId, Animation animation) {
		this.entityId = entityId;
		this.animation = animation;
	}

	public void readData(DataInputStream in) throws IOException {
		entityId = in.readInt();
		animation = Animation.parseAnimation(in.readByte());
	}

	public void writeData(DataOutputStream out) throws IOException {
		out.writeInt(entityId);
		out.writeByte(animation.getId());
	}

	public int getId() {
		return 18;
	}
}
