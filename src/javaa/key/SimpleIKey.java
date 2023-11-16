package javaa.key;

public class SimpleIKey<T>{
    protected T key;

    public SimpleIKey(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key +"";
    }
}