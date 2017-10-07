/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Security;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import org.bouncycastle.crypto.DataLengthException;

/**
 *
 * @author admin
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String dirPath = "C:\\Users\\admin\\Documents\\NetBeansProjects\\JavaApplication1\\src\\";
            FileInputStream fis = new FileInputStream(new File(dirPath+"clear.txt"));
            FileOutputStream fos = new FileOutputStream(new File(dirPath+"encrypt.txt"));
            //Вариант 1             
            //BouncyCastleAPI_AES_CBC bc = new BouncyCastleAPI_AES_CBC();  
            //Вариант 2            
            BouncyCastleProvider_AES_CBC bc = new BouncyCastleProvider_AES_CBC();
            bc.InitCiphers();
            //зашифровка файла           
            bc.CBCEncrypt(fis, fos);
            fis = new FileInputStream(new File(dirPath+"encrypt.txt"));
            fos = new FileOutputStream(new File(dirPath+"clear_test.txt"));
            //расшифровка файла            
            bc.CBCDecrypt(fis, fos);
        } catch (ShortBufferException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLengthException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JavaApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Test done !");

    }

}
