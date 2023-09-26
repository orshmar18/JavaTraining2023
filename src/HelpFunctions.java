import java.io.File;
import java.security.PublicKey;
import java.util.Scanner;

public class HelpFunctions {
    static final char ENCRYPTION = 'a';
    static final char DECRYTION = 'b';

    static final char UPENCRYPTION = 'c';

    static final char MULTIPLYENCRYPTION = 'd';

    static final char DOUBLEENCRYPTION = 'e';
    static Scanner scan = new Scanner(System.in);

    public static String getNewName(File file) {
        String parentDirectory = file.getParent();
        String fileNameWithoutExtension = file.getName().replaceFirst("[.][^.]+$", "");
        return parentDirectory + File.separator + fileNameWithoutExtension;
    }

    public static char UserInputChoise() {
        char choice = '0';
        System.out.println("Hello User");
        System.out.println("Choose Between :\n" + ENCRYPTION + ".Encryption\n" + DECRYTION + ".Decryption");
        while (choice != ENCRYPTION && choice != DECRYTION) {
            choice = scan.next().charAt(0);
            if (choice != ENCRYPTION && choice != DECRYTION)
                System.out.println("You Have To Choose a or b\nPlease Try Again");
        }
        return choice;
    }


    public static char UserInputTypeOfEncryption() {
        char choice = '0';
        System.out.println("Choose Between :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n");
        while (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION) {
            choice = scan.next().charAt(0);
            if (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION)
                System.out.println("You Have To Choose a or b\nPlease Try Again");
        }
        return choice;
    }

    public static EncryptionAlgorithm TypeOfEncryption(char choiceTypeOfEncryption) {
        if (choiceTypeOfEncryption == 'c') {
            return (new ShiftUpEncryption());
        } else {
            if (choiceTypeOfEncryption == 'd') {
                return (new ShiftMultiplyEncryption());
            } else {
                return (new DoubleEncryption());
            }
        }
    }

    public static int[] AllPrime(int n) {
        boolean[] isPrime = new boolean[n + 1];
        isPrime[2] = true;
        for (int i = 3; i <= n; i += 2) {
            isPrime[i] = true;
        }

        for (int p = 3; p * p <= n; p += 2) {
            if (isPrime[p]) {
                for (int i = p * p; i <= n; i += p * 2) {
                    isPrime[i] = false;
                }
            }
        }

        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i])
                count++;
        }

        int[] primes = new int[count];
        int index = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime[i]) {
                primes[index] = i;
                index++;
            }
        }
        return primes;
    }


}
