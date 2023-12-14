package main.java.fileEncryptor;

public interface ByteBufferProcessor<S> {
    byte[] dataProcessor(byte[] fileData, int byteRead, S IKey);
}