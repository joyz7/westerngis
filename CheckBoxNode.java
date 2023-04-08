
package com.cs2212;

/**
 * A node that represents a node in a tree view that has a checkbox associated with it for the sidebar.
 * @author sophi
 */
public class CheckBoxNode {
   private String text;
   private boolean selected;
   
   /**
    * Constructs a new checkbox node with the given text and selection status.
    * @param text     the text to be displayed for this node
    * @param selected the initial selection status for this node
    */
   public CheckBoxNode(String text, boolean selected) {
      this.text = text;
      this.selected = selected;
   }
   
   /**
    * Returns the text displayed for this node.
    * @return the text displayed for this node
    */
   public String getText() {
      return text;
   }
   
   /**
    * Sets the text displayed for this node.
    * @param text the new text to be displayed for this node
    */
   public void setText(String text) {
       this.text = text;
   }

    /**
    * Returns the selection status of this node.
    * @return the selection status of this node
    */
   public boolean isSelected() {
      return selected;
   }
   
   /**
    * Sets the selection status of this node.
    * @param selected the new selection status for this node
    */
   public void setSelected(boolean selected) {
      this.selected = selected;
   }
}
