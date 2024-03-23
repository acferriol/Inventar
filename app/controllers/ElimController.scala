package controllers

import OID.listaOID.lista
import models.listaDel
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._
import services.Consultas
import java.sql.SQLException
import javax.inject._
import scala.collection.mutable.ArrayBuffer


@Singleton
class ElimController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  ///Articulo///
  def eliminarArticulo = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.eliminar.selectArticulo(Consultas.obtenerArticulos(),lista))
  }
  def deleteArticulo: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[listaDel] =>
      NotFound("Error desconocido" + formWithErrors.errors.toString())
    }
    val successFunction={ data:listaDel =>
        val marcas = data.lis
        val arts = Consultas.obtenerArticulos()
        val borrar = new ArrayBuffer[String]()
      for(cont<- 0 to arts.length-1){
          if(marcas(cont)._1){
            borrar+=arts(cont).cod_art
          }
      }
      try{
          Consultas.deleteArticulos(borrar.toList)
          Redirect("/root/eliminar/articulo").flashing("success" -> "Articulos eliminados")
      }
      catch{
          case e:SQLException => Redirect("/root/eliminar/articulo").flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = lista.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  ///Local///
  def eliminarLocal = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.eliminar.selectLocal(Consultas.obtenerLocales(),lista))
  }
  def deleteLocal: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[listaDel] =>
      NotFound("Error desconocido" + formWithErrors.errors.toString())
    }
    val successFunction={ data:listaDel =>
        val marcas = data.lis
        val locales = Consultas.obtenerLocales()
        val borrar = new ArrayBuffer[String]()
      for(cont<- 0 to locales.length-1){
            if(marcas(cont)._1){
              borrar+=locales(cont).cod_local
            }
      }
      try{
          Consultas.deleteLocales(borrar.toList)
          Redirect("/root/eliminar/local").flashing("success" -> "Locales eliminados")
      }
      catch{
          case e:SQLException => Redirect("/root/eliminar/local").flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = lista.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  ///Departamento///
  def eliminarDepartamento = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.eliminar.selectDepartamento(Consultas.obtenerDepartamentos(),lista))
  }
  def deleteDepartamento: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[listaDel] =>
      NotFound("Error desconocido"+formWithErrors.errors.toString())
    }
    val successFunction={data:listaDel =>
        val marcas = data.lis
        val dptos = Consultas.obtenerDepartamentos()
        val borrar = new ArrayBuffer[String]()
      for(cont<- 0 to dptos.length-1){
          if(marcas(cont)._1){
             borrar+=dptos(cont).nombre_dpto
          }
      }
      try{
          Consultas.deleteDepartamentos(borrar.toList)
          Redirect("/root/eliminar/departamento").flashing("success" -> "Departamentos eliminados")
      }
      catch{
          case e:SQLException => Redirect("/root/eliminar/departamento").flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = lista.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }

  ///Trabajador///
  def eliminarTrabajador = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.eliminar.selectTrabajador(Consultas.obtenerTrabajadores(),lista))
  }
  def deleteTrabajador: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[listaDel] =>
      NotFound("Error desconocido"+formWithErrors.errors.toString())
    }
    val successFunction={ data:listaDel =>
        val marcas = data.lis
        val trabs = Consultas.obtenerTrabajadores()
        val borrar = new ArrayBuffer[String]()
      for(cont<- 0 to trabs.length-1){
           if(marcas(cont)._1){
              borrar+=trabs(cont).ct
           }
      }
      try{
           Consultas.deleteTrabajadores(borrar.toList)
           Redirect("/root/eliminar/trabajador").flashing("success" -> "Trabajadores eliminados")
      }
      catch{
           case e:SQLException => Redirect("/root/eliminar/trabajador").flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = lista.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }



}
