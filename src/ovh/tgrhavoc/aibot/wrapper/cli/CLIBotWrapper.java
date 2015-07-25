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
package ovh.tgrhavoc.aibot.wrapper.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import ovh.tgrhavoc.aibot.LanguageManager;
import ovh.tgrhavoc.aibot.MinecraftBot;
import ovh.tgrhavoc.aibot.ProxyData;
import ovh.tgrhavoc.aibot.ProxyData.ProxyType;
import ovh.tgrhavoc.aibot.RealmsUtil;
import ovh.tgrhavoc.aibot.Util;
import ovh.tgrhavoc.aibot.ai.AttackTask;
import ovh.tgrhavoc.aibot.ai.AvoidDeathTask;
import ovh.tgrhavoc.aibot.ai.BuildingTask;
import ovh.tgrhavoc.aibot.ai.ChopTreesTask;
import ovh.tgrhavoc.aibot.ai.DefendTask;
import ovh.tgrhavoc.aibot.ai.DerpTask;
import ovh.tgrhavoc.aibot.ai.DestroyingTask;
import ovh.tgrhavoc.aibot.ai.EatTask;
import ovh.tgrhavoc.aibot.ai.FallTask;
import ovh.tgrhavoc.aibot.ai.FarmingTask;
import ovh.tgrhavoc.aibot.ai.FishingTask;
import ovh.tgrhavoc.aibot.ai.FollowTask;
import ovh.tgrhavoc.aibot.ai.HostileTask;
import ovh.tgrhavoc.aibot.ai.MiningTask;
import ovh.tgrhavoc.aibot.ai.TaskManager;
import ovh.tgrhavoc.aibot.ai.TwerkTask;
import ovh.tgrhavoc.aibot.auth.AuthService;
import ovh.tgrhavoc.aibot.auth.AuthenticationException;
import ovh.tgrhavoc.aibot.auth.Session;
import ovh.tgrhavoc.aibot.auth.YggdrasilAuthService;
import ovh.tgrhavoc.aibot.auth.YggdrasilSession;
import ovh.tgrhavoc.aibot.protocol.ProtocolProvider;
import ovh.tgrhavoc.aibot.protocol.UnsupportedProtocolException;
import ovh.tgrhavoc.aibot.world.Difficulty;
import ovh.tgrhavoc.aibot.world.GameMode;
import ovh.tgrhavoc.aibot.wrapper.MinecraftBotWrapper;
import ovh.tgrhavoc.aibot.wrapper.backend.ChatBackend;
import ovh.tgrhavoc.aibot.wrapper.commands.AttackAllCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.AttackCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.BuildCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.CalcCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.ChatDelayCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.ChopCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.CommandException;
import ovh.tgrhavoc.aibot.wrapper.commands.CrouchCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.DerpCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.DestroyCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.DropAllCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.DropCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.DropIdCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.EquipCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.FarmCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.FishCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.FollowCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.HelpCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.InteractCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.MineCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.MuteCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.OwnerCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.PlayersCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.QuitCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.SayCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.SetWalkCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.StatusCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.StopCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.SwitchCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.ToolCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.TwerkCommand;
import ovh.tgrhavoc.aibot.wrapper.commands.WalkCommand;

