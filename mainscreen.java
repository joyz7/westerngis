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
 
public class mainscreen {
	
    static String searchText;
    static String poiJSON;
    static JSONArray pois;

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
        
        //Prepared map images
        BufferedImage alumni0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Alumni Hall-0.png"));
        JLabel alumni0 = new JLabel(new ImageIcon(alumni0Image));
        alumni0.setBounds(0,30,800,570);
        

        BufferedImage middle0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Middlesex College-0.png"));
        JLabel middle0 = new JLabel(new ImageIcon(middle0Image));
        middle0.setBounds(0,30,800,570);

        BufferedImage health1Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Health Sciences Building-1.png"));
        JLabel health1 = new JLabel(new ImageIcon(health1Image));
        health1.setBounds(0,30,800,570);

        //Create a scroll pane to hold the image
        JScrollPane alumni0scrollPane = new JScrollPane(alumni0);
        JScrollPane middle0scrollPane = new JScrollPane(middle0);
        JScrollPane health1scrollPane = new JScrollPane(health1);

        // Set the scroll pane properties
        alumni0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setPreferredSize(new Dimension(800, 500));

        middle0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setPreferredSize(new Dimension(800, 500));

        health1scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setPreferredSize(new Dimension(800, 500));
        
        // Set Scroll Bar speeds
        alumni0scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        health1scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        middle0scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        alumni0scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        health1scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        middle0scrollPane.getHorizontalScrollBar().setUnitIncrement(20);

        // Create Jframe for the entire window
        JFrame mainscreen = new JFrame();
        mainscreen.setLayout(null); // ENABLES PANELS TO BE ADDED WITHIN ONE ANOTHER
        mainscreen.setSize(1000,600); // make JFrame full screen
        mainscreen.setLocationRelativeTo(null); // center JFrame in the middle of the screen

       // JPanel for the top bar, that includes Search
        JPanel panelTop = new JPanel();
        panelTop.setBackground(Color.red);
        panelTop.setBounds(0,0, 1000, 30);
        
        // Create search bar
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        //event listener that takes in text the user searches
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //searchPOI(searchField);

                    searchText = searchField.getText();
                System.out.println("Search query: " + searchText);

                if (pois != null) {
                    for (int i = 0; i < pois.size(); i++) {

                        JSONObject poi = (JSONObject) pois.get(i);
                        String name = (String) poi.get("name");
                        long pid = (Long) poi.get("pid");
                        String roomnum = (String) poi.get("roomnum");
                        String description = (String) poi.get("description");


                        if (searchText.equals((String) poi.get("roomnum"))) {
                            System.out.println("Room number: " + (String) poi.get("roomnum"));
                            System.out.println("POI ID: " +(Long) poi.get("pid"));
                        }



                        /*
                        long xcoord = (Long) poi.get("xcoord");
                        long ycoord = (Long) poi.get("ycoord");


                        System.out.println("POI #" + pid);
                        System.out.println("Name: " + name);
                        //System.out.println("X coordinate: " + xcoord);
                        //System.out.println("Y coordinate: " + ycoord);
                        System.out.println("Room number: " + roomnum);
                        System.out.println("Description: " + description);
                        System.out.println();
                        */


                    }
                }

            }
        });
        panelTop.add(searchField);
        panelTop.add(searchButton);

        
       // JPanel for the map
       JTabbedPane panelMap = new JTabbedPane();
       panelMap.setBackground(Color.white);
       panelMap.setBounds(0,30,800,533);
       
       // JPanel behind the tabbed panels
       JPanel panelCenter = new JPanel();
       panelCenter.setBackground(Color.white);
       panelCenter.setBounds(0,30,800,533);
       panelCenter.add(panelMap);
       
       // Create different tabs
        panelMap.add("Alumni Hall", alumni0scrollPane);
        panelMap.add("Middlesex College", middle0scrollPane);
        panelMap.add("Health Sciences Building", health1scrollPane);
       
       // JPanel for the side bar
       JPanel panelSideBar = new JPanel();
       panelSideBar.setLayout(null);
       panelSideBar.setBackground(Color.yellow);
       panelSideBar.setBounds(800,30,200 , 533);
       
       // JPanel for the weather in the side bar
       JPanel panelWeather = new JPanel();
       panelWeather.setBackground(Color.green);
       panelWeather.setBounds(0,0,200,50);
       panelSideBar.add(panelWeather);
       
       // JPanel for the POIs
       JPanel panelPOI = new JPanel();
       panelPOI.setBackground(Color.blue);
       panelPOI.setBounds(0,50,200,550);
       panelSideBar.add(panelPOI);
       
        // Title for sidebar
        JLabel POITitle = new JLabel("Points of Interest");
        POITitle.setBounds(0, 5, 20, 40);
        POITitle.setFont(new Font("Arial", Font.BOLD, 12));
        POITitle.setBackground(Color.blue);
        POITitle.setForeground(Color.black);
        panelPOI.add(POITitle);
       
        // add all JPanels to the JFrame
        mainscreen.add(panelTop); // add top bar
        mainscreen.add(panelCenter); // add map
        mainscreen.add(panelSideBar); // add side bar
        mainscreen.setVisible(true);

    }
}