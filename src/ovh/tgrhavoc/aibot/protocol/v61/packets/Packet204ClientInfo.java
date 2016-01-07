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

public class Packet204ClientInfo extends AbstractPacket implements WriteablePacket {
	public String language;
	public int renderDistance;
	public int chatVisisble;
	public boolean chatColours;
	public int gameDifficulty;
	public boolean showCape;

	public Packet204ClientInfo() {
	}

	public Packet204ClientInfo(String par1Str, int par2, int par3, boolean par4, int par5, boolean par6) {
		language = par1Str;
		renderDistance = par2;
		chatVisisble = par3;
		chatColours = par4;
		gameDifficulty = par5;
		showCape = par6;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeString(language, out);
		out.writeByte(renderDistance);
		out.writeByte(chatVisisble | (chatColours ? 1 : 0) << 3);
		out.writeByte(gameDifficulty);
		out.writeBoolean(showCape);
	}

	@Override
	public int getId() {
		return 204;
	}
}