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

public class Packet7UseEntity extends AbstractPacket implements WriteablePacket {
	public int playerEntityId;
	public int targetEntity;
	public int isLeftClick;

	public Packet7UseEntity() {
	}

	public Packet7UseEntity(int playerEntityId, int targetEntity, int isLeftClick) {
		this.playerEntityId = playerEntityId;
		this.targetEntity = targetEntity;
		this.isLeftClick = isLeftClick;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.writeInt(playerEntityId);
		out.writeInt(targetEntity);
		out.writeByte(isLeftClick);
	}

	@Override
	public int getId() {
		return 7;
	}
}
