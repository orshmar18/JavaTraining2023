package helpFunctions;

import Enums.EDoubleEncryptionTypes;
import Enums.EEncryptionTypes;
import typesOfEncryption.*;

import java.util.Scanner;

public class EncryptionTypeSorts {
    static Scanner scan = new Scanner(System.in);

    public static IEncryptionAlgorithm typeOfEncryption(EEncryptionTypes eEncryptionTypes) {
        switch (eEncryptionTypes) {
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
        System.out.println("Choose Between :\n" + EDoubleEncryptionTypes.UP_MULTIPLY_ENCRYPTION.getChar() + ".ShiftUp And Then ShiftMultiply\n" + EDoubleEncryptionTypes.MULTIPLY_UP_ENCRYPTION.getChar() + ".ShiftMultiply And Then ShiftUp\n" + EDoubleEncryptionTypes.UP_UP_ENCRYPTION.getChar() + ".ShiftUp And Then ShiftUp\n" + EDoubleEncryptionTypes.MULTIPLY_MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply And Then ShiftMultiply\n");
        char doubleChoice;
        do {
            doubleChoice = scan.next().charAt(0);
            EDoubleEncryptionTypes doubleEncryptionTypes = EDoubleEncryptionTypes.getByChar(doubleChoice);
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
                    System.out.println("You Have To Choose Between :\n" + EDoubleEncryptionTypes.UP_MULTIPLY_ENCRYPTION.getChar() + " or " + EDoubleEncryptionTypes.MULTIPLY_UP_ENCRYPTION.getChar() + " or " + EDoubleEncryptionTypes.UP_UP_ENCRYPTION.getChar() + " or " + EDoubleEncryptionTypes.MULTIPLY_MULTIPLY_ENCRYPTION.getChar());
            }
        } while (!EDoubleEncryptionTypes.contains(doubleChoice));
        return null;
    }


    public static IEncryptionAlgorithm TypeOfRepeatEncryption() {
        System.out.print("Enter The Number Of Times You Want To Perform This Action : ");
        int n = scan.nextInt();
        char repeatChoice ;
        System.out.println("Choose Action To Perform :\n" + EEncryptionTypes.UP_ENCRYPTION.getChar() + ".ShiftUp\n" + EEncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply\n" + EEncryptionTypes.DOUBLE_ENCRYPTION.getChar() + ".DoubleEncryption\n");
        do {
            repeatChoice = scan.next().charAt(0);
            EEncryptionTypes encryptionTypes = EEncryptionTypes.getByChar(repeatChoice);
            switch (encryptionTypes) {
                case UP_ENCRYPTION:
                    return new RepeatIEncryption(new ShiftUpIEncryption(), n);
                case MULTIPLY_ENCRYPTION:
                    return new RepeatIEncryption(new ShiftMultiplyIEncryption(), n);
                case DOUBLE_ENCRYPTION:
                    return new RepeatIEncryption(TypeOfDoubleEncryption(), n);
                default:
                    System.out.println("You Have To Choose " + EEncryptionTypes.UP_ENCRYPTION.getChar() + " or " + EEncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + " or " + EEncryptionTypes.DOUBLE_ENCRYPTION.getChar() + "\nPlease Try Again");
            }
        } while (repeatChoice != EEncryptionTypes.UP_ENCRYPTION.getChar() && repeatChoice != EEncryptionTypes.MULTIPLY_ENCRYPTION.getChar() && repeatChoice != EEncryptionTypes.DOUBLE_ENCRYPTION.getChar());
        return null;
    }
}
