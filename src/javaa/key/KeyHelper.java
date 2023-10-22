package javaa.key;

import javaa.typesOfEncryption.DoubleIEncryption;
import javaa.typesOfEncryption.IEncryptionAlgorithm;
import javaa.typesOfEncryption.RepeatIEncryption;
import javaa.typesOfEncryption.ShiftUpIEncryption;
import javaa.typesOfEncryption.ShiftMultiplyIEncryption;
import javaa.helpFunctions.HelpFunctions;

import java.io.*;

public class KeyHelper {
    static final int FIRST = 0;
    static final int SECOND = 1;

    public static IKey simpleKeyFileReader(String keyPath) {
        SimpleIKey key = new SimpleIKey(0);
        try (BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))) {
            String linkKey;
            linkKey = keyReader.readLine();
            key.setKey(Integer.parseInt(linkKey));
        } catch (IOException e) {
        }
        return key;
    }

    public static IKey complexKeyFileReader(String keyPath) {
        ComplexIKey key = new ComplexIKey();
        try (BufferedReader keyReader = new BufferedReader(new FileReader(keyPath))) {
            String linkKey;
            linkKey = keyReader.readLine();
            String[] parts = linkKey.substring(1, linkKey.length() - 1).split(",");
            IKey firstIKey = new SimpleIKey(Integer.parseInt(parts[FIRST].trim()));
            IKey secondIKey = new SimpleIKey(Integer.parseInt(parts[SECOND].trim()));
            key.setComplex(firstIKey, secondIKey);
        } catch (IOException e) {
        }
        return key;
    }

    public static File keyFileCreator(String path, IKey IKey) {
        String fileKeyName = path + "_key.txt";
        File keyFile = new File(fileKeyName);
        try (BufferedWriter keyWriter = new BufferedWriter(new FileWriter(keyFile))) {
            keyWriter.write(IKey.toString());
        } catch (IOException e) {
        }
        return keyFile;
    }

    public static IKey keyFileReaderByType(IEncryptionAlgorithm encryptionType, String keyPath) {
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

    public static boolean checkIfKeyValid(IEncryptionAlgorithm encAlg, String keyPath) {
        if (encAlg.getClass().equals(ShiftUpIEncryption.class)) {
            SimpleIKey key = (SimpleIKey) simpleKeyFileReader(keyPath);
            checkRangeShiftUp(key);
        } else {
            if (encAlg.getClass().equals(ShiftMultiplyIEncryption.class)) {
                SimpleIKey key = (SimpleIKey) simpleKeyFileReader(keyPath);
                return checkRangeShiftMultiply(key);
            } else {
                if (encAlg.getClass().equals(DoubleIEncryption.class)) {
                    ComplexIKey keys = (ComplexIKey) complexKeyFileReader(keyPath);
                    return checkRangeDouble(encAlg, keys);
                } else {
                    if (encAlg.getClass().equals(RepeatIEncryption.class))
                        return checkRangeRepeat(encAlg, complexKeyFileReader(keyPath));
                }
            }
        }
        return false;
    }

    public static boolean checkRangeShiftUp(SimpleIKey key) {
        if (isDigitInRange(Character.getNumericValue(key.getKey()), 0, 32767))
            return true;
        else
            return false;
    }

    public static boolean checkRangeShiftMultiply(SimpleIKey key) {
        if (isDigitInRange(Character.getNumericValue(key.getKey()), 0, 255))
            return true;
        else
            return false;
    }

    public static boolean checkRangeDouble(IEncryptionAlgorithm encAlg, ComplexIKey keys) {
        if (encAlg.getClass().equals(new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftMultiplyIEncryption()))) {
            if (checkRangeShiftUp(keys.getFirst()) && checkRangeShiftMultiply(keys.getSecond()))
                return true;
        } else {
            if (encAlg.getClass().equals(new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftUpIEncryption()))) {
                if (checkRangeShiftMultiply(keys.getFirst()) && checkRangeShiftUp(keys.getSecond()))
                    return true;
            } else {
                if (encAlg.getClass().equals(new DoubleIEncryption(new ShiftUpIEncryption(), new ShiftUpIEncryption()))) {
                    if (checkRangeShiftUp(keys.getFirst()) && checkRangeShiftUp(keys.getSecond()))
                        return true;
                } else {
                    if (encAlg.getClass().equals(new DoubleIEncryption(new ShiftMultiplyIEncryption(), new ShiftMultiplyIEncryption()))) {
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
                return checkRangeDouble(encAlg, (ComplexIKey) key);
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


    public static char readCharFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int intValue = reader.read(); // Read the character as an integer

            if (intValue != -1) {
                return (char) intValue;
            } else {
                throw new IOException("End of file reached.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0; // Default value if an error occurs
        }
    }

    public static boolean isDigitInRange(int key, int min, int max) {
        if (Character.isDigit(key)) {
            int intValue = Character.getNumericValue(key);
            return intValue >= min && key <= max;
        }
        return false;
    }
}