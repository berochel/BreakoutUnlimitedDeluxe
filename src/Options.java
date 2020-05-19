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

//"Options" class handles initial user dialogue and their choices on the aspects of game

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Options extends JPanel implements Runnable, Constants, ActionListener {

    private int withSound = 0;
    private int difficulty = 2;
    private JTextField nameField;
    private String name;

    public Options(int width, int height) {
        super(new BorderLayout());
        super.setSize(width, height);

        setFocusable(true);
        //Create the radio buttons.
        JRadioButton birdButton = new JRadioButton("Music Turned Off");
        birdButton.setActionCommand("Music Turned Off");

        nameField = new JTextField();
        nameField.setActionCommand("Text");

        JRadioButton catButton = new JRadioButton("Music Turned On");
        catButton.setActionCommand("Music Turned On");
        catButton.setSelected(true);

        JRadioButton dogButton = new JRadioButton("Easy");
        dogButton.setActionCommand("Easy");
        dogButton.setSelected(true);

        JRadioButton rabbitButton = new JRadioButton("Medium");
        rabbitButton.setActionCommand("Medium");

        JRadioButton pigButton = new JRadioButton("Hard");
        pigButton.setActionCommand("Hard");

        JButton startButton = new JButton("Start!");
        startButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        startButton.setHorizontalTextPosition(AbstractButton.CENTER);
        startButton.setActionCommand("Start");
        startButton.setPreferredSize(new Dimension(20, 20));


        //Group the radio buttons.
        ButtonGroup group1 = new ButtonGroup();
        group1.add(birdButton);
        group1.add(catButton);
        ButtonGroup group2 = new ButtonGroup();
        group2.add(dogButton);
        group2.add(rabbitButton);
        group2.add(pigButton);

        //Register a listener for the radio buttons.
        birdButton.addActionListener(this);
        catButton.addActionListener(this);
        dogButton.addActionListener(this);
        rabbitButton.addActionListener(this);
        pigButton.addActionListener(this);
        startButton.addActionListener(this);

        JLabel welcomeLabel = new JLabel("<html><<i>BreakoutUnlimitedDeluxe by Jarosław Zabuski</i>" +
                "<br>Insert your name:<br></html>");
        JLabel instructionsLabel = new JLabel("<html>Instructions: Mouse - Move paddle, Space Key - " +
                "Pause game. <br>Choose whether or not to play music:</html>");
        JLabel difficultyLabel = new JLabel("<html>Choose difficulty:</html>");

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
        buttonPanel.add(welcomeLabel);
        buttonPanel.add(nameField);
        buttonPanel.add(instructionsLabel);
        buttonPanel.add(birdButton);
        buttonPanel.add(catButton);
        buttonPanel.add(difficultyLabel);
        buttonPanel.add(dogButton);
        buttonPanel.add(rabbitButton);
        buttonPanel.add(pigButton);
        buttonPanel.add(startButton);


        add(buttonPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }
    @Override
    public void run() {

    };
    public void actionPerformed(ActionEvent f)
    {
        switch(f.getActionCommand())
        {
            case "Easy":
                difficulty = EASY;
                break;
            case "Medium":
                difficulty = MEDIUM;
                break;
            case "Hard":
                difficulty = HARD;
                break;
            case "Music Turned Off":
                withSound = 1;
                break;
            case "Music Turned On":
                withSound = 0;
                break;
            case "Text":
                break;
            case "Start":
                name = nameField.getText();
                int x = Main.frame.getX();
                int y = Main.frame.getY();
                Main.frame.dispose();
                Main.frame =  new JFrame("BreakoutUnlimitedDeluxe");
                Main.frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                Main.frame.setResizable(false);
                Main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                Main.board = new Board(WINDOW_WIDTH, WINDOW_HEIGHT, difficulty, withSound,name);

                Main.pane = Main.frame.getContentPane();
                Main.pane.add(Main.board);

                Main.frame.setContentPane(Main.pane);


                //place frame in the middle of the.screen
                Main.dim = Toolkit.getDefaultToolkit().getScreenSize();

                Main.frame.setLocation(x, y);

                //sets the icon of the program
                Main.frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));

                Main.frame.setVisible(true);
                break;
        }
    }
}