## Acesso a banco de dados com JDBC

#### Objetivo geral:
- Conhecer os principais recursos do JDBC 
- Elaborar a estrutura básica de um projeto com JDBC
- Implementar o padrão DAO manualmente com JDBC

### Visão geral do JDBC

![](https://avaldes.com/wp-content/uploads/2011/05/Java_jdbc.png)



### Padrão de projeto DAO (Data Access Object)

#### Ideia geral do padrão DAO:
Para cada entidade, haverá um objeto responsável por fazer acesso a dados relacionado a esta entidade. Por exemplo:
- Cliente: ClienteDao
- Produto: ProdutoDao
- Pedido: PedidoDao

Cada DAO será definido por uma interface.
A injeção de dependência pode ser feita por meio do padrão de projeto Factory

![](https://i.ibb.co/YBn4wjJ/jdbc.png)

##### Exemplo:

##### 1. DaoFactory

```java
public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	 public static DepartmentDao createDepartment() {
		 return new DepartmentDaoJDBC(DB.getConnection());
	 }
}
```
##### 2. SellerDao

```java
public interface SellerDao {
	
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	Seller findById(Integer id);
	List<Seller> findAll();
	List<Seller> findByDepartment(Department department);
}
```

### Implementação de operadores no acesso ao banco de dados

#### Exemplo:
###### findById implementation

```java
@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ?");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs, dep);
				return obj;
			}
			return null;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
```

### End
