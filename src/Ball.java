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

public class Ball extends Structure implements Constants {
	private boolean onScreen;
	private double xDir = 1, yDir = -1;

	public Ball(int x, int y, int width, int height, Color color) {
		super(x, y, width, height, color);
		setOnScreen(true);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x, (int)y, width, height);
	}

	public void move() {
		x += xDir;
		y += yDir;
	}

	public void reset(int level) {
		x = BALL_X_START;
		y = BALL_Y_START;
		xDir = 1+(level-1)*X_ADDED_SPEED;
		yDir = -1-(level-1)*Y_ADDED_SPEED;
	}

	public void setXDir(double xDir) {
		this.xDir = xDir;
	}

	public void setYDir(double yDir) {
		this.yDir = yDir;
	}

	public void setOnScreen(boolean onScreen) {
		this.onScreen = onScreen;
	}

	public double getXDir() {
		return xDir;
	}

	public double getYDir() {
		return yDir;
	}

	public boolean isOnScreen() {
		return onScreen;
	}
}
