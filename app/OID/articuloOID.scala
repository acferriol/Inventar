package OID

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

object articuloOID {
  import models.articulo
  val articuloForm = Form(
    mapping(
      "codigo" -> nonEmptyText.verifying("El formato es DTXXXX",respetaFormato(_)),
      "nombre" -> nonEmptyText,
      "descripcion" -> nonEmptyText,
      "estado"-> nonEmptyText,
      "valor" -> of(doubleFormat),
      "ultimo_mantenimiento" -> sqlDate,
      "prox_mantenimiento" -> sqlDate,
      "responsable" -> text,
      "ubicacion" -> text,
    )(articulo.apply)(articulo.unapply)
  )
  def respetaFormato(cod:String): Boolean = {
    if(cod.length!=6){ return false }
    if(cod.substring(0,2)!="DT" && cod.substring(0,2)!="dt"){ return false }

    val s = cod.substring(2)
    for(c<-s){
      if(c>'9' || c<'0'){ return false }
    }
    true
  }

}
