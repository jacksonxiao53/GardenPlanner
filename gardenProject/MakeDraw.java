import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * This Class is used to make drawings and shapes(currently not avaiable.
 * 
 * @author Team10-7
 *
 */

public class MakeDraw {
	boolean isLining = false;
	
	private double initX = 0;
	private double initY = 0;
	private double finalX = 0;
	private double finalY = 0;
	
	protected ArrayList<Line> lineList = new ArrayList<Line>();
	
	StackPane sp;
	GardenController controller;
	
	/**
	 * Constructor
	 * @param lineList: 	ArrayList<Lines>
	 * @param sp			StackPane for the area that needs to be draw on
	 * @param controller	Controller to convert the drawing area so everything fits
	 */
	public MakeDraw(ArrayList<Line> lineList,
			StackPane sp, GardenController controller) {
		
		this.lineList = lineList;
		this.sp = sp;
		this.controller = controller;
	}
	
	/**
	 * Setter for initX
	 * @param initX: used for inital placement of X
	 */
	protected void setInitX(double initX) {
		this.initX = initX;
	}
	
	/**
	 * setter for initY
	 * @param initY: used for initial placement of Y
	 */
	protected void setInitY(double initY) {
		this.initY = initY;
	}
	
	/**
	 * setter for finalX
	 * @param finalX: user for final placement of X
	 */
	protected void setFinalX(double finalX) {
		this.finalX = finalX;
	}
	
	/**
	 * getter for finalY
	 * @param finalY: used for final placement of Y
	 */
	protected void setFinalY(double finalY) {
		this.finalY = finalY;
	}
	
	/**
	 * Sets the boolean value of @isLining to false or true base on its
	 * 	initial boolean value
	 */
	protected void setIsLining() {
		if(isLining) {
			isLining = false;
			System.out.println("False");
		}else {
			isLining = true;
			System.out.println("true");
		}
	}	
	
	/**
	 * Adds the line into the plot
	 */
	protected void addLine() {
		if(lineList.size() != 0){
			for(Line l: lineList) {
				sp.getChildren().remove(l);
			}
		}
		lineList.add(new Line(initX, initY, finalX, finalY));
		lineList.get(lineList.size()-1).setStroke(Color.BLACK);
		lineList.get(lineList.size()-1).setStrokeWidth(8);
		lineList.get(lineList.size()-1).setTranslateX(controller.getX());
		lineList.get(lineList.size()-1).setTranslateY(controller.getY());	
	}
	
	/**
	 * show the lines on the plot
	 */
	private void showLines() {
		for(Line l: lineList) {
			sp.getChildren().add(l);
		}
	}
	
	/**
	 * This action handle the drawing on the plot. This use line
	 * 	function from the javafx class to draw individual points,
	 *  or the user can set all actions to null.
	 * @param e
	 */
	protected void handleLineAction(ActionEvent e) {
		if(isLining) {
			sp.setOnMousePressed(e1 ->{
				initX = sp.getLayoutX();
				initY = sp.getLayoutY();
				e1.consume();
			});
			sp.setOnMouseDragged(e2 ->{
				controller.setX(e2.getX());
				controller.setY(e2.getY());
				finalX = sp.getLayoutX();
				finalY = sp.getLayoutY();
				addLine();
				showLines();
				e2.consume();
			});
		}else {
			sp.setOnMousePressed(null);
			sp.setOnMouseDragged(null);
		}
	}
	
}
