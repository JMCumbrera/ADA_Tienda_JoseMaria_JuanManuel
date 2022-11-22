fun main() {
    val dbInstance: Gestor = Gestor.getInstance()
    dbInstance.conectarBBDD()

    var selectStock = dbInstance.consultarStock()
    selectStock.forEach { println(it) }

/*

   var selectAll = dbInstance.selectAll()
  selectAll.forEach { println(it) }

    val selectProducto = dbInstance.selectProducto("12345678")
    if (selectProducto != null) {
        println(selectProducto)
    }

    val newProduct = MisProductos("1234567C","Alfombrilla", 400, 50, "Alfombrilla para el ratón")
    dbInstance.insertProducto(newProduct)


 */
    dbInstance.desconectar()
}

