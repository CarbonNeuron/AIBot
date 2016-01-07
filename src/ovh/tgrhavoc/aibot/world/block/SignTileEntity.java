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
package ovh.tgrhavoc.aibot.world.block;

import ovh.tgrhavoc.aibot.nbt.NBTTagCompound;

public class SignTileEntity extends TileEntity {
	private final String[] text;

	public SignTileEntity(NBTTagCompound nbt) {
		super(nbt);
		text = new String[4];
		for(int i = 0; i < 4; i++)
			text[i] = nbt.getString("Text" + (i + 1));
	}

	public SignTileEntity(int x, int y, int z, String[] text) {
		super(x, y, z);
		this.text = text.clone();
	}

	public SignTileEntity(BlockLocation location, String[] text) {
		super(location);
		this.text = text.clone();
	}

	public String[] getText() {
		return text.clone();
	}
}
