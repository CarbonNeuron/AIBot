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

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet202PlayerAbilities extends AbstractPacket implements ReadablePacket, WriteablePacket {
	public boolean disableDamage;
	public boolean flying;
	public boolean allowFlying;
	public boolean creativeMode;

	private float flySpeed, walkSpeed;

	public Packet202PlayerAbilities() {
		disableDamage = false;
		flying = false;
		allowFlying = false;
		creativeMode = false;
	}

	// public Packet202PlayerAbilities(PlayerCapabilities
	// par1PlayerCapabilities) {
	// field_50072_a = false;
	// field_50070_b = false;
	// field_50071_c = false;
	// field_50069_d = false;
	// field_50072_a = par1PlayerCapabilities.disableDamage;
	// field_50070_b = par1PlayerCapabilities.isFlying;
	// field_50071_c = par1PlayerCapabilities.allowFlying;
	// field_50069_d = par1PlayerCapabilities.isCreativeMode;
	// }

	@Override
	public void readData(DataInputStream in) throws IOException {
		byte flags = in.readByte();
		disableDamage = (flags & 1) > 0;
		flying = (flags & 2) > 0;
		allowFlying = (flags & 4) > 0;
		creativeMode = (flags & 8) > 0;

		flySpeed = in.readFloat();
		walkSpeed = in.readFloat();
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		byte flags = 0;
		if(disableDamage)
			flags |= 1;
		if(flying)
			flags |= 2;
		if(allowFlying)
			flags |= 4;
		if(creativeMode)
			flags |= 8;
		out.writeByte(flags);

		out.writeFloat(flySpeed);
		out.writeFloat(walkSpeed);
	}

	@Override
	public int getId() {
		return 202;
	}
}
