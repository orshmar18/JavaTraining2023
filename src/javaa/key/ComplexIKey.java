package javaa.key;

import java.util.Arrays;
import java.util.Objects;

public class ComplexIKey<T> implements IKey{
    protected T firstComplex;
    protected T secondComplex;


    public ComplexIKey() {
        this.firstComplex = null;
        this.secondComplex = null;
    }

    public ComplexIKey(T firstComplex, T secondComplex) {
        this.firstComplex = firstComplex;
        this.secondComplex = secondComplex;
    }

    public T getFirst() {
        return this.firstComplex;
    }

    public T getSecond() {
        return this.secondComplex;
    }

    public void setComplex(T firstIKey, T secondIKey) {
        this.firstComplex = firstIKey;
        this.secondComplex = secondIKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexIKey<?> that = (ComplexIKey<?>) o;
        return Objects.equals(firstComplex, that.firstComplex) && Objects.equals(secondComplex, that.secondComplex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstComplex, secondComplex);
    }

    @Override
    public String toString() {
        return "[" + firstComplex+"," + secondComplex+"]";
    }
}
