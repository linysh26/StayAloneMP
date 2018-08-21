package address.model;

import java.util.LinkedList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyMusicList {
	
	private LinkedList<Music> musicList;
	private IntegerProperty size;
	private StringProperty currentMusicID;
	
	public MyMusicList() {
		this.size = new SimpleIntegerProperty(0);
		this.currentMusicID = new SimpleStringProperty("");
		this.musicList = new LinkedList<>();
	}
	
	public MyMusicList(LinkedList<Music> musicList) {
		this.musicList = musicList;
		this.size.set(musicList.size());
	}
	
	public  void setMusicList(LinkedList<Music> musicList) {
		this.musicList = musicList;
		this.size.set(musicList.size());
	}
	
	public LinkedList<Music> getMusicList(){
		return this.musicList;
	}
	
	public IntegerProperty getSizeProperty() {
		return this.size;
	}
	
	public int getSizeValue() {
		return this.size.get();
	}
	
	public void setSizeValue(int size) {
		this.size.set(size);
	}
	
	public void addNewMusic(Music music) {
		if(musicList.contains(music)) {
			musicList.remove(music);
			musicList.addFirst(music);
		}
		else {
			this.musicList.addFirst(music);
			if(this.musicList.size() > 99)
				this.musicList.removeLast();
			this.size.set(this.musicList.size());
		}
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.getFirst().toString());
	}
	
	public void addNewList(MyMusicList musicList) {
		LinkedList<Music> temp = musicList.getMusicList();
		int temp_size = temp.size();
		for(int i = 0;i < temp_size;i++) {
			this.musicList.addFirst(temp.removeLast());
		}
		while(this.size.get() > 99) {
			this.musicList.removeLast();
		}
		this.currentMusicID.set(this.musicList.getFirst().toString());
	}
	public Music getCurrentPlayingMusic() {
		if(this.size.get() == 0)
			return null;
		else
			return this.musicList.getFirst();
	}
	
	public void forward() {
		this.musicList.addLast(this.musicList.removeFirst());
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.getFirst().toString());
	}
	
	public void backward() {
		this.musicList.addFirst(this.musicList.removeLast());
		this.currentMusicID.set("");
		this.currentMusicID.set(this.musicList.getFirst().toString());
	}
	
	public StringProperty currentPlayingMusicID() {
		return this.currentMusicID;
	}
	
}
