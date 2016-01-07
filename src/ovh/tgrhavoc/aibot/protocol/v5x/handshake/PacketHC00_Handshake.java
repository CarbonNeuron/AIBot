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
package ovh.tgrhavoc.aibot.protocol.v5x.handshake;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;

public class PacketHC00_Handshake extends AbstractPacketX implements WriteablePacket {
	private final int protocolVersion;
	private final String address;
	private final int port;
	private final State nextState;

	public PacketHC00_Handshake(int protocolVersion, String address, int port, State nextState) {
		super(0x00, State.HANDSHAKE, Direction.UPSTREAM);

		if(nextState != State.STATUS && nextState != State.LOGIN)
			throw new IllegalArgumentException("Next state must either be status or login");

		this.protocolVersion = protocolVersion;
		this.address = address;
		this.port = port;
		this.nextState = nextState;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeVarInt(protocolVersion, out);
		writeString(address, out);
		out.writeShort(port);
		writeVarInt(nextState == State.STATUS ? 1 : 2, out);
	}

	public int getProtocolVersion() {
		return protocolVersion;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public State getNextState() {
		return nextState;
	}
}
