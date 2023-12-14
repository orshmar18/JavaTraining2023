package main.java.helpFunctions;

import main.java.Enums.EncryptionOrDecryption;
import main.java.typesOfEncryption.*;
import main.java.Enums.DoubleEncryptionTypes;
import main.java.Enums.EncryptionTypes;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

public class HelpFunctions {

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
        System.out.println("Choose Between :\n" + EncryptionOrDecryption.ENCRYPTION.getChar() + ".Encryption\n" + EncryptionOrDecryption.DECRYPTION.getChar() + ".Decryption");
        do {
            choice = scan.next().charAt(0);
            if (!EncryptionOrDecryption.contains(choice))
                System.out.println("You Have To Choose " + EncryptionOrDecryption.ENCRYPTION.getChar() + " or " + EncryptionOrDecryption.DECRYPTION.getChar() + "\nPlease Try Again");
        } while (!EncryptionOrDecryption.contains(choice));
        return choice;
    }


    public static char userInputTypeOfEncryption() {
        char choice;
        System.out.println("Choose Between :\n" + EncryptionTypes.UP_ENCRYPTION.getChar() + ".ShiftUp\n" + EncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply\n" + EncryptionTypes.DOUBLE_ENCRYPTION.getChar() + ".DoubleEncryption\n" + EncryptionTypes.REPEAT_ENCRYPTION.getChar() + ".RepeatEncryption\n");
        do {
            choice = scan.next().charAt(0);
            if (!EncryptionTypes.contains(choice))
                System.out.println("You Have To Choose " + EncryptionTypes.UP_ENCRYPTION.getChar() + " or " + EncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + " or " + EncryptionTypes.DOUBLE_ENCRYPTION.getChar() + " or " + EncryptionTypes.REPEAT_ENCRYPTION.getChar() + "\nPlease Try Again");
        } while (!EncryptionTypes.contains(choice));
        return choice;
    }

    public static IEncryptionAlgorithm typeOfEncryption(char choiceTypeOfEncryption) {
        EncryptionTypes encryptionTypes = EncryptionTypes.getByChar(choiceTypeOfEncryption);
        switch (encryptionTypes) {
            case UP_ENCRYPTION:
                return new ShiftUpIEncryption();
            case MULTIPLY_ENCRYPTION:
                return new ShiftMultiplyIEncryption();
            case DOUBLE_ENCRYPTION:
                return TypeOfDoubleEncryption();
            case REPEAT_ENCRYPTION:
                return TypeOfRepeatEncryption();
        }
        return null;
    }

    public static IEncryptionAlgorithm TypeOfDoubleEncryption() {
        System.out.println("Choose Between :\n" + DoubleEncryptionTypes.UP_MULTIPLY_ENCRYPTION.getChar() + ".ShiftUp And Then ShiftMultiply\n" + DoubleEncryptionTypes.MULTIPLY_UP_ENCRYPTION.getChar() + ".ShiftMultiply And Then ShiftUp\n" + DoubleEncryptionTypes.UP_UP_ENCRYPTION.getChar() + ".ShiftUp And Then ShiftUp\n" + DoubleEncryptionTypes.MULTIPLY_MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply And Then ShiftMultiply\n");
        char doubleChoice;
        do {
            doubleChoice = scan.next().charAt(0);
            DoubleEncryptionTypes doubleEncryptionTypes = DoubleEncryptionTypes.getByChar(doubleChoice);
            switch (doubleEncryptionTypes) {
                case UP_MULTIPLY_ENCRYPTION:
                    return new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftMultiplyIEncryption());
                case MULTIPLY_UP_ENCRYPTION:
                    return new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftUpIEncryption());
                case UP_UP_ENCRYPTION:
                    return new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftUpIEncryption());
                case MULTIPLY_MULTIPLY_ENCRYPTION:
                    return new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption());
                default:
                    System.out.println("You Have To Choose Between :\n" + DoubleEncryptionTypes.UP_MULTIPLY_ENCRYPTION.getChar() + " or " + DoubleEncryptionTypes.MULTIPLY_UP_ENCRYPTION.getChar() + " or " + DoubleEncryptionTypes.UP_UP_ENCRYPTION.getChar() + " or " + DoubleEncryptionTypes.MULTIPLY_MULTIPLY_ENCRYPTION.getChar());
            }
        } while (!DoubleEncryptionTypes.contains(doubleChoice));
        return null;
    }


    public static IEncryptionAlgorithm TypeOfRepeatEncryption() {
        System.out.print("Enter The Number Of Times You Want To Perform This Action : ");
        int n = scan.nextInt();
        char repeatChoice ;
        System.out.println("Choose Action To Perform :\n" + EncryptionTypes.UP_ENCRYPTION.getChar() + ".ShiftUp\n" + EncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply\n" + EncryptionTypes.DOUBLE_ENCRYPTION.getChar() + ".DoubleEncryption\n");
        do {
            repeatChoice = scan.next().charAt(0);
            EncryptionTypes encryptionTypes = EncryptionTypes.getByChar(repeatChoice);
            switch (encryptionTypes) {
                case UP_ENCRYPTION:
                    return new RepeatIEncryption(new ShiftUpIEncryption(), n);
                case MULTIPLY_ENCRYPTION:
                    return new RepeatIEncryption(new ShiftMultiplyIEncryption(), n);
                case DOUBLE_ENCRYPTION:
                    return new RepeatIEncryption(TypeOfDoubleEncryption(), n);
                default:
                    System.out.println("You Have To Choose " + EncryptionTypes.UP_ENCRYPTION.getChar() + " or " + EncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + " or " + EncryptionTypes.DOUBLE_ENCRYPTION.getChar() + "\nPlease Try Again");
            }
        } while (repeatChoice != EncryptionTypes.UP_ENCRYPTION.getChar() && repeatChoice != EncryptionTypes.MULTIPLY_ENCRYPTION.getChar() && repeatChoice != EncryptionTypes.DOUBLE_ENCRYPTION.getChar());
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
