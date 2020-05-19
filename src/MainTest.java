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
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

class MainTest extends Main {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        Main.board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT, 2, 1,"jz");
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void testMain() {
        try {
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_SPACE);
        assert(board.difficulty == 2);
        assert(board.isPaused.get()) ;
            System.out.println("ok");
        } catch (AWTException ae) {
            ae.printStackTrace();
        }
    }
   @org.junit.jupiter.api.Test
    void testBallSpeed() {
            Ball ball = new Ball(5,5,20,20,Color.BLACK);
            assert(ball.getX()== 5);
            assert(ball.getY()== 5);
            assert(ball.getXDir() == 1);
            assert(ball.getYDir() == -1);
            ball.reset(10);
            assert(ball.getX() == Constants.BALL_X_START);
            assert(ball.getY() == Constants.BALL_Y_START);
            assert(ball.getXDir() == 1+9*X_ADDED_SPEED);
            assert(ball.getYDir() == -1-9*Y_ADDED_SPEED);
            ball.move();
            ball.move();
            assert(ball.getY() == Constants.BALL_Y_START+2*(-1-9*Y_ADDED_SPEED));

            System.out.println("ok");

    }
    @org.junit.jupiter.api.Test
    void testCollision() {
        Ball ball = new Ball(5,5,20,20,Color.BLACK);
        assert(ball.getX()== 5);
        assert(ball.getY()== 5);
        assert(ball.getXDir() == 1);
        assert(ball.getYDir() == -1);
        ball.reset(10);
        assert(ball.getX() == Constants.BALL_X_START);
        assert(ball.getY() == Constants.BALL_Y_START);
        assert(ball.getXDir() == 1+9*X_ADDED_SPEED);
        assert(ball.getYDir() == -1-9*Y_ADDED_SPEED);
        ball.move();
        Brick brick = new Brick((BALL_X_START), ((BALL_Y_START))-4,
                BRICK_WIDTH, BRICK_HEIGHT, Color.BLACK, 2, 1);
        assert(!brick.isDestroyed());
        assert(brick.hitTop((int)ball.getX(),(int)ball.getY()));
        System.out.println("ok");
    }
    @org.junit.jupiter.api.Test
    void testMusic() {
        try {
        String songOne = "a.wav";
        String songTwo = "b.wav";
        String songThree = "c.wav";
        String songFour = "d.wav";
        String[] trackList = {songOne, songTwo, songThree, songFour};
        AudioInputStream audio;
        Clip clip;

        audio = AudioSystem.getAudioInputStream(new File(trackList[1]).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audio);
        assert(clip.isOpen());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

}