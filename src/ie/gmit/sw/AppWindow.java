package ie.gmit.sw;

import java.io.*;
import java.time.*;
import java.time.format.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

public class AppWindow extends Application {
	private ObservableList<Customer> customers; //The Model - a list of observers.
	private TableView<Customer> tv; //The View - a composite of GUI components

	private ObservableList<Classes> classes;
	private TableView<Classes> classestv;

	private TextField txtFile;

	private List<String> packages = new ArrayList<>();

	@Override
	public void start(Stage stage) throws Exception {
		CustomerFactory cf = CustomerFactory.getInstance();
		customers = cf.getCustomers();

		ClassFactory clf = ClassFactory.getInstance();
		classes = clf.getClasses();

		stage.setTitle("GMIT - B.Sc. in Computing (Software Development)");
		stage.setWidth(800);
		stage.setHeight(600);

		stage.setOnCloseRequest((e) -> System.exit(0));


		VBox box = new VBox();
		box.setPadding(new Insets(10));
		box.setSpacing(8);

		//**Strategy Pattern**. Configure the Context with a Concrete Strategy
		Scene scene = new Scene(box); 
		stage.setScene(scene);

		ToolBar toolBar = new ToolBar(); //A ToolBar is a composite node for Buttons (leaf nodes)

		Button btnQuit = new Button("Quit"); //A Leaf node
		btnQuit.setOnAction(e -> System.exit(0)); //Plant an observer on the button
		toolBar.getItems().add(btnQuit); //Add to the parent node and build the tree
		
		Button btnAdd = new Button("Add"); //A Leaf node
		btnAdd.setOnAction(e -> { //Plant an observer on the button

			customers.add(
					new Customer("Sideshow Bob", 
							LocalDateTime.of(1970, 7, 7, 0, 0), 
							Status.Premium, 
							new Image(new File("./images/bob.png").toURI().toString()))
			);
		});

		toolBar.getItems().add(btnAdd); //Add to the parent node and build the tree
		
		
		Button btnDelete = new Button("Delete"); //A Leaf node
		btnDelete.setOnAction(e -> { //Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and
			 * remove it from the Model (ObservableList). Do not try to update
			 * the view directly.
			 */
			
			Customer c = tv.getSelectionModel().getSelectedItem();
			customers.remove(c);

			Classes cls = classestv.getSelectionModel().getSelectedItem();
			classes.remove(cls);
		});
		toolBar.getItems().add(btnDelete); //Add to the parent node and build the tree


		Button btnSelect = new Button("Select"); //A Leaf node
		btnSelect.setOnAction(e -> { //Plant an observer on the button


			Classes cls = classestv.getSelectionModel().getSelectedItem();
			try {
//				String className = cls.toString();
//				className.replace("/", ".");

				//System.out.println(className);

				Class cs = Class.forName("org.apache.commons.text.AlphabetConverter");

				System.out.println(cs.getDeclaredMethods());
			} catch (ClassNotFoundException classNotFoundException) {
				classNotFoundException.printStackTrace();
			}

		});
		toolBar.getItems().add(btnSelect); //Add to the parent node and build the tree
		
		/*
		 * Add all the sub trees of nodes to the parent node and build the tree
		 */
		box.getChildren().add(getFileChooserPane(stage)); //Add the sub tree to the main tree

		box.getChildren().add(getClassView());
		box.getChildren().add(getTableView());

		box.getChildren().add(toolBar); //Add the sub tree to the main tree

		// Display the window
		stage.show();
		stage.centerOnScreen();
	}

	private TitledPane getFileChooserPane(Stage stage) {
		VBox panel = new VBox(); //** A concrete strategy ***

		txtFile = new TextField(); //A leaf node

		FileChooser fc = new FileChooser(); //A leaf node
		fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR Files", "*.jar"));

		Button btnOpen = new Button("Select File"); //A leaf node
		btnOpen.setOnAction(e -> { //Plant an observer on the button
			File f = fc.showOpenDialog(stage);
			txtFile.setText(f.getAbsolutePath());
		});

		// Process the Jar File
		Button btnProcess = new Button("Process"); //A leaf node
		btnProcess.setOnAction(e -> { //Plant an observer on the button
			File f = new File(txtFile.getText());
			ClassFactory clf = ClassFactory.getInstance();
			JarLoader jl = new JarLoader();

			System.out.println("[INFO] Processing file " + f.getName());
			packages = jl.process(f);

			System.out.println(packages);

			// Loop over the arraylist of packages
			for(String names : packages) {
				classes.add(
						new Classes(names)
				);
			}
		});
		
		ToolBar tb = new ToolBar(); //A composite node
		tb.getItems().add(btnOpen); //Add to the parent node and build a sub tree
		tb.getItems().add(btnProcess); //Add to the parent node and build a sub tree

		panel.getChildren().add(txtFile); //Add to the parent node and build a sub tree
		panel.getChildren().add(tb); //Add to the parent node and build a sub tree

		TitledPane tp = new TitledPane("Select File to Process", panel); //Add to the parent node and build a sub tree
		tp.setCollapsible(false);
		return tp;
	}

