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

public class RespawnEvent extends ProtocolEvent {
	private final Dimension respawnDimension;
	private final Difficulty difficulty;
	private final GameMode gameMode;
	private final WorldType worldType;
	private final int worldHeight;

	public RespawnEvent(Dimension respawnDimension, Difficulty difficulty, GameMode gameMode, WorldType worldType, int worldHeight) {
		this.respawnDimension = respawnDimension;
		this.difficulty = difficulty;
		this.gameMode = gameMode;
		this.worldType = worldType;
		this.worldHeight = worldHeight;
	}

	public Dimension getRespawnDimension() {
		return respawnDimension;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public WorldType getWorldType() {
		return worldType;
	}

	public int getWorldHeight() {
		return worldHeight;
	}
}
