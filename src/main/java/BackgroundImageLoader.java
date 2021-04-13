import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.imageio.ImageIO;

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
	}
	
	public File getFile(String fileName) {
		return Paths.get("src/main/resources/" + fileName).toFile().getAbsoluteFile();
	}
	
	public void addImage(String line) {
		if(line.contains("﻿"))
			return;
		String words[] = line.split(",");
		String loc = words[17];
		try {
			BufferedImage image = ImageIO.read(getFile(loc));
			//COPIED THIS ONLINE
			//https://blog.idrsolutions.com/2012/11/convert-bufferedimage-to-javafx-image/
			WritableImage wr = null;
	        if (image != null) {
	            wr = new WritableImage(image.getWidth(), image.getHeight());
	            PixelWriter pw = wr.getPixelWriter();
	            for (int x = 0; x < image.getWidth(); x++) {
	                for (int y = 0; y < image.getHeight(); y++) {
	                    pw.setArgb(x, y, image.getRGB(x, y));
	                }
	            }
	        }
			plant_images.put(words[0], wr);
		} catch (IOException e) {
			System.out.println("Failed to add image");
			e.printStackTrace();
		}
	}
}
