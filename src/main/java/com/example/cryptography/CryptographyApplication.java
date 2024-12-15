package com.example.cryptography;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;

@SpringBootApplication
@RequiredArgsConstructor
public class CryptographyApplication {

    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        return Crypt.generateSignature(data, privateKey);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CryptographyApplication.class, args);

        KeyPair keyPair = Crypt.generateKeyPair();
        System.out.println("Key: " + keyPair);
        System.out.println("Public Key: " + keyPair.getPublic());
        System.out.println("Private Key: " + keyPair.getPrivate());
        byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
        String encodedPublicKeyString = Base64.getEncoder().encodeToString(encodedPublicKey);

        System.out.println("Encoded Public Key: " + encodedPublicKeyString);

        String data = "{\"userIdentifier\":\"9818567284\",\"amount\":10,\"channel\":\"CIPS\"}\n";

        String signatureString = generateSignature(data, Crypt.generateKeyPair().getPrivate());
        System.out.println("Signature String: " + signatureString);


    }

}
