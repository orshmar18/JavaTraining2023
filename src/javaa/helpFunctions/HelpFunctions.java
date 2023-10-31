package javaa.helpFunctions;

import javaa.exception.FileNotExistsException;
import javaa.exception.InvalidFilePathException;
import javaa.typesOfEncryption.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class HelpFunctions {
    static final char ENCRYPTION = 'a';
    static final char DECRYPTION = 'b';
    static final char UPENCRYPTION = 'c';
    static final char MULTIPLYENCRYPTION = 'd';
    static final char DOUBLEENCRYPTION = 'e';
    static final char REPEATENCRYPTION = 'f';

    static final char UPMULTIPLY = '1';
    static final char MULTIPLYUP = '2';
    static final char UPUP = '3';
    static final char MULTIPLYMULTIPLY = '4';
    static Scanner scan = new Scanner(System.in);

    public static String getNewName(File file) {
        String parentDirectory = file.getParent();
        String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
        return parentDirectory + File.separator + fileNameWithoutExtension;
    }
    public static String userInputFilePath() throws InvalidFilePathException, FileNotExistsException {
        String filePath;
        System.out.println("Enter The Path Of The File : ");
        filePath = scan.next();
        return filePath;
    }


    public static char UserInputChoise() {
        char choice = '0';
        System.out.println("Hello User");
        System.out.println("Choose Between :\n" + ENCRYPTION + ".Encryption\n" + DECRYPTION + ".Decryption");
        while (choice != ENCRYPTION && choice != DECRYPTION) {
            choice = scan.next().charAt(0);
            if (choice != ENCRYPTION && choice != DECRYPTION)
                System.out.println("You Have To Choose a or b\nPlease Try Again");
        }
        return choice;
    }


    public static char UserInputTypeOfEncryption() {
        char choice = '0';
        System.out.println("Choose Between :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n" + REPEATENCRYPTION + ".RepeatEncryption\n");
        while (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION) {
            choice = scan.next().charAt(0);
            if (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION)
                System.out.println("You Have To Choose " + UPENCRYPTION + " or " + MULTIPLYENCRYPTION + " or " + DOUBLEENCRYPTION + " or " + REPEATENCRYPTION + "\nPlease Try Again");
        }
        return choice;
    }

    public static IEncryptionAlgorithm TypeOfEncryption(char choiceTypeOfEncryption) {
        switch (choiceTypeOfEncryption) {
            case UPENCRYPTION:
                return (new ShiftUpIEncryption());
            case MULTIPLYENCRYPTION:
                return (new ShiftMultiplyIEncryption());
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
        }while (doubleChoice != UPMULTIPLY && doubleChoice != MULTIPLYUP && doubleChoice != UPUP && doubleChoice != MULTIPLYMULTIPLY);
        return null;
    }


    public static IEncryptionAlgorithm TypeOfRepeatEncryption() {
        System.out.print("Enter The Number Of Times You Want To Perform This Action : ");
        int n = scan.nextInt();
        char repeatChoice = '0';
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
        }while (repeatChoice != UPENCRYPTION && repeatChoice != MULTIPLYENCRYPTION && repeatChoice != DOUBLEENCRYPTION);
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
}
