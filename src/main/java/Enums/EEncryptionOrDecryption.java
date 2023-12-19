package Enums;

public enum EEncryptionOrDecryption {
  ENCRYPTION('e'),
  DECRYPTION('d'),
    NULL('N');
    private final char c;

    EEncryptionOrDecryption(char c) {this.c = c;}

    public char getChar(){return this.c;}

    public static EEncryptionOrDecryption getByChar(char c){
        for (EEncryptionOrDecryption encryptionOrDecryption : values()){
            if(encryptionOrDecryption.getChar() == c){
                return encryptionOrDecryption;
            }
        }
        return NULL;
    }

    public static boolean contains(char type){
        for (EEncryptionOrDecryption encryptionOrDecryption : values()){
            if(encryptionOrDecryption.getChar() == type){
                return true;
            }
        }
        return false;
    }
}
