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

import java.awt.*;
import java.util.Random;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

public class Brick extends Structure implements Constants {
	private int lives = 3, hits;
	private boolean destroyed;
	public Item item;
	private Color itemColor;


	public Brick(int x, int y, int width, int height, Color color, int itemType, int difficulty) {
		super(x, y, width, height, color);
		setHits(difficulty);
		setDestroyed(false);

		if (itemType == 1) {
			itemColor = Color.GREEN;
		}
		if (itemType == 2) {
			itemColor = Color.RED;
		}

		//places an item inside the brick to fall when the brick is destroyed
		item = new Item(x + (width / 4), y + (height / 4),
				ITEM_WIDTH, ITEM_HEIGHT, itemColor, itemType);
	}

	@Override
	public void draw(Graphics g) {
		if (!destroyed) {
			g.setColor(color);
			g.fillRect((int)x, (int)y, width, height);
		}
	}

	//add a hit to the brick, and destroy the brick when hits == lives
	public void addHit() {
		hits++;
		nextColor();
		if (hits == lives) {
			setDestroyed(true);
		}
	}

	//change color to get lighter until the brick is destroyed
	public void nextColor() {
		if (color == colors[0][0] || color == colors[0][1] || color == colors[0][2]) {
			color = blueColors[hits];
		}
		if (color == colors[1][0] || color == colors[1][1] || color == colors[1][2]) {
			color = redColors[hits];
		}
		if (color == colors[2][0] || color == colors[2][1] || color == colors[2][2]) {
			color = orangeColors[hits];
		}
		if (color == colors[3][0] || color == colors[3][1] || color == colors[3][2]) {
			color = yellowColors[hits];
		}
		if (color == colors[4][0] || color == colors[4][1] || color == colors[4][2]) {
			color = pinkColors[hits];
		}
		if (color == colors[5][0] || color == colors[5][1] || color == colors[5][2]) {
			color = grayColors[hits];
		}
		if (color == colors[6][0] || color == colors[6][1] || color == colors[6][2]) {
			color = greenColors[hits];
		}
	}

	//collision detection

	public boolean hitBottom(int ballX, int ballY) {
		if ((ballX >= x) && (ballX <= x + width + 1) && (ballY <= floor(y + height))
				&& (ballY >= ceil(y + height- 3)) && (!destroyed)) {
			addHit();
			return true;
		}
		return false;
	}

	public boolean hitTop(int ballX, int ballY) {
		if ((ballX >= x) && (ballX <= x + width + 1) && (ballY >= ceil(y))
				&& (ballY <= floor(y + 3)) && (!destroyed)) {
			addHit();
			return true;
		}
		return false;
	}

	public boolean hitLeft(int ballX, int ballY) {
		if ((ballY >= y) && (ballY <= y + height) && (ballX >= floor(x))
				&& (ballX <= ceil(x + 3)) && (!destroyed)) {
			addHit();
			return true;
		}
		return false;
	}

	public boolean hitRight(int ballX, int ballY) {
		if ((ballY >= y) && (ballY <= y + height) && (ballX <= ceil(x + width))
				&& (ballX >= floor(x + width - 3)) && (!destroyed)) {
			addHit();
			return true;
		}
		return false;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public void setDestroyed(boolean destroyed) {
		this.destroyed = destroyed;
	}

	public int getLives() {
		return lives;
	}

	public int getHits() {
		return hits;
	}

	public boolean isDestroyed() {
		return destroyed;
	}
}
