package address.view;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import sun.nio.ch.WindowsAsynchronousChannelProvider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.sun.glass.ui.Window;

import address.MusicPlayerMainUI;
import address.model.*;
import address.util.DurationUtil;
import address.util.Storage;


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
	private Button Button_LyricSetting;
	
	@FXML
	private Button Button_ShowBilibili;
	
	@FXML
	private Button Button_BilibiliSetting;
	
	@FXML
	private Button Button_SendBilibili;
	
	@FXML
	private Button Button_ShowComment;
	
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

	public Button getButton_Pause(){
		return this.Button_Play;
	}

	public Button getButton_Next(){
		return this.Button_Next;
	}

	public Button getButton_Last(){
		return this.Button_Last;
	}

	public Button getButton_Volumn(){
		return this.Button_Volumn;
	}

	public Button getButton_Search(){
		return this.Button_Search;
	}

	public Button getButton_Setting() {
		return this.Button_Upload;
	}
	
	public Button getButton_HomePage() {
		return this.Button_HomePage;
	}
	
	public Button getButton_Close() {
		return this.Button_Close;
	}
	
	public Button getButton_Minimum() {
		return this.Button_Minimum;
	}
	
	public Button getButton_Maximum() {
		return this.Button_Maximum;
	}
	
	public Button getButton_BilibiliSetting() {
		return this.Button_BilibiliSetting;
	}
	
	public Button getButton_ShowBilibili() {
		return this.Button_ShowBilibili;
	}
	
	public Button getButton_SendBilibili() {
		return this.Button_SendBilibili;
	}
	
	public Button getButton_ShowComment() {
		return this.Button_ShowComment;
	}
	
	public Button getButton_LyricSetting() {
		return this.Button_LyricSetting;
	}
	
	public Label getLabel_CurrentMusic() {
		return this.Label_CurrentMusic;
	}
	
	public Label getLabel_PlayingListNumber() {
		return this.Playing_List_Number;
	}
	
	public Label getLabel_Lyric() {
		return this.Lyric;
	}
	
	public Label getLabel_Volumn() {
		return this.Volumn;
	}
	
	public Label getLabel_MusicDuration() {
		return this.Music_Duration;
	}
	
	public AnchorPane getAnchorPane_Top() {
		return this.AnchorPane_Top;
	}
	
	public AnchorPane getAnchorPane_Bottom() {
		return this.AnchorPane_Bottom;
	}
	
	public AnchorPane getAnchorPane_PlayingPane() {
		return this.AnchorPane_PlayingPane;
	}
	
	public ProgressBar getProgressBar_Music() {
		return this.Progress_Music;
	}
	
	public ProgressBar getProgressBar_Volumn() {
		return this.Progress_Volumn;
	}
	
	public Slider getSlider_Music() {
		return this.Slider_Music;
	}
	
	public Slider getSlider_Volumn() {
		return this.Slider_Volumn;
	}
	
	public HBox getBilibili_Box() {
		return this.Bilibili_Box;
	}
	
	public HBox getVolumn_Box() {
		return this.Volumn_Box;
	}
	
	public HBox getMusicTag_Box() {
		return this.MusicTag_Box;
	}
	
	public ImageView getImage_Up() {
		return this.Image_TheUp;
	}
	
	public ImageView getImage_Music() {
		return this.Image_Music;
	}
	
	public ImageView getImage_MusicList() {
		return this.Image_MusicList;
	}
	
	
	public MusicPlayerMainUIController() {}
	
	@FXML
	private void initialize() {
		
		//set next page and last page button disabled
		this.Button_NextPage.setDisable(true);
		this.Button_LastPage.setDisable(true);
		this.Volumn.setText("50");
		this.Volumn.setPrefWidth(50);
		this.Playing_List_Number.setText("0");
		this.Label_CurrentMusic.setText("Music");
		this.Image_Music.setImage(new Image(new File("Images/xj.jpg").toURI().toString()));
		
		this.Button_Close.setText("X");
		this.Button_Maximum.setText("口");
		this.Button_Minimum.setText("__");
		this.Button_Play.setText(">");
		this.Button_Next.setText("->");
		this.Button_Last.setText("<-");
		this.Button_NextPage.setText(">");
		this.Button_LastPage.setText("<");
		this.Button_Refresh.setText("O");
		this.Button_HomePage.setText("合");
		this.Label_PlayingList.setText("Current");
		this.Button_Search.setText("Baidu");
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
		
	}
	
	@FXML
	private void volumnButtonHandler() {
		if(this.isSilent) {
			this.Slider_Volumn.setValue(this.volumnBeforeSilent);
			this.mediaPlayer.setVolume(this.volumnBeforeSilent);
			this.isSilent = false;
			this.Button_Volumn.setText("");
		}
		else {
			this.volumnBeforeSilent = this.Slider_Volumn.getValue();
			this.Slider_Volumn.setValue(0);
			this.isSilent = true;
			this.Button_Volumn.setText("/");
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
	/**
	 * ***************************CLIENT*PROPERTIES*RELATING*BUTTON***********************************************
	 */
	@FXML
	private void homeButtonHandler() {
		this.MusicPlayer.getPageQueue().addPage(Page.P.new HomePage());
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
		
		//add listener to the size of the playing list
		musicList.getSizeProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Button_Last.setDisable(musicList.getSizeValue() == 0);
					this.Button_Next.setDisable(musicList.getSizeValue() == 0);
					this.Slider_Music.setDisable(musicList.getSizeValue() == 0);
				});
		
		//add listener to number of music in the current playing music list
		musicList.getSizeProperty().addListener(
				(observable, oldValue, newValue) -> this.getLabel_PlayingListNumber().setText(Integer.toString(newValue.intValue())));
		
		//add listener to current music
		this.musicList.currentPlayingMusicID().addListener(
				(observable, oldValue, newValue) -> {
					Music currentMusic = musicList.getCurrentPlayingMusic();
					if(currentMusic != null) {
						this.Slider_Music.setDisable(false);
						this.Label_CurrentMusic.setText(currentMusic.getTag());
						if(musicList.getCurrentPlayingMusic().getAlbumPath() != "") {
							this.Image_Music.setImage(new Image(new File(currentMusic.getAlbumPath()).toURI().toString()));
						}
						else
							this.Image_Music.setImage(new Image(Music.DEFAULT_ALBUMPATH_));
						if(this.media != null)
							this.mediaPlayer.stop();
						media = new Media(new File(currentMusic.getPath()).toURI().toString());
						this.mediaPlayer = new MediaPlayer(media);
						Duration current_duration = mediaPlayer.getCurrentTime();
						Duration end_duration = mediaPlayer.getStopTime();
						this.Music_Duration.setText(DurationUtil.parse(current_duration) + "/" + DurationUtil.parse(end_duration));
						mediaPlayer.setVolume(this.Slider_Volumn.getValue());
						mediaPlayer.setAutoPlay(true);
						this.mediaPlayer.currentTimeProperty().addListener(
								(ob, ov, nv) -> {
									Duration currentDuration = this.mediaPlayer.getCurrentTime();
									Duration duration = this.mediaPlayer.getTotalDuration();
									if(!Slider_Music.isPressed())
									this.Slider_Music.setValue(currentDuration.toMillis()/duration.toMillis()*100);
								}); 
					}
					else {
						this.Slider_Music.setDisable(true);
					}
				});
		
		//add listener to music and volumn slider action
		
		this.Slider_Music.setOnMouseClicked(
				e -> {
					double newValue = Slider_Music.getValue();
					if(newValue/100 != this.Progress_Music.getProgress())
						this.Progress_Music.setProgress(newValue);
					this.mediaPlayer.pause();
					this.mediaPlayer.seek(new Duration(newValue/100*mediaPlayer.getTotalDuration().toMillis()));
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
					if(newValue/100 != this.Progress_Volumn.getProgress())
						this.Progress_Volumn.setProgress(newValue/100);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragged(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragEntered(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100);
					this.mediaPlayer.setVolume(newValue/100);
				});
		this.Slider_Volumn.setOnMouseDragExited(
				e -> {
					double newValue = Slider_Volumn.getValue();
					this.Progress_Volumn.setProgress(newValue/100);
					this.mediaPlayer.setVolume(newValue/100);
				});
		
		
		this.Slider_Music.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Progress_Music.setProgress((double)newValue/100);
					this.Music_Duration.setText(DurationUtil.parse(mediaPlayer.getCurrentTime()) + "/"
							+ DurationUtil.parse(mediaPlayer.getTotalDuration()));
					if(Progress_Music.getProgress() == 1) {
						if(isSingCycle)
							mediaPlayer.seek(mediaPlayer.getStartTime());
						else {
							if(musicList.getSizeValue() == 1)
								mediaPlayer.seek(mediaPlayer.getStartTime());
							else {
								this.musicList.forward();
							}
						}
					}
				});
		this.Slider_Volumn.valueProperty().addListener(
				(observable, oldValue, newValue) -> {
					this.Progress_Volumn.setProgress((double)newValue/100);
					this.Volumn.setText(Integer.toString(newValue.intValue()));
					this.mediaPlayer.setVolume(newValue.intValue()/100);
				});
		
		//add listener to text field
	}
	
	public void setMusicPlayer(MusicPlayerMainUI MusicPlayer) {
		this.MusicPlayer = MusicPlayer;
		this.musicList = MusicPlayer.getMusicPlayingList();
		this.addListenerToMainUI();
	}
	
	
	//Status
	private boolean ScreenStatus = false;
	private boolean PlayingStatus = false;
	private boolean isSilent = false;
	private boolean isSingCycle = false;
	private double volumnBeforeSilent = 50;
	
	private String lastPlayMusicPath = "";
	private MyMusicList musicList;
	private MusicPlayerMainUI MusicPlayer;
	private MediaPlayer mediaPlayer;
	private Media media;
}
