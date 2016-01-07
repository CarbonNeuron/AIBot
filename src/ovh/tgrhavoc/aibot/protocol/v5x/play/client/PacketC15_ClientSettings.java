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
package ovh.tgrhavoc.aibot.protocol.v5x.play.client;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.world.Difficulty;

public class PacketC15_ClientSettings extends AbstractPacketX implements WriteablePacket {
	private String locale;
	private ViewDistance viewDistance;
	private ChatMode chatMode;
	private Difficulty difficulty;
	private boolean showChatColors, showCape;

	public PacketC15_ClientSettings(String locale, ViewDistance viewDistance, ChatMode chatMode, Difficulty difficulty, boolean showChatColors, boolean showCape) {
		super(0x15, State.PLAY, Direction.UPSTREAM);

		this.locale = locale;
		this.viewDistance = viewDistance;
		this.chatMode = chatMode;
		this.difficulty = difficulty;
		this.showChatColors = showChatColors;
		this.showCape = showCape;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeString(locale, out);
		out.write(viewDistance.ordinal());
		out.write(chatMode.ordinal());
		out.writeBoolean(showChatColors);
		out.write(difficulty.getId());
		out.writeBoolean(showCape);
	}

	public String getLocale() {
		return locale;
	}

	public ViewDistance getViewDistance() {
		return viewDistance;
	}

	public ChatMode getChatMode() {
		return chatMode;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public boolean shouldShowChatColors() {
		return showChatColors;
	}

	public boolean shouldShowCape() {
		return showCape;
	}

	public enum ViewDistance {
		FAR,
		NORMAL,
		SHORT,
		TINY
	}

	public enum ChatMode {
		ENABLED,
		COMMANDS_ONLY,
		DISABLED,
	}
}
