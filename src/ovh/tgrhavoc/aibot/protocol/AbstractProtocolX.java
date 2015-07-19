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
import java.util.*;

import ovh.tgrhavoc.aibot.IntHashMap;

public abstract class AbstractProtocolX implements ProtocolX<PacketLengthHeader> {
	private final int version;
	private State state;

	private final Map<State, IntHashMap<Class<? extends Packet>>> packets;

	public AbstractProtocolX(int version) {
		this.version = version;

		packets = new EnumMap<>(State.class);
		for(State state : State.values())
			packets.put(state, new IntHashMap<Class<? extends Packet>>(0xFF));
		state = State.HANDSHAKE;
	}

	protected final void register(State state, Class<? extends Packet> packetClass) {
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
		packets.get(state).put(id, packetClass);
	}

	@Override
	public PacketLengthHeader readHeader(DataInputStream in) throws IOException {
		int length = AbstractPacketX.readVarInt(in), id = AbstractPacketX.readVarInt(in);
		return new PacketLengthHeader(id, length);
	}

	@Override
	public PacketLengthHeader createHeader(Packet packet, byte[] data) {
		return new PacketLengthHeader(packet.getId(), AbstractPacketX.varIntLength(packet.getId()) + data.length);
	}

	@Override
	public Packet createPacket(PacketLengthHeader header) {
		try {
			IntHashMap<Class<? extends Packet>> statePackets = packets.get(state);
			return statePackets.get(header.getId()).newInstance();
		} catch(Exception exception) {
			return null;
		}
	}

	@Override
	public int[] getPacketIds() {
		IntHashMap<Class<? extends Packet>> statePackets = packets.get(state);
		int[] ids = new int[256];
		int length = 0;
		for(int i = 0; i < 256; i++)
			if(statePackets.get(i) != null)
				ids[length++] = i;
		return Arrays.copyOfRange(ids, 0, length);
	}

	@Override
	public State getState() {
		return state;
	}

	protected void setState(State state) {
		this.state = state;
	}

	@Override
	public int getVersion() {
		return version;
	}
}
