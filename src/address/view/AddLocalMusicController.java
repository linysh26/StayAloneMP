package address.view;

import java.io.File;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;

import address.model.Music;
import address.util.Storage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AddLocalMusicController {
	
	private Stage dialog;
	private boolean isAdd = false;
	private Music music;
	
	@FXML
	private AnchorPane AnchorPane_Dialog;
	
	@FXML
	private Button Button_Add;
	
	@FXML
	private Button Button_Cancel;
	
	@FXML
	private Button Button_ChooseMusic;
	
	@FXML
	private Button Button_ChooseImage;
	
	@FXML
	private Label Label_MusicPath;
	
	@FXML
	private Label Label_AlbumPath;
	
	@FXML
	private Label Label_MusicName;
	
	@FXML
	private Label Label_Singer;
	
	@FXML
	private Label Label_Album;
	
	@FXML
	private TextField Field_MusicPath;
	
	@FXML
	private TextField Field_AlbumPath;
	
	@FXML
	private Label MusicName;
	
	@FXML
	private Label Singer;
	
	@FXML
	private Label Album;
	
	public void setMusic(Music music) {
        this.music = music;
    }
	
	public void initialize() {
		this.Album.setText("");
		this.Field_AlbumPath.setText("");
		this.MusicName.setText("");
		this.Field_MusicPath.setText("");
		this.Singer.setText("");
	}
	
	public boolean isAdd() {
		return this.isAdd;
	}

	@FXML
	public void addButtonHandler() {
		if(this.Field_MusicPath.getText() == "") {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("MusicPath should not be empty~");
			alert.setHeaderText("NO INPUT");
		}
		else {
			this.isAdd = true;
			dialog.close();
		}
	}
	@FXML
	public void cancelButtonHandler() {
		dialog.close();
	}
	
	@FXML
	public void loadMusicFileButtonHandler() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg", "*.mp3"));
		File file = fc.showOpenDialog(null);
		if(file != null) {
			String path = file.getAbsolutePath();
			path = path.replace("\\", "/");
			Music newMusic = Storage.parser(path);
			this.music.setPath(path);
			this.music.setAlbumName(newMusic.getAlbumName());
			this.music.setPicturePath(this.Field_AlbumPath.getText());
			this.music.setAlbumPicture(newMusic.getAlbumPicture());
			this.music.setMusicName(newMusic.getMusicName());
			this.music.setSinger(newMusic.getSinger());
			this.Field_MusicPath.setText(path);
			this.MusicName.setText(newMusic.getMusicName());
			this.Album.setText(newMusic.getAlbumName());
			this.Singer.setText(newMusic.getSinger());
		}
		else
			return;
	}

	@FXML
	public void loadImageFileButtonHandler() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("*.png", "*.jpg", "*.gif"));
		File file = fc.showOpenDialog(null);
		String path = file.getAbsolutePath();
		path = path.replace("\\", "/");
		this.Field_AlbumPath.setText(path);
		this.music.setPicturePath(path);
	}
	
	
	
	public void setStage(Stage dialog) {
		this.dialog = dialog;
	}
}
