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
package ovh.tgrhavoc.aibot.protocol.v4x.play.client;

import java.io.DataOutputStream;
import java.io.IOException;

import ovh.tgrhavoc.aibot.protocol.AbstractPacketX;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;
import ovh.tgrhavoc.aibot.protocol.WriteablePacket;

public class PacketC10_CreativeInventoryAction extends AbstractPacketX implements WriteablePacket {
	
	public PacketC10_CreativeInventoryAction() {
		super(0x0, State.PLAY, Direction.UPSTREAM);
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {}
}
