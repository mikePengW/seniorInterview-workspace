package jdk21.kem;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author wei.peng wrote on 2023-12-29
 * @version 1.0
 */
public class KeyEncapsulationMechanism {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, DecapsulateException {
        // Receiver side
        var kpg = KeyPairGenerator.getInstance("x25519");
        var kp = kpg.generateKeyPair();

        // Sender side
        var kem1 = KEM.getInstance("DHKEM");
        var sender = kem1.newEncapsulator(kp.getPublic());
        var encapsulated = sender.encapsulate();
        var k1 = encapsulated.key();

        // Receiver side
        var kem2 = KEM.getInstance("DHKEM");
        var receiver = kem2.newDecapsulator(kp.getPrivate());
        var k2 = receiver.decapsulate(encapsulated.encapsulation());

        assert Arrays.equals(k1.getEncoded(), k2.getEncoded());
    }

}
