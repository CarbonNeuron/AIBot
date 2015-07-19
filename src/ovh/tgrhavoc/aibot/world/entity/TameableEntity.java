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

import ovh.tgrhavoc.aibot.world.World;

public abstract class TameableEntity extends LivingEntity {
	protected String ownerName;
	protected boolean sitting, aggressive, tamed;

	public TameableEntity(World world, int id) {
		super(world, id);
	}

	public String getOwnerName() {
		return ownerName;
	}

	public boolean isSitting() {
		return sitting;
	}

	public boolean isAggressive() {
		return aggressive;
	}

	public boolean isTamed() {
		return tamed;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setSitting(boolean sitting) {
		this.sitting = sitting;
	}

	public void setAggressive(boolean aggressive) {
		this.aggressive = aggressive;
	}

	public void setTamed(boolean tamed) {
		this.tamed = tamed;
	}
}
