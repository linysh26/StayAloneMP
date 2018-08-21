package address;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import address.model.*;
import address.model.Page.HomePage;
import address.model.Page.SearchPage;
import address.model.Page.SettingPage;
import address.util.*;
import address.view.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.media.*;

/**
 * Main application
 * 
 * launch the application and show pane
 * 
 * @author 林一山
 *
 */
public class MusicPlayerMainUI extends Application{
	
	private static final String MusicPlayerName = "CrescentFairy";
	
	private Stage primaryStage;
	private PageQueue pageQueue;
	private BorderPane rootLayout;
	private Page currentPage;
	private AnchorPane currentAnchorPane;
	

	private SearchPageController Controller_SearchPage = null;
	private HomePageController Controller_HomePage = null;
	private AddLocalMusicController Controller_AddLocalMusic = null;
	
	private ObservableList<Music> MusicList = FXCollections.observableArrayList();
	private LinkedList<Music> LocalMusicList;
	private MyMusicList MusicPlayingList;
	
	private double XOffset = 0;
	private double YOffset = 0;
	
	private Sender send;
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public PageQueue getPageQueue() {
		return this.pageQueue;
	}
	
	public BorderPane getRootLayout() {
		return this.rootLayout;
	}
	
	public Page getCurrentPage() {
		return this.currentPage;
	}
	
	public AnchorPane getCurrentAnchorPane() {
		return this.currentAnchorPane;
	}
	
	public ObservableList<Music> getMusicList(){
		return this.MusicList;
	}
	
	public LinkedList<Music> getLocalMusicList(){
		return this.LocalMusicList;
	}
	
	public MyMusicList getMusicPlayingList() {
		return this.MusicPlayingList;
	}
	
	public double getXOffset() {
		return this.XOffset;
	}
	
	public double getYOffset() {
		return this.YOffset;
	}
	
	public MusicPlayerMainUI() {}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.pageQueue = new PageQueue();
		this.MusicPlayingList = new MyMusicList();

		try {
			Thread.currentThread().setName("MusicPlayer");
			//set BorderPane
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MusicPlayerMainUI.fxml"));
			rootLayout = (BorderPane)loader.load();
			MusicPlayerMainUIController Controller_MainUI = loader.getController();
			Controller_MainUI.setMusicPlayer(this);
			
			LocalMusicList = Storage.read("Local/LocalMusic.csv");
			this.currentPage = Page.P.new HomePage();
			this.pageQueue.addPage(currentPage);
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		rootLayout.setOnMousePressed( event -> { 
			XOffset = event.getSceneX();
			YOffset = event.getSceneY();
			});
		
		rootLayout.setOnMouseDragged( event -> { 
			primaryStage.setX(event.getScreenX() - XOffset); 
			primaryStage.setY(event.getScreenY() - YOffset);
			});
		
		
		Scene scene = new Scene(rootLayout);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setFullScreen(false);
		primaryStage.setTitle(MusicPlayerName);
		primaryStage.setScene(scene);
		
		primaryStage.show();
		
	}
	
