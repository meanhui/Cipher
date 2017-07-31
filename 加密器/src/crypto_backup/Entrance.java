package crypto_backup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JTextField;

public class Entrance {

	private JFrame frame;
	private JTextField textField;
	private String ID;
	
	public void setID(String id){
		this.ID = id;
	}
	public String getID(){
		return this.ID;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Entrance window = new Entrance();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void closeDialog(){	
		this.frame.setVisible(false);
		
	}
	public void openDialog(){
		this.frame.setVisible(true);
	}
	
	/**
	 * Create the application.
	 */
	public Entrance() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBackground(new Color(0, 153, 153));
		frame.setTitle("\u6587\u4EF6\u52A0\u5BC6\u5668");
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton encButton = new JButton("\u52A0\u5BC6");
		encButton.setBounds(92, 164, 120, 37);
		frame.getContentPane().add(encButton);
		
		JButton decButton = new JButton("\u89E3\u5BC6");
		decButton.setBounds(253, 164, 120, 37);
		frame.getContentPane().add(decButton);
		
		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9\u64CD\u4F5C");
		lblNewLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(160, 58, 107, 18);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(172, 102, 183, 18);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblFlag = new JLabel("Your ID:");
		lblFlag.setBounds(105, 104, 71, 15);
		frame.getContentPane().add(lblFlag);
		
		encButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputID = textField.getText();
				if(inputID.equals("")||inputID.equals(null)){
					JOptionPane.showMessageDialog(null, "Pls input your ID first", "Sorry",JOptionPane.WARNING_MESSAGE);  
					return ;
				}
				setID(inputID);
				//System.out.println(getID());
				EncDialog encDialog = new EncDialog();
				encDialog.setID(getID());
				closeDialog();
				encDialog.openDialog();
			}
		});
		
		decButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputID = textField.getText();
				if(inputID.equals("")||inputID.equals(null)){
					JOptionPane.showMessageDialog(null, "Pls input your ID first", "Sorry",JOptionPane.WARNING_MESSAGE);  
					return ;
				}
				setID(inputID);
				DecDialog decDialog = new DecDialog();
				
				decDialog.setID(getID());
				closeDialog();
				decDialog.openDialog();
				
			}
		});
		
		
	}
}
