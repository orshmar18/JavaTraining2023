package main.java.exception;

public class InvalidEncryptionKeyException extends Exception {
    public InvalidEncryptionKeyException(String message) {
        super(message);
    }
}
