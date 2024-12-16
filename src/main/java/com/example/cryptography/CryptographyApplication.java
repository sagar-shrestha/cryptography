package com.example.cryptography;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@SpringBootApplication
@RequiredArgsConstructor
public class CryptographyApplication {

    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        return Crypt.generateSignature(data, privateKey);
    }

    public static String generateSignatureUsingPublicKey(String data, PublicKey publicKey) throws Exception {
        return Crypt.generateSignatureUsingPublicKey(data, publicKey);
    }

    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        // Read pem file
        String pem = new String(Files.readAllBytes(Paths.get(filePath)));

        // Remove pem markers
        pem = pem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        // Decode base64
        byte[] keyBytes = Base64.getDecoder().decode(pem);

        // convert to private key
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(String filePath) throws Exception {
        // Read pem file
        String pem = new String(Files.readAllBytes(Paths.get(filePath)));

        // Remove pem markers
        pem = pem.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // Decode base64
        byte[] keyBytes = Base64.getDecoder().decode(pem);

        // convert to public key
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
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
        String data1 = "{\"userIdentifier\":\"9818567284\",\"amount\":10,\"channel\":\"CIPS\"}";

        String data2 = "{\"validationTraceId\":\"CEL1735813871842\",\"amount\":110.00,\"userIdentifier\":\"9818567284\",\"channel\":\"CIPS\",\"purpose\":\"LOAD\",\"transactionDate\":\"2025-01-02:16:16:29.653\",\"userType\":\"NORMAL\",\"transactionId\":\"7618\",\"tranRemarks\":\"Load Wallet\"}";

        // load private key from resources folder
        String privateKeyFilePath = "src/main/resources/privatekey.pem";
        PrivateKey privateKey = loadPrivateKey(privateKeyFilePath);
        System.out.println("Loaded Private Key: " + privateKey);

        // generate signature string
        String signatureString1 = generateSignature(data1, privateKey);
        System.out.println("Signature String data 1: " + signatureString1);

        // generate signature string
        String signatureString2 = generateSignature(data2, privateKey);
        System.out.println("Signature String data 2: " + signatureString2);

        // load public key from resoruces folder
//        String publicKeyFIlePath = "src/main/resources/publickey.pem";
//        PublicKey publicKey = loadPublicKey(publicKeyFIlePath);
//        System.out.println("Loaded Public Key: " + publicKey);

        // generate signature string  using private key from resources folder
//        String signatureString = generateSignatureUsingPublicKey(data, publicKey);
//        System.out.println("Signature String: " + signatureString);


    }

}
