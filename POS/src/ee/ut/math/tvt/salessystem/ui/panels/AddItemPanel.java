package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

public class AddItemPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private SalesSystemModel model;
	
	JTextField IdField;
	JTextField nameField;
	JTextField PriceField;
	JTextField QuantityField;
	JTextField descField;
	
	public AddItemPanel(SalesSystemModel model){
		this.model = model;
	}
	
	public JComponent drawAddingPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		
		JLabel idLabel = new JLabel("Id");
		JLabel nameLabel = new JLabel("Name");
		JLabel descLabel = new JLabel("Desc");
		JLabel priceLabel = new JLabel("Price");
		JLabel quantityLabel = new JLabel("Quantity");
		
		IdField = new JTextField(3);
		nameField = new JTextField(6);
		descField = new JTextField(6);
		PriceField = new JTextField(3);
		QuantityField = new JTextField(6);
		
		
		panel.add(idLabel);
		panel.add(IdField);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(descLabel);
		panel.add(descField);
		panel.add(priceLabel);
		panel.add(PriceField);
		panel.add(quantityLabel);
		panel.add(QuantityField);
		
		return panel;
	}

	public JTextField getDescField() {
		return descField;
	}

	public void setDescField(JTextField descField) {
		this.descField = descField;
	}

	public JTextField getIdField() {
		return IdField;
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getPriceField() {
		return PriceField;
	}

	public JTextField getQuantityField() {
		return QuantityField;
	}

	public void setIdField(JTextField idField) {
		IdField = idField;
	}

	public void setNameField(JTextField nameField) {
		this.nameField = nameField;
	}

	public void setPriceField(JTextField priceField) {
		PriceField = priceField;
	}

	public void setQuantityField(JTextField quantityField) {
		QuantityField = quantityField;
	}


}
