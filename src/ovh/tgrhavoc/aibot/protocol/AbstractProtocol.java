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
import java.lang.reflect.Constructor;
import java.util.Arrays;

import ovh.tgrhavoc.aibot.IntHashMap;

public abstract class AbstractProtocol implements Protocol<PacketHeader> {
	private final int version;

	private final IntHashMap<Class<? extends Packet>> packets;

	public AbstractProtocol(int version) {
		this.version = version;

		packets = new IntHashMap<>(0xFF);
	}

	protected final void register(Class<? extends Packet> packetClass) {
		if(packetClass == null)
			throw new NullPointerException();
		Constructor<? extends Packet> constructor;
		try {
			constructor = packetClass.getConstructor();
		} catch(Exception exception) {
			throw new IllegalArgumentException("No default constructor for " + packetClass.getSimpleName());
		}
		Packet packet;
		try {
			packet = constructor.newInstance();
		} catch(Exception exception) {
			throw new IllegalArgumentException(exception);
		}
		int id = packet.getId();
		if(packets.get(id) != null)
			throw new IllegalArgumentException("Duplicate packet ID " + id);
		packets.put(id, packetClass);
	}

	@Override
	public PacketHeader readHeader(DataInputStream in) throws IOException {
		return new PacketHeader(in.readInt());
	}

	@Override
	public PacketHeader createHeader(Packet packet, byte[] data) {
		return new PacketHeader(packet.getId());
	}

	@Override
	public Packet createPacket(PacketHeader header) {
		try {
			return packets.get(header.getId()).newInstance();
		} catch(Exception exception) {
			return null;
		}
	}

	@Override
	public int[] getPacketIds() {
		int[] ids = new int[256];
		int length = 0;
		for(int i = 0; i < 256; i++)
			if(packets.get(i) != null)
				ids[length++] = i;
		return Arrays.copyOfRange(ids, 0, length);
	}

	@Override
	public int getVersion() {
		return version;
	}
}
