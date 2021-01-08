package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

import java.nio.file.Paths;

/**
 * Database that stores the processed jar metrics
 *
 * @author Arnas Steponavicius
 */
public class Database {
    private EmbeddedStorageManager db = null;
    private ObservableList<MetricsImpl> root = FXCollections.observableArrayList();

    /**
     * Stores the data in a database
     */
    public void go() {
        root = MetricsFactory.getInstance().getMetrics();
        db = EmbeddedStorage.start(root.toArray(), Paths.get("./data"));
        db.storeRoot();
        System.out.println("[Saved to MicrostreamDB]");
        db.shutdown();
    }

    /**
     * Queries for all the classes
     */
    public void query() {
        System.out.println("\n[Query] Show all Classes");
        root.stream().forEach(System.out::println);
    }

    /**
     * Queries for all the classes and checks if they are an Interface or not
     */
    public void queryIsInterfaces() {
        System.out.println("\n[Query] Show all Interfaces");
        root.stream().forEach((s) -> {
            System.out.println("Class: " + s.getClassName() + "| [Interface]: " + s.getIsInterface());
        });
    }

    /**
     * Return a query for the metrics of each class
     */
    public void queryAllMetrics() {
        System.out.println("\n[Query] Show all Metrics");
        root.stream().forEach((s) -> {
            System.out.println("\nClass: " + s.getClassName() + "\n[Declared Methods]: " + s.getDeclaredMethod() + "\n[Is Interface]: " + s.getIsInterface() +
                    "\n[Is Record]: " + s.getIsRecord() + "\n[Modifiers]: " + s.getModifiers());
        });
    }

}
