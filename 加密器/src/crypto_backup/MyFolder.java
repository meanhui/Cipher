package crypto_backup;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import it.unisa.dia.gas.jpbc.Element;

/**
 * Created by xiaohao on 2017/5/26.
 */
/**
 * 计算文件和目录总数
 */
public class MyFolder {

	private static int countDirctory = 0;
	private static int countFile = 0;

	private Element message;

	public int getCountDirctory() {
		return countDirctory;
	}

	public int getCountFile() {
		return countFile;
	}

	long Compute(File f) {
		countDirctory = 0;
		countFile = 0;
		// File f = new File(a);
		if (f.isFile())
			return -1;
		getFileAndDirectory(f);
		return countDirctory + countFile;// 返回文件和文件夹的总数
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		// String s = "C:\\Users\\xiaohao\\Desktop\\新建文件夹";
		// String d = "C:\\Users\\xiaohao\\Desktop\\新建文件夹 (2)";
		// File ss=new File(s);
		// File dd=new File(d);
		// MyFolder mf=new MyFolder();
		// mf.encryFiles(ss,dd);

	}

	void getFileAndDirectory(File file) {// 获取文件夹和文件的总数
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File fileIndex : files) {
				if (fileIndex.isDirectory()) {
					countDirctory++;
					getFileAndDirectory(fileIndex);
				} else {
					countFile++;
				}
			}
		}
	}

	// 递归加密
	public void encryFiles(File src, File dest, String ID) throws IOException {
		try {
			message = KeygetID(ID);
		} catch (Exception e) {
			message = KeygenID(ID);
		} finally {
			Crypto c = new Crypto();
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdir();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					encryFiles(srcFile, destFile, ID);
				}
			} else {
				String name = dest.getParent() + File.separator + src.getName() + ".encrypt";
				File enFile = new File(name);
				System.out.println(name);
				enFile.createNewFile();
				// 递归复制
				c.encryptFile(src, enFile, getKey(), message);

			}
		}
	}

	// 递归解密
	public void decryFiles(File src, File dest, String ID) throws IOException, ClassNotFoundException {
		try {
			message = KeygetID(ID);
		} catch (Exception e) {
			message = KeygenID(ID);
		} finally {
			Crypto c = new Crypto();
			if (src.isDirectory()) {
				if (!dest.exists()) {
					dest.mkdir();
				}
				String files[] = src.list();
				for (String file : files) {
					File srcFile = new File(src, file);
					File destFile = new File(dest, file);
					// 递归复制
					decryFiles(srcFile, destFile, ID);
				}
			} else {
				String name = dest.getParent() + File.separator
						+ src.getName().substring(0, src.getName().lastIndexOf("."));
				File enFile = new File(name);
				enFile.createNewFile();
				// 递归复制
				c.decryptFile(src, enFile, message);

			}
		}
	}

	public Element KeygenID(String ID) throws IOException {

		BBGHIBE bbgHIBE = new BBGHIBE(ID);
		BBGHIBEMasterKey msk = bbgHIBE.Setup("params/curves/a.properties", 2);
		String[] testI1 = { ID };
		String[] receiver = new String[1];
		receiver[0] = testI1[0];
		String[] ciphertextIV = new String[1];
		System.arraycopy(receiver, 0, ciphertextIV, 0, 1);
		// KeyGen for depth 1
		if (BBGHIBE.isDebug) {
			System.out.println("Generate secret key for user at depth 1");
		}
		BBGHIBESecretKey SKDepth1 = bbgHIBE.KeyGen(msk, testI1);

		// encryption
		if (BBGHIBE.isDebug) {
			System.out.println("Encryption");
		}
		Element message = bbgHIBE.Encrypt(ciphertextIV);
		System.out.println(message);
		// System.out.println(message.getImmutable().toBytes().length);

		// FileInputStream fis = new FileInputStream("object.obj");
		// ObjectInputStream ois = new ObjectInputStream(fis);
		// BBGHIBECiphertext ciphertext = (BBGHIBECiphertext) ois.readObject();
		// ois.close();
		// fis.close();
		//
		// System.out.println(ciphertext.C_0);
		// System.out.println(ciphertext.C_1);
		// System.out.println(ciphertext.C_2);

		// //Decryption for depth 1
		// if (BBGHIBE.isDebug){
		// System.out.println("Dncryption for user at depth 1");
		// }
		// bbgHIBE.decrypt(ciphertextIV, ciphertext, SKDepth1);

		return message;
	}

	public Element KeygetID(String ID) throws IOException, ClassNotFoundException {
		BBGHIBE bbgHIBE = new BBGHIBE(ID);
		BBGHIBEMasterKey msk = bbgHIBE.Setup("params/curves/a.properties", 2);
		String[] testI1 = { ID };
		String[] receiver = new String[1];
		receiver[0] = testI1[0];
		String[] ciphertextIV = new String[1];
		System.arraycopy(receiver, 0, ciphertextIV, 0, 1);
		// KeyGen for depth 1
		if (BBGHIBE.isDebug) {
			System.out.println("Generate secret key for user at depth 1");
		}
		BBGHIBESecretKey SKDepth1 = bbgHIBE.KeyGen(msk, testI1);

		// //encryption
		// if (BBGHIBE.isDebug){
		// System.out.println("Encryption");
		// }
		// Element message = bbgHIBE.Encrypt(ciphertextIV);
		// System.out.println(message);
		// System.out.println(message.getImmutable().toBytes().length);

		FileInputStream fis = new FileInputStream("object.obj");
		ObjectInputStream ois = new ObjectInputStream(fis);
		BBGHIBECiphertext ciphertext = (BBGHIBECiphertext) ois.readObject();
		ois.close();
		fis.close();

		System.out.println(ciphertext.C_0);
		System.out.println(ciphertext.C_1);
		System.out.println(ciphertext.C_2);

		// //Decryption for depth 1
		// if (BBGHIBE.isDebug){
		// System.out.println("Dncryption for user at depth 1");
		// }
		Element message = bbgHIBE.decrypt(ciphertextIV, ciphertext, SKDepth1);
		return message;
	}

	/**
	 * 随机生成秘钥
	 * 
	 * @return
	 */
	public static String getKey() {
		String s = "";
		try {
			KeyGenerator kg = KeyGenerator.getInstance("AES");
			kg.init(128);
			// 要生成多少位，只需要修改这里即可128, 192或256
			SecretKey sk = kg.generateKey();
			byte[] b = sk.getEncoded();
			s = byteToHexString(b);
			// System.out.println(s);
			// System.out.println("十六进制密钥长度为" + s.length());
			// System.out.println("二进制密钥的长度为" + s.length() * 4);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			System.out.println("没有此算法。");
		}
		return s;
	}

	/**
	 * byte数组转化为16进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String strHex = Integer.toHexString(bytes[i]);
			if (strHex.length() > 3) {
				sb.append(strHex.substring(6));
			} else {
				if (strHex.length() < 2) {
					sb.append("0" + strHex);
				} else {
					sb.append(strHex);
				}
			}
		}
		return sb.toString();
	}

}
