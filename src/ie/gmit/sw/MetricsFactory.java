package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MetricsFactory {
	private static final MetricsFactory cf = new MetricsFactory();
	private ObservableList<MetricsImpl> model;

	private MetricsFactory() {
		model = FXCollections.observableArrayList();
	}
	
	public static MetricsFactory getInstance() {
		return cf;
	}
	
	public ObservableList<MetricsImpl> getMetrics() { return model; }
}