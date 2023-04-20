# Western University Interior Spaces Explorer

To ease the process of locating points of interest within buildings, this project aims to help individuals navigate Western University building interiors more easily through a layered, user-friendly web application. The project leverages existing PDF maps of Western's building interiors and allows users to browse buildings, toggle between layers and utilize various points of interest to help users navigate campus. The primary function will be to identify accessible features and buildings on campus, while also enabling users to locate specific rooms. The application will be accompanied by an editing tool that can be accessed by developers, allowing the creation and editing of map metadata. 

## Required Libraries and Third Party Tools
- **javax.swing** - The Swing GUI toolkit was used to develop the graphical components of the iterface.
- **java.io** - The Java I/O package allows the application to read and write from data files.
- **JSON-simple** - This toolkit was used to encode and decode JSON data.
- **java.awt** - The Java Abstract Window Toolkit was used to develop the graphical components of the interface.

## How to Compile and Run the Project in Netbeans

1. Download JSON-simple Version 1.1: https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple.
2. Add jar to libraries by going to "Tools" in the top bar, then "Libraries".
2. Load the Maven Project into NetBeans.
3. In the top bar of Netbeans, click on "Run". 
4. Navigate into "Set Project Configuration" then "Customize".
5. Navigate into "Run" under Categories.
6. Set the Main Class as com.cs2212.Welcome and click "Ok".
7. Build the project by clicking on the hammer labelled "Build Project".
8. Press the forward green triangle labelled "Run Project" to run the project.
