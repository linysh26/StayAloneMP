package address.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;



public class PropertiesUtil {
	private static String path;
	private static InputStream in;
	private static URL outUrl;
	private static String storePath;
	public static String getPath() {
		return path;
	}

	public static void setPath(String p) {
		path = p;
		try {
			storePath=outUrl.toString().substring(6);
			Properties properties=new Properties();
			properties.load(new InputStreamReader(in));
			properties.setProperty("path", path);
			
			properties.store(new FileWriter(storePath), "MusicPlayer-ini");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("���������ļ�ʱ����");
		}
	}
	
	static{
		try {
			in = ClassLoader.getSystemClassLoader().getResourceAsStream("ini.properties");
			outUrl=ClassLoader.getSystemClassLoader().getResource("ini.properties");
			Properties properties=new Properties();
			properties.load(new InputStreamReader(in));
			path=properties.getProperty("path");
			path.replace("\\", "");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("���������ļ�ʱ����");
		}

	}
	
	
	public static String getProperties(String key) {
		try {
			Properties properties=new Properties();
			properties.load(new InputStreamReader(in));
			String value=properties.getProperty(key);
			if(key=="path"){
				throw new RuntimeException("�벻Ҫ�������ȡ·����");
			}
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("���������ļ�ʱ����");
		}
	}

	public static void setProperties(String key,String value) {
		try {
			storePath=outUrl.toString().substring(6);
			Properties properties=new Properties();
			properties.load(new InputStreamReader(in));
			properties.setProperty(key, value);
			properties.store(new FileWriter(storePath), "MusicPlayer-ini");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("����������Ϣʱ����");
		}
	}
	
	
	
}