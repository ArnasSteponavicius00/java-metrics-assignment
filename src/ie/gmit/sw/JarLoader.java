package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarLoader {
    public void getJar(File jarFile) {
        try {
            JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

            JarEntry next = in.getNextJarEntry();

            while (next != null) {
                if (next.getName().endsWith(".class")) {
                    String name = next.getName().replaceAll("/", "\\.");
                    name = name.replaceAll(".class", "");

                    if (!name.contains("$"))
                        name.substring(0, name.length() - ".class".length());

                    System.out.println(name);
                }
                next = in.getNextJarEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
