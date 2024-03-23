package controllers

import javax.inject._
import play.api.data.Form
import play.api.mvc._
import acceso.Role.Data

@Singleton
class LoginController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  def login = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.login(acceso.Role.Roleform))
  }

  def acceder: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.login(formWithErrors))
    }
    val successFunction = { data: Data =>
      val user = data.name
      val password = data.password
      if (user == "root" && password == "root") {
          Redirect(routes.AdminController.inicioRoot)
      } else {
        if (user == "user" && password == "user") {
            Redirect(routes.UserController.inicioUser)
        } else {
            Redirect("/login").flashing("error" -> "Credenciales Incorrectas")
        }
      }
    }

    val formValidationResult = acceso.Role.Roleform.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
}