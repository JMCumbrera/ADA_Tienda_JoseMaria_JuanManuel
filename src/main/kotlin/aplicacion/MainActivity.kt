package aplicacion

import aplicacion.controlador.AppController
import aplicacion.vista.AppVista

fun main(){
    val vista: AppVista = AppVista()
    val controlador : AppController = AppController(vista)

    when(controlador.onStart()){
        1 -> controlador.onProducto()
        2 -> controlador.onAllProducts()
        3 -> controlador.onInsert()
        4 -> controlador.onDelete()
        5 -> controlador.onExit()
    }
}
