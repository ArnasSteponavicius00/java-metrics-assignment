package ie.gmit.sw;

/*
 * CustomerFactory is a singleton factory that creates a model (in this case
 * it is hard-wired as a set of Customer objects) that is returned by the
 * factory method. The domain model is returned as an ObservableList (a list of
 * Observers) that a Subject can be configured with. In this case, the Subject
 * will be a TableView. 
 * 
 * In this application, the ObservableList plays the role of the **Model** in 
 * the MVC framework. If we want to perform CRUD operations on the rows of
 * Customer objects displayed in the TableView, we do so by changing the elements
 * in the ObservableList, i.e. we update the model, not the view. 
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassFactory {
	private static final ClassFactory cf = new ClassFactory();
	private ObservableList<Classes> model;

	private ClassFactory() {
		model = FXCollections.observableArrayList();
	}
	
	public static ClassFactory getInstance() {
		return cf;
	}
	
	public ObservableList<Classes> getClasses() {
		/* This is the model that the ListView will use. The factory method
		 * observableArrayList() creates an ObservableList that automatically 
		 * observed by the ListView. Any changes that occur inside the 
		 * ObservableList will be automatically shown in the ListView. The
		 * interface ObservableList extends java.util.List
		 */
		return model;
	}
}