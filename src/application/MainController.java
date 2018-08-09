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

import java.util.Stack;

import application.SemanticWeb;
import tree.*;

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
        String title = artifact.getText();
        String description = description.getText();
        db.insertArtifact(title, 1, 1, 1, title, description);
        JOptionPane.showMessageDialog(new JFrame().getContentPane(), 
    		   "Successful!", "message", JOptionPane.INFORMATION_MESSAGE); 
    }
    
    
   
	  
    
    public void eventButton2(){
        String source = source.getValue();
        String target = target.getValue();
        int row=treeView.getSelectionModel().getSelectedIndex();
        String linkType = treeView.getTreeItem(row).getValue();
        db.insertTracelink(0, source, target, linkType);
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

		SemanticWeb semWeb = new SemanticWeb("http://www.ontorion.com/ontologies/Ontology92f6fe28b5854078a984b0607d68f51e#","TracLink.rdf");
    	TreeNode<String> treeRoot = semWeb.getRoot();
		createTreeView(treeRoot);
    }

	private void createTreeView(TreeNode<String> treeRoot) {
		TreeItem<String> root = new TreeItem<>("Link");
    	Stack<TreeItem<String>> treeStack = new Stack<>();
    	treeStack.push(root);
    	int currentLevel = 0;
    	boolean skipFrist = true;
    	for (TreeNode<String> node : treeRoot) {
    		if (!skipFrist) {
    			if(node.getLevel() <= currentLevel) {
    				for (int i = 0; i <= currentLevel - node.getLevel(); i++) {
    					treeStack.pop();    				
    				}
    			}
    			TreeItem<String> tmp = new TreeItem<>(node.data);
    			treeStack.peek().getChildren().add(tmp);
    			treeStack.push(tmp);
    			currentLevel = node.getLevel();    			
    		}
    		else {
    			skipFrist = false;
    		}
		}
    	root.setExpanded(true);
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














