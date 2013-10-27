package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.FlowLayout;

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
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		panel.setLayout(layout);
		
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
		
		
		JPanel IdPanel = new JPanel();
		JPanel NamePanel = new JPanel();
		JPanel descPanel = new JPanel();
		JPanel PricePanel = new JPanel();
		JPanel QuantPanel = new JPanel();
		
		
		IdPanel.add(idLabel);
		IdPanel.add(IdField);
		NamePanel.add(nameLabel);
		NamePanel.add(nameField);
		descPanel.add(descLabel);
		descPanel.add(descField);
		PricePanel.add(priceLabel);
		PricePanel.add(PriceField);
		QuantPanel.add(quantityLabel);
		QuantPanel.add(QuantityField);
		
		panel.add(IdPanel);
		panel.add(NamePanel);
		panel.add(descPanel);
		panel.add(PricePanel);
		panel.add(QuantPanel);
		
		
		
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
