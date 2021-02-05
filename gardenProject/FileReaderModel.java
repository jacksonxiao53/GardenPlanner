
/**
 * The FileReaderModel contains the logic for parsing plant information from a text file. 
 * @author Team 10-7
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
public class FileReaderModel {
	
	private ArrayList<Plant> plantList = new ArrayList<>();
	
	/**
	 * Constructor that takes in a filename as parameter. The constructor will read in the information one line at a time and
	 * creates a Plant object with the parsed information 
	 * @param filename - name of the file 
	 */
	public FileReaderModel(String filename) {
		 try {            
	            BufferedReader reader = new BufferedReader(new FileReader(filename));            
	            String line;
	            reader.readLine();
	            while ((line = reader.readLine()) != null) {    
	                String[] parts = line.split(",");
	                plantList.add(new Plant(parts[0],parts[1],parts[2],parts[3],parts[4],parts[7],parts[5],parts[6],parts[8],parts[9],parts[10],Double.parseDouble(parts[11])));	            }            
	            reader.close();        
	        } 
	        catch (Exception e) {            
	            System.err.format("Exception occurred trying to read '%s'.", filename);            
	            e.printStackTrace();        
	        }    
	}
	 public ArrayList<Plant> getPlantList(){
		 return plantList;
	 }
}
