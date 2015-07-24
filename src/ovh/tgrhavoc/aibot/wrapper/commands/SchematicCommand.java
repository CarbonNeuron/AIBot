package ovh.tgrhavoc.aibot.wrapper.commands;

import ovh.tgrhavoc.aibot.schematic.Schematic;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;

public class SchematicCommand extends AbstractCommand {
	
	public SchematicCommand(MinecraftBotWrapper bot) {
		super(bot, "schematic", "Build a schematic", "<name>", "(?i)[\\w]+");
	}

	@Override
	public void execute(String[] args) {
		String name = args[0];
		boolean isLocal;
		
		if(!name.endsWith(".schematic"))
			name = name + ".schematic";

		isLocal = !(getClass().getClassLoader().getResource("schematics/" + name) == null);
		
		@SuppressWarnings("unused")
		Schematic toBuild = new Schematic(name, isLocal);
		
		bot.say("Loaded schematic");
		
	}

}
