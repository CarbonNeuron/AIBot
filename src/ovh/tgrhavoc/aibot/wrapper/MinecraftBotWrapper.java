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
package ovh.tgrhavoc.aibot.wrapper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.*;

import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.ai.TaskManager;
import ovh.tgrhavoc.aibot.event.*;
import ovh.tgrhavoc.aibot.event.general.DisconnectEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.RequestRespawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.*;
import ovh.tgrhavoc.aibot.event.world.SpawnEvent;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.world.item.PlayerInventory;
import ovh.tgrhavoc.aibot.wrapper.backend.Backend;
import ovh.tgrhavoc.aibot.wrapper.commands.*;

public abstract class MinecraftBotWrapper implements EventListener {
	protected final MinecraftBot bot;
	protected final CommandManager commandManager;

	private final List<Backend> backends = new CopyOnWriteArrayList<>();
	private final List<String> owners = new CopyOnWriteArrayList<>();

	public MinecraftBotWrapper(MinecraftBot bot) {
		this.bot = bot;

		commandManager = new BasicCommandManager(this);
		bot.getEventBus().register(this);
	}

	@EventHandler
	public void onChatReceived(ChatReceivedEvent event) {
		String message = Util.stripColors(event.getMessage());
		System.out.println("[" + bot.getSession().getUsername() + "]> " + message);
		String nocheat = "Please type '([^']*)' to continue sending messages/commands\\.";
		Matcher nocheatMatcher = Pattern.compile(nocheat).matcher(message);
		if(nocheatMatcher.matches()) {
			try {
				String captcha = nocheatMatcher.group(1);
				bot.say(captcha);
			} catch(Exception exception) {
				exception.printStackTrace();
			}
		} else if(message.contains("teleport to")) {
			for(String owner : owners) {
				if(message.contains(owner)) {
					bot.say("/tpaccept");
					break;
				}
			}
		} else if(message.startsWith("/uc "))
			bot.say(message);
	}

	@EventHandler
	public void onHealthUpdate(HealthUpdateEvent event) {
		if(event.getHealth() <= 0)
			bot.getEventBus().fire(new RequestRespawnEvent());
	}

	@EventHandler
	public void onRespawn(RespawnEvent event) {
		TaskManager taskManager = bot.getTaskManager();
		taskManager.stopAll();
		bot.setActivity(null);
	}

	@EventHandler
	public void onSpawn(SpawnEvent event) {
		MainPlayerEntity player = event.getPlayer();
		PlayerInventory inventory = player.getInventory();
		inventory.setDelay(250);
	}

	@EventHandler
	public void onDisconnect(DisconnectEvent event) {
		System.out.println("[" + bot.getSession().getUsername() + "] Disconnected: " + event.getReason());
	}

	public void say(String message) {
		for(Backend backend : backends)
			backend.say(message);
	}

	public CommandManager getCommandManager() {
		return commandManager;
	}

	public String[] getOwners() {
		return owners.toArray(new String[0]);
	}

	public void addOwner(String owner) {
		owners.add(owner);
	}

	public void removeOwner(String owner) {
		owners.remove(owner);
	}

	public boolean isOwner(String username) {
		for(String owner : owners)
			if(owner.equals(username))
				return true;
		return false;
	}

	public void addBackend(Backend backend) {
		backend.enable();
		backends.add(backend);
	}

	public void removeBackend(Backend backend) {
		backends.remove(backend);
		backend.disable();
	}

	public Backend[] getBackends() {
		return backends.toArray(new Backend[backends.size()]);
	}

	public final MinecraftBot getBot() {
		return bot;
	}
}