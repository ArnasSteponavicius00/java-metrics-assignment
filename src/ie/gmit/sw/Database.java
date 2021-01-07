package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private EmbeddedStorageManager db = null;
    private ObservableList<MetricsImpl> root = FXCollections.observableArrayList();

    public void go() {
        root = MetricsFactory.getInstance().getMetrics();
        db = EmbeddedStorage.start(root.toArray(), Paths.get("./data"));
        query();
        db.shutdown();
    }

    public void saveToDatabase() {
        root = MetricsFactory.getInstance().getMetrics();
        db.storeRoot();
        db.shutdown();
    }

    private void query() {
        System.out.println("\n[Query] Show all Classes");
        root.stream()
                .forEach(System.out::println);
    }

}
