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
package ovh.tgrhavoc.aibot.protocol.v4x.login.server;

import java.io.*;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;

public class PacketLS01_EncryptionRequest extends AbstractPacketX implements ReadablePacket {
	private String serverId;
	private byte[] publicKey, verifyToken;

	public PacketLS01_EncryptionRequest() {
		super(0x01, State.LOGIN, Direction.DOWNSTREAM);
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		serverId = readString(in);
		publicKey = readByteArray(in);
		verifyToken = readByteArray(in);

		System.out.println("Read public key: " + byteArrayString(publicKey));
		System.out.println("Read verify token: " + byteArrayString(verifyToken));
	}

	private String byteArrayString(byte[] data) {
		StringBuffer buffer = new StringBuffer();
		for(byte b : data) {
			if(buffer.length() == 0)
				buffer.append("new byte[] { ");
			else
				buffer.append(", ");
			buffer.append("0x");
			if(b <= 0xF)
				buffer.append(0);
			buffer.append(Integer.toHexString(b & 0xFF).toUpperCase());
		}
		return buffer.append(" };").toString();
	}

	public String getServerId() {
		return serverId;
	}

	public byte[] getPublicKey() {
		return publicKey;
	}

	public byte[] getVerifyToken() {
		return verifyToken;
	}
}
