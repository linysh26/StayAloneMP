package address.view;

import java.util.List;

import address.MusicPlayerMainUI;
import address.model.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class SearchPageController {
	
	@FXML
	private AnchorPane AnchorPane_searchPage;//整个搜索页面的底层pane
	
	@FXML
	private TableView<Music> TableView_musicTable;//结果栏下的歌表id
	
	@FXML
	private TableColumn<Music, Boolean> TableColumn_Button;//下载按钮列
	
	@FXML
	private TableColumn<Music, String> TableColumn_search_musicTitle;//歌表中的“音乐标题”栏的id
	
	@FXML
	private TableColumn<Music, String> TableColumn_search_singer;//歌表中的“歌手”栏的id
	
	@FXML
	private TableColumn<Music, String> TableColumn_search_albumName;//歌表中的“专辑”栏的id
	
	@FXML
	private Label Tag;
	
	private MusicPlayerMainUI MusicPlayer;
	
	private String key;
	
	public class DownloadButtonCell<S, T> extends TableCell<S, T> {  
		private final Button Button_Download;
		private ObservableValue<T> ov;
		public DownloadButtonCell() {
			this.Button_Download = new Button();
			//添加元素  
			setGraphic(Button_Download);
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
				setGraphic(Button_Download);
			}
		}
	}
	
	@FXML
	private void initialize(){
		
		//initialize the information column
		TableColumn_search_musicTitle.setCellValueFactory(cellData -> cellData.getValue().getMusicNameProperty());
		TableColumn_search_singer.setCellValueFactory(cellData -> cellData.getValue().getSingerProperty());
		TableColumn_search_albumName.setCellValueFactory(cellData -> cellData.getValue().getAlbumNameProperty());
		TableColumn_Button.setCellValueFactory(new PropertyValueFactory<Music, Boolean>("TableColumn_Button"));
		
		//initialize the user operation column
		TableColumn_Button.setCellFactory(new Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>>(){
			public TableCell<Music, Boolean> call(TableColumn<Music, Boolean> param){
				final DownloadButtonCell<Music, Boolean> downloadCell = new DownloadButtonCell<Music, Boolean>();
				final Button downloadButton = (Button)downloadCell.getGraphic();
				downloadButton.setOnAction(e -> {
					Music music = MusicPlayer.getMusicList().get(downloadCell.getIndex());
					try{
						downloadMusic(music.toString());
					}catch(Exception exp) {
						exp.printStackTrace();
					}
				});
				return downloadCell;
			}
		});
		
		
		if(this.TableView_musicTable.getItems() == null)
			this.Tag.setText("Search " + key + " find " + 0);
		else
			this.Tag.setText("Search " + key + " find " + this.TableView_musicTable.getItems().size());
		
		//

	}
	private void downloadMusic(String information) {
		MusicPlayer.download(information);
	}
	public void setMusicPlayer(MusicPlayerMainUI MusicPlayer) {
        this.MusicPlayer = MusicPlayer;
        
        // Add observable list data to the table
        TableView_musicTable.setItems(MusicPlayer.getMusicList());
    }
	
	public void setKey(String key) {
		this.key = key;
	}
}
