package GameAssets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import utilities.Resources;

public class Land {
	
	public static final int LAND_POSY = 103;
	
	private List<ImageLand> listLand;
	private BufferedImage landImage1;
	private BufferedImage landImage2;
	private BufferedImage landImage3;
	
	private MainCharacter mainCharacter;
	
	public Land(int width, MainCharacter mainCharacter) {
		this.mainCharacter = mainCharacter;
		landImage1 = Resources.getResouceImage("data/land1.png");
		landImage2 = Resources.getResouceImage("data/land2.png");
		landImage3 = Resources.getResouceImage("data/land3.png");
		int numberOfImageLand = width / landImage1.getWidth() + 2;
		listLand = new ArrayList<ImageLand>();
		for(int i = 0; i < numberOfImageLand; i++) {
			ImageLand imageLand = new ImageLand();
			imageLand.posX = i * landImage1.getWidth();
			setImageLand(imageLand);
			listLand.add(imageLand);
		}
	}
	
	public void update(){
		Iterator<ImageLand> itr = listLand.iterator();
		ImageLand firstElement = itr.next();
		firstElement.posX -= mainCharacter.getSpeedX();
		float previousPosX = firstElement.posX;
		while(itr.hasNext()) {
			ImageLand element = itr.next();
			element.posX = previousPosX + landImage1.getWidth();
			previousPosX = element.posX;
		}
		if(firstElement.posX < -landImage1.getWidth()) {
			listLand.remove(firstElement);
			firstElement.posX = previousPosX + landImage1.getWidth();
			setImageLand(firstElement);
			listLand.add(firstElement);
		}
	}
	
	private void setImageLand(ImageLand imgLand) {
		int typeLand = getTypeOfLand();
		if(typeLand == 1) {
			imgLand.image = landImage1;
		} else if(typeLand == 3) {
			imgLand.image = landImage3;
		} else {
			imgLand.image = landImage2;
		}
	}
	
	public void draw(Graphics g) {
		for(ImageLand imgLand : listLand) {
			g.drawImage(imgLand.image, (int) imgLand.posX, LAND_POSY, null);
		}
	}
	
	private int getTypeOfLand() {
		Random rand = new Random();
		int type = rand.nextInt(10);
		if(type == 1) {
			return 1;
		} else if(type == 9) {
			return 3;
		} else {
			return 2;
		}
	}
	
	private class ImageLand {
		float posX;
		BufferedImage image;
	}
	
}
