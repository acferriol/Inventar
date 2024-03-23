package OID

import play.api.data.Form
import play.api.data.Forms._
import play.api.data.format.Formats._

object trabajadorOID {
  import models.trabajador

  val trabajadorForm = Form(
    mapping(
      "carnet" -> nonEmptyText.verifying("Carnet de 11 digitos",formatoCarnet(_)),
      "nombre" -> nonEmptyText,
      "apellidos" -> nonEmptyText,
      "direccion"-> text,
      "telefono" -> number,
      "sexo" -> of(charFormat),
      "nombre_departamento" -> text,
      "local de trabajo" -> text,
    )(trabajador.apply)(trabajador.unapply)
  )

  def formatoCarnet(s:String): Boolean ={
    if(s.length!=11){ return false }
    for(c<-s){
      if(c>'9' || c<'0'){ return false }
    }
    true
  }
}
