package ie.gmit.sw;

/*
 *  Customer is a record or a read-only bean class with a constructor that 
 *  matches the set of parameters in the record signature and a suite of 
 *  accessor methods. The default implementation of the methods equals() and 
 *  hashCode() aggregate all the attributes of the record.
 */

public class MetricsImpl implements Metrics{
    private String className;
    private String packageName;
    private boolean isInterface;
    private int modifiers;

    public MetricsImpl() {
    }

    public MetricsImpl(String className, String packageName, boolean isInterface, int modifiers) {
        this.className = className;
        this.packageName = packageName;
        this.isInterface = isInterface;
        this.modifiers = modifiers;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
}