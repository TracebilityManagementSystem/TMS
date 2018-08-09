package application;

import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.iterator.ExtendedIterator;

import java.io.*;
import java.util.Iterator;
import java.util.Stack;

import tree.*;


public class SemanticWeb {

	private OntModel model;
	Stack<TreeNode<String>> treeNodeStack;
	TreeNode<String> root;
    
    public SemanticWeb(String nameSpace, String inputFileName) {
    	model = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM );
    	
        InputStream in = FileManager.get().open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException( "File: " + inputFileName + " not found");
        }
        model.read(in, "");
        
        treeNodeStack=new Stack<TreeNode<String>>();
        root = new TreeNode<String>("thing");
        treeNodeStack.push(root);
        createTree();
	}
    
    public TreeNode<String> getRoot() {
    	return this.root;
    }

    
    private void createTree(){
    	Stack<OntResource> classStack=new Stack<OntResource>();    	
    	ExtendedIterator<OntClass> Oclass=model.listHierarchyRootClasses();
    	while(Oclass.hasNext()){
    		OntClass currentClass=Oclass.next();
    		dfs(classStack, currentClass);
    	}
    }
    
    private void dfs(Stack<OntResource> classStack, OntResource startpoint){
    	classStack.push(startpoint);
    	TreeNode<String> child = treeNodeStack.peek().addChild(startpoint.getLocalName());
    	treeNodeStack.push(child);
    	
    	while(!classStack.isEmpty()){
    		OntResource v=(OntResource) classStack.peek();
    		OntResource u=firstUnmarkedAdj(v);
    		if(u==null) {
    	        ExtendedIterator<? extends OntResource> iterLeaf=v.asClass().listInstances();
    	        while(iterLeaf.hasNext()){
    	        	OntResource leaf = iterLeaf.next();
    	        	treeNodeStack.peek().addChild(leaf.asIndividual().getLocalName());
    	        }
    	        classStack.pop();
    	        treeNodeStack.pop();
    		}
    		else{
        			u.addComment(v.getLocalName(), v.getLocalName());
        			classStack.push(u);
        			child = treeNodeStack.peek().addChild(u.getLocalName());
        			treeNodeStack.push(child);
    			}
    	}
    }
    
    private OntResource firstUnmarkedAdj(OntResource v) {
		Iterator<? extends OntResource> iter=v.asClass().listSubClasses();
        while(iter.hasNext()){
        	OntResource cls=iter.next();
        	if(cls.getComment(v.getLocalName())==null){
        		return cls;
        	}
        }
        return null;
	}

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for (TreeNode<String> node : root) {
			String indent = createIndent(node.getLevel());
			sb.append(indent + node.data + '\n');
		}
    	return sb.toString();
    }
    
	private String createIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append('\t');
		}
		return sb.toString();
	}
   
}
