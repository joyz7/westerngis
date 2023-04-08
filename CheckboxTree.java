package com.cs2212;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.EventListenerList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * The CheckboxTree class creates the checkbox tree in the sidebar in main screen.
 */
public class CheckboxTree extends JTree {

    CheckboxTree selfPointer = this;
    ArrayList<POI> poisToDraw  = new ArrayList<POI>(); // List of POIs available for that layer

    /**
     * The CheckedNode class represents one node in the CheckboxTree
     */
    private class CheckedNode {
        boolean isSelected;
        boolean hasChildren;
        boolean allChildrenSelected;
        
        /**
        Constructs a new CheckedNode object with selection and child node properties.
        @param isSelected_ the selection state of the node
        @param hasChildren_ the existence of child nodes for the node
        @param allChildrenSelected_ the selection state of all child nodes
        */
        public CheckedNode(boolean isSelected_, boolean hasChildren_, boolean allChildrenSelected_) {
            isSelected = isSelected_;
            hasChildren = hasChildren_;
            allChildrenSelected = allChildrenSelected_;
        }
    }
    HashMap<TreePath, CheckedNode> nodesCheckingState;
    HashSet<TreePath> checkedPaths = new HashSet<TreePath>();

    // Define new event type for "checking" a node
    EventListenerList listenerList = new EventListenerList();
    
    /**
     * Check if there is a change of state with the checkbox node
    */
    private class CheckChangeEvent extends EventObject {     
        private CheckChangeEvent(Object source) {
            super(source);          
        }       
    }   
    /**
     * Event Listener to check if there is a change of state with the checkbox node
     */
    private interface CheckChangeEventListener extends EventListener {
        /**
         * Check if there was a change of state
         * @param event representing the action taken
         */
        public void checkStateChanged(CheckChangeEvent event);
    }

