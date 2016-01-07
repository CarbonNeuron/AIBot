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

import static ovh.tgrhavoc.aibot.world.block.BlockType.Flag.*;
import static ovh.tgrhavoc.aibot.world.item.ToolType.*;
import ovh.tgrhavoc.aibot.world.item.ToolType;

public enum BlockType {
	UNKNOWN(-1),

	AIR(0, 0),
	STONE(1, PICKAXE),
	GRASS(2, SHOVEL),
	DIRT(3, SHOVEL),
	COBBLESTONE(4, PICKAXE),
	WOOD(5, AXE),
	SAPLING(6, INTERACTABLE),
	BEDROCK(7, SOLID | PLACEABLE | INDESTRUCTABLE),
	WATER(8, INDESTRUCTABLE),
	STATIONARY_WATER(9, INDESTRUCTABLE),
	LAVA(10, INDESTRUCTABLE),
	STATIONARY_LAVA(11, INDESTRUCTABLE),
	SAND(12, SHOVEL),
	GRAVEL(13, SHOVEL),
	GOLD_ORE(14, PICKAXE),
	IRON_ORE(15, PICKAXE),
	COAL_ORE(16, PICKAXE),
	LOG(17, AXE),
	LEAVES(18, SHEARS),
	SPONGE(19),
	GLASS(20, PICKAXE),
	LAPIS_ORE(21, PICKAXE),
	LAPIS_BLOCK(22, PICKAXE),
	DISPENSER(23, SOLID | INTERACTABLE | PLACEABLE, PICKAXE),
	SANDSTONE(24, PICKAXE),
	NOTE_BLOCK(25, SOLID | INTERACTABLE | PLACEABLE, PICKAXE),
	BED_BLOCK(26, SOLID | INTERACTABLE | PLACEABLE),
	POWERED_RAIL(27, PLACEABLE, PICKAXE),
	DETECTOR_RAIL(28, PLACEABLE, PICKAXE),
	PISTON_STICKY_BASE(29, PICKAXE),
	WEB(30, PLACEABLE, SWORD),
	LONG_GRASS(31, PLACEABLE),
	DEAD_BUSH(32, PLACEABLE),
	PISTON_BASE(33, PICKAXE),
	PISTON_EXTENSION(34, PICKAXE),
	WOOL(35, SWORD),
	PISTON_MOVING_PIECE(36),
	YELLOW_FLOWER(37, PLACEABLE),
	RED_ROSE(38, PLACEABLE),
	BROWN_MUSHROOM(39, PLACEABLE),
	RED_MUSHROOM(40, PLACEABLE),
	GOLD_BLOCK(41, PICKAXE),
	IRON_BLOCK(42, PICKAXE),
	DOUBLE_STEP(43, PICKAXE),
	STEP(44, PICKAXE),
	BRICK(45, PICKAXE),
	TNT(46),
	BOOKSHELF(47, AXE),
	MOSSY_COBBLESTONE(48, PICKAXE),
	OBSIDIAN(49, PICKAXE),
	TORCH(50, PLACEABLE),
	FIRE(51, INDESTRUCTABLE),
	MOB_SPAWNER(52, PICKAXE),
	WOOD_STAIRS(53, AXE),
	CHEST(54, SOLID | INTERACTABLE | PLACEABLE, AXE),
	REDSTONE_WIRE(55, PLACEABLE),
	DIAMOND_ORE(56, PICKAXE),
	DIAMOND_BLOCK(57, PICKAXE),
	WORKBENCH(58, SOLID | INTERACTABLE | PLACEABLE, AXE),
	CROPS(59, PLACEABLE),
	SOIL(60, SHOVEL),
	FURNACE(61, SOLID | INTERACTABLE | PLACEABLE, PICKAXE),
	BURNING_FURNACE(62, SOLID | INTERACTABLE | PLACEABLE, PICKAXE),
	SIGN_POST(63, PLACEABLE, 16, AXE),
	WOODEN_DOOR(64, SOLID | INTERACTABLE | PLACEABLE, AXE),
	LADDER(65, PLACEABLE),
	RAILS(66, PLACEABLE, PICKAXE),
	COBBLESTONE_STAIRS(67, PICKAXE),
	WALL_SIGN(68, PLACEABLE, AXE),
	LEVER(69, INTERACTABLE | PLACEABLE),
	STONE_PLATE(70, PLACEABLE, PICKAXE),
	IRON_DOOR_BLOCK(71, PICKAXE),
	WOOD_PLATE(72, PLACEABLE, AXE),
	REDSTONE_ORE(73, PICKAXE),
	GLOWING_REDSTONE_ORE(74, PICKAXE),
	REDSTONE_TORCH_OFF(75, PLACEABLE),
	REDSTONE_TORCH_ON(76, PLACEABLE),
	STONE_BUTTON(77, INTERACTABLE | PLACEABLE, PICKAXE),
	SNOW(78, PLACEABLE, SHOVEL),
	ICE(79, PICKAXE),
	SNOW_BLOCK(80, SHOVEL),
	CACTUS(81),
	CLAY(82, SHOVEL),
	SUGAR_CANE_BLOCK(83, PLACEABLE),
	JUKEBOX(84, PICKAXE),
	FENCE(85, AXE),
	PUMPKIN(86, AXE),
	NETHERRACK(87, PICKAXE),
	SOUL_SAND(88, SHOVEL),
	GLOWSTONE(89, PICKAXE),
	PORTAL(90, PLACEABLE | INDESTRUCTABLE),
	JACK_O_LANTERN(91, AXE),
	CAKE_BLOCK(92),
	DIODE_BLOCK_OFF(93, INTERACTABLE | PLACEABLE),
	DIODE_BLOCK_ON(94, INTERACTABLE | PLACEABLE),
	LOCKED_CHEST(95, AXE),
	TRAP_DOOR(96, AXE),
	MONSTER_EGGS(97),
	SMOOTH_BRICK(98, PICKAXE),
	HUGE_MUSHROOM_1(99, AXE),
	HUGE_MUSHROOM_2(100, AXE),
	IRON_FENCE(101, PICKAXE),
	THIN_GLASS(102, PICKAXE),
	MELON_BLOCK(103, AXE),
	PUMPKIN_STEM(104, PLACEABLE),
	MELON_STEM(105, PLACEABLE),
	VINE(106, PLACEABLE),
	FENCE_GATE(107, AXE),
	BRICK_STAIRS(108, PICKAXE),
	SMOOTH_STAIRS(109, PICKAXE),
	MYCEL(110, SHOVEL),
	WATER_LILY(111),
	NETHER_BRICK(112, PICKAXE),
	NETHER_FENCE(113, PICKAXE),
	NETHER_BRICK_STAIRS(114, PICKAXE),
	NETHER_WARTS(115, PLACEABLE),
	ENCHANTMENT_TABLE(116, PICKAXE),
	BREWING_STAND(117, PICKAXE),
	CAULDRON(118, PICKAXE),
	ENDER_PORTAL(119, INDESTRUCTABLE),
	ENDER_PORTAL_FRAME(120, INDESTRUCTABLE | SOLID | PLACEABLE),
	ENDER_STONE(121, PICKAXE),
	DRAGON_EGG(122, SOLID | INTERACTABLE),
	REDSTONE_LAMP_OFF(123, PICKAXE),
	REDSTONE_LAMP_ON(124, PICKAXE),
	WOOD_DOUBLE_STEP(125, AXE),
	WOOD_STEP(126, AXE),
	COCOA(127),
	SANDSTONE_STAIRS(128, PICKAXE),
	EMERALD_ORE(129, PICKAXE),
	ENDER_CHEST(130, INDESTRUCTABLE | INTERACTABLE | SOLID | PLACEABLE),
	TRIPWIRE_HOOK(131, INTERACTABLE | PLACEABLE),
	TRIPWIRE(132, PLACEABLE),
	EMERALD_BLOCK(133, PICKAXE),
	SPRUCE_WOOD_STAIRS(134, AXE),
	BIRCH_WOOD_STAIRS(135, AXE),
	JUNGLE_WOOD_STAIRS(136, AXE),
	COMMAND(137),
	BEACON(138),
	COBBLE_WALL(139, PICKAXE),
	FLOWER_POT(140),
	CARROT(141, PLACEABLE),
	POTATO(142, PLACEABLE),
	WOOD_BUTTON(143, INTERACTABLE | PLACEABLE, AXE),
	SKULL(144),
	ANVIL(145, PICKAXE);

