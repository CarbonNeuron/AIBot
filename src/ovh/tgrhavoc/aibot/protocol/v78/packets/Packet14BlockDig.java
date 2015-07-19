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
package ovh.tgrhavoc.aibot.protocol.v78.packets;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet14BlockDig extends AbstractPacket implements WriteablePacket {
	public int xPosition;
	public int yPosition;
	public int zPosition;

	public int face;
	public int status;

	public Packet14BlockDig() {
	}

	public Packet14BlockDig(int par1, int par2, int par3, int par4, int par5) {
		status = par1;
		xPosition = par2;
		yPosition = par3;
		zPosition = par4;
		face = par5;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.write(status);
		out.writeInt(xPosition);
		out.write(yPosition);
		out.writeInt(zPosition);
		out.write(face);
	}

	@Override
	public int getId() {
		return 14;
	}
}
