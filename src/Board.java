/*
*    BreakoutUnlimitedDeluxe By Jarosław Zabuski
*	
*	 **LICENSE**
*
*	 This file is a part of BreakoutUnlimitedDeluxe.
*
*	 BreakoutUnlimitedDeluxe is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    BreakoutUnlimitedDeluxe is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with BreakoutUnlimitedDeluxe.  If not, see <http://www.gnu.org/licenses/>.
*/

//"Board" class handles all game logic and displays items on the screen

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.lang.Thread;
import javax.sound.sampled.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.TreeMap;

public class Board extends JPanel implements Runnable, Constants {
	//items on-screen
	private Paddle paddle;
	private Ball ball;
	private Brick[][] brick = new Brick[BRICK_COLUMNS][BRICK_ROWS];

	//initial values for important variables, like max amount of lives or the staring level
	protected int score = 0;
	private int lives = MAX_LIVES;
	private int waitTime = 3;
	private int withSound;
	protected int difficulty;
	private int level = 1;
	private int MAX_BRICKS = 0;
	private int bricksLeft = MAX_BRICKS;
	private double xSpeed;

	//player's name
	private String playerName;
	private String resumeLabel = "start";

	//the game's thread
	private Thread game;

	//songs and other stuff for background music
	private AudioInputStream audio;
	private Clip clip;

	//data structures to handle high scores
	private ArrayList<Item> items = new ArrayList<Item>();
	protected AtomicBoolean isPaused = new AtomicBoolean(true);

	//final destination of randomly generated level layout from templates
	private int[][] layout = new int[BRICK_COLUMNS][BRICK_ROWS];

	public Board(int width, int height, int diff, int soundOption,String name) {
		super.setSize(width, height);
		addMouseMotionListener(
				new MouseMotionListener(){
			public void mouseMoved(MouseEvent e) {
			paddle.setX(e.getX());
		}
			public void mouseDragged(MouseEvent e){}
			//this reacts when the mouse is clicked, moved, then released, but for now is unused
		}
		);
		playerName = name;
		if(playerName == null || playerName.isEmpty()) playerName = "jz";
		withSound = soundOption;
		difficulty = diff;
		addKeyListener(new BoardKeyListener());
		setFocusable(true);

		placeBricksOnBoard(difficulty);
		paddle = new Paddle(PADDLE_X_START, PADDLE_Y_START, PADDLE_WIDTH, PADDLE_HEIGHT, Color.BLACK);
		ball = new Ball(BALL_X_START, BALL_Y_START, BALL_WIDTH, BALL_HEIGHT, Color.BLACK);

		playMusic(trackList, withSound, level);

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Point hotSpot = new Point(0,0);
		BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
		setCursor(invisibleCursor);

		game = new Thread(this);
		game.start();
		stop();
		isPaused.set(true);
	}

	//fills the array of bricks
	public void placeBricksOnBoard(int difficulty) {
		Random rand = new Random();
		int layoutTemplateOne = rand.nextInt(LAYOUTS_NUMBER-1)+1;
		int layoutTemplateTwo = rand.nextInt(LAYOUTS_NUMBER-1)+1;
		if (layoutTemplateOne == layoutTemplateTwo) {
			layoutTemplateOne = 0;
		}

		for(int i = 0; i < BRICK_COLUMNS; i++) {
			for (int j = 0; j < BRICK_ROWS; j++) {
				layout[i][j] = layoutTemplates[j+BRICK_ROWS*layoutTemplateOne][i]
						+ layoutTemplates[j+BRICK_ROWS*layoutTemplateTwo][i];
			}
		}
		for(int i = 0; i < BRICK_COLUMNS; i++) {
			for(int j = 0; j < BRICK_ROWS; j++) {

				int itemType = rand.nextInt(3) + 1;
				Color color = Constants.colors[rand.nextInt(BRICK_ROWS)][0];
				if (layout[i][j] == 1) {
					brick[i][j] = new Brick((i * BRICK_WIDTH), ((j * BRICK_HEIGHT) + (BRICK_HEIGHT / 2)),
							BRICK_WIDTH, BRICK_HEIGHT, color, itemType, difficulty);
					MAX_BRICKS++;
				}
			}
		}
		bricksLeft = MAX_BRICKS;
	}

