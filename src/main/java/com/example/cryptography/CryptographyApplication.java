package com.example.cryptography;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
@RequiredArgsConstructor
public class CryptographyApplication {

    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        return Crypt.generateSignature(data, privateKey);
    }

    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        // Read pem file
        String pem = new String(Files.readAllBytes(Paths.get(filePath)));

        // Remove pem markers
        pem = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // Decode base64
        byte[] keyBytes = Base64.getDecoder().decode(pem);

        // convert to private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(CryptographyApplication.class, args);

        // generate keypair
//        KeyPair keyPair = Crypt.generateKeyPair();
//        System.out.println("Key: " + keyPair);
//        System.out.println("Public Key: " + keyPair.getPublic());
//        System.out.println("Private Key: " + keyPair.getPrivate());

        // encode public key string
//        byte[] encodedPublicKey = keyPair.getPublic().getEncoded();
//        String encodedPublicKeyString = Base64.getEncoder().encodeToString(encodedPublicKey);

//        System.out.println("Encoded Public Key: " + encodedPublicKeyString);

        // encode private key string
//        byte[] encodedPrivateKey = keyPair.getPrivate().getEncoded();
//        String encodedPrivateKeyString = Base64.getEncoder().encodeToString(encodedPrivateKey);

//        System.out.println("Encoded Private Key: " + encodedPrivateKeyString);

        // set data
        String data = "{\"userIdentifier\":\"9818567284\",\"amount\":10,\"channel\":\"CIPS\"}\n";

        // generate signature string
//        String signatureString = generateSignature(data, Crypt.generateKeyPair().getPrivate());
//        System.out.println("Signature String: " + signatureString);


        // load private key from resources folder
        String filePath = "src/main/resources/nchlprivate.pem";
        PrivateKey privateKey = loadPrivateKey(filePath);
        System.out.println("Loaded Private Key: " + privateKey);

        // generate signature string  using private key from resources folder
        String signatureString = generateSignature(data, privateKey);
        System.out.println("Signature String: " + signatureString);


    }

}
