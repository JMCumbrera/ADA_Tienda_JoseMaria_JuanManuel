package aplicacion.controlador

import aplicacion.modelo.Gestor
import aplicacion.modelo.clases.MisProductos
import aplicacion.vista.AppVista


class AppController(val vista:AppVista) {

    fun onAllProducts(){

        val gestor: Gestor = Gestor.getInstance()

        gestor.conectarBBDD()


        val listaProductos: List<MisProductos>? = gestor.consultarStock()

        if (listaProductos == null){

            vista.baseDeDatosCaida()
        } else if (listaProductos.size == 0){

            vista.noProdStock()
        } else if ( listaProductos.size > 0) {
            for (producto in listaProductos)
            vista.showProducts(producto)
        } else {

            vista.errorGeneral()

        }

    }

    fun onProducto(){

        val gestor = Gestor.getInstance()

        gestor.conectarBBDD()
        // Llamar a la vista para interactuar con el user



        val producto = gestor.selectProducto(AppVista().introducirId())

        if (producto == null){

            vista.noProdStock()
        } else if ( producto != null ) {

        vista.showProducts(producto) }
        else {
            vista.errorGeneral()
        }
    }

    fun onStart() : Int {
        val opcion = vista.mainMenu()
        return opcion
    }

    fun onExit() {
        val gestor = Gestor.getInstance()
        gestor.desconectar()
        vista.salir()
    }
}