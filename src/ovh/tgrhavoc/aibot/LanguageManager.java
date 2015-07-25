package ovh.tgrhavoc.aibot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {
	
	private final Map<String, String> lang;
	
	public LanguageManager(String language){
		lang = new HashMap<String, String>();
		File f = new File("langs/" + language);
		
		if (!f.exists())
			throw new IllegalArgumentException("File 'langs/" + language + "' doesn't exist");
		
		try(InputStream in = new FileInputStream(f)){
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while((line = reader.readLine()) != null){
				//System.out.println("Parsing: " + line);
				int index = line.indexOf('=');
				if(index == -1)
					continue;
				String key = line.substring(0, index);
				String value = line.substring(index + 1);
				lang.put(key, value);
				//System.out.printf("Put key '%s' with value '%s'\n", key, value);
			}
			reader.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getUnformattedText(String key){
		return lang.get(key);
	}
	
	public String getFormattedText(String key, Object...args){
		return String.format(lang.get(key), args);
	}
	
	public static void setUp(){
		File langFolder = new File("langs/");
		if(!langFolder.exists())
			langFolder.mkdir();
		
		File outFile = new File("langs/en_US.lang");
		if(outFile.exists())
			return;
		
		InputStream in = null;
		OutputStream out = null;
		try{
			outFile.createNewFile();
			
			in = LanguageManager.class.getClassLoader().getResourceAsStream("lang/en_US.lang");
			out = new FileOutputStream(outFile);
			int i = -1;
			while((i = in.read()) != -1){
				out.write(i);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
