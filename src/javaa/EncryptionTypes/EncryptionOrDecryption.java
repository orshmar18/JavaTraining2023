package javaa.EncryptionTypes;

public enum EncryptionOrDecryption {
  ENCRYPTION('e'),
  DECRYPTION('d'),
    NULL('N');
    private final char c;

    EncryptionOrDecryption(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static EncryptionOrDecryption getByChar(char c){
        for (EncryptionOrDecryption encryptionOrDecryption : values()){
            if(encryptionOrDecryption.getChar() == c){
                return encryptionOrDecryption;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (EncryptionOrDecryption encryptionOrDecryption : values()){
            if(encryptionOrDecryption.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
