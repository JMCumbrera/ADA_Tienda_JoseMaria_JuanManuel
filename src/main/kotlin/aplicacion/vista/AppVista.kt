package aplicacion.vista

import aplicacion.modelo.clases.MisProductos

class AppVista {
    fun baseDeDatosCaida() { println("Base de datos caída") }

    fun noProdStock() { println("No existen productos") }

    fun nullProductError() { println("[ERROR: Objeto Producto nulo]") }

    fun introducirId(): String {
        println("Introduce un ID: \n")
        return readln().trim().toString()
    }

    fun introducirProducto(): MisProductos {
        var producto: MisProductos? = null
        var id_producto: String = ""
        var nombre_producto: String = ""
        var precio_producto: Int = 0
        var cantidad_producto: Int = 0
        var descripcion_producto: String = ""

        println("Introduzca la ID de su producto: \n")
        id_producto = readln().toString()
        println("\nIntroduzca el nombre de su producto: \n")
        nombre_producto = readln().toString()
        println("\nIntroduzca el precio de su producto: \n")
        precio_producto = readln().toInt()
        println("\nIntroduzca la cantidad de su producto: \n")
        cantidad_producto = readln().toInt()
        println("\nIntroduzca la descripción de su producto: \n")
        descripcion_producto = readln().toString()
        producto = MisProductos(id_producto, nombre_producto, precio_producto, cantidad_producto, descripcion_producto)

        return producto
    }

    fun introducirNombre(): String {
        println("Introduzca un nombre: ")
        return readln().trim()
    }

    fun productoEliminado(producto: MisProductos) {
        println("El producto ${producto.nombre} ha sido eliminado")
    }

    fun productoActualizado(producto: MisProductos) {
        println("El producto ${producto.nombre} ha sido actualizado")
    }
    
    fun showProducts(producto: MisProductos) {
        println("\nProducto: ")
        println("Id: ${producto.id}")
        println("Nombre: ${producto.nombre}")
        println("Descripción: ${producto.descripcion}")
        println("Precio: ${producto.precio}")
        println("Cantidad: ${producto.cantidad}")
    }

    fun errorGeneral() { println("*************ERROR FATAL*************") }

    fun mainMenu(): Int {
        println("Bienvenido, elija una opción: ")
        println("=============================")
        println("1. Insertar un producto")
        println("2. Mostrar todos los productos")
        println("3. Mostrar un producto")
        println("4. Mostrar productos con stock ")
        println("5. Actualizar un producto")
        println("6. Borrar un producto")
        println("7. Salir")
        println("=============================")
        println("\nElija una opción: ")
        return readln().trim().toInt()
    }

    fun salir() { println("¡Hasta la próxima!") }
}
