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
 
public class testing {
	
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

    public testing(DefaultListModel washroomsList, DefaultListModel classroomsList, DefaultListModel restaurantsList, DefaultListModel navigationList, DefaultListModel csSpecficList) throws IOException {

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
        JFrame mainscreen = new JFrame();
        mainscreen.setLocationRelativeTo(null); // center JFrame in the middle of the screen
        mainscreen.setBounds(100,100,350,350);
        
        //Prepared map images
        BufferedImage alumni0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Alumni Hall-0.png"));
        JLabel alumni0 = new JLabel(new ImageIcon(alumni0Image));

        BufferedImage middle0Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Middlesex College-0.png"));
        JLabel middle0 = new JLabel(new ImageIcon(middle0Image));

        BufferedImage health1Image = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\Health Sciences Building-1.png"));
        JLabel health1 = new JLabel(new ImageIcon(health1Image));

        //Create a scroll pane to hold the image
        JScrollPane alumni0scrollPane = new JScrollPane(alumni0);
        JScrollPane middle0scrollPane = new JScrollPane(middle0);
        JScrollPane health1scrollPane = new JScrollPane(health1);

        // Set the scroll pane properties
        alumni0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        alumni0scrollPane.setPreferredSize(new Dimension(800, 600));

        middle0scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        middle0scrollPane.setPreferredSize(new Dimension(800, 600));

        health1scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        health1scrollPane.setPreferredSize(new Dimension(800, 600));

      //Search bar in the top panel
        JPanel panelTop = new JPanel();

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


        //Create sidebar
        JPanel panelLeft = new JPanel();
        panelLeft.setBackground(new Color(102, 0, 153));
        panelLeft.setPreferredSize(new Dimension(50, 50));


        //Create center panel
        JTabbedPane panelCenter = new JTabbedPane();
        panelCenter.setBackground(new Color(102, 0, 153));
        //panelTop.setPreferredSize(new Dimension(50, 50));


        panelCenter.add("Alumni Hall", alumni0scrollPane);
        panelCenter.add("Middlesex Collage", middle0scrollPane);
        panelCenter.add("Health Sciences Building", health1scrollPane);

        //Create right check boxes
        JPanel panelRight= new JPanel(new GridBagLayout());
        panelRight.setBackground(new Color(102, 0, 153));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        panelRight.setPreferredSize(new Dimension(300, 1200));

        JCheckBox checkBox1 = new JCheckBox("Favourites");
        JCheckBox checkBox2 = new JCheckBox("User Created");
        JCheckBox checkBox3 = new JCheckBox("Washrooms");
        JCheckBox checkBox4 = new JCheckBox("Classrooms");
        JCheckBox checkBox5 = new JCheckBox("Navigation");
        JCheckBox checkBox6 = new JCheckBox("Resturaunts");
        JCheckBox checkBox7 = new JCheckBox("Gen Labs");
        JCheckBox checkBox8 = new JCheckBox("CS Specfic");

        JList favourites = new JList();
        JList userCreated = new JList();
        JList washrooms = new JList(washroomsList);
        JList classrooms = new JList(classroomsList);
        JList navigation = new JList(navigationList);
        JList restauraunts = new JList(restaurantsList);
        JList genLabs = new JList();
        JList csSpecfic = new JList(csSpecficList);
        
        JScrollPane favouritesScrollPane = new JScrollPane(favourites);
        JScrollPane userCreatedScrollPane = new JScrollPane(userCreated);
        JScrollPane washroomsScrollPane = new JScrollPane(washrooms);
        JScrollPane classroomsScrollPane = new JScrollPane(classrooms);
        JScrollPane navigationScrollPane = new JScrollPane(navigation);
        JScrollPane resturauntsScrollPane = new JScrollPane(restauraunts);
        JScrollPane genLabsScrollPane = new JScrollPane(genLabs);
        JScrollPane csSpecficScrollPane = new JScrollPane(csSpecfic);

        // Set the scroll pane properties
        favouritesScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        favouritesScrollPane.setPreferredSize(new Dimension(200, 100));

        userCreatedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        userCreatedScrollPane.setPreferredSize(new Dimension(200, 100));

        washroomsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        washroomsScrollPane.setPreferredSize(new Dimension(200, 100));
        
        classroomsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        classroomsScrollPane.setPreferredSize(new Dimension(200, 100));

        navigationScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        navigationScrollPane.setPreferredSize(new Dimension(200, 100));

        resturauntsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        resturauntsScrollPane.setPreferredSize(new Dimension(200, 100));
        
        genLabsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        genLabsScrollPane.setPreferredSize(new Dimension(200, 100));

        csSpecficScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        csSpecficScrollPane.setPreferredSize(new Dimension(200, 100));
        
        panelRight.add(checkBox1);
        panelRight.add(favouritesScrollPane, gbc);
         panelRight.add(checkBox2);
        panelRight.add(userCreatedScrollPane, gbc);
        panelRight.add(checkBox3);
        panelRight.add(washroomsScrollPane, gbc);
        panelRight.add(checkBox4);
        panelRight.add(classroomsScrollPane,gbc);
        panelRight.add(checkBox5);
        panelRight.add(navigationScrollPane, gbc);
        panelRight.add(checkBox6);
        panelRight.add(resturauntsScrollPane, gbc);
        panelRight.add(checkBox7);
        panelRight.add(genLabsScrollPane, gbc);
        panelRight.add(checkBox8);
        panelRight.add(csSpecficScrollPane, gbc);
        
        JPanel panelBottom = new JPanel();
        panelBottom.setBackground(new Color(102, 0, 153));
        panelBottom.setPreferredSize(new Dimension(50, 50));


        //Random
        JButton c = new JButton("Click Here");  
        panelRight.add(c);

        JComboBox jComboBox1 = new JComboBox();
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ground Floor", "First Floor", "Second Floor", "Third Floor", "Fourth Floor" }));
        panelBottom.add(jComboBox1);


        // main window
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Scroll Bar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the Jpanel to the main window
        frame.add(panelTop, BorderLayout.NORTH); 
        frame.add(panelLeft, BorderLayout.WEST);
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelRight, BorderLayout.EAST);
        frame.add(panelBottom, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        //frame.setResizable(false);
    }
}