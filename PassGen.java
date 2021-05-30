import java.security.SecureRandom;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PassGen {

	public static String frmName = new String("Password generator");

	private static final String EN_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //26 symbols
	private static final String EN_LOW = "abcdefghijklmnopqrstuvwxyz"; //26 symbols
	private static final String NUMBERS = "0123456789"; //10 symbols
	private static final String SPECIAL_CHARS = "!@#$%^&*_=+-/"; //13 symbols

	public static void main(String[] args) {
		createWindow();
	}

	public static void createWindow() {
		//Initialize Form and components
		JFrame frame = new JFrame(frmName);
		JTextArea textArea = new JTextArea();
		JScrollPane scrollTA = new JScrollPane(textArea);
		JLabel lblLen = new JLabel("Password length:");
		JLabel lblCnt = new JLabel("Password count:");
		JButton btnGen = new JButton("Generate");
		JCheckBox chbEnCap = new JCheckBox("A-Z");
		JCheckBox chbEnLow = new JCheckBox("a-z");
		JCheckBox chbNumb = new JCheckBox("0-9");
		JCheckBox chbChars = new JCheckBox("!@#$%^&*_=+-/");
		JTextField txtPLen = new JTextField();
		JTextField txtPCnt = new JTextField();
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		JMenuItem menuItemSave = new JMenuItem("Save", KeyEvent.VK_T);
		JMenuItem menuItemExit = new JMenuItem("Exit", KeyEvent.VK_T);
		JMenuItem menuItemAbout = new JMenuItem("About", KeyEvent.VK_T);
		
		Font font = new Font(Font.MONOSPACED, Font.PLAIN, 14);
		textArea.setFont(font);

		//Build menu
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		//menuItemSave.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
		//menuItemExit.getAccessibleContext().setAccessibleDescription("This doesn't really do anything");
		menuFile.add(menuItemSave);
		menuFile.add(menuItemExit);
		menuHelp.add(menuItemAbout);

		//Set elements properties
		chbEnCap.setSelected(true);
		chbEnLow.setSelected(true);
		chbNumb.setSelected(true);
		chbChars.setSelected(true);
		textArea.setEditable(false);
		txtPLen.setText("10");
		txtPCnt.setText("5");
		textArea.append("Программа для генерации \n"+
						"паролей. Для того чтобы \n"+
						"сгенерировать пароль \n"+
						"необходимо выбрать \n"+
						"параметри и нажать кнопку \n"+
						"сгенерировать.");

		//Events
		KeyAdapter ka = new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if( !(Character.isDigit(c) || 
				(c == KeyEvent.VK_BACK_SPACE) || 
				(c == KeyEvent.VK_DELETE)) ) {
					e.consume();
				}
			}
		};
	    txtPLen.addKeyListener(ka);
		txtPCnt.addKeyListener(ka);
		btnGen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer cntPass = new Integer( Integer.parseInt( txtPCnt.getText() ) );
				Integer passLen = new Integer( Integer.parseInt( txtPLen.getText() ) );
				String strSymbols = new String("");

				if(chbEnCap.isSelected()) { strSymbols = strSymbols + EN_CAPS; }
				if(chbEnLow.isSelected()) { strSymbols = strSymbols + EN_LOW; }
				if(chbNumb.isSelected())  { strSymbols = strSymbols + NUMBERS; }
				if(chbChars.isSelected()) { strSymbols = strSymbols + SPECIAL_CHARS; }

				textArea.setText("");

				if(cntPass > 1) {
					for(int i = 0; i < cntPass; i++) {
						textArea.append(generatePassword(passLen, strSymbols) + "\n");
					}
				} else {
					textArea.setText(generatePassword(passLen, strSymbols));
				}
			}
		});

		//Set elements position @code{setBounds(x, y, width, height)}
		lblLen.setBounds  ( 10,  10,  110, 30 );
		txtPLen.setBounds ( 130, 10,  60,  30 );
		lblCnt.setBounds  ( 10,  40,  110, 30 );
		txtPCnt.setBounds ( 130, 40,  60,  30 );
		chbEnCap.setBounds( 10,  110, 180, 30 );
		chbEnLow.setBounds( 10,  140, 180, 30 );
		chbNumb.setBounds ( 10,  170, 180, 30 );
		chbChars.setBounds( 10,  200, 180, 30 );
		btnGen.setBounds  ( 10,  330, 180, 40 );
		textArea.setBounds( 200, 10,  190, 360);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 422);
		frame.setJMenuBar(menuBar);
		frame.add(lblLen);
		frame.add(lblCnt);
		frame.add(textArea);
		//frame.add(scrollTA);
		frame.add(btnGen);
		frame.add(chbEnCap);
		frame.add(chbEnLow);
		frame.add(chbNumb);
		frame.add(chbChars);
		frame.add(txtPLen);
		frame.add(txtPCnt);
		frame.setLayout(null);

		//Display the window.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static String generatePassword(int len, String dic) {
		SecureRandom random = new SecureRandom();
		String result = new String();
		Integer i;
		Integer index = new Integer(0);
		for(i = 0; i < len; i++) {
			index = random.nextInt(dic.length());
			result += dic.charAt(index);
		}
		return result;
	}

}