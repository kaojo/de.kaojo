/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.kaojo.security.cipher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author julian
 */
public class SecureHashingAlgorithmHelper {

    private static final String ALGORITHM = "SHA-256";

    public static String hashSHA256(String input) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance(ALGORITHM);

        md.update(input.getBytes());
        byte[] digest = md.digest();

        byte[] encode = Base64.encodeBase64(digest);
        return new String(encode);
    }

}
