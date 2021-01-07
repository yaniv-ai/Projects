package databaseManager;

import helpers.DualMapSet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPasswordField;

import vo.PriceVO;
import vo.ProductVO;
import vo.StoreVO;
import vo.UserVO;

import enums.Ranks;
import exceptions.DBConnectionException;

public class DBQueries {
	private static final String connectionDriver = "jdbc:odbc:SuperZap";

	private static final String loginQuery = " select Rank from Managment where UserName = ? and UserPass = ? ";
	private static final String usersQuery = " select UserName, UserPass, Rank from Managment order by UserName ";
	private static final String categoriesQuery = " select Id, CatName from Categories order by Id ";
	private static final String storesQuery = " select Id, Storename, StoreUrl from Stores order by Id ";
	private static final String allProductsQuery = " select Id, ProName, Producer, Category from Products order by Id ";
	private static final String categoryProductsQuery = " select Id, ProName, Producer from Products where Category = ? order by Id ";
	private static final String allPricesQuery = " select ProductID, StoreID, Price from Prices order by ProductID, StoreID";
	private static final String pricesQuery = " select ProductID, StoreID, Price "
			+ " from ( Prices as A inner join Products as B on B.Id = A.ProductID ) "
			+ " where B.Category = ? order by ProductID, StoreID";
	private static final String deletePrice = " delete from Prices where StoreID = ? and ProductID = ? ";
	private static final String insertPrice = " insert into  Prices ( StoreID ,ProductID ,Price ) values ( ? , ? , ?  )";
	private static final String maxCategoryId = " select max(Id) as MaxId from Categories ";
	private static final String insertCategory = " insert into  Categories ( Id ,CatName ) values ( ? , ?  )";
	private static final String maxProductId = " select max(Id) as MaxId from Products ";
	private static final String insertProduct = " insert into  Products ( Id ,ProName ,Producer ,Category ) values ( ? , ? , ? , ? )";

	private static final String insertUSer = " insert into Managment ( USerName ,UserPass ,Rank ) values ( ? , ? , ? )";
	private static final String updateRank = " update Managment set Rank = ? where UserName = ? ";
	private static final String deleteUSer = " delete from Managment where UserName = ? ";

	public DBQueries() {
	}

