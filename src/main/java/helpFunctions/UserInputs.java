package helpFunctions;

import Enums.EEncryptionOrDecryption;
import Enums.EEncryptionTypes;
import Enums.ESolutionTypes;

import java.util.Scanner;

public class UserInputs {
    static Scanner scan = new Scanner(System.in);
    public static String userInputFilePath() {
        String filePath;
        System.out.println("Enter The Path Of The File : ");
        filePath = scan.next();
        return filePath;
    }

    public static EEncryptionOrDecryption UserInputChoice() {
        char choice;
        System.out.println("Hello User");
        System.out.println("Choose Between :\n" + EEncryptionOrDecryption.ENCRYPTION.getChar() + ".Encryption\n" + EEncryptionOrDecryption.DECRYPTION.getChar() + ".Decryption");
        do {
            choice = scan.next().charAt(0);
            if (!EEncryptionOrDecryption.contains(choice))
                System.out.println("You Have To Choose " + EEncryptionOrDecryption.ENCRYPTION.getChar() + " or " + EEncryptionOrDecryption.DECRYPTION.getChar() + "\nPlease Try Again");
        } while (!EEncryptionOrDecryption.contains(choice));
        return EEncryptionOrDecryption.getByChar(choice);
    }

    public static ESolutionTypes UserInputChoiceOfCase() {
        char choice;
        System.out.println("Choose Between :\n" + ESolutionTypes.JSON.getChar() + ".Json\n" + ESolutionTypes.XML.getChar() + ".Xml\n"+ ESolutionTypes.MANUAL.getChar() + ".Manual\n");
        do {
            choice = scan.next().charAt(0);
            if (!ESolutionTypes.contains(choice))
                System.out.println("You Have To Choose " + ESolutionTypes.JSON.getChar() + " or " + ESolutionTypes.XML.getChar()+ " or " + ESolutionTypes.MANUAL.getChar() + "\nPlease Try Again");
        } while (!ESolutionTypes.contains(choice));
        return ESolutionTypes.getByChar(choice);
    }

    public static EEncryptionTypes userInputTypeOfEncryption() {
        char choice;
        System.out.println("Choose Between :\n" + EEncryptionTypes.UP_ENCRYPTION.getChar() + ".ShiftUp\n" + EEncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + ".ShiftMultiply\n" + EEncryptionTypes.DOUBLE_ENCRYPTION.getChar() + ".DoubleEncryption\n" + EEncryptionTypes.REPEAT_ENCRYPTION.getChar() + ".RepeatEncryption\n");
        do {
            choice = scan.next().charAt(0);
            if (!EEncryptionTypes.contains(choice))
                System.out.println("You Have To Choose " + EEncryptionTypes.UP_ENCRYPTION.getChar() + " or " + EEncryptionTypes.MULTIPLY_ENCRYPTION.getChar() + " or " + EEncryptionTypes.DOUBLE_ENCRYPTION.getChar() + " or " + EEncryptionTypes.REPEAT_ENCRYPTION.getChar() + "\nPlease Try Again");
        } while (!EEncryptionTypes.contains(choice));
        return EEncryptionTypes.getByChar(choice);
    }
}
