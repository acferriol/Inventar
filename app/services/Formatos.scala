package services

object Formatos {
  ///*AUXILIARES*////
  def liderOrg(name:String): List[(String,String)]={
    val d = Consultas.findDepartamento(name)
    val lid = (d.lider -> (Consultas.findTrabajador(d.lider).nombre+" "+Consultas.findTrabajador(d.lider).apellidos))
    var lis = List(lid)
    lis++=Consultas.idnombre()
    lis.distinct
  }
  def dptoOrg(ct:String):List[(String,String)]={
    val t = Consultas.findTrabajador(ct)
    val dt = (t.nombre_dpto->t.nombre_dpto)
    var lis = List(dt)
    lis++=Consultas.dptoNombre()
    lis.distinct
  }
  def locOrg(ct:String):List[(String,String)]={
    val t = Consultas.findTrabajador(ct)
    val lt = (t.loc_trab->Consultas.findLocal(t.loc_trab).nombre_local)
    var lis = List(lt)
    lis++=Consultas.localCod()
    lis.distinct
  }
  def respOrg(coda:String): List[(String,String)]={
    val art = Consultas.findArticulo(coda)
    val resp = (art.responsable->(Consultas.findTrabajador(art.responsable).nombre+" "+ Consultas.findTrabajador(art.responsable).apellidos))
    var lis = List(resp)
    lis++=Consultas.idnombre()
    lis.distinct
  }
  def ubicOrg(coda:String): List[(String,String)] ={
    val art = Consultas.findArticulo(coda)
    val ubic = (art.ubicacion->Consultas.findLocal(art.ubicacion).nombre_local)
    var lis = List(ubic)
    lis++=Consultas.localCod()
    lis.distinct
  }
}
