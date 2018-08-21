package address.model;

import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PageQueue {
	private LinkedList<Page> pageList;
	private IntegerProperty index;
	private IntegerProperty size;
	
	
	public PageQueue() {
		this.pageList = new LinkedList<>();
		this.index = new SimpleIntegerProperty(-1);
		this.size = new SimpleIntegerProperty(0);
	}
	
	public int getIndex() {
		return index.get();
	}
	public IntegerProperty getIndexProperty() {
		return this.index;
	}
	
	public int getSize() {
		return size.get();
	}
	public IntegerProperty getSizeproperty() {
		return this.size;
	}
	
	public void bind(IntegerProperty size, IntegerProperty index) {
		size.bind(this.size);
		index.bind(this.index);
	}
	
	public Page getCurrentPage() {
		return this.pageList.get(this.index.get());
	}
	
	public void addPage(Page newPage) {
		this.pageList.add(index.get() + 1, newPage);
		this.index.set(this.index.get() + 1);
		while(index.get() != this.pageList.size() - 1) {
			this.pageList.removeLast();
		}
		this.size.set(this.pageList.size());
		System.out.println("Page type: " + newPage.getType());
		System.out.println("Current index: " + this.index.get() + " Current size: " + this.size.get());
	}
	
	public boolean backwardAvailable() {
		return index.get() > 0;
	}
	
	public boolean forewardAvailable() {
		return index.get() < size.get() - 1;
	}
	
	public void backward() {
		this.index.set(this.index.get() - 1);
	}
	
	public void foreward() {
		this.index.set(this.index.get() + 1);
	}
}
