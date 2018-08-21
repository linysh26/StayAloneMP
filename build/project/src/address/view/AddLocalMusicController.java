package address.view;

import java.io.File;

import address.model.Music;
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
	private Music newMusic;
	
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
	private TextField Field_MusicName;
	
	@FXML
	private TextField Field_Singer;
	
	@FXML
	private TextField Field_Album;
	
	public void setMusic(Music music) {
        this.newMusic = music;
    }
	
	public void initialize() {
		this.Field_Album.setText("");
		this.Field_AlbumPath.setText("");
		this.Field_MusicName.setText("");
		this.Field_MusicPath.setText("");
		this.Field_Singer.setText("");
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
			newMusic.setPath(this.Field_MusicPath.getText());
			newMusic.setSinger(this.Field_Singer.getText());
			newMusic.setAlbumPath(this.Field_AlbumPath.getText());
			newMusic.setAlbumName(this.Field_Album.getText());
			newMusic.setMusicName(Field_MusicName.getText());
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
		String path = file.getAbsolutePath();
		path = path.replace("\\", "/");
		this.Field_MusicPath.setText(path);
		this.Field_MusicName.setText(file.getName().substring(0, file.getName().lastIndexOf(".")));
	}
	
	@FXML
	public void loadImageFileButtonHandler() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("*.png", "*.jpg", "*.gif"));
		File file = fc.showOpenDialog(null);
		String path = file.getAbsolutePath();
		path = path.replace("\\", "/");
		this.Field_AlbumPath.setText(path);
	}
	
	
	
	public void setStage(Stage dialog) {
		this.dialog = dialog;
	}
}
