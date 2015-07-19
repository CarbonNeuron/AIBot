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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import ovh.tgrhavoc.aibot.wrapper.cli.CLIBotWrapper;

public class Main {
	public static void main(String[] args) {
		printNotice();
		
		OptionParser parser = new OptionParser();
		parser.acceptsAll(Arrays.asList("d", "debug"));
		parser.acceptsAll(Arrays.asList("h", "help"));
		parser.acceptsAll(Arrays.asList("c", "conditions"));
		
		String[] mainArgs = Arrays.copyOfRange(args, 0, args.length);
		
		OptionSet options;
		try {
			options = parser.parse(mainArgs);
		} catch(OptionException exception) {
			printHelp(parser);
			return;
		}
		if(options.has("conditions")){
			printTC();
			return;
		}
		
		if(options.has("help")) {
			printHelp(parser);
			return;
		}
		
		CLIBotWrapper.main(mainArgs);
	}
	
	private static void printTC(){
		InputStream in = Main.class.getClassLoader().getResourceAsStream("tc.txt");
		byte[] b;
		try {
			b = new byte[in.available()];
			in.read(b, 0, b.length);
			System.out.println(new String(b, Charset.forName("UTF8")));
		} catch (IOException e) {
			//e.printStackTrace();
			return;
		}
		
	}
	
	private static void printNotice(){
		System.out.println("AIBot  Copyright (C) 2015  Jordan Dalton (jordan.8474@gmail.com)");
		System.out.println("This program comes with ABSOLUTELY NO WARRANTY.");
		System.out.println("This is free software, and you are welcome to redistribute it");
		System.out.println("under certain conditions; type \"c\" as a command argument for details.\n\n");
	}
	
	private static void printHelp(OptionParser parser) {
		try {
			parser.printHelpOn(System.out);
		} catch(Exception exception) {
			exception.printStackTrace();
		}
	}
}
