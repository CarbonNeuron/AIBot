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

import java.io.IOException;

import javax.crypto.SecretKey;

public interface ConnectionHandler {
	public void sendPacket(WriteablePacket packet);

	public void process();

	public boolean supportsEncryption();

	public SecretKey getSharedKey() throws UnsupportedOperationException;

	public void setSharedKey(SecretKey sharedKey) throws UnsupportedOperationException;

	public void enableEncryption() throws UnsupportedOperationException;

	public void enableDecryption() throws UnsupportedOperationException;

	public boolean isEncrypting();

	public boolean isDecrypting();

	public boolean supportsPausing();

	public void pauseReading() throws UnsupportedOperationException;

	public void pauseWriting() throws UnsupportedOperationException;

	public void resumeReading() throws UnsupportedOperationException;

	public void resumeWriting() throws UnsupportedOperationException;

	public boolean isReadingPaused();

	public boolean isWritingPaused();

	public void connect() throws IOException;

	public void disconnect(String reason);

	public boolean isConnected();

	public String getServer();

	public int getPort();

	public Protocol<?> getProtocol();
}
