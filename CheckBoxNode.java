
package com.cs2212;

/**
 *
 * @author sophi
 */
public class CheckBoxNode {
   private final String text;
   private boolean selected;

   public CheckBoxNode(String text, boolean selected) {
      this.text = text;
      this.selected = selected;
   }

   public String getText() {
      return text;
   }

   public boolean isSelected() {
      return selected;
   }

   public void setSelected(boolean selected) {
      this.selected = selected;
   }

   @Override
   public String toString() {
      return text;
   } 
}
