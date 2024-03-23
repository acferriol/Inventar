package OID

import play.api.data.Form
import play.api.data.Forms._

object localOID {
    import models.local
  val localForm = Form(
    mapping(
      "cod_local" -> nonEmptyText.verifying("EL formato debe ser locXXX",formato(_)),
      "nombre_local" -> nonEmptyText,
      "tipo_local" -> nonEmptyText.verifying("Tipo de local incorrecto",tipoCorrecto(_)),
    )(local.apply)(local.unapply)
  )

  def tipoCorrecto(tipo:String):Boolean={
    tipo=="laboratorio" || tipo=="oficina" || tipo=="descanso"
  }
  def formato(cod:String):Boolean={
    if(cod.length!=6){ return false }
    if(cod.substring(0,3).toLowerCase!="loc"){ return false }
    val str = cod.substring(3)
    for(c<-str){
      if(c>'9' || c<'0'){ return false }
    }
    true
  }
}
