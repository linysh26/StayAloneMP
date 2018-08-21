package address.model;

import java.time.LocalDate;
import java.util.LinkedList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

/**
 * the class specify the music list
 * @author ÐÇ¿Õ
 *
 */
public class MyMusicList {
	
	// using as mlid in database
	private IntegerProperty id;
	// observable value of the music list's name, which can be use as primary key
	private StringProperty musicListName;
	// observable list of musics
	private ObservableList<Music> musicList;
	// observable value of size
	private IntegerProperty size;
	// observable value of music ID, according to which we listen to and switch to next song
	private StringProperty currentMusicID;
	// album picture of the first song if not being specified
	private ImageView musicListImage;
	// the date of the music list being created
	private LocalDate date;
	
	
	public MyMusicList() {
		this.size = new SimpleIntegerProperty(0);
		this.currentMusicID = new SimpleStringProperty("");
		this.musicList = FXCollections.observableArrayList();
	}
	
	public MyMusicList(ObservableList<Music> musicList) {
		this.musicList = musicList;
		this.size.set(musicList.size());
	}
	
//************************************ Property Operation ***********************************************
	public void setMusicListName(String name) {
		this.musicListName.setValue(name);
	}
	
	public StringProperty getNameProperty() {
		return this.musicListName;
	}
	
	public String getName() {
		return this.musicListName.get();
	}
	
	public  void setMusicList(ObservableList<Music> musicList) {
		this.musicList = musicList;
		this.size.set(musicList.size());
	}
	
	public ObservableList<Music> getMusicList(){
		return this.musicList;
	}
	
	public IntegerProperty getSizeProperty() {
		return this.size;
	}
	
	public int getSizeValue() {
		return this.musicList.size();
	}
	
	public void setSizeValue(int size) {
		this.size.set(size);
	}
//****************************************** Functional Operation ******************************************
	public void addNewPlayingMusic(Music music) {
		if(musicList.contains(music)) {
			musicList.remove(music);
			musicList.add(0, music);
		}
		else {
			this.musicList.add(0, music);
			if(this.musicList.size() > 99)
				this.musicList.remove(musicList.size() - 1);
			this.size.set(this.musicList.size());
		}
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.get(0).toString());
	}
	
	public void addNewMusic(Music music) {
		if(musicList.contains(music)) {
			return;
		}
		else {
			if(this.musicList.size() != 99) {
				this.musicList.add(music);
				this.size.set(this.musicList.size());
			}
		}
	}
	
	public void addNewMusicList(MyMusicList musicList) {
		ObservableList<Music> temp = musicList.getMusicList();
		int temp_size = temp.size();
		for(int i = 0;i < temp_size;i++) {
			this.musicList.add(1, temp.remove(temp.size() - 1));
		}
		while(this.size.get() > 99) {
			this.musicList.remove(musicList.getSizeValue() - 1);
		}
	}
	public void removeMusic(int index) {
		if(musicList.size() <= 0) {
			return;
		}
		else {
			this.musicList.remove(index);
			if(this.musicList.size() != 0)
				this.currentMusicID.set(this.musicList.get(0).toString());
			this.size.set(this.musicList.size());
		}
	}
	public void addNewList(MyMusicList musicList) {
		ObservableList<Music> temp = musicList.getMusicList();
		int temp_size = temp.size();
		for(int i = 0;i < temp_size;i++) {
			this.musicList.add(0, temp.remove(temp.size() - 1));
		}
		while(this.size.get() > 99) {
			this.musicList.remove(musicList.getSizeValue() - 1);
		}
		this.currentMusicID.set(this.musicList.get(0).toString());
	}
	public Music getCurrentPlayingMusic() {
		if(this.size.get() == 0)
			return null;
		else
			return this.musicList.get(0);
	}
	
	public void forward() {
		this.musicList.add(this.musicList.remove(0));
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.get(0).toString());
	}
	
	public void backward() {
		this.musicList.add(0, this.musicList.remove(musicList.size() - 1));
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.get(0).toString());
	}
	
	public StringProperty currentPlayingMusicID() {
		return this.currentMusicID;
	}
}
