package swrd;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;

public class Resources {
	private static Map<ImageEnum, BufferedImage> ImageMap = new EnumMap<>(ImageEnum.class);
	private static Map<FontEnum, Font> FontMap = new EnumMap<>(FontEnum.class);
	private static Map<SoundEnum, Clip> SoundMap = new EnumMap<>(SoundEnum.class);
	private static boolean muted;
	public static final int SPRITESIZE = 16;
	
	public static enum ImageEnum {
		FLOOR, CHARACTER, SWORD, GOLDENSWORD, WALL,
		
		COMMON_SWORD, UNCOMMON_SWORD, RARE_SWORD, EXOTIC_SWORD, UNTOUCHED_SWORD,
		COMMON_BOW, UNCOMMON_BOW, RARE_BOW, EXOTIC_BOW, UNTOUCHED_BOW, 
		COMMON_ARMOR, UNCOMMON_ARMOR, RARE_ARMOR, EXOTIC_ARMOR, UNTOUCHED_ARMOR, 
		COMMON_ARROW, UNCOMMON_ARROW, RARE_ARROW, EXOTIC_ARROW, UNTOUCHED_ARROW,  ;
	}
	
	public static enum FontEnum {
		DEFAULTFONT;
	}
	
	public static enum SoundEnum {
		
	}	


	
	public static void init() {
		try {
			BufferedImage sheet = ImageIO.read(new File("res/Images/Atlas.png"));
			BufferedImage weaponSheet = ImageIO.read(new File("res/Images/SWRD Weapons.png"));
			
			ImageMap.put(ImageEnum.FLOOR, getSprite(sheet, 2, 0)); //row 15, col 11
			ImageMap.put(ImageEnum.CHARACTER, getSprite(sheet, 15, 11)); //row 2, col 7
			ImageMap.put(ImageEnum.SWORD, getSprite(sheet, 2, 7));
			ImageMap.put(ImageEnum.GOLDENSWORD, getSprite(sheet, 2, 10));
			ImageMap.put(ImageEnum.WALL, getSprite(sheet, 1, 0));
			
			ImageMap.put(ImageEnum.COMMON_SWORD, getSprite(weaponSheet, 0, 0));
			ImageMap.put(ImageEnum.UNCOMMON_SWORD, getSprite(weaponSheet, 0, 1));
			ImageMap.put(ImageEnum.RARE_SWORD, getSprite(weaponSheet, 0, 2));
			ImageMap.put(ImageEnum.EXOTIC_SWORD, getSprite(weaponSheet, 0, 3));
			ImageMap.put(ImageEnum.UNTOUCHED_SWORD, getSprite(weaponSheet, 0, 4));
			
			ImageMap.put(ImageEnum.COMMON_BOW, getSprite(weaponSheet, 1, 0));
			ImageMap.put(ImageEnum.UNCOMMON_BOW, getSprite(weaponSheet, 1, 1));
			ImageMap.put(ImageEnum.RARE_BOW, getSprite(weaponSheet, 1, 2));
			ImageMap.put(ImageEnum.EXOTIC_BOW, getSprite(weaponSheet, 1, 3));
			ImageMap.put(ImageEnum.UNTOUCHED_BOW, getSprite(weaponSheet, 1, 4));
			
			ImageMap.put(ImageEnum.COMMON_ARMOR, getSprite(weaponSheet, 2, 0));
			ImageMap.put(ImageEnum.UNCOMMON_ARMOR, getSprite(weaponSheet, 2, 1));
			ImageMap.put(ImageEnum.RARE_ARMOR, getSprite(weaponSheet, 2, 2));
			ImageMap.put(ImageEnum.EXOTIC_ARMOR, getSprite(weaponSheet, 2, 3));
			ImageMap.put(ImageEnum.UNTOUCHED_ARMOR, getSprite(weaponSheet, 2, 4));
			
			ImageMap.put(ImageEnum.COMMON_ARROW, getSprite(weaponSheet, 3, 0));
			ImageMap.put(ImageEnum.UNCOMMON_ARROW, getSprite(weaponSheet, 3, 1));
			ImageMap.put(ImageEnum.RARE_ARROW, getSprite(weaponSheet, 3, 2));
			ImageMap.put(ImageEnum.EXOTIC_ARROW, getSprite(weaponSheet, 3, 3));
			ImageMap.put(ImageEnum.UNTOUCHED_ARROW, getSprite(weaponSheet, 3, 4));
		}	catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FontMap.put(FontEnum.DEFAULTFONT, Font.createFont(Font.TRUETYPE_FONT, new File("res/Fonts/Goldman-Regular.ttf")));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	private static BufferedImage getSprite(BufferedImage sheet, int row, int col) {
		return sheet.getSubimage(col * SPRITESIZE, row * SPRITESIZE, SPRITESIZE, SPRITESIZE);
	}
	
	public static BufferedImage getImage(ImageEnum choice) {
		if (ImageMap.containsKey(choice)) {
			return (ImageMap.get(choice));
		} else {
			System.out.println("Invalid choice");
			return new BufferedImage(10, 10, 10);
		}
	}
	
	public static Font getFont(FontEnum choice) {
		if (FontMap.containsKey(choice)) {
			return FontMap.get(choice);
		} else {
			System.out.println("Invalid choice");
			return new Font("Times New Roman", Font.PLAIN, 12);
		}
	}
	
	public static void playSound(SoundEnum choice) {
		if (SoundMap.containsKey(choice)) {
			Clip current = SoundMap.get(choice);
			if (muted) { return; }
			current.setMicrosecondPosition(0);
			current.start();
		} else {
			System.out.println("Invalid choice");
		}
	}
	
	public static void stopSound(SoundEnum choice) {
		if (SoundMap.containsKey(choice)) {
			Clip current = SoundMap.get(choice);
			current.stop();
		} else {
			System.out.println("Invalid choice");
		}
	}
	
	public static void mute() {
		if (!muted) {
			muted = true;
			SoundMap.values().forEach(clip -> {
				if (clip == null) { return; }
				clip.stop(); 
				});
		} else {
			muted = false;
			System.out.println("restarting");
		}
	}
	
	public static boolean isMuted() { 
		return muted;
	}
}