	//starts the thread
	public void start() {
		game.resume();
		isPaused.set(false);
	}

	//stops the thread
	public void stop() {
		game.suspend();
	}

	//runs the game
	public void run() {
		xSpeed = 1;
		while(true) {
			int x1 = (int)ball.getX();
			int y1 = (int)ball.getY();

			//Makes sure speed doesnt get too fast/slow

			checkIfPaddleHit(x1, y1);
			checkIfWallHit(x1, y1);
			checkIfBricksHit(x1, y1);
			checkLevelStatus();
			checkIfBallOut(y1);
			ball.move();
			dropItems();
			checkItemList();
			repaint();

			try {
				game.sleep(waitTime);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}

	public void addItem(Item i) {
		items.add(i);
	}

	public void dropItems() {
		for (int i = 0; i < items.size(); i++) {
			Item tempItem = items.get(i);
			tempItem.drop();
			items.set(i, tempItem);
		}
	}

	public void checkItemList() {
		for (int i = 0; i < items.size(); i++) {
			Item tempItem = items.get(i);
			if (paddle.caughtItem(tempItem)) {
				items.remove(i);
			}
			else if (tempItem.getY() > WINDOW_HEIGHT) {
				items.remove(i);
			}
		}
	}
//check whether player has completed the level, or failed to do so, by losing all lives
	public void checkLevelStatus() {
		if (bricksLeft == NO_BRICKS) {
			resumeLabel = "start";
			for (int i = 0; i < BRICK_COLUMNS; i++) {
				for (int j = 0; j < BRICK_ROWS; j++) {
					if (layout[i][j] == 1) brick[i][j].setDestroyed(false);
				}
			}
			stopMusic(trackList, withSound, level);
			paddle.reset();
			bricksLeft = MAX_BRICKS = 0;
			placeBricksOnBoard(difficulty);
			lives++;
			level++;
			ball.reset(level);
			score += 100;
			playMusic(trackList, withSound, level);
			isPaused.set(true);
			repaint();
			stop();
		}
		if (lives == MIN_LIVES) {
			isPaused.set(true);
			repaint();
			stop();
		}
	}

	public void checkIfPaddleHit(int x1, int y1) {
		switch(paddle.hitPaddle(x1, y1))
		{
			case 1:
				ball.setYDir(-1-(level-1)*Y_ADDED_SPEED);
				xSpeed = -1-(level-1)*X_ADDED_SPEED;
				ball.setXDir(xSpeed);
				break;
			case 2:
				ball.setYDir(-1.4-(level-1)*Y_ADDED_SPEED);
				xSpeed = -0.5-(level-1)*X_ADDED_SPEED;
				ball.setXDir(xSpeed);
				break;
			case 3:
				ball.setYDir(-1.4-(level-1)*Y_ADDED_SPEED);
				xSpeed = 0.5+(level-1)*X_ADDED_SPEED;
				ball.setXDir(xSpeed);
				break;
			case 4:
				ball.setYDir(-1-(level-1)*Y_ADDED_SPEED);
				xSpeed = 1+(level-1)*X_ADDED_SPEED;
				ball.setXDir(xSpeed);
				break;
		}

		if (paddle.getX() <= 0) {
			paddle.setX(0);
		}
		if (paddle.getX() + paddle.getWidth() >= getWidth()) {
			paddle.setX(getWidth() - paddle.getWidth());
		}
	}

	public void checkIfWallHit(int x1, int y1) {
		if (x1 >= getWidth() - ball.getWidth()) {
			xSpeed = -Math.abs(xSpeed);
			ball.setXDir(xSpeed);
		}
		if (x1 <= 0) {
			xSpeed = Math.abs(xSpeed);
			ball.setXDir(xSpeed);
		}
		if (y1 <= 0) {
			ball.setYDir(Math.abs(ball.getYDir()));
		}
		if (y1 >= getHeight()) {
			ball.setYDir(-Math.abs(ball.getYDir()));
		}
	}

	public void checkIfBricksHit(int x1, int y1) {
		for (int i = 0; i < BRICK_COLUMNS; i++) {
			for (int j = 0; j < BRICK_ROWS; j++) {
					if (layout[i][j] == 1 && brick[i][j].hitBottom(x1, y1)) {
						score += 50;
						ball.setYDir(Math.abs(ball.getYDir()));
						if (brick[i][j].isDestroyed()) {
							bricksLeft--;

							addItem(brick[i][j].item);
						}
					}
					if (layout[i][j] == 1 && brick[i][j].hitLeft(x1, y1)) {
						score += 50;
						xSpeed = -xSpeed;
						ball.setXDir(xSpeed);
						if (brick[i][j].isDestroyed()) {
							bricksLeft--;

							addItem(brick[i][j].item);
						}
					}
					if (layout[i][j] == 1 && brick[i][j].hitRight(x1, y1)) {
						score += 50;
						xSpeed = -xSpeed;
						ball.setXDir(xSpeed);
						if (brick[i][j].isDestroyed()) {
							bricksLeft--;

							addItem(brick[i][j].item);
						}
					}
					if (layout[i][j] == 1 && brick[i][j].hitTop(x1, y1)) {
						score += 50;
						ball.setYDir(-Math.abs(ball.getYDir()));
						if (brick[i][j].isDestroyed()) {
							bricksLeft--;

							addItem(brick[i][j].item);
						}
					}
			}
		}
	}

	public void checkIfBallOut(int y1) {
		if (y1 > PADDLE_Y_START + 10) {
			resumeLabel = "start";
			lives--;
			if (score > 100) {
				score -= 100;
			} else {
				score = 0;
			}
			ball.reset(level);
			isPaused.set(true);
			repaint();
			stop();

		}
	}

	//plays different music throughout game if user wants to
	public void playMusic(String[] songs, int yesNo, int level) {
		switch (yesNo) {
			case 1:
				return;
			case -1:
				System.exit(0);
		}

		level = (level-1)%4;

		try {
			audio = AudioSystem.getAudioInputStream(new File(songs[level]).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	public void stopMusic(String[] songs, int yesNo, int level) {
		switch (yesNo) {
			case 1:
				return;
			case -1:
				System.exit(0);
		}
		try {
			clip.stop();
			clip.close();
			audio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//fills the board
	@Override
	public void paintComponent(Graphics g) {
		Toolkit.getDefaultToolkit().sync();
		super.paintComponent(g);
		paddle.draw(g);
		ball.draw(g);
		FontMetrics font = g.getFontMetrics();

		for (int i = 0; i < BRICK_COLUMNS; i++) {
			for (int j = 0; j < BRICK_ROWS; j++) {
				if (layout[i][j] == 1) brick[i][j].draw(g);
			}
		}
		g.setColor(Color.BLACK);
		g.drawLine(0,PADDLE_Y_START+PADDLE_HEIGHT/2,WINDOW_WIDTH , PADDLE_Y_START+PADDLE_HEIGHT/2);
		g.drawString("Player: " + playerName, 10, getHeight() - (2*(getHeight()/10)) + 25);
		g.drawString("Lives: " + lives, 10, getHeight() - (getHeight()/10));
		g.drawString("Level: " + level,
				WINDOW_WIDTH - 10 - font.stringWidth("Level: " + level), getHeight() - ((getHeight()/10)));
		g.drawString("Score: " + score,
				WINDOW_WIDTH - 10 - font.stringWidth("Score: " + score), getHeight() - (2*(getHeight()/10)) + 25);



		if(isPaused.get() == true)
		{
			g.drawString("Press Space to " + resumeLabel + " !", 550, getHeight() - (getHeight()/10));
		}

		for (Item i: items) {
			i.draw(g);
		}


		if (lives == MIN_LIVES) {


			setCursor(Cursor.getDefaultCursor());

			g.setColor(Color.BLACK);
			g.fillRect(0,0,getWidth(),getHeight());
			g.setColor(Color.WHITE);
			g.drawString("Name: " + playerName +
					", Score: " + score + ", Level: " + level, getWidth()/5, 20);
			g.drawString("Game Over! Did you make it onto the high score table?", getWidth()/5, 50);
			try {
				printHighscoreTable(g);
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			g.drawString("Press the Spacebar twice to play again.", getWidth()/5, getHeight()-20);
		}
	}

	//makes sure the HighScores.txt file exists
	public void makeHighscoreTable() throws IOException {
		String filename = "HighScores";
		File f = new File(filename + ".txt");
		if (f.createNewFile()) {
			try {
				writeFakeScores();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	//if there was no previous high score table, this one inputs 10 fake players and scores to fill it
	public void writeFakeScores() throws IOException {
		Random rand = new Random();

		int numLines = 10;
		File f = new File("HighScores.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
		for (int i = 1; i <= numLines; i++) {
			int score = rand.nextInt(2000);
			if (numLines - i >= 1) {
				bw.write("Name: " + "Player" + i + ", " + "Score: " + score + "\n");
			}
			else {
				bw.write("Name: " + "Player" + i + ", " + "Score: " + score);
			}
		}
		bw.close();
		try {
			sortHighscoreTable();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	//returns the player's name and score formatted correctly
	public String returnPlayerInfo() {
		return "Name: " + playerName + ", Score: " + score;
	}

	//add game to high score file by appending it
	public void saveLastGame() throws IOException {
		File f = new File("HighScores.txt");
		FileWriter fw = new FileWriter(f.getAbsoluteFile(), true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append("\n" + returnPlayerInfo());
		bw.close();
	}

	//sorts the high score table high to low using maps
	public void sortHighscoreTable() throws IOException {
		File f = new File("HighScores.txt");
		File temp = new File("temp.txt");
		TreeMap<Integer, ArrayList<String>> topTen = new TreeMap<Integer, ArrayList<String>>();
		BufferedReader br = new BufferedReader(new FileReader(f.getAbsoluteFile()));
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp.getAbsoluteFile()));


		String line = null;
		while ((line = br.readLine()) != null) {
			if (line.isEmpty()) continue;

				String[] scores = line.split("Score: ");
				Integer score = Integer.valueOf(scores[1]);
				ArrayList<String> players = null;
				
				//make sure two players with same score are dealt with
				if ((players = topTen.get(score)) == null) {
					players = new ArrayList<String>(1);
					players.add(scores[0]);
					topTen.put(Integer.valueOf(scores[1]), players);
				}
				else {
					players.add(scores[0]);
				}

		}

		for (Integer score : topTen.descendingKeySet()) {
			for (String player : topTen.get(score)) {
				try {
					bw.append(player + "Score: " + score + "\n");
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
		br.close();
		bw.close();
		try {
			makeNewScoreTable();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	//save the sorted table to the high score file
	public void makeNewScoreTable() throws IOException {
		File f = new File("HighScores.txt");
		File g = new File("temp.txt");
		f.delete();
		g.renameTo(f);
	}

	public void printHighscoreTable(Graphics g) throws IOException {
		try {
			makeHighscoreTable();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		try {
			saveLastGame();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		try {
			sortHighscoreTable();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		int h = 100;
		File fileToRead = new File("HighScores.txt");
		LineNumberReader lnr = new LineNumberReader(new FileReader(fileToRead));
		String line = lnr.readLine();
		while (line != null && lnr.getLineNumber() <= 10) {
			int rank = lnr.getLineNumber();
			g.drawString(rank + ". " + line, getWidth()/5, h);
			h += 15;
			line = lnr.readLine();
		}
		lnr.close();
	}

	//class that handles key controls and resetting the game after game over
	private class BoardKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent ke) {
			int key = ke.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
				if (lives > MIN_LIVES) {
					if (isPaused.get() == false) {
						stop();
						resumeLabel = "resume";
						isPaused.set(true);
						repaint();

					}
					else {
						start();
					}
				}
				else {
					stopMusic(trackList, withSound, level);
					paddle.setWidth(PADDLE_WIDTH);
					resumeLabel = "start";
					lives = MAX_LIVES;
					score = 0;
					bricksLeft = MAX_BRICKS;
					level = 1;
					placeBricksOnBoard(difficulty);
					isPaused.set(true);
					ball.reset(level);
					playMusic(trackList, withSound, level);
					Toolkit toolkit = Toolkit.getDefaultToolkit();
					Point hotSpot = new Point(0,0);
					BufferedImage cursorImage = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
					Cursor invisibleCursor = toolkit.createCustomCursor(cursorImage, hotSpot, "InvisibleCursor");
					setCursor(invisibleCursor);
					for (int i = 0; i < BRICK_COLUMNS; i++) {
						for (int j = 0; j < BRICK_ROWS; j++) {
							if (layout[i][j] == 1) brick[i][j].setDestroyed(false);
						}
					}
				}
			}
		}
	}
}
