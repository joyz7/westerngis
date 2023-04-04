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
import java.awt.event.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.Component;

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
        mainscreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainscreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { // Save JSON data
                int count = 1; // Pass in from welcome + update when creating POIs
                HashMap<Integer,POI> poiMap = new HashMap<>();
                POI test = new POI(0,0,0,0,"MC 123", "Classroom", "class", true);
                POI test1 = new POI(1,0,0,0,"MC 234", "Washroom", "wash", true);
                poiMap.put(0,test);
                poiMap.put(1,test1);
                JSONArray pois = new JSONArray();
                for (int i=0; i<=count; i++) {
                    JSONObject poi = new JSONObject();
                    poi.put("name", poiMap.get(i).getName());
                    poi.put("xcoord", poiMap.get(i).getXCoord());
                    poi.put("yoord", poiMap.get(i).getYCoord());
                    poi.put("roomnum", poiMap.get(i).getRoomNum());
                    poi.put("layerid", poiMap.get(i).getLayerId());
                    poi.put("builtin", poiMap.get(i).isBuiltIn());  
                    poi.put("description", poiMap.get(i).getDescription());
                    pois.add(poi);
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("pois", pois);
                try {
                    FileWriter file = new FileWriter("src/main/java/com/cs2212/test.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                } catch (Exception error) {
                    error.printStackTrace();
                }
                e.getWindow().dispose();
            }
        });

        // JPanel for unsaved work
        /*JPanel exitPanel = new JPanel();
        LayoutManager layout = new FlowLayout();  
        exitPanel.setLayout(layout);       
        JButton button = new JButton("Click Me!");
        button.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
              JOptionPane.showMessageDialog(mainscreen, "Do you want to save your work?",
                 "Swing Tester", JOptionPane.ERROR_MESSAGE);
           }
        });

        panel.add(button);
        frame.getContentPane().add(panel, BorderLayout.CENTER);    
   }  */
        
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
       panelSideBar.setBounds(970,30,230 , 600);
       
       // JPanel for the weather in the side bar
       JPanel panelWeather = new JPanel();
       panelWeather.setBackground(Color.green);
       panelWeather.setBounds(0,0,230,50);
       panelSideBar.add(panelWeather);
       
       // JPanel for the POI Title and Button
       JPanel panelPOITitle = new JPanel();
       panelPOITitle.setLayout(null);
       panelPOITitle.setBackground(Color.blue);
       panelPOITitle.setBounds(0,50,230,30);
       panelSideBar.add(panelPOITitle);
       
       // JPanel for the POIs
       JPanel panelPOIs = new JPanel();
       panelPOIs.setLayout(new BorderLayout());
       panelPOIs.setBackground(Color.red);
       panelPOIs.setBounds(0,80,230,520);
       panelSideBar.add(panelPOIs);
       
       // CHANGES START HERE ----------------------------------------------
       
       CheckboxTree tree = new CheckboxTree(); 
       JScrollPane panelPOIScroll = new JScrollPane(tree); // add tree to scroll pane
       //panelPOIScroll.setLayout(null);
       panelPOIScroll.setBackground(Color.white);
       panelPOIScroll.setBounds(0,80,230,520);    
       panelPOIScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
       panelPOIScroll.getVerticalScrollBar().setUnitIncrement(20);
       panelPOIs.add(panelPOIScroll); // add scroll pane to side bar
       
       // ai ya
       /*
       CheckboxTree POIList = new CheckboxTree();
       DefaultTreeModel model = new DefaultTreeModel(createTree());
       POIList.setModel(model);
       POIList.setRootVisible(true);
       
       POIList.addCheckChangeEventListener(new CheckboxTree.CheckChangeEventListener() {
            public void checkStateChanged(CheckboxTree.CheckChangeEvent event) {
                System.out.println("event");
                TreePath[] paths = POIList.getCheckedPaths();
                for (TreePath tp : paths) {
                    for (Object pathPart : tp.getPath()) {
                        System.out.print(pathPart + ",");
                    }                   
                    System.out.println();
                }
            }           
        }); 
       
       */
       /*
       JScrollPane panelPOIScroll = new JScrollPane(POIList); // add tree to scroll pane
       panelPOIs.setLayout(null);
       panelPOIs.setBackground(Color.pink);
       panelPOIs.setBounds(0,80,230,520);    
       panelPOIScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       panelPOIScroll.getVerticalScrollBar().setUnitIncrement(20);
       panelSideBar.add(panelPOIScroll); // add scroll pane to side bar
       //panelSideBar.repaint();
*/
                    // CHANGES END HERE ------------------------------------------------    
                    
        // Title for sidebar
        JLabel POITitle = new JLabel("Points of Interest");
        POITitle.setBounds(5, 5, 200, 20);
        POITitle.setFont(new Font("Arial", Font.BOLD, 12));
        POITitle.setBackground(Color.blue);
        POITitle.setForeground(Color.black);
        panelPOITitle.add(POITitle);
        
        // Add a button to Add POIs
        JButton addPOIBtn = new JButton("Add POI");
        addPOIBtn.setBounds(120,5, 90, 20);
        //addPOIBtn.setHorizontalAlignment(SwingConstants.LEFT);
        panelPOITitle.add(addPOIBtn);
 
        // add all JPanels to the JFrame
        mainscreen.add(panelTop); // add top bar
        mainscreen.add(panelCenter); // add map
        mainscreen.add(panelSideBar); // add side bar
        //mainscreen.setResizable(false);
        mainscreen.setVisible(true);

    }
        // ADDED CODE HERE
        private DefaultMutableTreeNode createTree() {
            DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
            root.add(new DefaultMutableTreeNode("child 1"));
            root.add(new DefaultMutableTreeNode("child 2"));
            return root;
        }
}