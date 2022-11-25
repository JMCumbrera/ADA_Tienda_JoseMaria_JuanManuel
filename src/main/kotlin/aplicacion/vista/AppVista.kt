package aplicacion.vista

import aplicacion.modelo.clases.MisProductos

class AppVista {
    fun baseDeDatosCaida() {
println("Base de datos caída")    }

    fun noProdStock() {
        println("No existen productos")
    }

    fun introducirId():String {
        println("Introduce un id")
       return readln().trim().toString()
    }

    fun showProducts(producto: MisProductos) {
        println(" Producto :")
        println("Id : ${producto.id}")
        println("Nombre : ${producto.nombre}")
        println("Descripcion : ${producto.descripcion}")
        println("Precio : ${producto.precio}")
        println("Cantidad : ${producto.cantidad}")
    }

    fun errorGeneral() {
        println("*************ERROR FATAL*******************")
    }

    fun mainMenu() : Int {
        println("Bienvenido elije una opcion")
        println("2. Mostrar 1 producto ")
        println("1. Mostrar productos con stock ")
        println("0. Salir ")
        return readln().trim().toInt()

    }

    fun salir() {
        println(" adios ")
    }
}