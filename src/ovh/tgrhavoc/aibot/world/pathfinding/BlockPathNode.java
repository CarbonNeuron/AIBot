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
package ovh.tgrhavoc.aibot.world.pathfinding;

import ovh.tgrhavoc.aibot.world.block.BlockLocation;

public class BlockPathNode implements PathNode {
	private final BlockLocation location;
	private final PathSearch source;

	private PathNode previous, next;

	private double cost = Integer.MAX_VALUE, costEstimate = Integer.MAX_VALUE;

	public BlockPathNode(PathSearch source, BlockLocation location) {
		this(source, location, null, null);
	}

	public BlockPathNode(PathSearch source, BlockLocation location, PathNode previous, PathNode next) {
		this.location = location;
		this.source = source;
		this.previous = previous;
		this.next = next;
	}

	@Override
	public BlockLocation getLocation() {
		return location;
	}

	@Override
	public PathNode getNext() {
		return next;
	}

	@Override
	public PathNode getPrevious() {
		return previous;
	}

	@Override
	public void setNext(PathNode next) {
		this.next = next;
	}

	@Override
	public void setPrevious(PathNode previous) {
		this.previous = previous;
	}

	@Override
	public boolean isStart() {
		return previous == null;
	}

	@Override
	public boolean isEnd() {
		return next == null;
	}

	@Override
	public PathSearch getSource() {
		return source;
	}

	@Override
	public double getCost() {
		return cost;
	}

	@Override
	public double getCostEstimate() {
		return costEstimate;
	}

	@Override
	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public void setCostEstimate(double costEstimate) {
		this.costEstimate = costEstimate;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof PathNode && location.equals(((PathNode) obj).getLocation());
	}

	@Override
	public String toString() {
		return location.toString() + " Cost=" + cost + " Estimate=" + costEstimate;
	}
}
