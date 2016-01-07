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
package ovh.tgrhavoc.aibot.world;

public enum WorldType {
	DEFAULT("default"),
	FLAT("flat"),
	DEFAULT_1_1("default_1_1"),
	LARGE_BIOMES("largeBiomes"),
	AMPLIFIED("amplified");

	private final String name;

	private WorldType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static WorldType parseWorldType(String name) {
		for(WorldType worldType : values())
			if(worldType.getName().equals(name))
				return worldType;
		return null;
	}
}