	private TableView<Classes> getClassView() {
		classestv = new TableView<>(classes); //A TableView is a composite node
		classestv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Stretch columns to fit the window

		TableColumn<Classes, String> name = new TableColumn<>("Class Name");
		name.setCellValueFactory(new Callback<CellDataFeatures<Classes, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Classes, String> p) {
				return new SimpleStringProperty(p.getValue().name());
			}
		});

		//Creates an observable table column from a String field extracted from the Customer class
//		TableColumn<Classes, Number> loc = new TableColumn<>("LOC");
//
//		loc.setCellValueFactory(new Callback<CellDataFeatures<Classes, Number>, ObservableValue<Number>>() {
//			public ObservableValue<Number> call(CellDataFeatures<Classes, Number> p) {
//				return new SimpleIntegerProperty(p.getValue().loc());
//			}
//		});
//
//		TableColumn<Classes, Number> sloc = new TableColumn<>("SLOC");
//
//		sloc.setCellValueFactory(new Callback<CellDataFeatures<Classes, Number>, ObservableValue<Number>>() {
//			public ObservableValue<Number> call(CellDataFeatures<Classes, Number> p) {
//				return new SimpleIntegerProperty(p.getValue().sloc());
//			}
//		});

		classestv.getColumns().add(name);
//		classestv.getColumns().add(loc);
//		classestv.getColumns().add(sloc);

		return classestv;
	}

	private TableView<Customer> getTableView() {
		/*
		 * The next line is **very important**. We configure a View (TableView) with
		 * a Model (ObservableList<Customer>). The Model is observable and will
		 * propagate any changes to it to the View or Views that render it.
		 */
		tv = new TableView<>(customers); //A TableView is a composite node
		tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Stretch columns to fit the window


		/*
		 *  Create a TableColumn from the class Customer that displays some attribute
		 *  as a String. This field is Observable and the method call() will be fired
		 *  when the table is being refreshed or when the model is updated. The instance
		 *  of the interface Callback is implemented as an anonymous inner class and acts
		 *  as a Controller for the table column. Callback is also an example of an Observer
		 *  and the inner class a ConcreteObserver. The method call() is analogous to the
		 *  notify() method in the Observer Pattern.
		 */
		TableColumn<Customer, String> name = new TableColumn<>("Methods");
		name.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				return new SimpleStringProperty(p.getValue().name());
			}
		});

		//Creates an observable table column from a String field extracted from the Customer class
		TableColumn<Customer, String> dob = new TableColumn<>("DOB");
		dob.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				return new SimpleStringProperty(
						DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(p.getValue().dob()));
			}
		});

		//Creates an observable table column from a String field extracted from the Customer class
		TableColumn<Customer, String> status = new TableColumn<>("Status");
		status.setCellValueFactory(new Callback<CellDataFeatures<Customer, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Customer, String> p) {
				return new SimpleStringProperty(p.getValue().status().toString());
			}
		});

		//Creates an observable table column from an Image attribute of the Customer class
		TableColumn<Customer, ImageView> img = new TableColumn<>("Image");
		img.setCellValueFactory(new Callback<CellDataFeatures<Customer, ImageView>, ObservableValue<ImageView>>() {
			public ObservableValue<ImageView> call(CellDataFeatures<Customer, ImageView> p) {
				return new SimpleObjectProperty<>(new ImageView(p.getValue().image()));
			}
		});

		tv.getColumns().add(name); //Add nodes to the tree
		tv.getColumns().add(dob);  //Add nodes to the tree
		tv.getColumns().add(status); //Add nodes to the tree
		tv.getColumns().add(img);  //Add nodes to the tree
		return tv;
	}
}