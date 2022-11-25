package aplicacion.modelo.sentencias

class SQLStatements {
    companion object {
        const val SELECT_ALL_PRODUCTS = "SELECT * FROM productos;"
        const val SELECT_PRODUCT_BY_ID = "SELECT * FROM productos WHERE id = ?;"
        const val INSERT_PRODUCT = "INSERT INTO productos (ID, Nombre, Precio, Cantidad, Descripcion) VALUES (?,?,?,?,?);"

        const val SELECT_WITH_STOCK = "SELECT * FROM productos WHERE cantidad > 0"
        const val DELETE_PRODUCT = "DELETE FROM productos WHERE NOMBRE = ? "
    }
}
