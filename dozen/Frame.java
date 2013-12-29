package net.schastny.dozen;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Frame extends JFrame {

	private ManagementSystem ms = ManagementSystem.getInstance();
	private JPanel chipsFrame;
	private Vector<JPanel> chipsList = new Vector<JPanel>();
	private Vector<JPanel> petalsList = new Vector<JPanel>();

	public Frame() {
		super("Tricky Dozen");
		setBounds(250, 150, 450, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenuItem gameNewItem = new JMenuItem("Shuffling");
		gameNewItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int version = new Random().nextInt(100);
				Object res = JOptionPane.showInputDialog(null,
						"Please, enter combination number", "New combination",
						JOptionPane.QUESTION_MESSAGE, null, null, version);
				if (res != null) {
					Integer resI = new Integer(res.toString());
					randomizeButtons(resI);
				}
			}
		});
		gameMenu.add(gameNewItem);
		menuBar.add(gameMenu);
		setJMenuBar(menuBar);

		chipsFrame = new JPanel();
		chipsFrame.setLayout(null);
		chipsFrame.setBounds(0, 0, 450, 400);
		chipsFrame.setName("ChipsFrame");
		add(chipsFrame);
		
		initiateChips();
		initiatePetals();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Frame f = new Frame();
				f.setVisible(true);
			}
		});
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		System.out.println("OLOLO");
//		// JButton b = (JButton) e.getSource();
//		// moveButton(b.getText());
//	}

	private void randomizeButtons(int version) {
		ms.setRightOrder();
		Random r = new Random(version);
		for (int i = 0; i < 15; i++) {
			int mButton = r.nextInt(4) + 1;
			switch(mButton){
				case 1: ms.turnDown();break;
				case 2: ms.turnUp(); break;
				case 3: ms.jumpUp();break;
				case 4: ms.jumpDown();break;
				default: break;
			}
			Frame frame = (Frame) chipsFrame.getParent().getParent().getParent().getParent();
			frame.moveChips();
//			delay(250);
		}
	}
	
	private void delay(int miliSeconds){
		int cSec = Calendar.getInstance().get(Calendar.MILLISECOND);
		while(Math.abs(cSec - Calendar.getInstance().get(Calendar.MILLISECOND)) < miliSeconds){
		}
	}

	private void initiateChip(String value, int orderId, int x, int y) {
		int widthX = 50;
		int widthY = 50;
		JPanel b = new JPanel();
		
		JLabel lab = new JLabel(value);
		lab.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
		
		b.add(lab);
		b.setBounds(x, y, widthX, widthY);
		Border border = BorderFactory.createEtchedBorder(Color.white, new Color(178, 178, 178));
		b.setBorder(border);
		b.setBackground(Color.GREEN);
		chipsList.add(b);
		chipsFrame.add(b);
	}
	
	private void initiateChips(){
		initiateChip("1", 	1,  200, 50);
		initiateChip("2", 	2,  250, 100);
		initiateChip("3", 	3,  300, 100);
		initiateChip("4", 	4,  350, 150);
		initiateChip("5", 	5,  300, 200);
		initiateChip("6", 	6,  250, 200);
		initiateChip("7", 	7,  200, 250);
		initiateChip("8", 	8,  150, 200);
		initiateChip("9", 	9,  100, 200);
		initiateChip("10",  10, 50,  150);
		initiateChip("11",  11, 100, 100);
		initiateChip("12",  12, 150, 100);
		initiateChip("13",  13, 200, 150);
	}
	
	public void moveChips(){
		String[] chips = ms.getChipsAsString();
		for (int i=0; i<13; i++){
			JLabel l = (JLabel) chipsList.get(i).getComponent(0);
			l.setText(chips[i]);
		}
	}

	private void initiatePetals(){
		initiatePetal(1, 200, 100, 50, 50);
		initiatePetal(2, 250, 150, 100, 50);
		initiatePetal(3, 200, 200, 50, 50);
		initiatePetal(4, 100, 150, 100, 50);
	}
	
	private void initiatePetal(int petalId, int x, int y, int widthX, int widthY){
		JPanel b = new JPanel();
		b.setName(new Integer(petalId).toString());
		b.setBounds(x, y, widthX, widthY);
		Border border = BorderFactory.createEtchedBorder(Color.white, new Color(178, 178, 178));
		b.setBorder(border);
		b.setBackground(Color.GRAY);
		b.addMouseListener(new PetalMouseListener());
		petalsList.add(b);
		chipsFrame.add(b);
	}
	
}

class PetalMouseListener implements MouseListener{

	ManagementSystem ms = ManagementSystem.getInstance();
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		int mButton = arg0.getButton();
		//1 - левая клавиша - по часовой
		//3 - правая клавиша - против часовой
		switch(mButton){
			case 1: ms.turnDown();break;
			case 3: ms.turnUp();break;
			default: break;
		}
		Frame frame = (Frame) arg0.getComponent().getParent().getParent().getParent().getParent().getParent();
		frame.moveChips();
		if (ms.isRightOrder()){
			JOptionPane.showMessageDialog(
					frame,
					"Congratulations! Epic win!", 
					"You win!",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JPanel petal = (JPanel) arg0.getComponent();
		petal.setBackground(Color.DARK_GRAY);
		int petalNumber = new Integer(petal.getName());
		ms.setActivePetal(petalNumber);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		JPanel petal = (JPanel) arg0.getComponent();
		petal.setBackground(Color.GRAY);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
