package aplicacion.controlador

import aplicacion.modelo.Gestor
import aplicacion.modelo.clases.MisProductos
import aplicacion.vista.AppVista

class AppController(val vista: AppVista) {
    fun onStockProducts() {
        val gestor: Gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        val listaProductos: List<MisProductos>? = gestor.consultarStock()

        when {
            (listaProductos == null) -> { vista.baseDeDatosCaida() }
            (listaProductos.isEmpty()) -> { vista.noProdStock() }
            (listaProductos.size > 0) -> { for (producto in listaProductos) vista.showProducts(producto) }
            else -> { vista.errorGeneral() }
        }
    }

    fun onAllProducts() {
        val gestor: Gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        val listaProductos: List<MisProductos>? = gestor.selectAll()

        when {
            (listaProductos == null) -> { vista.baseDeDatosCaida() }
            (listaProductos.isEmpty()) -> { vista.noProdStock() }
            (listaProductos.size > 0) -> { for (producto in listaProductos) vista.showProducts(producto) }
            else -> { vista.errorGeneral() }
        }
    }

    fun onProducto() {
        val gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        // Llamar a la vista para interactuar con el user
        val producto = gestor.selectProducto(AppVista().introducirId())

        when {
            (producto == null) -> { vista.noProdStock() }
            (producto != null) -> { vista.showProducts(producto) }
            else -> { vista.errorGeneral() }
        }
    }

    fun onDelete() {
        val gestor: Gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        val producto = gestor.selectProducto(AppVista().introducirId())

        when {
            (producto == null) -> { vista.noProdStock() }
            (producto != null) -> {
                gestor.eliminarProducto(AppVista().introducirNombre())
                vista.productoEliminado(producto)
            }
            else -> { vista.errorGeneral() }
        }
    }

    fun onUpdate() {
        val gestor: Gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        val producto = gestor.updateProducto(AppVista().introducirProducto())

        when {
            (producto == null) -> { vista.nullProductError() }
            (producto != null) -> { vista.productoActualizado(producto) }
            else -> { vista.errorGeneral() }
        }
    }
    
    fun onInsert() {
        val gestor = Gestor.getInstance()
        gestor.conectarBBDD()

        val producto = gestor.insertProducto(AppVista().introducirProducto())

        when {
            (producto == null) -> { vista.nullProductError() }
            (producto != null) -> { vista.showProducts(producto) }
            else -> { vista.errorGeneral() }
        }
    }

    fun onStart(): Int {
        val opcion = vista.mainMenu()
        return opcion
    }

    fun onExit() {
        val gestor = Gestor.getInstance()
        gestor.desconectar()
        vista.salir()
    }
}
