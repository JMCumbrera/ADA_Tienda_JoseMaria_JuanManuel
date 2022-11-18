data class MisProductos (val id: String, val nombre: String, val precio: Int, val cantidad: Int, val descripcion: String) {
    init {
        require(id.isNotEmpty()) { "La ID no puede estar vacía" }
        require(nombre.isNotEmpty()) { "El nombre no puede estar vacío" }
        require(precio > 0) { "El precio debe ser mayor que 0" }
        require(cantidad >= 0) { "La cantidad no puede ser negativa" }
    }
}