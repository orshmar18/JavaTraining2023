package key;

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

    public void setComplex(IKey firstIKey, IKey secondIKey) {
        this.complexIKey[0] = firstIKey;
        this.complexIKey[1] = secondIKey;
    }

    @Override
    public String toString() {
        return Arrays.toString(complexIKey) + "";
    }
}
