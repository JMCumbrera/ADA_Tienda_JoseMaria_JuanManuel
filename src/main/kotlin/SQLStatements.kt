class SQLStatements {
    companion object {
        const val SELECT_ALL_PRODUCTS = "SELECT * FROM productos;"
        const val SELECT_PRODUCT_BY_ID = "SELECT * FROM productos WHERE id = ?;"
        const val INSERT_PRODUCT = "INSERT INTO productos (ID, Nombre, Precio, Cantidad, Descripcion) VALUES (?,?,?,?,?);"
    }
}