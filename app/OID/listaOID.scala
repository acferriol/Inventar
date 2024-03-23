package OID

import models.listaDel
import play.api.data.Form
import play.api.data.Forms._

object listaOID {

  val lista = Form(
    mapping(
      "sels" -> list(
        tuple(
          "sel"->boolean,
          "index"-> number
        )
      )
    )(listaDel.apply)(listaDel.unapply)
  )

}
