import java.util.Arrays;

public class ComplexKey implements Key{

    Key[] complex = new Key[2];

    public ComplexKey() {
        this.complex[0] = new SimpleKey(0);
        this.complex[1] = new SimpleKey(0);
    }

    public ComplexKey(Key a,Key b) {
        this.complex[0] = a;
        this.complex[1] = b;
    }


    public Key[] getComplex() {
        return complex;
    }

    public void setComplex(Key a,Key b) {
        this.complex[0] = a;
        this.complex[1] = b;
    }

    @Override
    public String toString() {
        return  Arrays.toString(complex)+"";
    }
}
