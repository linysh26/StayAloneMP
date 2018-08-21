package address.view;

import java.util.ArrayList;
import java.util.List;

import address.MusicPlayerMainUI;
import address.model.*;
import address.view.SearchPageController.DownloadButtonCell;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class HomePageController {
	
	@FXML
	private AnchorPane AnchorPane_HomePage;
	
	@FXML
	private ScrollPane ScrollPane_CoversPane;
	
	@FXML
	private TabPane TabPane_;
	
	@FXML
	private Tab Tab_Music;
	
	@FXML
	private Tab Tab_Manga;
	
	@FXML
	private Tab Tab_Novel;
	
	@FXML
	private Tab Tab_Vedio;
	
	// list for each tab
	private ArrayList<MyMusicList> musicLists;
	private ArrayList<MyMangaList> mangaLists;
	private ArrayList<MyNovelList> novelLists;
	private ArrayList<MyVideoList> videoLists;
	
	
	@FXML
	private void initialize() {
		
		
		
		
		
	}
	
	public void setMusicPlayer(MusicPlayerMainUI musicPlayer) {
		this.musicPlayer = musicPlayer;
	}
	
	// reference of the music player
	private MusicPlayerMainUI musicPlayer;
}
