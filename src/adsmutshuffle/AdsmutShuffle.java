/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsmutshuffle;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.security.*;
import java.io.*;
import java.util.Timer;
import java.util.*;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.pushingpixels.substance.api.skin.*;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.birosoft.liquid.LiquidLookAndFeel;


/**
 *
 * @author ADMUDS
 */
public class AdsmutShuffle extends JFrame{
    JButton charButton[];
    JButton freeButton;
    JPanel infoPanel;
    JPanel panel;
    Rectangle[] bounds;
    Rectangle freeBB;   //original bound for the free button
    JTextArea quoteClicks;
    JTextArea quote;
    
    
    String[] charsPreffered;
    public JButton changeColorButton;
    int normalTilePosition[];
    String tileTexts[];
    
    int restartCount=0;
    int buttonOnUse;
    int countClick=0;
    int count2=0;
    
    public static int gameMode;
    public static int nTilesCorrectlyPlaced;
    boolean gamePaused=false;
    boolean sound=true;
    char[] displayChar;
    
    public static final int NUMBER_OF_TILES=16;
    private static JRadioButton[] rad2;
    public static AdsmutShuffle aChallenge;
    public static int expectedClicks;
    public static JLabel avatar;
    public static JLabel cAvatar[];
    public static String[] playerRankDetails;
    public static double[] playerScore;
    public static int expectedTime;
    
    public JTextArea timeField;
    public JButton chalengeModeButton;
    public JButton pausePlayButton;
    public JButton soundOnButton;
    public JButton highScoreButton;
    public JButton changeCharTypeButton;
    public JButton viewProgressButton;
    public JButton playButton;
    public JTextField timeFieldC;
    public JLabel timeLabel;
    public JTextField clicksField;
    public JLabel clicksLabel;
    public JButton cancelChallengeButton;
    public int currentExpectedTime;
    public String singlePlayerName;
    
    private JButton cFriendsButton;
    private JButton cYSButton;
    private JButton cPlayButton;
    private JButton editChallengeButton;
    private JFrame challengeFrame;
    private JPanel challengePanel;
    private JButton startButton;
    private JTextField[] challengePlayersField;
    private static String[] challengePlayersName;
    private static int numberOfPlayers;
    private JRadioButton randomPlayersArrangementRad;
    private static int challengeType;
    
    
    
