package com.cs2212;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

//import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.DefaultListModel;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class mainscreen {
	
    static String searchText;
    static String poiJSON;
    static JSONArray pois;
    boolean addPOI = false;
    Component selectedComponent;
    final int mainscreenWidth = 1200; // width of the JFrame
    final int mainscreenHeight = 650; // height of the JFrame
    // REMINDER: ADD CONSTANTS FOR THE WIDTHS AND HEIGHTS OF EVERYTHING
    String[] alumniFloors = {"Basement", "Ground Floor", "Second Floor"}; // change to dynamically populate if have time
    //String[] healthFloors = {"Ground Floor", "Second Floor", "Third Floor", "Fourth Floor"};
    
    
    /*
    public int searchPOI(JTextField searchField){
            searchText = searchField.getText();
    System.out.println("Search query: " + searchText);

    return 0;
    }

    */

    public mainscreen(DefaultListModel washroomsList, DefaultListModel classroomsList, DefaultListModel restaurantsList, DefaultListModel navigationList, DefaultListModel csSpecficList) throws IOException {

      //Parse POI json
        String filename = ".\\src\\main\\java\\com\\cs2212\\POI.json";


        try {
            //Parse and print out each of the different results 
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObj = (JSONObject) obj;
            pois = (JSONArray) jsonObj.get("pois");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create Jframe for the entire window
        JFrame mainscreen = new JFrame("Map of Western University");
        mainscreen.setLayout(null); // ENABLES PANELS TO BE ADDED WITHIN ONE ANOTHER
        mainscreen.setSize(mainscreenWidth,mainscreenHeight); // make JFrame full screen
        mainscreen.setLocationRelativeTo(null); // center JFrame in the middle of the screen
        mainscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Prepared map images
        BufferedImage alumni0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Alumni Hall-0.png"));
        JLabel alumni0 = new JLabel(new ImageIcon(alumni0Image));
        alumni0.setBounds(0,30,970,550);
        

        BufferedImage middle0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Middlesex College-0.png"));
        JLabel middle0 = new JLabel(new ImageIcon(middle0Image));
        middle0.setBounds(0,30,970,550);

        BufferedImage health1Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Health Sciences Building-1.png"));
        JLabel health1 = new JLabel(new ImageIcon(health1Image));
        health1.setBounds(0,30,970,550);

        //Create a scroll pane to hold the image
        JScrollPane alumni0scrollPane = new JScrollPane(alumni0);
        JScrollPane middle0scrollPane = new JScrollPane(middle0);
        JScrollPane health1scrollPane = new JScrollPane(health1);

        // Set the scroll pane properties
        alumni0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setPreferredSize(new Dimension(970, 550));

        middle0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setPreferredSize(new Dimension(970, 550));

        health1scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setPreferredSize(new Dimension(970, 550));
        
        // Set Scroll Bar speeds
        alumni0scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        health1scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        middle0scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        alumni0scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        health1scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        middle0scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        
       // JPanel for the top bar, that includes Search
        JPanel panelTop = new JPanel();
        panelTop.setLayout(null);
        panelTop.setBackground(Color.red);
        panelTop.setBounds(0,0, 1200, 30);
        
        // Create search bar
        JTextField searchField = new JTextField(20);
        searchField.setBounds(450,3,225,24);
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(685,3,100,25);
        panelTop.add(searchField);
        panelTop.add(searchButton);
        
       // Create dropdown to switch floors
       JComboBox floors = new JComboBox(alumniFloors);
       floors.setBounds(825,3,125,24);
       panelTop.add(floors);

        
       // JPanel for the map
       JTabbedPane panelMap = new JTabbedPane();
       panelMap.setBackground(Color.white);
       panelMap.setBounds(0,30,970,620);
       
       // JPanel behind the tabbed panels
       JPanel panelCenter = new JPanel();
       panelCenter.setBackground(Color.white);
       panelCenter.setBounds(0,30,970,620);
       panelCenter.add(panelMap);
       
       // Create different tabs
        panelMap.add("Alumni Hall", alumni0scrollPane);
        panelMap.add("Middlesex College", middle0scrollPane);
        panelMap.add("Health Sciences Building", health1scrollPane);
       
       // JPanel for the side bar
       JPanel panelSideBar = new JPanel();
       panelSideBar.setLayout(null);
       panelSideBar.setBackground(Color.yellow);
       panelSideBar.setBounds(970,30,230 , 620);
       
       // JPanel for the weather in the side bar
       JPanel panelWeather = new JPanel();
       panelWeather.setBackground(Color.green);
       panelWeather.setBounds(0,0,230,50);
       panelSideBar.add(panelWeather);
       
       // JPanel for the POIs
       JPanel panelPOI = new JPanel();
       panelPOI.setLayout(null);
       panelPOI.setBackground(Color.blue);
       panelPOI.setBounds(0,50,230,550);
       panelSideBar.add(panelPOI);
       
        // Title for sidebar
        JLabel POITitle = new JLabel("Points of Interest");
        POITitle.setBounds(5, 5, 200, 20);
        POITitle.setFont(new Font("Arial", Font.BOLD, 12));
        POITitle.setBackground(Color.blue);
        POITitle.setForeground(Color.black);
        panelPOI.add(POITitle);
        
        // Add a button to Add POIs
        JButton addPOIBtn = new JButton("Add POI");
        addPOIBtn.setBounds(120,5, 90, 20);
        //addPOIBtn.setHorizontalAlignment(SwingConstants.LEFT);
        panelPOI.add(addPOIBtn);
 
        // add all JPanels to the JFrame
        mainscreen.add(panelTop); // add top bar
        mainscreen.add(panelCenter); // add map
        mainscreen.add(panelSideBar); // add side bar
        mainscreen.setVisible(true);

    }
}