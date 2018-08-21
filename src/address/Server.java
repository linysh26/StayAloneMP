package address;

import java.io.File;

public class Server {
	
	public static void main(String []args) {
		try {
			Receiver receive = new Receiver();
			receive.load();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
