package javaa.helpFunctions;

import javaa.typesOfEncryption.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class HelpFunctions {
    static final char ENCRYPTION = 'e';
    static final char DECRYPTION = 'd';
    static final char UPENCRYPTION = 'u';
    static final char MULTIPLYENCRYPTION = 'm';
    static final char DOUBLEENCRYPTION = 'd';
    static final char REPEATENCRYPTION = 'r';

    static final char UPMULTIPLY = '1';
    static final char MULTIPLYUP = '2';
    static final char UPUP = '3';
    static final char MULTIPLYMULTIPLY = '4';
    static Scanner scan = new Scanner(System.in);

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

    public static String userInputFilePath() {
        String filePath;
        System.out.println("Enter The Path Of The File : ");
        filePath = scan.next();
        return filePath;
    }

    public static char UserInputChoice() {
        char choice;
        System.out.println("Hello User");
        System.out.println("Choose Between :\n" + ENCRYPTION + ".Encryption\n" + DECRYPTION + ".Decryption");
        do {
            choice = scan.next().charAt(0);
            if (choice != ENCRYPTION && choice != DECRYPTION)
                System.out.println("You Have To Choose " + ENCRYPTION + " or " + DECRYPTION + "\nPlease Try Again");
        } while (choice != ENCRYPTION && choice != DECRYPTION);
        return choice;
    }


    public static char UserInputTypeOfEncryption() {
        char choice;
        System.out.println("Choose Between :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n" + REPEATENCRYPTION + ".RepeatEncryption\n");
        do {
            choice = scan.next().charAt(0);
            if (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION)
                System.out.println("You Have To Choose " + UPENCRYPTION + " or " + MULTIPLYENCRYPTION + " or " + DOUBLEENCRYPTION + " or " + REPEATENCRYPTION + "\nPlease Try Again");
        } while (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION);
        return choice;
    }

    public static IEncryptionAlgorithm TypeOfEncryption(char choiceTypeOfEncryption) {
        switch (choiceTypeOfEncryption) {
            case UPENCRYPTION:
                return new ShiftUpIEncryption();
            case MULTIPLYENCRYPTION:
                return new ShiftMultiplyIEncryption();
            case DOUBLEENCRYPTION:
                return TypeOfDoubleEncryption();
            case REPEATENCRYPTION:
                return TypeOfRepeatEncryption();
        }
        return null;
    }

    public static IEncryptionAlgorithm TypeOfDoubleEncryption() {
        System.out.println("Choose Between :\n" + UPMULTIPLY + ".ShiftUp And Then ShiftMultiply\n" + MULTIPLYUP + ".ShiftMultiply And Then ShiftUp\n" + UPUP + ".ShiftUp And Then ShiftUp\n" + MULTIPLYMULTIPLY + ".ShiftMultiply And Then ShiftMultiply\n");
        char doubleChoice = '0';
        do {
            doubleChoice = scan.next().charAt(0);
            switch (doubleChoice) {
                case UPMULTIPLY:
                    return new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftMultiplyIEncryption());
                case MULTIPLYUP:
                    return new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftUpIEncryption());
                case UPUP:
                    return new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftUpIEncryption());
                case MULTIPLYMULTIPLY:
                    return new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption());
                default:
                    System.out.println("You Have To Choose Between :\n" + UPMULTIPLY + " or " + MULTIPLYUP + " or " + UPUP + " or " + MULTIPLYMULTIPLY);
            }
        } while (doubleChoice != UPMULTIPLY && doubleChoice != MULTIPLYUP && doubleChoice != UPUP && doubleChoice != MULTIPLYMULTIPLY);
        return null;
    }


    public static IEncryptionAlgorithm TypeOfRepeatEncryption() {
        System.out.print("Enter The Number Of Times You Want To Perform This Action : ");
        int n = scan.nextInt();
        char repeatChoice ;
        System.out.println("Choose Action To Perform :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n");
        do {
            repeatChoice = scan.next().charAt(0);
            switch (repeatChoice) {
                case UPENCRYPTION:
                    return new RepeatIEncryption(new ShiftUpIEncryption(), n);
                case MULTIPLYENCRYPTION:
                    return new RepeatIEncryption(new ShiftMultiplyIEncryption(), n);
                case DOUBLEENCRYPTION:
                    return new RepeatIEncryption(TypeOfDoubleEncryption(), n);
                default:
                    System.out.println("You Have To Choose " + UPENCRYPTION + " or " + MULTIPLYENCRYPTION + " or " + DOUBLEENCRYPTION + "\nPlease Try Again");
            }
        } while (repeatChoice != UPENCRYPTION && repeatChoice != MULTIPLYENCRYPTION && repeatChoice != DOUBLEENCRYPTION);
        return null;
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
