import SQLStatements.Companion.INSERT_PRODUCT
import SQLStatements.Companion.SELECT_ALL_PRODUCTS
import SQLStatements.Companion.SELECT_PRODUCT_BY_ID
import java.sql.*

class Gestor private constructor() {
    companion object {
        private var instance: Gestor? = null

        fun getInstance(): Gestor {
            if (instance == null) {
                instance = Gestor()
            }
            return instance!!
        }
    }

    private val url: String = "jdbc:mysql://localhost/"
    private val bd: String = "tienda"
    private val user: String = "root"
    private val password: String = ""

    @Volatile
    private var conn: Connection? = null

    fun conectarBBDD() {
        if (conn == null) {
            println("[Conexión realizada]")
            conn = DriverManager.getConnection(url+bd, user, password)
        } else {
            println("[Conexión ya existente]")
        }
    }

    fun desconectar() {
        if (conn != null) {
            conn!!.close()
            println("[Desconexión realizada]")
        } else {
            println("[No existe conexión a la BBDD]")
        }
    }

    fun selectAll(): MutableList<MisProductos> {
        var productos: MutableList<MisProductos> = mutableListOf()

        if (conn != null) {
            try {
                conn!!.prepareStatement(SELECT_ALL_PRODUCTS).use { statement ->
                    val results = statement.executeQuery()

                    while (results.next()) {
                        val id = results.getString("ID")
                        val nombre = results.getString("Nombre")
                        val precio = results.getInt("Precio")
                        val cantidad = results.getInt("Cantidad")
                        val descripcion = results.getString("Descripcion")
                        productos.add(MisProductos(id, nombre, precio, cantidad, descripcion))
                    }
                }
            } catch (e: SQLException) {
                printSQLException(e)
            }
        }
        return productos
    }

    fun selectProducto(id_producto: String): MisProductos? {
        var producto: MisProductos? = null

        if (conn != null) {
            try {
                conn!!.prepareStatement(SELECT_PRODUCT_BY_ID).use { statement ->
                    statement.setString(1, id_producto)
                    val results = statement.executeQuery()

                    while (results.next()) {
                        val nombre = results.getString("Nombre")
                        val precio = results.getInt("Precio")
                        val cantidad = results.getInt("Cantidad")
                        val descripcion = results.getString("Descripcion")
                        producto = MisProductos(id_producto, nombre, precio, cantidad, descripcion)
                    }
                }

            } catch (e: SQLException) {
                printSQLException(e)
            }
        }

        return producto
    }

    fun insertProducto(producto: MisProductos) {
        if (conn != null) {
            conn!!.autoCommit = false
            try {
                conn!!.prepareStatement(INSERT_PRODUCT).use { statement ->
                    statement.setString(1, producto.id)
                    statement.setString(2, producto.nombre)
                    statement.setInt(3, producto.precio)
                    statement.setInt(4,producto.cantidad)
                    statement.setString(5,producto.descripcion)
                    println(statement)
                    statement.executeUpdate()
                }
                conn!!.commit()
            } catch (e: SQLException) {
                conn!!.rollback()
                printSQLException(e)
            }
        }
    }

    private fun printSQLException(ex: SQLException) {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }
}