public class RepeatEncryption implements EncryptionAlgorithm{
    private final EncryptionAlgorithm encAlg;
    private final int n;

    public RepeatEncryption(EncryptionAlgorithm encAlg, int n) {
        this.encAlg = encAlg;
        this.n = n;
    }

    public  EncryptionAlgorithm getEncAlg(){
        return encAlg;
    }


    @Override
    public byte[] dataEncryption(byte[] filedata, int byteRead, Key key) {
        byte[] result;
        result = encAlg.dataEncryption(filedata,byteRead,key);
        for (int i = 1 ; i < n ; i++){
            result = encAlg.dataEncryption(result,byteRead,key);
        }
        return result;
    }

    @Override
    public byte[] dataDecryption(byte[] filedata, int byteRead, Key key) {
        byte[] result;
        result = encAlg.dataDecryption(filedata,byteRead,key);
        for (int i = 1 ; i < n ; i++){
            result = encAlg.dataDecryption(result,byteRead,key);
        }
        return result;
    }

    @Override
    public Key generateKey() {
        return encAlg.generateKey();
    }
}
