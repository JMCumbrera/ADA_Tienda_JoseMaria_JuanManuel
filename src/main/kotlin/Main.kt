fun main() {
    val dbInstance: Gestor = Gestor.getInstance()
    dbInstance.conectarBBDD()
    var selectAll = dbInstance.selectAll()
    selectAll.forEach { println(it) }

    val selectProducto = dbInstance.selectProducto("1234567A")
    if (selectProducto != null) {
        println(selectProducto)
    }

    val newProduct = MisProductos("1234567C","Alfombrilla", 400, 50, "Alfombrilla para el ratón")
    dbInstance.insertProducto(newProduct)

    dbInstance.desconectar()
}