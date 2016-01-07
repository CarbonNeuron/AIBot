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
package ovh.tgrhavoc.aibot.world.entity;

import ovh.tgrhavoc.aibot.IntHashMap;
import ovh.tgrhavoc.aibot.world.World;

public class EndermanEntity extends AggressiveEntity {
	protected int heldItemId;
	protected boolean aggravated = false;

	public EndermanEntity(World world, int id) {
		super(world, id);
	}

	public boolean isAggravated() {
		return aggravated;
	}

	public int getHeldItemId() {
		return heldItemId;
	}

	public void setAggravated(boolean aggravated) {
		this.aggravated = aggravated;
	}

	public void setHeldItemId(int heldItemId) {
		this.heldItemId = heldItemId;
	}

	@Override
	public void updateMetadata(IntHashMap<WatchableObject> metadata) {
		super.updateMetadata(metadata);
		if(metadata.containsKey(16))
			setHeldItemId((Byte) metadata.get(16).getObject());
		if(metadata.containsKey(17))
			setAggravated(((Byte) metadata.get(17).getObject()).byteValue() == 1);
	}
}
