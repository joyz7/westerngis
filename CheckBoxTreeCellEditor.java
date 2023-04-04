
package com.cs2212;
import javax.swing.*;
import javax.swing.tree.*;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 *
 * @author sophi
 */
public class CheckBoxTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
   private final CheckBoxTreeCellRenderer renderer = new CheckBoxTreeCellRenderer();
   private final JTree tree;

   public CheckBoxTreeCellEditor(JTree tree) {
      this.tree = tree;
   }

   @Override
   public Object getCellEditorValue() {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
      Object userObject = node.getUserObject();
      if (userObject instanceof String) {
         String text = (String) userObject;
         boolean selected = renderer.checkBox.isSelected();
         return new CheckBoxNode(text, selected);
      }
      return null;
   }

   @Override
   public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row) {
      Component editor = renderer.getTreeCellRendererComponent(tree, value, true, expanded, leaf, row, true);
      editor.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent event) {
            if (SwingUtilities.isLeftMouseButton(event)) {
               tree.stopEditing();
            }
         }
      });
      return editor;
   }
}
