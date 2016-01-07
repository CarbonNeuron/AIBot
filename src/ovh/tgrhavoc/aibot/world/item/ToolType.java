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
package ovh.tgrhavoc.aibot.world.item;


public enum ToolType {
	SWORD(268, 272, 267, 283, 276),
	PICKAXE(270, 274, 257, 285, 278),
	SHOVEL(269, 273, 256, 284, 277),
	AXE(271, 275, 258, 286, 279),
	HOE(290, 291, 292, 294, 293),
	SHEARS(359);

	private final int[] ids;

	private ToolType(int... ids) {
		this.ids = ids;
	}

	public int[] getIds() {
		return ids.clone();
	}

	public static ToolType getById(int id) {
		for(ToolType type : values())
			for(int typeId : type.ids)
				if(id == typeId)
					return type;
		return null;
	}
}
