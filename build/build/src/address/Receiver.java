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
	private String receiveString;
	private File receiveFile;
	
	 static {  
	        // 设置数字格式，保留一位有效小数  
	        df = new DecimalFormat("#0.0");  
	        df.setRoundingMode(RoundingMode.HALF_UP);  
	        df.setMinimumFractionDigits(1);  
	        df.setMaximumFractionDigits(1);  
	    }  
	 
	 public Receiver() throws Exception {  
	        super(SERVER_PORT);  
	        message = new String("");
	    }  
	 
	 public void load() throws Exception {
		 while(true) {
            Socket socket = this.accept();   
            new Thread(new Task(socket)).start();  
	    }
	 }
	 
	 class Task implements Runnable {
		 
		 public static final int SEARCH_TASK = 0, DOWNLOAD_TASK = 1, UPLOAD_TASK = 2;
		 
		 private String MUSIC_PATH;
		private String ALBUM_PATH;
		private String key;
		  
	        private Socket socket;  
	  
	        private DataInputStream dis;  
	  
	        private FileOutputStream fos;  
	        
	        private DataInputStream musicDis;
	        
	        private FileOutputStream musicFos;
	  
	        public Task(Socket socket) {
	            this.socket = socket;  
	        }  
	        
	        @Override  
	        public void run() {  
	            try {  
	                dis = new DataInputStream(socket.getInputStream());
	                message = dis.readUTF();
	                System.out.println(message);
	                int task = Integer.parseInt(message);
	                if(task == 1) {
	                	
	                	
	                	key = dis.readUTF();
	                	List<Music> musicData = Storage.read("Server/AllMusic.csv").stream().filter(m -> m.getMusicName() == key
	                || m.getAlbumName() == key || m.getSinger() == key).collect(Collectors.toList());
	                	
	                	Sender send = new Sender(socket.getRemoteSocketAddress().toString());
	                	send.sendString(Integer.toString(musicData.size()));
	                	for(int i = 0;i < musicData.size();i++) {
	                		send.sendString(musicData.get(i).getMusicName());
	                		send.sendString(musicData.get(i).getSinger());
	                		send.sendString(musicData.get(i).getAlbumName());
	                	}
	                	send.close();
	                }
	                else if(task == 2){
	                	// 文件名和长度  
		                System.out.println(message);
	                	
		                String musicName = dis.readUTF();
		                System.out.println(musicName);
		                String singer = dis.readUTF();
		                System.out.println(singer);
		                String album = dis.readUTF();
		                System.out.println(album);
		                
		                String albumFileName = dis.readUTF();
		                System.out.println(albumFileName);
		                long albumFileLength = dis.readLong();
		                System.out.println(albumFileLength);
		                
		                File albumDirectory = new File("Server/imageData/");  
		                if(!albumDirectory.exists()) {  
		                    albumDirectory.mkdir();
		                }  
		                File albumFile = new File(albumDirectory.getAbsolutePath() + File.separatorChar + albumFileName);  
		                fos = new FileOutputStream(albumFile);  
		  
		                // 开始接收歌曲图片文件  
		                byte[] albumBytes = new byte[1024];
		                
		                int albumLength = 0;  
		                while((albumLength = dis.read(albumBytes, 0, albumBytes.length)) != -1) {
		                    fos.write(albumBytes, 0, albumLength);  
		                    fos.flush();  
		                }
		                
		                System.out.println("finish receiving file");
		                
		                musicDis = new DataInputStream(socket.getInputStream());
		                
		                File musicDirectory = new File("Server/musicData/");  
		                if(!musicDirectory.exists()) {  
		                    musicDirectory.mkdir();  
		                }  
		                
		                String musicFileName = musicDis.readUTF();
		                long musicFileLength = musicDis.readLong();
		                System.out.println(musicFileName);
		                System.out.println(musicFileLength);
		                
		                File musicFile = new File(musicDirectory.getAbsolutePath() + File.separatorChar + musicFileName);  
		                fos = new FileOutputStream(musicFile);
		                
		                // 开始接收文件  
		                byte[] bytes = new byte[1024];  
		                int length = 0;  
		                while((length = musicDis.read(bytes, 0, bytes.length)) != -1) {  
		                    fos.write(bytes, 0, length);  
		                    fos.flush();  
		                }
		                LinkedList<Music> musicData = Storage.read("Server/AllMusic.csv");
		                musicData.add(new Music(musicFile.getPath(), albumFile.getPath(), musicName, singer, album));
		                Storage.write(new File("Server/AllMusic.csv"), musicData);
	                }
	                else {
	                	String musicInformation = dis.readUTF();
	                	Sender send = new Sender(socket.getRemoteSocketAddress().toString());
	                	LinkedList<Music> musicData = Storage.read("Server/AllMusic.csv");
	                	Music music = null;
	                	for(int i = 0;i < musicData.size();i++) {
	                		if(musicData.get(i).toString() == musicInformation) {
	                			music = musicData.get(i);break;}
	                	}
	                	if(music == null)
	                		send.sendString("No such music");
	                	else {
	                		send.sendFile(music.getPath());
	                		send.close();
	                	}
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
	 
	 public String getMessage() {
		 return this.message;
	 }
	 
	 public String getReceiveString() {
		 return this.receiveString;
	 }
	 
	 public File getReceiveFile() {
		 return this.receiveFile;
	 }
}
