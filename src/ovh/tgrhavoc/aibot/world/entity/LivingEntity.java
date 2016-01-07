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
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public abstract class LivingEntity extends Entity {
	protected int health, breathTimer, growthTimer, potionEffectColor;
	protected boolean onFire, crouching, riding, sprinting, performingAction;
	protected double headYaw;

	public LivingEntity(World world, int id) {
		super(world, id);
	}

	public int getHealth() {
		return health;
	}

	public int getBreathTimer() {
		return breathTimer;
	}

	public int getGrowthTimer() {
		return growthTimer;
	}

	public int getPotionEffectColor() {
		return potionEffectColor;
	}

	public boolean isOnFire() {
		return onFire;
	}

	public boolean isCrouching() {
		return crouching;
	}

	public boolean isSprinting() {
		return sprinting;
	}

	public boolean isRiding() {
		return riding;
	}

	public boolean isPerformingAction() {
		return performingAction;
	}

	public ItemStack getWornItemAt(int slot) {
		return null;
	}

	public double getHeadYaw() {
		return headYaw;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setBreathTimer(int breathTimer) {
		this.breathTimer = breathTimer;
	}

	public void setGrowthTimer(int growthTimer) {
		this.growthTimer = growthTimer;
	}

	public void setPotionEffectColor(int potionEffectColor) {
		this.potionEffectColor = potionEffectColor;
	}

	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
	}

	public void setCrouching(boolean crouching) {
		this.crouching = crouching;
	}

	public void setSprinting(boolean sprinting) {
		this.sprinting = sprinting;
	}

	public void setRiding(boolean riding) {
		this.riding = riding;
	}

	public void setPerformingAction(boolean performingAction) {
		this.performingAction = performingAction;
	}

	public void setWornItemAt(int slot, ItemStack item) {
	}

	public void setHeadYaw(double headYaw) {
		this.headYaw = headYaw;
	}

	@Override
	public void updateMetadata(IntHashMap<WatchableObject> metadata) {
		if(metadata.containsKey(0)) {
			byte flags = (Byte) metadata.get(0).getObject();
			setOnFire((flags & 1) != 0);
			setCrouching((flags & 2) != 0);
			setRiding((flags & 4) != 0);
			setSprinting((flags & 8) != 0);
			setPerformingAction((flags & 16) != 0);
		}

		if(metadata.containsKey(1))
			setBreathTimer((Short) metadata.get(1).getObject());
		if(metadata.containsKey(8))
			setPotionEffectColor((Integer) metadata.get(8).getObject());
		if(metadata.containsKey(12))
			setGrowthTimer((Integer) metadata.get(12).getObject());
	}
}
