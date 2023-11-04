package javaa.key;

import javaa.exception.InvalidEncryptionKeyException;
import javaa.menu.Menu;
import javaa.typesOfEncryption.DoubleIEncryption;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.typesOfEncryption.RepeatIEncryption;
import javaa.typesOfEncryption.ShiftUpIEncryption;
import javaa.typesOfEncryption.ShiftMultiplyIEncryption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Scanner;

public class KeyHelper {
    private static final Logger logger = LogManager.getLogger(Menu.class);


    public static IKey simpleKeyFileReader(String keyPath) throws InvalidEncryptionKeyException {
        SimpleIKey key = new SimpleIKey(0);
        try {
            File file = new File(keyPath);
            Scanner scanner = new Scanner(file);
            if (scanner.hasNextInt()) {
                int intValue = scanner.nextInt();
                key.setKey(intValue);
            } else {
                throw new InvalidEncryptionKeyException("The Key Is Not Valid");
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return key;
    }

    public static IKey complexKeyFileReader(String keyPath) throws InvalidEncryptionKeyException {
        ComplexIKey key = new ComplexIKey();

        try (BufferedReader reader = new BufferedReader(new FileReader(keyPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    // Remove the brackets and split the content by ","
                    String content = line.substring(1, line.length() - 1);
                    String[] numbers = content.split(",");
                        if (isInteger(numbers[0].trim()) && isInteger(numbers[1].trim())) {
                            IKey firstIKey = new SimpleIKey(Integer.parseInt(numbers[0].trim()));
                            IKey secondIKey = new SimpleIKey(Integer.parseInt(numbers[1].trim()));
                            key.setComplex(firstIKey, secondIKey);
                            return key;
                        } else {
                            throw new InvalidEncryptionKeyException("The Key Is Not Valid");                        }
                    }else {
                    throw new InvalidEncryptionKeyException("The Key Is Not Valid");
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return key;
    }



    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            return false;
        }
    }


    public static File keyFileCreator(String path, IKey IKey) {
        String fileKeyName = path + "_key.txt";
        File keyFile = new File(fileKeyName);
        try (BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile))) {
            keyWriter.write(IKey.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return keyFile;
    }

    public static IKey keyFileReaderByType(IEncryptionAlgorithm encryptionType, String keyPath) throws InvalidEncryptionKeyException {
        if ((encryptionType.getClass() == RepeatIEncryption.class)) {
            if (((RepeatIEncryption) encryptionType).getEncAlg().getClass() == DoubleIEncryption.class) {
                return KeyHelper.complexKeyFileReader(keyPath);
            } else {
                return KeyHelper.simpleKeyFileReader(keyPath);
            }
        } else {
            if (encryptionType.getClass() == DoubleIEncryption.class) {
                return KeyHelper.complexKeyFileReader(keyPath);
            } else {
                return KeyHelper.simpleKeyFileReader(keyPath);
            }
        }
    }


    public static boolean checkIfKeyValid(IEncryptionAlgorithm encAlg, String keyPath) throws InvalidEncryptionKeyException {
        if (encAlg.getClass().equals(ShiftUpIEncryption.class)) {
            SimpleIKey key = (SimpleIKey) simpleKeyFileReader(keyPath);
            return checkRangeShiftUp(key);
        } else {
            if (encAlg.getClass().equals(ShiftMultiplyIEncryption.class)) {
                SimpleIKey key = (SimpleIKey) simpleKeyFileReader(keyPath);
                return checkRangeShiftMultiply(key);
            } else {
                if (encAlg.getClass().equals(DoubleIEncryption.class)) {
                    ComplexIKey keys = (ComplexIKey) complexKeyFileReader(keyPath);
                    return checkRangeDouble(encAlg, keys);
                } else {
                    if (((RepeatIEncryption) encAlg).getEncAlg().getClass() == DoubleIEncryption.class)
                        return checkRangeRepeat(encAlg, complexKeyFileReader(keyPath));
                    else {
                        if (((RepeatIEncryption) encAlg).getEncAlg().getClass() == ShiftUpIEncryption.class || ((RepeatIEncryption) encAlg).getEncAlg().getClass() == ShiftMultiplyIEncryption.class)
                            return checkRangeRepeat(encAlg, simpleKeyFileReader(keyPath));
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkRangeShiftUp(SimpleIKey key) {
        if (isDigitInRange(key.getKey(), 0, 32767))
            return true;
        else
            return false;
    }

    public static boolean checkRangeShiftMultiply(SimpleIKey key) {
        if (isDigitInRange(key.getKey(), 0, 255))
            return true;
        else
            return false;
    }

    public static boolean checkRangeDouble(IEncryptionAlgorithm encAlg, ComplexIKey keys) {
        if ((((DoubleIEncryption) encAlg).getEncAlg1().getClass() == ShiftUpIEncryption.class) && (((DoubleIEncryption) encAlg).getEncAlg2().getClass() == ShiftMultiplyIEncryption.class)) {
            if (checkRangeShiftUp(keys.getFirst()) && checkRangeShiftMultiply(keys.getSecond()))
                return true;
        } else {
            if ((((DoubleIEncryption) encAlg).getEncAlg1().getClass() == ShiftMultiplyIEncryption.class) && (((DoubleIEncryption) encAlg).getEncAlg2().getClass() == ShiftUpIEncryption.class)) {
                if (checkRangeShiftMultiply(keys.getFirst()) && checkRangeShiftUp(keys.getSecond()))
                    return true;
            } else {
                if ((((DoubleIEncryption) encAlg).getEncAlg1().getClass() == ShiftUpIEncryption.class) && (((DoubleIEncryption) encAlg).getEncAlg2().getClass() == ShiftUpIEncryption.class)) {
                    if (checkRangeShiftUp(keys.getFirst()) && checkRangeShiftUp(keys.getSecond()))
                        return true;
                } else {
                    if ((((DoubleIEncryption) encAlg).getEncAlg1().getClass() == ShiftMultiplyIEncryption.class) && (((DoubleIEncryption) encAlg).getEncAlg2().getClass() == ShiftMultiplyIEncryption.class)) {
                        if (checkRangeShiftMultiply(keys.getFirst()) && checkRangeShiftMultiply(keys.getSecond()))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkRangeRepeat(IEncryptionAlgorithm encAlg, IKey key) {
        if (encAlg.getClass().equals(RepeatIEncryption.class)) {
            if (((RepeatIEncryption) encAlg).getEncAlg().getClass() == DoubleIEncryption.class) {
                return checkRangeDouble(((RepeatIEncryption) encAlg).getEncAlg(), (ComplexIKey) key);
            } else {
                if (((RepeatIEncryption) encAlg).getEncAlg().getClass() == ShiftMultiplyIEncryption.class) {
                    return checkRangeShiftMultiply((SimpleIKey) key);
                } else {
                    if (((RepeatIEncryption) encAlg).getEncAlg().getClass() == ShiftUpIEncryption.class)
                        return checkRangeShiftUp((SimpleIKey) key);
                }
            }
        }
        return false;
    }


    public static boolean isDigitInRange(int key, int min, int max) {
        if (key >= min && key <= max) {
            return true;
        }
        return false;
    }
}