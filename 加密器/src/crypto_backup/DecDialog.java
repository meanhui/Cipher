package crypto_backup;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class DecDialog {

	private JFrame Decframe;
	private String filePath, fileName, dirPath;
	private String ID;

	public void setID(String id) {
		this.ID = id;
	}

	public String getID() {
		return this.ID;
	}

	/**
	 * Launch the application.
	 */
	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { DecDialog window = new
	 * DecDialog(); window.Decframe.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public void closeDialog() {
		this.Decframe.setVisible(false);
		this.Decframe = null;
	}

	public void openDialog() {
		this.Decframe.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public DecDialog() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Decframe = new JFrame();
		Decframe.setTitle("\u89E3\u5BC6");
		Decframe.setBounds(100, 100, 450, 300);
		Decframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Decframe.getContentPane().setLayout(null);

		JButton decFileButton = new JButton("\u89E3\u5BC6\u5355\u4E2A\u6587\u4EF6");
		decFileButton.setBounds(82, 131, 117, 38);
		Decframe.getContentPane().add(decFileButton);

		JButton decDirButton = new JButton("\u89E3\u5BC6\u76EE\u5F55");
		decDirButton.setBounds(248, 131, 117, 38);
		Decframe.getContentPane().add(decDirButton);

		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9\u64CD\u4F5C");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(167, 53, 100, 30);
		Decframe.getContentPane().add(lblNewLabel);

		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(331, 228, 93, 23);
		Decframe.getContentPane().add(backButton);

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Entrance entrance = new Entrance();
				entrance.openDialog();
				closeDialog();
			}
		});

		decFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("decFILE--->current ID:" + getID());
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = jfc.showDialog(new JLabel(), "ѡ��");
				File file = jfc.getSelectedFile();
				if (option == JFileChooser.CANCEL_OPTION)
					return;

				if (file.isFile()) {
					filePath = file.getAbsolutePath();
					fileName = jfc.getSelectedFile().getName();
					int result = JOptionPane.showConfirmDialog(null, "��Ҫ�����ļ����Ƿ������" + filePath, "Sure?",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						// ȷ����Ĵ���д����
						MyFolder mf = new MyFolder();
						File a = new File(filePath);
						File b = new File(filePath);
						try {
							mf.decryFiles(a, b, getID());
							JOptionPane.showMessageDialog(null, "��������ˣ�","���",JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException | ClassNotFoundException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "�ļ�����ʧ�ܣ������ļ��Ϸ��Ժ�Ȩ�ޣ�","����",JOptionPane.ERROR_MESSAGE);
						}
						System.out.println(filePath + "\t" + fileName);
					} else if (result == 1)
						return;
				} else {
					JOptionPane.showMessageDialog(null, "Pls choose a file", "Pls choose a file",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		decDirButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("decDIR--->current ID:" + getID());
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = jfc.showDialog(new JLabel(), "ѡ��");
				File dir = jfc.getSelectedFile();
				if (option == JFileChooser.CANCEL_OPTION)
					return;

				if (dir.isDirectory()) {
					dirPath = dir.getAbsolutePath();
					int result = JOptionPane.showConfirmDialog(null, "��Ҫ����Ŀ¼��ȫ���ļ����Ƿ������" + dirPath, "Sure?",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						// ȷ����Ĵ���д����
						MyFolder mf = new MyFolder();
						File a = new File(dirPath);
						File b = new File(dirPath+"/../encrypted");// ��Ҫ�޸Ğ��x��ָ����Ŀ�
						try {
							mf.decryFiles(a, b, getID());
							JOptionPane.showMessageDialog(null, "��������ˣ�","���",JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException | ClassNotFoundException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "�ļ�����ʧ�ܣ������ļ��Ϸ��Ժ�Ȩ�ޣ�","����",JOptionPane.ERROR_MESSAGE);
						}
						System.out.println(dirPath);
					} else if (result == 1)
						return;
				} else {
					JOptionPane.showMessageDialog(null, "Pls choose a directory", "Pls choose a directory",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

}
