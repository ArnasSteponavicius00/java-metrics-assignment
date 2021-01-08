package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Factory Pattern for accessing metrics
 *
 * @author Arnas Steponavicius
 */
public class MetricsFactory {
    private static final MetricsFactory cf = new MetricsFactory();
    private ObservableList<MetricsImpl> model;

    private MetricsFactory() {
        model = FXCollections.observableArrayList();
    }

    /**
     * @return cf - Used to create an instance of the factory
     */
    public static MetricsFactory getInstance() {
        return cf;
    }

    /**
     * @return model - contains the metric data
     */
    ObservableList<MetricsImpl> getMetrics() {
        return model;
    }

    /**
     * @param metric - passed in from JarHandler, add the metric to an ObservableList
     */
    public void add(MetricsImpl metric) {
        model.add(metric);
    }
}