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
import ovh.tgrhavoc.aibot.world.*;

public class Packet1Login extends AbstractPacket implements ReadablePacket {
	public int playerId;
	public WorldType worldType = WorldType.DEFAULT;
	public boolean bool;

	public GameMode gameMode = GameMode.SURVIVAL;
	public Dimension dimension = Dimension.OVERWORLD;
	public Difficulty difficulty = Difficulty.EASY;

	public byte worldHeight, maxPlayers;

	public Packet1Login() {
	}

	public Packet1Login(int id) {
		playerId = id;
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		playerId = in.readInt();
		String s = readString(in, 16);
		worldType = WorldType.parseWorldType(s);

		if(worldType == null) {
			worldType = WorldType.DEFAULT;
		}

		byte b = in.readByte();
		bool = (b & 8) == 8;

		gameMode = GameMode.getGameModeById(b & -9);
		dimension = Dimension.getDimensionById(in.readByte());
		difficulty = Difficulty.getDifficultyById(in.readByte());
		worldHeight = in.readByte();
		maxPlayers = in.readByte();
	}

	@Override
	public int getId() {
		return 1;
	}
}
