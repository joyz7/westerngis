# Western University Interior Spaces Explorer

To ease the process of locating points of interest within buildings, this project aims to help individuals navigate Western University building interiors more easily through a layered, user-friendly web application. The project leverages existing PDF maps of Western's building interiors and allows users to browse buildings, toggle between layers and utilize various points of interest to help users navigate campus. The primary function will be to identify accessible features and buildings on campus, while also enabling users to locate specific rooms. The application will be accompanied by an editing tool that can be accessed by developers, allowing the creation and editing of map metadata. 

## Required Libraries and Third Party Tools
- **javax.swing** - The Swing GUI toolkit was used to develop the graphical components of the iterface.
- **java.io** - The Java I/O package allows the application to read and write from data files.
- **JSON-simple** - This toolkit was used to encode and decode JSON data.
- **java.awt** - The Java Abstract Window Toolkit was used to develop the graphical components of the interface.

## How to Compile the Project From Source Code and Run the Project

1. Download JSON-simple Version 1.1: https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple.
2. Add jar to libraries by going to "Tools" in the top bar and then "Libraries".
2. Load the Maven Project into NetBeans.
3. In the top bar of Netbeans, click on "Run", right between "Refactor" and "Debug". 
4. Navigate into "Set Project Configuration" then "Customize".
5. Navigate into "Run" under Categories.
6. Set the Main Class as com.cs2212.Welcome and press "Ok".
7. Build the project by clicking on the hammer labeled "Build Project".
8. Press the forward green triangle button labelled "Run Project" to run the project.

## How to Use the Software

**General user:**
1. As a new user, you enter a username and password, then click the "Sign Up" button. This information will be saved in our program, so as an existing user, you can enter your information and click the "Log In" button.
2. The landing page is the map for Floor 0 of Alumni Hall. Click the tabs at the top to switch between buildings, and the Floor dropdown on the top bar to switch between floors.
3. The sidebar contains a list of layers, including favourites and user-created POIs, and POIs for the current floor. Click the circle beside the layer name to show the POIs under the layer. Select the POI checkbox to display a pin on the map for the location of the POI and deselect it to remove the pin. Select the layer checkbox to display all the POIs under the specific layer.
   a. The map will scroll to the POI location. Click on the pin icon for a pop-up of the POI metadata. There is a checkbox to favourite the POI, which will add the POI under the favourites layer.
4. Click the "Add POI" button, select a location on the map for the POI, and enter the Room Name, Number, and Description. The POI will be added under the User-Created POIs layer.
   a. Click on the pin for user-created POIs to edit or delete the POI, which updates the list of POIs on the sidebar.
5. Enter text in the search bar and click the "Search" button to get a list of search results across buildings. Clicking on the result will show the POI in the map of its floor and building.
6. The top right corner has a "Help" button containing information about the program, and the current weather information.
7. After clicking the X to close the program, a pop-up prompts the user if they are sure about exiting. When the program closes, all the POI metadata and user's log-in information, created POIs, and favourited POIs are saved to the JSON file. This file is loaded in when the program starts.

**Developer:**
1. As a developer, you can enter the username and password provided in the "Developer Login Information" and click the "Developer Log In" button. Only users with the developer login credentials can access this view.
2. The features are similar to the user's view, except the developer adds built-in POIs when "Add POI" is selected. The pop-up from clicking all POI pins contains the option to edit or delete the built-in POIs. This is reflected in the sidebar and will be updated in the user's list of POIs.

## Developer Login Information

Username: developer

Password: encrypted
