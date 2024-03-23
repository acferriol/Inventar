package OID

import play.api.data.Form
import play.api.data.Forms._

object departamentoOID {
  import models.departamento

  val departamentoForm = Form(
    mapping(
      "nombre" -> nonEmptyText,
      "objetivo" -> nonEmptyText,
      "al frente" -> nonEmptyText,
    )(departamento.apply)(departamento.unapply)
  )
}
