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

//"Structure" class is the base for all on-screen objects in the game

import java.awt.*;

public class Structure implements Constants {
	protected int  width, height;
	protected double x, y;
	protected Color color;

	public Structure(int x, int y, int width, int height, Color color) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setColor(color);
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}
}
