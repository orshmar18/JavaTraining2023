package main.java.directoryProcessor;

import main.java.Utils.Pair;
import main.java.fileEncryptor.ByteBufferProcessor;
import main.java.key.IKey;
import main.java.typesOfEncryption.IEncryptionAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncDirectoryProcessor<T extends IKey> extends DirectoryProcessor<T> {

    private static final int MINUTES_LIMIT_TIME = 5;
    private static final Logger LOGGER = LogManager.getLogger(AsyncDirectoryProcessor.class);

    public AsyncDirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm){super(encryptionAlgorithm);}

    @Override
    public void directoryProcess(Pair<String,String>[] pathsPairs, T key, ByteBufferProcessor<T> processorData) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(Pair<String,String> pathPair : pathsPairs){
            String readPath =  pathPair.getFirst();
            String writePath =   pathPair.getSecond();
            executorService.execute(()-> fileEncryptor.doEncryptOrDecrypt(readPath,writePath,key,processorData));
        }
        try{
            executorService.shutdown();
            boolean wasTimesOut = executorService.awaitTermination(MINUTES_LIMIT_TIME, TimeUnit.MINUTES);
            if (!wasTimesOut)
                LOGGER.error("The encryption/decryption threads haven't finished in your defined timeframe, and therefore the encryption/decryption wasn't successful");
        }catch (Exception e){
            LOGGER.error("The encryption/decryption threads haven't finished , the process took more then your limit: "+MINUTES_LIMIT_TIME);
        }finally {
            executorService.shutdownNow();
        }
    }
}
