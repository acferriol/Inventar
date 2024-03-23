package acceso

import play.api.data.Form
import play.api.data.Forms._

object Role {

  case class Data(name: String, password: String)

  val Roleform = Form(
    mapping(
      "Usuario" -> nonEmptyText,
      "Contrasena" -> nonEmptyText,
    )(Data.apply)(Data.unapply)
  )



}
