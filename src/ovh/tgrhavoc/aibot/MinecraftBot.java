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
package ovh.tgrhavoc.aibot;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;

import ovh.tgrhavoc.aibot.ai.Activity;
import ovh.tgrhavoc.aibot.ai.BasicTaskManager;
import ovh.tgrhavoc.aibot.ai.TaskManager;
import ovh.tgrhavoc.aibot.auth.AuthService;
import ovh.tgrhavoc.aibot.auth.AuthenticationException;
import ovh.tgrhavoc.aibot.auth.InvalidSessionException;
import ovh.tgrhavoc.aibot.auth.LegacyAuthService;
import ovh.tgrhavoc.aibot.auth.OfflineSession;
import ovh.tgrhavoc.aibot.auth.Session;
import ovh.tgrhavoc.aibot.auth.YggdrasilAuthService;
import ovh.tgrhavoc.aibot.event.ConcurrentEventBus;
import ovh.tgrhavoc.aibot.event.EventBus;
import ovh.tgrhavoc.aibot.event.EventHandler;
import ovh.tgrhavoc.aibot.event.EventListener;
import ovh.tgrhavoc.aibot.event.general.DisconnectEvent;
import ovh.tgrhavoc.aibot.event.general.TickEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.ChatSentEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.HandshakeEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.InventoryCloseEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.PlayerMoveEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.PlayerMoveRotateEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.PlayerRotateEvent;
import ovh.tgrhavoc.aibot.event.protocol.client.PlayerUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.ExperienceUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.HealthUpdateEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.KickEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.LoginEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.RespawnEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.TeleportEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.WindowCloseEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.WindowOpenEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.WindowSlotChangeEvent;
import ovh.tgrhavoc.aibot.event.protocol.server.WindowUpdateEvent;
import ovh.tgrhavoc.aibot.event.world.SpawnEvent;
import ovh.tgrhavoc.aibot.protocol.ConnectionHandler;
import ovh.tgrhavoc.aibot.protocol.Protocol;
import ovh.tgrhavoc.aibot.protocol.ProtocolProvider;
import ovh.tgrhavoc.aibot.protocol.ProtocolX;
import ovh.tgrhavoc.aibot.protocol.SocketConnectionHandler;
import ovh.tgrhavoc.aibot.protocol.UnsupportedProtocolException;
import ovh.tgrhavoc.aibot.world.BasicWorld;
import ovh.tgrhavoc.aibot.world.World;
import ovh.tgrhavoc.aibot.world.entity.MainPlayerEntity;
import ovh.tgrhavoc.aibot.world.item.ChestInventory;
import ovh.tgrhavoc.aibot.world.item.Inventory;
import ovh.tgrhavoc.aibot.world.item.ItemStack;

public class MinecraftBot implements EventListener {
	public static final int DEFAULT_PORT = 25565;
	public static final int LATEST_PROTOCOL = -1;
	public static final int MAX_CHAT_LENGTH = 100;
	public static final UUID CLIENT_TOKEN = Util.generateSystemUUID("CLIENT_TOKEN");

	private final ExecutorService service;
	private final EventBus eventBus;
	private final TaskManager taskManager;
	private final ConnectionHandler connectionHandler;
	private final AuthService<?> authService;
	private final Session session;
	private final ProxyData loginProxy, connectProxy;

	private MainPlayerEntity player;
	private World world;

	private boolean hasSpawned = false, movementDisabled = false, muted = false;
	private int messageDelay = 2000;
	private long lastMessage;
	private Activity activity;
	
