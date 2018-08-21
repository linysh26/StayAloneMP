package address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Music{
	
	//default static
	public static String DEFAULT_ALBUMPATH_ = "images/xj.jpg";
	
	//variable
	private StringProperty Path;
	private StringProperty Singer;
	private byte[] albumPicture;
	private String picturePath;
	private StringProperty musicName;
	private StringProperty albumName;
	// using as mid in database
	private StringProperty ID;
	
	public Music(String path, String picturePath, byte[] albumPicture, String musicName, String singer, String musicListName, String ID) {
		this.Path = new SimpleStringProperty(path);
		this.albumPicture = albumPicture;
		this.picturePath = picturePath;
		this.Singer = new SimpleStringProperty(singer);
		this.musicName = new SimpleStringProperty(musicName);
		this.albumName = new SimpleStringProperty(musicListName);
		this.ID = new SimpleStringProperty(ID);
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
	
	public String getPicturePath() {
		return this.picturePath;
	}
	
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	
	public byte[] getAlbumPicture() {
		return this.albumPicture;
	}
	
	public void setAlbumPicture(byte[] albumPicture) {
		this.albumPicture = albumPicture;
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
	
	
	public StringProperty currentMusicStringID() {
		return ID;
	}
}