package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Stack;

import application.SemanticWeb;
import tree.*;

public class MainController {

    @FXML
    private TreeView<String> treeView;
    @FXML
    private ChoiceBox<String> source;
    @FXML
    private ChoiceBox<String> target;
    

    ObservableList<String> list = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
    	
    	list.addAll("Requirement A", "Class B", "Test Case T");
    	source.getItems().addAll(list);
    	target.getItems().addAll(list);
    	
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
}














