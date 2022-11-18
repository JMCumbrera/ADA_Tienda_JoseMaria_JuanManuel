fun main() {
    val dbInstance: Gestor = Gestor.getInstance()
    dbInstance.conectarBBDD()
    var selectAll = dbInstance.select()
    selectAll.forEach { println(it) }
    dbInstance.desconectar()
}