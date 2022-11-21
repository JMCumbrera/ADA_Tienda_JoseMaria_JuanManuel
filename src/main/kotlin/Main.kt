fun main() {
    val dbInstance: Gestor = Gestor.getInstance()
    dbInstance.conectarBBDD()
    var selectAll = dbInstance.selectAll()
    selectAll.forEach { println(it) }
    //dbInstance.desconectar()

    var selectProducto = dbInstance.selectProducto("1234567A")
    if (selectProducto != null) {
        selectProducto.nombre
        selectProducto.cantidad
        selectProducto.descripcion
    }

    val newProduct: MisProductos = MisProductos("1234567C","Alfombrilla", 400, 50, "Alfombrilla para el ratón")
    dbInstance.insertProducto(newProduct)
}