    /**
     * Notifies CheckChangeEventListener objects of a check change event.
     * @param event the CheckChangeEvent object to be fired
     */
    private void fireCheckChangeEvent(CheckChangeEvent event) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = 0; i < listeners.length; i++) {
            if (listeners[i] == CheckChangeEventListener.class) {
                ((CheckChangeEventListener) listeners[i + 1]).checkStateChanged(event);
            }
        }
    }


    /**
     * Set the model of the checkbox tree to be the new TreeModel
     * @param newModel the TreeModel to be set as the new model
     */
    @Override
    public void setModel(TreeModel newModel) {
        super.setModel(newModel);
        resetCheckingState();
    }

    /**
     * Get the list of nodes to be drawn on the mainscreen.
     * @return the ArrayList containing POI objects to draw.
     */
    public ArrayList<POI> getPOIDraw(){
        return poisToDraw;
    }
    
    /**
     * Set the list of POIs to draw to a new list.
     * @param pois the ArrayList of POIs to draw.
     */
    public void setPOIDraw(ArrayList<POI> pois) {
        poisToDraw = pois;
    }
    
    /**
     * Clear the list of POIs to draw.
     */
    public void clearPOI(){
        poisToDraw.clear();
    }
    
    /**
     * Mark all nodes as unchecked.
     */
    private void resetCheckingState() { 
        nodesCheckingState = new HashMap<TreePath, CheckedNode>();
        checkedPaths = new HashSet<TreePath>();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)getModel().getRoot();
        if (node == null) {
            return;
        }
        addSubtreeToCheckingStateTracking(node);
    }

    /**
     * Creates a subtree with the specified node as the root to track checking states
     * @param node the root node
     */
    private void addSubtreeToCheckingStateTracking(DefaultMutableTreeNode node) {
        TreeNode[] path = node.getPath();   
        TreePath tp = new TreePath(path);
        CheckedNode cn = new CheckedNode(false, node.getChildCount() > 0, false);
        nodesCheckingState.put(tp, cn);
        for (int i = 0 ; i < node.getChildCount() ; i++) {              
            addSubtreeToCheckingStateTracking((DefaultMutableTreeNode) tp.pathByAddingChild(node.getChildAt(i)).getLastPathComponent());
        }
    }

    /**
     * Turns nodes into checkboxes.
     */
    private class CheckBoxCellRenderer extends JPanel implements TreeCellRenderer {        
        JCheckBox checkBox;     
        /**
         * Constructor for the checkbox cell.
         */
        public CheckBoxCellRenderer() {
            super();
            this.setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            add(checkBox, BorderLayout.CENTER);
            setOpaque(false);
        }
        /**
         * Returns the object that was used to create the TreeCellRenderer.
         * @param tree the tree of all nodes.
         * @param value the Object stored in the node.
         * @param selected true/false representing if the node was selected.
         * @param expanded true/false depending whether its children have been selected.
         * @param leaf true/false depending if the node is a leaf.
         * @param row index of the row being rendered
         * @param hasFocus if the cell has focus
         * @return component used as a tree cell renderer for that node
         */
        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
            Object obj = node.getUserObject(); 
            TreePath tp = new TreePath(node.getPath());
            CheckedNode cn = nodesCheckingState.get(tp);
            if (cn == null) {
                return this;
            }    
            try {            
                // If obj can be successfully casted as a POI object
                if ((POI)obj instanceof POI) {
                    POI POIobj = (POI)obj;
                    checkBox.setSelected(cn.isSelected);
                    checkBox.setText(POIobj.checkBoxText());
                    checkBox.setOpaque(cn.isSelected && cn.hasChildren && ! cn.allChildrenSelected);
                }
            } catch (Exception e) {
                // If obj cannot be successfully casted as a POI object, it is a String object
                checkBox.setSelected(cn.isSelected);
                checkBox.setText(obj.toString());
                checkBox.setOpaque(cn.isSelected && cn.hasChildren && ! cn.allChildrenSelected);              
            }
        return this;
        }       
    }

    /**
     * CheckboxTree class creates the checkbox tree in the sidebar.
     */
    public CheckboxTree() {
        super();
        // Disabling toggling by double-click
        this.setToggleClickCount(0);
        // Override cell renderer
        CheckBoxCellRenderer cellRenderer = new CheckBoxCellRenderer();
        this.setCellRenderer(cellRenderer);

        DefaultTreeSelectionModel dtsm = new DefaultTreeSelectionModel() {      
            // Totally disabling the selection mechanism
            public void setSelectionPath(TreePath path) {
            }           
            public void addSelectionPath(TreePath path) {                       
            }           
            public void removeSelectionPath(TreePath path) {
            }
            public void setSelectionPaths(TreePath[] pPaths) {
            }
        };
        // Calling checking mechanism on mouse click
        this.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent arg0) {
                TreePath tp = selfPointer.getPathForLocation(arg0.getX(), arg0.getY());
                if (tp == null) {
                    return;
                } else {
                    DefaultMutableTreeNode poiNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
                        if (poiNode.getUserObject() instanceof POI) {
                            POI poi = (POI) poiNode.getUserObject();
                            poi.setActive();
                            // Checks if the poi is active and should be drawn or not
                            if (poi.isActive() == true){
                            poisToDraw.add(poi);
                            } else {
                            poisToDraw.remove(poi);   
                            }
                            
                        } else {
                            // Goes through the child of the layer/poi nodes and marks them for active and drawing
                            for (int i=0; i<poiNode.getChildCount(); i++) {
                                DefaultMutableTreeNode poi = (DefaultMutableTreeNode) poiNode.getChildAt(i);
                                POI p = (POI) poi.getUserObject();
                                p.setActive();
                                if (p.isActive() == true){
                                 poisToDraw.add(p);
                                }
                                else{
                                 poisToDraw.remove(p);
                                }
                            }
                        }
                }
                
                boolean checkMode = ! nodesCheckingState.get(tp).isSelected;
                checkSubTree(tp, checkMode);
                updatePredecessorsWithCheckMode(tp, checkMode);
                // Firing the check change event
                fireCheckChangeEvent(new CheckChangeEvent(new Object()));
                // Repainting tree after it was updated
                selfPointer.repaint();                          
            }
            @Override
            public void mouseEntered(MouseEvent arg0) {         
            }    
            @Override
            public void mouseExited(MouseEvent arg0) {              
            }
            @Override
            public void mousePressed(MouseEvent arg0) {             
            }
            @Override
            public void mouseReleased(MouseEvent arg0) {
            }           
        });
        this.setSelectionModel(dtsm);
    }
    
    // When a node is checked/unchecked, updating the states of the predecessors
    /**
     * Updates the parent nodes of children nodes according to their changed states.
     * @param tp TreePath representing checked nodes
     * @param check checked state of child nodes
     */
    private void updatePredecessorsWithCheckMode(TreePath tp, boolean check) {
        TreePath parentPath = tp.getParentPath();
        // If it is the root, stop the recursive calls and return
        if (parentPath == null) {
            return;
        }       
        CheckedNode parentCheckedNode = nodesCheckingState.get(parentPath);
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) parentPath.getLastPathComponent();     
        parentCheckedNode.allChildrenSelected = false;
        parentCheckedNode.isSelected = false;
        int selectedChildren = 0;
        // for each parent node, loop through each child
        for (int i = 0 ; i < parentNode.getChildCount() ; i++) {                
            TreePath childPath = parentPath.pathByAddingChild(parentNode.getChildAt(i));
            CheckedNode childCheckedNode = nodesCheckingState.get(childPath);           
            
            // If child is not selected, then the parent cannot be selected
            if (childCheckedNode.isSelected == false) {
                parentCheckedNode.isSelected = false;      
            } else {
                selectedChildren++;
            }          
        }            
        if (parentNode.getChildCount() == selectedChildren) {
            parentCheckedNode.isSelected = true;
        }
        if (parentCheckedNode.isSelected) {
            checkedPaths.add(parentPath);
        } else {
            checkedPaths.remove(parentPath);
        }
        // Go to upper predecessor
        updatePredecessorsWithCheckMode(parentPath, check);
    }
    
    /**
     * Checks/unchecks the subtree rooted at a given TreePath.
     * @param tp the TreePath of the root node of the subtree to be checked or unchecked
     * @param check the checking state applied to subtree
     */
    private void checkSubTree(TreePath tp, boolean check) {
        CheckedNode cn = nodesCheckingState.get(tp);
        cn.isSelected = check;
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tp.getLastPathComponent();
        for (int i = 0 ; i < node.getChildCount() ; i++) {              
            checkSubTree(tp.pathByAddingChild(node.getChildAt(i)), check);
        }
        cn.allChildrenSelected = check;
        if (check) {
            checkedPaths.add(tp);
        } else {
            checkedPaths.remove(tp);
        }
    }
}