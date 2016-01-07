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
package ovh.tgrhavoc.aibot.event.protocol.server;

import ovh.tgrhavoc.aibot.event.protocol.ProtocolEvent;
import ovh.tgrhavoc.aibot.world.*;

public class LoginEvent extends ProtocolEvent {
	private final int playerId;
	private final WorldType worldType;
	private final GameMode gameMode;
	private final Dimension dimension;
	private final Difficulty difficulty;
	private final int worldHeight, maxPlayers;

	public LoginEvent(int playerId, WorldType worldType, GameMode gameMode, Dimension dimension, Difficulty difficulty, int worldHeight, int maxPlayers) {
		this.playerId = playerId;
		this.worldType = worldType;
		this.gameMode = gameMode;
		this.dimension = dimension;
		this.difficulty = difficulty;
		this.worldHeight = worldHeight;
		this.maxPlayers = maxPlayers;
	}

	public int getPlayerId() {
		return playerId;
	}

	public WorldType getWorldType() {
		return worldType;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}
}