	private final int id, maxStack, flags;
	private final ToolType toolType;

	private BlockType(int id) {
		this(id, SOLID | PLACEABLE);
	}

	private BlockType(int id, ToolType toolType) {
		this(id, SOLID | PLACEABLE, 64, toolType);
	}

	private BlockType(int id, int flags) {
		this(id, flags, 64);
	}

	private BlockType(int id, int flags, ToolType toolType) {
		this(id, flags, 64, toolType);
	}

	private BlockType(int id, int flags, int maxStack) {
		this(id, flags, maxStack, null);
	}

	private BlockType(int id, int flags, int maxStack, ToolType toolType) {
		this.id = id;
		this.flags = flags;
		this.maxStack = maxStack;
		this.toolType = toolType;
	}

	public int getId() {
		return id;
	}

	public int getMaxStack() {
		return maxStack;
	}

	public boolean isSolid() {
		return (flags & SOLID) == SOLID;
	}

	public boolean isInteractable() {
		return (flags & INTERACTABLE) == INTERACTABLE;
	}

	public boolean isPlaceable() {
		return (flags & PLACEABLE) == PLACEABLE;
	}

	public boolean isIndestructable() {
		return (flags & INDESTRUCTABLE) == INDESTRUCTABLE;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public static BlockType getById(int id) {
		for(BlockType type : values())
			if(type.getId() == id)
				return type;
		return UNKNOWN;
	}

	protected static final class Flag {
		public static final int SOLID = 1, INTERACTABLE = 2, PLACEABLE = 4, INDESTRUCTABLE = 8;
	}
}
