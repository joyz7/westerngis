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

public class mainscreen {
    
    private JFrame mainscreen;
    private Main main;
    static String searchText;
    static String poiJSON;
    static JSONArray pois;
    private Campus campus;
    private Building currBuilding;
    private Floor currFloor;
    private HashMap<Integer, POI> poiMap;
    private int poiCount;
    private ArrayList<POI> drawnPois;
    boolean addPOI = false;
    Component activeScrollComponent;
    CheckboxTree POIList;
    final int mainscreenWidth = 1200; // width of the JFrame
    final int mainscreenHeight = 650; // height of the JFrame
    // REMINDER: ADD CONSTANTS FOR THE WIDTHS AND HEIGHTS OF EVERYTHING
    JPanel panelTop;

    final Color mediumGrey = new Color(202, 203, 204);
    final Color lightGrey = new Color(232, 232, 232);
    final Color darkGrey = new Color(88, 89, 89);

    JTabbedPane panelMap;
    JScrollPane alumniScrollPane;
    JScrollPane middlesexScrollPane;
    JScrollPane healthScrollPane;
    JComboBox floors;
    JPanel panelSideBar;
    JScrollPane resultScrollPane;
    /*
    public int searchPOI(JTextField searchField){
            searchText = searchField.getText();
    System.out.println("Search query: " + searchText);

    return 0;
    }

     */
    public Floor getCurrFloor() {
        return currFloor;
    }

    public void setCurrFloor(Floor floor) {
        currFloor = floor;
    }

