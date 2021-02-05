import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 * The GardenModel class contains the logic in calculating the x and y location for drag and drop
 * @author Team 10-7
 *
 */
public class GardenModel {

	private double xoffset = 455;
	private double yoffset = 400;
	
	
	public double calculateX(double x) {
		return x-xoffset;
	}
	public double calculateY(double y) {
		return y-yoffset;
	}
	
	/**
	 * Serialize the SaveData object and store the byte into fileName
	 * @param data - SaveData object with attributes of the garden that wants to be saved 
	 * @param fileName - file where the byte streams will be stored
	 * @throws Exception
	 */
	public void save(SaveData data, String fileName) throws Exception{
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
			oos.writeObject(data);
		}
		
	}
	
	/**
	 * Deserialize the SaveData object and returns it 
	 * @param fileName - name of the file that will be deserialize 
	 * @return SaveData - object with states of a saved garden 
	 * @throws Exception
	 */
	public SaveData load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
			return (SaveData) ois.readObject();
		}

	}
}