    public AdsmutShuffle() {
    
    }
    public void showGamePane(){
    // Set the title
    setTitle("Adsmut Shuffle");
    setLayout(null);
    // Set the default close operation
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(Toolkit.getDefaultToolkit().getScreenSize());
    //setSize(getLH(650, 1), getLH(650, 2));
    setLocationRelativeTo(null);
    setResizable(false);
    
    // Create a panel
     panel = new JPanel();
    // Set the panel to use FlowLayout
    panel.setLayout(null);
    panel.setBackground(Color.RED);
    
    
    infoPanel = new JPanel();
    // Set the panel to use FlowLayout
    infoPanel.setBounds(getLH(400, 1), getLH(10, 2), getLH(220, 1), getLH(570, 2));
    infoPanel.setLayout(null);
    infoPanel.setBackground(Color.WHITE);
    add(infoPanel);

    quote=new JTextArea();
    quote.setBounds(getLH(10, 1), getLH(400, 2), getLH(100, 1), getLH(35, 2));
    quote.setFont(new Font("Monospaced", Font.BOLD, getLH(16, 2)));
    infoPanel.add(quote);
    
    quoteClicks = new JTextArea();
    quoteClicks.setBounds(getLH(110, 1), getLH(400, 2), getLH(100, 1), getLH(35, 2));
    quoteClicks.setFont(new Font("Monospaced", Font.BOLD, getLH(16, 2)));
    infoPanel.add(quoteClicks);
    
    timeField = new JTextArea();
    timeField.setBounds(getLH(70, 1), getLH(400, 2), getLH(100, 1), getLH(35, 2));
    timeField.setFont(new Font("Monospaced", Font.BOLD, getLH(16, 2)));
    infoPanel.add(timeField);
    
    //On sound
    ImageIcon soundOn = new ImageIcon(".//res//sound on.png");
    ImageIcon soundOff = new ImageIcon(".//res//sound off.png");
    soundOnButton = new JButton(soundOn);
    soundOnButton.setBounds(getLH(10, 1), getLH(490, 2), soundOnButton.getPreferredSize().width, soundOnButton.getPreferredSize().height);
    infoPanel.add(soundOnButton);
    
    soundOnButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            ShuffleSound.setSoundStatus(!sound);
            if(sound){
                soundOnButton.setIcon(soundOff);
                sound = false;
                //setEnabled(false);
            }
            else{
                soundOnButton.setIcon(soundOn);
                sound = true;
            }
        }
    });
    
    //pause and play
    ImageIcon pauseIcon=new ImageIcon(".//res//pause.png");
    ImageIcon playIcon=new ImageIcon(".//res//play.png");
    pausePlayButton=new JButton(pauseIcon);
    pausePlayButton.setBounds(getLH(130, 1), getLH(490, 2), pausePlayButton.getPreferredSize().width, pausePlayButton.getPreferredSize().height);
    infoPanel.add(pausePlayButton);
    
    pausePlayButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            gamePaused =! gamePaused;
            if(gamePaused){
                pausePlayButton.setIcon(playIcon);
            }
            else{
                pausePlayButton.setIcon(pauseIcon);
                startTime();
            }
            pauseGame();
        }
    });
    
    //challenge mode button
    chalengeModeButton=new JButton("Enter Challenge Mode");
    chalengeModeButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    chalengeModeButton.setBounds(getLH(10, 1), getLH(10, 2), getLH(200, 1), getLH(30, 2));
    chalengeModeButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            //entered challenge mode
            showChallengePane();
        }
    });
    infoPanel.add(chalengeModeButton);
    
    JButton exit=new JButton("Exit");
        exit.setFont(new Font("Georgia", Font.BOLD, getLH(16, 2)));
        exit.setBounds(getLH(550, 1), getLH(590, 2), exit.getPreferredSize().width, exit.getPreferredSize().height);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                int choice=JOptionPane.showConfirmDialog(null, "Exit", "Confirm to exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(choice==JOptionPane.OK_OPTION)
                    System.exit(0);
                }
            });
                                    
        add(exit);
    
    //Button to change background
    changeColorButton=new JButton("Change background");
    changeColorButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    changeColorButton.setBounds(getLH(10, 1), getLH(50, 2), getLH(200, 1), getLH(30, 2));
    changeColorButton.setEnabled(false);
    changeColorButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent evt){
            Color color = JColorChooser.showDialog(null, "Choose a color", chalengeModeButton.getBackground());
            for(int i=0; i<charButton.length; i++){
                charButton[i].setBackground(color);
            }
        }
    });

    infoPanel.add(changeColorButton);
    
    //view high scores button
    highScoreButton=new JButton("View High Scores");
    highScoreButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    highScoreButton.setBounds(getLH(10, 1), getLH(90, 2), getLH(200, 1), getLH(30, 2));
    highScoreButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            
        }
    });
    infoPanel.add(highScoreButton);
    
    //change character types button
    changeCharTypeButton=new JButton("Change Character type");
    changeCharTypeButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    changeCharTypeButton.setBounds(getLH(10, 1), getLH(130, 2), getLH(200, 1), getLH(30, 2));
    changeCharTypeButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            showCharTypeDialog();
        }
    });
    infoPanel.add(changeCharTypeButton);
    
    viewProgressButton=new JButton("View Your Progress");
    viewProgressButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    viewProgressButton.setBounds(getLH(10, 1), getLH(170, 2), getLH(200, 1), getLH(30, 2));
    viewProgressButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            showProgressPane();
        }
    });
    infoPanel.add(viewProgressButton);
    
    //play welcome sound
    ShuffleSound.playSound("welcome");
    
    // Create some buttons
    //the buttons repesent the tiles in this game
    displayChar=new char[15];
    displayChar[0]='A';
    int convert=(int) (displayChar[0]);
    for(int i=0; i<displayChar.length; i++){
        displayChar[i]=(char) (convert+i);
    }
    
    //THE main panel the holds the tiles
    panel.setBounds(getLH(10, 1), getLH(10, 2), getLH(382, 1), getLH(570, 2));
    int wDifference = (getLH(382, 1)-382)/4;
    int hDifference = (getLH(570, 2)-570)/4;
    int tileWidth = 94 + wDifference;
    int tileHeigth = 141 + hDifference;
    
    tileTexts=new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O"};
    charButton=new JButton[15];
    for(int i=0; i<charButton.length; i++){
        charButton[i]=new JButton(String.format("%c", displayChar[i]));
        charButton[i].setFont(new Font("Monospaced", Font.BOLD, 100));
        charButton[i].setBounds(3+tileWidth*(i%4), 3+(i/4)*tileHeigth, tileWidth, tileHeigth);
        int index=i;
        charButton[i].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                move(index);
            }
    });
        charButton[i].setEnabled(false);
        panel.add(charButton[i]);
        
    }
    //This method is called to save all the bounds for each of the tiles. 
    //This game uses bounds for tile placement and movement and there might be different bounds for tiles on diffrent PC.
    saveTilesBound();
    //working on the free button
    freeButton=new JButton(" ");
    freeButton.setFont(new Font("Monospaced", Font.BOLD, 100));
    freeButton.setBounds(charButton[NUMBER_OF_TILES-2].getBounds().x+charButton[NUMBER_OF_TILES-2].getBounds()
            .width, charButton[NUMBER_OF_TILES-2].getBounds().y, charButton[NUMBER_OF_TILES-2].getBounds()
                    .width, charButton[NUMBER_OF_TILES-2].getBounds().height);
    panel.add(freeButton);
    freeBB=freeButton.getBounds();
    
    playButton=new JButton("Play free mode");
    playButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    playButton.setBounds(getLH(10, 1), getLH(585, 2), getLH(382, 1), getLH(30, 2));
    playButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            gameMode=1;
            restartCount++;
            changeColorButton.setEnabled(true);
            //startTime();
            if(restartCount>1){
                int res = JOptionPane.showConfirmDialog(null, "The game will restart and the initial progress will be lost", "Restart", JOptionPane.INFORMATION_MESSAGE);
                if(res == JOptionPane.YES_OPTION){
                    adsShuf=new AdsmutShuffle();
                    adsShuf.showGamePane();
                    infoPanel.add(timeArea);
                    shuffleGame();
                    //inversion: to mke sure that the shuffled tiles are solvable
                    while(!isSolvable()){
                        shuffleGame();
                    }
                    playButton.setText("Restart");
                }
            }
            else{
                startTime();
                infoPanel.add(timeArea);
                shuffleGame();
                //inversion
                while(!isSolvable()){
                    shuffleGame();
                }
                //System.out.println(getPresentTilePosition(charButton[i].getBounds()));
                playButton.setText("Restart");
            }
            
        }
    });
    add(playButton);
    panel.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.BLACK));
    
    // Add the panel to the frame
    add(panel);
    setVisible(true);
  }
  public void showProgressPane(){
    JFrame progressFrame=new JFrame("Your Progress");
    JPanel progressPanel=new JPanel();
    progressPanel.setSize(getLH(410, 1), getLH(430, 2));
    progressPanel.setLayout(null);
    progressPanel.setBackground(Color.WHITE);

    JLabel topLabel=new JLabel("Your game progress. You can do it!");
    topLabel.setFont(new Font("Aharoni", Font.BOLD, getLH(23, 2)));
    topLabel.setBounds(10, 10, topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
    progressPanel.add(topLabel);


    progressPanel.add(getProgressPanel(getLH(300, 1), getLH(300, 2), getLH(70, 1), getLH(70, 2)));

    JButton backButton=new JButton("OK, take me back to the game");
    backButton.setFont(new Font("Aharoni", Font.BOLD, getLH(14, 2)));
    backButton.setBounds(getLH(75, 1), getLH(355, 2), getLH(250, 1), getLH(25, 2));
    backButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            progressFrame.setVisible(false);
        }
    });
    progressPanel.add(backButton);

    progressFrame.add(progressPanel);
    //progressFrame.setResizable(false);
    progressFrame.setBounds(getLH(400, 1), getLH(150, 2), getLH(410, 1), getLH(430, 2));
    progressFrame.setVisible(true);
    progressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }
  public void pauseGame(){
    panel.setEnabled(!gamePaused);
    for(JButton b: charButton){
        b.setEnabled(!gamePaused);
    }
    if(gameMode==1){
        freeModeTimer.cancel();
    }
  }
  
  //this method checks if there is a bound.txt file which contains the tiles bound and deletes it if found then recreate a new bound file.
  //this is useful if this game is being played on different laptops with different screen sizes
  //the bounds for each tile on different screens varies
  public void saveTilesBound(){
      File boundsFile = new File(".//res//bounds.txt");
      if(boundsFile.exists()){
          boundsFile.delete();
      }
      try{
          boundsFile.createNewFile();
          FileWriter writer = new FileWriter(boundsFile);
          for(int i = 0; i < NUMBER_OF_TILES-1; i++){
              //saving the x, y, width and height of each bounds
              writer.write(String.format("%d %d %d %d %d\n", charButton[i].getBounds().x, charButton[i]
                      .getBounds().y, charButton[i].getBounds().width, charButton[i].getBounds().height, i));
          }
          writer.close();
      }catch(Exception e){
          e.printStackTrace();
      }
  }
  public JPanel getProgressPanel(int X, int Y, int x, int y){
        FlowLayout layout = new FlowLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        
        JPanel mainProgressPanel = new JPanel(layout);
        mainProgressPanel.setBounds(50, 50, X, Y);
        mainProgressPanel.setBackground(Color.LIGHT_GRAY);

        JButton[] progButtons=new JButton[charButton.length];
        for(int i=0; i<charButton.length; i++){
            progButtons[i]=new JButton(tileTexts[getAsArranged()[i]]);
            if(getAsArranged()[i] == i){
                progButtons[i].setBackground(Color.GREEN);
            }
            else{
                progButtons[i].setBackground(Color.RED);
            }
            int size = X == 300 ? 14 : 10;
            progButtons[i].setFont(new Font("Aharoni", Font.BOLD, getLH(size, 2)));
            progButtons[i].setPreferredSize(new Dimension(x, y));
            progButtons[i].setVisible(true);
            
            //saving the variable i in another variable to allow its usage in inner class i.e effectively final local variable
            int index = i;
            
            //adding this listener to allow the user to click and see the status of each tile
            progButtons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    if(progButtons[index].getBackground().equals(Color.GREEN)){
                        JOptionPane.showMessageDialog(null, "Tile "+progButtons[index].getText()+" is correctly placed", "Correct", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Tile "+progButtons[index].getText()+" is wrongly placed", "Wrong", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            });
            mainProgressPanel.add(progButtons[i]);
        }
        return mainProgressPanel;
  }
  
  public int[] getAsArranged(){
    int asArranged[] = new int[charButton.length];
    for(int i = 0; i < charButton.length; i++){  
        for(int j = 0; j < charButton.length; j++){
            if(bounds[i].equals(charButton[j].getBounds())){
                asArranged[i] = getNormalTilePosition(charButton[j].getText());
            }
        }
    }
    return asArranged;
  }
    public boolean isSolvable(){
        int inversion=0;
        int asArranged[] = getAsArranged();
        for(int i=0; i<charButton.length; i++){
            for(int j=i; j<charButton.length; j++){
                if(asArranged[i] > asArranged[j]){
                    inversion++;
                }
            }
        }
        //System.out.println("Total is "+inversion);
        return inversion%2==0;
    }
    
  
    JRadioButton rad[];
    int selectedCharType;
    
    public void showCharTypeDialog(){
        JFrame charTypeFrame=new JFrame("Select Preferred Character");
        JPanel charTypePanel=new JPanel();
        charTypePanel.setSize(getLH(200, 1), getLH(200, 2));
        charTypePanel.setLayout(null);
        charTypePanel.setBackground(Color.WHITE);

        JLabel topLabel=new JLabel("Select a character type");
        //topLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
        charTypePanel.add(topLabel);

        rad = new JRadioButton[3];
        rad[0]=new JRadioButton("Alphabet");
        rad[1]=new JRadioButton("Numbers");
        rad[2]=new JRadioButton("Custom");

        for(int i=0; i<rad.length; i++){
            rad[i].setBounds(getLH(10, 1), getLH(30+20*i, 2), rad[1].getPreferredSize().width, rad[1].getPreferredSize().height);
            rad[i].setEnabled(true);
            rad[i].setSelected(false);
            charTypePanel.add(rad[i]);
        }
        rad[0].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    for(int i=0; i<rad.length; i++){
                        if(i!=0)
                            rad[i].setSelected(false);
                    }
                }
                });
        rad[1].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    for(int i=0; i<rad.length; i++){
                        if(i!=1)
                            rad[i].setSelected(false);
                    }
                }
                });
        rad[2].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    for(int i=0; i<rad.length; i++){
                        if(i!=2)
                            rad[i].setSelected(false);
                    }
                }
                });


        JButton oKButton=new JButton("OK");
        oKButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
        oKButton.setBounds(getLH(130, 1), getLH(130, 2), getLH(60, 1), getLH(25, 2));
        oKButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                for(int i = 0; i < rad.length; i++){
                    if(rad[i].isSelected()){
                        selectedCharType = i;
                        //do something...
                        // if it is alphabet
                        if(selectedCharType == 0){
                            displayChar[0] = 'A';
                            int convert = (int) (displayChar[0]);
                            for(int k = 0; k < displayChar.length; k++){
                            displayChar[k] = (char) (convert+k);
                            //System.out.println(displayChar[i]);
                            }
                            for(int j=0; j<charButton.length; j++){
                            charButton[j].setText(String.format("%c", displayChar[j]));
                            tileTexts[j] = charButton[j].getText();;
                        }
                        }
                        //if it is numbers
                        else if(selectedCharType == 1){
                            for(int j = 0; j < charButton.length; j++){
                            charButton[j].setText(String.format("%d", j+1));
                            charButton[j].setFont(new Font("Monospaced", Font.BOLD, getLH(50, 2)));
                            tileTexts[j] = charButton[j].getText();
                        }  
                        }
                        else{
                            JFrame customCharTypeFrame = new JFrame("Select Preferred Character");
                            JPanel customCharTypePanel = new JPanel();
                            customCharTypePanel.setSize(getLH(550, 1), getLH(200, 2));
                            customCharTypePanel.setLayout(null);
                            customCharTypePanel.setBackground(Color.WHITE);

                            JTextField[] cusCharField = new JTextField[15];
                            for(int a = 0; a < cusCharField.length; a++){
                                cusCharField[a] = new JTextField();
                                cusCharField[a].setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
                                cusCharField[a].setBounds(getLH(10+35*a, 1), getLH(30, 2), getLH(30, 1), getLH(30, 2));
                                cusCharField[a].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                                customCharTypePanel.add(cusCharField[a]);
                            }
                            JLabel topLabel = new JLabel("Fill the boxes with your desired characters");
                            topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
                            customCharTypePanel.add(topLabel);

                            JButton cusOkButton = new JButton("OK");
                            cusOkButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
                            cusOkButton.setBounds(getLH(200, 1), getLH(110, 2), getLH(60, 1), getLH(25, 2));
                            cusOkButton.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent evt){
                                   charsPreffered = new String[cusCharField.length];
                                   for(int ii = 0; ii < charsPreffered.length; ii++){
                                       charsPreffered[ii] = cusCharField[ii].getText();
                                   }
                                   for(int ii = 0; ii < charsPreffered.length; ii++){
                                       if(charsPreffered[ii].equalsIgnoreCase("")||charsPreffered[ii].equalsIgnoreCase(" ")||charsPreffered[ii].equalsIgnoreCase("  ")){
                                           JOptionPane.showMessageDialog(null, "Empty String is not allowed as in box "+(ii+1)+" \nKindly input a character or two", "Error!", JOptionPane.ERROR_MESSAGE);
                                           break;
                                       }
                                       else if(hasAMatchElement(charsPreffered, ii)){
                                           JOptionPane.showMessageDialog(null, "You cannot repeat same character or string twice\n Kindly input a distinct character in each box\nYou inputed more than one "+charsPreffered[ii], "Error!", JOptionPane.ERROR_MESSAGE);
                                           break;
                                       }
                                       else{
                                           for(int jj = 0; jj < charsPreffered.length; jj++){
                                               charButton[jj].setText(charsPreffered[jj]);
                                               charButton[jj].setFont(new Font("Times New Roman", Font.BOLD, getLH(30, 2)));
                                               tileTexts[jj] = charButton[jj].getText();
                                           }
                                           customCharTypeFrame.dispose();
                                       }
                                   }
                            }
                            });
                            customCharTypePanel.add(cusOkButton);

                            JButton cusCancelButton=new JButton("Cancel");
                            cusCancelButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
                            cusCancelButton.setBounds(getLH(300, 1), getLH(110, 2), getLH(100, 1), getLH(25, 2));
                            cusCancelButton.addActionListener(new ActionListener(){
                            public void actionPerformed(ActionEvent evt){
                                   customCharTypeFrame.dispose();
                            }
                            });
                            customCharTypePanel.add(cusCancelButton);

                            customCharTypeFrame.add(customCharTypePanel);
                            customCharTypeFrame.setResizable(false);
                            customCharTypeFrame.setBounds(getLH(375, 1), getLH(200, 2), getLH(550, 1), getLH(200, 2));
                            customCharTypeFrame.setVisible(true);
                            customCharTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        }

                        charTypeFrame.setVisible(false);

                    }
                }
            }
        });
        charTypePanel.add(oKButton);

        JButton cancelButton=new JButton("Cancel");
        cancelButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
        cancelButton.setBounds(getLH(10, 1), getLH(130, 2), getLH(90, 1), getLH(25, 2));
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                charTypeFrame.setVisible(false);
            }
        });
        charTypePanel.add(cancelButton);

        charTypeFrame.add(charTypePanel);
        charTypeFrame.setLocationRelativeTo(null);
        charTypeFrame.setResizable(false);
        charTypeFrame.setBounds(getLH(500, 1), getLH(250, 2), getLH(200, 1), getLH(200, 2));
        charTypeFrame.setVisible(true);
        charTypeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public boolean hasAMatchElement(String[] arr, int element){
        boolean isContain=false;
        for(int i = 0; i < arr.length; i++){
            if(i == element){
            }
            else if(arr[i].equalsIgnoreCase(arr[element]))
                isContain= true;
        }
        return isContain;
    }
    
  public void move(int index){
    countClick++;
    quoteClicks.setText(countClick+" clicks");
    
    //check if the tile clicked is movable and then move it
    if(isMovable(index)){
        ShuffleSound.playSound("move");
        Rectangle r=charButton[index].getBounds();
        charButton[index].setBounds(freeButton.getBounds());
        freeButton.setBounds(r);
        panel.repaint();
    }
    infoPanel.repaint();

    //if the player is playing a free mode, this block shows the winning popup
    if(gameMode==1){
        if(isCompleted()){
            timeArea.setText("You won!");
            timeArea.repaint();
            
            Timer timerForWin = new Timer();
            TimerTask taskForWin = new TimerTask(){
                int timeToDisplayWinFrame2 = 0;     //this was named ...2 because we already used timeToDisplayWinFrame in method checkMoveStatus
                @Override
                public void run(){
                    if(timeToDisplayWinFrame2 == 0){
                        for(JButton b: charButton){
                            b.setBackground(Color.GREEN);
                            b.setEnabled(false);
                            freeModeTimer.cancel();
                        }
                        timeArea.setText("You won!");
                        timeArea.repaint();
                    }
                    //the next if blocks show a congratulatory texts on the time area before displaying the win pane
                    if(timeToDisplayWinFrame2 == 1){
                        timeArea.setText("Wow!");
                        timeArea.repaint();
                    }
                    if(timeToDisplayWinFrame2 == 2){
                        timeArea.setText("Welldone!");
                        timeArea.repaint();
                    }
                    if(timeToDisplayWinFrame2 == 3){
                        displayWinFrameFreeMode();
                    }
                    timeToDisplayWinFrame2++;
                }
            };
            timerForWin.schedule(taskForWin, 0, 1000);
            }
  }
    //this block checks if the game mode is challenge and then checks the move status if the game is completed
    //the checkMovesStatus method was used only for the challenge mode
    //this is because the challenge mode uses a static AdsmutShuffle object and trying to paint some components require calling the object
    //review the chcekMovesStatus method to understand this better
    else if(gameMode==2){
        checkMovesStatus(countClick, expectedClicks);
        
    }
  }
  public void displayWinFrameFreeMode(){
        ShuffleSound.playSound("win");
        timeArea.setVisible(false);
        setVisible(false);
        JFrame winFrame=new JFrame("You Won!");
        JPanel winPanel=new JPanel();
        winPanel.setLayout(null);
        winPanel.setSize(getLH(350, 1), getLH(410, 2));
        ImageIcon thumbUp=new ImageIcon(".//res//thumb up.png");
        JLabel thumbUpLabel=new JLabel(thumbUp);
        thumbUpLabel.setFont(new Font("Georgia", Font.BOLD, getLH(12, 2)));
        thumbUpLabel.setForeground(Color.black);
        thumbUpLabel.setBounds((winPanel.getWidth()-thumbUpLabel.getPreferredSize()
                .width)/2, getLH(3, 2), thumbUpLabel.getPreferredSize().width, thumbUpLabel.getPreferredSize().height);
        winPanel.add(thumbUpLabel);

        JLabel youWonLabel=new JLabel("YOU WON!");
        youWonLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(20, 2)));
        youWonLabel.setForeground(Color.black);
        youWonLabel.setBounds(getLH(130, 1), getLH(233, 2), youWonLabel.getPreferredSize().width, youWonLabel.getPreferredSize().height);
        winPanel.add(youWonLabel);

        JLabel timeElapsedLabel=new JLabel("Time elapsed: "+getTimeCount()+" seconds");
        timeElapsedLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        timeElapsedLabel.setForeground(Color.black);
        timeElapsedLabel.setBounds(getLH(110, 1), getLH(256, 2), timeElapsedLabel.getPreferredSize().width, timeElapsedLabel.getPreferredSize().height);
        winPanel.add(timeElapsedLabel);

        JLabel totalClicksLabel=new JLabel("Total clicks: "+countClick+"");
        totalClicksLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        totalClicksLabel.setForeground(Color.black);
        totalClicksLabel.setBounds(getLH(110, 1), getLH(272, 2), totalClicksLabel.getPreferredSize().width, totalClicksLabel.getPreferredSize().height);
        winPanel.add(totalClicksLabel);

        JButton playButton=new JButton("Play again");
        playButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
        playButton.setBounds(getLH(3, 1), getLH(330, 2), getLH(100, 1), getLH(30, 2));
        playButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            winFrame.dispose();
            AdsmutShuffle a=new AdsmutShuffle();
            a.showGamePane();
        }
        });
        winPanel.add(playButton);

        //challenge mode
        JButton cButton=new JButton("Enter challenge mode");
        cButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
        cButton.setBounds(getLH(3, 1), getLH(290, 2), getLH(327, 1), getLH(30, 2));
        cButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            showChallengePane();
            winFrame.dispose();
        }
        });
        winPanel.add(cButton);

        JButton exit=new JButton("Exit");
        exit.setFont(new Font("Times New Roman", Font.BOLD, getLH(14, 2)));
        exit.setBounds(getLH(230, 1), getLH(330, 2), getLH(100, 1), getLH(30, 2));
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                int choice=JOptionPane.showConfirmDialog(winFrame, "Exit", "Confirm to exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(choice==JOptionPane.OK_OPTION)
                    System.exit(1);
                }
            });

        winPanel.add(exit);


        winFrame.add(winPanel);
        winFrame.setSize(getLH(350, 1), getLH(410, 2));
        winFrame.setLocationRelativeTo(null);
        winFrame.setVisible(true);
        winFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //JOptionPane.showMessageDialog(null, "Welldone,the puzzle is completed after "+countClick+" clicks", "Wow!", JOptionPane.PLAIN_MESSAGE);
  }
  public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
  //the purpose of this method is to enable the software to work in the same manner on all PC (with different screen resolution)
    private int getLH(int value, int type){
        //this system has a size of 1280 by 800 pixel
        //type in the argument means either width or height
        int exactValue=0;
        switch(type){
            case 1: //width
                exactValue=(SCREEN_SIZE.width*value)/1280;
                break;
            case 2:
                exactValue=(SCREEN_SIZE.height*value)/800;
                break;
        }
        return exactValue;
    }
  public void displayWinFrame(){            
    ShuffleSound.playSound("win");
    setVisible(false);
    JFrame winFrame=new JFrame("You Won!");
    winFrame.setResizable(false);
    winFrame.setLocationRelativeTo(null);
    
    JPanel winPanel=new JPanel();
    winPanel.setLayout(null);
    winPanel.setSize(getLH(640, 1), getLH(400, 2));
    
    ImageIcon thumbUp=Tools.scaleImage(new File(".//res//thumb up.png"), getLH(300, 1), getLH(300, 2));
    JLabel thumbUpLabel=new JLabel(thumbUp);
    thumbUpLabel.setFont(new Font("Georgia", Font.BOLD, getLH(12, 2)));
    thumbUpLabel.setForeground(Color.black);
    thumbUpLabel.setBounds(getLH(1, 1), getLH(20, 2), thumbUpLabel.getPreferredSize().width, thumbUpLabel.getPreferredSize().height);
    winPanel.add(thumbUpLabel);

    if(challengeType==1){
        JPanel p=aChallenge.getProgressPanel(getLH(200, 1), getLH(150, 2), getLH(50, 1), getLH(37, 2));
        p.setBounds(getLH(355, 1), getLH(80, 2), getLH(200, 1), getLH(150, 2));
        winPanel.add(p);

        avatar.setBounds(getLH(420, 1), getLH(5, 2), avatar.getPreferredSize().width, avatar.getPreferredSize().height);
        winPanel.add(avatar);
    }
    else{
        JPanel p=aGChallenge[currentPlayer].getProgressPanel(getLH(200, 1), getLH(150, 2), getLH(50, 1), getLH(37, 2));
        p.setBounds(getLH(355, 1), getLH(80, 2), getLH(200, 1), getLH(150, 2));
        winPanel.add(p);

        cAvatar[currentPlayer-1].setBounds(getLH(420, 1), getLH(5, 2), cAvatar[currentPlayer-1].getPreferredSize().width, cAvatar[currentPlayer-1].getPreferredSize().height);
        winPanel.add(cAvatar[currentPlayer-1]);
    }
    JLabel percentageCompleted=new JLabel("Percentage completed: 100%");
    percentageCompleted.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    percentageCompleted.setForeground(Color.black);
    percentageCompleted.setBounds(getLH(360, 1), getLH(235, 2), percentageCompleted.getPreferredSize().width, percentageCompleted.getPreferredSize().height);
    winPanel.add(percentageCompleted);

    JLabel youLoseLabel=new JLabel("YOU WON THE CHALLENGE!");
    youLoseLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(20, 2)));
    youLoseLabel.setForeground(Color.black);
    youLoseLabel.setBounds(getLH(310, 1), getLH(250, 2), youLoseLabel.getPreferredSize().width, youLoseLabel.getPreferredSize().height);
    winPanel.add(youLoseLabel);

    String timeRemain = "Remains: "+cTimeCounter;
    JLabel timeElapsedLabel=new JLabel("Expected completion time: "+expectedTime+" seconds   "+timeRemain);
    timeElapsedLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
    timeElapsedLabel.setForeground(Color.black);
    timeElapsedLabel.setBounds(getLH(310, 1), getLH(280, 2), timeElapsedLabel.getPreferredSize().width, timeElapsedLabel.getPreferredSize().height);
    winPanel.add(timeElapsedLabel);


    String movesRemain=challengeType==2?"Remains: "+(expectedClicks-aGChallenge[currentPlayer].countClick):"Remains: "+(expectedClicks-countClick);
    JLabel totalClicksLabel=new JLabel("Maximum clicks: "+expectedClicks+"   "+movesRemain);
    totalClicksLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
    totalClicksLabel.setForeground(Color.black);
    totalClicksLabel.setBounds(getLH(310, 1), getLH(295, 2), totalClicksLabel.getPreferredSize().width, totalClicksLabel.getPreferredSize().height);
    winPanel.add(totalClicksLabel);

    if(challengeType==1){
        JButton playButton=new JButton("<< Back to game menu");
        playButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        playButton.setBounds(getLH(10, 1), getLH(330, 2), getLH(170, 1), getLH(25, 2));
        playButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            winFrame.setVisible(false);
            dispose();
            adsShuf=new AdsmutShuffle();
            adsShuf.showGamePane();
            adsShuf.repaint();
        }
        });
        winPanel.add(playButton);

        JButton highScoreButton=new JButton("**View high scores**");
        highScoreButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        highScoreButton.setBounds(getLH(260, 1), getLH(330, 2), getLH(170, 1), getLH(25, 2));
        highScoreButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){

        }
        });
        winPanel.add(highScoreButton);

        JButton exit=new JButton("Exit >>");
        exit.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        exit.setBounds(getLH(515, 1), getLH(330, 2), getLH(80, 1), getLH(25, 2));
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                int choice=JOptionPane.showConfirmDialog(winFrame, "Exit", "Confirm to exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(choice==JOptionPane.OK_OPTION)
                    System.exit(0);
                }
            });

        winPanel.add(exit);
        aChallenge.dispose();
    }

    else{
        playerRankDetails[currentPlayer]=String.format("Completed tiles: %.0f", (double) ((double) (100*nTilesCorrectlyPlaced)/NUMBER_OF_TILES))+"% Clicks remaining: "+(expectedClicks-aGChallenge[currentPlayer].countClick)+" Time remaining: "+cTimeCounter+" seconds";
        playerScore[currentPlayer] = (double) ((double) (100*nTilesCorrectlyPlaced)/NUMBER_OF_TILES) + ((double) (expectedClicks-aGChallenge[currentPlayer].countClick)*100/expectedClicks) + ((double) cTimeCounter*100/expectedTime);
        
        System.out.println(playerRankDetails[currentPlayer]);
        System.out.println(" current player="+currentPlayer+" n player ="+numberOfPlayers);
        String text=currentPlayer==numberOfPlayers?"Check result":"Go to "+challengePlayersName[currentPlayer]+" turn";
        JButton nextPButton=new JButton(text);
        nextPButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        nextPButton.setBounds(getLH(10, 1), getLH(330, 2), getLH(170, 1), getLH(25, 2));
        nextPButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                winFrame.dispose();
                playGroupChallenge();
            }
        });
        winPanel.add(nextPButton);
    }
    
    winFrame.setLocationRelativeTo(null);
    winFrame.add(winPanel);
    winFrame.setSize(getLH(640, 1), getLH(400, 2));
    winFrame.setVisible(true);
    winFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
}
  
  public void displayLoseFrame(){            
    ShuffleSound.playSound("lose");
    setVisible(false);
    JFrame loseFrame=new JFrame("You Lose!");
    loseFrame.setResizable(false);
    
    JPanel losePanel=new JPanel();
    losePanel.setLayout(null);
    losePanel.setSize(getLH(640, 1), getLH(400, 2));
    ImageIcon thumbDown=new ImageIcon(".//res//thumb down.jpeg");
    JLabel thumbDownLabel=new JLabel(thumbDown);
    thumbDownLabel.setFont(new Font("Georgia", Font.BOLD, getLH(12, 2)));
    thumbDownLabel.setForeground(Color.black);
    thumbDownLabel.setBounds(getLH(1, 1), getLH(20, 2), thumbDownLabel.getPreferredSize().width, thumbDownLabel.getPreferredSize().height);
    losePanel.add(thumbDownLabel);

    if(challengeType==1){
    JPanel p=aChallenge.getProgressPanel(getLH(200, 1), getLH(150, 2), getLH(50, 1), getLH(37, 2));
    p.setBounds(getLH(355, 1), getLH(80, 2), getLH(200, 1), getLH(150, 2));
    losePanel.add(p);

    avatar.setBounds(getLH(420, 1), getLH(5, 2), avatar.getPreferredSize().width, avatar.getPreferredSize().height);
    losePanel.add(avatar);
    }
    else{
        JPanel p=aGChallenge[currentPlayer].getProgressPanel(getLH(200, 1), getLH(150, 2), getLH(50, 1), getLH(37, 2));
        p.setBounds(getLH(385, 1), getLH(80, 2), getLH(200, 1), getLH(150, 2));
        losePanel.add(p);

        losePanel.add(cAvatar[currentPlayer-1]);
    }
    JLabel percentageCompleted=new JLabel("Percentage completed: "+String.format("%.0f", (double) ((double) (100*nTilesCorrectlyPlaced)/NUMBER_OF_TILES))+"%");
    percentageCompleted.setFont(new Font("Times New Roman", Font.BOLD, getLH(16, 2)));
    percentageCompleted.setForeground(Color.black);
    percentageCompleted.setBounds(getLH(360, 1), getLH(235, 2), percentageCompleted.getPreferredSize().width, percentageCompleted.getPreferredSize().height);
    losePanel.add(percentageCompleted);

    JLabel youLoseLabel=new JLabel("YOU LOST THE CHALLENGE!");
    youLoseLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(20, 2)));
    youLoseLabel.setForeground(Color.black);
    youLoseLabel.setBounds(getLH(310, 1), getLH(250, 2), youLoseLabel.getPreferredSize().width, youLoseLabel.getPreferredSize().height);
    losePanel.add(youLoseLabel);

    String timeRemain=challengeType==2?"Remains: "+cTimeCounter:"";
    JLabel timeElapsedLabel=new JLabel("Expected completion time: "+expectedTime+" seconds   "+timeRemain);
    timeElapsedLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
    timeElapsedLabel.setForeground(Color.black);
    timeElapsedLabel.setBounds(getLH(310, 1), getLH(280, 2), timeElapsedLabel.getPreferredSize().width, timeElapsedLabel.getPreferredSize().height);
    losePanel.add(timeElapsedLabel);


    String movesRemain=challengeType==2?"Remains: "+(expectedClicks-aGChallenge[currentPlayer].countClick):"";
    JLabel totalClicksLabel=new JLabel("Maximum clicks: "+expectedClicks+"   "+movesRemain);
    totalClicksLabel.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
    totalClicksLabel.setForeground(Color.black);
    totalClicksLabel.setBounds(getLH(310, 1), getLH(295, 2), totalClicksLabel.getPreferredSize().width, totalClicksLabel.getPreferredSize().height);
    losePanel.add(totalClicksLabel);

    if(challengeType==1){
        JButton playButton=new JButton("<< Back to game menu");
        playButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
        playButton.setBounds(getLH(10, 1), getLH(330, 2), getLH(170, 1), getLH(25, 2));
        playButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            loseFrame.setVisible(false);
            dispose();
            adsShuf=new AdsmutShuffle();
            adsShuf.showGamePane();
            adsShuf.repaint();
        }
        });
        losePanel.add(playButton);

        JButton highScoreButton=new JButton("**View high scores**");
        highScoreButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        highScoreButton.setBounds(getLH(260, 1), getLH(330, 2), getLH(170, 1), getLH(25, 2));
        highScoreButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            
        }
        });
        losePanel.add(highScoreButton);

        JButton exit=new JButton("Exit >>");
        exit.setFont(new Font("Times New Roman", Font.BOLD, 12));
        exit.setBounds(getLH(515, 1), getLH(330, 2), getLH(80, 1), getLH(25, 2));
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                int choice=JOptionPane.showConfirmDialog(loseFrame, "Exit", "Confirm to exit", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(choice==JOptionPane.OK_OPTION)
                    System.exit(0);
                }
            });

        losePanel.add(exit);
        aChallenge.dispose();
    }

    else{
        playerRankDetails[currentPlayer]=String.format("%.0f", (double) ((double) (100*nTilesCorrectlyPlaced)/NUMBER_OF_TILES))+"% "+(expectedClicks-aGChallenge[currentPlayer].countClick)+" "+cTimeCounter;
        playerScore[currentPlayer] = (double) ((double) (100*nTilesCorrectlyPlaced)/NUMBER_OF_TILES) + ((double) (expectedClicks-aGChallenge[currentPlayer].countClick)*100/expectedClicks) + ((double) cTimeCounter*100/expectedTime);

        System.out.println(playerRankDetails[currentPlayer]);
        System.out.println(" current player="+currentPlayer+" n player ="+numberOfPlayers);
        String text=currentPlayer==numberOfPlayers?"Check result":"Go to "+challengePlayersName[currentPlayer]+" turn";
        JButton nextPButton=new JButton(text);
        nextPButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        nextPButton.setBounds(getLH(10, 1), getLH(330, 2), getLH(170, 1), getLH(35, 2));
        nextPButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            loseFrame.dispose();
            playGroupChallenge();
        }
        });
        losePanel.add(nextPButton);
    }
    loseFrame.add(losePanel);
    loseFrame.setSize(getLH(620, 1), getLH(400, 2));
    loseFrame.setLocationRelativeTo(null);
    loseFrame.setVisible(true);
    loseFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
}
  
  
  public boolean isCompleted(){
      boolean status=false;
      System.out.println("\n\n");
      nTilesCorrectlyPlaced=0;
      
      for(int i=0; i<charButton.length; i++){
          if(charButton[i].getX()==bounds[i].getX()&&charButton[i].getY()==bounds[i].getY()){
              System.out.println("Button "+i+" is correctly placed");
              nTilesCorrectlyPlaced++;
          }
          //System.out.println("Bound "+i+" "+bounds[i]+" is now "+charButton[i].getBounds());
      }
      System.out.println(nTilesCorrectlyPlaced);
      if(nTilesCorrectlyPlaced==15){
          status=true;
      }
    if(countClick%2==0)
        infoPanel.setSize(getLH(220, 1)+1, getLH(570, 2)+1);
    else
        infoPanel.setSize(getLH(220, 1), getLH(570, 2));
    quote.setText(nTilesCorrectlyPlaced+" DONE");
    return status;
  }
  String statusMessage;
  public boolean isMovable (int index){
      boolean status=false;
      if((charButton[index].getX()+charButton[index].getWidth()==freeButton.getX())&&(charButton[index].getY()==freeButton.getY())){
          status=true;
      }
      else if((charButton[index].getX()==freeButton.getWidth()+freeButton.getX())&&(charButton[index].getY()==freeButton.getY())){
          status=true;
      }
      else if((charButton[index].getY()+charButton[index].getHeight()==freeButton.getY())&&(charButton[index].getX()==freeButton.getX())){
          status=true;
      }
      else if((charButton[index].getY()==freeButton.getHeight()+freeButton.getY())&&(charButton[index].getX()==freeButton.getX())){
          status=true;
      }
      else{
          /*try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(getClass().getResource("error.wav").getPath()));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
          }
          catch(IOException e){
              e.printStackTrace();
          }
          catch(UnsupportedAudioFileException e){
              e.printStackTrace();
          }
          catch(LineUnavailableException e){
              e.printStackTrace();
          }*/
          Toolkit.getDefaultToolkit().beep();
      }
      
      return status;
  }
  public void shuffleGame(){
      SecureRandom r=new SecureRandom();
      if(freeButton.getX()!=freeBB.getX()&&freeButton.getY()!=freeBB.getY()){
          Rectangle temp=freeButton.getBounds();
          freeButton.setBounds(freeBB);
          for(int i=0; i<charButton.length; i++){
              if(freeBB.getX()==charButton[i].getX()&&freeBB.getY()==charButton[i].getY()){
                  charButton[i].setBounds(temp);
              }
          }
      }
      nTilesCorrectlyPlaced=0;
      countClick=0;
      quote.setText(nTilesCorrectlyPlaced+" DONE");
      quote.setEnabled(false);
      quoteClicks.setText(countClick+" clicks");
      quoteClicks.setEnabled(false);
      
      Rectangle tempBound;
      normalTilePosition=new int[charButton.length];
      bounds=new Rectangle[charButton.length];
      //saving the initial bounds of each characters
      try{
          Scanner scan=new Scanner(Paths.get(".//res//bounds.txt"));
          int index=0;
          while(scan.hasNext()){
              bounds[index]=new Rectangle(scan.nextInt(), scan.nextInt(), scan.nextInt(), scan.nextInt());
              normalTilePosition[index]=scan.nextInt();
              index++;
          }
      }catch (IOException ioException){
          ioException.printStackTrace();
    }
      for(int i=0; i<charButton.length; i++){
          charButton[i].setEnabled(true);
          //System.out.println("Bound "+i+" "+bounds[i]);
      }
      //now shuffling
      for(int i=0; i<charButton.length; i++){
          tempBound=charButton[i].getBounds();
          int j=r.nextInt(charButton.length);
          charButton[i].setBounds(charButton[j].getBounds());
          charButton[j].setBounds(tempBound);
      }
      panel.repaint();
  }
  
  public int getNormalTilePosition(String tileText){
      int pos=0;
      for(int i=0; i<normalTilePosition.length; i++){
      if(tileText.equalsIgnoreCase(tileTexts[i])){
          pos=normalTilePosition[i];
        }
      }
      return pos;
  }
  
  public int getPresentTilePosition(Rectangle bound){
      int pos=0;
      for(int i=0; i<charButton.length; i++){
          if(bounds[i].equals(bound)){
          pos=normalTilePosition[i];
      }
      }
      return pos;
  }
  
  JButton easyDifficultyButton;
    JButton mediumDifficultyButton;
    JButton hardDifficultyButton;
    JButton customDifficultyButton;
  public void showChallengePane(){
        gameMode=2;
        challengeFrame=new JFrame("Challenge Mode");
        challengePanel=new JPanel();
        challengePanel.setSize(getLH(500, 1), getLH(500, 2));
        challengePanel.setLayout(null);
        challengePanel.setBackground(Color.WHITE);

        JLabel topLabel=new JLabel("Do not be scared to face a new challenge!");
        topLabel.setFont(new Font("Aharoni", Font.BOLD, 23));
        topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
        challengePanel.add(topLabel);

        JLabel topLabel2=new JLabel("..SET UP A CHALLENGE..");
        topLabel2.setFont(new Font("Aharoni", Font.ITALIC, 14));
        topLabel2.setBounds(getLH(150, 1), getLH(35, 2), topLabel2.getPreferredSize().width, topLabel2.getPreferredSize().height);
        challengePanel.add(topLabel2);

        startButton=new JButton("Start Challenge");
        startButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        startButton.setBounds(getLH(150, 1), getLH(430, 2), getLH(200, 1), getLH(25, 2));
        startButton.setEnabled(false);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                switch(challengeType){
                    case 1:
                        //for self challenge
                        break;

                    case 2:
                        //for group challenge
                        if(randomPlayersArrangementRad.isSelected()){
                            SecureRandom r=new SecureRandom();
                            int arr[]=new int[numberOfPlayers];
                            Arrays.fill(arr, -1);
                            for(int i=0; i<numberOfPlayers; i++){
                                int j=r.nextInt(numberOfPlayers);
                                while(hasAMatchElement(arr, j)){
                                    j=r.nextInt(numberOfPlayers);
                                }
                                arr[i]=j;
                            }
                            for(int i=0; i<numberOfPlayers; i++){
                                challengePlayersName[i]=challengePlayersField[arr[i]].getText();
                            }

                        }
                        else{
                            for(int i=0; i<numberOfPlayers; i++){
                                challengePlayersName[i]=challengePlayersField[i].getText();
                                System.out.println(challengePlayersName[i]);

                            }
                        }
                        for(int i=0; i<numberOfPlayers; i++){
                            if(challengePlayersName[i].equalsIgnoreCase("")||challengePlayersName[i].equalsIgnoreCase(" ")||challengePlayersName[i].equalsIgnoreCase("  ")){
                               try{
                                   throw new Exception();
                               }catch(Exception exp){
                                    JOptionPane.showMessageDialog(null, "Empty String is not allowed as in box "+(i+1)+" \nKindly input a character or two", "Error! Invalid Player Name", JOptionPane.ERROR_MESSAGE);
                                    break;
                               }
                            }

                            System.out.println(challengePlayersName[i]);

                            }
                }
                try{
                        if(rad2[0].isSelected()){ //time challenge
                            expectedTime=Integer.parseInt(timeFieldC.getText());
                        }
                        else if(rad2[1].isSelected()){ //clicks challenge
                            expectedClicks=Integer.parseInt(clicksField.getText());
                        }
                        else{ //time and clicks
                            expectedTime=Integer.parseInt(timeFieldC.getText());
                            expectedClicks=Integer.parseInt(clicksField.getText());
                        }
                        
                        
                        challengeFrame.setVisible(false);
                        }catch(Exception exception){
                            JOptionPane.showMessageDialog(challengePanel, "Your input is not valid! \nCheck the fileds again and input only integer values for time and/or clicks", "Invalid input", JOptionPane.ERROR_MESSAGE);
                        }
                showChallengeProfile();
                
            }
        });
        challengePanel.add(startButton);

        cYSButton=new JButton("Self Challenge");
        cYSButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        cYSButton.setBounds(getLH(10, 1), getLH(55, 2), getLH(200, 1), getLH(25, 2));
        cYSButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                challengeType=1;
                cYSButton.setEnabled(false);
                showChallengeSettings();
                challengePanel.repaint();
            }
        });
        challengePanel.add(cYSButton);

        cFriendsButton=new JButton("Group Challenge");
        cFriendsButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        cFriendsButton.setBounds(getLH(260, 1), getLH(55, 2), getLH(200, 1), getLH(25, 2));
        cFriendsButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                challengeType=2;
                try{
                numberOfPlayers=Integer.parseInt(JOptionPane.showInputDialog(null, "How many players?", "Number of players", JOptionPane.QUESTION_MESSAGE));
                playerScore = new double[numberOfPlayers+1];
                playerRankDetails = new String[numberOfPlayers+1];
                JLabel buttomLabel = new JLabel("You can edit player names");
                buttomLabel.setFont(new Font("Aharoni", Font.PLAIN, 10));
                buttomLabel.setBounds(getLH(260, 1), getLH(90+28*numberOfPlayers, 2), buttomLabel.getPreferredSize().width, buttomLabel.getPreferredSize().height);
                challengePanel.add(buttomLabel);

                challengePlayersField = new JTextField[numberOfPlayers];
                challengePlayersName = new String[numberOfPlayers];
                for(int i = 0; i < numberOfPlayers; i++){
                    challengePlayersField[i] = new JTextField("Player "+(i+1));
                    challengePlayersField[i].setBackground(cFriendsButton.getBackground());
                    challengePlayersField[i].setBounds(getLH(260, 1), getLH(90+28*i, 2), getLH(200, 1), getLH(25, 2));
                    challengePlayersField[i].setFont(new Font("Aharoni", Font.PLAIN, 12));
                    challengePanel.add(challengePlayersField[i]);
                }
                randomPlayersArrangementRad=new JRadioButton("Allow random players arrangement");
                randomPlayersArrangementRad.setSelected(false);
                randomPlayersArrangementRad.setBounds(getLH(10, 1), getLH(402, 2), randomPlayersArrangementRad.getPreferredSize().width, randomPlayersArrangementRad.getPreferredSize().height);
                challengePanel.add(randomPlayersArrangementRad);
                challengePanel.repaint();
                showChallengeSettings();
                challengePanel.repaint();
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(challengePanel, "Invalid input!\nEnter integer value");
                }

            }
        });
        challengePanel.add(cFriendsButton);



        challengeFrame.add(challengePanel);
        //progressFrame.setResizable(false);
        challengeFrame.setBounds(getLH(400, 1), getLH(130, 2), getLH(500, 1), getLH(500, 2));
        challengeFrame.setVisible(true);
        challengeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
}
    int clicked=0;
    public void showChallengeSettings(){
        cYSButton.setEnabled(false);
        
        //if it is a single player challenge, we have to save the player name at once
        //this line uses JOptionPane to trigger the player to input their name
        if(challengeType==1){
            singlePlayerName = JOptionPane.showInputDialog(null, "Player name", "Enter player name", JOptionPane.QUESTION_MESSAGE);
            if(singlePlayerName.trim().length() == 0||singlePlayerName.matches("^[^a-zA-Z]+$"))
                singlePlayerName="Player";
        }
        //ading fields for expected copletion time and maximum alowable clicks
        //since we have to set these for both single player and multiplayer challenge
        timeLabel=new JLabel("Expected completion time in seconds:");
        timeLabel.setFont(new Font("Aharoni", Font.ITALIC, 16));
        timeLabel.setBounds(getLH(10, 1), getLH(345, 2), timeLabel.getPreferredSize().width, timeLabel.getPreferredSize().height);

        timeFieldC=new JTextField();
        timeFieldC.setFont(new Font("Monospaced", Font.BOLD, 14));
        timeFieldC.setEnabled(false);
        timeFieldC.setBounds(getLH(250, 1), getLH(340, 2), getLH(70, 1), getLH(25, 2));

        clicksLabel=new JLabel("Maximum number of clicks:");
        clicksLabel.setFont(new Font("Aharoni", Font.ITALIC, 16));
        clicksLabel.setBounds(getLH(10, 1), getLH(375, 2), clicksLabel.getPreferredSize().width, clicksLabel.getPreferredSize().height);

        clicksField=new JTextField();
        clicksField.setFont(new Font("Monospaced", Font.BOLD, 14));
        clicksField.setEnabled(false);
        clicksField.setBounds(getLH(250, 1), getLH(370, 2), getLH(70, 1), getLH(25, 2));

        rad2 = new JRadioButton[3];
        rad2[0]=new JRadioButton("Time challenge");
        rad2[1]=new JRadioButton("Clicks challenge");
        rad2[2]=new JRadioButton("Time and clicks");

        
        for(int i=0; i<rad2.length; i++){
            rad2[i].setFont(new Font("Aharoni", Font.PLAIN, getLH(12, 2)));
            rad2[i].setBounds(getLH(10, 1), getLH(100+30*i, 2), getLH(200, 1), getLH(30, 2));
            rad2[i].setEnabled(true);
            rad2[i].setSelected(false);
            rad2[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    
                    JLabel selectDifficultyLabel=new JLabel("Select difficulty");
                    selectDifficultyLabel.setFont(new Font("Aharoni", Font.ITALIC, getLH(16, 2)));
                    selectDifficultyLabel.setBounds(getLH(10, 1), getLH(230, 2), selectDifficultyLabel.getPreferredSize().width, selectDifficultyLabel.getPreferredSize().height);
                    challengePanel.add(selectDifficultyLabel);
                    
                    if(clicked>0){
                        easyDifficultyButton.setVisible(false);
                        mediumDifficultyButton.setVisible(false);
                        hardDifficultyButton.setVisible(false);
                        customDifficultyButton.setVisible(false);
                    }
            easyDifficultyButton=new JButton("Easy");
            easyDifficultyButton.setBackground(Color.GREEN);
            easyDifficultyButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
            easyDifficultyButton.setBounds(getLH(10, 1), getLH(250, 2), getLH(100, 1), getLH(25, 2));
            easyDifficultyButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    if(rad2[0].isSelected()){
                        timeFieldC.setText("120");
                    }
                    else if(rad2[1].isSelected()){
                        clicksField.setText("200");
                    }
                    else{
                        timeFieldC.setText("120");
                        clicksField.setText("200");
                    }
                    }
                });

            challengePanel.add(easyDifficultyButton);
            
            mediumDifficultyButton=new JButton("Medium");
            mediumDifficultyButton.setBackground(Color.BLUE);
            mediumDifficultyButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
            mediumDifficultyButton.setBounds(getLH(130, 1), getLH(250, 2), getLH(100, 1), getLH(25, 2));
            mediumDifficultyButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    if(rad2[0].isSelected()){
                        timeFieldC.setText("90");
                    }
                    else if(rad2[1].isSelected()){
                        clicksField.setText("150");
                    }
                    else{
                        timeFieldC.setText("90");
                        clicksField.setText("150");
                    }
                    }
                });

            challengePanel.add(mediumDifficultyButton);
            
            hardDifficultyButton=new JButton("Hard");
            hardDifficultyButton.setBackground(Color.RED);
            hardDifficultyButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
            hardDifficultyButton.setBounds(getLH(250, 1), getLH(250, 2), getLH(100, 1), getLH(25, 2));
            hardDifficultyButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    if(rad2[0].isSelected()){
                        timeFieldC.setText("60");
                    }
                    else if(rad2[1].isSelected()){
                        clicksField.setText("100");
                    }
                    else{
                        timeFieldC.setText("60");
                        clicksField.setText("100");
                    }
                    }
                });

            challengePanel.add(hardDifficultyButton);
            
            customDifficultyButton=new JButton("Custom");
            customDifficultyButton.setFont(new Font("Times New Roman", Font.BOLD, getLH(12, 2)));
            customDifficultyButton.setBounds(getLH(370, 1), getLH(250, 2), getLH(100, 1), getLH(25, 2));
            customDifficultyButton.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    timeFieldC.setEnabled(true);
                    clicksField.setEnabled(true);
                    }
                });

            challengePanel.add(customDifficultyButton);
            clicked++;
        }
            });
            challengePanel.add(rad2[i]);
            
        }
        rad2[0].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    clicksLabel.setVisible(false);
                    clicksField.setVisible(false);
                    for(int i=0; i<rad2.length; i++){
                        if(rad2[i].isSelected())
                            count2=1;
                        if(i!=0)
                            rad2[i].setSelected(false);
                    }
                    startButton.setEnabled(count2==1);
                    count2=0;

                    timeFieldC.setVisible(true);
                    timeLabel.setVisible(true);

                    challengePanel.add(timeLabel);


                    challengePanel.add(timeFieldC);
                    challengePanel.repaint();
                }
                });
        rad2[1].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    for(int i=0; i<rad2.length; i++){
                        if(rad2[i].isSelected())
                            count2=1;
                        if(i!=1)
                            rad2[i].setSelected(false);
                    }
                    startButton.setEnabled(count2==1);

                    timeFieldC.setVisible(false);
                    timeLabel.setVisible(false);
                    clicksLabel.setVisible(true);
                    clicksField.setVisible(true);

                    challengePanel.add(clicksLabel);


                    challengePanel.add(clicksField);
                    count2=0;
                    challengePanel.repaint();
                }
                });
        rad2[2].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent evt){
                    for(int i=0; i<rad2.length; i++){
                        if(rad2[i].isSelected())
                            count2=1;
                        if(i!=2)
                            rad2[i].setSelected(false);
                    }
                    startButton.setEnabled(count2==1);

                    timeFieldC.setVisible(false);
                    timeLabel.setVisible(false);
                    clicksLabel.setVisible(false);
                    clicksField.setVisible(false);

                    timeFieldC.setVisible(true);
                    timeLabel.setVisible(true);
                    challengePanel.add(timeLabel);
                    challengePanel.add(timeFieldC);
                    clicksLabel.setVisible(true);
                    clicksField.setVisible(true);
                    challengePanel.add(clicksLabel);
                    challengePanel.add(clicksField);
                    challengePanel.repaint();
                    count2=0;
                }
                });
                challengePanel.repaint();
        
    }
    public JFrame challengeProfileFrame;
    public JPanel challengeProfilePanel;
    public JButton okConfirmButton;
    public static int currentPlayer;
    public void showChallengeProfile(){
        challengeProfileFrame=new JFrame("Challenge Profile");
        challengeProfilePanel=new JPanel();
        challengeProfileFrame.setVisible(true);
        challengeProfilePanel.setSize(getLH(700, 1), getLH(550, 2));
        challengeProfilePanel.setLayout(null);
        challengeProfilePanel.setBackground(Color.WHITE);

        JLabel topLabel=new JLabel();
        if(challengeType==1){
            topLabel.setText("It is a single player challenge");
                }
        else{
            topLabel.setText("It is a "+numberOfPlayers+" players challenge");
                }
        topLabel.setFont(new Font("Aharoni", Font.BOLD, 23));
        topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
        challengeProfilePanel.add(topLabel);

        JLabel topLabel2=new JLabel();
        if(challengeType==1){
            topLabel2.setText("Going to be tough");
        }
        else{
            topLabel2.setText("Going to be fun");
        }
        topLabel2.setFont(new Font("Aharoni", Font.ITALIC, 14));
        topLabel2.setBounds(getLH(150, 1), getLH(35, 2), topLabel2.getPreferredSize().width, topLabel2.getPreferredSize().height);
        challengeProfilePanel.add(topLabel2);
        
        cAvatar=new JLabel[numberOfPlayers];
        if(challengeType==1){
            ImageIcon ava=new ImageIcon(".//res//1.png");
            avatar=new JLabel(singlePlayerName, ava, JLabel.CENTER);
            avatar.setFont(new Font("Aharoni", Font.BOLD, 25));
            avatar.setVerticalTextPosition(JLabel.BOTTOM);
            avatar.setHorizontalTextPosition(JLabel.CENTER);
            avatar.setBounds(getLH(300, 1), getLH(70, 1), avatar.getPreferredSize().width, avatar.getPreferredSize().height);
            challengeProfilePanel.add(avatar);
        }
        
        else{
            for(int i=0; i<numberOfPlayers; i++){
                ImageIcon ava=new ImageIcon(".//res//"+Integer.toString(i+1)+".png");
                cAvatar[i]=new JLabel(challengePlayersName[i], ava, JLabel.CENTER);
                cAvatar[i].setFont(new Font("Aharoni", Font.BOLD, 25));
                cAvatar[i].setVerticalTextPosition(JLabel.BOTTOM);
                cAvatar[i].setHorizontalTextPosition(JLabel.CENTER);
                cAvatar[i].setBounds(getLH(80+i*(cAvatar[0].getPreferredSize().width+15), 1), getLH(70, 2), cAvatar[0].getPreferredSize().width, cAvatar[0].getPreferredSize().height);
                challengeProfilePanel.add(cAvatar[i]);
            }
        }
        
        JLabel hintLabel=new JLabel();
        if(challengeType==1){
            hintLabel.setText("Player is expected to finish the game on or before:");
                }
        else{
            hintLabel.setText("Players are expected to finish the game on or before:");
                }
        
        hintLabel.setFont(new Font("Aharoni", Font.BOLD, 22));
        hintLabel.setForeground(Color.BLUE);
        hintLabel.setBounds(getLH(60, 1), getLH(170, 2), hintLabel.getPreferredSize().width, hintLabel.getPreferredSize().height);
        challengeProfilePanel.add(hintLabel);
        
        ImageIcon timeIcon=new ImageIcon(".//res//clock.png");
        ImageIcon clicksIcon=new ImageIcon(".//res//clicks.png");
        ImageIcon symbolIcon=new ImageIcon(".//res//symbol.png");
        JLabel timeLabel1=new JLabel(expectedTime+" seconds", timeIcon, JLabel.CENTER);
        timeLabel1.setFont(new Font("Monospaced", Font.BOLD, 45));
        timeLabel1.setVerticalTextPosition(JLabel.BOTTOM);
        timeLabel1.setHorizontalTextPosition(JLabel.CENTER);
        timeLabel1.setForeground(Color.GREEN);
        timeLabel1.setBounds(getLH(30, 1), getLH(200, 2), timeLabel1.getPreferredSize().width, timeLabel1.getPreferredSize().height);
        
        JLabel symbolLabel1=new JLabel(symbolIcon);
        symbolLabel1.setBounds(getLH(290, 1), getLH(250, 2), symbolLabel1.getPreferredSize().width, symbolLabel1.getPreferredSize().height);
        
        JLabel clicksLabel1=new JLabel(expectedClicks+" clicks", clicksIcon, JLabel.CENTER);
        clicksLabel1.setFont(new Font("Monospaced", Font.BOLD, 45));
        clicksLabel1.setVerticalTextPosition(JLabel.BOTTOM);
        clicksLabel1.setHorizontalTextPosition(JLabel.CENTER);
        clicksLabel1.setForeground(Color.RED);
        clicksLabel1.setBounds(getLH(370, 1), getLH(200, 2), clicksLabel1.getPreferredSize().width, clicksLabel1.getPreferredSize().height);
        
        
        if(rad2[0].isSelected()){
            challengeProfilePanel.add(timeLabel1);
        }
        else if(rad2[1].isSelected()){
            clicksLabel1.setBounds(timeLabel1.getBounds());
            challengeProfilePanel.add(clicksLabel1);
        }
        else{
            challengeProfilePanel.add(timeLabel1);
            challengeProfilePanel.add(clicksLabel1);
            challengeProfilePanel.add(symbolLabel1);
        }

        okConfirmButton=new JButton("Next>>");
        okConfirmButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        okConfirmButton.setBounds(getLH(10, 1), getLH(470, 2), getLH(100, 1), getLH(25, 2));
        okConfirmButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                if(challengeType==1){
                    playSingleChallenge();
                }
                else{
                    aGChallenge=new AdsmutShuffle[numberOfPlayers+1];
                    playGroupChallenge();
                    
                }
                
            }
        });
        challengeProfilePanel.add(okConfirmButton);
        
        cancelChallengeButton=new JButton("Cancel");
        cancelChallengeButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        cancelChallengeButton.setBounds(getLH(550, 1), getLH(470, 2), getLH(100, 1), getLH(25, 2));
        cancelChallengeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                initComponents();
                challengeProfileFrame.dispose();
                
            }
        });
        challengeProfilePanel.add(cancelChallengeButton);
        
        editChallengeButton=new JButton("Edit");
        editChallengeButton.setFont(new Font("Aharoni", Font.BOLD, 14));
        editChallengeButton.setBounds(getLH(275, 1), getLH(470, 2), getLH(100, 1), getLH(25, 2));
        editChallengeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
                challengeFrame.setVisible(true);
                challengeProfileFrame.setVisible(false);
                if(challengeType==1){
                    singlePlayerName= (String) JOptionPane.showInputDialog(null, "Edit player name", "Enter new player name", JOptionPane.QUESTION_MESSAGE, null, null, singlePlayerName);
                    if(singlePlayerName.trim().length() == 0||singlePlayerName.matches("^[^a-zA-Z]+$"))
                        singlePlayerName="Player";
                }
            }
        });
        challengeProfilePanel.add(editChallengeButton);
        
        challengeProfileFrame.add(challengeProfilePanel);
        //progressFrame.setResizable(false);
        challengeProfileFrame.setBounds(getLH(310, 1), getLH(60, 2), getLH(700, 1), getLH(550, 2));
        challengeProfileFrame.setVisible(true);
        challengeProfileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public static AdsmutShuffle[] aGChallenge;
    public void playGroupChallenge(){
        currentPlayer++;
        //dispose();
        if(currentPlayer<=numberOfPlayers){
            aGChallenge[currentPlayer]=new AdsmutShuffle();
            aGChallenge[currentPlayer].showGamePane();
            aGChallenge[currentPlayer].chalengeModeButton.setVisible(false);
            aGChallenge[currentPlayer].highScoreButton.setVisible(false);
            aGChallenge[currentPlayer].changeColorButton.setBounds(getLH(10, 1), getLH(210, 2), getLH(200, 1), getLH(30, 2));
            aGChallenge[currentPlayer].playButton.setVisible(false);
            aGChallenge[currentPlayer].changeColorButton.setEnabled(true);
            currentExpectedTime=expectedTime;
            cPlayButton=new JButton("Start");
            cPlayButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
            cPlayButton.setBounds(getLH(10, 1), getLH(585, 2), getLH(382, 1), getLH(30, 2));
            cPlayButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt){
            //startTime();

            startTimeCountDown(expectedTime);
            aGChallenge[currentPlayer].infoPanel.add(cTimeArea);
            aGChallenge[currentPlayer].shuffleGame();
            //inversion
            while(!aGChallenge[currentPlayer].isSolvable()){
                aGChallenge[currentPlayer].shuffleGame();
            }
            //System.out.println(getPresentTilePosition(charButton[i].getBounds()));
            cPlayButton.setEnabled(false);
            }
            });
            aGChallenge[currentPlayer].add(cPlayButton);

            String topLabelText=challengeType==1?"Challenge Mode: Single Player":"Challenge Mode: "+numberOfPlayers+" players";
            JLabel topLabel=new JLabel(topLabelText);
            topLabel.setFont(new Font("Aharoni", Font.PLAIN, 14));
            topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
            aGChallenge[currentPlayer].infoPanel.add(topLabel);

            ImageIcon ava=new ImageIcon(".//res//1.png");
            JLabel avatar=new JLabel(challengePlayersName[currentPlayer-1], ava, JLabel.CENTER);
            avatar.setFont(new Font("Aharoni", Font.BOLD, 25));
            avatar.setVerticalTextPosition(JLabel.BOTTOM);
            avatar.setHorizontalTextPosition(JLabel.CENTER);
            avatar.setBounds(getLH(65, 1), getLH(25, 2), avatar.getPreferredSize().width, avatar.getPreferredSize().height);
            aGChallenge[currentPlayer].infoPanel.add(avatar);

            JLabel topLabel2=new JLabel("Expected time: "+expectedTime+" seconds");
            topLabel2.setFont(new Font("Aharoni", Font.PLAIN, 16));
            topLabel2.setBounds(getLH(10, 1), getLH(97, 2), topLabel2.getPreferredSize().width, topLabel2.getPreferredSize().height);

            JLabel topLabel3=new JLabel("Maximum clicks: "+expectedClicks);
            topLabel3.setFont(new Font("Aharoni", Font.PLAIN, 16));
            topLabel3.setBounds(getLH(10, 1), getLH(113, 2), topLabel3.getPreferredSize().width, topLabel3.getPreferredSize().height);

            if(rad2[0].isSelected()){
                aGChallenge[currentPlayer].infoPanel.add(topLabel2);
            }
            else if(rad2[1].isSelected()){
                topLabel3.setBounds(topLabel2.getBounds());
                aGChallenge[currentPlayer].infoPanel.add(topLabel3);
            }
            else{
                aGChallenge[currentPlayer].infoPanel.add(topLabel2);
                aGChallenge[currentPlayer].infoPanel.add(topLabel3);
            }
            aGChallenge[currentPlayer].infoPanel.repaint();
        }
        else{
            //to pass the player names to the rank method, we need to let the index start from 1 instead of 0 we used in challenge name
            //therefore, this line writes the player names int another array starting from index 1
            String[] playerNames = new String[numberOfPlayers + 1];
            for(int i=1; i<=numberOfPlayers; i++){
                playerNames[i] = challengePlayersName[i-1];
            }
            
            //this line passes the player names and the scores to the rank method, 
            //the rank method will return an array of results with player ranks in ascending order of indices
            String[] results = rankScores(playerScore, playerNames);
            
            //Frame to hold the panes for results
            JFrame resultFrame = new JFrame("Results");
            resultFrame.setLayout(new FlowLayout());
            resultFrame.setSize(new Dimension(getLH(360, 1), getLH(50*(numberOfPlayers + 2), 2)));
            
            //declaring panels to hold each player details
            JPanel[] playerPane = new JPanel[numberOfPlayers+1];
            for(int i=1; i<=numberOfPlayers; i++){
                playerPane[i] = new JPanel();
                JLabel l = new JLabel(results[i]);
                l.setFont(new Font("Arial", Font.BOLD, getLH(20, 2)));
                playerPane[i].add(l);
                playerPane[i].setPreferredSize(new Dimension(getLH(350, 1), getLH(50, 2)));
                //let the winner have green background
                JFileChooser f = new JFileChooser();
                if(i == 1)
                    playerPane[i].setBackground(Color.GREEN);
                //let the second have blue
                else if(i == 2)
                    playerPane[i].setBackground(Color.BLUE);
                //let the rest have red
                else
                    playerPane[i].setBackground(Color.RED);
                
                resultFrame.add(playerPane[i]);
            }
            resultFrame.add(new JButton("Back to main menu"));
            
            resultFrame.setResizable(false);
            resultFrame.setVisible(true);
            resultFrame.setLocationRelativeTo(null);
            
        }
    }
    //method to rank the players according to their scores
    //this method still needs some perferctions: there is a bug in case two or more players have the same score
    public String[] rankScores(double scores[], String playerNames[]){
        //get number of players from the length of the array passed
        int nPlayers = scores.length-1;
        //duplicate the scores to another array to have it saved somewhere before ranking
        double scoresDup[] = new double[nPlayers+1];
        for(int i = 1; i <= nPlayers; i++){
            scoresDup[i] = scores[i];
        }
        //sort the scores in ascending order, although, descending order is what we need
        Arrays.sort(scores);
        //now changing the sorted array to descending order
        for(int i = 1; i <= nPlayers/2; i++){
            double temp = scores[i];
            scores[i] = scores[nPlayers-i+1];
            scores[nPlayers-i+1] = temp;
        }
        //now sort player names to fit the ranked scores
        String sortedPlayerNames[] = new String[nPlayers+1];
        for(int i = 1; i <= nPlayers; i++){
            for(int j = 1; j <= nPlayers; j++){
                if(scores[i] == scoresDup[j]){
                    sortedPlayerNames[i] = playerNames[j];
                }
            }
        }
        //concatenating the rank, names and scores
        String[] results = new String[nPlayers+1];
        for(int i = 1; i <= nPlayers; i++){
            results[i] = i + ". " +sortedPlayerNames[i] + "  Total Score: " + String.format("%.0f", scores[i]);
        }
        return results;
    }
    public void playSingleChallenge(){
        dispose();
        challengeProfileFrame.dispose();
        aChallenge = new AdsmutShuffle();
        aChallenge.showGamePane();
        aChallenge.chalengeModeButton.setVisible(false);
        aChallenge.highScoreButton.setVisible(false);
        aChallenge.changeColorButton.setBounds(getLH(10, 1), getLH(210, 2), getLH(200, 1), getLH(30, 2));
        aChallenge.playButton.setVisible(false);
        aChallenge.changeColorButton.setEnabled(true);
        
        currentExpectedTime = expectedTime;
        cPlayButton = new JButton("Start");
        cPlayButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        cPlayButton.setBounds(getLH(10, 1), getLH(585, 2), getLH(382, 1), getLH(30, 2));
        cPlayButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            startTimeCountDown(expectedTime);
            aChallenge.infoPanel.add(cTimeArea);
            aChallenge.shuffleGame();
            //inversion
            while(!aChallenge.isSolvable()){
                aChallenge.shuffleGame();
            }
            cPlayButton.setEnabled(false);
        }
        });
        aChallenge.add(cPlayButton);

        //top labe displays Single player challenge or n players challenge
        String topLabelText = challengeType == 1 ? "Challenge Mode: Single Player" : "Challenge Mode: " + numberOfPlayers + " players";
        JLabel topLabel=new JLabel(topLabelText);
        topLabel.setFont(new Font("Aharoni", Font.PLAIN, 14));
        topLabel.setBounds(getLH(10, 1), getLH(10, 2), topLabel.getPreferredSize().width, topLabel.getPreferredSize().height);
        aChallenge.infoPanel.add(topLabel);

        ImageIcon ava = new ImageIcon(".//res//1.png");
        JLabel avatar = new JLabel(singlePlayerName, ava, JLabel.CENTER);
        avatar.setFont(new Font("Aharoni", Font.BOLD, 25));
        avatar.setVerticalTextPosition(JLabel.BOTTOM);
        avatar.setHorizontalTextPosition(JLabel.CENTER);
        avatar.setBounds(getLH(65, 1), getLH(25, 2), avatar.getPreferredSize().width, avatar.getPreferredSize().height);
        aChallenge.infoPanel.add(avatar);

        JLabel topLabel2 = new JLabel("Expected time: " + expectedTime + " seconds");
        topLabel2.setFont(new Font("Aharoni", Font.PLAIN, 16));
        topLabel2.setBounds(getLH(10, 1), getLH(97, 2), topLabel2.getPreferredSize().width, topLabel2.getPreferredSize().height);

        JLabel topLabel3 = new JLabel("Maximum clicks: " + expectedClicks);
        topLabel3.setFont(new Font("Aharoni", Font.PLAIN, 16));
        topLabel3.setBounds(getLH(10, 1), getLH(113, 2), topLabel3.getPreferredSize().width, topLabel3.getPreferredSize().height);

        if(rad2[0].isSelected()){
            aChallenge.infoPanel.add(topLabel2);
        }
        else if(rad2[1].isSelected()){
            topLabel3.setBounds(topLabel2.getBounds());
            aChallenge.infoPanel.add(topLabel3);
        }
        else{
            aChallenge.infoPanel.add(topLabel2);
            aChallenge.infoPanel.add(topLabel3);
        }
        aChallenge.infoPanel.repaint();
                
                
    }
    //initializes some important components
    public void initComponents(){
        this.rad2 = new JRadioButton[3];
        this.count2 = 0;
        this.numberOfPlayers = 0;
        this.timeFieldC = new JTextField();
        this.timeLabel = new JLabel();
        this.clicksField = new JTextField();
        this.clicksLabel = new JLabel();
        this.cFriendsButton = new JButton();
        this.cYSButton = new JButton();
        this.cancelChallengeButton = new JButton();
        this.editChallengeButton = new JButton();
        this.challengeFrame = new JFrame();
        this.challengePanel = new JPanel();
        this.startButton = new JButton();
        
        randomPlayersArrangementRad = new JRadioButton();
        this.challengeType = 0;
        this.expectedTime = 0;
        this.expectedClicks = 0;
        this.singlePlayerName = "";
    }
    public boolean hasAMatchElement(int[] arr, int element){
        //this method checks if a particular value exists twice in arr
        boolean isContain = false;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] == element)
                isContain = true;
        }
        return isContain;
    }
    
    public JTextField timeArea;
    public static JTextField cTimeArea;
    //public String time;
    int timeCounter = 0;
    public static int cTimeCounter;
    public static Timer timeCountDown;
    Timer freeModeTimer;
    
    public void startTime(){
        try{
            timeArea.setText("");
            timeArea.setVisible(false);
        }catch(NullPointerException e){
            System.out.println("Never used before");
        }
        freeModeTimer = new Timer();
        timeArea = new JTextField();
        timeArea.setVisible(true);
        timeArea.setBounds(getLH(10, 1), getLH(300, 2), getLH(200, 1), getLH(50, 2));
        timeArea.setEditable(false);
        timeArea.setFont(new Font("Monospaced", Font.BOLD, 30));
        infoPanel.add(timeArea);
        
        //setting a schedule
        StartTimeCount tT = new StartTimeCount();
        freeModeTimer.schedule(tT, 0, 1000);
    }
    
    public void startTimeCountDown(int seconds){
        cTimeCounter = seconds;
        timeCountDown = new Timer();
        cTimeArea = new JTextField();
        cTimeArea.setVisible(true);
        cTimeArea.setBounds(getLH(10, 1), getLH(280, 2), getLH(200, 1), getLH(50, 2));
        cTimeArea.setEditable(false);
        cTimeArea.setFont(new Font("Monospaced", Font.BOLD, 30));
        //setting a schedule
        StartTimeCountDown tT=new StartTimeCountDown();
        timeCountDown.schedule(tT, 0, 1000);
    }
    
    public int getTimeCount(){
        return timeCounter;
    }
  
    class StartTimeCount extends TimerTask {
        public void run() {
            LocalTime lt = LocalTime.of(timeCounter/3600, (timeCounter/60)%60, timeCounter%60);
            String time = lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            timeArea.setText(time);
            timeArea.repaint();
            timeCounter++;
        }
    }
    
    class StartTimeCountDown extends TimerTask {
    public void run() {
    if(cTimeCounter>0){
    LocalTime lt = LocalTime.of(cTimeCounter/3600, (cTimeCounter/60)%60, cTimeCounter%60);
    String time = lt.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    cTimeArea.setText(time);
    cTimeArea.repaint();
    }
    else if(cTimeCounter == 0){
        cTimeArea.setText("Time up");
        cTimeArea.repaint();
        timeCountDown.cancel();
        //timeArea.setVisible(false);
        Timer timerForLose = new Timer();
        TimerTask taskForLose = new TimerTask(){
            int timeToDisplayLoseFrame = 0;
            public void run(){
                if(timeToDisplayLoseFrame == 0&&challengeType == 1){
                    ShuffleSound.playSound("lose");
                    aChallenge.setEnabled(false);
                }
                else if(timeToDisplayLoseFrame == 0&&challengeType == 2){
                    ShuffleSound.playSound("lose");
                    aGChallenge[currentPlayer].setEnabled(false);
                }
                if(timeToDisplayLoseFrame == 3){
                    displayLoseFrame();
                    cTimeArea.setVisible(false);
                }
                timeToDisplayLoseFrame++;
            }
        };
        timerForLose.schedule(taskForLose, 0, 1000);
        
    }
    cTimeCounter--;
    }
    }
    
    public void checkMovesStatus(int click, int ex){
        if(isCompleted()){
            ShuffleSound.playSound("win");
            timeCountDown.cancel();
            panel.setEnabled(false);
            for(JButton b: charButton){
                b.setEnabled(false);
            }
            cTimeArea.setText("You won!");
            cTimeArea.repaint();
            
            Timer timerForWin = new Timer();
            TimerTask taskForWin = new TimerTask(){
                int timeToDisplayWinFrame = 0;
                @Override
                public void run(){
                    if(timeToDisplayWinFrame == 0){
                        cTimeArea.setText("You won!");
                        cTimeArea.repaint();
                    }
                    if(timeToDisplayWinFrame == 1){
                        cTimeArea.setText("Wow!");
                        cTimeArea.repaint();
                    }
                    if(timeToDisplayWinFrame == 2){
                        cTimeArea.setText("Welldone!");
                        cTimeArea.repaint();
                    }
                    if(timeToDisplayWinFrame == 3){
                        displayWinFrame();
                    }
                    timeToDisplayWinFrame++;
                }
            };
            timerForWin.schedule(taskForWin, 0, 1000);
        }
        else if(click == ex && challengeType == 2){
            timeCountDown.cancel();
            cTimeArea.setVisible(false);
            
            JTextField moveFinished = new JTextField("No more moves!");
            moveFinished.setVisible(true);
            moveFinished.setBounds(getLH(10, 1), getLH(280, 2), getLH(200, 1), getLH(50, 2));
            moveFinished.setEditable(false);
            moveFinished.setFont(new Font("Monospaced", Font.BOLD, 20));
            
            aGChallenge[currentPlayer].infoPanel.add(moveFinished);
            //timeArea.setVisible(false);
            Timer timerForLose = new Timer();
            TimerTask taskForLose = new TimerTask(){
                int timeToDisplayLoseFrame=0;
                public void run(){
                    if(timeToDisplayLoseFrame == 0){
                        ShuffleSound.playSound("lose");
                        aGChallenge[currentPlayer].setEnabled(false);
                    }
                    if(timeToDisplayLoseFrame == 3){
                        displayLoseFrame();
                    }
                    timeToDisplayLoseFrame++;
                }
            };
            timerForLose.schedule(taskForLose, 0, 1000);
        }
        else if(click == ex){
            timeCountDown.cancel();
            cTimeArea.setVisible(false);
            JTextField moveFinished = new JTextField("No more moves!");
            moveFinished.setVisible(true);
            moveFinished.setBounds(getLH(10, 1), getLH(280, 2), getLH(200, 1), getLH(50, 2));
            moveFinished.setEditable(false);
            moveFinished.setFont(new Font("Monospaced", Font.BOLD, 20));
            aChallenge.infoPanel.add(moveFinished);
            //timeArea.setVisible(false);
            Timer timerForLose = new Timer();
            TimerTask taskForLose = new TimerTask(){
                int timeToDisplayLoseFrame = 0;
                public void run(){
                    if(timeToDisplayLoseFrame == 0){
                        ShuffleSound.playSound("lose");
                        aChallenge.setEnabled(false);
                    }
                    if(timeToDisplayLoseFrame == 3){
                        displayLoseFrame();
                    }
                    timeToDisplayLoseFrame++;
                }
            };
            timerForLose.schedule(taskForLose, 0, 1000);
        }
    }
    
    //Main method
    public static AdsmutShuffle adsShuf;
    public static void main(String[] args) {
        adsShuf = new AdsmutShuffle();
        adsShuf.showGamePane();
  }
}

    
