package javaa.EncryptionTypes;

public enum EncryptionTypes {
    UP_ENCRYPTION('u'),
    MULTIPLY_ENCRYPTION('m'),
    DOUBLE_ENCRYPTION('d'),
    REPEAT_ENCRYPTION('r'),
    NULL('N');
    private final char c;

    EncryptionTypes(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static EncryptionTypes getByChar(char c){
        for (EncryptionTypes encryptionTypes : values()){
            if(encryptionTypes.getChar() == c){
                return encryptionTypes;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (EncryptionTypes encryptionTypes : values()){
            if(encryptionTypes.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
