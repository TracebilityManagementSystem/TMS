package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

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
}
