package masachim;

import helpers.DualMapSet;
import helpers.EqualMapSet;
import helpers.JSensitiveIntField;
import helpers.ProductListCellRenderer;
import helpers.ProductTableCellRenderer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import main.MainWindow;

import databaseManager.DBQueries;
import enums.Options;
import templates.MyPanel;
import vo.PriceVO;
import vo.ProductNameVO;
import vo.ProductVO;
import vo.StoreVO;

public class MasachHashvaatMechirim extends MyPanel {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 239939932452871639L;

	// יישות ששולפת נתונים מהטבלאות
	private DBQueries dbq;

	// טבלאות מרכזיות
	private DualMapSet<Integer, String> categoryIDToCategoryName;
	private EqualMapSet<String, Vector<Integer>> categoryProducts;
	private DualMapSet<Integer, Integer> storeIDToStoreCulomn;
	private DualMapSet<Integer, ProductNameVO> productIDToProductName;
	private DualMapSet<Integer, Integer> productIDToPriceRow;
	private DefaultTableModel priceTable;

	// הגדרת הכפתור שמחזיר תלפריט הראשי
	private JButton back = null;

	// הקומבו של הקטגוריות
	private JComboBox<String> categoriesCombo;

	private JList<Integer> productList;
	private DefaultListModel<Integer> listModel;
	private DefaultTableModel tableModel;
	private static final String ALL_CATEGORIES = "כל הקטגוריות";

	// הכותרת של הטבלאות היא קבועה עבור כל הטבלאות
	private String[] koteret;

	private JSensitiveIntField kamut;

	public MasachHashvaatMechirim() throws Exception {
		/*
		 * אתחול רכיב קישור מול הטבלאות
		 */
		dbq = new DBQueries();

		/*
		 * יצירת מערכים ראשיים
		 */
		init();

		/*
		 * יצירת הפאנל של בחירת מוצרים להוספה לפי קטגוריות
		 */
		JPanel addProduct = createAddProduct();

		/*
		 * יצירת הפאנל המרכזי של מפה ההשוואה
		 */
		JPanel tablePanel = createTablePanel();

		/*
		 * קביעת תצורת המסך הראשי
		 */
		setLayout(new BorderLayout());

		/*
		 * מיקום הטבלה במרכז המסך
		 */
		add(tablePanel, BorderLayout.CENTER);

		/*
		 * מיקום פאנל הוספת מוצרים
		 */
		add(addProduct, BorderLayout.EAST);

		/*
		 * התחלת הגדרת כפתור חזרה אחורה
		 */
		back = new JButton(Options.tafrit.getDescription());
		back.setActionCommand(Options.tafrit.name());

		/*
		 * מיקום כפתור החזרה בתחתית המסך
		 */
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(back);
		add(gridBagPanel, BorderLayout.SOUTH);
	}

