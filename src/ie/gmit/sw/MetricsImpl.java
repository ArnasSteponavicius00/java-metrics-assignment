package ie.gmit.sw;


/**
 * Implements the Metrics Interface, used to get and set the metric values
 * @author Arnas Steponavicius
 *
 */
public class MetricsImpl implements Metrics {
    private String className;
    private String decMethod;
    private boolean isInterface;
    private boolean isRecord;
    private int modifiers;

    public MetricsImpl() {
    }

    public MetricsImpl(String className, String decMethod, boolean isInterface, boolean isRecord, int modifiers) {
        this.className = className;
        this.decMethod = decMethod;
        this.isInterface = isInterface;
        this.isRecord = isRecord;
        this.modifiers = modifiers;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDeclaredMethod() {
        return decMethod;
    }

    public void setDeclaredMethod(String decMethod) {
        this.decMethod = decMethod;
    }

    public boolean getIsInterface() {
        return isInterface;
    }

    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public boolean getIsRecord() {
        return isRecord;
    }

    @Override
    public void setRecord(boolean isRecord) {
        this.isRecord = isRecord;
    }
}