package net.schastny.puzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Frame extends JFrame implements ActionListener {

	private JPanel bFrame;
	private final static int X = 75;

	private int[][] matrix = new int[4][4];
	private Vector<JButton> buttons = new Vector<JButton>();

	public Frame() {
		super("Fifteen puzzle");
		setBounds(250, 150, X * 4 + 8, X * 4 + 50);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		JMenu game = new JMenu("Game");
		JMenuItem gameNew = new JMenuItem("Shuffling");
		gameNew.addActionListener(new ActionListener() {
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
		game.add(gameNew);
		menuBar.add(game);
		setJMenuBar(menuBar);

		bFrame = new JPanel();
		bFrame.setLayout(null);
		add(bFrame);

		initiateMatrix();
		drawButtons();
	}

	public void initiateMatrix() {
		int value = 1;
		for (int j = 0; j < 4; j++) {
			for (int i = 0; i < 4; i++) {
				if (value != 16)
					matrix[i][j] = value++;
				else
					matrix[i][j] = 0;
			}
		}

		value = 1;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (value != 0) {
					JButton b = new JButton(Integer.toString(value++));
					b.addActionListener(this);
					buttons.add(b);
					bFrame.add(b);
				}
			}
		}
	}

	public void drawButtons() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				int val = matrix[i][j];
				if (val != 0) {
					buttons.get(val - 1).setBounds(X * i, X * j, X, X);
				}
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Frame f = new Frame();
				f.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		moveButton(b.getText());
	}

	private void moveButton(String b) {
		int val = new Integer(b);
		int direction = findHole(val);
		if (direction != 0)
			doMoving(val, direction);
	}

	private int findHole(int val) {
		int ii = 0;
		int jj = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == val) {
					ii = i;
					jj = j;
				}
			}
		}
		int leftCell = (jj == 0) ? -1 : matrix[ii][jj - 1];
		int rightCell = (jj == 3) ? -1 : matrix[ii][jj + 1];
		int topCell = (ii == 0) ? -1 : matrix[ii - 1][jj];
		int bottomCell = (ii == 3) ? -1 : matrix[ii + 1][jj];
		int result = 0;
		if (leftCell == 0)
			result = 1;
		if (rightCell == 0)
			result = 2;
		if (topCell == 0)
			result = 3;
		if (bottomCell == 0)
			result = 4;
		return result;
	}

	private void doMoving(int val, int direction) {
		int ii = 0;
		int jj = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (matrix[i][j] == val) {
					ii = i;
					jj = j;
				}
			}
		}
		matrix[ii][jj] = 0;
		switch (direction) {
			case 1:jj--;break;
			case 2:jj++;break;
			case 3:ii--;break;
			case 4:ii++;break;
		}
		matrix[ii][jj] = val;
		JButton b = buttons.elementAt(val - 1);
		b.setBounds(X * ii, X * jj, X, X);
	}

	private void randomizeButtons(int version) {
		initiateMatrix();
		drawButtons();
		Random r = new Random(version);
		for (int i = 0; i < 1000; i++) {
			Integer random = r.nextInt(15) + 1;
			moveButton(random.toString());
		}
	}

}
