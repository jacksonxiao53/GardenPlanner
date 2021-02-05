import java.util.*;
/**
 * SaveData class contains data from a garden that a user wants to save. This class implements Serializable. 
 * The projectName, width, height, images, and image locations are serialize. 
 * @author Team 10-7
 *
 */
public class SaveData implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String projectName; //Name of the garden project
	private String saveDate;
	private int width; //Width of the garden
	private int height; //Height of the garden
	private ArrayList<String> imgPaths; //Path to the location of the image
	private ArrayList<String> xyCoord; // X and Y coordinates for an image 
	private ArrayList<Double> radii;
	private ArrayList<String> labelString;
	private ArrayList<String> labelXY;
	
	/**
	 * Constructor that takes no parameters. Set imgPaths and xyCoord to ArrayLists
	 */
	public SaveData() {
		imgPaths = new ArrayList<>();
		xyCoord = new ArrayList<>();
		radii = new ArrayList<>();
		labelString = new ArrayList<>();
		labelXY = new ArrayList<>();
	}
	/**
	 * Constructor that takes in projectName, width, height, imgPaths, xyList, radii, ls, and xyL
	 * @param pName - name of garden project
	 * @param w - width of garden
	 * @param h - height of garden
	 * @param imgPaths - ArrayList of path of images
	 * @param xyList - ArrayList of X and Y coordinates of image 
	 * @param radii - ArrayList of sizes of each circle image
	 * @param ls - ArrayList of label names 
	 * @param xyL - ArrayList of X and Y coordinates of labels
	 */
	public SaveData(String pName, String date, int w, int h, ArrayList<String> imgPaths, ArrayList<String> xyList,ArrayList<Double> radii,ArrayList<String> ls,ArrayList<String> xyL) {
		this.projectName = pName;
		this.saveDate = date;
		this.width = w;
		this.height =h;
		this.xyCoord = xyList;
		this.imgPaths = imgPaths;
		this.radii = radii;
		this.labelString = ls;
		this.labelXY = xyL;
		
	}
	
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getSaveDate() {
		return saveDate;
	}
	
	public void setSaveTime(String save) {
		this.saveDate = save;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public ArrayList<String> getImgPaths(){
		return imgPaths;
	}
	public ArrayList<String> getXY(){
		return xyCoord;
	}
	
	public void setImgPaths(ArrayList<String> l) {
		this.imgPaths = l;
	}
	public ArrayList<Double> getRadii() {
		return radii;
	}
	public ArrayList<String> getLabelString(){
		return labelString;
	}
	public ArrayList<String> getLabelXY(){
		return labelXY;
	}
	
}
