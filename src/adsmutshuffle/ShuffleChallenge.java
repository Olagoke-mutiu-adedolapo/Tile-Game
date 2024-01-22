/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsmutshuffle;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 *
 * @author ADMUDS
 */
public class ShuffleChallenge {
    private JRadioButton[] rad2;
    int count2=0;
    public JTextField timeFieldC;
    public JLabel timeLabel;
    public AdsmutShuffle aChallenge;
    public JTextField clicksField;
    public JLabel clicksLabel;
    private JButton cFriendsButton;
    private JButton cYSButton;
    private JButton cPlayButton;
    public JButton cancelChallengeButton;
    private JButton editChallengeButton;
    private JFrame challengeFrame;
    private JPanel challengePanel;
    private JButton startButton;
    private JTextField[] challengePlayersField;
    private String[] challengePlayersName;
    private int numberOfPlayers;
    private JRadioButton randomPlayersArrangementRad;
    private int challengeType;
    public int expectedTime;
    public int currentExpectedTime;
    public int expectedClicks;
    public String singlePlayerName;
    public JLabel avatar;
    public ShuffleChallenge(){
        
    }
    
}
