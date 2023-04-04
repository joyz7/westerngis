
package com.cs2212;
import java.awt.Component;
import javax.swing.*;
import javax.swing.tree.*;

/**
 *
 * @author sophi
 */
public class CheckBoxTreeCellRenderer extends DefaultTreeCellRenderer {
   final JCheckBox checkBox = new JCheckBox();

   @Override
   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
      super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
      if (value instanceof DefaultMutableTreeNode) {
         Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
         if (userObject instanceof String) {
            setText((String) userObject);
            setIcon(null);
            checkBox.setSelected(false);
            checkBox.setOpaque(false);
            checkBox.setEnabled(tree.isEnabled());
            return checkBox;
         }
      }
      return this;
   }
}    
