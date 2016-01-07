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

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.world.*;

public class PacketS01_JoinGame extends AbstractPacketX implements ReadablePacket {
	private int playerId;
	private GameMode gameMode;
	private boolean hardcore;
	private Dimension dimension;
	private Difficulty difficulty;
	private int maxPlayers;
	private WorldType worldType;

	public PacketS01_JoinGame() {
		super(0x01, State.PLAY, Direction.DOWNSTREAM);
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		playerId = in.readInt();

		int gameModeValue = in.read();
		gameMode = GameMode.getGameModeById(gameModeValue & 0x7);
		hardcore = (gameModeValue & 0x8) == 0x8;

		dimension = Dimension.getDimensionById(in.read());
		difficulty = Difficulty.getDifficultyById(in.readByte());
		maxPlayers = in.read();

		worldType = WorldType.parseWorldType(readString(in));
	}

	public int getPlayerId() {
		return playerId;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public boolean isHardcore() {
		return hardcore;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public WorldType getWorldType() {
		return worldType;
	}
}
