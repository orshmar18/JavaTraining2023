package Enums;

public enum EEncryptionTypes {
    UP_ENCRYPTION('u'),
    MULTIPLY_ENCRYPTION('m'),
    DOUBLE_ENCRYPTION('d'),
    REPEAT_ENCRYPTION('r'),
    NULL('N');
    private final char c;

    EEncryptionTypes(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static EEncryptionTypes getByChar(char c){
        for (EEncryptionTypes encryptionTypes : values()){
            if(encryptionTypes.getChar() == c){
                return encryptionTypes;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (EEncryptionTypes encryptionTypes : values()){
            if(encryptionTypes.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
