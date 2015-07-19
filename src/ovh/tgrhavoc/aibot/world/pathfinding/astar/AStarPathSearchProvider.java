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
package ovh.tgrhavoc.aibot.world.pathfinding.astar;

import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.block.BlockLocation;
import ovh.tgrhavoc.aibot.world.pathfinding.*;

public class AStarPathSearchProvider implements PathSearchProvider {
	private final Heuristic heuristic;
	private final WorldPhysics worldPhysics;

	private final World world;

	public AStarPathSearchProvider(Heuristic heuristic, WorldPhysics worldPhysics) {
		this.heuristic = heuristic;
		this.worldPhysics = worldPhysics;

		world = worldPhysics.getWorld();
	}

	@Override
	public AStarPathSearch provideSearch(BlockLocation start, BlockLocation end) {
		return new AStarPathSearch(this, start, end);
	}

	@Override
	public Heuristic getHeuristic() {
		return heuristic;
	}

	@Override
	public WorldPhysics getWorldPhysics() {
		return worldPhysics;
	}

	@Override
	public World getWorld() {
		return world;
	}
}
