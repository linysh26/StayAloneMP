package address;

import java.io.BufferedReader;
import java.io.DataOutputStream;  
import java.io.File;  
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;  

public class Sender extends Socket {
	private Socket client;
	public OutputStream outputStream = null;
	public FileInputStream fis;
	public DataOutputStream dos;
	public BufferedReader charStream = null;
	public String message;
	
	private static final int SERVER_PORT = 1010; // 服务端端口 
	
	public Sender(String SERVER_IP) throws Exception{
		 super(SERVER_IP, SERVER_PORT);
	     this.client = this;
	     outputStream = client.getOutputStream();
	     dos = new DataOutputStream(outputStream);
	     System.out.println("Client[port:" + client.getLocalPort() + "] 成功连接服务端");
	}
	
	public void sendString(String key) throws Exception {
		dos.writeUTF(key);
	}
	
	public void sendFile(String path) throws Exception {  
        try {  
        	File file = new File(path);  
            if(file.exists()) {  
                fis = new FileInputStream(file);
                
                System.out.println(file.getName());
                // 文件名和长度  
                dos.writeBytes(file.getName());
                dos.flush();
                dos.writeLong(file.length());  
                dos.flush();
                
                // 开始传输文件  
                System.out.println("======== 开始传输文件 ========");  
                byte[] bytes = new byte[1024];  
                int length = 0;  
                long progress = 0;  
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {  
                    dos.write(bytes, 0, length);  
                    dos.flush();  
                    progress += length;  
                    System.out.print("| " + (100*progress/file.length()) + "% |");  
                }
                System.out.println();  
                System.out.println("======== 文件传输成功 ========");  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }
}
