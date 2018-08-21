package address;

import java.io.DataInputStream;  
import java.io.File;  
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.RoundingMode;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import address.model.Music;
import address.util.Storage;  

public class Receiver extends ServerSocket {
	
	
	private static final int SERVER_PORT = 1010; // 服务端端口 
	private static DecimalFormat df = null;
	
	private String message;
	
	static {
		// 设置数字格式，保留一位有效小数 
		df = new DecimalFormat("#0.0");
		df.setRoundingMode(RoundingMode.HALF_UP);
		df.setMinimumFractionDigits(1);
		df.setMaximumFractionDigits(1);
		}
	public Receiver() throws Exception {
		super(SERVER_PORT);
	}
	public void load() throws Exception {
		while(true) {
			Socket socket = this.accept();
			new Thread(new Task(socket)).start();
		}
	}
	class Task implements Runnable {
		public static final int SEARCH_TASK = 0, DOWNLOAD_TASK = 1, UPLOAD_TASK = 2;
		
		LinkedList<String> musicData;
		private Socket socket;
		private DataInputStream dis;
		private FileOutputStream fos;
		
		public Task(Socket socket) {
			this.socket = socket;
			musicData = Storage.read("Server/AllMusic.csv");
		}
		@Override
		public void run() {  
			try {  
			    dis = new DataInputStream(socket.getInputStream());
			    message = dis.readUTF();
			    int TaskType = Integer.parseInt(message);
			    switch(TaskType) {
			    
				    case SEARCH_TASK:
				    	String key = dis.readUTF();
				    	List<Music> result = Storage.read("Server/AllMusic.csv").stream().map(s -> Storage.parser(s)).filter(m -> m.getMusicName() == key
				|| m.getAlbumName() == key || m.getSinger() == key).collect(Collectors.toList());
					
						Sender send = new Sender(socket.getRemoteSocketAddress().toString());
						send.sendString(Integer.toString(result.size()));
						for(int i = 0;i < result.size();i++) {
							send.sendString(result.get(i).getMusicName());
							send.sendString(result.get(i).getSinger());
							send.sendString(result.get(i).getAlbumName());
						}
						send.close();
						break;
						
				    case UPLOAD_TASK:
					// 文件名和长度 
				    	System.out.println("Upload Task: ");
						File musicDirectory = new File("Server/musicData/");
						if(!musicDirectory.exists()) {  
						    musicDirectory.mkdir();  
						}
						
						String musicFileName = dis.readUTF();
						long musicFileLength = dis.readLong();
						String musicFilePath = musicDirectory.getAbsolutePath() + File.separatorChar + musicFileName;
						File musicFile = new File(musicFilePath);  
						fos = new FileOutputStream(musicFile);
						
						// 开始接收文件  
						byte[] bytes = new byte[1024];  
						int length = 0;
						while((length = dis.read(bytes, 0, bytes.length)) != -1) {  
						    fos.write(bytes, 0, length);
						    fos.flush();
						}
						System.out.println("======== 文件接收成功 [File Name：" + musicFileName + "] [Size：" + getFormatFileSize(musicFileLength) + "] ========");
						musicData.add(musicFilePath);
						Storage.write(new File("Server/AllMusic.csv"), musicData);
						break;
					case DOWNLOAD_TASK:
						System.out.println("Download Task: ");
						String musicInformation = dis.readUTF();
						Sender sender = new Sender(socket.getRemoteSocketAddress().toString());
						Music music = null;
						for(int i = 0;i < musicData.size();i++) {
							if(musicData.get(i).toString() == musicInformation) {
								music = Storage.parser(musicData.get(i));
								break;
							}
						}
						if(music == null)
							sender.sendString("No such music");
						else {
							sender.sendFile(music.getPath());
							sender.close();
						}
						break;
						default:break;
                }
            } catch (Exception e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    if(fos != null)  
                        fos.close();  
                    if(dis != null)  
                        dis.close();  
                    socket.close();  
                } catch (Exception e) {}  
            }
		}
    }
	private String getFormatFileSize(long length) {
		double size = ((double) length) / (1 << 30);
		if(size >= 1) {
			return df.format(size) + "GB";
		}
		size = ((double) length) / (1 << 20);
		if(size >= 1) {
			return df.format(size) + "MB";
			}
		size = ((double) length) / (1 << 10);
		if(size >= 1) {
			return df.format(size) + "KB";  
		}
		return length + "B";
	}
}
