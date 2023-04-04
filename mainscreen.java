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
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class mainscreen {
    
    private Main main;
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

    public mainscreen(Main main, DefaultListModel washroomsList, DefaultListModel classroomsList, DefaultListModel restaurantsList, DefaultListModel navigationList, DefaultListModel csSpecficList) throws IOException {

        this.main = main;
      
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
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                main.logOut();
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
       panelPOIs.setLayout(null);
       panelPOIs.setBackground(Color.red);
       panelPOIs.setBounds(0,80,230,520);
       panelSideBar.add(panelPOIs);
       
       JScrollPane panelPOIScroll = new JScrollPane();
       panelPOIs.setLayout(null);
       panelPOIs.setBackground(Color.pink);
       panelPOIs.setBounds(0,80,230,520);    
       panelPOIScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       panelPOIScroll.getVerticalScrollBar().setUnitIncrement(20);
       panelSideBar.add(panelPOIScroll); 
       
      
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
 
        //Jacky Added Code --- POI Stuff -----------------------------

                //button action listener to toggle on the poi adding mode
        addPOIBtn.addActionListener(e -> {
            
            addPOI = !addPOI; // Toggle the boolean variable
            if (addPOI == true){
                addPOIBtn.setText("Adding POI...");
            }
            else{
                addPOIBtn.setText("Add POI");                
            }
        });
        // Declare a MouseListener instance
        final MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouse click event here...
                System.out.println("asldkjasdojk");
            }
        };
        
        
                // Get the currently selected component
        selectedComponent = panelMap.getSelectedComponent();
        
         
        
        panelMap.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
        
                // Get the currently selected component
                Component selectedComponent = tabbedPane.getSelectedComponent();

                // Remove the MouseListener from the previously selected component (if any)
                // This is necessary to avoid adding the same listener multiple times
                // and potentially causing memory leaks or unexpected behavior.
                Component[] components = tabbedPane.getComponents();
                for (Component component : components) {
                    if (component != selectedComponent && component instanceof JComponent) {
                        ((JComponent) component).removeMouseListener(mouseListener);
                    }   
                }
                // Add a MouseListener to the selected component
//                selectedComponent.addMouseListener(mouseListener);
                
                // Add a MouseListener to the selected component
                selectedComponent.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Handle mouse click event here...
                        System.out.println(e.getX()+","+e.getY());
                    }
                });
                
                
//                // Get the currently active tab's component
//                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
//                selectedComponent = tabbedPane.getSelectedComponent();
//                System.out.println("yomama  "+tabbedPane.getSelectedIndex());
            }
        });
        
        panelMap.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the currently active tab's component
//                selectedComponent= panelCenter.getSelectedComponent();
                // Update the selected component variable
                System.out.println("boijoiiybh");
            }
        });
        
        //intial mouse listener?
        selectedComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the mouse click location
                System.out.println("-----mmmmmmmmm");
                if (addPOI == true){
                    newPoiAdd(main, mainscreen,e.getX(),e.getY());
                    addPOI = false; //Turn off the clicking
                    addPOIBtn.setText("Add POI");
                }

            }
        });
                
        
        
        // add all JPanels to the JFrame
        mainscreen.add(panelTop); // add top bar
        mainscreen.add(panelCenter); // add map
        mainscreen.add(panelSideBar); // add side bar
        mainscreen.setResizable(false);
        mainscreen.setVisible(true);

    }
     
    //Method of adding POI
    //creating a popup menu of getting poi info, and updating the user of adding
    //the poi or not
    private void newPoiAdd(Main main, JFrame frame, long xCoord, long yCoord){
        // Create a panel with a grid layout for the input boxes
        JPanel panel = new JPanel(new GridLayout(0, 2));

        // Add labels and text fields for point name, room number, and description
        panel.add(new JLabel("Point name:"));
        JTextField pointNameField = new JTextField();
        panel.add(pointNameField);

        panel.add(new JLabel("Room number:"));
        JTextField roomNumberField = new JTextField();
        panel.add(roomNumberField);

        panel.add(new JLabel("Description:"));
        JTextField descriptionField = new JTextField();
        panel.add(descriptionField);

        // Show the input dialog with the panel as the message
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter point information", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked OK and get the input values
        if (result == JOptionPane.OK_OPTION) {
          String name = pointNameField.getText();
          String roomNum = roomNumberField.getText();
          String description = descriptionField.getText();

          // Verify the inputs
          System.out.println("Point name: " + name);
          System.out.println("Room number: " + roomNum);
          System.out.println("Description: " + description);
          
          if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
            
//          Create POI
          POI newPOI = new POI(42069,42069,xCoord,yCoord,roomNum,name,description,false);
          main.addPOI(newPOI);
          //Hasmap.put(temp)
              JOptionPane.showMessageDialog(null, "Successfully added");
          }
          else{      
            JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
          }            
        } else {
            JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
        }
    }
}
