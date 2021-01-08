package ie.gmit.sw;

public interface Metrics {
    public String getClassName();
    public void setClassName(String className);

    public String getDeclaredMethod();
    public void setDeclaredMethod(String packageName);

    public boolean getIsInterface();
    public void setIsInterface(boolean isInterface);

    public boolean getIsRecord();
    public void setRecord(boolean record);

    public int getModifiers();
    public void setModifiers(int modifiers);
}
