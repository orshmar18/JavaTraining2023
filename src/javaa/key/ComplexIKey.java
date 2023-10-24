package javaa.key;

import java.util.Arrays;

public class ComplexIKey implements IKey {
    IKey[] complexIKey = new IKey[2];

    public ComplexIKey() {
        this.complexIKey[0] = new SimpleIKey(0);
        this.complexIKey[1] = new SimpleIKey(0);
    }

    public ComplexIKey(IKey a, IKey b) {
        this.complexIKey[0] = a;
        this.complexIKey[1] = b;
    }

    public IKey[] getComplex() {
        return complexIKey;
    }
    public SimpleIKey getFirst(){
        return (SimpleIKey) this.complexIKey[0];
    }
    public SimpleIKey getSecond(){
        return (SimpleIKey) this.complexIKey[1];
    }

    public void setComplex(IKey firstIKey, IKey secondIKey) {
        this.complexIKey[0] = firstIKey;
        this.complexIKey[1] = secondIKey;
    }

    @Override
    public String toString() {
        return Arrays.toString(complexIKey) + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexIKey that = (ComplexIKey) o;
        return Arrays.equals(complexIKey, that.complexIKey);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(complexIKey);
    }
}
