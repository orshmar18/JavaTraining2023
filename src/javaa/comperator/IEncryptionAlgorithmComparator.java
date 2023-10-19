package javaa.comperator;

import javaa.typesOfEncryption.IEncryptionAlgorithm;

import java.util.Comparator;

public class IEncryptionAlgorithmComparator implements Comparator<IEncryptionAlgorithm> {

    @Override
    public int compare(IEncryptionAlgorithm encAlg1, IEncryptionAlgorithm encAlg2) {
        return Math.max(encAlg1.getKeyStrength(), encAlg2.getKeyStrength());
    }
}
