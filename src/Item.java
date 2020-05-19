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

public class Item extends Structure implements Constants {

	private int type;

	public Item(int x, int y, int width, int height, Color color, int type) {
		super(x, y, width, height, color);
		setType(type);
	}

	public void draw(Graphics g) {
		if(type == ITEM_EMPTY) {
			return;
		}
		g.setColor(color);
		g.fillRect((int)x, (int)y, width, height);
	}

	public void drop() {
		y += 1;
	}

	public void resizePaddle(Paddle p) {
		if (getType() == ITEM_BIGGER && p.getWidth() < PADDLE_MAX) {
			p.setWidth(p.getWidth() + 15);
		}
		else if (getType() == ITEM_SMALLER && p.getWidth() > PADDLE_MIN) {
			p.setWidth(p.getWidth() - 15);
		}
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
