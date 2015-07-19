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

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.event.protocol.client.EntityHitEvent;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.ArtType;

public class PaintingEntity extends Entity {
	private final ArtType artType;
	private int direction;

	public PaintingEntity(World world, int id, ArtType artType) {
		super(world, id);
		this.artType = artType;
	}

	public ArtType getArtType() {
		return artType;
	}

	public int getBlockX() {
		return (int) getX();
	}

	public int getBlockY() {
		return (int) getY();
	}

	public int getBlockZ() {
		return (int) getZ();
	}

	public int getDirection() {
		return direction;
	}

	@Override
	public void setX(double x) {
		super.setX((int) x);
	}

	@Override
	public void setY(double y) {
		super.setY((int) y);
	}

	@Override
	public void setZ(double z) {
		super.setZ((int) z);
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public void breakPainting() {
		MinecraftBot bot = world.getBot();
		bot.getEventBus().fire(new EntityHitEvent(this));
	}
}