	private LanguageManager languageManger;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MinecraftBot(Builder builder, LanguageManager manager) throws AuthenticationException, UnsupportedProtocolException, InvalidSessionException, IOException {
		service = Executors.newCachedThreadPool();
		eventBus = new ConcurrentEventBus();
		eventBus.register(this);
		taskManager = new BasicTaskManager(this);

		Protocol<?> protocol;
		if(builder.getProtocol() >= 0) {
			ProtocolProvider<?> provider = ProtocolProvider.getProvider(builder.getProtocol());
			if(provider == null)
				throw new UnsupportedProtocolException(getLanguageManger().getFormattedText("error.unsupportedprotocolexception", builder.getProtocol()));
			protocol = provider.getProtocolInstance(this);
		} else
			protocol = ProtocolProvider.getLatestProvider().getProtocolInstance(this);
		connectionHandler = new SocketConnectionHandler(this, protocol, builder.getServer(), builder.getPort(), builder.getConnectProxy());

		if(builder.getAuthService() != null)
			authService = builder.getAuthService();
		else if(protocol instanceof ProtocolX)
			authService = new YggdrasilAuthService(CLIENT_TOKEN);
		else
			authService = new LegacyAuthService();

		if(builder.getSession() != null)
			authService.validateSession(builder.getSession());
		loginProxy = builder.getLoginProxy();
		connectProxy = builder.getConnectProxy();

		if(StringUtils.isNotBlank(builder.getPassword()) && (builder.getSession() == null || !builder.getSession().isValidForAuthentication()))
			session = authService.login(builder.getUsername(), builder.getPassword(), loginProxy);
		else if(builder.getSession() != null)
			session = builder.getSession();
		else
			session = new OfflineSession(builder.getUsername());

		connectionHandler.connect();

		eventBus.fire(new HandshakeEvent(session, connectionHandler.getServer(), connectionHandler.getPort()));
		new TickHandler();
	}

	@EventHandler
	public void onLogin(LoginEvent event) {
		setWorld(new BasicWorld(this, event.getWorldType(), event.getDimension(), event.getDifficulty(), event.getWorldHeight()));
		player = new MainPlayerEntity(world, event.getPlayerId(), session.getUsername(), event.getGameMode());
		world.spawnEntity(player);
	}

	@EventHandler
	public void onRespawn(RespawnEvent event) {
		setWorld(new BasicWorld(this, event.getWorldType(), event.getRespawnDimension(), event.getDifficulty(), event.getWorldHeight()));
		player.setGameMode(event.getGameMode());
	}

	@EventHandler
	public void onTeleport(TeleportEvent event) {
		player.setX(event.getX());
		player.setY(event.getY());
		player.setZ(event.getZ());
		player.setYaw(event.getYaw());
		player.setPitch(event.getPitch());
		if(!hasSpawned) {
			eventBus.fire(new SpawnEvent(player));
			hasSpawned = true;
		}
	}

	@EventHandler
	public void onHealthUpdate(HealthUpdateEvent event) {
		player.setHealth(event.getHealth());
		player.setHunger(event.getHunger());
	}

	@EventHandler
	public void onExperienceUpdate(ExperienceUpdateEvent event) {
		player.setExperienceLevel(event.getExperienceLevel());
		player.setExperienceTotal(event.getExperienceTotal());
	}

	@EventHandler
	public void onWindowOpen(WindowOpenEvent event) {
		if(player == null)
			return;
		System.out.println("Opened inventory " + event.getInventoryType() + ": " + event.getSlotCount() + " slots");
		switch(event.getInventoryType()) {
		case CHEST:
			player.setWindow(new ChestInventory(this, event.getWindowId(), event.getSlotCount() == 27 ? false : true));
			break;
		default:
		}
	}

	@EventHandler
	public void onWindowClose(WindowCloseEvent event) {
		if(player == null)
			return;
		player.setWindow(null);
	}

	@EventHandler
	public void onWindowSlotChange(WindowSlotChangeEvent event) {
		if(player == null)
			return;
		Inventory window = player.getWindow();
		if(event.getWindowId() != 0 && (window == null || event.getWindowId() != window.getWindowId()))
			return;
		if(event.getWindowId() == 0)
			player.getInventory().setItemFromServerAt(event.getSlot(), event.getNewItem());
		else
			window.setItemFromServerAt(event.getSlot(), event.getNewItem());
	}

