package com.cs2212;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreeModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * The mainscreen class represents the home screen after the user logs in.
 */
public class mainscreen {
    
    // Declare Instance Variables
    private JFrame mainscreen;
    private Main main;
    static String searchText;
    static JSONArray pois;
    private Campus campus;
    private Building currBuilding;
    private Floor currFloor;
    private HashMap<Integer, POI> poiMap;
    boolean addPOI = false;
    Component activeScrollComponent;
    CheckboxTree POIList;
    final int mainscreenWidth = 1200; // width of the JFrame
    final int mainscreenHeight = 650; // height of the JFrame
    JPanel panelTop;
    JButton addPOIBtn = new JButton("Add POI");
    final Color mediumGrey = new Color(202, 203, 204);
    final Color lightGrey = new Color(232, 232, 232);
    final Color darkGrey = new Color(88, 89, 89);
    JLabel mapLbl;
    JTabbedPane panelMap;
    JScrollPane alumniScrollPane;
    JScrollPane middlesexScrollPane;
    JScrollPane healthScrollPane;
    JComboBox floors;
    JPanel panelSideBar;
    JScrollPane resultScrollPane;
    
    /**
     * Returns the current floor.
     * @return the Floor object of the current floor.
     */
    public Floor getCurrFloor() {
        return currFloor;
    }

    /**
     * Set the current floor.
     * @param floor the current floor to be set.
     */
    public void setCurrFloor(Floor floor) {
        currFloor = floor;
    }

