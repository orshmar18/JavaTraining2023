package javaa.key;

public class SimpleIKey implements IKey {
    protected int key;

    public SimpleIKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key +"";
    }
}