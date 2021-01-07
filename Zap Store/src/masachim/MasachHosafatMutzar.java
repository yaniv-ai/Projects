package masachim;

import helpers.DualMapSet;
import helpers.EqualMapSet;
import helpers.JSensitiveTextField;
import helpers.PriceTableCellRenderer;
import helpers.PriceTableFieldEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main.MainWindow;

import databaseManager.DBQueries;
import enums.Options;
import templates.MyPanel;
import vo.PriceVO;
import vo.ProductNameVO;
import vo.ProductVO;
import vo.StoreVO;

public class MasachHosafatMutzar extends MyPanel implements TableModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// הגדרת הכפתור שמחזיר תלפריט הראשי
	private JButton back = null;

	// הוספת קטגוריה
	private JSensitiveTextField newCategory;
	private JButton addNewCategory;

	// הוספת מוצר
	private JSensitiveTextField newProductName;
	private JSensitiveTextField newProductProducer;
	private JButton addNewProduct;

	// טבלת התיקיות. מכילה את כל תיקיות הקטגוריות
	private JTabbedPane tabbedPane;

	private DualMapSet<Integer, String> categoriesToNames;
	private DualMapSet<Integer, Integer> storesToCulomns;
	private DualMapSet<Integer, ProductNameVO> productIDToProductname;
	private EqualMapSet<Integer, DefaultTableModel> dataModels;

	// הכותרת של הטבלאות היא קבועה עבור כל הטבלאות
	private String[] koteret;

	// יישות ששולפת נתונים מהטבלאות
	private DBQueries dbq;

	public MasachHosafatMutzar() throws Exception {
		dbq = new DBQueries();
		JPanel gridBagPanel = new JPanel(new GridBagLayout());

		// ממלא את טבלאות המחירים
		fillTabbedPane();

		// הכנת פאנל הוספת מוצר
		JPanel addProduct = createAddProduct();

		// הגדרת כפתור החזרה אחורה
		back = new JButton(Options.tafrit.getDescription());
		back.setActionCommand(Options.tafrit.name());

		setLayout(new BorderLayout());

		// מיקום הטבלאות במרכז המסך
		add(tabbedPane, BorderLayout.CENTER);

		// מיקום פאנל הוספת מוצר
		add(addProduct, BorderLayout.EAST);

		// מיקום כפתור החזרה בתחתית המסך
		gridBagPanel.add(back);
		add(gridBagPanel, BorderLayout.SOUTH);
	}

	private void fillTabbedPane() throws Exception {
		dataModels = new EqualMapSet<Integer, DefaultTableModel>();
		// 1. שליפת כל החנויות
		// 2. יצירת מפה (מספר חנות,מספר עמודה) ככה נקבע עבור כל חנות באיזו
		// עמודה בטבלה יופיע המחיר של כל מוצר
		// העמודה האחרונה שמורה למספר הקטלוגי של כל מוצר
		// 3. שליפת כל המוצרים לצורך בניית מפה של השם והיצרן
		// 4. בניית המפה לשליפת שמות לפי מספר קטלוגי
		// 5. הכנת הכותרת של הטבלאות לפי היצרנים
		// 6. שליפת כל הקטגוריות
		// 7. הגדרת מסך תיקיות

		// ****

		// 1.
		Vector<StoreVO> stores = dbq.getStores();
		// 2.
		storesToCulomns = new DualMapSet<Integer, Integer>();
		for (int i = 0; i < stores.size(); i++) {
			storesToCulomns.put(stores.elementAt(i).getStoreID(), i);
		}

		// 3.
		Vector<ProductVO> products = dbq.getCategoryProducts();
		// 4.
		productIDToProductname = new DualMapSet<Integer, ProductNameVO>();
		for (int i = 0; i < products.size(); i++) {
			productIDToProductname.put(products.elementAt(i).getProductID(),
					new ProductNameVO(products.elementAt(i).getProductName(),
							products.elementAt(i).getProductProducer()));
		}

		// 5.
		koteret = new String[stores.size() + 2];
		for (int i = 0; i < stores.size(); i++) {
			String name = stores.elementAt(i).getStoreName();
			int id = stores.elementAt(i).getStoreID();
			koteret[storesToCulomns.getRightKey(id)] = name;
		}
		koteret[koteret.length - 2] = "יצרן";
		koteret[koteret.length - 1] = "שם מוצר";

		// 6.
		categoriesToNames = dbq.getCategories();

		// 7.
		tabbedPane = new JTabbedPane();

		Set<Integer> catSet = categoriesToNames.leftKeySet();
		Iterator<Integer> i = catSet.iterator();
		while (i.hasNext()) {
			Integer cat = i.next();
			JPanel panel = fillCategoryTable(cat);
			tabbedPane.addTab(categoriesToNames.getRightKey(cat), panel);
		}

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	private JPanel fillCategoryTable(final Integer cat) throws Exception {
		Vector<ProductVO> products = dbq.getCategoryProducts(cat);
		final DualMapSet<Integer, Integer> productRow = new DualMapSet<Integer, Integer>();
		for (int i = 0; i < products.size(); i++) {
			productRow.put(products.elementAt(i).getProductID(), i);
		}

		Object[][] data = new Object[productRow.size()][storesToCulomns.size() + 2];

		Vector<PriceVO> prices = dbq.getPrices(cat);
		for (int i = 0; i < productRow.size(); i++) {
			ProductVO p = products.elementAt(i);
			int row = productRow.getRightKey(p.getProductID());
			data[row][storesToCulomns.size()] = p.getProductID();
			data[row][storesToCulomns.size() + 1] = p.getProductID();
		}
		for (int i = 0; i < prices.size(); i++) {
			try {
				PriceVO p = prices.elementAt(i);
				int row = productRow.getRightKey(p.getProductID());
				int column = storesToCulomns.getRightKey(p.getStoreID());
				data[row][column] = p.getPrice();
			} catch (Exception e) {
			}
		}

		DefaultTableModel dm = new DefaultTableModel(data, koteret);
		dm.addTableModelListener(this);
		dataModels.put(cat, dm);
		JTable table = new JTable(dm);
		table.getTableHeader().setFont(MainWindow.FONT);
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(MainWindow.FONT);

		PriceTableCellRenderer cellRenderer = new PriceTableCellRenderer(
				productIDToProductname);
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		cellRenderer.setVerticalAlignment(JLabel.CENTER);
		PriceTableFieldEditor cellEditor = new PriceTableFieldEditor();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(
					cellRenderer);
			table.getColumn(table.getColumnName(i)).setCellEditor(cellEditor);
		}

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createAddProduct() {
		// הוספת הקטגוריה
		JLabel newCategoryLabel = new JLabel("שם קטגוריה: ",
				SwingConstants.CENTER);
		newCategory = new JSensitiveTextField();
		newCategory.setHorizontalAlignment(JTextField.CENTER);
		addNewCategory = new JButton("הוסף");
		newCategoryLabel.setFont(MainWindow.FONT);
		newCategory.setFont(MainWindow.FONT);
		addNewCategory.setFont(MainWindow.FONT);
		addNewCategory.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addCategory(newCategory.getText());
			}

		});

		// הוספת המוצר
		JLabel newProductNameLabel = new JLabel("שם מוצר: ",
				SwingConstants.CENTER);
		JLabel newProductProducerLabel = new JLabel("שם יצרן: ",
				SwingConstants.CENTER);
		newProductName = new JSensitiveTextField();
		newProductName.setHorizontalAlignment(JTextField.CENTER);
		newProductProducer = new JSensitiveTextField();
		newProductProducer.setHorizontalAlignment(JTextField.CENTER);
		addNewProduct = new JButton("הוסף");
		newProductNameLabel.setFont(MainWindow.FONT);
		newProductProducerLabel.setFont(MainWindow.FONT);
		newProductName.setFont(MainWindow.FONT);
		newProductProducer.setFont(MainWindow.FONT);
		addNewProduct.setFont(MainWindow.FONT);
		addNewProduct.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addProduct(newProductName.getText(),
						newProductProducer.getText());
			}
		});

		JPanel gridBagPanel;

		JPanel catGrid = new JPanel(new GridLayout(1, 2, 20, 20));
		catGrid.add(newCategory);
		catGrid.add(newCategoryLabel);
		JPanel catPanel = new JPanel(new BorderLayout());
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(catGrid);
		catPanel.add(gridBagPanel, BorderLayout.NORTH);
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(addNewCategory);
		catPanel.add(gridBagPanel, BorderLayout.SOUTH);
		catPanel.add(new JLabel("   "), BorderLayout.EAST);
		catPanel.add(new JLabel("   "), BorderLayout.WEST);
		catPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.lightGray, 2, true),
				" הוספת קטגוריה ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		JPanel prodGrid = new JPanel(new GridLayout(2, 2, 20, 20));
		prodGrid.add(newProductName);
		prodGrid.add(newProductNameLabel);
		prodGrid.add(newProductProducer);
		prodGrid.add(newProductProducerLabel);
		JPanel prodPanel = new JPanel(new BorderLayout());
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(prodGrid);
		prodPanel.add(gridBagPanel, BorderLayout.NORTH);
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(addNewProduct);
		prodPanel.add(gridBagPanel, BorderLayout.SOUTH);
		prodPanel.add(new JLabel("   "), BorderLayout.EAST);
		prodPanel.add(new JLabel("   "), BorderLayout.WEST);

		prodPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.lightGray, 2, true),
				" הוספת מוצר ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(catPanel);
		centerPanel.add(gridBagPanel);

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(prodPanel);
		centerPanel.add(gridBagPanel);

		JPanel returnedPanel = new JPanel(new BorderLayout());
		returnedPanel.add(centerPanel, BorderLayout.CENTER);
		returnedPanel.add(new JLabel("        "), BorderLayout.WEST);
		returnedPanel.add(new JLabel("        "), BorderLayout.EAST);
		return returnedPanel;
	}

	private void addCategory(String name) {
		String message = null;
		if (name == null || name.length() <= 0) {
			message = "עליך להקליד שם קטגוריה על מנת להוסיפה לחנות";
		}
		if (message == null && categoriesToNames.containsRightKey(name)) {
			message = "קטגוריה כבר קיימת, נא לבחור שם אחר";
		}
		if (message == null) {
			try {
				Integer catID = dbq.addNewCategory(name);
				categoriesToNames.put(catID, name);
				JPanel panel = fillCategoryTable(catID);
				tabbedPane.addTab(categoriesToNames.getRightKey(catID), panel);
				newCategory.setText(null);
				message = "קטגוריה הוספה בהצלחה !";
			} catch (Exception e) {
				message = "תקלה בעת נסיון הוספת קטגוריה";
			}
		}

		if (message != null) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addProduct(String productName, String productProducer) {
		String message = null;
		if (productName == null || productName.length() <= 0) {
			message = "עליך להקליד שם מוצר על מנת להוסיף מוצר";
		}
		if (message == null
				&& (productProducer == null || productProducer.length() <= 0)) {
			message = "עליך להקליד שם יצרן על מנת להוסיף מוצר";
		}
		if (message == null
				&& productIDToProductname.containsRightKey(new ProductNameVO(
						productName, productProducer))) {
			message = "מוצר כבר קיים, נא לבחור שם אחר";
		}
		if (message == null) {
			try {
				int tabIndex = tabbedPane.getSelectedIndex();
				String catName = tabbedPane.getTitleAt(tabIndex);
				Integer catID = categoriesToNames.getLeftKey(catName);
				Integer productID = dbq.addNewProduct(productName,
						productProducer, catID);
				productIDToProductname.put(productID, new ProductNameVO(
						productName, productProducer));
				DefaultTableModel dm = dataModels.get(catID);
				Object[] data = new Object[storesToCulomns.size() + 2];
				data[storesToCulomns.size()] = productID;
				data[storesToCulomns.size() + 1] = productID;
				dm.insertRow(0, data);

				newProductName.setText(null);
				newProductProducer.setText(null);
				JOptionPane.showMessageDialog(this, "מוצר הוסף בהצלחה", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				message = "תקלה בעת נסיון הוספת מוצר";
			}
		}

		if (message != null) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		if (back != null) {
			back.addActionListener(l);
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE) {
			int tabIndex = tabbedPane.getSelectedIndex();
			String catName = tabbedPane.getTitleAt(tabIndex);
			Integer catID = categoriesToNames.getLeftKey(catName);
			DefaultTableModel dm = dataModels.get(catID);
			int storeID = (Integer) storesToCulomns.getLeftKey(e.getColumn());
			int productID = (Integer) dm.getValueAt(e.getFirstRow(),
					dm.getColumnCount() - 1);
			Double price = (Double) dm.getValueAt(e.getFirstRow(),
					e.getColumn());
			try {
				dbq.updatePrice(storeID, productID, price);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this,
						"תקלה בעת נסיון שינוי מחיר", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
