
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.sun.tools.javac.Main;

import javafx.scene.image.Image;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;


public class BackgroundImageLoader extends Thread {
	private Thread thread;
	private String threadName;
	private HashMap<String, Image> plant_images;
	
	public BackgroundImageLoader(String name, HashMap<String, Image> pi) {
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
		test();
		/*
		System.out.println("PLEASE ONLY SHOW ONCE");
		//load data file and create a list of lines
		File plantData = Paths.get("src/main/resources/result.csv").toFile().getAbsoluteFile();
		BufferedReader br;
		int numLines = 0;
		
		try {
			br = new BufferedReader(new FileReader(plantData));
		    String line;
		    while ((line = br.readLine()) != null) {
		        addImage(line);
		        numLines++;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public InputStream getFile(String fileName) {
		return BackgroundImageLoader.class.getResourceAsStream("images/"+fileName);
	}
	
	public void addImage(String line) {
		if(line.contains("﻿"))
			return;
		String words[] = line.split(",");
		String loc = words[17];
		try {
			Image image = new Image(getFile(loc), 150, 150, true, false);
			plant_images.put(words[0], image);
			
		} catch (Exception e) {
			System.out.println("Failed to add image");
			e.printStackTrace();
		}
	}
	
	public void test() {
		File dir = Paths.get("src/main/resources/images").toFile().getAbsoluteFile();
		File[] directoryListing = dir.listFiles();
		int x = 0;
		if (directoryListing != null) {
			for (File child : directoryListing) {
				Image image = new Image(getFile(child.getName()));
				String name = child.getName().replace(".jpg", "");
				plant_images.put(name, image);
			}
		}
	}
}
