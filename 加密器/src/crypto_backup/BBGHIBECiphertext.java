package crypto_backup;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;



public class BBGHIBECiphertext implements Serializable {
	Element C_0;
	Element C_1;
	Element C_2;
	
	BBGHIBECiphertext(){}
	
//	/** 
//	 * Serialize this instance. 
//	 *
//	 * @param out Target to which this instance is written. 
//	 * @throws IOException Thrown if exception occurs during serialization. 
//	 */
	private void writeObject(final ObjectOutputStream out) throws IOException
	{
		System.out.println(C_0);
		out.write(this.C_0.toBytes());
		System.out.println(this.C_1);
		out.write(this.C_1.toBytes());
		System.out.println(this.C_2);
		out.write(this.C_2.toBytes());
	}
//	/** 
//	 * Deserialize this instance from input stream. 
//	 *
//	 * @param in Input Stream from which this instance is to be deserialized. 
//	 * @throws IOException Thrown if error occurs in deserialization. 
//	 * @throws ClassNotFoundException Thrown if expected class is not found. 
//	 */
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException
	{
		byte[] b1 = new byte[128];
		byte[] b2 = new byte[128];
		byte[] b3 = new byte[128];
		in.read(b1);
		System.out.println(new String(b1));
//		C_0 = null;
//		this.C_0.setFromBytes(b1);
		in.read(b2);
//		this.C_0.setFromBytes(b2);
		in.read(b3);
		Pairing pairing = PairingFactory.getPairing("params/curves/a.properties");
		this.C_0 = pairing.getGT().newRandomElement();
		this.C_1 = pairing.getG1().newRandomElement();
		this.C_2 = pairing.getG2().newRandomElement();
		this.C_0.setFromBytes(b1);
		this.C_1.setFromBytes(b2);
		this.C_2.setFromBytes(b3);
		
		System.out.println(C_0);
		System.out.println(C_1);
		System.out.println(C_2);
//		this.C_0.setFromBytes(b3);
//		this.C_1=(Element) in.readObject();
//		this.C_2=(Element) in.readObject();
	}

}