package controllers

import play.api.mvc._
import javax.inject._

@Singleton
class AdminController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  def inicioRoot= Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.rootInicio())
  }
  def insertar = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.administrar.menuInsertar())
  }
  def modificar = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.administrar.menuModificar())
  }
  def eliminar = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.administrar.menuEliminar())
  }
  def reportes = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.administrar.reportes())
  }
}
