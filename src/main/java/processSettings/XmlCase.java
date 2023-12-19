package processSettings;

import Enums.EEncryptionOrDecryption;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import fileEncryptor.FileEncryptor;
import helpFunctions.FileOrDirectoryCases;
import helpFunctions.UserInputs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import typesOfEncryption.IEncryptionAlgorithm;

import java.io.File;
import java.io.IOException;

import static menu.Menu.StartMenu;

public class XmlCase {


    static ProcessSettings processSettings;
    private static final Logger logger = LogManager.getLogger(JsonCase.class);

    public static void xmlConfiguration() throws IOException {
       XmlMapper xmlMapper = new XmlMapper();
        File file = new File("C:\\Users\\or448\\IdeaProjects\\JavaTraining2023\\src\\main\\resources\\config.xml");
        processSettings = xmlMapper.readValue(file, ProcessSettings.class);
    }

    public static void xmlCase() {
        EEncryptionOrDecryption encryptionOrDecryption = UserInputs.UserInputChoice();
        try {
            xmlConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileEncryptor<?> fileEncryptor = new FileEncryptor<>((IEncryptionAlgorithm) processSettings.encryptionAlgorithm);
        switch (encryptionOrDecryption) {
            case ENCRYPTION -> {
                FileOrDirectoryCases.fileEncryptCase(fileEncryptor, processSettings.originalPath);
            }
            case DECRYPTION -> {
                FileOrDirectoryCases.fileDecryptCase(fileEncryptor, processSettings.encryptedPath, processSettings.keyPath);
            }
            default -> {
                System.out.println("You Did Not Enter One Of The Options\nPlease Try Again");
                logger.warn("The User Did Not Pick One Of The Options");
                StartMenu();
            }
        }

    }



}
