package javaa.directoryProcessor;

import javaa.key.IKey;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
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
    public void directoryProcess(String[] fileToReadPath, String[] fileToWriteInPath, T key, boolean isEncryption) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int i = 0 ; i < fileToReadPath.length ; i++){
            String readPath = fileToReadPath[i];
            String writePath = fileToWriteInPath[i];
            executorService.execute(()-> fileEncryptor.doEncryptOrDecrypt(readPath,writePath,key,isEncryption));
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
