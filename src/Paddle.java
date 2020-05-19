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

import static java.lang.Math.*;

public class Paddle extends Structure implements Constants {
	private int xSpeed;

	public Paddle(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
	}

	public void reset() {
		x = PADDLE_X_START;
		y = PADDLE_Y_START;
	}

	//checks if the ball hit the paddle
	public int hitPaddle(int ballX, int ballY) {
		if ((ballX >= x) && (ballX <= x + width) && ((ballY >= y) && (ballY <= y + height)))
		{
			if (ballX >= x && ballX <= x + ceil(width/4)) return 1;
			else if (ballX >= x + ceil(width/4) && ballX <= x + round(width/2)) return 2;
			else if (ballX >= x + round(width/2) && ballX <= x + 3*floor(width/4)) return 3;
			else return 4;
		}
		return 0;
	}

	//resizes the paddle if it touches an item
	public boolean caughtItem(Item i) {
		if ((i.getX() < x + width) && (i.getX() + i.getWidth() > x) && (y == i.getY() || y == i.getY() - 1)) {
			i.resizePaddle(this);
			return true;
		}
		return false;
	}
}
