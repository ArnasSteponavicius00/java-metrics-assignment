package ie.gmit.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 * Processes the JAR file and its metrics.
 *
 * @author Arnas Steponavicius
 */
public class JarHandler {
    private MetricsFactory mf = MetricsFactory.getInstance();

    /**
     * @param jarFile - JAR File passed in for processing its metrics.
     * @throws IOException
     */
    public void process(File jarFile) throws IOException {
        handleJar(jarFile);
    }

    /**
     * @param jarFile - Needs to be processed and metrics to be extracted.
     * @throws IOException
     */
    private void handleJar(File jarFile) throws IOException {
        JarInputStream in = new JarInputStream(new FileInputStream(jarFile));

        JarEntry next = in.getNextJarEntry();

        while (next != null) {
            if (next.getName().endsWith(".class")) {
                String name = next.getName().replaceAll("/", "\\.");
                name = name.replaceAll(".class", "");

                if (!name.contains("$"))
                    name.substring(0, name.length() - ".class".length());

                try {
                    Class<?> cls = Class.forName(name);

                    String className = cls.getName();
                    String decMethods = cls.getConstructors().toString();
                    boolean isInterface = cls.isInterface();
                    boolean isRecord = cls.isRecord();
                    int modifiers = cls.getModifiers();

                    // Create a new metrics object with the processed values
                    MetricsImpl met = new MetricsImpl(className, decMethods, isInterface, isRecord, modifiers);

                    // Add these metrics to the Observable list handled by the MetricsFactory()
                    mf.add(met);

                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    System.out.println("" + e);
                }
            }
            next = in.getNextJarEntry();
        }
    }
}
