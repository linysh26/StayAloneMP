package address.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

import address.model.Music;


public class Storage {
	public static void write(File file,LinkedList<Music> musicList) {
		FileOutputStream out = null;
		OutputStreamWriter writer = null;
		BufferedWriter bwriter = null; 
		try {
			out = new FileOutputStream(file);
			writer = new OutputStreamWriter(out);
			bwriter = new BufferedWriter(writer); 
			
			for(Music m : musicList){
	            bwriter.append(m.toString()).append("\r");
	        }
		}catch(Exception e) {
			
		}finally {
			if(bwriter!=null){
                try {
                    bwriter.close();
                    bwriter=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(writer!=null){
                try {
                    writer.close();
                    writer=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }

		}
	}
	public static LinkedList<Music> read(String path) {
		File file = new File(path);//记得添加路径
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new LinkedList<Music>();
		}
		BufferedReader breader = null;
		try {
			breader = new BufferedReader(new FileReader(file));
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line = "";
		int count;
		LinkedList<Music> LocalMusicList = new LinkedList<Music>();
		
		try {
			while((line = breader.readLine()) != null) {
				String musicPath = "";
				String musicTitle = "";
				String singer = "";
				String album = "";
				String albumPath = "";
				for(count = 0;line.charAt(count)!=',';count++) {
					musicPath = musicPath + line.charAt(count);
				}
				count++;
				for(;line.charAt(count)!=',';count++) {
					albumPath = albumPath + line.charAt(count);
				}
				count++;
				for(;line.charAt(count)!=',';count++) {
					musicTitle = musicTitle + line.charAt(count);
				}
				count++;
				for(;line.charAt(count)!=',';count++) {
					singer = singer + line.charAt(count);
				}
				count++;
				for(;count<line.length();count++) {
					album = album + line.charAt(count);
				}
				
				Music music = new Music(musicPath,albumPath,musicTitle,singer,album);
				LocalMusicList.add(music);
			}
		}catch(IndexOutOfBoundsException ioobe) {
			ioobe.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return LocalMusicList;
	}
}
