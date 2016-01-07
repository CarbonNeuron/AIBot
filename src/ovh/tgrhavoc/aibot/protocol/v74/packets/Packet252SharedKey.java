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
import java.security.*;

import javax.crypto.SecretKey;

import ovh.tgrhavoc.aibot.protocol.*;

public class Packet252SharedKey extends AbstractPacket implements ReadablePacket, WriteablePacket {
	public SecretKey sharedKey;
	public byte[] sharedSecret = new byte[0];
	public byte[] verifyToken = new byte[0];

	public Packet252SharedKey() {
	}

	public Packet252SharedKey(SecretKey sharedKey, PublicKey publicKey, byte[] data) {
		this.sharedKey = sharedKey;
		try {
			sharedSecret = EncryptionUtil.cipher(1, publicKey, sharedKey.getEncoded());
			verifyToken = EncryptionUtil.cipher(1, publicKey, data);
		} catch(GeneralSecurityException exception) {
			throw new Error("Unable to cipher", exception);
		}
	}

	@Override
	public void readData(DataInputStream in) throws IOException {
		sharedSecret = readByteArray(in);
		verifyToken = readByteArray(in);
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeByteArray(sharedSecret, out);
		writeByteArray(verifyToken, out);
	}

	@Override
	public int getId() {
		return 252;
	}
}