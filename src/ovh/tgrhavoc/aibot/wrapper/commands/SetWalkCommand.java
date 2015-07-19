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
package ovh.tgrhavoc.aibot.wrapper.commands;

import ovh.tgrhavoc.aibot.ai.WalkActivity;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class SetWalkCommand extends AbstractCommand {

	public SetWalkCommand(MinecraftBotWrapper bot) {
		super(bot, "walksettings", "Change walk settings",
				"<speed> [jump-factor [fall-factor [liquid-factor]]]",
				"[-]?[0-9]+(\\.[0-9]+)?" + "( [-]?[0-9]+(\\.[0-9]+)?"
						+ "( [-]?[0-9]+(\\.[0-9]+)?"
						+ "( [-]?[0-9]+(\\.[0-9]+)?)?)?)?");
	}

	@Override
	public void execute(String[] args) {
		controller.say("Set walk settings!");
		WalkActivity.setDefaultSpeed(Double.parseDouble(args[0]));
		if(args.length == 1)
			return;
		WalkActivity.setDefaultJumpFactor(Double.parseDouble(args[1]));
		if(args.length == 2)
			return;
		WalkActivity.setDefaultFallFactor(Double.parseDouble(args[2]));
		if(args.length == 3)
			return;
		WalkActivity.setDefaultLiquidFactor(Double.parseDouble(args[3]));
	}
}
