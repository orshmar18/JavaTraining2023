package javaa.directoryProcessor;

import javaa.typesOfEncryption.IEncryptionAlgorithm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncDirectoryProcessor<T> extends DirectoryProcessor<T> {

    public AsyncDirectoryProcessor(IEncryptionAlgorithm<T> encryptionAlgorithm){super(encryptionAlgorithm);}

    @Override
    public void directoryProcess(String[] fileToReadPath, String[] fileToWriteInPath, T key, boolean isEncryption) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for(int i = 0 ; i < fileToReadPath.length ; i++){
            final int index = i;
            executorService.execute(()-> doEncryptOrDecrypt(fileToReadPath[index],fileToWriteInPath[index],key,isEncryption));
        }
        try{
            executorService.shutdown();
            boolean timeout = executorService.awaitTermination(5, TimeUnit.MINUTES);
            if (!timeout)
                System.out.println("The thread was more then 1 minutes");
        }catch (Exception e){
            System.out.println("The thread was more then 1 minutes");
        }finally {
            executorService.shutdownNow();
        }
    }
}
