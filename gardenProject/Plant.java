
/**
 * The Plant class represents a plant object. Each plant object has a name, type, bloom time, flower 
 * color, fall foliage color, summer foliage color, light amount, water use, and a file path to an image 
 * of the plant 
 * @author Team 10-7
 */
import javafx.beans.property.SimpleStringProperty;

public class Plant
    {

        private final SimpleStringProperty plantName;
        private final SimpleStringProperty plantType; //new
        private final SimpleStringProperty bloomTime;
        private final SimpleStringProperty flowerColor; //new
        private final SimpleStringProperty fallFoliageColor; //new
        private final SimpleStringProperty summerFoliageColor; //new
        private final SimpleStringProperty light;
        private final SimpleStringProperty waterUse;
        private final String imagePath;
        private final String imagePath_bloom;
        private final String imagePath_nonbloom;
        private final double plantWidth;
        private final String winterImagePath;
        
        /**
         * Plant constructor that initializes all fields to the given parameters
         * @param pName - name of plant
         * @param plantType - type of plant 
         * @param bloomTime - bloom time of plant
         * @param flowerColor - flower color of plant
         * @param fallFoliageColor - fall foliage color of plant
         * @param summerFoliageColor - summer foliage color of plant
         * @param light - light level for plant
         * @param waterUse - water use of plant
         * @param imagePath - file path to an image of the plant 
         * @param imagePath_bloom - file path to an image of the bloom plant
         * @param imagePath_nonbloom - file path to an image of the Non-bloom plant
         */
        Plant(String pName, String plantType,String bloomTime, String flowerColor, String fallFoliageColor,String summerFoliageColor, String light, String waterUse,String imagePath,String imagePath_bloom,String imagePath_nonbloom,double plantWidth)
        {
            this.plantName = new SimpleStringProperty(pName);
            this.plantType = new SimpleStringProperty(plantType);
            this.bloomTime = new SimpleStringProperty(bloomTime);
            this.flowerColor = new SimpleStringProperty(flowerColor);
            this.fallFoliageColor = new SimpleStringProperty(fallFoliageColor);
            this.summerFoliageColor = new SimpleStringProperty(summerFoliageColor);
            this.light = new SimpleStringProperty(light);
            this.waterUse = new SimpleStringProperty(waterUse);
            this.imagePath = imagePath;
            this.imagePath_bloom = imagePath_bloom;
            this.imagePath_nonbloom = imagePath_nonbloom;
            this.plantWidth = plantWidth;
            this.winterImagePath = "images/PlantWinterImage.jpeg";

        }

        public String getPlantName()
        {
            return plantName.get();
        }

        public void setPlantName(String fName)
        {
            plantName.set(fName);
        }
        
        public String getPlantType() {
        	return plantType.get();
        }
        public void setPlantType(String type) {
        	plantType.set(type);
        }
        public String getBloom()
        {
        	return bloomTime.get();
        }
        public void setBloom(String fName)
        {
        	bloomTime.set(fName);
        }
        public String getFlowerColor() {
        	return flowerColor.get();
        }
        public void setFlowerColor(String color) {
        	flowerColor.set(color);
        }
        public String getFallFoliageColor() {
        	return fallFoliageColor.get();
        }
        public void setFallFoliageColor(String color) {
        	fallFoliageColor.set(color);
        }
        public String getSummerFoliageColor() {
        	return summerFoliageColor.get();
        }
        public void getSummerFoliageColor(String color) {
        	summerFoliageColor.set(color);
        }

        public String getLight()
        {
            return light.get();
        }

        public void setLight(String fName)
        {
            light.set(fName);
        }

        public String getWater()
        {
            return waterUse.get();
        }

        public void setWater(String fName)
        {
            waterUse.set(fName);
        }
        public String getImagePath() {
        	return imagePath;
        } 
        
        public String getImagePath_bloom() {
        	return imagePath_bloom;
        } 
        
        public String getImagePath_nonbloom() {
        	return imagePath_nonbloom;
        } 
        public double getPlantWidth() {
        	return plantWidth;
        }
        public String getWinterImage() {
        	return winterImagePath;
        }
        
    }