public class CLIBotWrapper extends MinecraftBotWrapper {
	private CLIBotWrapper(MinecraftBot bot, String owner, LanguageManager lManager) {
		super(bot, lManager);
		addOwner(owner);
		addBackend(new ChatBackend(this));
		
		TaskManager taskManager = bot.getTaskManager();
		taskManager.registerTask(new FallTask(bot));
		taskManager.registerTask(new ChopTreesTask(bot));
		taskManager.registerTask(new FollowTask(bot));
		taskManager.registerTask(new DefendTask(bot));
		taskManager.registerTask(new AttackTask(bot));
		taskManager.registerTask(new HostileTask(bot));
		taskManager.registerTask(new EatTask(bot));
		taskManager.registerTask(new MiningTask(bot));
		taskManager.registerTask(new FishingTask(bot));
		taskManager.registerTask(new FarmingTask(bot));
		taskManager.registerTask(new BuildingTask(bot));
		taskManager.registerTask(new AvoidDeathTask(bot));
		taskManager.registerTask(new DestroyingTask(bot));
		taskManager.registerTask(new DerpTask(bot));
		taskManager.registerTask(new TwerkTask(bot));
		
		commandManager.register(new AttackAllCommand(this));
		commandManager.register(new AttackCommand(this));
		commandManager.register(new BuildCommand(this));
		commandManager.register(new CalcCommand(this));
		commandManager.register(new ChatDelayCommand(this));
		commandManager.register(new ChopCommand(this));
		commandManager.register(new DestroyCommand(this));
		commandManager.register(new DropAllCommand(this));
		commandManager.register(new DropCommand(this));
		commandManager.register(new DropIdCommand(this));
		commandManager.register(new EquipCommand(this));
		commandManager.register(new FarmCommand(this));
		commandManager.register(new FishCommand(this));
		commandManager.register(new FollowCommand(this));
		commandManager.register(new HelpCommand(this));
		commandManager.register(new InteractCommand(this));
		commandManager.register(new MineCommand(this));
		commandManager.register(new OwnerCommand(this));
		commandManager.register(new PlayersCommand(this));
		commandManager.register(new QuitCommand(this));
		commandManager.register(new SayCommand(this));
		commandManager.register(new SetWalkCommand(this));
		commandManager.register(new StatusCommand(this));
		commandManager.register(new StopCommand(this));
		commandManager.register(new SwitchCommand(this));
		commandManager.register(new ToolCommand(this));
		commandManager.register(new WalkCommand(this));
		commandManager.register(new DerpCommand(this));
		
		commandManager.register(new TwerkCommand(this));
		commandManager.register(new CrouchCommand(this));
		commandManager.register(new MuteCommand(this));		
		
		BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
		while(bot.isConnected()){
			try {
				String line = consoleIn.readLine();
				
				getCommandManager().execute(line);
				
			} catch (IOException e) {
				if (bot.isConnected())
					e.printStackTrace();
				
			}catch (CommandException e){
				StringBuilder error = new StringBuilder("Error: ");
				if(e.getCause() != null)
					error.append(e.getCause().toString());
				else if(e.getMessage() == null)
					error.append("null");
				if(e.getMessage() != null) {
					if(e.getCause() != null)
						error.append(": ");
					error.append(e.getMessage());
				}
				say(error.toString());
			}
		}
	}

