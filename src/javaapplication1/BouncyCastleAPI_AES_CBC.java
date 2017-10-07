/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 *
 * @author admin
 */
public class BouncyCastleAPI_AES_CBC {

    PaddedBufferedBlockCipher encryptCipher = null;
    PaddedBufferedBlockCipher decryptCipher = null;       // Буфер для передачи между потоками     
    byte[] buf = new byte[16];              //входной     
    byte[] obuf = new byte[512];            //выходной     // Ключ key     
    byte[] key = null;     // Инициализирующий вектор для режима CBC     
    byte[] IV = null;       // Размер блока по умолчанию    
    public static int blockSize = 16;

    public BouncyCastleAPI_AES_CBC() {         //192-битный ключ по умолчанию         
        key = "SECRET_1SECRET_2SECRET_3".getBytes();         //Вектор по умолчанию – все байты 0         
        IV = new byte[blockSize];
    }

    public BouncyCastleAPI_AES_CBC(byte[] keyBytes) {
        //Получаем key         
        key = new byte[keyBytes.length];
        System.arraycopy(keyBytes, 0, key, 0, keyBytes.length);           //Получаем вектор IV         
        IV = new byte[blockSize];
    }

    public BouncyCastleAPI_AES_CBC(byte[] keyBytes, byte[] iv) {
        //Получаем key         
        key = new byte[keyBytes.length];
        System.arraycopy(keyBytes, 0, key, 0, keyBytes.length);

        //Получаем вектор IV         
        IV = new byte[blockSize];
        System.arraycopy(iv, 0, IV, 0, iv.length);
    }

    public void InitCiphers() {
        //Создаем объекты ciphers         
        // AES cipher в режиме CBC с паддингом         
        encryptCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));
        decryptCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()));

        //Создаем параметр вектора IV         
        ParametersWithIV parameterIV = new ParametersWithIV(new KeyParameter(key), IV);
        encryptCipher.init(true, parameterIV);
        decryptCipher.init(false, parameterIV);
    }
    
    
    
    public void CBCEncrypt(InputStream in, OutputStream out)
            throws ShortBufferException,
            IllegalBlockSizeException,
            BadPaddingException,
            DataLengthException,
            IllegalStateException,
            InvalidCipherTextException,
            IOException {
        // Байты, записываемые в out будут зашифрованы     
        // Читаем байты из in записываем зашифрованными в out              
        int noBytesRead = 0;        //число байт, которые читаем     
        int noBytesProcessed = 0;   //число обработанных байт       
        while ((noBytesRead = in.read(buf)) >= 0) {
            noBytesProcessed = encryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
            out.write(obuf, 0, noBytesProcessed);
        }
        noBytesProcessed = encryptCipher.doFinal(obuf, 0);
        out.write(obuf, 0, noBytesProcessed);
        out.flush();
        in.close();
        out.close();
    }
    
    public void CBCDecrypt(InputStream in, OutputStream out)
            throws ShortBufferException,
            IllegalBlockSizeException,
            BadPaddingException,
            DataLengthException,
            IllegalStateException,
            InvalidCipherTextException,
            IOException {
        // Байты, записываемые в out будут зашифрованы     
        // Читаем байты из in записываем зашифрованными в out              
        int noBytesRead = 0;        //число байт, которые читаем     
        int noBytesProcessed = 0;   //число обработанных байт       
        while ((noBytesRead = in.read(buf)) != 0) {
            noBytesProcessed = decryptCipher.processBytes(buf, 0, noBytesRead, obuf, 0);
            out.write(obuf, 0, noBytesProcessed);
        }
        noBytesProcessed = decryptCipher.doFinal(obuf, 0);
        out.write(obuf, 0, noBytesProcessed);
        out.flush();
        in.close();
        out.close();
    }

    
}