    /**
     * Create the UI for displaying the maps.
     * @param building the name of the building.
     * @param floor the integer representing a floor.
     * @throws IOException 
     */
    public void createMap(String building, int floor) throws IOException {
        try {
            //Prepare map images
            BufferedImage mapImage = ImageIO.read(new File("src/main/java/com/cs2212/images/" + building + "-" + floor + ".png"));
            JLabel image = new JLabel(new ImageIcon(mapImage));
            image.setBounds(0, 30, 970, 550); // Set size of the image
            // Set up maps for each of the buildings
            if (building.equals("Alumni Hall")) { // Alumni Hall
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
            } else if (building.equals("Middlesex College")) { // Middlesex College
                middlesexScrollPane = new JScrollPane(image);
                middlesexScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                middlesexScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                middlesexScrollPane.setPreferredSize(new Dimension(970, 550));
                middlesexScrollPane.getVerticalScrollBar().setUnitIncrement(20);
                middlesexScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
                panelMap.add(building, middlesexScrollPane);
            } else if (building.equals("Health Sciences Building")) { // Health Sciences Building
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
    
    /**
     * Rebuild the side bar with new information.
     * @param newTree TreeModel containing the POIs and layers for the specific map.
     */
    private void repaintUI(TreeModel newTree) {
        mainscreen.remove(panelSideBar);
        generateSideBar(newTree);
        floors.revalidate(); // Trigger a new layout pass
        floors.repaint(); // Repaint the combobox
    }
    
    /**
     * Change floor according to user selection.
     * @param building Building object to obtain number of floors
     */
    private void changeFloor(Building building) {
        // Create dropdown to switch floors
        floors = new JComboBox(building.getFloorsArray());
        JLabel floorsLabel = new JLabel("Floor:");
        floorsLabel.setBounds(870, 3, 50, 24);
        floors.setBounds(915, 3, 125, 24);
        panelTop.add(floorsLabel);
        panelTop.add(floors); // add combo box to the panel
        // Create a listener to grab user selection
        floors.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                try {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        activeScrollComponent = panelMap.getSelectedComponent(); //ensures the active panel for drawing
                        Integer floorNum = (int) event.getItem();
                        // Consider that HSB does not have a basement
                        if (currBuilding.getName().equals("Health Sciences Building")) {
                            floorNum -= 1;
                        }
                        Floor newFloor = building.getArray().get(floorNum);
                        // Change the floor image and set new current floor to reflect user selection
                        changeFloorImage(building.getName(), floorNum);
                        setCurrFloor(newFloor);
                        TreeModel newTree = main.makeTree(newFloor);
                        repaintUI(newTree);
                        drawPOIs();//drawing the poits
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Change the map displayed according to floor.
     * @param building name of the building to switch to
     * @param floor integer representing the floor to switch to
     * @throws IOException 
     */
    private void changeFloorImage(String building, int floor) throws IOException {
        try {
            BufferedImage mapImage = ImageIO.read(new File("src/main/java/com/cs2212/images/" + building + "-" + floor + ".png"));
            mapLbl = new JLabel(new ImageIcon(mapImage));
            mapLbl.setBounds(0, 30, 970, 550);
            //Create a scroll pane to hold the image
            if (building.equals("Alumni Hall")) {
                alumniScrollPane.setViewportView(mapLbl);
            } else if (building.equals("Middlesex College")) {
                middlesexScrollPane.setViewportView(mapLbl);
            } else if (building.equals("Health Sciences Building")) {
                healthScrollPane.setViewportView(mapLbl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the POIs on the map according to list of POIs to draw.
     */
    private void drawPOIs() {
        try {
            activeScrollComponent = panelMap.getSelectedComponent();
            JScrollPane activeScrollPane = (JScrollPane) activeScrollComponent;
            BufferedImage mapImage = ImageIO.read(new File(currFloor.getImage()));

            // Create a label to hold the Alumni Hall image and set its bounds
            mapLbl = new JLabel(new ImageIcon(mapImage));
            mapLbl.setBounds(0, 0, mapImage.getWidth(), mapImage.getHeight());
            // Create a layered pane to layer the components
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(mapImage.getWidth(), mapImage.getHeight()));
            layeredPane.add(mapLbl, JLayeredPane.DEFAULT_LAYER); // Add the Alumni Hall image label to the bottom layer
            ArrayList<POI> poisToDraw = POIList.getPOIDraw();
            //For every poi that needs be draw; draw it. if empty/no pois ignore
            if (poisToDraw != null) {
                for (POI poi : poisToDraw) {
                    layeredPane.add(poi.getLbl(), JLayeredPane.PALETTE_LAYER);
                }
            }
            if (poisToDraw.size()> 0) {
                POI lastPOI = poisToDraw.get(poisToDraw.size()-1);
                JViewport viewport = activeScrollPane.getViewport();
                viewport.setView(layeredPane);
                viewport.setViewPosition(new Point((int)lastPOI.getXCoord()-50, (int)lastPOI.getYCoord()-50));
                activeScrollPane.setViewport(viewport);
                activeScrollPane.revalidate(); // Trigger a new layout pass
                activeScrollPane.repaint(); // Repaint the JLayeredPane  
            } else {
                // Get the current viewport
                JViewport viewport = activeScrollPane.getViewport();
               // Keep the same view position
                Point viewPosition = viewport.getViewPosition();
                // Update the viewport's view component with the new content
                viewport.setView(layeredPane);
                // Set the view position to the same location as before
                viewport.setViewPosition(viewPosition);
                activeScrollPane.setViewport(viewport);
                activeScrollPane.revalidate(); // Trigger a new layout pass
                activeScrollPane.repaint(); // Repaint the JLayeredPane    
            }

        } catch (IOException e) {
        }
    }

    /**
     * 
     * @param main
     * @param campus
     * @param poiMap
     * @throws IOException 
     */
    public mainscreen(Main main, Campus campus, HashMap<Integer, POI> poiMap) throws IOException {
        this.main = main;
        this.campus = campus;
        panelMap = new JTabbedPane();
        panelTop = new JPanel();
        this.poiMap = poiMap;

        for (Object poi : poiMap.values()) {
            POI currPOI = (POI) poi;
            currPOI.setMainframe(this);
        }

        main.setMainframe(this);

        //Parse POI json
        String filename = "src/main/java/com/cs2212/poi.json";

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
        mainscreen = new JFrame("Map of Western University");
        mainscreen.setLayout(null); // ENABLES PANELS TO BE ADDED WITHIN ONE ANOTHER
        mainscreen.setSize(mainscreenWidth, mainscreenHeight); // make JFrame full screen
        mainscreen.setLocationRelativeTo(null); // center JFrame in the middle of the screen
        mainscreen.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainscreen.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //popup that warns the user that unsaved progress will be deleted
                int result = JOptionPane.showConfirmDialog(mainscreen, "Are you sure you want to exit? Any unsaved work will be lost!", "Exit Program", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                  e.getWindow().dispose();
                  main.logOut();
                } 
            }
        });

        // Create building maps
        createMap("Alumni Hall", 0);
        createMap("Middlesex College", 0);
        createMap("Health Sciences Building", 1);
        // Sets current building as Alumni Hall
        currBuilding = (Building) campus.getBuildings().get(0);
        changeFloor(currBuilding);
        setCurrFloor(currBuilding.getArray().get(0));

        final MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        };

        // Get the currently selected component
        activeScrollComponent = panelMap.getSelectedComponent();
        panelMap.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                JTabbedPane tabbedPane = (JTabbedPane) e.getSource();
                activeScrollComponent = panelMap.getSelectedComponent();

                // Set building from selected pane
                currBuilding = (Building) campus.getBuildings().get(tabbedPane.getSelectedIndex());
                activeScrollComponent = tabbedPane.getSelectedComponent();

                // Get the currently selected component
                activeScrollComponent = tabbedPane.getSelectedComponent();

                // Remove the MouseListener from the previously selected component
                Component[] components = tabbedPane.getComponents();
                for (Component component : components) {
                    if (component != activeScrollComponent && component instanceof JComponent) {
                        ((JComponent) component).removeMouseListener(mouseListener);
                    }
                }
                panelTop.remove(floors);
                changeFloor(currBuilding);
                Floor newFloor = currBuilding.getArray().get(0);
                setCurrFloor(newFloor);
                TreeModel newTree = main.makeTree(newFloor);
                repaintUI(newTree);
                drawPOIs(); //drawing the poits
            }
        });

        activeScrollComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the mouse click location
                drawPOIs();
                if (addPOI == true) {
                    mapLbl.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent ex) {
                            // Get the mouse click location
                            if (addPOI == true) {
                                newPoiAdd(ex.getX()-13, ex.getY()-40, floors);
                                addPOIBtn.setText("Add POI");
                                addPOI = false; //Turn off the clicking
                            }
                        }
                    });
                }
            }
        });
        if (addPOI == true) {
            mapLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent ex) {
                    // Get the mouse click location
                    if (addPOI == true) {
                        newPoiAdd(ex.getX()-13, ex.getY()-40, floors);
                        addPOIBtn.setText("Add POI");
                        addPOI = false; //Turn off the clicking
                    }
                }
            });
        }
        // JPanel for the top bar, that includes Search
        panelTop.setLayout(null);
        panelTop.setBackground(mediumGrey);
        panelTop.setBounds(0, 0, 1200, 30);

        // JPanel for the map
        panelMap.setBackground(Color.white);
        panelMap.setBounds(0, 30, 970, 620);

        // JPanel behind the tabbed panels
        JPanel panelCenter = new JPanel(); // what was changed
        panelCenter.setBackground(Color.white);
        panelCenter.setBounds(0, 30, 970, 620);
        panelCenter.add(panelMap); // what was changed
        mainscreen.add(panelCenter); // add map

        // Create search bar
        JTextField searchField = new JTextField(20);
        searchField.setBounds(10, 3, 650, 24);
        // Create search button
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(685, 3, 100, 25);

        //add search bar components into top panel
        panelTop.add(searchField);
        panelTop.add(searchButton);
        //panelTop.add(closeResults);

        //event listener that takes in text the user searches
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchText = searchField.getText();

                //create String list to contain search results
                DefaultListModel<POI> searchResultsList = main.search(searchText);
                JList resultJList = new JList<>(searchResultsList);
                
                if (searchResultsList.isEmpty()) {
                    JLabel emptyResult = new JLabel("No results were found. Try again!");
                    emptyResult.setHorizontalAlignment(JLabel.CENTER);
                    JOptionPane.showMessageDialog(null, emptyResult, "Search Results", JOptionPane.PLAIN_MESSAGE);
                } else {
                    //add selection listener to the resultJList instance
                    resultJList.addListSelectionListener(new ListSelectionListener() {
                        public void valueChanged(ListSelectionEvent e) {
                            if (!e.getValueIsAdjusting()) {
                                //get the selected item
                                POI selectedItem = (POI) resultJList.getSelectedValue();
                                String layerId = selectedItem.getLayerId();
                                char building = layerId.toLowerCase().charAt(0);
                                // Determine which building the selected item is from
                                if (building == 'a') {
                                    currBuilding = (Building) campus.getBuildings().get(0);
                                } else if (building == 'm') {
                                    currBuilding = (Building) campus.getBuildings().get(1);
                                } else if (building == 'h') {
                                    currBuilding = (Building) campus.getBuildings().get(2);
                                }

                                try {
                                    panelTop.remove(floors);
                                    floors = new JComboBox(currBuilding.getFloorsArray());
                                    floors.setBounds(915, 3, 125, 24);
                                    panelTop.add(floors);
                                    Floor newFloor = currBuilding.getArray().get(Character.getNumericValue(layerId.charAt(1)));
                                    changeFloorImage(currBuilding.getName(), newFloor.getNumber());
                                    setCurrFloor(newFloor);
                                    TreeModel newTree = main.makeTree(newFloor);
                                    repaintUI(newTree); 
                                    ArrayList<POI> draw = new ArrayList<>();
                                    draw.add(selectedItem);
                                    POIList.setPOIDraw(draw);
                                    // Set POI to active
                                    selectedItem.isActive();
                                    drawPOIs(); //drawing the pois
                                } catch (IOException error) {
                                    error.printStackTrace();
                                }            
                            }
                        }
                    });
                    // Create scroll panes for results
                    resultScrollPane = new JScrollPane(resultJList);
                    resultScrollPane.setPreferredSize(new Dimension(350,200));
                    JPanel resultsPane = new JPanel();
                    resultsPane.add(resultScrollPane);

                    //add search results components into top panel
                    JOptionPane searchResultsPanel = new JOptionPane(resultsPane);
                    
                    JOptionPane.showMessageDialog(null, resultsPane, "Search Results", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });

        // Help icon for users to click
        JButton helpIcon = new JButton("Help");
        helpIcon.setBounds(1050, 3, 125, 24);
        panelTop.add(helpIcon);

        // Event listener so that when the help icon is clicked the PDF is opened
        helpIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File pdfFile = new File("src/main/java/com/cs2212/resources/CS2212_Help_Document.pdf");
                    if (pdfFile.exists()) {
                        Desktop.getDesktop().open(pdfFile);
                    } 
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // add all JPanels to the JFrame
        TreeModel defaultTree = main.makeTree(currFloor);
        generateSideBar(defaultTree);
        mainscreen.add(panelTop); // add top bar
        mainscreen.setResizable(false);
        mainscreen.setVisible(true);
    }
    
    /**
     * Generate sidebar with POIs according to the map
     * @param layers TreeModel with the different POI categories and the POIs themselves
     */
    private void generateSideBar(TreeModel layers) {

        // JPanel for the side bar
        panelSideBar = new JPanel();
        panelSideBar.setLayout(null);
        panelSideBar.setBackground(Color.white);
        panelSideBar.setBounds(970, 30, 230, 580);

        // JPanel for the weather in the side bar
        JPanel panelWeather = new JPanel();
        panelWeather.setBackground(lightGrey);
        panelWeather.setBounds(0, 0, 230, 50);
        panelSideBar.add(panelWeather);

        Weather newWeather = new Weather("London");

        if (newWeather.getCurrCondition().equals("No internet.")){
            JLabel weatherString = new JLabel ("No Internet Connection.");
            panelWeather.add(weatherString);
        } else {
            JLabel weatherString = new JLabel(newWeather.getCity() + ": " + newWeather.getCurrWeather() + "°C " + newWeather.getCurrCondition());
            panelWeather.add(weatherString);
        }

        // JPanel for the POI Title and Button
        JPanel panelPOITitle = new JPanel();
        panelPOITitle.setLayout(null);
        panelPOITitle.setBackground(darkGrey);
        panelPOITitle.setBounds(0, 50, 230, 30);
        panelSideBar.add(panelPOITitle);

        // Turn TreeModel into checkbox list of POIs
        POIList = new CheckboxTree();
        POIList.setShowsRootHandles(true);
        POIList.setRootVisible(false);
        POIList.setModel(layers);
        
        JScrollPane panelPOIScroll = new JScrollPane(POIList); // add tree to scroll pane
        panelPOIScroll.setBackground(Color.white);
        panelPOIScroll.setBounds(0, 80, 230, 500);
        panelPOIScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelPOIScroll.getVerticalScrollBar().setUnitIncrement(20);
        panelSideBar.add(panelPOIScroll); // add scroll pane to side bar

        // Label for sidebar
        JLabel POITitle = new JLabel("Points of Interest");
        POITitle.setBounds(5, 5, 200, 20);
        POITitle.setFont(new Font("Arial", Font.BOLD, 12));
        POITitle.setBackground(darkGrey);
        POITitle.setForeground(Color.white);
        panelPOITitle.add(POITitle);

        // Listener for when user selects checkbox to draw the POI marker
        POIList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawPOIs();
            }
        });

        // Add a button to Add POIs
        addPOIBtn = new JButton("Add POI");
        addPOIBtn.setBounds(120, 5, 90, 20);
        panelPOITitle.add(addPOIBtn);

        //Listener for when user selects checkbox to draw the POI marker
        POIList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                drawPOIs();
            }
        });

        // Button action listener to toggle on the poi adding mode
        addPOIBtn.addActionListener(e -> {
            addPOI = !addPOI; // Toggle the boolean variable
            drawPOIs();
            if (addPOI == true) {
                addPOIBtn.setText("Click Map");
            } else {
                addPOIBtn.setText("Add POI");
            }
            
            if (mapLbl != null) {
            mapLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the mouse click location
                    if (addPOI == true) {
                        newPoiAdd(e.getX()-13, e.getY()-40, floors);
                        addPOIBtn.setText("Add POI");
                        addPOI = false; //Turn off the clicking
                    }
                }
            });
        }
        });

        if (mapLbl != null) {
            mapLbl.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Get the mouse click location
                    if (addPOI == true) {
                        newPoiAdd(e.getX()-13, e.getY()-40, floors);
                        addPOIBtn.setText("Add POI");
                        addPOI = false; //Turn off the clicking
                    }
                }
            });
        }
        mainscreen.add(panelSideBar); // add side bar
    }
    
    /**
     * Create and display pop-up menu that shows when user clicks on POI.
     * @param xCoord x coordinate of where the user clicked on.
     * @param yCoord y coordinate of where the user clicked on.
     * @param floorCB JComboBox containing the floors of the building.
     */
    private void newPoiAdd(long xCoord, long yCoord, JComboBox floorCB) {
        boolean isDeveloper = main.isDeveloper();
        ArrayList<String> layersList = new ArrayList<String>();
        layersList.add("Classrooms");
        layersList.add("Washrooms");
        layersList.add("Entry/Exit");
        layersList.add("Navigation");
        layersList.add("Resturaunts");

        if (currBuilding.getName().equals("Health Sciences Building")) {
            layersList.add("Gen Labs");
        } else if (currBuilding.getName().equals("Middlesex College")) {
            layersList.add("CS Specfic");
        }
        
        String[] layersArr = new String[layersList.size()];
        layersArr = layersList.toArray(layersArr);
 
        JComboBox layers = new JComboBox(layersArr);
        JTextField layerField = new JTextField();     

        char layerType = 'a';
        
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
         
        //if dev, then add this component to panel
        if (isDeveloper) {     
            panel.add(new JLabel("Layer:"));
            panel.add(layers);   
        }
        
        // Show the input dialog with the panel as the message
        int result = JOptionPane.showConfirmDialog(null, panel, "Enter point information", JOptionPane.OK_CANCEL_OPTION);

        // Check if the user clicked OK and get the input values
        if (result == JOptionPane.OK_OPTION) {
            String name = pointNameField.getText();
            String roomNum = roomNumberField.getText();
            String description = descriptionField.getText();
            // If the user is a developer, display layer
            if (isDeveloper) {
               
                String layer = layers.getSelectedItem().toString();

                if (layer.equals("Washrooms")) {
                    layerType = 'w';
                } else if (layer.equals("Classrooms")) {
                    layerType = 'c';
                } else if (layer.equals("Gen Labs")) {
                    layerType = 'g';
                } else if (layer.equals("CS Specific")) {
                    layerType = 's';
                } else if (layer.equals("Resturaunts")) {
                    layerType = 'r';
                } else if (layer.equals("Entry/Exit")) {
                    layerType = 'e';
                } else if (layer.equals("Navigation")) {
                    layerType = 'n';
                }          
            }

            Integer selectedFloor = (Integer) floorCB.getSelectedItem();

            // Make all fields mandatory
            if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                // Create POI
                if (isDeveloper) {
                    main.addPOI(currBuilding.getName().toLowerCase().charAt(0) + Integer.toString(selectedFloor) + layerType, xCoord, yCoord, roomNum, name, description);
                } else {
                    main.addPOI(currBuilding.getName().toLowerCase().charAt(0) + Integer.toString(selectedFloor) + "u", xCoord, yCoord, roomNum, name, description);
                }
                TreeModel newTree = main.makeTree(currFloor);
                repaintUI(newTree);
                drawPOIs();
                // Display message
                JOptionPane.showMessageDialog(null, "Successfully added");
            } else {
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
        }
    }

    /**
     * Display the POI on the map
     * @param poiID ID of the POI to be displayed
     */
    public void displayPOI(int poiID) {

        String favOption = ""; // Text variable to change between favourite and unfavourite
        String[] buttons = {favOption, "Edit", "Delete"};
        boolean isDev = main.isDeveloper(); // Changes to true if user is a developer      

        //Get the POI object
        POI poiToDisplay = (POI) poiMap.get(poiID);

        // Create pop up panel
        JPanel POIPopUp = new JPanel(new GridLayout(6, 0));
        // Display Name
        POIPopUp.add(new JLabel("Name:"));
        JLabel POIName = new JLabel(poiToDisplay.getName());
        POIPopUp.add(POIName);
        // Display Room Number
        POIPopUp.add(new JLabel("Room Number:"));
        JLabel POIRoom = new JLabel(poiToDisplay.getRoomNum());
        POIPopUp.add(POIRoom);
        // Display Description
        POIPopUp.add(new JLabel("Description:"));
        JLabel POIDescription = new JLabel(poiToDisplay.getDescription());
        POIPopUp.add(POIDescription);

        if (isDev) {
            //Add two additional buttons
            JButton devEdit = new JButton("Edit");
            POIPopUp.add(devEdit);

            JButton devDelete = new JButton("Delete");
            POIPopUp.add(devDelete);

            // Event listener for edit
            devEdit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

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
                        JOptionPane.showMessageDialog(null, "Successfully Edited");

                        String name = pointNameField.getText();
                        String roomNum = roomNumberField.getText();
                        String description = descriptionField.getText();
                        
                        // Ensure that all fields are filled
                        if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                            main.editPOIInfo(poiToDisplay, name, roomNum, description);
                            mainscreen.remove(POIPopUp);
                            mainscreen.invalidate();
                            mainscreen.validate();
                            mainscreen.repaint();
                            displayPOI(poiID);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Unsuccessful: No POI Edited");
                    }
                }
            });
            // Event listener for deleting a POI
            devDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Boolean success = main.deletePOI(poiToDisplay);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Successfully Deleted");
                        TreeModel newTree = main.makeTree(currFloor);
                        repaintUI(newTree);
                    } else {
                        JOptionPane.showMessageDialog(null, "Unsuccessful: No POI Deleted");
                    }
                }
            });
        } else {
            
            HashSet<POI> createdPOIs = main.getCreated();
            
            if (createdPOIs.contains(poiMap.get(poiID))) {
            
                //Add two additional buttons
                JButton userEdit = new JButton("Edit");
                POIPopUp.add(userEdit);

                JButton userDelete = new JButton("Delete");
                POIPopUp.add(userDelete);

                //Event listener for edit
                userEdit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

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
                            JOptionPane.showMessageDialog(null, "Successfully Edited");

                            String name = pointNameField.getText();
                            String roomNum = roomNumberField.getText();
                            String description = descriptionField.getText();
                            // Ensure all fields are filled
                            if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                                main.editPOIInfo(poiToDisplay, name, roomNum, description);
                                mainscreen.remove(POIPopUp);
                                mainscreen.invalidate();
                                mainscreen.validate();
                                mainscreen.repaint();
                                displayPOI(poiID);
                            }
                        } else{      
                            JOptionPane.showMessageDialog(null, "Unsuccessful: No POI Edited");
                        }          
                    }
                });
                // Event listener for delete
                userDelete.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Boolean success = main.deletePOI(poiToDisplay);
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Successfully Deleted");
                            TreeModel newTree = main.makeTree(currFloor);
                            repaintUI(newTree);
                            drawPOIs();
                        } else {
                            JOptionPane.showMessageDialog(null, "Unsuccessful: No POI Deleted");
                        }
                    }
                });     

            }
            // Display checkbox for favourite
            JCheckBox isFavourite = new JCheckBox("Favourite");
            POIPopUp.add(isFavourite);
            if (poiMap.get(poiID).getFavourite()) {
                isFavourite.setSelected (true);
            }

            // Event listener for the favourite option
            isFavourite.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    if (poiMap.get(poiID).getFavourite()) {
                        main.removeFavourite(poiID);
                        JOptionPane.showMessageDialog(null, "Successfully removed from favourites.");
                    } else {
                        main.addFavourite(poiID);
                        JOptionPane.showMessageDialog(null, "Successfully added to favourites.");
                    }
                    TreeModel newTree = main.makeTree(currFloor);
                    repaintUI(newTree);
                    drawPOIs();
                }     
            });
        }
        JOptionPane.showConfirmDialog(null, POIPopUp, "Information", JOptionPane.DEFAULT_OPTION);
    }
}