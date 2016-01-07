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
package ovh.tgrhavoc.aibot.protocol.v4x.login.client;

import java.io.*;
import java.security.*;

import javax.crypto.SecretKey;

import ovh.tgrhavoc.aibot.protocol.*;
import ovh.tgrhavoc.aibot.protocol.ProtocolX.State;

public class PacketLC01_EncryptionResponse extends AbstractPacketX implements WriteablePacket {
	private final SecretKey secretKey;
	private final PublicKey publicKey;
	private final byte[] sharedSecret, verifyToken;

	public PacketLC01_EncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
		super(0x01, State.LOGIN, Direction.UPSTREAM);

		this.secretKey = secretKey;
		this.publicKey = publicKey;

		try {
			sharedSecret = EncryptionUtil.cipher(1, publicKey, secretKey.getEncoded());
			this.verifyToken = EncryptionUtil.cipher(1, publicKey, verifyToken);
		} catch(GeneralSecurityException exception) {
			throw new Error("Unable to cipher", exception);
		}
	}

	@Override
	public void writeData(DataOutputStream out) throws IOException {
		writeByteArray(sharedSecret, out);
		writeByteArray(verifyToken, out);
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public byte[] getSharedSecret() {
		return sharedSecret;
	}

	public byte[] getVerifyToken() {
		return verifyToken;
	}
}
