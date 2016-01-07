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

public class Packet254ServerPing extends AbstractPacket implements WriteablePacket {
	public int protocolVersion;
	public String hostname;
	public int port;

	public Packet254ServerPing() {
	}

	public Packet254ServerPing(int protocolVersion, String hostname, int port) {
		this.protocolVersion = protocolVersion;
		this.hostname = hostname;
		this.port = port;
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		out.writeByte(1);
		out.write(250);
		writeString("MC|PingHost", out);
		out.writeShort((3 + (2 * hostname.length())) + 4);
		out.write(protocolVersion);
		writeString(hostname, out);
		out.writeInt(port);
	}

	@Override
	public int getId() {
		return 254;
	}
}
