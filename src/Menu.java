import java.io.File;
import java.util.Scanner;

public class Menu {
    static final char ENCRYPTION = 'a';

    static final char DECRYTION = 'b';
    public static void Menu(){
        char choiceEncryption = HelpFunctions.UserInputChoise();
        Scanner scan = new Scanner(System.in);
        String filePath;
        System.out.println("Enter The Path Of The File : ");
        filePath = scan.next();
        File file = new File(filePath);

        char chooseTypeOfEncryption = HelpFunctions.UserInputTypeOfEncryption();

        FileEncryptor fileEncryptor = new FileEncryptor(HelpFunctions.TypeOfEncryption(chooseTypeOfEncryption));

        if (file.exists()){
            switch (choiceEncryption){
                case ENCRYPTION -> {
                    fileEncryptor.encryptFile(filePath);
                }
                case DECRYTION -> {
                    System.out.print("Please Enter The Key Path : ");
                    String kPath = scan.next();
                    fileEncryptor.deccryptFile(filePath,kPath);
                }
                default -> System.out.println("You Did Not Enter One Of The Options");
            }
        }else System.out.println("File Not Exist");
    }
}
