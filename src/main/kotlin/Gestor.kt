import java.sql.*

class Gestor private constructor() {
    companion object {
        private var instance: Gestor? = null

        fun getInstance(): Gestor {
            if (instance == null) {
                instance = Gestor()
            }
            return instance!!
        }
    }

    private val url: String = "jdbc:mysql://localhost/"
    private val bd: String = "tienda"
    private val user: String = "root"
    private val password: String = ""

    @Volatile
    private var conn: Connection? = null

    fun conectarBBDD() {
        if (conn == null) {
            println("[Conexión realizada]")
            conn = DriverManager.getConnection(url+bd, user, password)
        } else {
            println("[Conexión ya existente]")
        }
    }

    fun desconectar() {
        if (conn != null) {
            conn!!.close()
            println("[Desconexión realizada]")
        } else {
            println("[No existe conexión a la BBDD]")
        }
    }
    //private val selectAll = "SELECT * FROM productos;"

    //private val miStatement: Statement = conn?.createStatement() ?:

    /*fun todosLosDatos() {
        val miResultSet = miStatement.executeQuery(selectAll)
        if (miResultSet != null) {

            println("Todos los productos :")
            while (miResultSet.next()) {
                println(miResultSet.getString(1))
                println(miResultSet.getString(2))
                println(miResultSet.getString(3))
                println(miResultSet.getString(4))
                println(miResultSet.getString(5))
                println("------------------------------")
            }
        }
    }*/
    fun select(): MutableMap<String, String> {
        var resultsMap: MutableMap<String, String> = mutableMapOf()

        if (conn != null) {
            // Con la conexión creada, vamos a obtener un objeto Statement
            val st: Statement = conn!!.createStatement()

            // El siguiente paso sería ejecutar una sentencia SQL
            val sentencia: String = "SELECT * FROM productos;"
            val results: ResultSet = st.executeQuery(sentencia)

            while (results.next()) {
                resultsMap["ID"] = results.getString("ID")
                resultsMap["Nombre"] = results.getString("Nombre")
                resultsMap["Precio"] = results.getInt("Precio").toString()
                resultsMap["Cantidad"] = results.getInt("Cantidad").toString()
                resultsMap["Descripcion"] = results.getString("Descripcion")
            }
        }
        return resultsMap
    }
}