	public Ranks login(String username, String password) throws Exception {
		Connection con = null;
		Ranks rank = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(loginQuery);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				try {
					rank = Ranks.valueOf(rs.getString("Rank"));
				} catch (Exception e) {

				}
			}
			return rank;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<UserVO> getUsers() throws Exception {
		Connection con = null;
		Vector<UserVO> users = new Vector<UserVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(usersQuery);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				String username = rs.getString("UserName");
				JPasswordField password = new JPasswordField(
						rs.getString("UserPass"));
				Ranks rank;
				try {
					rank = Ranks.valueOf(rs.getString("Rank"));
				} catch (Exception e) {
					rank = Ranks.user;

				}
				users.add(new UserVO(username, password, rank));
			}
			return users;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public DualMapSet<Integer, String> getCategories() throws Exception {
		Connection con = null;
		DualMapSet<Integer, String> categories = new DualMapSet<Integer, String>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(categoriesQuery);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer id = rs.getInt("Id");
				String catName = rs.getString("CatName");
				categories.put(id, catName);
			}
			return categories;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<StoreVO> getStores() throws Exception {
		Connection con = null;
		Vector<StoreVO> stores = new Vector<StoreVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(storesQuery);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer id = rs.getInt("Id");
				String storename = rs.getString("Storename");
				String storeUrl = rs.getString("StoreUrl");
				stores.add(new StoreVO(id, storename, storeUrl));
			}
			return stores;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<ProductVO> getCategoryProducts() throws Exception {
		Connection con = null;
		Vector<ProductVO> products = new Vector<ProductVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(allProductsQuery);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer id = rs.getInt("Id");
				String name = rs.getString("ProName");
				String producer = rs.getString("Producer");
				Integer category = rs.getInt("Category");
				products.add(new ProductVO(id, name, producer, category));
			}
			return products;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<ProductVO> getCategoryProducts(int category) throws Exception {
		Connection con = null;
		Vector<ProductVO> products = new Vector<ProductVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(categoryProductsQuery);
			ps.setInt(1, category);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer id = rs.getInt("Id");
				String name = rs.getString("ProName");
				String producer = rs.getString("Producer");
				products.add(new ProductVO(id, name, producer, category));
			}
			return products;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<PriceVO> getPrices() throws Exception {
		Connection con = null;
		Vector<PriceVO> prices = new Vector<PriceVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(allPricesQuery);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer productID = rs.getInt("ProductID");
				Integer storeID = rs.getInt("StoreID");
				Double price;
				try {
					price = Double.parseDouble(rs.getString("Price"));
				} catch (Exception e) {
					price = null;
				}
				prices.add(new PriceVO(productID, storeID, price));
			}
			return prices;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Vector<PriceVO> getPrices(int category) throws Exception {
		Connection con = null;
		Vector<PriceVO> prices = new Vector<PriceVO>();
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(pricesQuery);
			ps.setInt(1, category);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			while (rs.next()) {
				Integer productID = rs.getInt("ProductID");
				Integer storeID = rs.getInt("StoreID");
				Double price;
				try {
					price = Double.parseDouble(rs.getString("Price"));
				} catch (Exception e) {
					price = null;
				}
				prices.add(new PriceVO(productID, storeID, price));
			}
			return prices;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public void updatePrice(int storeID, int productID, Double price)
			throws Exception {
		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(deletePrice);
			ps.setInt(1, storeID);
			ps.setInt(2, productID);
			ps.execute();

			ps = con.prepareStatement(insertPrice);
			ps.setInt(1, storeID);
			ps.setInt(2, productID);
			if (price == null) {
				ps.setString(3, null);
			} else {
				ps.setDouble(3, price);
			}
			ps.execute();
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Integer addNewCategory(String name) throws Exception {
		Connection con = null;
		Integer id = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(maxCategoryId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				id = rs.getInt("MaxID");
			}
			if (id != null) {
				id++;
				ps = con.prepareStatement(insertCategory);
				ps.setInt(1, id);
				ps.setString(2, name);
				ps.execute();
			}
			return id;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public Integer addNewProduct(String productName, String productProducer,
			Integer catID) throws Exception {
		Connection con = null;
		Integer id = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(maxProductId);
			ps.execute();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				id = rs.getInt("MaxID");
			}
			if (id != null) {
				id++;
				ps = con.prepareStatement(insertProduct);
				ps.setInt(1, id);
				ps.setString(2, productName);
				ps.setString(3, productProducer);
				ps.setInt(4, catID);
				ps.execute();
			}
			return id;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public boolean addNewUser(String username, String password, Ranks rank)
			throws Exception {
		if (username == null || password == null || rank == null) {
			return false;
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(insertUSer);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.setString(3, rank.name());
			ps.execute();
			return true;
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public void updateRank(String name, Ranks rank) throws Exception {
		if (name == null || rank == null) {
			return;
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(updateRank);
			ps.setString(1, rank.name());
			ps.setString(2, name);
			ps.execute();
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	public void deleteUser(String name) throws Exception {
		if (name == null) {
			return;
		}
		Connection con = null;
		try {
			con = DriverManager.getConnection(connectionDriver);
		} catch (SQLException e) {
			throw new DBConnectionException("(Connection failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		}

		try {
			PreparedStatement ps = con.prepareStatement(deleteUSer);
			ps.setString(1, name);
			ps.execute();
		} catch (SQLException e) {
			throw new DBConnectionException("(Execution failed) "
					+ e.getMessage(), e.getSQLState(), e.getCause());
		} catch (Exception e) {
			throw new Exception(
					"(query() general Exception) " + e.getMessage(),
					e.getCause());
		} finally {
			if (con != null) {
				try {
					if (!con.isClosed())
						con.close();
				} catch (SQLException e) {
					throw new DBConnectionException(
							"(Closing connection failed) " + e.getMessage(),
							e.getSQLState(), e.getCause());
				}
			}
		}
	}

	// public static void main(String[] args) {
	// final DBQueries dbq = new DBQueries();
	// try {
	// final JPanel panel = new JPanel(new BorderLayout());
	// // panel.add(new JScrollPane(table), BorderLayout.CENTER);
	//
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// final JFrame frame = new JFrame();
	// frame.getContentPane().add(panel);
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// frame.setPreferredSize(new Dimension(500, 500));
	// frame.setMinimumSize(new Dimension(800, 500));
	// frame.pack();
	// frame.setVisible(true);
	// }
	// });
	//
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// }
}
