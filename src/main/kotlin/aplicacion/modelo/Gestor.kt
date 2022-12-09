package aplicacion.modelo

import aplicacion.modelo.clases.MisProductos
import aplicacion.modelo.sentencias.SQLStatements.Companion.DELETE_PRODUCT
import aplicacion.modelo.sentencias.SQLStatements.Companion.INSERT_PRODUCT
import aplicacion.modelo.sentencias.SQLStatements.Companion.SELECT_ALL_PRODUCTS
import aplicacion.modelo.sentencias.SQLStatements.Companion.SELECT_PRODUCT_BY_ID
import aplicacion.modelo.sentencias.SQLStatements.Companion.SELECT_WITH_STOCK
import aplicacion.modelo.sentencias.SQLStatements.Companion.UPDATE_PRODUCT
import com.mysql.cj.jdbc.exceptions.CommunicationsException
import java.sql.*
import kotlin.system.exitProcess

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

    @Volatile private var conn: Connection? = null

    fun conectarBBDD() {
        if (conn == null) {
            try {
                println("\n[Conexión realizada]\n")
                conn = DriverManager.getConnection(url + bd, user, password)
            } catch (ex: CommunicationsException) {
                ex.printStackTrace(System.err)
                System.err.println("\nSQLState: " + ex.sqlState)
                System.err.println("Error Code: " + ex.errorCode)
                System.err.println("Message: " + ex.message)
                exitProcess(-1)
            }
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

    fun consultarStock(): MutableList<MisProductos> {
        var productosConStock: MutableList<MisProductos> = mutableListOf()

        if (conn != null) {
            conn!!.autoCommit = false
            try {
                conn!!.prepareStatement(SELECT_WITH_STOCK).use { statement ->
                    val results = statement.executeQuery()

                    while (results.next()) {
                        val id = results.getString("ID")
                        val nombre = results.getString("Nombre")
                        val precio = results.getInt("Precio")
                        val cantidad = results.getInt("Cantidad")
                        val descripcion = results.getString("Descripcion")
                        productosConStock.add(MisProductos(id, nombre, precio, cantidad, descripcion))
                    }
                    conn!!.commit()
                }
            } catch (e: SQLException) {
                conn!!.rollback()
                printSQLException(e)
            }
        }
        return productosConStock
    }

    fun selectProducto(id_producto: String): MisProductos? {
        var producto: MisProductos? = null

        if (conn != null) {
            conn!!.autoCommit = false
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
                    conn!!.commit()
                }
            } catch (e: SQLException) {
                conn!!.rollback()
                printSQLException(e)
            }
        }
        return producto
    }

    fun eliminarProducto(nombreProducto: String) {
        if (conn != null) {
            conn!!.autoCommit = false
            try {
                conn!!.prepareStatement(DELETE_PRODUCT).use { statement ->
                    statement.setString(1, nombreProducto)
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

    fun updateProducto(producto: MisProductos): MisProductos? {
        var productoActualizado: MisProductos? = null
        var rowUpdated = false

        if (conn != null) {
            conn!!.autoCommit = false
            try {
                conn!!.prepareStatement(UPDATE_PRODUCT).use { statement ->
                    statement.setString(5, producto.id)
                    statement.setString(1 , producto.nombre)
                    statement.setInt(2, producto.precio)
                    statement.setInt(3, producto.cantidad)
                    statement.setString(4, producto.descripcion)
                    rowUpdated = statement.executeUpdate() > 0
                }
                conn!!.commit()
            } catch (e: SQLException) {
                conn!!.rollback()
                printSQLException(e)
            }
        }

        return if (rowUpdated) {
            productoActualizado = producto
            productoActualizado
        } else { productoActualizado }
    }

    fun insertProducto(producto: MisProductos): MisProductos {
        if (conn != null) {
            conn!!.autoCommit = false
            try {
                conn!!.prepareStatement(INSERT_PRODUCT).use { statement ->
                    statement.setString(1, producto.id)
                    statement.setString(2, producto.nombre)
                    statement.setInt(3, producto.precio)
                    statement.setInt(4, producto.cantidad)
                    statement.setString(5, producto.descripcion)
                    println(statement)
                    statement.executeUpdate()
                }
                conn!!.commit()
            } catch (e: SQLException) {
                conn!!.rollback()
                printSQLException(e)
            }
        }
        return producto
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
