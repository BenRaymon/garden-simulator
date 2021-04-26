
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import com.sun.tools.javac.Main;

import javafx.scene.image.Image;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


public class BackgroundImageLoader extends Thread {
	private Thread thread;
	private String threadName;
	private ConcurrentHashMap<String, Image> plant_images;
	
	public BackgroundImageLoader(String name, ConcurrentHashMap<String, Image> pi) {
		this.threadName = name;
		this.plant_images = pi;
	}
	
	public void start() {
		System.out.println("Starting background load process");
		if (thread == null) {
			thread = new Thread(this, threadName);
			thread.start();
		}
	}
	
	// The thread runs this function
	public void run() {
		LoadImages();
	}
	
	public InputStream getFile(String fileName) {
		return BackgroundImageLoader.class.getResourceAsStream("images/"+fileName);
	}
	
//	public void addImage(String line) {
//		if(line.contains("﻿"))
//			return;
//		String words[] = line.split(",");
//		String loc = words[17];
//		try {
//			Image image = new Image(getFile(loc), 150, 150, true, false);
//			plant_images.put(words[0], image);
//			
//		} catch (Exception e) {
//			System.out.println("Failed to add image");
//			e.printStackTrace();
//		}
//	}
	
	public void LoadImages() {
		File dir = Paths.get("src/main/resources/images").toFile().getAbsoluteFile();
		File[] directoryListing = dir.listFiles();
		int x = 0;
		if (directoryListing != null) {
			for (File child : directoryListing) {
				Image image = new Image(getFile(child.getName()));
				String name = child.getName().replace(".jpg", "");
				synchronized(plant_images) {
					plant_images.put(name, image);
				}
			}
		}
	}
}