	private void init() throws Exception {
		/*
		 * אחזור מפה של הקטגוריות
		 */
		categoryIDToCategoryName = dbq.getCategories();

		// אחזור רשימת המוצרים
		Vector<ProductVO> products = dbq.getCategoryProducts();

		// אתחול מפה אשר תחזיק עבור כל קטגוריה את מערך כל המוצרים שלה
		// אתחול מפה אשר מקשרת בין המספר הקטלוגי לשם המוצר
		// יצירת וקטור אשר יחזיק את כל המוצרים בשביל הקטגוריה של כל המוצרים
		categoryProducts = new EqualMapSet<String, Vector<Integer>>();
		productIDToProductName = new DualMapSet<Integer, ProductNameVO>();
		Vector<Integer> allProducts = new Vector<Integer>();
		for (int i = 0; i < products.size(); i++) {
			ProductVO vo = products.elementAt(i);
			// אחזור שם הקטגוריה לפי המספור הרץ
			String catName = categoryIDToCategoryName.getRightKey(vo
					.getProductCategory());

			// הוספת המוצר למפת השמות: מספר מוצר - שם מוצר
			productIDToProductName.put(
					vo.getProductID(),
					new ProductNameVO(vo.getProductName(), vo
							.getProductProducer()));

			if (categoryProducts.containsKey(catName)) {
				// אם הקטגוריה כבר קיימת אז מוסיפים לוקטור המוצרים של הקטגוריה
				Vector<Integer> vector = categoryProducts.get(catName);
				vector.add(vo.getProductID());
				categoryProducts.put(catName, vector);
			} else {
				// אם הקטגוריה לא קיימת, מוסיפים ווקטור חדש
				Vector<Integer> vector = new Vector<Integer>();
				vector.add(vo.getProductID());
				categoryProducts.put(catName, vector);
			}
			// הוספת המוצר לרשימת המוצרים הכללית
			allProducts.add(vo.getProductID());

		}
		// הוספת ווקטור כל המוצריך למערת המוצרים
		categoryProducts.put(ALL_CATEGORIES, allProducts);

		/*
		 * יצירת מערך המיקומים של החנויות בעמודות הטבלה
		 */
		Vector<StoreVO> stores = dbq.getStores();
		storeIDToStoreCulomn = new DualMapSet<Integer, Integer>();
		for (int i = 0; i < stores.size(); i++) {
			storeIDToStoreCulomn.put(stores.elementAt(i).getStoreID(), i);
		}

		/*
		 * יצירת הכותרת של טבלת השוואת המחירים
		 */
		koteret = new String[stores.size() + 3];
		for (int i = 0; i < stores.size(); i++) {
			String name = stores.elementAt(i).getStoreName();
			int id = stores.elementAt(i).getStoreID();
			koteret[storeIDToStoreCulomn.getRightKey(id)] = name;
		}
		koteret[koteret.length - 3] = "כמות";
		koteret[koteret.length - 2] = "יצרן";
		koteret[koteret.length - 1] = "שם מוצר";

		/*
		 * יצירת מערך מחירים לפי מספר קטלוגי וחנות
		 */
		// מערך המצביע באיזו שורה יימצא כל מוצר
		productIDToPriceRow = new DualMapSet<Integer, Integer>();
		for (int i = 0; i < products.size(); i++) {
			productIDToPriceRow.put(products.elementAt(i).getProductID(), i);
		}

		// הכנת כותרת - הכרחי ליצירת טבלת המחירים הכללית
		String[] modelKoteret = new String[storeIDToStoreCulomn.size() + 1];
		for (int i = 0; i < stores.size(); i++) {
			String name = stores.elementAt(i).getStoreName();
			int id = stores.elementAt(i).getStoreID();
			modelKoteret[storeIDToStoreCulomn.getRightKey(id)] = name;
		}
		modelKoteret[modelKoteret.length - 1] = "מספר מוצר";

		// הגדרת הטבלה
		// מספר העמודות הוא כמספר החנויות + עוד עמודה למספר הקטלוגי של המוצר
		// מספר השורות הוא כמספר המוצרים
		Object[][] data = new Object[productIDToPriceRow.size()][storeIDToStoreCulomn
				.size() + 1];

		// ריצה על עמודת המוצרים והגדרת כל מוצר
		for (int i = 0; i < productIDToPriceRow.size(); i++) {
			ProductVO p = products.elementAt(i);
			int row = productIDToPriceRow.getRightKey(p.getProductID());
			data[row][storeIDToStoreCulomn.size()] = p.getProductID();
		}

		// שליפת כל המחירים של כל המוצרים
		Vector<PriceVO> prices = dbq.getPrices();

		// ריצה על כל המחירים ושיבוצם בטבלה לפי השורה של המוצר והעמודה של החנות
		for (int i = 0; i < prices.size(); i++) {
			try {
				PriceVO p = prices.elementAt(i);
				int row = productIDToPriceRow.getRightKey(p.getProductID());
				int column = storeIDToStoreCulomn.getRightKey(p.getStoreID());
				data[row][column] = p.getPrice();
			} catch (Exception e) {
			}
		}

		// ולבסוף הגדרת טבלת המחירים הכוללת
		priceTable = new DefaultTableModel(data, modelKoteret);
	}

