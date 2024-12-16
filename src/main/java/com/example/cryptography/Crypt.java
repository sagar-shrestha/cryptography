package com.example.cryptography;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.*;
import java.util.Base64;

@Component
public class Crypt {

    // Method to generate RSA key pair
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }
    // Method to generate a signature
    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] signatureBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signatureBytes);
    }

    // method to generate a signature using public key
    public static String generateSignatureUsingPublicKey(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptBytes);
    }
}
