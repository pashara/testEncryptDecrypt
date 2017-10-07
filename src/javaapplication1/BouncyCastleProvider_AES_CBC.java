/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author admin
 */
public class BouncyCastleProvider_AES_CBC {

    public static int blockSize = 16;
    Cipher encryptCipher = null;
    Cipher decryptCipher = null;       // Создаем буфер для передачи потоков     
    byte[] buf = new byte[blockSize];       //входной буфер     
    byte[] obuf = new byte[512];            //выходной буфер   

    // Формируем ключ     
    byte[] key = null;     // Вектор инициализации, необходимый для режима CBC     
    byte[] IV = null;

    public BouncyCastleProvider_AES_CBC() {  //для работы со 192-битным ключом необходимо установить дополнительные 
        // файлы со страницы загрузок JCE/JDK           
        key = "SECRET_1SECRET_2".getBytes();         //Инициализируем IV 
        IV = new byte[blockSize];
    }

    public BouncyCastleProvider_AES_CBC(String pass, byte[] iv) {
        //получаем ключ и вектор IV         
        key = pass.getBytes();
        IV = new byte[blockSize];
        System.arraycopy(iv, 0, IV, 0, iv.length);
    }

    public BouncyCastleProvider_AES_CBC(byte[] pass, byte[] iv) {
        //получаем ключ и вектор IV         
        key = new byte[pass.length];
        System.arraycopy(pass, 0, key, 0, pass.length);
        IV = new byte[blockSize];
        System.arraycopy(iv, 0, IV, 0, iv.length);
    }

    public void InitCiphers()
            throws NoSuchAlgorithmException,
            NoSuchProviderException,
            NoSuchProviderException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException {
        //1. создаем шифрующий Сipher с помощью провайдера Bouncy Castle        
        encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        //2. Создаем key        
        SecretKey keyValue = new SecretKeySpec(key, "AES");

        //3. Создаем вектор IV        
        AlgorithmParameterSpec IVspec = new IvParameterSpec(IV);

        //4. Инициализируем Cipher        
        encryptCipher.init(Cipher.ENCRYPT_MODE, keyValue, IVspec);

        //1 Создаем дешифрующий Cipher        
        decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        //2. Ключ уже есть        
        //3. IV уже есть        
        //4. Инициализируем дешифрующий Cipher        
        decryptCipher.init(Cipher.DECRYPT_MODE, keyValue, IVspec);
    }

    public void CBCEncrypt(InputStream fis, OutputStream fos)
            throws IOException,
            ShortBufferException,
            IllegalBlockSizeException,
            BadPaddingException {

        //optionally put the IV at the beggining of the cipher file        
        //fos.write(IV, 0, IV.length);          
        byte[] buffer = new byte[blockSize];
        int noBytes = 0;
        byte[] cipherBlock = new byte[encryptCipher.getOutputSize(buffer.length)];
        int cipherBytes;
        while ((noBytes = fis.read(buffer)) != -1) {
            cipherBytes = encryptCipher.update(buffer, 0, noBytes, cipherBlock);
            fos.write(cipherBlock, 0, cipherBytes);

        }

        //Вызываем doFinal        
        cipherBytes = encryptCipher.doFinal(cipherBlock, 0);
        fos.write(cipherBlock, 0, cipherBytes);          //Закрываем файлы        
        fos.close();
        fis.close();

    }
    
    
    public void CBCDecrypt(InputStream fis, OutputStream fos)
            throws IOException,
            ShortBufferException,
            IllegalBlockSizeException,
            BadPaddingException {

        //optionally put the IV at the beggining of the cipher file        
        //fos.write(IV, 0, IV.length);          
        byte[] buffer = new byte[blockSize];
        int noBytes = 0;
        byte[] cipherBlock = new byte[decryptCipher.getOutputSize(buffer.length)];
        int cipherBytes;
        while ((noBytes = fis.read(buffer)) != -1) {
            cipherBytes = decryptCipher.update(buffer, 0, noBytes, cipherBlock);
            fos.write(cipherBlock, 0, cipherBytes);

        }

        
                
        //Вызываем doFinal        
        cipherBytes = decryptCipher.doFinal(cipherBlock, 0);
        fos.write(cipherBlock, 0, cipherBytes);          //Закрываем файлы        
        fos.close();
        fis.close();

    }
}
