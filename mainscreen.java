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
	
    static String searchText;
    static String poiJSON;
    static JSONArray pois;
    boolean addPOI = false;
    Component selectedComponent;
    final int mainscreenWidth = 1200; // width of the JFrame
    final int mainscreenHeight = 650; // height of the JFrame
    // REMINDER: ADD CONSTANTS FOR THE WIDTHS AND HEIGHTS OF EVERYTHING
    //String[] alumniFloors = {"Basement", "Ground Floor", "Second Floor"}; // change to dynamically populate if have time
    //String[] healthFloors = {"Ground Floor", "Second Floor", "Third Floor", "Fourth Floor"};
    JTabbedPane panelMap;
    JScrollPane alumniScrollPane;
    JScrollPane middlesexScrollPane;
    JScrollPane healthScrollPane;
    
    /*
    public int searchPOI(JTextField searchField){
            searchText = searchField.getText();
    System.out.println("Search query: " + searchText);

    return 0;
    }

    */
    public void createMap (String building, int floor) throws IOException {
        try {
            //Prepared map images
            BufferedImage mapImage = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\" + building + "-" + floor + ".png"));
            JLabel image = new JLabel(new ImageIcon(mapImage));
            image.setBounds(0,30,970,550);
            if (building.equals("Alumni Hall")) {
                //Create a scroll pane to hold the image
                alumniScrollPane = new JScrollPane(image);
                //Set the scroll pane properties
                alumniScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                alumniScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                alumniScrollPane.setPreferredSize(new Dimension(970, 550));
                //Set scroll bar speed
                alumniScrollPane.getVerticalScrollBar().setUnitIncrement(20);
                alumniScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
                panelMap.add(building, alumniScrollPane);
            } else if (building.equals("Middlesex College")) {
                middlesexScrollPane = new JScrollPane(image);
                middlesexScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                middlesexScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                middlesexScrollPane.setPreferredSize(new Dimension(970, 550));
                middlesexScrollPane.getVerticalScrollBar().setUnitIncrement(20);
                middlesexScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
                panelMap.add(building, middlesexScrollPane);
            } else if (building.equals("Health Sciences Building")) {
                healthScrollPane = new JScrollPane(image);
                healthScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                healthScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                healthScrollPane.setPreferredSize(new Dimension(970, 550));
                healthScrollPane.getVerticalScrollBar().setUnitIncrement(20);
                healthScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
                panelMap.add(building, healthScrollPane);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void changeFloor(String building, int floor) throws IOException {
        try {
            BufferedImage mapImage = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\" + building + "-" + floor + ".png"));
            JLabel image = new JLabel(new ImageIcon(mapImage));
            image.setBounds(0,30,970,550);
            //Create a scroll pane to hold the image
            if (building.equals("Alumni Hall")) {
                alumniScrollPane.setViewportView(image);
            } else if (building.equals("Middlesex College")) {
                middlesexScrollPane.setViewportView(image);
            } else if (building.equals("Health Sciences Building")) {
                healthScrollPane.setViewportView(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public mainscreen(DefaultListModel washroomsList, DefaultListModel classroomsList, DefaultListModel restaurantsList, DefaultListModel navigationList, DefaultListModel csSpecficList) throws IOException {
        panelMap = new JTabbedPane();
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
        
        // Create building maps
        createMap("Alumni Hall",0);
        createMap("Middlesex College",0);
        createMap("Health Sciences Building",1);

       // JPanel for the top bar, that includes Search
        JPanel panelTop = new JPanel();
        panelTop.setLayout(null);
        panelTop.setBackground(Color.red);
        panelTop.setBounds(0,0, 1200, 30);
        
        // Create search bar
        JTextField searchField = new JTextField(20);
        searchField.setBounds(10,3,650,24);
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(685,3,100,25);
        JButton closeResults = new JButton("Close");
        closeResults.setBounds(790,3,100,25);
        
        //create String list to contain search results
        DefaultListModel<String> searchResultsList = new DefaultListModel<>();
        JList resultJList = new JList<>(searchResultsList);
        JScrollPane resultScrollPane = new JScrollPane(resultJList);

        //add search bar components into top panel
        panelTop.add(searchField);
        panelTop.add(searchButton);
        panelTop.add(closeResults);
        
        //add search results components into top panel
        JPanel searchResultsPanel = new JPanel();
        searchResultsPanel.add(resultScrollPane);
        
        resultScrollPane.setBounds(10, 30, 650, 50);    
        panelTop.add(resultScrollPane);
        resultScrollPane.setVisible(false);
        
        //event listener that takes in text the user searches
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	searchResultsList.clear();
            	
            	searchText = searchField.getText();
                System.out.println("Search query: " + searchText);

                
                if (pois != null) {
                	for (int i = 0; i < pois.size(); i++) {
                		
                	
                        JSONObject poi = (JSONObject) pois.get(i);
                        String name = (String) poi.get("name");
                        long pid = (Long) poi.get("pid");
                        String roomnum = (String) poi.get("roomnum");
                        String description = (String) poi.get("description");
                        //poiMap.get(i).getName().equals(name)
                        
                        //Search for room number
                        if (searchText.equals(roomnum)) {
                        	System.out.println("Room number: " + roomnum);
                        	System.out.println("POI ID: " + pid);
                        	searchResultsList.addElement(name);
                        	
                        }
                        
                      //Search for name
                        if (searchText.equals(name)) {
                        	System.out.println("Name: " + name);
                        	System.out.println("POI ID: " + pid);
                        	searchResultsList.addElement(name);
                        }
                        
                        String []strArray = description.split(" ");
                        
                      //Search for description
                        for(int k=0; k<strArray.length;k++) {
                        	if (searchText.equals(strArray[k])) {
                            	System.out.println("Description: " + description);
                            	System.out.println("POI ID: " + description);
                            	searchResultsList.addElement(name);
                            }
                        }
                       
                    }
                	panelTop.setBounds(0,0, 1200, 100);
                	resultScrollPane.setVisible(true);
                	
                }
                
            }
        });
        
        //Event listener to close the search results
        closeResults.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		resultScrollPane.setVisible(false);
        		panelTop.setBounds(0,0, 1200, 30);
        	}
        });
        
        Campus campus = new Campus("Western University", "1151 Richmond Street, London");
        /*Building middlesex = new Building("Middlesex College", "1151 Richmond Street, London");
        Building healthsci = new Building("Health Sciences Building", "1151 Huron Drive, London");*/
        Building alumni = new Building("Alumni Hall", "Lambton Dr, London");
        Floor a0 = new Floor(0, alumni, "src/main/java/com/cs2212/images/Alumni Hall-0.png");
        Floor a1 = new Floor(1, alumni, "src/main/java/com/cs2212/images/Alumni Hall-1.png");
        Floor a2 = new Floor(2, alumni, "src/main/java/com/cs2212/images/Alumni Hall-2.png");
        alumni.addFloor(a0);
        alumni.addFloor(a1);
        alumni.addFloor(a2);
        // Create dropdown to switch floors
        JComboBox floors = new JComboBox(alumni.getFloorsArray());
        floors.setBounds(915,3,125,24);
        panelTop.add(floors);
        floors.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                try {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        changeFloor("Alumni Hall", (int) event.getItem());
                    }
                } catch (IOException e) {
                    e.printStackTrace(); 
                }
            }
        });
        
       // JPanel for the map
       //JTabbedPane panelMap = new JTabbedPane();
       panelMap.setBackground(Color.white);
       panelMap.setBounds(0,30,970,620);
       
       // JPanel behind the tabbed panels
       JPanel panelCenter = new JPanel();
       panelCenter.setBackground(Color.white);
       panelCenter.setBounds(0,30,970,620);
       panelCenter.add(panelMap);
       
       // Create different tabs
        /*panelMap.add("Alumni Hall", alumni0scrollPane);
        panelMap.add("Middlesex College", middle0scrollPane);
        panelMap.add("Health Sciences Building", health1scrollPane);
       */
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
       
       Weather newWeather = new Weather("London");
       JLabel weatherString = new JLabel(newWeather.getCity() + "" + newWeather.getCurrWeather() + "°C " + newWeather.getCurrCondition());
       panelWeather.add(weatherString);

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
                /*ImageIcon image = new ImageIcon("src/main/java/com/cs2212/images/pin.png");
                JLabel test = new JLabel(image);
                panelMap.add(test);
                BufferedImage testimage = ImageIO.read(new File(".\\src\\main\\java\\com\\cs2212\\images\\pin.png"));
                JLabel testicon = new JLabel(new ImageIcon(testimage));
                testicon.setBounds(130,50,5,5);*/
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
                    newPoiAdd(mainscreen,e.getX(),e.getY());
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
    private static void newPoiAdd(JFrame frame, long xCoord, long yCoord){
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
            
          //Create POI
          //POI temp = new POI(42069,42069,xCoord,yCoord,roomNum,name,description,false);
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
