package crypto_backup;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class EncDialog {

	private JFrame Encframe;
	private String dirPath, fileName, filePath;
	private String ID;

	public void setID(String id) {
		// System.out.println(id);
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
	 * Runnable() { public void run() { try { EncDialog window = new
	 * EncDialog(); window.Encframe.setVisible(true); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public void closeDialog() {
		this.Encframe.setVisible(false);
		this.Encframe = null;
	}

	public void openDialog() {
		this.Encframe.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public EncDialog() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Encframe = new JFrame();
		Encframe.setTitle("\u52A0\u5BC6");
		Encframe.setBounds(100, 100, 450, 300);
		Encframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Encframe.getContentPane().setLayout(null);

		JButton encFileButton = new JButton("\u52A0\u5BC6\u5355\u4E2A\u6587\u4EF6");
		encFileButton.setBounds(82, 131, 117, 38);
		Encframe.getContentPane().add(encFileButton);

		JButton encDirButton = new JButton("\u52A0\u5BC6\u76EE\u5F55");
		encDirButton.setBounds(248, 131, 117, 38);
		Encframe.getContentPane().add(encDirButton);

		JLabel lblNewLabel = new JLabel("\u8BF7\u9009\u62E9\u64CD\u4F5C");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(167, 53, 100, 30);
		Encframe.getContentPane().add(lblNewLabel);

		JButton backButton = new JButton("\u8FD4\u56DE");
		backButton.setBounds(331, 228, 93, 23);
		Encframe.getContentPane().add(backButton);

		encFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("encFile--->current ID:" + getID());
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int option = jfc.showDialog(new JLabel(), "选择");
				File file = jfc.getSelectedFile();
				if (option == JFileChooser.CANCEL_OPTION)
					return;

				if (file.isFile()) {
					filePath = file.getAbsolutePath();
					fileName = jfc.getSelectedFile().getName();

					int result = JOptionPane.showConfirmDialog(null, "将要加密文件，是否继续？" + filePath, "Sure?",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						// 确定后的代码写这里
						MyFolder mf = new MyFolder();
						File a = new File(filePath);
						File b = new File(filePath);
						try {
							mf.encryFiles(a, b, getID());
							JOptionPane.showMessageDialog(null, "加密完成了！","完成",JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "文件加密失败，请检查文件合法性和权限！","错误",JOptionPane.ERROR_MESSAGE);
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

		encDirButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println("encDIR--->current ID:" + getID());
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int option = jfc.showDialog(new JLabel(), "选择");
				File dir = jfc.getSelectedFile();
				if (option == JFileChooser.CANCEL_OPTION)
					return;

				if (dir.isDirectory()) {
					dirPath = dir.getAbsolutePath();
					int result = JOptionPane.showConfirmDialog(null, "将要加密目录下全部文件，是否继续？" + dirPath, "Sure?",
							JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						// 确定后的代码写这里
						MyFolder mf = new MyFolder();
						File a = new File(dirPath);
						File b = new File(dirPath+"/../encrypted");//需要修改為選擇指定的目錄
						try {
							mf.encryFiles(a, b, getID());
							JOptionPane.showMessageDialog(null, "加密完成了！","完成",JOptionPane.INFORMATION_MESSAGE);
						} catch (IOException e1) {
							// TODO 自动生成的 catch 块
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "文件加密失败，请检查文件合法性和权限！","错误",JOptionPane.ERROR_MESSAGE);
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

		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Entrance entrance = new Entrance();
				entrance.openDialog();
				closeDialog();
			}
		});
		// System.out.println(getID());

	}

}
