# AIBot
An automated bot for the game Minecraft.

# Getting Started

### Required Arguments
When running the bot, you __must__ supply the following arguments:

`-b` or `--bot` - This must be supplied before you give any other arguments.

`-u` or `--username` - This is the username that the bot will use (what is used when you log into the Minecraft Client)

`-s` or `--server` - This is the server you want the bot to connect to (can use ports, just add : then the port number).

`--protocol` - The protocol version the bot should use (1.7 servers use protocol 4)

If you are connecting to an offline server you must add the argument `-O`(capital o) or `--offline` otherwise you must specify the account password with `-p` or `--password` (The password is what you enter into the Minecraft client when loggin in).

### Regular Users

If you don't want to modify the code in any way, then read this to get started.

1. Download the binary files for the [latest release of the project.](https://github.com/TGRHavoc/AIBot/releases)
2. Run the JAR file using the command line or batch/bash file
   1. Use `java -jar AIBot.jar --help` for a list of all available commands

If you're on windows, I've created a [simple batch file](https://github.com/TGRHavoc/AIBot/blob/master/start_bot.bat) that you can use to run the bot quickly, just make sure you change the values in the file to suite your needs.

### Developers
1. Download a copy of the repo to your development workspace using the methods you want (e.g. eGit, SourceTree, GitHub GUI).
2. Now, make sure that the project is shown in your workspace.
3. To build the project, run the file `build.xml` as an ant build file

Enjoy!

~ Havoc
