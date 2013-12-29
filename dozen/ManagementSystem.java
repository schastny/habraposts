package net.schastny.dozen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ManagementSystem {
	
	private static ManagementSystem ms;
	public int[] chips = new int[13]; // chips
	private int activePetal = 1; // 1-top; 2-right; 3-bottom; 4-left;

	private ManagementSystem() {
		for (int i = 0; i < 13; i++) {
			chips[i] = i + 1;
		}
	}
	
	public static ManagementSystem getInstance() {
		if (ms == null)
			ms = new ManagementSystem();
		return ms;
	}
	
	public String[] getChipsAsString() {
		String[] chipsString = new String[13];
		for (int i=0; i<13; i++){
			chipsString[i] = new Integer(chips[i]).toString();
		}
		return chipsString;
	}

	public boolean isRightOrder() {
		boolean result = true;
		for(int i=0; i<13; i++){
			if ( chips[i] != (i+1) ) 
				result = false;
		}
		return result;
	}
	
	public void setRightOrder(){
		for (int i = 0; i < 13; i++) {
			chips[i] = i + 1;
		}
	}
	
	public int getActivePetal() {
		return activePetal;
	}

	public void setActivePetal(int activePetal) {
		this.activePetal = activePetal;
	}

	// Смена активного лепестка (activePetal) по часовой стрелке 
	// (Для кнопок)
	public void jumpDown() {
		if (activePetal == 4)
			activePetal = 1;
		else
			activePetal++;
	}

	// Смена активного лепестка (activePetal) против часовой стрелки 
	// (Для кнопок)
	public void jumpUp() {
		if (activePetal == 1)
			activePetal = 4;
		else
			activePetal--;
	}

	// Поворот активного лепестка по часовой стрелке
	public void turnDown() {
		switch (activePetal) {
		case 1:
			moveDown(0, 1, 12, 11);
			break;
		case 2:
			moveDown(1, 2, 3, 4, 5, 12);
			break;
		case 3:
			moveDown(12, 5, 6, 7);
			break;
		case 4:
			moveDown(7, 8, 9, 10, 11, 12);
			break;
		}
	}

	// Поворот активного лепестка против часовой стрелки
	public void turnUp() {
		switch (activePetal) {
		case 1:
			moveUp(0, 1, 12, 11);
			break;
		case 2:
			moveUp(1, 2, 3, 4, 5, 12);
			break;
		case 3:
			moveUp(12, 5, 6, 7);
			break;
		case 4:
			moveUp(7, 8, 9, 10, 11, 12);
			break;
		}
	}

	// Поворот по часовой
	private void moveDown(int... val) {
		// Первую фишку - на вторую позицию, последнюю - на первую
		int last = chips[val[val.length - 1]];
		for (int i = val.length - 1; i > 0; i--) {
			chips[val[i]] = chips[val[i - 1]];
		}
		chips[val[0]] = last;
	}

	// Поворот против часовой
	private void moveUp(int... val) {
		int first = chips[val[0]];
		for (int i = 0; i < val.length - 1; i++) {
			chips[val[i]] = chips[val[i + 1]];
		}
		chips[val[val.length - 1]] = first;
	}

	public void drawPetals(){
		System.out.printf("%8d \n", chips[0]);
		System.out.printf("%4d%d%4d%2d \n", chips[10], chips[11], chips[1], chips[2]);
		System.out.printf("%2d%6d%6d \n", chips[9], chips[12], chips[3]);
		System.out.printf("%4d%d%4d%2d \n", chips[8], chips[7], chips[5], chips[4]);
		System.out.printf("%8d \n", chips[6]);
	}
	
	public static void main(String[] args) {
		ManagementSystem ms = new ManagementSystem();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String command = "";
			char letter;
			while (!command.equals("quit")){
				ms.drawPetals();
				command = in.readLine();
				letter = command.charAt(0);
				switch(letter){
					case 100: ms.turnDown(); break; // letter d
					case 97: ms.turnUp(); break; // letter a
					case 115: ms.jumpDown(); break; // letter s
					case 119: ms.jumpUp(); break; // letter w
					default: break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}