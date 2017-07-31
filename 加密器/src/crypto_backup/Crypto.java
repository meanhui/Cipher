package crypto_backup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by xiaohao on 2017/5/26.
 */
public class Crypto {

    /**
     * AES方式解密文件
     *
     * @param sourceFile
     * @return
     * @throws ClassNotFoundException 
     */
    public File decryptFile(File sourceFile, File decryptFile, Element message) throws IOException, ClassNotFoundException {

    	
    	byte[] keyid = message.getImmutable().toBytes();
    	
    	
    	
        String sKey="";
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int r;
        try {
            byte[] bKey=new byte[32];
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(decryptFile);

            inputStream.read(bKey);
            sKey=new String (ByteXOR(bKey,keyid));//从文件头读取密钥

            Cipher cipher = initAESCipher(sKey, Cipher.DECRYPT_MODE);

            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte[] buffer = new byte[1024];


            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return decryptFile;
    }

    /**
     * 对文件进行AES加密
     *
     * @param sourceFile
     * @param sKey
     * @return
     */
    public File encryptFile(File sourceFile, File encrypfile, String sKey, Element message) throws IOException {

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
        	byte[] keyid = message.getImmutable().toBytes();
        	System.out.println(keyid.length);
        	
        	
            byte[] bkey = sKey.getBytes();
            System.out.println(bkey.length);
            inputStream = new FileInputStream(sourceFile);

            outputStream = new FileOutputStream(encrypfile);
            Cipher cipher = initAESCipher(sKey, Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);

            outputStream.write(ByteXOR(bkey,keyid), 0, 32);
            outputStream.flush();//文件头写入AES密钥

            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            cipherInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return encrypfile;
    }

    /**
     * 初始化 AES Cipher
     *
     * @param sKey
     * @param cipherMode
     * @return
     */
    public Cipher initAESCipher(String sKey, int cipherMode) {
        //创建Key gen
        KeyGenerator keyGenerator = null;
        Cipher cipher = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128, new SecureRandom(sKey.getBytes()));
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] codeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(codeFormat, "AES");
            cipher = Cipher.getInstance("AES");
            //初始化
            cipher.init(cipherMode, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return cipher;
    }

    public static void main(String[] args) throws IOException {
//        File a=new File("C:\\Users\\xiaohao\\Desktop\\新建文件夹\\1.txt");
//        File b=new File("C:\\Users\\xiaohao\\Desktop\\新建文件夹\\2.txt");
//        File c =new File("C:\\Users\\xiaohao\\Desktop\\新建文件夹\\3.txt");
//        Crypto cp = new Crypto();
//        cp.encryptFile(a,b,"1234567812345678");
//        cp.decryptFile(b,c);

    }
    
    public byte[] ByteXOR(byte[] parameter1, byte[] tmp)
    {
    	byte[] parameter2 = new byte[32];
    	System.out.println(parameter1.length + "\n" + tmp.length);
    	System.arraycopy(tmp, 0, parameter2, 0, parameter1.length);
    	System.out.println(parameter1.length + "\n" + parameter2.length);
        if(parameter1.length != parameter2.length)
        {
            System.out.println("不能进行模二加！");
            return null;
        }

        byte[] ByteXOR = new byte[parameter1.length];
        byte temp3;
        
        for(int i = 0; i < parameter1.length; i++)
        {
            byte temp1 = parameter1[i];
            byte temp2 = parameter2[i];
            temp3 = (byte)(temp1 ^ temp2);
            ByteXOR[i] = temp3;
        }
        
        return ByteXOR;
    }
}
