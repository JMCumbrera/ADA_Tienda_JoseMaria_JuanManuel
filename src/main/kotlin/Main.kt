import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

fun main() {
    var db : miDataBase = miDataBase.getInstance()
    db.conectar()
    db.todosLosDatos()
    db.desconectar(db.conectar())
}


class miDataBase private constructor() {


    private val url: String = "jdbc:mysql://localhost/"
    private val bd: String = "tienda"
    private val user: String = "root"
    private val pass: String = ""

    private val selectAll = "SELECT * FROM productos;"

    private val miStatement: Statement = conectar().createStatement()

    private var conexion: Connection = conectar()


    companion object {

        private var instancia: miDataBase? = null

        fun getInstance(): miDataBase {

            if (instancia == null) {
                instancia = miDataBase()
            }
            return instancia!!
        }
    }

    fun conectar(): Connection {

        if (conexion != null) {

            //Cargar el driver de la BBDD
            Class.forName("com.mysql.cj.jdbc.Driver")

        }
        return try {
            DriverManager.getConnection(url + bd, user, pass)
        } catch (e: SQLException) {
            throw RuntimeException("No se puede establecer una conexion", e)
        }

    }

    fun desconectar(conexion: Connection) {
        try {
            conexion.close()
        } catch (e: SQLException) {
            throw RuntimeException("Error al cerrar la conexion", e)
        }
    }

    fun todosLosDatos() {

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

    }





}