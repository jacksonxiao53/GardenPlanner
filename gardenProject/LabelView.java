
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * 
 * The LabelView Class makes labels. Thats the x and y position from where
 * 	the mouse was last click after pressing on the label button. After that
 * 	a pop-up screen will show up and the user can put in a label.
 * 
 * @author Team10-7
 *
 *
 */
public class LabelView {

	private double xpos;
	private double ypos;
	protected ArrayList<Label> labelList;
	protected StackPane sp;
	protected TextField tf;
	protected Button submit;
	protected Stage stage;
	private ArrayList<String> labelString = new ArrayList<String>();
	private ArrayList<String> labelXY = new ArrayList<String>();

	/**
	 * Constructor for LabelView Class
	 * @param sp: Takes a StackPane
	 */
	public LabelView(ArrayList<Label> labelList, StackPane sp) {
		this.sp = sp;
		this.labelList = labelList;
		submit = new Button("Enter");
		FlowPane fp = new FlowPane();
		Scene scene = new Scene(fp,250,100);
		tf = new TextField("Insert Label");
		
		fp.getChildren().add(tf);
		fp.getChildren().add(submit);
		fp.setPadding(new Insets(20));
		fp.setVgap(20);
		
		stage = new Stage();
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);

		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
		    	if(e.getTarget() == submit) {
		    		addLabel(tf.getText());
		    		tf.clear();
		    		showLabels();
		    		handleDelete(labelList);
		    		stage.close();
		    		e.consume();
		    	}else {
		    		stage.showAndWait();
		    	}
			}
		};
		
		submit.setOnAction(event);
	}
	
	/**
	 * Adds a new label to the screen.
	 * @param lab: A String for the new label
	 */
	protected void addLabel(String lab) {
		if(labelList.size() != 0){
			for(Label l: labelList) {
				sp.getChildren().remove(l);
			}
		}
		labelString.add(lab);
		Label newLabel = new Label(lab);
		labelList.add(newLabel);
		newLabel.setTranslateX(xpos);
		newLabel.setTranslateY(ypos);	
		labelXY.add(xpos+","+ypos);
		newLabel.setTooltip(new Tooltip("Double click to delete"));
	}
	
	
	
	
	/**
	 * Function to add Labels onto the StackPane
	 */
	private void showLabels() {
		for(Label l: labelList) {
			sp.getChildren().add(l);	
			
			
		}
	}
		
	/**
	 * getter for the button submit
	 * @return Button - the submit button
	 */
	protected Button getSubmit() {
		return submit;
	}
	
	/**
	 * Getter for the Stage stage.
	 * @return Stage - the current stage 
	 */
	protected Stage getStage() {
		return stage;
	}
	
	/**
	 * setter for xpos
	 * @param xpos - x position of label
	 */
	protected void setX(double xpos) {
		this.xpos = xpos;
	}
	
	/**
	 * Setter for ypos
	 * @param ypos - y position of label
	 */
	protected void setY(double ypos) {
		this.ypos = ypos;
	}
	/**
	 * Handle placing the label on the stackpane
	 * @param sp - where the label will be placed
	 * @param controller - controller class
	 */
	protected void handleLabelAction(StackPane sp, GardenController controller) {
		sp.setOnMouseClicked(e1 ->{
		   	if(e1.getTarget() == getSubmit()) {
		   		getStage().close();
		   	}else {
		   		controller.setX(e1.getX());
		   		controller.setY(e1.getY());
		   		double xpos = controller.getX();
		   		double ypos = controller.getY();
		   		setX(xpos);
		   		setY(ypos);
		   		getStage().showAndWait();
		   	}
		   	e1.consume();
			sp.setOnMouseClicked(null);
		});
	}
	
	public ArrayList<String> getLabelString() {
		return this.labelString;
	}
	public ArrayList<String> getLabelXY(){
		return this.labelXY;
	}
	public void setLabelString(ArrayList<String> l){
		this.labelString = l;
	}
	public void setLabelXY(ArrayList<String> l) {
		this.labelXY = l;
	}
	
	/**
	 * Add label into the ArrayList of labels
	 * @param l - a label
	 */
	public void addLabelList(Label l) {
		labelList.add(l);
	}
	
	/**
	 * If a label is double clicked then it will be deleted 
	 * @param list - list of labels on the stackpane
	 */
	public void handleDelete(ArrayList<Label> list) {
		for(Label l: list) {
			l.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
			    public void handle(MouseEvent mouseEvent) {
			          if(mouseEvent.getClickCount() == 2){
			        	  int index = labelList.indexOf(mouseEvent.getSource());
			        	  labelList.remove(index);
			        	  labelString.remove(index);
			        	  labelXY.remove(index);
			        	  sp.getChildren().remove(l);
			        	  
			        }
			    }
			});
		}
	}
	
	
	
	
}