    public void createMap(String building, int floor) throws IOException {
        try {
            //Prepared map images
            BufferedImage mapImage = ImageIO.read(new File("src/main/java/com/cs2212/images/" + building + "-" + floor + ".png"));
            JLabel image = new JLabel(new ImageIcon(mapImage));
            image.setBounds(0, 30, 970, 550);
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
    
    public void repaintUI(TreeModel newTree) {
        mainscreen.remove(panelSideBar);
        generateSideBar(newTree);
        floors.revalidate(); // Trigger a new layout pass
        floors.repaint(); // Repaint the combobox
    }

    public void changeFloor(Building building) {
        // Create dropdown to switch floors
        floors = new JComboBox(building.getFloorsArray());
        floors.setBounds(915, 3, 125, 24);
        panelTop.add(floors);
        floors.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                try {
                    if (event.getStateChange() == ItemEvent.SELECTED) {
                        activeScrollComponent = panelMap.getSelectedComponent(); //ensures the active panel for drawing
                        Integer floorNum = (int) event.getItem();
                        if (currBuilding.getName().equals("Health Sciences Building")) {
                            floorNum -= 1;
                         }
                        Floor newFloor = building.getArray().get(floorNum);
                        changeFloorImage(building.getName(), floorNum);
                        setCurrFloor(newFloor);
                        drawnPois.clear();
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

    public void changeFloorImage(String building, int floor) throws IOException {
        try {
            BufferedImage mapImage = ImageIO.read(new File("src/main/java/com/cs2212/images/" + building + "-" + floor + ".png"));
            JLabel image = new JLabel(new ImageIcon(mapImage));
            image.setBounds(0, 30, 970, 550);
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
    
    public void drawPOIs() {
        try {
            activeScrollComponent = panelMap.getSelectedComponent();
            JScrollPane activeScrollPane = (JScrollPane) activeScrollComponent;
            BufferedImage mapImage = ImageIO.read(new File(currFloor.getImage()));

            // Create a label to hold the Alumni Hall image and set its bounds
            JLabel mapLbl = new JLabel(new ImageIcon(mapImage));
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
//                    System.out.println("drawing: " + poi.getName()); CAN DELETE
                }
            }
// Get the current viewport
            JViewport viewport = activeScrollPane.getViewport();

// Keep the same view position
            Point viewPosition = viewport.getViewPosition();

// Update the viewport's view component with the new content
            viewport.setView(layeredPane);

// Set the view position to the same location as before
            viewport.setViewPosition(viewPosition);

            viewport.revalidate(); // Trigger a new layout pass
            viewport.repaint(); // Repaint the JLayeredPane               
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public mainscreen(Main main, Campus campus, HashMap<Integer, POI> poiMap) throws IOException {
        this.main = main;
        this.campus = campus;
        poiCount = poiMap.size();
        panelMap = new JTabbedPane();
        panelTop = new JPanel();
        this.poiMap = poiMap;
        
        for (Object poi : poiMap.values()) {
            POI currPOI = (POI) poi;
            currPOI.setMainframe(this);
        }
        
        main.setMainframe(this);

        //Parse POI json
        String filename = "src/main/java/com/cs2212/test.json";
        
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
                e.getWindow().dispose();
                main.logOut();
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

                // Remove the MouseListener from the previously selected component (if any)
                // This is necessary to avoid adding the same listener multiple times
                // and potentially causing memory leaks or unexpected behavior.
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
                if (drawnPois != null) {
                    drawnPois.clear();
                }
                TreeModel newTree = main.makeTree(newFloor);
                repaintUI(newTree); 
                drawPOIs();//drawing the poits
                
                System.out.println(currFloor.getBuilding().getName());
                System.out.println(currBuilding.getName());
                // Add a MouseListener to the selected component
//                selectedComponent.addMouseListener(mouseListener);

                // Add a MouseListener to the selected component
                activeScrollComponent.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Handle mouse click event here...
                        System.out.println(e.getX() + "," + e.getY());
                    }
                });

//                // TEST CASE: POI POP UP
//                User testUser = new User("bob", "bob");
//                POI testPOI = new POI(5, 4, 250, 250, "AH 24", "Alumni Hall Classroom", "Male washroom in the basement of MC, located by the southside stairwell. Non-accessible washroom.", true);
//                displayPOIInfo(testPOI, testUser, developerMap, favouritePoiObjects);
            }
        });
                   
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
        
        //JButton closeResults = new JButton("Close");
        //closeResults.setBounds(790, 3, 100, 25);
                
        //add search bar components into top panel
        panelTop.add(searchField);
        panelTop.add(searchButton);
        //panelTop.add(closeResults);

         // Create a panel for the search results dropdown
        
        //event listener that takes in text the user searches
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchText = searchField.getText();
                System.out.println("Search query: " + searchText);
                System.out.println("Building: " + currBuilding.getName());
                System.out.println("Floor list: " + currBuilding.getArray());
                
                //create String list to contain search results
                DefaultListModel<POI> searchResultsList = main.search(searchText, currBuilding);
                JList resultJList = new JList<>(searchResultsList);
                
                //add selection listener to the resultJList instance
                resultJList.addListSelectionListener(new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (!e.getValueIsAdjusting()) {
                            //get the selected item
                            POI selectedItem = (POI) resultJList.getSelectedValue();
                            String layerId = selectedItem.getLayerId();
                            char building = layerId.toLowerCase().charAt(0);
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
                                // DISPLAY THE POI BY CHECKING OFF CHECKBOX - SET POI TO ACTIVE?
                                selectedItem.isActive();

                                drawPOIs();//drawing the pois
                            } catch (IOException error) {
                                error.printStackTrace();
                            }            
                        }
                    }
                });
                
                resultScrollPane = new JScrollPane(resultJList);
                JPanel test = new JPanel();
                test.add(resultScrollPane);

                //add search results components into top panel
                JOptionPane searchResultsPanel = new JOptionPane(test);
                
                //JOptionPane searchResultsPanel = new JOptionPane(resultScrollPane);
                JOptionPane.showMessageDialog(null,test, "Search Results", JOptionPane.PLAIN_MESSAGE);
            }
        });


        //Event listener to close the search results
        /* DELETE LATER
        closeResults.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultScrollPane.setVisible(false);
                panelTop.setBounds(0, 0, 1200, 30);
            }
        });
        */
        
        //Help icon for users to click
        JButton helpIcon = new JButton("Help");
        helpIcon.setBounds(1050, 3, 125, 24);
        panelTop.add(helpIcon);

        //Event listener so that when the help icon is clicked the PDF is opened
        helpIcon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File pdfFile = new File("src/main/java/com/cs2212/resources/CS2212_Help_Document.pdf");
                    if (pdfFile.exists()) {
                        Desktop.getDesktop().open(pdfFile);
                    } else {
                        System.out.println("The PDF file does not exist.");
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
        //mainscreen.setResizable(false);
        mainscreen.setVisible(true);
    }
          
            // CHANGES START HERE ----------------------------------------------
    public void generateSideBar(TreeModel layers) {

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
        JLabel weatherString = new JLabel(newWeather.getCity() + ": " + newWeather.getCurrWeather() + "°C " + newWeather.getCurrCondition());
        panelWeather.add(weatherString);

        // JPanel for the POI Title and Button
        JPanel panelPOITitle = new JPanel();
        panelPOITitle.setLayout(null);
        panelPOITitle.setBackground(darkGrey);
        panelPOITitle.setBounds(0, 50, 230, 30);
        panelSideBar.add(panelPOITitle);

        POIList = new CheckboxTree();
        POIList.setShowsRootHandles(true);
        POIList.setRootVisible(false);
        POIList.setModel(layers);
        if (drawnPois != null) {
            POIList.setPOIDraw(drawnPois);
        }
        
        JScrollPane panelPOIScroll = new JScrollPane(POIList); // add tree to scroll pane
        panelPOIScroll.setBackground(Color.white);
        panelPOIScroll.setBounds(0, 80, 230, 500);
        panelPOIScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelPOIScroll.getVerticalScrollBar().setUnitIncrement(20);
        panelSideBar.add(panelPOIScroll); // add scroll pane to side bar

        JLabel POITitle = new JLabel("Points of Interest");
        POITitle.setBounds(5, 5, 200, 20);
        POITitle.setFont(new Font("Arial", Font.BOLD, 12));
        POITitle.setBackground(darkGrey);
        POITitle.setForeground(Color.white);
        panelPOITitle.add(POITitle);

            //Jacky added; listener for when user selects checkbox to draw the POI marker
        POIList.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent e) {
                     drawPOIs();
                 }
        });
        
        
        // Add a button to Add POIs
        JButton addPOIBtn = new JButton("Add POI");
        addPOIBtn.setBounds(120, 5, 90, 20);
        //addPOIBtn.setHorizontalAlignment(SwingConstants.LEFT);
        panelPOITitle.add(addPOIBtn);

                //button action listener to toggle on the poi adding mode
        addPOIBtn.addActionListener(e -> {
            addPOI = !addPOI; // Toggle the boolean variable
            if (addPOI == true) {
                addPOIBtn.setText("Click Map");
            } else {
                addPOIBtn.setText("Add POI");
            }
        });
        
        //intial mouse listener?
        activeScrollComponent.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Get the mouse click location
                if (addPOI == true) {
                    newPoiAdd(e.getX(), e.getY(), floors);
                    addPOIBtn.setText("Add POI");
                    addPOI = false; //Turn off the clicking
                }
            }
        });
        
        mainscreen.add(panelSideBar); // add side bar

    }
    
    //Method of adding POI
    //creating a popup menu of getting poi info, and updating the user of adding
    //the poi or not
    private void newPoiAdd(long xCoord, long yCoord, JComboBox floorCB) {
        
        boolean isDeveloper = main.isDeveloper();
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
        

        if (isDeveloper) {            
            panel.add(new JLabel("Layer"));
            String[] layerStrings = {"Washroom", "Classroom", "Gen Lab", "CS Specific", "Resturaunt", "Exit/Entry point", "Navigation"};
            JComboBox layerDropDown = new JComboBox(layerStrings);
            panel.add(layerDropDown);
            String layer = (String) layerDropDown.getSelectedItem(); 
            if (layer.equals("CS Specific")) {
                layerType = 's';
            } else {
                layerType = layer.toLowerCase().charAt(0);
            }
        }
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

            Integer selectedFloor = (Integer) floorCB.getSelectedItem();
            System.out.println("Selected floor: " + selectedFloor);

            if (result == JOptionPane.OK_OPTION && !pointNameField.getText().isEmpty() && !roomNumberField.getText().isEmpty() && !descriptionField.getText().isEmpty()) {
                System.out.println(xCoord + " " + yCoord);
                //Create POI !!!!!
                POI newPOI;
                if (!isDeveloper) {
                    newPOI = new POI(poiCount, currBuilding.getName().toLowerCase().charAt(0) + Integer.toString(selectedFloor) + "u", xCoord, yCoord, roomNum, name, description, false);
                } else {
                    newPOI = new POI(poiCount, currBuilding.getName().toLowerCase().charAt(0) + Integer.toString(selectedFloor) + layerType, xCoord, yCoord, roomNum, name, description, true);
                }
                main.addPOI(newPOI);
                drawnPois = POIList.getPOIDraw(); // Store POIs drawn on map
                TreeModel newTree = main.makeTree(currFloor);
                repaintUI(newTree);
                drawPOIs();
                JOptionPane.showMessageDialog(null, "Successfully added");
            } else {
                JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Unsuccessful No POI Added");
        }
    }

    public void displayPOI(int poiID) {
        
        String favOption = ""; // Text variable to change between favourite and unfavourite
        String[] buttons = {favOption, "Edit", "Delete"};
        boolean isDev = main.isDeveloper(); // Changes to true if user is a developer      
        
        //Get the POI object
        POI poiToDisplay = poiMap.get(poiID);
        
        // Create pop up panel
        JPanel POIPopUp = new JPanel(new GridLayout(6,0 ));
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

            //Event listener for edit
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
            //Event listener for delete
            devDelete.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Boolean success = main.deletePOI(poiToDisplay);
                    if (success) {
                        JOptionPane.showMessageDialog(null, "Successfully Deleted");
                        TreeModel newTree = main.makeTree(currFloor);
                        repaintUI(newTree);
                    } else {
                        JOptionPane.showMessageDialog(null, "Unsuccessful No POI Deleted");
                    }
                }
            });     
        } else {
            //Display checkbox for favourite
            JCheckBox isFavourite = new JCheckBox("Favourite");
            POIPopUp.add(isFavourite);

            //Event listener for the favourite option
            isFavourite.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                // FAVOURITES ???????????????????????????????????????????????????????????
                }     
            });
        }
        JOptionPane.showConfirmDialog(null, POIPopUp, "Information", JOptionPane.DEFAULT_OPTION); 

    }
}
