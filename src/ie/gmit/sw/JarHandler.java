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
    public ObservableList<MetricsImpl> process(File jarFile) throws IOException {
        return getJar(jarFile);
    }

    // Get the jar file, called in process for abstraction
    private ObservableList<MetricsImpl> getJar(File jarFile) throws IOException {
            JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

            JarEntry next = in.getNextJarEntry();

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

                        System.out.println(modifiers);

                        MetricsImpl met = new MetricsImpl(className, decMethods, isInterface, modifiers);

                        packages.add(met);

                    } catch (ClassNotFoundException | NoClassDefFoundError e) {
                        System.out.println("" + e);
                    }
                }
                next = in.getNextJarEntry();
            }
            return packages;
    }
}
