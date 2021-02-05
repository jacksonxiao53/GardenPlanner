/**
 *  The GardenView class contains methods that sets up the GUI interface for our garden planning software
 *  @author Team 10-7
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.skin.ListViewSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GardenView extends Application  {

	protected GardenController controller;
	protected Scene homeScene;
	protected Scene createScene;
	protected Scene modifyScene;
	protected Scene presetScene;
	protected Scene helpScene;
	protected Scene layoutScene;
	protected Stage primaryStage;
	protected Scene saveScene;
	protected double WIDTH = 1200;
	protected double HEIGHT = 800;
	protected double x;
	
	private final ObservableList<Plant> data; 
	private ObservableList<SaveData> savedFiles;
	protected ArrayList<String> imgPaths = new ArrayList<>();
	protected ArrayList<String> xyCoordinates = new ArrayList<>();
	protected ArrayList<Double> radii = new ArrayList<>();
	private TableView<SaveData> table = new TableView<SaveData>();
	
	protected ArrayList<Line> lineList = new ArrayList<>();
	protected ArrayList<Label> labelList = new ArrayList<>();
	protected ArrayList<String> labelString = new ArrayList<>();
	protected ArrayList<String> labelXY = new ArrayList<>();
	
	private int gardenWidth;
	private int gardenHeight;
	
	protected boolean drawing = false, labeling = false, deleting = false;
	
	
	
	/**
	 * Constructor that takes in no parameter. Initializes the controller and data fields.
	 */
	public GardenView(){
		controller = new GardenController();
		data = FXCollections.observableArrayList(controller.getPlantList());
		savedFiles = FXCollections.observableArrayList(controller.fileList());
	}
	
	
	/**
	 * Overrides the start method when the application is started. Takes in a stage as parameter and set
	 * the primary stage to the parameter. Creates the home scene, create scene, modify scene, preset garden
	 * scene, and the help scene. Set the initial set to home scene.
	 * @param stage - container that hosts a Scene 
	 */
    @Override
    public void start(Stage stage) {
    	
    	primaryStage = stage;
    	homeScene = buildHomeScene();
    	createScene = buildCreateScene();
    	modifyScene = buildModifyScene();
    	presetScene = buildPresetScene();
    	helpScene = buildHelpScene();
        stage.setScene(homeScene);
        stage.show();
    }

    
    /**
     * This method creates a scene representing the home screen. This method creates buttons for the create
     * screen, modify screen, preset garden screen, and the help screen. Method has event handler which will
     * switch the scene based on the button pressed. 
     * @return scene - contains all the content of the home scene
     */
	private Scene buildHomeScene() {
    	BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		//bp.setStyle("-fx-background-image: url(\"/images/commonMilkweed.png\");");
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
        
        Button create = new Button("Create");
        create.setStyle("-fx-font-size:20");
        create.setMinSize(150, 75);
        create.setMaxWidth(Double.MAX_VALUE);
        Button modify = new Button("Modify Existing");
        modify.setStyle("-fx-font-size:20");
        modify.setMinSize(150, 75);
        modify.setMaxWidth(Double.MAX_VALUE);
        Button preset = new Button("Preset Garden");
        preset.setStyle("-fx-font-size:20");
        preset.setMinSize(150, 75);
        preset.setMaxWidth(Double.MAX_VALUE);
        Button help = new Button("Help");
        help.setStyle("-fx-font-size:20");
        help.setMinSize(150, 75);
        help.setMaxWidth(Double.MAX_VALUE);
        
        GridPane gp = new GridPane();
        gp.setMaxSize(WIDTH-100, 400);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        gp.getColumnConstraints().addAll(col1, col2);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPercentHeight(50);
        row2.setPercentHeight(50);
        gp.getRowConstraints().addAll(row1, row2);
        gp.setHgap(65);
        gp.add(create, 0, 0);
        gp.add(modify, 1, 0);
        gp.add(preset, 0, 1);
        gp.add(help, 1, 1);
        bp.setCenter(gp);
        
        Text title = new Text("Group 10-7 Garden Project");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        bp.setTop(title);
        BorderPane.setMargin(title, new Insets(25,25,25,25));
        BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
            	if(e.getSource().equals(create)) {
            		primaryStage.setScene(createScene);
             	} else if(e.getSource().equals(modify)) {
            		savedFiles = FXCollections.observableArrayList(controller.fileList());
             		table.setItems(savedFiles);
             		primaryStage.setScene(modifyScene);
            	} else if(e.getSource().equals(preset)) {
            		primaryStage.setScene(presetScene);
            	} else if(e.getSource().equals(help)) {
            		primaryStage.setScene(helpScene);
            	}
            } 
        }; 
 
        create.setOnAction(event); 
        modify.setOnAction(event);
        preset.setOnAction(event); 
        help.setOnAction(event);
        
		return new Scene(bp, WIDTH, HEIGHT);
	}
	
	
	/**
	 * This method creates a create screen where users can enter a new file name and the dimensions of 
	 * their property. Method has event handler which will switch the scene based on the button pressed. 
	 * @return Scene - contains all the content of the create scene 
	 */
	private Scene buildCreateScene() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
		
		Button back = new Button("Back");
        back.setStyle("-fx-font-size:15");
        back.setMinSize(75, 50);
        
        Button create = new Button("Create");
        create.setStyle("-fx-font-size:15");
        create.setMinSize(75, 50);

        HBox hb = new HBox(back, create);
        bp.setBottom(hb);
        
        
		Text title = new Text("Create Screen");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        bp.setTop(title);
        BorderPane.setMargin(title, new Insets(25,25,25,25));
        BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
        
        Label fnLabel = new Label("File Name: ");
        fnLabel.setFont(new Font("Arial", 24));
        fnLabel.setTextFill(Color.BLACK);
        Label psLabel = new Label("Property Size (feet)");
        psLabel.setFont(new Font("Arial", 24));
        psLabel.setTextFill(Color.BLACK);
        Label hLabel = new Label("Height: ");
        hLabel.setFont(new Font("Arial", 24));
        hLabel.setTextFill(Color.BLACK);
        Label wLabel = new Label("Width: ");
        wLabel.setFont(new Font("Arial", 24));
        wLabel.setTextFill(Color.BLACK);
        
        TextField fileName = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.matches("0")) return;
            width.setText(newValue.replaceAll("[^\\d]", ""));
            width.setText(newValue.replaceAll("0", ""));
        });
        height.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (newValue.matches("\\d*") && !newValue.matches("0")) return;
            height.setText(newValue.replaceAll("[^\\d]", ""));
            height.setText(newValue.replaceAll("0", ""));
        });
        
        GridPane gp = new GridPane();
		gp.addRow(0, fnLabel, fileName);
		gp.addRow(1, psLabel);
		gp.addRow(2, hLabel, height);
		gp.addRow(3, wLabel, width);
		ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        gp.getColumnConstraints().addAll(col1, col2);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        row1.setPercentHeight(20);
        row2.setPercentHeight(15);
        row3.setPercentHeight(15);
        row4.setPercentHeight(15);
        gp.getRowConstraints().addAll(row1, row2, row3, row4);
		bp.setCenter(gp);
        
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
				if (e.getSource().equals(back)) {
					fileName.clear();
					width.clear();
					height.clear();
					primaryStage.setScene(homeScene);
				} else if (e.getSource().equals(create)) {
					try {
						String f = fileName.getText();
						String h = height.getText();
						String w = width.getText();
						fileName.clear();
						height.clear();
						width.clear();
						buildLayoutScene(f, Integer.parseInt(h.trim()), Integer.parseInt(w.trim()),null,false);
					} catch(Exception ex) {
						;
					}
				}
            } 
        }; 
 
        back.setOnAction(event); 
        create.setOnAction(event);
		
		return new Scene(bp, WIDTH, HEIGHT);
	}
	
	
	/**
	 * This method creates the modify scene that allows user to select existing save files and load it 
	 * into the application. 
	 * @return Scene - contains all the content of the modify scene 
	 */
	private Scene buildModifyScene() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
		
		Label fileTitle = new Label("Existing Files");
		fileTitle.setFont(new Font("Arial", 24));
		fileTitle.setTextFill(Color.WHITE);
		BorderPane.setAlignment(fileTitle, Pos.CENTER);
		
		int tableSize  = (int) (WIDTH-40);
		table.setMaxWidth(tableSize);
    	TableColumn fileName = new TableColumn("File Name");
    	fileName.setMinWidth(tableSize/3);
    	fileName.setCellValueFactory(new PropertyValueFactory<SaveData, String>("projectName"));
        
        TableColumn lastSave = new TableColumn("Last Save");
        lastSave.setMinWidth(tableSize/3);
        lastSave.setCellValueFactory(new PropertyValueFactory<SaveData, String>("saveDate"));
 
        TableColumn dimensions = new TableColumn("Dimensions");
        lastSave.setMinWidth(tableSize/3);
        TableColumn height = new TableColumn("Height");
        TableColumn width = new TableColumn("Width");
        height.setMinWidth(tableSize/6);
        width.setMinWidth(tableSize/6);
        height.setCellValueFactory(new PropertyValueFactory<SaveData, String>("height"));
        width.setCellValueFactory(new PropertyValueFactory<SaveData, String>("width"));
        dimensions.getColumns().addAll(height, width);
 
        table.getColumns().addAll(fileName, lastSave, dimensions);
        table.setItems(savedFiles);
        
        Button back = new Button("Back");
        back.setStyle("-fx-font-size:15");
        back.setMinSize(75, 50);
        
        Button delete = new Button("Delete");
        delete.setStyle("-fx-font-size:15");
        delete.setMinSize(75, 50);
        
        Button modify = new Button("Modify");
        modify.setStyle("-fx-font-size:15");
        modify.setMinSize(75, 50);
        
		HBox hb = new HBox(back, delete, modify);
        
		bp.setBottom(hb);
        bp.setTop(fileTitle);
        bp.setCenter(table);
        
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            {
				if (e.getSource().equals(back)) {
					primaryStage.setScene(homeScene);
				} else if (e.getSource().equals(delete)) {
					try {
					SaveData data = table.getSelectionModel().getSelectedItem();
					Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + data.getProjectName() + ".save?",
							ButtonType.YES, ButtonType.CANCEL);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						File[] files = new File(System.getProperty("user.dir")).listFiles();
						for (File file : files) {
							if (file.getName().equals(data.getProjectName() + ".save")) {
								table.getItems().remove(table.getSelectionModel().getSelectedItem());
								file.delete();
							}
						}
					}
					} catch (Exception exception){
						;
					}
	            } else if (e.getSource().equals(modify)) {
					SaveData data = table.getSelectionModel().getSelectedItem();
                    buildLayoutScene(data.getProjectName(),data.getHeight(),data.getWidth(),data,false);     
	            } 
            } 
        }; 
        
        back.setOnAction(event); 
        delete.setOnAction(event); 
        modify.setOnAction(event); 
        
		return new Scene(bp, WIDTH, HEIGHT);
	}
	
	
	/**
	 * Creates the preset garden scene with EventHandler that switch scenes based on the button pressed
	 * @return Scene - contains all the content of the preset scene 
	 */
	private Scene buildPresetScene() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
		
		Button back = new Button("Back");
        back.setStyle("-fx-font-size:15");
        back.setMinSize(75, 50);
        bp.setBottom(back);
        BorderPane.setAlignment(back, Pos.BOTTOM_LEFT);
		
		Text title = new Text("Preset Screen");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        bp.setTop(title);
        BorderPane.setMargin(title, new Insets(25,25,25,25));
        BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
        

        Label fnLabel = new Label("File Name: ");
        fnLabel.setFont(new Font("Arial", 24));
        fnLabel.setTextFill(Color.BLACK);
        Label psLabel = new Label("Property Size (feet)");
        psLabel.setFont(new Font("Arial", 24));
        psLabel.setTextFill(Color.BLACK);
        Label hLabel = new Label("Height: ");
        hLabel.setFont(new Font("Arial", 24));
        hLabel.setTextFill(Color.BLACK);
        Label wLabel = new Label("Width: ");
        wLabel.setFont(new Font("Arial", 24));
        wLabel.setTextFill(Color.BLACK);
        
        Label presetGardenLabel = new Label("Preset Gardens: ");
        presetGardenLabel.setFont(new Font("Arial", 24));
        presetGardenLabel.setTextFill(Color.BLACK);
        
        
        TextField fileName = new TextField();
        TextField width = new TextField();
        TextField height = new TextField();
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.matches("\\d*") && !newValue.matches("0")) return;
            width.setText(newValue.replaceAll("[^\\d]", ""));
            width.setText(newValue.replaceAll("0", ""));
        });
        height.textProperty().addListener((observable, oldValue, newValue) -> {
        	if (newValue.matches("\\d*") && !newValue.matches("0")) return;
            height.setText(newValue.replaceAll("[^\\d]", ""));
            height.setText(newValue.replaceAll("0", ""));
        });
        
        GridPane gp = new GridPane();
		gp.addRow(0, fnLabel, fileName);
		gp.addRow(1, psLabel);
		gp.addRow(2, hLabel, height);
		gp.addRow(3, wLabel, width);
		gp.addRow(4,presetGardenLabel);
		ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        gp.getColumnConstraints().addAll(col1, col2);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        RowConstraints row3 = new RowConstraints();
        RowConstraints row4 = new RowConstraints();
        RowConstraints row5 = new RowConstraints();
        RowConstraints row6 = new RowConstraints();
        row1.setPercentHeight(20);
        row2.setPercentHeight(15);
        row3.setPercentHeight(15);
        row4.setPercentHeight(15);
        row5.setPercentHeight(15);
        row6.setPercentHeight(15);
        gp.getRowConstraints().addAll(row1, row2, row3, row4,row5,row6);
		bp.setCenter(gp);
		
		Button garden1 = new Button("Preset Garden #1");
		Button garden2 = new Button("Preset Garden #2");
		Button garden3 = new Button("Preset Garden #3");
		
		garden1.setTooltip(new Tooltip("Bloom Time: Spring" + "\n"
										+"Soil Moisture: Average" + "\n"+
										"Light: Filtered-Shade"));
		garden2.setTooltip(new Tooltip("Bloom Time: Summer" + "\n"
										+"Soil Moisture: Moist" + "\n"+
										"Light: Full-Sun"));
		garden3.setTooltip(new Tooltip("Bloom Time: Fall" + "\n"
										+"Soil Moisture: Average" + "\n"+
										"Light: Filtered-Shade "));
		HBox buttonbox = new HBox(10);
		buttonbox.getChildren().addAll(garden1,garden2,garden3);
		gp.addRow(5,buttonbox);
		
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
				if (e.getSource().equals(back)) {
					primaryStage.setScene(homeScene);
				}
				if(e.getSource().equals(garden1)) {
					SaveData data = controller.getData("presetGarden1.save");
					String f = fileName.getText();
					String h = height.getText();
					String w = width.getText();
					fileName.clear();
					height.clear();
					width.clear();
					buildLayoutScene(f, Integer.parseInt(h.trim()), Integer.parseInt(w.trim()),data,true);	
				}
				if(e.getSource().equals(garden2)) {
					SaveData data = controller.getData("presetGarden2.save");
					String f = fileName.getText();
					String h = height.getText();
					String w = width.getText();
					fileName.clear();
					height.clear();
					width.clear();
					buildLayoutScene(f, Integer.parseInt(h.trim()), Integer.parseInt(w.trim()),data,true);	
				}
				if(e.getSource().equals(garden3)) {
					SaveData data = controller.getData("presetGarden3.save");
					String f = fileName.getText();
					String h = height.getText();
					String w = width.getText();
					fileName.clear();
					height.clear();
					width.clear();
					buildLayoutScene(f, Integer.parseInt(h.trim()), Integer.parseInt(w.trim()),data,true);	
				}
            } 
        }; 
 
        back.setOnAction(event); 
        garden1.setOnAction(event);
        garden2.setOnAction(event);
        garden3.setOnAction(event);


        
		return new Scene(bp, WIDTH, HEIGHT);
	}

	
	/**
	 * Creates the help scene which will show all the user tips for the garden feature
	 * @return Scene - contains all the content of the help scene 
	 */
	private Scene buildHelpScene() {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
		
		Button back = new Button("Back");
        back.setStyle("-fx-font-size:15");
        back.setMinSize(75, 50);
        bp.setBottom(back);
        BorderPane.setAlignment(back, Pos.BOTTOM_LEFT);

		Text title = new Text("Help Screen");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
		bp.setTop(title);
		BorderPane.setMargin(title, new Insets(25, 25, 25, 25));
		BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);

		Rectangle background = new Rectangle(WIDTH-200, HEIGHT-200);
		background.setFill(Color.WHITE);
		background.setStroke(Color.BLACK);
		background.setStrokeWidth(3);
		StackPane.setAlignment(background, Pos.CENTER);
		
		Text helpText = new Text("CREATE: Create a new garden with filename and property size. \n" + "\n"
				+ "MODIFY EXISTING: Choose a file in your browser that you want to modify. \n" + "\n"
				+ "PRESET GARDEN: Give an preset example of a garden with some plants. \n" + "\n"
				+ "SEARCH BAR: Search the plants that you want to put into the garden.\n" + "\n"
				+ "TOOL BAR: Features like label, select, and delete that can be use to make garden.  \n" + "\n"
				+ "SEASON SLIDER: Choose the seasons of the year that you want to see for the plants. \n" + "\n"
				+ "INFO: Give the description of the plants like light, water use, and soil type. \n" + "\n"	
				+ "FILTER: Choose plants by light, water use, bloom type, and soil type. \n" + "\n"
				+ "SAVE: Save your garden as a file to your computer. \n");
		helpText.setFont(Font.font("Arial", FontWeight.NORMAL, 22));
		helpText.wrappingWidthProperty().bind(bp.widthProperty());
        helpText.setTextAlignment(TextAlignment.CENTER);
        StackPane.setAlignment(helpText, Pos.CENTER);
        
        StackPane sp = new StackPane();
        sp.getChildren().addAll(background, helpText);
        
		bp.setCenter(sp);
		
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
				if (e.getSource().equals(back)) {
					primaryStage.setScene(homeScene);
				}
            } 
        }; 
 
        back.setOnAction(event); 
        
		return new Scene(bp, WIDTH, HEIGHT);
	}

	
	/**
	 * Creates the main layout scene. The layout scene takes in a file name, the height of the property, the width of the property, a SaveData object,
	 * and if it is a preset garden or not.
	 * The layout scene contains a ListView of plants, a search feature, ToolTip feature, save feature, and drag/drop capability. 
	 * @param fileName - the name of the garden project
	 * @param gardenHeight - height of property entered by user
	 * @param gardenWidth - width of property entered by user
	 * @param savedGarden - holds a SaveData object which implements serializable. If the user is not loading a saved garden then it will be null
	 * @param preset - true if it is a preset garden and false if it is not
	 */
	private void buildLayoutScene(String fileName, int gardenHeight, int gardenWidth,SaveData savedGarden,boolean preset) {
		this.gardenHeight = gardenHeight;
		this.gardenWidth = gardenWidth;
		BorderPane bp = new BorderPane();
		bp.setStyle("-fx-background-image: url(\"/images/background.jpg\");-fx-background-size: 1400, 700;");
		
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
        choiceBox.getItems().addAll("Plant Name","Plant Type","Bloom Time","Flower Color","Fall Foliage Color","Summer Foliage Color", "Light", "Water Use");
        choiceBox.setValue("Plant Name");
        
        TextField textField = new TextField();
        textField.setPromptText("Search");
        
		
		ListView<Label> lv = new ListView<Label>();
		lv.setPrefSize(WIDTH/4, WIDTH/2);

		//add season radio button
		RadioButton rbWinter = new RadioButton("Winter");
		RadioButton rbSpring = new RadioButton("Spring");
		RadioButton rbSummer = new RadioButton("Summer");
		RadioButton rbFall = new RadioButton("Fall");
		rbSpring.setSelected(true);
		
		//allow one radio click at a time
		ToggleGroup radioGroup = new ToggleGroup();
	    rbWinter.setToggleGroup(radioGroup);
	    rbSpring.setToggleGroup(radioGroup);
	    rbSummer.setToggleGroup(radioGroup);
	    rbFall.setToggleGroup(radioGroup);
	   
	    GridPane gp = new GridPane();
	    gp.addRow(0, rbWinter, rbSpring);
	    gp.addRow(1, rbSummer, rbFall);
	    gp.setAlignment(Pos.CENTER);
		
		VBox vBox = new VBox(textField, choiceBox,gp,lv);// Add choiceBox and textField to hBox
		VBox.setMargin(textField, new Insets(30, 0, 0, 0));
		VBox.setMargin(lv, new Insets(30, 0, 0, 0));
		VBox.setMargin(gp, new Insets(20, 0, 0, 0));
		vBox.setAlignment(Pos.CENTER);
		

		TilePane tp = new TilePane(Orientation.VERTICAL);
		tp.setStyle("-fx-background-color: DAE6F3;");
		tp.setPrefWidth(WIDTH/4);
		bp.setLeft(tp);
		tp.getChildren().add(vBox);

		ObservableList<Label> labels = FXCollections.observableArrayList();
		
		//this should show all the picture
		for(Plant pl: data) {
			String name = pl.getPlantName();
			ImageView iv = new ImageView();
			//String season = pl.getBloom();
				
			if (pl.getBloom().equals("Spring")) {
				iv.setImage(new Image(pl.getImagePath()));
			} else {
				iv.setImage(new Image(pl.getImagePath_nonbloom()));
			}
        	iv.setPreserveRatio(true);
        	iv.setFitHeight(100);
        	Circle c = new Circle(50);
        	c.setFill(new ImagePattern(iv.getImage()));
            Label nLabel = new Label(name.toUpperCase());
            nLabel.setFont(new Font("Arial", 10));
            nLabel.setTextFill(Color.BLACK);
            nLabel.setGraphic(c);
            nLabel.setUserData(pl);
            Tooltip tt = new Tooltip();
			tt.setText(
			    "Name: " + ((Plant) nLabel.getUserData()).getPlantName() + "\n" +
			    "Plant Type: " + ((Plant) nLabel.getUserData()).getPlantType() + "\n" +
			    "Bloom Time: " + ((Plant) nLabel.getUserData()).getBloom() + "\n" +
			    "Color: " + ((Plant) nLabel.getUserData()).getFlowerColor() + "\n" +
			    "Fall Color: " + ((Plant) nLabel.getUserData()).getFallFoliageColor() + "\n" +
			    "Summer Color: " + ((Plant) nLabel.getUserData()).getSummerFoliageColor() + "\n" +
			    "Light: " + ((Plant) nLabel.getUserData()).getLight() + "\n" +
			    "Water: " + ((Plant) nLabel.getUserData()).getWater() + "\n"+
			    "Width(inches): " + ((Plant) nLabel.getUserData()).getPlantWidth()
			    
			);
			tt.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
			nLabel.setTooltip(tt);
            labels.add(nLabel);
            source(nLabel, radioGroup);
		}
		////Insert RadioButton, the image should change when each radiobutton is click
		
		radioGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				for (Label l : labels) {
					ImageView iv = new ImageView();
					Plant p = (Plant)l.getUserData();
					String season = p.getBloom();
					
					if(((RadioButton) radioGroup.getSelectedToggle()).getText().equals("Winter")) {
						iv.setImage(new Image(p.getWinterImage()));
					}
					else if (((RadioButton) radioGroup.getSelectedToggle()).getText().equals(season)) {
						iv.setImage(new Image(p.getImagePath()));
					} else {
						iv.setImage(new Image(p.getImagePath_nonbloom()));
					}
					iv.setPreserveRatio(true);
					iv.setFitHeight(100);
					Circle c = new Circle(50);
			        c.setFill(new ImagePattern(iv.getImage()));
					l.setGraphic(c);
				}
			
			}

		});


		FilteredList<Label> flPlant = new FilteredList<Label>(labels, p -> true);
		lv.setItems(flPlant);
		
        textField.setOnKeyReleased(keyEvent ->
        {
            switch (choiceBox.getValue())//Switch on choiceBox value
            {
            	case "Plant Name":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getPlantName().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Plant Type":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getPlantType().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Bloom Time":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getBloom().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Flower Color":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getFlowerColor().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Fall Foliage Color":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getFallFoliageColor().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Summer Foliage Color":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getSummerFoliageColor().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Light":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getLight().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            	case "Water Use":
            		flPlant.setPredicate(p -> ((Plant) p.getUserData()).getWater().toLowerCase().contains(textField.getText().toLowerCase().trim()));//filter table by first name
            		break;
            }
        });

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) ->
        {//reset table and textfield when new choice is selected
            if (newVal != null)
            {
                textField.setText("");
                flPlant.setPredicate(null);//This is same as saying flPlant.setPredicate(p->true);
            }
        });
 
        Rectangle rect;
        int rectSize = 600;
        double rectWidth = -1;
        double rectHeight = -1;
        if(gardenHeight > gardenWidth) {
        	double n1 = gardenHeight;
        	double n2 = gardenWidth;
        	double factor = n2/n1;
        	rectWidth = rectSize*factor;
        	rectHeight = rectSize;
        	rect = new Rectangle(rectWidth, rectHeight);
        } else if(gardenHeight < gardenWidth) {
        	double n1 = gardenHeight;
        	double n2 = gardenWidth;
        	double factor = n1/n2;
        	rectWidth = rectSize;
        	rectHeight = rectSize*factor;
        	rect = new Rectangle(rectWidth, rectHeight);
        } else {
        	rectWidth = rectSize;
        	rectHeight = rectSize;
        	rect = new Rectangle(rectSize, rectSize);
        }
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(3);
        
        Label title = new Label(fileName + " " + gardenWidth + " ft x " + gardenHeight + " ft Garden");
        title.setFont(new Font("Arial", 25));
        title.setTextFill(Color.BLACK);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        
        Button label = new Button("Label");
        label.setStyle("-fx-font-size:15");
        label.setMinSize(75, 50);
        StackPane.setAlignment(label, Pos.TOP_LEFT);
        
        /**Button line	= new Button("Draw");
        line.setStyle("-fx-font-size:15");
        line.setMinSize(75, 50);
        StackPane.setAlignment(line, Pos.CENTER_LEFT);*/

        Button exit = new Button("Exit");
        exit.setStyle("-fx-font-size:15");
        exit.setMinSize(75, 50);
        StackPane.setAlignment(exit, Pos.BOTTOM_LEFT);
        
        Button save = new Button("Save");
        save.setStyle("-fx-font-size:15");
        save.setMinSize(75, 50);
        StackPane.setAlignment(save, Pos.BOTTOM_RIGHT);
        
        ImageView iv = new ImageView();
        iv.setImage(new Image("/images/trash.png"));
        iv.setPreserveRatio(true);
        iv.setFitHeight(100);
        iv.setFitWidth(100);
        StackPane.setAlignment(iv, Pos.TOP_RIGHT);
        target2(iv);
        
        StackPane sp = new StackPane();
        sp.setPadding(new Insets(20, 20, 20, 20));
		bp.setCenter(sp);
		sp.getChildren().addAll(rect, title, exit, save, iv,label);
		target(sp);
		LabelView labelview = new LabelView(labelList,sp);
		//MakeDraw drawings = new MakeDraw(lineList, sp, controller);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
				if (e.getSource().equals(exit)) {
					Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to exit? All progress will be lost.", ButtonType.YES, ButtonType.CANCEL);
					alert.showAndWait();
					if (alert.getResult() == ButtonType.YES) {
						imgPaths.clear();
						xyCoordinates.clear();
						radii.clear();
						labelString.clear();
						labelXY.clear();
						labelList.clear();
						System.out.println(labelString);
						System.out.println(labelXY);
						primaryStage.setScene(homeScene);
					}
				} 
				if (e.getSource().equals(save)) {
					WritableImage screenshot = takePicture();
					//primaryStage.setScene(homeScene);
					buildSaveScene(fileName, gardenHeight, gardenWidth,screenshot);
					primaryStage.setScene(saveScene);
				}
				if(e.getSource().equals(label)) {
					labelview.handleLabelAction(sp,controller);
					labelString = labelview.getLabelString();
					labelXY = labelview.getLabelXY();
				}
				/**if(e.getSource().equals(line)) {

					drawings.setIsLining();
					drawings.handleLineAction(e);
				}*/
           
            } 
        }; 
 
        exit.setOnAction(event);
        save.setOnAction(event);
        label.setOnAction(event);
        //line.setOnAction(event);
        if(savedGarden != null && !preset) {
        	imgPaths = savedGarden.getImgPaths();
        	xyCoordinates = savedGarden.getXY();
        	radii = savedGarden.getRadii();
        	labelString = savedGarden.getLabelString();
        	labelXY = savedGarden.getLabelXY();
        	if(labelString != null && labelXY!= null) {
        		for (int i = 0; i<labelString.size();i++) {
        			Label l = new Label(labelString.get(i));
        			String xy = labelXY.get(i);
        			String[] xyParts = xy.split(",");
        			l.setTranslateX(Double.parseDouble(xyParts[0]));
        			l.setTranslateY(Double.parseDouble(xyParts[1]));
        			sp.getChildren().add(l);
        			labelview.addLabelList(l);
        			l.setTooltip(new Tooltip("Double click to delete"));
        			
        		}
        		labelview.handleDelete(labelview.labelList);
        		labelview.setLabelString(labelString);
        		labelview.setLabelXY(labelXY);
        	}
        	for(int i = 0; i<imgPaths.size();i++) {
        		Image img = new Image(imgPaths.get(i));
        		Circle c = new Circle(50);
            	c.setFill(new ImagePattern(img));
            	sp.getChildren().add(c);
            	String coords = xyCoordinates.get(i);
            	String[] parts = coords.split(",");
            	c.setTranslateX(Double.parseDouble(parts[0]));
        		c.setTranslateY(Double.parseDouble(parts[1]));
        		source2(c);
        		c.setCenterX(Double.parseDouble(parts[0]));
        		c.setCenterY(Double.parseDouble(parts[1]));
        		c.setRadius(radii.get(i));
        	}
        }
        if(savedGarden != null && preset) {
        	imgPaths = savedGarden.getImgPaths();
        	xyCoordinates = savedGarden.getXY();
        	for(int i = 0; i<imgPaths.size();i++) {
        		Image img = new Image(imgPaths.get(i));
        		Circle c = new Circle(50);
            	c.setFill(new ImagePattern(img));
            	sp.getChildren().add(c);
            	String coords = xyCoordinates.get(i);
            	String[] parts = coords.split(",");
            	c.setTranslateX(Double.parseDouble(parts[0]));
        		c.setTranslateY(Double.parseDouble(parts[1]));
        		source2(c);
        		c.setCenterX(Double.parseDouble(parts[0]));
        		c.setCenterY(Double.parseDouble(parts[1]));
        		
        		for(Plant pl : data) {
        			if(pl.getImagePath_nonbloom().equals(imgPaths.get(i)) || pl.getImagePath().equals(imgPaths.get(i))) {
        				double plantWidth = pl.getPlantWidth()/12; //width in ft
        				double factor = 600/gardenWidth;
        				c.setRadius((plantWidth*factor)/2);
        		    	radii.add((plantWidth*factor)/2);
        			}
        		}
        	}
        }
		
		layoutScene = new Scene(bp, WIDTH, HEIGHT);
		primaryStage.setScene(layoutScene);
	}


	/**
	 * Creates the save scene. The save scene contains labels, a textfield, and an imageview of a preview of the garden.
	 * @param projectName - name of garden project
	 * @param height - height of the garden
	 * @param width - width of the garden
	 * @param screenshot - a WritableImage of a screenshot of the layout scene 
	 */
	private void buildSaveScene(String projectName, int height, int width,WritableImage screenshot) {
		BorderPane bp = new BorderPane();
		bp.setPadding(new Insets(20, 20, 20, 20));
		bp.setStyle("-fx-background-color:DARKSEAGREEN;-fx-background-size: 1400, 700;");
		
		Button back = new Button("Back");
        back.setStyle("-fx-font-size:15");
        back.setMinSize(75, 50);
        
        Button save = new Button("Save");
        save.setStyle("-fx-font-size:15");
        save.setMinSize(75, 50);

        HBox hb = new HBox(back, save);
        bp.setBottom(hb);
        
        Text title = new Text("Save Screen");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 35));
        bp.setTop(title);
        BorderPane.setMargin(title, new Insets(25,25,25,25));
        BorderPane.setAlignment(title, Pos.BOTTOM_CENTER);
        
        Label fnLabel = new Label("File Name: ");
        fnLabel.setFont(new Font("Arial", 24));
        fnLabel.setTextFill(Color.BLACK);
        
        TextField fileName = new TextField(projectName);
        
        Label pLabel = new Label("Preview:");
        pLabel.setFont(new Font("Arial", 24));
        pLabel.setTextFill(Color.BLACK);
        
        
        GridPane gp = new GridPane();
		gp.add(fnLabel,0,0,1,1);	
		gp.add(fileName,1,0,1,1);	
		ImageView preview = new ImageView(screenshot);
    	preview.setPreserveRatio(true);
		preview.setFitHeight(550);
		preview.setFitWidth(800);
		
		gp.add(pLabel,0,1,1,1);
		gp.add(preview,0,4,3,3);
		bp.setCenter(gp);
		
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) 
            { 
				if (e.getSource().equals(back)) {
					primaryStage.setScene(layoutScene);
				} else if (e.getSource().equals(save)) {
					
					try {
						String saveName = fileName.getText();
						Alert alert = new Alert(AlertType.CONFIRMATION, "Save file as "+saveName+".save?", ButtonType.YES, ButtonType.CANCEL);
						alert.showAndWait();
						
						if (alert.getResult() == ButtonType.YES) {
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
							LocalDateTime now = LocalDateTime.now(); 
							String saveTime = dtf.format(now);
							controller.save(saveName, saveTime, width, height, imgPaths, xyCoordinates,radii,labelString,labelXY);
							imgPaths.clear();
							xyCoordinates.clear();
							radii.clear();
							labelString.clear();
							labelXY.clear();
							labelList.clear();
							primaryStage.setScene(homeScene);
						}
						
					} catch(Exception ex) {
						;
					}
				}
            } 
        }; 
 
        back.setOnAction(event); 
        save.setOnAction(event);
        
        saveScene = new Scene(bp, WIDTH, HEIGHT);
		primaryStage.setScene(saveScene);
		
		
	}
	
	
	/**
	 * Takes a screenshot of the layout screen and save it as a jpg file 
	 */
	protected WritableImage takePicture() {
		WritableImage writableImage =  new WritableImage((int)layoutScene.getWidth(), (int)layoutScene.getHeight());
	    layoutScene.snapshot(writableImage);
	    return writableImage;
	}
	
	/**
	 * Setup the source for drag and drop. When a label is pressed, the image of the label will be put on the dragboard
	 * @param tg -  a group of radio buttons that represents bloom time
	 * @param l - label that displays the information of a plant
	 */
	private void source(Label l, ToggleGroup tg) {
		l.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Dragboard db = l.startDragAndDrop(TransferMode.COPY);

				ClipboardContent content = new ClipboardContent();
				
				Plant p = (Plant)l.getUserData();
				Circle c = (Circle)l.getGraphic();
				Paint circlePaint = c.getFill();
				Image sourceImage = ((ImagePattern) circlePaint).getImage();
				double plantWidth = p.getPlantWidth()/12; //plant width in feet
				if(((RadioButton)tg.getSelectedToggle()).getText().equals(p.getBloom())) {
					content.putString(p.getImagePath()+","+plantWidth);
				} else {
					if(((RadioButton)tg.getSelectedToggle()).getText().equals("Winter")) {
						content.putString(p.getWinterImage()+","+plantWidth);
					}
					else {
						content.putString(p.getImagePath_nonbloom()+","+plantWidth);
					}
				}
				content.putImage(sourceImage);
				db.setContent(content);

				event.consume();
			}

		});

		l.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					l.getScene().setCursor(Cursor.HAND);
				}
			}
		});

		l.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					l.getScene().setCursor(Cursor.DEFAULT);
				}
			}
		});
	}
	/**
	 * Setup the source for drag and drop. The source is a circle that represents a plant. The image of the plant and radius of the 
	 * circle will be put into the dragboard
	 * @param c - a circle with a plant image
	 */
	private void source2(Circle c) {
		c.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				 
				Dragboard db = c.startDragAndDrop(TransferMode.MOVE);

				ClipboardContent content = new ClipboardContent();

				
				Paint circlePaint = c.getFill();
				Image sourceImage = ((ImagePattern) circlePaint).getImage();
				content.putImage(sourceImage);
				content.putString(Double.toString(c.getRadius()));
				db.setContent(content);
				
				event.consume();
			}

		});
		
		c.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					c.getScene().setCursor(Cursor.HAND);
				}
			}
		});

		c.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!mouseEvent.isPrimaryButtonDown()) {
					c.getScene().setCursor(Cursor.DEFAULT);
				}
			}
		});
	}
	
	
	/**
	 * Setup the target for drag and drop. The target is a stackpane
	 * @param spane - stackpane that represents the canvas for which plants are dropped into 
	 */
	public void target(StackPane spane) {

		spane.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();

				if (db.hasImage()) {
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}

				event.consume();
			}
		});

		spane.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				int xRectOffset = 150;
				int yRectOffset = 100;
				int rectSize = 600;
				int imageRadius = 50;
				if (event.getX() > xRectOffset + ((rectSize/2) - ((Rectangle) spane.getChildren().get(0)).getWidth()/2) + imageRadius && 
					event.getX() < xRectOffset + rectSize - ((rectSize/2) - ((Rectangle) spane.getChildren().get(0)).getWidth()/2) - imageRadius &&
					event.getY() > yRectOffset + ((rectSize/2) - ((Rectangle) spane.getChildren().get(0)).getHeight()/2) + imageRadius &&
					event.getY() < yRectOffset + rectSize - ((rectSize/2) - ((Rectangle) spane.getChildren().get(0)).getHeight()/2) - imageRadius) {
					controller.setX(event.getX());
					controller.setY(event.getY());

					Dragboard db = event.getDragboard();
					
					if (db.hasImage()) {
						Object source = event.getGestureSource(); //get source
						String plantWidth = "null";
						if(event.getAcceptedTransferMode() == TransferMode.MOVE) {
							move((Circle)source);
							plantWidth =db.getString();
							createCopy2(db.getImage(),spane, Double.parseDouble(plantWidth));
						}
		
						if(event.getAcceptedTransferMode() == TransferMode.COPY) {
							String[] parts = db.getString().split(",");
							imgPaths.add(parts[0]);
							plantWidth = parts[1];
							createCopy(db.getImage(),spane, Double.parseDouble(plantWidth));
						}
						spane.getChildren().remove(source); //remove source after drop
						event.setDropCompleted(true);
					} else {
						event.setDropCompleted(false);
					}

					event.consume();
				}
			}
		});

	}
	/**
	 * Setup the target for drag and drop. The target is an ImageView of a trash can. When a source is dropped onto
	 * the imageview then it is deleted. 
	 * @param iv - ImageView of a trash can
	 */
	private void target2(ImageView iv) {
		iv.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				StackPane spane = (StackPane) iv.getParent();
				Circle source = (Circle) event.getGestureSource();
				String previousXY = source.getCenterX() + ","+source.getCenterY(); // store x and y coordinates for source
				int prevIndex = -1; // store index of source for imgPaths arraylist and xyCoordinates arraylist
				for(int i = 0; i<xyCoordinates.size();i++) {
					if(xyCoordinates.get(i).equals(previousXY)) {
						prevIndex = i;
					}
				}
				imgPaths.remove(prevIndex);
				xyCoordinates.remove(prevIndex);
				radii.remove(prevIndex);
				spane.getChildren().remove(source);
				event.consume();
			}
		});
	}
	
	/**
	 * Method that is called when a node is moved on the stackpane. This method updates the xyCoordinates, imgPaths, and radii ArrayLists.
	 * These ArrayLists are used to save the image, position, and size for each node in the garden. 
	 * @param c - the circle object that us being moved.
	 */
	protected void move(Circle c) {
		String previousXY = c.getCenterX() + ","+c.getCenterY(); // store x and y coordinates for source
		int prevIndex = -1; // store index of source for imgPaths arraylist and xyCoordinates arraylist
		for(int i = 0; i<xyCoordinates.size();i++) {
			if(xyCoordinates.get(i).equals(previousXY)) {
				prevIndex = i;
			}
		}
		xyCoordinates.remove(prevIndex);
		radii.remove(prevIndex);
		String path = imgPaths.get(prevIndex);
		imgPaths.remove(prevIndex);
		imgPaths.add(path);
		
		
	}


	/**
	 * Creates a circle with a plant image and drop it at a given x and y location on the stackpane. The size of the circle is resized 
	 * base on the width of the plant and the width of the garden. This method is only called when the image is being dragged from the listview.
	 * @param image - image that is on the dragboard
	 * @param spane - stackpane which is the canvas for drag and drop
	 * @param width - width of the given plant 
	 */
	protected void createCopy(Image image, StackPane spane,double width) {
		double factor = 600/gardenWidth;
    	Circle c = new Circle((width*factor)/2);
    	c.setFill(new ImagePattern(image));
    	spane.getChildren().add(c);
    	xyCoordinates.add(controller.getX()+","+controller.getY());
    	radii.add((width*factor)/2);
    	c.setTranslateX(controller.getX());
		c.setTranslateY(controller.getY());
		c.setCenterX(controller.getX());
		c.setCenterY(controller.getY());
		source2(c);
	}
	/**
	 * Creates a circle with a plant image with a radius that is equal to the parameter. The circle with dropped at a given x and y location on
	 * the stackpane. This method is only called if the circle is already on the stackpane. 
	 * @param image - image of plant 
	 * @param spane - stackpane which is the canvas for drag and drop
	 * @param radius - radius of the current circle
	 */
	protected void createCopy2(Image image, StackPane spane, double radius) {
		Circle c = new Circle(radius);
    	c.setFill(new ImagePattern(image));
    	spane.getChildren().add(c);
    	xyCoordinates.add(controller.getX()+","+controller.getY());
    	radii.add(radius);
    	c.setTranslateX(controller.getX());
		c.setTranslateY(controller.getY());
		c.setCenterX(controller.getX());
		c.setCenterY(controller.getY());
		source2(c);
	}
	
	

	public static void main(String[] args) {
        launch();
    }

}
