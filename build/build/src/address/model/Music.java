package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Music{
	
	//default static
	public static String DEFAULT_ALBUMPATH_ = "file:Resources/Images/default_music_album.png";
	
	//variable
	private StringProperty Path;
	private StringProperty Singer;
	private StringProperty albumPath;
	private StringProperty musicName;
	private StringProperty albumName;
	private StringProperty ID;
	
	public Music(String path, String albumPath, String musicName, String singer, String musicListName) {
		this.Path = new SimpleStringProperty(path);
		this.albumPath = new SimpleStringProperty(albumPath);
		this.Singer = new SimpleStringProperty(singer);
		this.musicName = new SimpleStringProperty(musicName);
		this.albumName = new SimpleStringProperty(musicListName);
		this.ID = new SimpleStringProperty(this.Path.get() + "," + this.albumPath.get() + "," + this.musicName.get() + "," + this.Singer.get() + "," + this.albumName.get());
	}
	
	public void setSinger(String singer) {
		this.Singer.set(singer);
	}
	
	public StringProperty getSingerProperty() {
		return this.Singer;
	}
	
	public String getSinger() {
		return this.Singer.get();
	}
	
	public void setMusicName(String musicName_) {
		this.musicName.set(musicName_);
	}
	
	public String getMusicName() {
		return this.musicName.get();
	}
	
	public StringProperty getMusicNameProperty() {
		return this.musicName;
	}
	
	public String getAlbumName() {
		return this.albumName.get();
	}
	
	public void setAlbumName(String musicListName_) {
		this.albumName.set(musicListName_);
	}
	
	public StringProperty getAlbumNameProperty() {
		return this.albumName;
	}
	
	public String getAlbumPath() {
		return this.albumPath.get();
	}
	
	public void setAlbumPath(String albumPath_) {
		this.albumPath.set(albumPath_);
	}
	
	public String getPath() {
		return this.Path.get();
	}
	
	public void setPath(String Path_) {
		this.Path.set(Path_);
	}
	
	public String getTag() {
		return musicName.get() + " - " + albumName.get();
	}
	
	@Override
	public String toString() {
		return this.Path.get() + "," + this.albumPath.get() + "," + this.musicName.get() + "," + this.Singer.get() + "," + this.albumName.get();
	}
	
	public StringProperty currentMusicStringID() {
		return ID;
	}
}