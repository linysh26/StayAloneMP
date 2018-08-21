package address.view;

import java.util.List;

import javax.swing.event.HyperlinkEvent.EventType;

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

public class MusicListPageController {
	
	@FXML
	private AnchorPane AnchorPane_musicListPage;//整个搜索页面的底层pane
	
	@FXML
	private Button Button_Upload;
	
	@FXML
	private Button Button_Add;
	
	@FXML
	private Button Button_Remove;
	
	@FXML
	private TableView<Music> TableView_musicTable;//结果栏下的歌表id
	
	@FXML
	private TableColumn<Music, Boolean> TableColumn_Button;
	
	@FXML
	private TableColumn<Music, String> TableColumn_musicTitle;//歌表中的“音乐标题”栏的id
	
	@FXML
	private TableColumn<Music, String> TableColumn_singer;//歌表中的“歌手”栏的id
	
	@FXML
	private TableColumn<Music, String> TableColumn_albumName;//歌表中的“专辑”栏的id
	
	@FXML
	private Label Tag;
	
	private MusicPlayerMainUI MusicPlayer;
	
	public class PlayButtonCell<S, T> extends TableCell<S, T> {
		private final HBox HBox_Buttons;
		private final Button Button_Play;
		private final Button Button_Add;
		private final Button Button_Download;
		private ObservableValue<T> ov;
		public PlayButtonCell() {
			this.Button_Play = new Button("播放");
			this.Button_Add = new Button("+");
			this.Button_Download = new Button("↓");
			this.HBox_Buttons = new HBox();
			this.HBox_Buttons.getChildren().add(this.Button_Play);
			this.HBox_Buttons.getChildren().add(this.Button_Add);
			this.HBox_Buttons.getChildren().add(this.Button_Download);
			//添加元素  
			setGraphic(HBox_Buttons);
		}
		@Override
		protected void updateItem(T item, boolean empty) {
			System.out.println("empty："+empty);
			super.updateItem(item, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
				}
			else {
				setGraphic(HBox_Buttons);
			}
		}
	}
	
	@FXML
	private void initialize(){
	
		//initialize the information column
		TableColumn_musicTitle.setCellValueFactory(cellData -> cellData.getValue().getMusicNameProperty());
		TableColumn_singer.setCellValueFactory(cellData -> cellData.getValue().getSingerProperty());
		TableColumn_albumName.setCellValueFactory(cellData -> cellData.getValue().getAlbumNameProperty());
		
		//initial the button column for playing
		TableColumn_Button.setCellFactory(new Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>>(){
			public TableCell<Music, Boolean> call(TableColumn<Music, Boolean> param){
				final PlayButtonCell<Music, Boolean> playCell = new PlayButtonCell<Music, Boolean>();
				final HBox button_box = (HBox)playCell.getGraphic();
				final ObservableList<Node> buttons = button_box.getChildren();
				Button play_button = (Button)buttons.get(0);
				Button add_button = (Button)buttons.get(1);
				Button download_button = (Button)buttons.get(2);
				play_button.setOnAction(e -> {
					Music music = MusicPlayer.getMusicList().get(playCell.getIndex());
					try{
						addNewPlayingMusic(music);
					}catch(Exception exp) {
						exp.printStackTrace();
					}
				});
				add_button.setOnAction(e -> {
					Music music = MusicPlayer.getMusicList().get(playCell.getIndex());
					try{
						MusicPlayer.getMusicPlayingList().addNewMusic(music);
					}catch(Exception exp) {
						exp.printStackTrace();
					}
				});
				download_button.setDisable(true);
				return playCell;
			}
		});
		this.Tag.setText("My Music List");
	}
	@FXML
	private void addLocalMusic() {
		Music newMusic = new Music("music file path", "", new byte[0], "music", "singer", "album", "ID");
		boolean isAdd = this.MusicPlayer.showAddLocalMusicDialog(newMusic);
		if(isAdd) {
			this.MusicPlayer.addLocalMusic(newMusic);
			Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.initOwner(MusicPlayer.getPrimaryStage());
	    	alert.setTitle("Information");
	    	alert.setHeaderText("Add Music");
	    	alert.setContentText("SUCCESSFULLY!");
	    	alert.showAndWait();
		}
	}
	@FXML
	public void uploadMusic() {
		int selectedIndex = TableView_musicTable.getSelectionModel().getSelectedIndex();
	    if(selectedIndex >= 0)
	    	MusicPlayer.upload(selectedIndex);
	    else {
	    	Alert alert = new Alert(AlertType.WARNING);
	    	alert.initOwner(MusicPlayer.getPrimaryStage());
	    	alert.setTitle("NO SELECTION");
	    	alert.setHeaderText("NO MUSIC SELECTED!");
	    	alert.setContentText("Please select one music.");
	    	
	    	alert.showAndWait();
	    }
	}
	
	@FXML
	public void removeMusic() {
		int selectedIndex = TableView_musicTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0)
	    	MusicPlayer.removeMusic(selectedIndex);
	    else {
	    	Alert alert = new Alert(AlertType.WARNING);
	    	alert.initOwner(MusicPlayer.getPrimaryStage());
	    	alert.setTitle("NO SELECTION");
	    	alert.setHeaderText("NO MUSIC SELECTED!");
	    	alert.setContentText("Please select one music.");
	    	
	    	alert.showAndWait();
	    }
	}
	
	@FXML
	public void addNewPlayingMusic(Music music) {
		this.MusicPlayer.addNewMusicToPlayingList(music);
	}
	public void setMusicPlayer(MusicPlayerMainUI musicPlayer) {
        this.MusicPlayer = musicPlayer;
        
    	TableView_musicTable.setItems(MusicPlayer.getMusicList());
    }
}
