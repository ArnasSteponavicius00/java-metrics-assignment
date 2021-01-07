package ie.gmit.sw;

public interface Metrics {
    public String getClassName();
    public void setClassName(String className);

    public String getPackageName();
    public void setPackageName(String packageName);

    public boolean getIsInterface();
    public void setIsInterface(boolean isInterface);

    public int getModifiers();
    public void setModifiers(int modifiers);
}
