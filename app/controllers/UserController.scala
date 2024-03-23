package controllers

import javax.inject._
import play.api._
import play.api.data.Form
import play.api.mvc._
import acceso.Role
import acceso.Role.Data
import services.Consultas


class UserController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  def inicioUser= Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.userInicio())
  }
}
