package application;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.glass.events.MouseEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainController implements Initializable
{

	@FXML
	private Button loadButton;
	@FXML
	private TextField artifact;
	@FXML
	private Text imageName;
	@FXML
    private TextArea description;
	@FXML
    private Button btn1;
	@FXML
    private Button btn2;
	@FXML
    private TreeView<String> treeView;
    @FXML
    private ChoiceBox<String> source;
    @FXML
    private ChoiceBox<String> target;

    private FileChooser fileChooser = new FileChooser();
    private Image readImage;
    private PixelReader pixelReader;
    private Statement sql;
    private Database db; 
    private ResultSet rs;
    
    ObservableList<String> list = FXCollections.observableArrayList();
	private String filePath;

    
    public void eventButton1(){
        String text1 = artifact.getText();
        String text2 = description.getText();
        db.insertArtifact(text1, 1, 1, 1, text1, text2);
        JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
    		   "Successful!", "message", JOptionPane.INFORMATION_MESSAGE); 
    }
    
    
   
	  
    
    public void eventButton2(){
        String text1 = source.getValue();
        String text2 = target.getValue();
        int row=treeView.getSelectionModel().getSelectedIndex();
        String text3 = treeView.getTreeItem(row).getValue();
        db.insertTracelink(0, text1, text2, text3);
        JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
    		   "Successful!", "message", JOptionPane.INFORMATION_MESSAGE); 
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeDB();
		try {
			rs=sql.executeQuery("SELECT Art_uri FROM Artifact");
			  while(rs.next())
		         {
		         
		          String uri=rs.getString(1);
		          list.add(uri);
		          
		         }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//list.addAll("Requirement A", "Class B", "Test Case T");
    	source.getItems().addAll(list);
    	target.getItems().addAll(list);
    	
    	loadButton.setOnAction(
                (final ActionEvent e) -> {
                    File file = fileChooser.showOpenDialog(new Stage());
                    if (file != null) {
                        String path = file.getAbsolutePath();
                        filePath = file.getParent();
                        String fileName = file.getName();
                        readImage = new Image("file:///" + path);
                        pixelReader = readImage.getPixelReader();
                        System.out.println(readImage);
                        System.out.println(filePath);
                        System.out.println(fileName);
                        imageName.setText(filePath+" "+fileName);
                    }
                }
        );
    	
    	TreeItem<String> root = new TreeItem<>("Trace-link");
    	root.setExpanded(true);
    	root.getChildren().add(new TreeItem<String>("re-link"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("overlap"));
    	root.getChildren().get(0).getChildren().get(0).getChildren().add(new TreeItem<String>("Adapt"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("product-related-link"));
    	root.getChildren().get(0).getChildren().get(1).getChildren().add(new TreeItem<String>("Satisfy"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("dependency"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Require-Features-In"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Casual-Dependency-Conformance"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Import"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Developmental"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Export"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Part-Of"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Correspondence"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Usage"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Derrive"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Is-A"));
    	root.getChildren().get(0).getChildren().get(2).getChildren().add(new TreeItem<String>("Has-A"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("generalize"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("contribution"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("rationale"));
    	root.getChildren().get(0).getChildren().add(new TreeItem<String>("process-related-link"));
    	root.getChildren().add(new TreeItem<String>("se-link"));
    	root.getChildren().get(1).getChildren().add(new TreeItem<String>("Implemented-By"));
    	root.getChildren().get(1).getChildren().add(new TreeItem<String>("Tested-By"));
    	root.getChildren().add(new TreeItem<String>("mde-link"));
    	root.getChildren().get(2).getChildren().add(new TreeItem<String>("implicit"));
    	root.getChildren().get(2).getChildren().add(new TreeItem<String>("explicit"));
    	treeView.setRoot(root);
		
	}
	

	
		 private void initializeDB() {
	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tms?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8&useSSL=false", "root", "");
	            db = new Database(conn);
	            sql=conn.createStatement();
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	
    

	 }
}
