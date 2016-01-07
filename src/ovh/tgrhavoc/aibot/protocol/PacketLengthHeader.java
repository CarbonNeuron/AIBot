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
package ovh.tgrhavoc.aibot.protocol;

import java.io.DataOutputStream;
import java.io.IOException;

public class PacketLengthHeader extends PacketHeader {
	private final int length;

	public PacketLengthHeader(int id, int length) {
		super(id);

		this.length = length;
	}

	public final int getLength() {
		return length;
	}

	@Override
	public void write(DataOutputStream out) throws IOException {
		AbstractPacketX.writeVarInt(getLength(), out);
		AbstractPacketX.writeVarInt(getId(), out);
	}

	@Override
	public String toString() {
		return "PacketLengthHeader{id=0x" + Integer.toHexString(getId()).toUpperCase() + ",length=" + getLength() + "}";
	}
}