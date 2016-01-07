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
package ovh.tgrhavoc.aibot.protocol;

import java.io.*;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.*;

public final class EncryptionUtil {
	private EncryptionUtil() {
	}

	public static SecretKey generateSecretKey() {
		CipherKeyGenerator generator = new CipherKeyGenerator();
		generator.init(new KeyGenerationParameters(new SecureRandom(), 128));
		return new SecretKeySpec(generator.generateKey(), "AES");
	}

	public static KeyPair createNewKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		return generator.generateKeyPair();
	}

	public static byte[] encrypt(String string, PublicKey publicKey, SecretKey secretKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return hash("SHA-1", string.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded());
	}

	private static byte[] hash(String algorithm, byte[]... data) throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		for(byte[] section : data)
			digest.update(section);
		return digest.digest();
	}

	public static PublicKey generatePublicKey(byte[] encodedKey) throws GeneralSecurityException {
		X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedKey);
		KeyFactory factory = KeyFactory.getInstance("RSA");
		return factory.generatePublic(spec);
	}

	public static SecretKey createSecretKey(PrivateKey key, byte[] data) throws GeneralSecurityException {
		return new SecretKeySpec(cipher(2, key, data), "AES");
	}

	public static byte[] cipher(int method, Key key, byte[] data) throws GeneralSecurityException {
		Cipher cipher = Cipher.getInstance(key.getAlgorithm());
		cipher.init(method, key);
		return cipher.doFinal(data);
	}

	public static OutputStream encryptOutputStream(OutputStream outputStream, SecretKey key) {
		return new CipherOutputStream(outputStream, createBlockCipher(key, true));
	}

	public static InputStream decryptInputStream(InputStream inputStream, SecretKey key) {
		return new CipherInputStream(inputStream, createBlockCipher(key, false));
	}

	public static BufferedBlockCipher createBlockCipher(SecretKey key, boolean out) {
		BufferedBlockCipher blockCipher = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
		blockCipher.init(out, new ParametersWithIV(new KeyParameter(key.getEncoded()), key.getEncoded(), 0, 16));
		return blockCipher;
	}
}