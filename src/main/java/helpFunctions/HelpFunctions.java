package helpFunctions;

import Enums.EEncryptionOrDecryption;
import typesOfEncryption.*;
import Enums.EDoubleEncryptionTypes;
import Enums.EEncryptionTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class HelpFunctions {

    public static String removeFileExtension(String filePath) {
        File file = new File(filePath);
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf(".");

        if (lastDotIndex != -1) {
            return filePath.substring(0, filePath.length() - (fileName.length() - lastDotIndex));
        } else {
            return filePath;
        }
    }

    public static int[] AllPrime(int limit) { //
        boolean[] isPrime = new boolean[limit + 1];
        isPrime[2] = true;
        for (int i = 3; i <= limit; i += 2) {
            isPrime[i] = true;
        }
        for (int p = 3; p * p <= limit; p += 2) {
            if (isPrime[p]) {
                for (int i = p * p; i <= limit; i += p * 2) {
                    isPrime[i] = false;
                }
            }
        }
        int count = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i])
                count++;
        }
        int[] primes = new int[count];
        int index = 0;
        for (int i = 2; i <= limit; i++) {
            if (isPrime[i]) {
                primes[index] = i;
                index++;
            }
        }
        return primes;
    }

    public static boolean isValidPath(String path) {
        Path filePath = Paths.get(path);
        return filePath.isAbsolute();
    }

    public static boolean isFileExists(String path) {
        Path filePath = Paths.get(path);
        return filePath.toFile().exists();
    }

public static boolean checksIfPathIsFileOrDirectory(String filePath){
        Path path = Paths.get(filePath);
        if(Files.isDirectory(path))
            return true;
        else {
            if(Files.isRegularFile(path))
                return false;
        }
        return false;
    }

    public static Path duplicateDirectory(Path originalDirectoryPath,String addToPath){
        Path duplicateDirectory = Paths.get(originalDirectoryPath+addToPath);
        try {
            if (Files.exists(duplicateDirectory)) {
                // If it exists, delete its contents
                Files.walkFileTree(duplicateDirectory, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Files.delete(file);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }

            // Copy the source directory to the destination directory
            Files.walkFileTree(originalDirectoryPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = duplicateDirectory.resolve(originalDirectoryPath.relativize(dir));
                    Files.copy(dir, targetDir, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.copy(file, duplicateDirectory.resolve(originalDirectoryPath.relativize(file)), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
                    return FileVisitResult.CONTINUE;
                }
            });
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return duplicateDirectory;
    }

}
