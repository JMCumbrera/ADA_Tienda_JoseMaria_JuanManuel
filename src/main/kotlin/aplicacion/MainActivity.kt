package aplicacion

import aplicacion.controlador.AppController
import aplicacion.vista.AppVista

fun main(){
    val vista: AppVista = AppVista()
    val controlador : AppController = AppController(vista)

    when(controlador.onStart()){
        1 -> controlador.onInsert()
        2 -> controlador.onAllProducts()
        3 -> controlador.onProducto()
        4 -> controlador.onStockProducts()
        5 -> controlador.onUpdate()
        6 -> controlador.onDelete()
        7 -> controlador.onExit()
    }
}