	public static void main(String[] args, LanguageManager manager) {
		// TODO main
		OptionParser parser = new OptionParser();
		
		System.out.println(manager.getUnformattedText("help.help"));
		
		parser.acceptsAll(Arrays.asList("h", "help"), manager.getUnformattedText("help.help"));
		OptionSpec<String> serverOption = parser.acceptsAll(Arrays.asList("s", "server"), manager.getUnformattedText("help.server")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.server"));
		OptionSpec<String> proxyOption = parser.acceptsAll(Arrays.asList("P", "proxy"), manager.getUnformattedText("help.proxy")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.proxyaddress"));
		OptionSpec<String> ownerOption = parser.acceptsAll(Arrays.asList("o", "owner"), manager.getUnformattedText("help.owner")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.owner"));
		OptionSpec<String> usernameOption = parser.acceptsAll(Arrays.asList("u", "username"), manager.getUnformattedText("help.username")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.username"));
		OptionSpec<String> passwordOption = parser.acceptsAll(Arrays.asList("p", "password"), manager.getUnformattedText("help.password")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.password"));
		OptionSpec<?> offlineOption = parser.acceptsAll(Arrays.asList("O", "offline"), manager.getUnformattedText("help.offline"));
		OptionSpec<?> autoRejoinOption = parser.acceptsAll(Arrays.asList("a", "auto-rejoin"), manager.getUnformattedText("help.autorejoin"));
		OptionSpec<String> protocolOption = parser.accepts("protocol", manager.getUnformattedText("help.protocol")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.version"));
		OptionSpec<?> protocolsOption = parser.accepts("protocols", manager.getUnformattedText("help.protocols"));
		OptionSpec<?> mcoServersOption = parser.accepts("mco-servers", manager.getUnformattedText("help.mcoservers"));
		OptionSpec<String> mcoServerOption = parser.accepts("mco-server", manager.getUnformattedText("help.mcoservers")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.server"));
		
		OptionSpec<String> accountListOption = parser.accepts("account-list", manager.getUnformattedText("help.accountlist")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.file"));
		OptionSpec<String> socksProxyListOption = parser.accepts("socks-proxy-list", manager.getUnformattedText("help.socksproxylist")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.file"));
		OptionSpec<String> httpProxyListOption = parser.accepts("http-proxy-list", manager.getUnformattedText("help.httpproxylist")).withRequiredArg().describedAs(manager.getUnformattedText("help.arguments.file"));

		OptionSet options;
		try {
			options = parser.parse(args);
		} catch(OptionException exception) {
			try {
				parser.printHelpOn(System.out);
			} catch(Exception exception1) {
				exception1.printStackTrace();
			}
			return;
		}

		if(options.has("help")) {
			printHelp(parser);
			return;
		}
		if(options.has(protocolsOption)) {
			System.out.println(manager.getUnformattedText("text.protocols.available"));
			for(ProtocolProvider<?> provider : ProtocolProvider.getProviders())
				System.out.println(manager.getFormattedText("text.protocols.protocol", provider.getMinecraftVersion(), provider.getSupportedVersion(), provider.getClass().getName()));
			System.out.println(manager.getUnformattedText("text.protocols.nonelisted"));
			System.out.println(manager.getUnformattedText("text.protocols.nonespecified"));
			return;
		}

		final boolean offline = options.has(offlineOption);
		final boolean autoRejoin = options.has(autoRejoinOption);

		final List<String> accounts;
		final String username, password;
		if(options.has(accountListOption)) {
			accounts = loadAccounts(options.valueOf(accountListOption));
			username = null;
			password = null;
		} else {
			accounts = null;
			if(options.has(usernameOption)) {
				username = options.valueOf(usernameOption);
				if(!offline && options.has(passwordOption))
					password = options.valueOf(passwordOption);
				else if(!offline) {
					System.out.println(manager.getFormattedText("text.options.required.two", "password", "offline"));
					printHelp(parser);
					return;
				} else
					password = null;
			} else {
				username = null;
				password = null;
			}
		}

		if(options.has(mcoServersOption)) {
			if(offline) {
				System.out.println(manager.getUnformattedText("text.mco.error.authentication"));
				return;
			}
			if(username == null || password == null) {
				System.out.println(manager.getUnformattedText("text.mco.error.username"));
				return;
			}
			ProxyData proxy = null;
			if(options.has(proxyOption))
				proxy = toProxy(options.valueOf(proxyOption), ProxyData.ProxyType.SOCKS);

			YggdrasilAuthService service = new YggdrasilAuthService(MinecraftBot.CLIENT_TOKEN);
			YggdrasilSession session;
			try {
				session = service.login(username, password, proxy);
			} catch(AuthenticationException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.details"));
				exception.printStackTrace();
				return;
			} catch(IOException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.auth"));
				exception.printStackTrace();
				return;
			}
			
			//TODO: If debug
			//System.out.println("Requesting MCO server list...");
			
			RealmsUtil.Server[] servers;
			try {
				servers = RealmsUtil.requestServers(session, proxy);
			} catch(IOException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.request"));
				exception.printStackTrace();
				return;
			}

			if(servers.length == 0) {
				System.out.println(manager.getUnformattedText("text.mco.error.noservers"));
				return;
			}
			System.out.println(manager.getFormattedText("text.mco.server.available", servers.length));
			for(RealmsUtil.Server server : servers) {
				System.out.println(manager.getFormattedText("text.mco.server.name", server.getName(), server.getId()));
				System.out.println(manager.getFormattedText("text.mco.server.owner", server.getOwner()));
				System.out.println(manager.getFormattedText("text.mco.server.motd", server.getMotd()));
				System.out.println(manager.getFormattedText("text.mco.server.difficulty", Difficulty.getDifficultyById(server.getDifficulty()).name()));
				System.out.println(manager.getFormattedText("text.mco.server.gamemode", GameMode.getGameModeById(server.getGameMode()).name()));
				if(!server.isExpired())
					System.out.println(manager.getFormattedText("text.mco.server.subscription.left", server.getDaysLeft()));
				else
					System.out.println(manager.getUnformattedText("text.mco.server.subscription.expired"));
				System.out.println(manager.getFormattedText("text.mco.server.state", server.getState()));
			}
			return;
		}

		final String server;
		if(!options.has(serverOption) && !options.has(mcoServerOption)) {
			System.out.println(manager.getFormattedText("text.options.required", "server"));
			printHelp(parser);
			return;
		} else if(options.has(mcoServerOption)) {
			if(offline) {
				System.out.println(manager.getUnformattedText("text.mco.error.authentication"));
				return;
			}
			if(username == null || password == null) {
				System.out.println(manager.getUnformattedText("text.mco.error.username"));
				return;
			}
			ProxyData proxy = null;
			if(options.has(proxyOption))
				proxy = toProxy(options.valueOf(proxyOption), ProxyData.ProxyType.SOCKS);
			YggdrasilAuthService service = new YggdrasilAuthService(MinecraftBot.CLIENT_TOKEN);
			YggdrasilSession session;
			try {
				session = service.login(username, password, proxy);
			} catch(AuthenticationException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.details"));
				exception.printStackTrace();
				return;
			} catch(IOException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.auth"));
				exception.printStackTrace();
				return;
			}

			RealmsUtil.Server[] availableServers;
			try {
				availableServers = RealmsUtil.requestServers(session, proxy);
			} catch(IOException exception) {
				System.err.println(manager.getUnformattedText("text.mco.error.request"));
				exception.printStackTrace();
				return;
			}
			String serverName = options.valueOf(mcoServerOption);
			RealmsUtil.Server targetServer = null;
			for(RealmsUtil.Server availableServer : availableServers)
				if(serverName.equalsIgnoreCase(availableServer.getName()))
					targetServer = availableServer;
			if(targetServer == null) {
				try {
					int id = Integer.parseInt(serverName);
					for(RealmsUtil.Server availableServer : availableServers)
						if(id == availableServer.getId())
							targetServer = availableServer;
					if(targetServer == null && id < availableServers.length)
						targetServer = availableServers[id];
				} catch(NumberFormatException exception) {}
			}
			if(targetServer == null) {
				System.out.println(manager.getUnformattedText("text.mco.error.noserver"));
				return;
			}

			String address = null;
			for(int i = 0; i < 6; i++) {
				try {
					address = RealmsUtil.requestAddress(targetServer, session, proxy);
					break;
				} catch(IOException exception) {
					if(i == 5) {
						System.err.println(manager.getUnformattedText("text.mco.error.connect"));
						exception.printStackTrace();
						return;
					}
				}
				try {
					Thread.sleep(5000);
				} catch(InterruptedException exception) {}
			}
			if(address == null) {
				System.out.println(manager.getUnformattedText("text.mco.error.connect"));
				return;
			}
			server = address;
		} else
			server = options.valueOf(serverOption);

		final String owner;
		if(!options.has(ownerOption)) {
			System.out.println(manager.getFormattedText("text.options.required", "owner"));
			printHelp(parser);
			return;
		} else
			owner = options.valueOf(ownerOption);

		final int protocol;
		if(options.has(protocolOption)) {
			String protocolString = options.valueOf(protocolOption);
			int parsedProtocol;
			try {
				parsedProtocol = Integer.parseInt(protocolString);
			} catch(NumberFormatException exception) {
				ProtocolProvider<?> foundProvider = null;
				for(ProtocolProvider<?> provider : ProtocolProvider.getProviders())
					if(protocolString.equals(provider.getMinecraftVersion()))
						foundProvider = provider;
				if(foundProvider == null) {
					System.out.println(manager.getFormattedText("text.provider.noprovider", protocolString));
					return;
				} else
					parsedProtocol = foundProvider.getSupportedVersion();
			}
			protocol = parsedProtocol;
		} else
			protocol = MinecraftBot.LATEST_PROTOCOL;

		final List<String> socksProxies;
		final String defaultProxy;
		if(options.has(socksProxyListOption)) {
			socksProxies = loadProxies(options.valueOf(socksProxyListOption));
			defaultProxy = null;
		} else {
			socksProxies = null;
			if(options.has(proxyOption))
				defaultProxy = options.valueOf(proxyOption);
			else
				defaultProxy = null;
		}

		final List<String> httpProxies;
		if(options.has(httpProxyListOption))
			httpProxies = loadLoginProxies(options.valueOf(httpProxyListOption));
		else if(username == null && accounts != null) {
			System.out.println(manager.getFormattedText("text.options.requiredwith", "http-proxy-list", "account-list"));
			printHelp(parser);
			return;
		} else
			httpProxies = null;

		final List<String> accountsInUse = new ArrayList<String>();
		Random random = new Random();

		if(!offline) {
			AuthService<?> authService = new YggdrasilAuthService(MinecraftBot.CLIENT_TOKEN);
			user: do {
				Session session = null;
				String loginProxy;
				String account;
				if(username == null) {
					account = accounts.get(random.nextInt(accounts.size()));
					synchronized(accountsInUse) {
						while(accountsInUse.contains(account))
							account = accounts.get(random.nextInt(accounts.size()));
						accountsInUse.add(account);
					}
				} else
					account = username + ":" + password;
				String[] accountParts = account.split(":");
				while(true) {
					loginProxy = username != null ? null : httpProxies.get(random.nextInt(httpProxies.size()));
					try {
						session = authService.login(accountParts[0], accountParts[1], toProxy(loginProxy, ProxyData.ProxyType.HTTP));
						break;
					} catch(IOException exception) {
						System.err.println("[Bot] " + exception);
						if(username != null)
							break user;
					} catch(AuthenticationException exception) {
						System.err.println("[Bot] " + exception);
						if(username != null)
							break user;
						continue user;
					}
				}
				System.out.println("[" + session.getUsername() + "] " + session);

				while(true) {
					String proxy = socksProxies != null ? socksProxies.get(random.nextInt(socksProxies.size())) : defaultProxy;
					try {
						CLIBotWrapper bot = new CLIBotWrapper(createBot(server, session.getUsername(), session.getPassword(), authService, session, protocol, null, proxy, manager), owner, manager);
						if(!bot.getBot().isConnected())
							System.out.println("[" + session.getUsername() + "] Account failed");
						while(bot.getBot().isConnected()) {
							try {
								Thread.sleep(1500);
							} catch(InterruptedException exception) {
								exception.printStackTrace();
							}
						}
						if(!autoRejoin)
							break;
					} catch(Exception exception) {
						exception.printStackTrace();
						System.out.println("[" + session.getUsername() + "] Error connecting: " + exception.getCause().toString());
					}
				}
			} while(username == null);
		} else {
			while(true) {
				String proxy = socksProxies != null ? socksProxies.get(random.nextInt(socksProxies.size())) : defaultProxy;
				try {
					String name = "";
					if(username == null)
						name = Util.generateRandomString(10 + random.nextInt(6));
					else
						name = username;
					CLIBotWrapper bot = new CLIBotWrapper(createBot(server, name, null, null, null, protocol, null, proxy, manager), owner, manager);
					while(bot.getBot().isConnected()) {
						try {
							Thread.sleep(1500);
						} catch(InterruptedException exception) {
							exception.printStackTrace();
						}
					}
					if(!autoRejoin)
						break;
					else
						continue;
				} catch(Exception exception) {
					System.out.println("[Bot] Error connecting: " + exception.toString());
					exception.printStackTrace();
				}
			}
		}
		System.exit(0);
	}

	private static void printHelp(OptionParser parser) {
		try {
			parser.printHelpOn(System.out);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}

	private static List<String> loadProxies(String fileName) {
		List<String> proxies = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line;
			while((line = reader.readLine()) != null) {
				if(line.trim().isEmpty())
					continue;
				String[] parts = line.split(" ")[0].trim().split(":");
				proxies.add(parts[0].trim() + ":" + Integer.parseInt(parts[1].trim()));
			}
			reader.close();
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		System.out.println("Loaded " + proxies.size() + " proxies.");
		return proxies;
	}

	private static List<String> loadLoginProxies(String fileName) {
		List<String> proxies = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line;
			while((line = reader.readLine()) != null) {
				if(line.trim().isEmpty())
					continue;
				String[] parts = line.split(" ")[0].trim().split(":");
				proxies.add(parts[0].trim() + ":" + Integer.parseInt(parts[1].trim()));
			}
			reader.close();
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		System.out.println("Loaded " + proxies.size() + " login proxies.");
		return proxies;
	}

	private static List<String> loadAccounts(String fileName) {
		List<String> accounts = new ArrayList<String>();
		try {
			Pattern pattern = Pattern.compile("[\\w]{1,16}");
			BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
			String line;
			while((line = reader.readLine()) != null) {
				Matcher matcher = pattern.matcher(line);
				if(!matcher.find())
					continue;
				String username = matcher.group();
				if(!matcher.find())
					continue;
				String password = matcher.group();
				accounts.add(username + ":" + password);
			}
			reader.close();
		} catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		System.out.println("Loaded " + accounts.size() + " accounts.");
		return accounts;
	}

	private static ProxyData toProxy(String proxyData, ProxyData.ProxyType type) {
		if(proxyData == null)
			return null;
		int port = 80;
		if(proxyData.contains(":")) {
			String[] parts = proxyData.split(":");
			proxyData = parts[0];
			port = Integer.parseInt(parts[1]);
		}
		return new ProxyData(proxyData, port, type);
	}

	private static MinecraftBot createBot(String server, String username, String password, AuthService<?> service, Session session, int protocol, String loginProxy, String proxy, LanguageManager manager) throws AuthenticationException, UnsupportedProtocolException, IOException {
		MinecraftBot.Builder builder = MinecraftBot.builder();
		if(proxy != null && !proxy.isEmpty()) {
			int port = 80;
			ProxyType type = ProxyType.SOCKS;
			if(proxy.contains(":")) {
				String[] parts = proxy.split(":");
				proxy = parts[0];
				port = Integer.parseInt(parts[1]);
				if(parts.length > 2)
					type = ProxyType.values()[Integer.parseInt(parts[2]) - 1];
			}
			builder.connectProxy(new ProxyData(proxy, port, type));
		}
		if(loginProxy != null && !loginProxy.isEmpty()) {
			int port = 80;
			if(loginProxy.contains(":")) {
				String[] parts = loginProxy.split(":");
				loginProxy = parts[0];
				port = Integer.parseInt(parts[1]);
			}
			builder.loginProxy(new ProxyData(loginProxy, port, ProxyType.HTTP));
		}
		builder.username(username).authService(service).protocol(protocol);
		if(session != null)
			builder.session(session);
		else
			builder.password(password);
		if(server != null && !server.isEmpty()) {
			int port = 25565;
			if(server.contains(":")) {
				String[] parts = server.split(":");
				server = parts[0];
				port = Integer.parseInt(parts[1]);
			}
			builder.server(server).port(port);
		} else
			throw new IllegalArgumentException(manager.getUnformattedText("error.illegalargumentexception.unknownserver"));

		return builder.build(manager);
	}
}
