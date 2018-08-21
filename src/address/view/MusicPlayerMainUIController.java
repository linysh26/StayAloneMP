package address.view;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.Duration;
import sun.nio.ch.WindowsAsynchronousChannelProvider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.LinkedList;

import address.MusicPlayerMainUI;
import address.model.*;
import address.util.DurationUtil;
import address.util.Storage;
import address.view.MusicListPageController.PlayButtonCell;


/**
 * This class is a controller of the music player's main UI
 * Initializing the user's music list
 * Handling on action of buttons: PLAY_BUTTON, NEXT_BUTTON, LAST_BUTTON
 * Showing BorderPane's right by adding a listener to the change of page queue
 * 
 * @author 林一山
 *
 */

public class MusicPlayerMainUIController {
	
	@FXML
	private Button Button_Play;
	
	@FXML
	private Button Button_Next;
	
	@FXML
	private Button Button_Last;
	
	@FXML
	private Button Button_Volumn;
	
	@FXML
	private Label Label_PlayingList;
	
	@FXML
	private Button Button_CurrentPlayingMusicList;
	
	@FXML
	private Button Button_Mode;
	
	@FXML
	private Button Button_Search;
	
	@FXML
	private Button Button_Refresh;
	
	@FXML
	private Button Button_NextPage;
	
	@FXML
	private Button Button_LastPage;
	
	@FXML
	private Button Button_HomePage;
	
	@FXML
	private Button Button_Upload;
	
	@FXML
	private Button Button_Close;
	
	@FXML
	private Button Button_Minimum;
	
	@FXML
	private Button Button_Maximum;
	
	@FXML
	private Button Button_Lyric;
	
	@FXML
	private Button Button_Setting;
	
	@FXML
	private Button Button_ShowBilibili;
	
	@FXML
	private Button Button_BilibiliSetting;
	
	@FXML
	private Button Button_SendBilibili;
	
	@FXML
	private Button Button_ShowComment;
	
	@FXML
	private Label Label_Mood;
	
	@FXML
	private Label Label_CurrentMusic;
	
	@FXML
	private Label Music_Duration;
	
	@FXML
	private Label Lyric;
	
	@FXML
	private Label Playing_List_Number;
	
	@FXML
	private Label Volumn;
	
	@FXML
	private TextField Field_Search;
	
	@FXML
	private TextField Field_Bilibili;
	
	@FXML
	private AnchorPane AnchorPane_Top;
	
	@FXML
	private AnchorPane AnchorPane_Bottom;
	
	@FXML
	private AnchorPane AnchorPane_PlayingPane;
	
	@FXML
	private AnchorPane AnchorPane_CurrentListPane;
	
	@FXML
	private TableView<Music> TableView_CurrentList;
	
	@FXML
	private TableColumn<Music, Boolean> TableColumn_currentMusic;
	
	@FXML
	private TableColumn<Music, String> TableColumn_currentMusicName;
	
	@FXML
	private TableColumn<Music, String> TableColumn_currentMusicSinger;
	
	@FXML
	private HBox Volumn_Box;
	
	@FXML
	private HBox Bilibili_Box;
	
	@FXML
	private HBox MusicTag_Box;
	
	@FXML
	private ImageView Image_Music;
	
	@FXML
	private ImageView Image_MusicList;
	
	@FXML
	private ImageView Image_TheUp;
	
	@FXML
	private Slider Slider_Music;
	
	@FXML
	private Slider Slider_Volumn;
	
	@FXML
	private ProgressBar Progress_Music;
	
	@FXML
	private ProgressBar Progress_Volumn;
	
