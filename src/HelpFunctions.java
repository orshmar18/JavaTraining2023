import java.io.File;
import java.util.Scanner;

public class HelpFunctions {
    static final char ENCRYPTION = 'a';
    static final char DECRYTION = 'b';
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
        System.out.println("Choose Between :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n"+ REPEATENCRYPTION + ".RepeatEncryption\n");
        while (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION) {
            choice = scan.next().charAt(0);
            if (choice != UPENCRYPTION && choice != MULTIPLYENCRYPTION && choice != DOUBLEENCRYPTION && choice != REPEATENCRYPTION)
                System.out.println("You Have To Choose "+UPENCRYPTION+" or "+MULTIPLYENCRYPTION+" or "+DOUBLEENCRYPTION+" or "+REPEATENCRYPTION+"\nPlease Try Again");
        }
        return choice;
    }

    public static EncryptionAlgorithm TypeOfEncryption(char choiceTypeOfEncryption) {
        switch (choiceTypeOfEncryption){
            case UPENCRYPTION :
                return (new ShiftUpEncryption());
            case MULTIPLYENCRYPTION :
                return (new ShiftMultiplyEncryption());
            case DOUBLEENCRYPTION:
                return TypeOfDoubleEncryption();
            case REPEATENCRYPTION:
                return TypeOfRepeatEncryption();
        }
        return null;
    }

    public static EncryptionAlgorithm TypeOfDoubleEncryption(){
        System.out.println("Choose Between :\n" + UPMULTIPLY + ".ShiftUp And Then ShiftMultiply\n" + MULTIPLYUP + ".ShiftMultiply And Then ShiftUp\n" + UPUP + ".ShiftUp And Then ShiftUp\n"+ MULTIPLYMULTIPLY + ".ShiftMultiply And Then ShiftMultiply\n");
        char doubleChoice = '0';
        while (doubleChoice != UPMULTIPLY && doubleChoice != MULTIPLYUP && doubleChoice != UPUP && doubleChoice != MULTIPLYMULTIPLY){
            doubleChoice = scan.next().charAt(0);
            switch (doubleChoice){
                case UPMULTIPLY :
                    return new DoubleEncryption(new ShiftUpEncryption(),new ShiftMultiplyEncryption());
                case MULTIPLYUP :
                    return new DoubleEncryption(new ShiftMultiplyEncryption(),new ShiftUpEncryption());
                case UPUP :
                    return new DoubleEncryption(new ShiftUpEncryption(),new ShiftUpEncryption());
                case MULTIPLYMULTIPLY:
                    return new DoubleEncryption(new ShiftMultiplyEncryption(),new ShiftMultiplyEncryption());
                default:
                    System.out.println("You Have To Choose Between :\n" + UPMULTIPLY + " or " + MULTIPLYUP + " or " + UPUP + " or "+ MULTIPLYMULTIPLY);
            }
        }
        return null;
    }


    public static EncryptionAlgorithm TypeOfRepeatEncryption(){
        System.out.print("Enter The Number Of Times You Want To Perform This Action : ");
        int n = scan.nextInt();
        char repeatChoice = '0';
        System.out.println("Choose Action To Perform :\n" + UPENCRYPTION + ".ShiftUp\n" + MULTIPLYENCRYPTION + ".ShiftMultiply\n" + DOUBLEENCRYPTION + ".DoubleEncryption\n");
        while (repeatChoice != UPENCRYPTION && repeatChoice != MULTIPLYENCRYPTION && repeatChoice != DOUBLEENCRYPTION){
            repeatChoice = scan.next().charAt(0);
            switch (repeatChoice){
                case UPENCRYPTION :
                    return new RepeatEncryption(new ShiftUpEncryption(),n);
                case MULTIPLYENCRYPTION :
                    return new RepeatEncryption(new ShiftMultiplyEncryption(),n);
                case DOUBLEENCRYPTION :
                    return new RepeatEncryption(TypeOfDoubleEncryption(),n);
                default:
                    System.out.println("You Have To Choose "+UPENCRYPTION+" or "+MULTIPLYENCRYPTION+" or "+DOUBLEENCRYPTION+"\nPlease Try Again");
            }
        }
        return null;
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
