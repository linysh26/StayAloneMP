package address.model;

import java.util.LinkedList;

import javafx.scene.Parent;

/**
 * Base class
 * page
 * private variable type's value represent the type of the page and its controller.
 * @author ÐÇ¿Õ
 *
 */
public class Page{
	
	public static final int HOME = 0, SETTING  = 1, SEARCH = 2, MUSICLIST = 3;
	public static Page P = new Page();
	private int type;
	private Parent page;
	
	public int getType() {
		return type;
	}
	protected void setType(int type) {
		this.type = type;
	}
	public Parent getPage() {
		return page;
	}
	protected void setPage(Parent page) {
		this.page = page;
	}
	/**
	 * Sub class
	 * usr's home page
	 * @author ÐÇ¿Õ
	 *
	 */
	public class HomePage extends Page{
		
		public HomePage() {
			super();
			setType(Page.HOME);
		}
	}
	
	public class MusicListPage extends Page{
		
		public MusicListPage() {
			super();
			setType(Page.MUSICLIST);
		}
	}
	/**
	 * Sub class
	 * result page of search
	 * @author ÐÇ¿Õ
	 *
	 */
	public class SearchPage extends Page{
		
		private String key;
		private LinkedList<Music> musicList;
		
		public SearchPage(LinkedList<Music> musicList, String key) {
			super();
			setType(Page.SEARCH);
			this.musicList = musicList;
			this.key = key;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String name) {
			this.key = name;
		}
		public LinkedList<Music> getMusicList(){
			return this.musicList;
		}
		
	}
	/**
	 * Sub class
	 * page for setting
	 * @author ÐÇ¿Õ
	 *
	 */
	public class SettingPage extends Page{
		public SettingPage() {
			super();
			setType(Page.SETTING);
		}
	}
	
}