	// self-defined cell of the currently playing music table
	public class CurrentMusicTagCell<S, T> extends TableCell<S, T> {
		private final HBox HBox_Tag;
		private final Button Button_Play;
		private final Button Button_Remove;
		private ObservableValue<T> ov;
		public CurrentMusicTagCell() {
			this.Button_Play = new Button("||");
			this.Button_Remove = new Button("-");
			this.HBox_Tag = new HBox();
			this.HBox_Tag.getChildren().add(this.Button_Play);
			this.HBox_Tag.getChildren().add(this.Button_Remove);
			//add to show
			setGraphic(HBox_Tag);
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
				setGraphic(HBox_Tag);
			}
		}
	}
	
	
	public MusicPlayerMainUIController() {}
	
	@FXML
	private void initialize() {
		
		//set next page and last page button disabled
		this.Button_NextPage.setDisable(true);
		this.Button_LastPage.setDisable(true);
		this.Slider_Music.setDisable(true);
		
		this.Volumn.setText("50");
		this.Volumn.setPrefWidth(50);
		this.Label_Mood.setText("\\(≥▽≤)>");
		this.Label_CurrentMusic.setText("Music");
		this.Label_CurrentMusic.setAlignment(Pos.CENTER);
		this.Image_Music.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
		this.Image_MusicList.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
		
		this.Button_Close.setText("X");
		this.Button_Maximum.setText("口");
		this.Button_Minimum.setText("__");
		this.Button_Play.setText(">");
		this.Button_Next.setText("->");
		this.Button_Last.setText("<-");
		this.Button_Play.setDisable(true);;
		this.Button_Next.setDisable(true);
		this.Button_Last.setDisable(true);
		this.Button_NextPage.setText(">");
		this.Button_LastPage.setText("<");
		this.Button_Refresh.setText("O");
		this.Button_HomePage.setText("合");
		this.Button_Setting.setText("三");
		this.Label_PlayingList.setText("0");
		this.Button_Search.setText("Search");
		this.Button_Volumn.setText("Sound");
		
		this.AnchorPane_CurrentListPane = new AnchorPane();
		this.AnchorPane_CurrentListPane.setPrefHeight(600.0);
		this.AnchorPane_CurrentListPane.setPrefWidth(400.0);
		// set content and property of the currently playing musics list table
		this.TableView_CurrentList = new TableView<Music>();
		this.TableColumn_currentMusic = new TableColumn<Music, Boolean>();
		this.TableColumn_currentMusicName = new TableColumn<Music, String>();
		this.TableColumn_currentMusicSinger = new TableColumn<Music, String>();
		// initialize currently playing music pane
		this.AnchorPane_PlayingPane.getChildren().add(this.AnchorPane_CurrentListPane);
		AnchorPane.setBottomAnchor(AnchorPane_CurrentListPane, 0.0);
		AnchorPane.setLeftAnchor(AnchorPane_CurrentListPane, 0.0);
		Label top_label = new Label("播放列表");
		top_label.setFont(Font.font(20));
		top_label.autosize();
		Button close_button = new Button("X");
		close_button.setOnAction(e -> {
			this.AnchorPane_CurrentListPane.setVisible(false);
		});
		ObservableList<Node> items = this.AnchorPane_CurrentListPane.getChildren();
		items.add(close_button);
		items.add(top_label);
		items.add(TableView_CurrentList);
		// set elements' anchor upon parent
		AnchorPane.setTopAnchor(close_button, 5.0);
		AnchorPane.setRightAnchor(close_button, 5.0);
		AnchorPane.setTopAnchor(top_label, 10.0);
		AnchorPane.setRightAnchor(top_label, 160.0);
		AnchorPane.setLeftAnchor(top_label, 160.0);
		AnchorPane.setRightAnchor(TableView_CurrentList, 0.0);
		AnchorPane.setLeftAnchor(TableView_CurrentList, 0.0);
		AnchorPane.setBottomAnchor(TableView_CurrentList, 0.0);
		this.TableView_CurrentList.setPrefHeight(550);
		this.TableView_CurrentList.getColumns().add(this.TableColumn_currentMusic);
		this.TableView_CurrentList.getColumns().add(this.TableColumn_currentMusicName);
		this.TableView_CurrentList.getColumns().add(this.TableColumn_currentMusicSinger);
		this.TableColumn_currentMusic.setPrefWidth(80);
		this.TableColumn_currentMusicName.setPrefWidth(200);
		this.TableColumn_currentMusicSinger.setPrefWidth(120);
		// override the table's column
		this.TableColumn_currentMusic.setCellFactory(new Callback<TableColumn<Music, Boolean>, TableCell<Music, Boolean>>(){
			public TableCell<Music, Boolean> call(TableColumn<Music, Boolean> param){
				final CurrentMusicTagCell<Music, Boolean> TagCell = new CurrentMusicTagCell<Music, Boolean>();
				final HBox tag_box = (HBox)TagCell.getGraphic();
				final ObservableList<Node> items = tag_box.getChildren();
				Button play_button = (Button)items.get(0);
				Button remove_button = (Button)items.get(1);
				play_button.setOnAction(e -> {
					try{
						Music music = MusicPlayer.getMusicPlayingList().getMusicList().get(TagCell.getIndex());
						musicList.addNewPlayingMusic(music);
					}catch(Exception exp) {
						exp.printStackTrace();
					}
				});
				remove_button.setOnAction(e -> {
					try{
						musicList.removeMusic(TagCell.getIndex());
					}catch(Exception exp) {
						exp.printStackTrace();
					}
				});
				return TagCell;
			}
		});
		this.TableColumn_currentMusicName.setCellValueFactory(celldata -> celldata.getValue().getMusicNameProperty());
		this.TableColumn_currentMusicSinger.setCellValueFactory(celldata -> celldata.getValue().getSingerProperty());
		this.AnchorPane_CurrentListPane.setVisible(false);
	}
	
	/**
	 * ******************************WINDOW'S*FUNDAMENTAL*FUNCTION*BUTTON***************************************
	 */
	@FXML
	private void closeButtonHandler() {						//关闭
		Storage.write(new File("Local/LocalMusic.csv"), MusicPlayer.getLocalMusicList());
		Platform.exit();
		System.exit(0);
	}
	@FXML
	private void maximumButtonHandler() {					//最大化
		this.ScreenStatus = !this.ScreenStatus;
		this.MusicPlayer.getPrimaryStage().setFullScreen(this.ScreenStatus);
	}
	@FXML
	private void minimumButtonHander() {					//最小化
		MusicPlayer.getPrimaryStage().setIconified(true);
	}
	/**
	 * *****************************PLAY*CONTROLLING*BUTTON******************************************************
	 */
	@FXML
	private void nextMusicButtonHandler() {
		musicList.forward();
	}
	@FXML
	private void lastMusicButtonHandler() {
		musicList.backward();
	}
	@FXML
	private void playingButtonHandler() {
		Status status = mediaPlayer.getStatus();
		
		if(status == Status.UNKNOWN || status == Status.HALTED)
			return;
		
		if(status == Status.PAUSED || status == Status.READY || status == Status.HALTED) {
			mediaPlayer.play();
			this.Button_Play.setText("||");
		}
		else {
			mediaPlayer.pause();
			this.Button_Play.setText(">");
		}
	}
	
	@FXML
	private void playingListButtonHandler() {
		this.AnchorPane_CurrentListPane.setVisible(!this.AnchorPane_CurrentListPane.isVisible());
	}
	
	@FXML
	private void volumnButtonHandler() {
		if(this.isSilent) {
			this.Slider_Volumn.setValue(this.volumnBeforeSilent);
			if(this.mediaPlayer != null)
				this.mediaPlayer.setVolume(this.volumnBeforeSilent/100);
			this.isSilent = false;
			this.Button_Volumn.setText("Sound");
		}
		else {
			this.volumnBeforeSilent = this.Slider_Volumn.getValue();
			this.Slider_Volumn.setValue(0);
			this.isSilent = true;
			this.Button_Volumn.setText("Silent");
		}
	}
	
	@FXML
	private void playModeButtonHandler() {
		if(this.isSingCycle)
			this.Button_Mode.setStyle("ContiguousMode");
		else
			this.Button_Mode.setStyle("SingleMode");
		this.isSingCycle = !isSingCycle;
	}
	
	@FXML
	private void lyricButtonHandler() {
		
	}
	/**
	 * ***************************CLIENT*PROPERTIES*RELATING*BUTTON***********************************************
	 */
	@FXML
	private void homeButtonHandler() {
		this.MusicPlayer.getPageQueue().addPage(Page.P.new HomePage());
	}
	
	@FXML
	private void settingButtonHandler() {
		this.MusicPlayer.showSettingPage();
	}
	/**
	 * ***************************PAGE*QUEUE*OPERATION*BUTTON*****************************************************
	 */
	@FXML
	private void nextPageButtonHandler() {
		this.MusicPlayer.getPageQueue().foreward();
	}
	
	@FXML
	private void lastPageButtonHandler() {
		this.MusicPlayer.getPageQueue().backward();
	}
	
	@FXML
	private void refreshButtonHandler() {
		MusicPlayer.showCurrentPage(MusicPlayer.getPageQueue().getCurrentPage());
	}
	@FXML
	private void searchButtonHandler() {
		String key = this.Field_Search.getText();
		if(this.Field_Search.getText() != null) {
			LinkedList<Music> musicList_result = MusicPlayer.getSearchResult(key);
			this.MusicPlayer.getPageQueue().addPage(Page.P.new SearchPage(musicList_result, key));
		}
		else
			return;
	}
	/**
	 * ***************************BILIBILI*OPERATION*BUTTON*******************************************************
	 * 
	 * 敬请期待
	 * ************************************TO**BE*****************************************************************
	 * ***********************************CONTINUE****************************************************************
	 * **************************************咚*******************************************************************
	 
	@FXML
	private void showBilibiliButtonHandler() {
	}
	@FXML
	private void bilibiliSettingButtonHandler() {
		
	}
	@FXML
	private void bilibiliSendButtonHandler() {
		
	}
	@FXML
	private void showCommentButtonHandler() {
		
	}
	*/

	private void addListenerToMainUI() {
		PageQueue pageQueue = MusicPlayer.getPageQueue();
		this.Slider_Volumn.setMax(100);
		this.Slider_Volumn.setMin(0);
		Slider_Volumn.setValue(50);
		this.Slider_Music.setMax(100);
		this.Slider_Music.setMin(0);
		Progress_Volumn.setProgress(0.5);
			
		//add listener to page queue
		pageQueue.getIndexProperty().addListener(
				(observable, oldValue, newValue) -> {
					System.out.println("Current Page change.");
					MusicPlayer.showCurrentPage(pageQueue.getCurrentPage());
					this.Button_LastPage.setDisable(!pageQueue.backwardAvailable());
					this.Button_NextPage.setDisable(!pageQueue.forewardAvailable());
				});
		
		//add listener to number of music in the current playing music list
		musicList.getSizeProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Label_PlayingList.setText(Integer.toString(newValue.intValue()));
					boolean Unable = newValue.intValue() == 0;
					this.Button_Play.setDisable(Unable);
					this.Button_Next.setDisable(Unable);
					this.Button_Last.setDisable(Unable);
					this.Slider_Music.setDisable(Unable);
					if(Unable) {
						this.mediaPlayer.stop();
						this.Label_Mood.setText("(* ￣︿￣)");
						this.Label_CurrentMusic.setText("Music");
						this.Image_Music.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
						this.Image_MusicList.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
						this.Slider_Music.setValue(0.0);
					}
					else
						this.Label_Mood.setText("");
				});
		
		//add listener to current music
		this.musicList.currentPlayingMusicID().addListener(
				(observable, oldValue, newValue) -> {
					Music currentMusic = musicList.getCurrentPlayingMusic();
					if(currentMusic != null) {
						this.Slider_Music.setDisable(false);
						this.Label_CurrentMusic.setText(currentMusic.getTag());
						ByteArrayInputStream in = new ByteArrayInputStream(currentMusic.getAlbumPicture(), 14, currentMusic.getAlbumPicture().length - 14);
						ByteArrayInputStream in_album = new ByteArrayInputStream(currentMusic.getAlbumPicture(), 14, currentMusic.getAlbumPicture().length - 14);;
						if(musicList.getCurrentPlayingMusic().getPicturePath().length() != 0) {
							this.Image_Music.setImage(new Image(new File(musicList.getCurrentPlayingMusic().getPicturePath()).toURI().toString()));
							this.Image_MusicList.setImage(new Image(new File(musicList.getCurrentPlayingMusic().getPicturePath()).toURI().toString()));
						}
						else if(musicList.getCurrentPlayingMusic().getAlbumPicture().length != 0) {
							this.Image_Music.setImage(new Image(in));
							this.Image_MusicList.setImage(new Image(in_album));
						}
						else {
							this.Image_Music.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
							this.Image_MusicList.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
						}
						if(this.media != null)
							this.mediaPlayer.stop();
						media = new Media(new File(currentMusic.getPath()).toURI().toString());
						this.mediaPlayer = new MediaPlayer(media);
						Duration current_duration = mediaPlayer.getCurrentTime();
						Duration end_duration = mediaPlayer.getStopTime();
						this.Music_Duration.setText(DurationUtil.parse(current_duration) + "/" + DurationUtil.parse(end_duration));
						mediaPlayer.seek(mediaPlayer.getStartTime());
						mediaPlayer.setAutoPlay(true);
						mediaPlayer.setVolume(this.Slider_Volumn.getValue()/100);
						this.Button_Play.setText("||");
						this.mediaPlayer.statusProperty().addListener(
							(ob, ov, nv) -> {
								if(nv == Status.UNKNOWN || nv == Status.HALTED)
									this.Label_Mood.setText("(* ￣︿￣)");
								if(nv == Status.PAUSED || nv == Status.READY || nv == Status.HALTED) {
									this.Label_Mood.setText("Σ(っ °Д °;)っ");
								}
								else {
									this.Label_Mood.setText("o(*≧▽≦)ツ┏━┓");
								}
							});
						this.mediaPlayer.currentTimeProperty().addListener(
								(ob, ov, nv) -> {
									Duration currentDuration = this.mediaPlayer.getCurrentTime();
									Duration duration = this.mediaPlayer.getTotalDuration();
									if(!Slider_Music.isPressed())
										this.Slider_Music.setValue(currentDuration.toMillis()/duration.toMillis()*100);
									if(currentDuration.toSeconds() >=  duration.toSeconds() - 0.1) {
										this.Button_Play.setText(">");
										this.musicList.forward();
									}
								});
						
					}
					else {
						this.Slider_Music.setDisable(true);
					}
				});
		
		//add listener to music and volume slider action
		
		this.Slider_Music.setOnMouseClicked(
				e -> {
					double newValue = Slider_Music.getValue();
					if(newValue/100 != this.Progress_Music.getProgress())
						this.Progress_Music.setProgress(newValue);
					this.mediaPlayer.pause();
					this.mediaPlayer.seek(new Duration(newValue/100*mediaPlayer.getTotalDuration().toMillis()));
					this.Button_Play.setText("||");
					this.mediaPlayer.play();
				});
		this.Slider_Music.setOnMouseDragged(
				e -> {
					double newValue = Slider_Music.getValue();
					this.Progress_Music.setProgress(newValue/100);
					this.Music_Duration.setText(DurationUtil.parse(new Duration(mediaPlayer.getTotalDuration().toMillis()*Progress_Music.getProgress())) + "/"
							+ DurationUtil.parse(mediaPlayer.getTotalDuration()));
				});
		this.Slider_Music.setOnMouseDragEntered(
				e -> {
					double newValue = Slider_Music.getValue();
					this.Progress_Music.setProgress(newValue/100);
					
				});
		this.Slider_Music.setOnMouseDragExited(
				e -> {
					double newValue = Slider_Music.getValue();
					this.Progress_Music.setProgress(newValue);
					this.mediaPlayer.pause();
					this.mediaPlayer.seek(new Duration(newValue/100*mediaPlayer.getTotalDuration().toMillis()));
					this.mediaPlayer.play();
				});
		this.Slider_Volumn.setOnMouseClicked(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100F);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragged(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100F);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragEntered(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100F);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragExited(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100F);
					this.mediaPlayer.setVolume(newValue/100);
				});
		
		
		this.Slider_Music.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Progress_Music.setProgress((double)newValue/100);
					this.Music_Duration.setText(DurationUtil.parse(mediaPlayer.getCurrentTime()) + "/"
							+ DurationUtil.parse(mediaPlayer.getTotalDuration()));
					if(this.Slider_Music.getValue() == 100) {
						if(isSingCycle)
							mediaPlayer.seek(mediaPlayer.getStartTime());
						else
							this.musicList.forward();
					}
				});
		this.Slider_Volumn.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Progress_Volumn.setProgress((double)newValue/100F);
					this.Volumn.setText(Integer.toString(newValue.intValue()));
					if(this.mediaPlayer != null)
						this.mediaPlayer.setVolume(newValue.intValue()/100);
				});
		
		this.Image_MusicList.setOnMouseClicked(e -> {
			
		});
		
		//add listener to text field
	}
	
	public void setMusicPlayer(MusicPlayerMainUI MusicPlayer) {
		this.MusicPlayer = MusicPlayer;
		this.musicList = MusicPlayer.getMusicPlayingList();
		this.TableView_CurrentList.setItems(musicList.getMusicList());
		this.addListenerToMainUI();
	}
	
	
	//Status
	private boolean ScreenStatus = false;
	private boolean isSilent = false;
	private boolean isSingCycle = false;
	private double volumnBeforeSilent = 50;
	
	private MyMusicList musicList;
	private MusicPlayerMainUI MusicPlayer;
	private MediaPlayer mediaPlayer;
	private Media media;
}
