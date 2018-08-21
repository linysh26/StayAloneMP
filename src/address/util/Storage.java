package address.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.stream.FileImageOutputStream;

import address.model.Music;
import javafx.scene.image.Image;

import java.sql.*;


public class Storage {
	
	// connection
	private static Connection connection = null;
	private static Statement sta = null;
	
	// mysql driver and database url
	private static String JDBCDriver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/mp";
	
	//username & password
	private static String username = "root";
	private static String password = "(Lyn)4&6";
	
	
	public static void initialize() {
		
		// register jdbc driver
		try {
			Class.forName(JDBCDriver);
			// open link
			// connection = DriverManager.getConnection(url, username, password);
			
			// do query
			// sta = connection.createStatement();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Music> require(String sql) {
		
		LinkedList<String> paths = new LinkedList<String>();
		try {
			// open link
			connection = DriverManager.getConnection(url, username, password);
			
			// do query
			sta = connection.createStatement();
			ResultSet rs = sta.executeQuery(sql);
			// move the cursor to the beginning of the result set
			rs.beforeFirst();
			// explore the result set
			/**
			 * data:{
			 * 
			 * 
			 * }
			 */
			while(rs.next()) {
				
			}
			
		} catch (SQLException sqle) {
			// TODO Auto-generated catch block
			sqle.printStackTrace();
		}
		return parser(paths);
	}

	public static void write(File file,LinkedList<String> musicList) {
		
		FileOutputStream out = null;
		OutputStreamWriter writer = null;
		BufferedWriter bwriter = null;
		
		try {
			out = new FileOutputStream(file);
			writer = new OutputStreamWriter(out);
			bwriter = new BufferedWriter(writer); 
			
			for(String m : musicList){
	            bwriter.append(m).append("\r");
	        }
		}catch(Exception e) {
			
		}finally {
			if(bwriter!=null){
                try {
                    bwriter.close();
                    bwriter=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(writer!=null){
                try {
                    writer.close();
                    writer=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
		}
	}
	
	public static LinkedList<String> read(String path) {
		
		File file = new File(path);
		
		//判断存储文件是否存在
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new LinkedList<String>();
		}
		
		//构造文件读取breader
		BufferedReader breader = null;
		try {
			breader = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		LinkedList<String> musicData = new LinkedList<String>();
		//读取歌曲路径信息
		try {
			
			String line = "";
			while((line = breader.readLine()) != null) {
				musicData.add(line);
			}
		}catch(IndexOutOfBoundsException ioobe) {
			ioobe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return musicData;
	}
	
	public static List<Music> parser(LinkedList<String> musicData) {
		
		return musicData.stream().map(path -> {
			File musicFile = new File(path);
			if(!musicFile.exists())
				return null;
			byte[] buffer = new byte[128];
			try {
				RandomAccessFile raf = new RandomAccessFile(musicFile, "r");
				byte[] header = new byte[10];
				raf.read(header);
				byte[] buf = new byte[10];
				String musicName = "";
				String album = "";
				String singer = "";
				String ID = "";
				byte[] image = new byte[1];
				while(true) {
					raf.read(buf);
					if("TAG".equals(new String(buf, 0, 3)))
						break;
					else if(buf[0] == -1) {
						int frame_content_size = 1034;
						if((buf[2]&0x02) == 2)
							frame_content_size = frame_content_size + 1;
						byte[] frame_content = new byte[frame_content_size];
						raf.read(frame_content);
						continue;
					}
					System.out.println(new String(buf, 0, 4));
					if(!Character.isLetterOrDigit(buf[0])
							|| !Character.isLetterOrDigit(buf[1])
							|| !Character.isLetterOrDigit(buf[2])
							|| !Character.isLetterOrDigit(buf[3]))
						break;
					System.out.println(((buf[4]&0x7F + (buf[4] < 0?1:0)*0x80)*0x10000000*0x10) + " " + ((buf[5]&0x7F + (buf[5] < 0?1:0)*0x80)*0x10000) + " " + ((buf[6]&0x7F + (buf[6] < 0?1:0)*0x80)*0x100) + " " + (buf[7]&0x7F + (buf[7] < 0?1:0)*0x80));
					int size = ((buf[4]&0x7F + (buf[4] < 0?1:0)*0x80)*0x10000000*0x10) + ((buf[5]&0x7F + (buf[5] < 0?1:0)*0x80)*0x10000) + ((buf[6]&0x7F + (buf[6] < 0?1:0)*0x80)*0x100) + (buf[7]&0x7F + (buf[7] < 0?1:0)*0x80);
					byte[] content = new byte[size];
					raf.read(content);
					String encoding = "";
					switch(content[0]) {
					case 0:
						encoding = "iso-8859-1";break;
					case 1:
						encoding = "utf-16le";break;
					case 2:
						encoding = "utf-16be";break;
					case 3:
						encoding = "utf-8";break;
						default:break;
					}
					String frame_header = new String(buf, 0, 4);
					
					switch(frame_header) {
					case "TXXX":
						ID = new String(content, 6, size - 6, encoding);
						break;
					case "TIT2":
						musicName = new String(content, 1, size - 1, encoding);
						break;
					case "TALB":
						album = new  String(content, 1, size - 1, encoding);
						break;
					case "TPE1":
						singer = new String(content, 1, size - 1, encoding);
						break;
					case "COMM":
						System.out.println(new String(content, 1, size - 1, encoding));
						break;
					case "APIC":
						image = content;
						FileImageOutputStream fios;
						if("PNG".equals(new String(image, 15, 3)))
							fios = new FileImageOutputStream(new File("images/" + musicName + ".png"));
						else
							fios = new FileImageOutputStream(new File("images/" + musicName + ".jpg"));
						System.out.println(image.length);
						fios.write(image, 14, image.length - 14);
						fios.close();
						default:
							break;
					}
				}
				return new Music(path, "", image, musicName, singer, album, ID);
			}catch(IOException ioe) {
				ioe.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
	}
	
	public static Music parser(String path) {
		File musicFile = new File(path);
		if(!musicFile.exists())
			return null;
		try {
			RandomAccessFile raf = new RandomAccessFile(musicFile, "r");
			byte[] header = new byte[10];
			raf.read(header);
			byte[] buf = new byte[10];
			String musicName = "";
			String album = "";
			String singer = "";
			String ID = "";
			byte[] image = new byte[1];
			while(true) {
				raf.read(buf);
				if("TAG".equals(new String(buf, 0, 3)))
					break;
				else if(buf[0] == -1) {
					int frame_content_size = 1034;
					if((buf[2]&0x02) == 2)
						frame_content_size = frame_content_size + 1;
					byte[] frame_content = new byte[frame_content_size];
					raf.read(frame_content);
					continue;
				}
				System.out.println(new String(buf, 0, 4));
				if(!Character.isLetterOrDigit(buf[0])
						|| !Character.isLetterOrDigit(buf[1])
						|| !Character.isLetterOrDigit(buf[2])
						|| !Character.isLetterOrDigit(buf[3]))
					break;
				System.out.println(((buf[4]&0x7F + (buf[4] < 0?1:0)*0x80)*0x10000000*0x10) + " " + ((buf[5]&0x7F + (buf[5] < 0?1:0)*0x80)*0x10000) + " " + ((buf[6]&0x7F + (buf[6] < 0?1:0)*0x80)*0x100) + " " + (buf[7]&0x7F + (buf[7] < 0?1:0)*0x80));
				int size = ((buf[4]&0x7F + (buf[4] < 0?1:0)*0x80)*0x10000000*0x10) + ((buf[5]&0x7F + (buf[5] < 0?1:0)*0x80)*0x10000) + ((buf[6]&0x7F + (buf[6] < 0?1:0)*0x80)*0x100) + (buf[7]&0x7F + (buf[7] < 0?1:0)*0x80);
				byte[] content = new byte[size];
				raf.read(content);
				String encoding = "";
				switch(content[0]) {
				case 0:
					encoding = "iso-8859-1";break;
				case 1:
					encoding = "utf-16le";break;
				case 2:
					encoding = "utf-16be";break;
				case 3:
					encoding = "utf-8";break;
					default:break;
				}
				String frame_header = new String(buf, 0, 4);
				
				switch(frame_header) {
				case "TXXX":
					ID = new String(content, 6, size - 6, encoding);
					break;
				case "TIT2":
					musicName = new String(content, 1, size - 1, encoding);
					break;
				case "TALB":
					album = new  String(content, 1, size - 1, encoding);
					break;
				case "TPE1":
					singer = new String(content, 1, size - 1, encoding);
					break;
				case "COMM":
					System.out.println(new String(content, 1, size - 1, encoding));
					break;
				case "APIC":
					image = content;
					FileImageOutputStream fios;
					if("PNG".equals(new String(image, 15, 3)))
						fios = new FileImageOutputStream(new File("images/" + musicName + ".png"));
					else
						fios = new FileImageOutputStream(new File("images/" + musicName + ".jpg"));
					fios.write(image, 14, image.length - 14);
					fios.close();
					default:
						break;
				}
			}
			return new Music(path, "", image, musicName, singer, album, ID);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
