package controllers

import play.api.mvc._
import services.Consultas

import javax.inject._

@Singleton
class ReportesController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  def reporteDepartamentosUser = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_dpt("/user",Consultas.obtenerDepartamentos()))
  }
  def reporteHistorialUser = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_historial("/user",Consultas.obtenerHistorial()))
  }
  def reporteResponsabilidadUser = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_resp("/user",Consultas.obtenerResponsabilidad()))
  }
  def reporteTrabajadoresUser = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_trabajadores("/user",Consultas.obtenerTrabajadores()))
  }
  def reporteDepartamentosRoot = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_dpt("/root/reportes",Consultas.obtenerDepartamentos()))
  }
  def reporteHistorialRoot = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_historial("/root/reportes",Consultas.obtenerHistorial()))
  }
  def reporteResponsabilidadRoot = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_resp("/root/reportes",Consultas.obtenerResponsabilidad()))
  }
  def reporteTrabajadoresRoot = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.visualizar.v_trabajadores("/root/reportes",Consultas.obtenerTrabajadores()))
  }
}
