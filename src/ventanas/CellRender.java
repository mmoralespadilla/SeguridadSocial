	package ventanas;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRender extends DefaultTableCellRenderer {

		@Override
		 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		 if (value instanceof JLabel) {
		 JLabel label = (JLabel) value;
		 label.setOpaque(true);
		 setHorizontalAlignment(SwingConstants.CENTER);

		 fillColor(table, label, isSelected);
		 return label;
		 } else {
		 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		 }
		 }
		 public void fillColor(JTable t, JLabel l, boolean isSelected) {
		 if (isSelected) {
		 l.setBackground(t.getSelectionBackground());
		 l.setForeground(t.getSelectionForeground());
		 setHorizontalAlignment(SwingConstants.CENTER);
		 } else {
		 l.setBackground(t.getBackground());
		 l.setForeground(t.getForeground());
		 setHorizontalAlignment(SwingConstants.CENTER);
		 }
		 }
}