	@EventHandler
	public void onWindowUpdate(WindowUpdateEvent event) {
		if(player == null)
			return;
		Inventory window = player.getWindow();
		if(event.getWindowId() != 0 && (window == null || event.getWindowId() != window.getWindowId()))
			return;
		ItemStack[] items = event.getItems();
		if(event.getWindowId() == 0)
			for(int i = 0; i < items.length; i++)
				player.getInventory().setItemFromServerAt(i, items[i]);
		else
			for(int i = 0; i < items.length; i++)
				window.setItemFromServerAt(i, items[i]);
	}

	@EventHandler
	public void onKick(KickEvent event) {
		connectionHandler.disconnect("Kicked: " + event.getReason());
	}

	public synchronized void runTick() {
		try {
			if (connectionHandler.isConnected())
				connectionHandler.process();
			
			if(hasSpawned) {
				taskManager.update();
				if(activity != null) {
					if(activity.isActive()) {
						activity.run();
						if(!activity.isActive()) {
							activity.stop();
							activity = null;
						}
					} else {
						activity.stop();
						activity = null;
					}
				}

				if(!movementDisabled)
					updateMovement();
			}

			eventBus.fire(new TickEvent());
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	public synchronized void updateMovement() {
		double x = player.getX(), y = player.getY(), z = player.getZ(), yaw = player.getYaw(), pitch = player.getPitch();
		boolean move = x != player.getLastX() || y != player.getLastY() || z != player.getLastZ();
		boolean rotate = yaw != player.getLastYaw() || pitch != player.getLastPitch();
		boolean onGround = player.isOnGround();

		PlayerUpdateEvent event;
		if(move && rotate)
			event = new PlayerMoveRotateEvent(player, x, y, z, yaw, pitch, onGround);
		else if(move)
			event = new PlayerMoveEvent(player, x, y, z, onGround);
		else if(rotate)
			event = new PlayerRotateEvent(player, yaw, pitch, onGround);
		else
			event = new PlayerUpdateEvent(player, onGround);
		eventBus.fire(event);

		player.setLastX(player.getX());
		player.setLastY(player.getY());
		player.setLastZ(player.getZ());
		player.setLastYaw(player.getYaw());
		player.setLastPitch(player.getPitch());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(player == null)
			return;
		if(event.getInventory().getWindowId() != 0)
			player.setWindow(null);
	}

	@EventHandler
	public synchronized void onDisconnect(DisconnectEvent event) {
		service.shutdownNow();
		eventBus.clearListeners();
		hasSpawned = false;
		player = null;
		world = null;
	}

	public synchronized void say(String message) {
		if (muted)
			return;
		while(message.length() > MAX_CHAT_LENGTH) {
			long elapsed = System.currentTimeMillis() - lastMessage;
			if(elapsed < messageDelay) {
				try {
					Thread.sleep(messageDelay - elapsed);
				} catch(InterruptedException e) {}
			}
			String part = message.substring(0, MAX_CHAT_LENGTH);
			System.out.println("Attempting to say: " + message);
			eventBus.fire(new ChatSentEvent(part));
			message = message.substring(part.length());
			lastMessage = System.currentTimeMillis();
		}
		if(!message.isEmpty()) {
			long elapsed = System.currentTimeMillis() - lastMessage;
			if(elapsed < messageDelay) {
				try {
					Thread.sleep(messageDelay - elapsed);
				} catch(InterruptedException e) {}
			}
			System.out.println("Attempting to say: " + message);
			eventBus.fire(new ChatSentEvent(message));
			lastMessage = System.currentTimeMillis();
		}
	}

	public boolean hasSpawned() {
		return hasSpawned;
	}

	public synchronized World getWorld() {
		return world;
	}

	public synchronized void setWorld(World world) {
		if(this.world != null)
			this.world.destroy();
		this.world = world;
		if(player != null) {
			if(world != null)
				player = new MainPlayerEntity(world, player.getId(), player.getName(), player.getGameMode());
			else
				player = null;
			world.spawnEntity(player);
		}
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		if(activity == null && this.activity != null)
			this.activity.stop();
		this.activity = activity;
	}

	public boolean hasActivity() {
		return activity != null;
	}

	public Session getSession() {
		return session;
	}

	public ExecutorService getService() {
		return service;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public TaskManager getTaskManager() {
		return taskManager;
	}

	public ConnectionHandler getConnectionHandler() {
		return connectionHandler;
	}

	public AuthService<?> getAuthService() {
		return authService;
	}

	public ProxyData getLoginProxy() {
		return loginProxy;
	}

	public ProxyData getConnectProxy() {
		return connectProxy;
	}

	public boolean isConnected() {
		return connectionHandler.isConnected();
	}

	public boolean isMovementDisabled() {
		return movementDisabled;
	}

	public void setMovementDisabled(boolean movementDisabled) {
		this.movementDisabled = movementDisabled;
	}
	
	public boolean isMuted(){
		return muted;
	}
	
	public void setMuted(boolean mute){
		this.muted = mute;
	}

	public int getMessageDelay() {
		return messageDelay;
	}

	public void setMessageDelay(int messageDelay) {
		this.messageDelay = messageDelay;
	}

	public MainPlayerEntity getPlayer() {
		return player;
	}

	public static final Builder builder() {
		return new Builder();
	}

	private final class TickHandler implements Runnable {
		private final Timer timer = new Timer(20, 20);
		private final Future<?> thread;

		public TickHandler() {
			thread = service.submit(this);
		}

		@Override
		public void run() {
			while(true) {
				if(thread != null && thread.isCancelled())
					return;
				timer.update();
				for(int i = 0; i < timer.getElapsedTicks(); i++)
					runTick();
				if(timer.getFPSCoolDown() > 0) {
					try {
						Thread.sleep(timer.getFPSCoolDown());
					} catch(InterruptedException exception) {}
				}
			}
		}
	}

	public static final class Builder {
		private String server;
		private int port = MinecraftBot.DEFAULT_PORT;
		private int protocol = MinecraftBot.LATEST_PROTOCOL;

		private String username;
		private String password;

		private ProxyData loginProxy;
		private ProxyData connectProxy;

		private AuthService<?> authService;
		private Session session;

		private Builder() {
		}

		public synchronized Builder server(String server) {
			this.server = server;
			return this;
		}

		public synchronized Builder port(int port) {
			this.port = port;
			return this;
		}

		public synchronized Builder protocol(int protocol) {
			this.protocol = protocol;
			return this;
		}

		public synchronized Builder username(String username) {
			this.username = username;
			return this;
		}

		public synchronized Builder password(String password) {
			this.password = password;
			return this;
		}

		public synchronized Builder loginProxy(ProxyData loginProxy) {
			this.loginProxy = loginProxy;
			return this;
		}

		public synchronized Builder connectProxy(ProxyData connectProxy) {
			this.connectProxy = connectProxy;
			return this;
		}

		public synchronized Builder authService(AuthService<?> authService) {
			this.authService = authService;
			return this;
		}

		public synchronized Builder session(Session session) {
			this.session = session;
			return this;
		}

		public synchronized MinecraftBot build(LanguageManager manager) throws AuthenticationException, UnsupportedProtocolException, InvalidSessionException, IOException {
			return new MinecraftBot(this, manager);
		}

		public String getServer() {
			return server;
		}

		public int getPort() {
			return port;
		}

		public int getProtocol() {
			return protocol;
		}

		public String getUsername() {
			return username;
		}

		public String getPassword() {
			return password;
		}

		public ProxyData getLoginProxy() {
			return loginProxy;
		}

		public ProxyData getConnectProxy() {
			return connectProxy;
		}

		public AuthService<?> getAuthService() {
			return authService;
		}

		public Session getSession() {
			return session;
		}
	}

	public LanguageManager getLanguageManger() {
		return languageManger;
	}

	public void setLanguageManger(LanguageManager languageManger) {
		this.languageManger = languageManger;
	}
}
