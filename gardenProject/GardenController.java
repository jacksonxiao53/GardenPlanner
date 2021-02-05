  
import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * The controller class for our garden software. 
 * @author Team 10-7
 *
 */
public class GardenController  {
	
	private GardenModel model = new GardenModel();
	private FileReaderModel reader = new FileReaderModel("Plants.txt");
	private ArrayList<SaveData> fileList = new ArrayList<>();
	private double x = 0.0;
	private double y = 0.0;
	

	public void setX(double x) {
		double updatedX = model.calculateX(x);
		this.x = updatedX;
		
	}
	
	public void setY(double y) {
		double updatedY = model.calculateY(y);
		this.y= updatedY;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	
	public ArrayList<Plant> getPlantList(){
		return reader.getPlantList();
	}
	/**
	 * Creates a SaveData object. Calls the save method in the model class with the SaveData object and the name of the file
	 * that it will be saved as. 
	 * @param projectName - name of garden project
	 * @param height - height of garden
	 * @param width - width of garden
	 * @param imgList - ArrayList of paths of plant images
	 * @param xy - ArrayList of x and y locations for plant images
	 * @param saveName - file name that the file will be saved as 
	 */
	public void save(String projectName, String date, int width, int height , ArrayList<String> imgList, ArrayList<String> xy, ArrayList<Double> radii,ArrayList<String> labelString, ArrayList<String> labelXY) {
		SaveData data = new SaveData(projectName, date, width, height, imgList, xy, radii,labelString,labelXY);
		fileList.add(data);
		try {
			model.save(data,projectName+".save");
		}
		catch(Exception e) {
			System.out.println("Couldn't save: " + e.getMessage());
		}
	}
	/**
	 * Calls the load method from the model class that returns a SaveData object. The SaveData object is then returned
	 * @param fileName - name of the file that will be loaded in 
	 * @return data - SaveData object
	 */
	public SaveData getData(String fileName) {
		try {
		SaveData data = model.load(fileName);
		return data;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	/**
	 * Method that returns a list of saved files
	 * @return - an ArrayList of SaveData objects
	 */
	public ArrayList<SaveData> fileList() {
		fileList.clear();
		File[] files = new File(System.getProperty("user.dir")).listFiles();
    	for (File file : files) {
    		String name = file.getName().trim();
        	if (name.endsWith(".save")) {
        		if (!name.equals("presetGarden1.save") && !name.equals("presetGarden2.save") && !name.equals("presetGarden3.save")) {
        			fileList.add(getData(name));
        		}
        	}
    	}
    	return fileList;
	}
	
}