	private JPanel createTablePanel() throws Exception {

		Object[][] data = new Object[1][storeIDToStoreCulomn.size() + 3];
		for (int i = 0; i < storeIDToStoreCulomn.size(); i++) {
			data[0][i] = (Double) 0.0;
		}

		data[0][koteret.length - 1] = "סה\"כ";
		tableModel = new DefaultTableModel(data, koteret);
		JTable table = new JTable(tableModel);
		table.getTableHeader().setFont(MainWindow.FONT);
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(MainWindow.FONT);
		table.setEnabled(false);
		ProductTableCellRenderer cellRenderer = new ProductTableCellRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(table.getColumnName(i)).setCellRenderer(
					cellRenderer);
		}

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JScrollPane(table), BorderLayout.CENTER);

		return panel;
	}

	private JPanel createAddProduct() throws Exception {
		// יצירת מערך של כל הקטגוריות והוספת קטגוריית "כל המוצרים" לצורך הקומבו
		String[] categoryArray = new String[categoryIDToCategoryName.size() + 1];
		categoryArray[0] = ALL_CATEGORIES;
		// המרת אוסף הקטגוריות לסוג אוסף אחר המאפשר ריצה על כל קטגוריה
		Set<String> catSet = categoryIDToCategoryName.rightKeySet();
		Iterator<String> i = catSet.iterator();
		int n = 0;
		// הוספת שמות הקטגוריות למערך הקטגוריות
		while (i.hasNext()) {
			n++;
			categoryArray[n] = i.next();
		}

		// יצירת הקומבו - ע"י מערך השמות של הקטגוריות
		categoriesCombo = new JComboBox<String>(categoryArray);
		categoriesCombo.setFont(MainWindow.FONT);
		// הוספת מאזין לקומבו - בכל פעם שנשנה את הקטגוריה נשנה את רשימת המוצרים
		// ברשימה
		categoriesCombo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				changeToChosenCategory();
			}
		});

		listModel = new DefaultListModel<Integer>();
		productList = new JList<Integer>(listModel);
		productList.setCellRenderer(new ProductListCellRenderer(
				productIDToProductName));

		changeToChosenCategory();

		JButton hosef = new JButton("הוסף");
		hosef.setFont(MainWindow.FONT);
		hosef.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addProducts();
			}

		});
		JButton roken = new JButton("רוקן טבלה");
		roken.setFont(MainWindow.FONT);
		roken.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetProductTable();
			}

		});

		kamut = new JSensitiveIntField();
		kamut.setLengthLimit(3);
		kamut.setFont(MainWindow.FONT);

		JLabel kamutLabel = new JLabel("כמות: ", SwingConstants.CENTER);
		kamutLabel.setFont(MainWindow.FONT);

		JPanel southPanel = new JPanel(new GridLayout(1, 4, 5, 5));

		JPanel gridBagPanel;

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(roken);
		southPanel.add(gridBagPanel);
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(hosef);
		southPanel.add(gridBagPanel);
		southPanel.add(kamut);
		southPanel.add(kamutLabel);

		JPanel returnedPanel = new JPanel(new BorderLayout());

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(categoriesCombo);
		returnedPanel.add(new JLabel("     "), BorderLayout.WEST);
		returnedPanel.add(new JLabel("     "), BorderLayout.EAST);
		returnedPanel.add(gridBagPanel, BorderLayout.NORTH);

		returnedPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(southPanel);
		returnedPanel.add(gridBagPanel, BorderLayout.SOUTH);

		returnedPanel.setMinimumSize(new Dimension(300, 200));
		return returnedPanel;
	}

	private void resetProductTable() {
		while (tableModel.getRowCount() > 1) {
			tableModel.removeRow(0);
		}
		for (int i = 0; i < tableModel.getColumnCount() - 3; i++) {
			tableModel.setValueAt((Double) 0.0, 0, i);
		}
	}

	private void changeToChosenCategory() {
		String category = (String) categoriesCombo.getSelectedItem();
		listModel.removeAllElements();
		if (categoryProducts.containsKey(category)) {
			Vector<Integer> vector = categoryProducts.get(category);
			for (int i = 0; i < vector.size(); i++) {
				listModel.addElement(vector.elementAt(i));
			}
		}
		productList.repaint();
		productList.setEnabled(true);
	}

	private void addProducts() {
		if (kamut == null || kamut.toInt() == null || kamut.toInt() <= 0) {
			JOptionPane.showMessageDialog(this, "עליך להקיש כמות מבוקשת",
					"Error", JOptionPane.ERROR_MESSAGE);
		} else if (productList.getSelectedValuesList() == null
				|| productList.getSelectedValuesList().size() <= 0) {
			JOptionPane.showMessageDialog(this, "עליך לבחור לפחות מוצר אחד",
					"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			Iterator<Integer> i = productList.getSelectedValuesList()
					.iterator();
			Integer kamutHosafa = kamut.toInt();
			while (i.hasNext()) {
				Integer productID = i.next();
				String productName = productIDToProductName.getRightKey(
						productID).getProductName();
				String productProducer = productIDToProductName.getRightKey(
						productID).getProductProducer();
				Object[] data = new Object[storeIDToStoreCulomn.size() + 3];
				data[data.length - 3] = kamutHosafa;
				data[data.length - 2] = productProducer;
				data[data.length - 1] = productName;
				int productPriceRow = productIDToPriceRow
						.getRightKey(productID);
				for (int j = 0; j < storeIDToStoreCulomn.size(); j++) {
					try {
						Double price = (Double) priceTable.getValueAt(
								productPriceRow, j)
								* Double.parseDouble(kamutHosafa.toString());
						data[j] = price;
						Double totalPrice = (Double) tableModel.getValueAt(
								tableModel.getRowCount() - 1, j);
						totalPrice += price;
						tableModel.setValueAt(totalPrice,
								tableModel.getRowCount() - 1, j);
					} catch (Exception e) {

					}
				}
				tableModel.insertRow(0, data);
			}
		}
	}

	//
	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		if (back != null) {
			back.addActionListener(l);
		}
	}

	// public static void main(String[] args) {
	// try {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// final JFrame frame = new JFrame();
	//
	// frame.getContentPane()
	// .add(new MasachHashvaatMechirim());
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// frame.setPreferredSize(new Dimension(500, 500));
	// frame.setMinimumSize(new Dimension(800, 500));
	// frame.pack();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// } catch (Exception e) {
	// System.err.println(e.getMessage());
	// }
	//
	// }
}
