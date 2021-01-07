package ie.gmit.sw;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.application.*;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.*;

public class AppWindow extends Application {
	private ObservableList<MetricsImpl> metrics;
	private TableView<MetricsImpl> metricsView;
	private ObservableList<MetricsImpl> metricsImplArr = FXCollections.observableArrayList();
	private Database db = new Database();

	private TextField txtFile;

	@Override
	public void start(Stage stage) throws Exception {
		MetricsFactory mf = MetricsFactory.getInstance();
		metrics = mf.getMetrics();

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
			System.out.println("add button");
		});

		toolBar.getItems().add(btnAdd); //Add to the parent node and build the tree
		
		
		Button btnDelete = new Button("Delete"); //A Leaf node
		btnDelete.setOnAction(e -> { //Plant an observer on the button
			/*
			 * Get the selected Customer object from the View (TableView) and
			 * remove it from the Model (ObservableList). Do not try to update
			 * the view directly.
			 */

			MetricsImpl cls = metricsView.getSelectionModel().getSelectedItem();
			metrics.remove(cls);
		});
		toolBar.getItems().add(btnDelete); //Add to the parent node and build the tree


		Button btnSelect = new Button("Save to Database"); //A Leaf node
		btnSelect.setOnAction(e -> { //Plant an observer on the button
			db.saveToDatabase();

			MetricsImpl cls = metricsView.getSelectionModel().getSelectedItem();
			System.out.println(cls);


		});
		toolBar.getItems().add(btnSelect); //Add to the parent node and build the tree

		Button btnYurt = new Button("Print"); //A Leaf node
		btnYurt.setOnAction(e -> { //Plant an observer on the button
			MetricsImpl cls = metricsView.getSelectionModel().getSelectedItem();
			System.out.println(cls.getClassName());

			db.go();
		});
		toolBar.getItems().add(btnYurt); //Add to the parent node and build the tree
		
		/*
		 * Add all the sub trees of nodes to the parent node and build the tree
		 */
		box.getChildren().add(getFileChooserPane(stage)); //Add the sub tree to the main tree

		box.getChildren().add(getMetricsView());

		//box.getChildren().add(getMetricsView());

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
			MetricsFactory clf = MetricsFactory.getInstance();
			JarHandler handle = new JarHandler();
			File file = new File(txtFile.getText());

			System.out.println("[INFO] Processing Jar File " + file.getName());
			try {
				metricsImplArr = handle.process(file);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}

			// Loop over the arraylist of packages and add to List
			for(MetricsImpl met : metricsImplArr) {
				metrics.add(
						new MetricsImpl(met.getClassName(),
								met.getPackageName(),
								met.getIsInterface(),
								met.getModifiers())
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

	/**
	 * Method that returns the Table View of the classes inside the JAR File
	 *
	 * @return Table of classes in the processed JAR file
	 */
	private TableView<MetricsImpl> getMetricsView() {
		metricsView = new TableView<>(metrics); //A TableView is a composite node
		metricsView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); //Stretch columns to fit the window

		TableColumn<MetricsImpl, String> name = new TableColumn<>("Class Name");
		name.setCellValueFactory(new Callback<CellDataFeatures<MetricsImpl, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<MetricsImpl, String> p) {
				return new SimpleStringProperty(p.getValue().getClassName());
			}
		});

		TableColumn<MetricsImpl, String> packageName = new TableColumn<>("Package Name");
		packageName.setCellValueFactory(new Callback<CellDataFeatures<MetricsImpl, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<MetricsImpl, String> p) {
				return new SimpleStringProperty(p.getValue().getPackageName());
			}
		});

		TableColumn<MetricsImpl, Boolean> isInterface = new TableColumn<>("Is Interface");
		isInterface.setCellValueFactory(new Callback<CellDataFeatures<MetricsImpl, Boolean>, ObservableValue<Boolean>>() {
			public ObservableValue<Boolean> call(CellDataFeatures<MetricsImpl, Boolean> p) {
				return new SimpleBooleanProperty(p.getValue().getIsInterface());
			}
		});

		TableColumn<MetricsImpl, Number> modifiers = new TableColumn<>("Modifiers");
		modifiers.setCellValueFactory(new Callback<CellDataFeatures<MetricsImpl, Number>, ObservableValue<Number>>() {
			public ObservableValue<Number> call(CellDataFeatures<MetricsImpl, Number> p) {
				return new SimpleIntegerProperty(p.getValue().getModifiers());
			}
		});

		metricsView.getColumns().add(name);
		metricsView.getColumns().add(packageName);
		metricsView.getColumns().add(isInterface);
		metricsView.getColumns().add(modifiers);

		return metricsView;
	}
}