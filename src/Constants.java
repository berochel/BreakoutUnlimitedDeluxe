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

import java.awt.Color;

public interface Constants {
	public static final int WINDOW_WIDTH = 1260;
	public static final int WINDOW_HEIGHT = 600; //400 for automode, 600 for normal gameplay;

	public static final int MAX_LIVES = 5;
	public static final int MIN_LIVES = 0;

	public static final int BALL_WIDTH = 10;
	public static final int BALL_HEIGHT = 10;
	public static final int BALL_X_START = 600;
	public static final int BALL_Y_START = 350;
	public static final double X_ADDED_SPEED = 0.2;
	public static final double Y_ADDED_SPEED = 0.2;

	public static final int PADDLE_WIDTH = 70;
	public static final int PADDLE_HEIGHT = 10;
	public static final int PADDLE_X_START = 600;
	public static final int PADDLE_Y_START = 450;
	public static final int PADDLE_MIN = 35;
	public static final int PADDLE_MAX = 170;

	public static final int BRICK_COLUMNS = 21;
	public static final int BRICK_ROWS = 7;
	public static final int BRICK_WIDTH = 60;
	public static final int BRICK_HEIGHT = 35;
	public static final int NO_BRICKS = 0;

	//colors used to color the bricks, paddle, ball etc.
	public static final Color BLUE_BRICK_ONE = new Color(0,0,255);
	public static final Color BLUE_BRICK_TWO = new Color(28,134,238);
	public static final Color BLUE_BRICK_THREE = new Color(0,191,255);
	public static final Color RED_BRICK_ONE = new Color(255,0,0);
	public static final Color RED_BRICK_TWO = new Color(255,106,106);
	public static final Color RED_BRICK_THREE = new Color(238,140,140);
	public static final Color PINK_BRICK_ONE = new Color(205,0,205);
	public static final Color PINK_BRICK_TWO = new Color(238,130,210);
	public static final Color PINK_BRICK_THREE = new Color(245,200,245);
	public static final Color GRAY_BRICK_ONE = new Color(77,77,77);
	public static final Color GRAY_BRICK_TWO = new Color(125,125,125);
	public static final Color GRAY_BRICK_THREE = new Color(179,179,179);
	public static final Color GREEN_BRICK_ONE = new Color(0,139,0);
	public static final Color GREEN_BRICK_TWO = new Color(0,205,0);
	public static final Color GREEN_BRICK_THREE = new Color(150,255,150);
	public static final Color ORANGE_BRICK_ONE = new Color(255,150,0);
	public static final Color ORANGE_BRICK_TWO = new Color(255,150,100);
	public static final Color ORANGE_BRICK_THREE = new Color(255,200,170);
	public static final Color YELLOW_BRICK_ONE = new Color(255,215,0);
	public static final Color YELLOW_BRICK_TWO = new Color(255,255,0);
	public static final Color YELLOW_BRICK_THREE = new Color(255,246,143);
	public Color[] blueColors = {BLUE_BRICK_ONE, BLUE_BRICK_TWO, BLUE_BRICK_THREE, Color.BLACK};
	public Color[] redColors = {RED_BRICK_ONE, RED_BRICK_TWO, RED_BRICK_THREE, Color.BLACK};
	public Color[] orangeColors = {ORANGE_BRICK_ONE, ORANGE_BRICK_TWO, ORANGE_BRICK_THREE, Color.BLACK};
	public Color[] yellowColors = {YELLOW_BRICK_ONE, YELLOW_BRICK_TWO, YELLOW_BRICK_THREE, Color.BLACK};
	public Color[] pinkColors = {PINK_BRICK_ONE, PINK_BRICK_TWO, PINK_BRICK_THREE, Color.BLACK};
	public Color[] grayColors = {GRAY_BRICK_ONE, GRAY_BRICK_TWO, GRAY_BRICK_THREE, Color.BLACK};
	public Color[] greenColors = {GREEN_BRICK_ONE, GREEN_BRICK_TWO, GREEN_BRICK_THREE, Color.BLACK};
	public Color[][] colors = {blueColors, redColors, orangeColors, yellowColors, pinkColors, grayColors, greenColors};


	//basic templates of level layouts in Brick Breaker
	public final short[][] layoutTemplates = {
			//template 0 - diamonds
			{0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0},
			{1,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,0,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0},
			{0,0,1,1,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,0,0},
			{0,0,0,1,1,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,0,1,1,1,0,0,0,1,1,1,1,1,1,1,1,1,1,1},
			{0,0,0,0,0,1,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0},
			//template 1 - two gems
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,1,1,1,1,1,0,0,0,0,0,1,1,1,1,1,0,0,0},
			{0,0,0,0,1,1,1,0,0,0,0,0,0,0,1,1,1,0,0,0,0},
			{0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			//template 2 - idk, lattice, maybe
			{0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0},
			//template 3 - two sneaks
			{0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},
			{0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0},
			{1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1},
			{0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0},
			{1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1},
			{0,0,0,1,0,1,0,0,0,1,0,1,0,0,0,1,0,1,0,0,0},
			{0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0},
			//template 4 - void
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			//template 5 - some pixel tiles i found on g**gle
			{1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1},
			{1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1},
			{0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,0},
			{1,1,0,0,0,1,1,1,1,0,0,0,1,1,1,1,0,0,0,1,1},
			{0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,0},
			{1,0,0,1,0,0,1,1,0,0,1,0,0,1,1,0,0,1,0,0,1},
			{1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1},
			//template 6 - blocks with varying sizes for mutual fun and joy of the whole family
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{0,0,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,0,0},
			{1,0,1,1,1,0,1,0,1,1,1,1,1,0,1,0,1,1,1,0,1},
			{0,0,1,1,1,0,0,0,1,1,1,1,1,0,0,0,1,1,1,0,0},
			{0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
			//template 7 - summ star shape like tiles
			{1,0,1,0,1,0,1,1,0,1,0,1,0,1,1,0,1,0,1,0,1},
			{0,1,1,0,1,1,0,0,1,1,0,1,1,0,0,1,1,0,1,1,0},
			{1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1},
			{0,0,1,0,1,0,0,0,0,1,0,1,0,0,0,0,1,0,1,0,0},
			{1,1,0,1,0,1,1,1,1,0,1,0,1,1,1,1,0,1,0,1,1},
			{0,1,1,0,1,1,0,0,1,1,0,1,1,0,0,1,1,0,1,1,0},
			{1,0,1,0,1,0,1,1,0,1,0,1,0,1,1,0,1,0,1,0,1},
	};
	public static final int LAYOUTS_NUMBER = layoutTemplates.length/7;

	public static final  String songOne = "a.wav";
	public static final  String songTwo = "b.wav";
	public static final  String songThree = "c.wav";
	public static final  String songFour = "d.wav";
	public static final  String[] trackList = {songOne, songTwo, songThree, songFour};

	public static final int ITEM_WIDTH = 20;
	public static final int ITEM_HEIGHT = 10;
	public static final int ITEM_BIGGER = 1;
	public static final int ITEM_SMALLER = 2;
	public static final int ITEM_EMPTY = 3;

	public static final int HARD = 0;
	public static final int MEDIUM = 1;
	public static final int EASY = 2;
}