	public void showCurrentPage(Page newPage) {
		FXMLLoader loader;
		
		try {
			switch(newPage.getType()) {
				case Page.HOME:
					loader = new FXMLLoader(MusicPlayerMainUI.class.getResource("view/HomePage.fxml"));
					MusicList.setAll(this.LocalMusicList);
					currentAnchorPane = (AnchorPane) loader.load();
					Controller_HomePage = loader.getController();
					Controller_HomePage.setMusicPlayer(this);
					break;
				case Page.SEARCH:
					Page.SearchPage result = (Page.SearchPage)newPage;
					MusicList.setAll(result.getMusicList());
					loader = new FXMLLoader(MusicPlayerMainUI.class.getResource("view/SearchPage.fxml"));
					currentAnchorPane = (AnchorPane)loader.load();
					Controller_SearchPage = loader.getController();
					Controller_SearchPage.setMusicPlayer(this);
					break;
				default:
					loader = new FXMLLoader(MusicPlayerMainUI.class.getResource("view/DefaultErrorPage.fxml"));
					currentAnchorPane = (AnchorPane)loader.load();
					break;
			}
			this.rootLayout.setCenter(this.currentAnchorPane);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public boolean showAddLocalMusicDialog(Music newMusic) {
		try {
			FXMLLoader loader = new FXMLLoader(MusicPlayerMainUI.class.getResource("view/AddLocalMusicDialog.fxml"));
			AnchorPane addLocalMusicAnchorPane = (AnchorPane)loader.load();
			
			
			
			Scene scene = new Scene(addLocalMusicAnchorPane);
			Stage addLocalMusicDialogStage = new Stage();
			addLocalMusicDialogStage.setTitle("LOG IN");
			addLocalMusicDialogStage.initStyle(StageStyle.UNDECORATED);
			addLocalMusicDialogStage.initModality(Modality.WINDOW_MODAL);
			addLocalMusicDialogStage.initOwner(primaryStage);
			addLocalMusicDialogStage.setScene(scene);
			
			Controller_AddLocalMusic = loader.getController();
			Controller_AddLocalMusic.setStage(addLocalMusicDialogStage);
			
			Controller_AddLocalMusic.setMusic(newMusic);
			
			addLocalMusicDialogStage.showAndWait();
			return Controller_AddLocalMusic.isAdd();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		return false;
	}
	
	public void addLocalMusic(Music newMusic) {
		if(LocalMusicList.contains(newMusic)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(primaryStage);
			alert.setContentText("Please select another music </>");
			alert.setHeaderText("The music has been added");
			alert.setTitle("Same music exist!");
			alert.showAndWait();
			return;
		}
		this.MusicList.add(newMusic);
		this.LocalMusicList.add(newMusic);
        Storage.write(new File("Local/LocalMusic.csv"), LocalMusicList);
	}
	
	public LinkedList<Music> getSearchResult(String key) {
		try {
			send = new Sender("172.18.32.176");
			send.sendString("1");
			send.sendString(key);
			ServerSocket srv = new ServerSocket(1010);
			Socket socket = srv.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String size = dis.readUTF();
			
			LinkedList<Music> searchResult = new LinkedList<Music>();
			for(int i = 0;i < Integer.parseUnsignedInt(size, 10);i++) {
				searchResult.add(new Music("","", dis.readUTF(), dis.readUTF(), dis.readUTF()));
			}
			return searchResult;
			
		}catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new LinkedList<Music>();
	}
	
	public void download(String information) {
		try {
			send = new Sender("172.18.32.176");
			send.sendString("3");
			send.sendString(information);
			ServerSocket srv = new ServerSocket(1010);
			Socket socket = srv.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			String message = null;
			message = dis.readUTF();
			
			if(message == "No such music") {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(primaryStage);
				alert.setContentText("Please try later </>");
				alert.setHeaderText("Couldn't find the very music in database");
				alert.setTitle("Download music error!");
				alert.showAndWait();
			}
			else {
				
				FileOutputStream fos;
				
				String musicName = dis.readUTF();
                String singer = dis.readUTF();
                String album = dis.readUTF();
                
                String albumFileName = dis.readUTF();
                long albumFileLength = dis.readLong();  
                File albumDirectory = new File("Local/imageData/");  
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
                File musicDirectory = new File("Local/musicData/");  
                if(!musicDirectory.exists()) {  
                    musicDirectory.mkdir();  
                }  
                String musicFileName = dis.readUTF();  
                long musicFileLength = dis.readLong();
                File musicFile = new File(musicDirectory.getAbsolutePath() + File.separatorChar + musicFileName);  
                fos = new FileOutputStream(musicFile);
                
                // 开始接收文件  
                byte[] bytes = new byte[1024];  
                int length = 0;  
                while((length = dis.read(bytes, 0, bytes.length)) != -1) {  
                    fos.write(bytes, 0, length);  
                    fos.flush();  
                }
                Music newMusic = new Music(musicFile.getPath(), albumFile.getPath(), musicName, singer, album);
                LocalMusicList.add(newMusic);
                
                Storage.write(new File("Local/LocalMusic.csv"), LocalMusicList);
                
                srv.close();
                if(dis != null)
                	dis.close();
                if(fos != null)
                	fos.close();
			}
			
			
		}catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void upload(int index) {
		Music music = MusicList.get(index);
		try {
			send = new Sender("172.18.32.176");
			send.sendString("2");
			send.sendString(music.getMusicName());
			send.sendString(music.getSinger());
			send.sendString(music.getAlbumName());
			send.sendFile(music.getPath());
			send.close();
		}catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void removeMusic(int index) {
		this.MusicList.remove(index);
		this.LocalMusicList.remove(index);
		Storage.write(new File("Local/LocalMusic.csv"), LocalMusicList);
	}
	
	public void addNewMusicToPlayingList(Music music) {
		this.MusicPlayingList.addNewMusic(music);
	}

	public static void main(String []args) {
		launch(MusicPlayerMainUI.class ,args);
	}
}
