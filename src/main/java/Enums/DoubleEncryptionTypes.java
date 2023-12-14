package main.java.Enums;

public enum DoubleEncryptionTypes {
    UP_MULTIPLY_ENCRYPTION('1'),
    MULTIPLY_UP_ENCRYPTION('2'),
    UP_UP_ENCRYPTION('3'),
    MULTIPLY_MULTIPLY_ENCRYPTION('4'),
    NULL('N');
    private final char c;

    DoubleEncryptionTypes(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static DoubleEncryptionTypes getByChar(char c){
        for (DoubleEncryptionTypes doubleEncryptionTypes : values()){
            if(doubleEncryptionTypes.getChar() == c){
                return doubleEncryptionTypes;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (DoubleEncryptionTypes doubleEncryptionTypes : values()){
            if(doubleEncryptionTypes.getChar() == type){
                return true;
            }
        }
        return false;
    }
}

