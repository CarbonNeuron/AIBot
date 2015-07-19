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

public interface Protocol<H extends PacketHeader> {
	// Pre-1.7
	public static final int V1_5_2 = 61;
	public static final int V1_6_1 = 72;
	public static final int V1_6_2 = 74;
	public static final int V1_6_4 = 78;

	// Post-1.7 - X protocols
	public static final int V1_7_2 = 4;

	public int getVersion();

	public H readHeader(DataInputStream in) throws IOException;

	public H createHeader(Packet packet, byte[] data);

	public Packet createPacket(H header);

	public int[] getPacketIds();
}
