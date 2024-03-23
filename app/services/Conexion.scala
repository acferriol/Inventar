package services

import java.sql.DriverManager

object Conexion {
  val url = "jdbc:postgresql://localhost:5432/Inventario_DB"
  val user = "postgres"
  val pwd = "alejandro0011"
  var cnx = DriverManager.getConnection(url,user,pwd)

  def Conexion(): Unit ={
    Class.forName("com.postgresql.jdbc.Driver")
    //println("Conexion Establecida")
  }

}
