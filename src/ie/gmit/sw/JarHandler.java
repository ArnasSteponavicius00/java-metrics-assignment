package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarHandler {
    private ObservableList<MetricsImpl> packages = FXCollections.observableArrayList();

    // Return the jar file classes from this method
    public void process(File jarFile) throws IOException {
        getJar(jarFile);
    }

    // Get the jar file, called in process for abstraction
    private void getJar(File jarFile) throws IOException {
            JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

            JarEntry next = in.getNextJarEntry();
            MetricsFactory mf = MetricsFactory.getInstance();

            while (next != null) {
                if (next.getName().endsWith(".class")) {
                    String name = next.getName().replaceAll("/", "\\.");
                    name = name.replaceAll(".class", "");

                    if (!name.contains("$")) name.substring(0, name.length() -".class".length());

                    try {
                        Class cls = Class.forName(name);

                        String className = cls.getName();
                        String decMethods = cls.getConstructors().toString();
                        boolean isInterface = cls.isInterface();
                        int modifiers = cls.getModifiers();

                        MetricsImpl met = new MetricsImpl(className, decMethods, isInterface, modifiers);

                        mf.add(met);

                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        System.out.println("" + e);
                    }
                }
                next = in.getNextJarEntry();
            }
    }
}
