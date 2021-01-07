package ie.gmit.sw;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarLoader {
    private List<String> packages = new ArrayList<>();

    // Return the jar file classes from this method
    public ArrayList<String> process(File jarFile) {
        return getJar(jarFile);
    }

    // Get the jar file, called in process for abstraction
    private ArrayList<String> getJar(File jarFile) {
        try {
            JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

            JarEntry next = in.getNextJarEntry();

            while (next != null) {
                if (next.getName().endsWith(".class")) {
                    String name = next.getName().replaceAll("/", "\\.");
                    name = name.replaceAll(".class", "");

                    if (!name.contains("$")) {
                        name.substring(0, name.length() - ".class".length());

                        Class cls = Class.forName(name);

                        packages.add(name);
                    }
                    System.out.println(packages);
                }
                next = in.getNextJarEntry();
            }
            return (ArrayList<String>) packages;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (ArrayList<String>) packages;
    }
}
