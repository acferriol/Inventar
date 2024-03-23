package services

import models._

import scala.collection.mutable.ArrayBuffer


object Consultas {
  val cnx = Conexion
  var st = cnx.cnx.createStatement()

  /*SELECTS*/
  def obtenerDepartamentos(): List[departamento] = {
    val res = new ArrayBuffer[departamento]()
    val rt = st.executeQuery("Select * from v_departamento order by nombre_dpto")
    while(rt.next()){
      res += departamento(rt.getString(1),rt.getString(2),rt.getString(3)+" "+rt.getString(4))
    }
    res.toList
  }

  def obtenerHistorial(): List[v_historial] = {
    val res = new ArrayBuffer[v_historial]()
    val rt = st.executeQuery("select * from v_historial order by fecha_baja")
    while(rt.next()){
      res += v_historial(rt.getString(1),rt.getDate(2),rt.getString(3))
    }
    res.toList
  }
  def obtenerResponsabilidad():List[v_responsabilidad] ={
    val res = new ArrayBuffer[v_responsabilidad]()
    val rt = st.executeQuery("select * from v_responsabilidad_material order by cod_art")
    while(rt.next()){
      res += v_responsabilidad(rt.getString(1),rt.getString(2),rt.getString(3)+" "+rt.getString(4))
    }
    res.toList
  }
  def obtenerTrabajadores():List[trabajador] = {
    val res = new ArrayBuffer[trabajador]()
    val rt = st.executeQuery("select * from v_trabajador order by ct")
    while(rt.next()){
      res += trabajador(rt.getString(1),rt.getString(2),rt.getString(3),rt.getString(4),rt.getInt(5),rt.getString(6).charAt(0),rt.getString(7),rt.getString(8))
    }
    res.toList
  }
  def obtenerArticulos():List[articulo]={
    val res = new ArrayBuffer[articulo]()
    val rt = st.executeQuery("select * from articulo order by cod_art")
    while(rt.next()){
      res+= articulo(rt.getString(1),rt.getString(2),rt.getString(3),rt.getString(4),rt.getDouble(5),rt.getDate(6),rt.getDate(7),rt.getString(8),rt.getString(9))
    }
    res.toList
  }
  def obtenerLocales():List[local]={
    val res = new ArrayBuffer[local]()
    val rt = st.executeQuery("select * from locals order by cod_local")
    while(rt.next()){
      res+=local(rt.getString(1),rt.getString(2),rt.getString(3))
    }
    res.toList
  }
  /*UPDATES*/
  def updateDepartamento(name:String,dpt:departamento): Unit ={
    st.executeQuery("update departamento set nombre_dpto='"+dpt.nombre_dpto+"',objetivo='"+dpt.objetivo+"'," +
      "trab_al_frente='"+dpt.lider+"' where nombre_dpto='"+name+"' returning true")
  }
  def updateTrabajador(ct:String,trab:trabajador)={
    val stmt = cnx.cnx.prepareStatement("Update trabajador set ct=?,nombre=?,apellidos=?,direccion=?" +
      ",telefono=?,sexo=?,nombre_dpto=?,loc_trab=? where ct=? returning true")
    stmt.setString(1,trab.ct)
    stmt.setString(2,trab.nombre)
    stmt.setString(3,trab.apellidos)
    stmt.setString(4,trab.direccion)
    stmt.setInt(5,trab.telefono)
    stmt.setObject(6,trab.sexo)
    stmt.setString(7,trab.nombre_dpto)
    stmt.setString(8,trab.loc_trab)
    stmt.setString(9,ct)
    stmt.execute()
  }
  def updateArticulo(coda:String,art:articulo): Unit ={
    val stmt = cnx.cnx.prepareStatement("update articulo set cod_art=?,nombre_art=?,descripcion_art=?,estado_art=?::estado" +
      ",valor_aprox=?,f_ult_mto=?,f_prox_mto=?,responsable=?,ubicacion=? where cod_art=? returning true")
    stmt.setString(1,art.cod_art)
    stmt.setString(2,art.nombre_art)
    stmt.setString(3,art.descripcion_art)
    stmt.setString(4,art.estado_art)
    stmt.setDouble(5,art.valor_aprox)
    stmt.setDate(6,art.f_ult_mto)
    stmt.setDate(7,art.f_prox_mto)
    stmt.setString(8,art.responsable)
    stmt.setString(9,art.ubicacion)
    stmt.setString(10,coda)
    stmt.execute()
  }
  def updateLocal(cod:String,loc:local)={
    st.executeQuery("update locals set cod_local='"+loc.cod_local+"',nombre_local='"+loc.nombre_local+"',tipo_local='"
    +loc.tipo_local+"' where cod_local='"+cod+"'returning true"
    )
  }
  /*AUXILIARES*/
  def idnombre():List[(String,String)]={
    val res = new ArrayBuffer[(String,String)]()
    val cons = obtenerTrabajadores()
    var cp=""
    for(x <- cons) {
      cp = x.nombre+" "+x.apellidos
      res+=(x.ct->cp)
    }
    res.toList
  }
  def dptoNombre():List[(String,String)]={
    val res = new ArrayBuffer[(String,String)]()
    val cons = obtenerDepartamentos()
    for(x <- cons){
      res+=(x.nombre_dpto -> x.nombre_dpto)
    }
    res.toList
  }
  def localCod():List[(String,String)]={
    val res = new ArrayBuffer[(String,String)]()
    val cons = obtenerLocales()
    for(x <- cons){
      res+=(x.cod_local->x.nombre_local)
    }
    res.toList
  }
  /*INSERT*/
  def anadirLocal(local:models.local): Unit ={
    st.executeQuery("INSERT INTO locals(cod_local,nombre_local,tipo_local) VALUES('"+local.cod_local+"','"+local.nombre_local+"','"+local.tipo_local+"') returning true")
  }
  def anadirDpto(dpto:models.departamento): Unit ={
    st.executeQuery("INSERT INTO departamento(nombre_dpto,objetivo,trab_al_frente) VALUES('"+dpto.nombre_dpto+"','"+dpto.objetivo+"','"+dpto.lider+"') returning true")
  }
  def anadirArticulo(art:models.articulo): Unit = {
    st.executeQuery("Insert into articulo(cod_art,nombre_art,descripcion_art,estado_art," +
      "valor_aprox,f_ult_mto,f_prox_mto,responsable,ubicacion)" +
      "VALUES('"+art.cod_art+"','"+art.nombre_art+"','"+art.descripcion_art+"','"+art.estado_art+"',"+art.valor_aprox+",'"
      +art.f_ult_mto.toString+"','"+art.f_prox_mto.toString+"','"+art.responsable+"','"+art.ubicacion+"') returning true")
  }
  def anadirTrabajador(tb:models.trabajador):Unit = {
    st.executeQuery("Insert into trabajador(ct,nombre,apellidos,direccion,telefono,sexo,nombre_dpto,loc_trab)"+
    "VALUES('"+tb.ct+"','"+tb.nombre+"','"+tb.apellidos+"','"+tb.direccion+"',"+tb.telefono+",'"+tb.sexo+"','"+tb.nombre_dpto+"','"+tb.loc_trab+"')returning true"
    )
  }
  /*DELETE*/
  def deleteLocales(lis:List[String]): Unit ={
    val stmt = cnx.cnx.prepareStatement("delete from locals where cod_local=?")
    for(s<-lis){
      stmt.setString(1,s)
      stmt.execute()
      stmt.clearParameters()
    }
  }
  def deleteArticulos(lis:List[String]): Unit ={
    val stmt = cnx.cnx.prepareStatement("delete from articulo where cod_art=?")
    for(s<-lis){
      stmt.setString(1,s)
      stmt.execute()
      stmt.clearParameters()
    }
  }
  def deleteDepartamentos(lis:List[String]): Unit ={
    val stmt = cnx.cnx.prepareStatement("delete from departamento where nombre_dpto=?")
    for(s<-lis){
      stmt.setString(1,s)
      stmt.execute()
      stmt.clearParameters()
    }
  }
  def deleteTrabajadores(lis:List[String]): Unit ={
    val stmt = cnx.cnx.prepareStatement("delete from trabajador where ct=?")
    for(s<-lis){
      stmt.setString(1,s)
      stmt.execute()
      stmt.clearParameters()
    }
  }
  /*BUSQUEDA*/
  def findTrabajador(ci: String):trabajador ={
    val rt = st.executeQuery("select * from trabajador where ct='"+ci+"'")
    rt.next()
    trabajador(rt.getString(1),rt.getString(2),rt.getString(3),rt.getString(4),rt.getInt(5),rt.getString(6).charAt(0),rt.getString(7),rt.getString(8))
  }
  def findDepartamento(name: String):departamento ={
    val rt = st.executeQuery("select * from departamento where nombre_dpto='"+name+"'")
    rt.next()
    departamento(rt.getString(1),rt.getString(2),rt.getString(3))
  }
  def findArticulo(cod: String):articulo ={
    val rt = st.executeQuery("select * from articulo where cod_art='"+cod+"'")
    rt.next()
    articulo(rt.getString(1),rt.getString(2),rt.getString(3),rt.getString(4),rt.getDouble(5),rt.getDate(6),rt.getDate(7),rt.getString(8),rt.getString(9))
  }
  def findLocal(cod: String):local ={
    val rt = st.executeQuery("select * from locals where cod_local='"+cod+"'")
    rt.next()
    local(rt.getString(1),rt.getString(2),rt.getString(3))
  }
}
