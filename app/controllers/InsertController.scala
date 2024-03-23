package controllers

import javax.inject._
import play.api._
import play.api.data.Form
import play.api.mvc._
import models.{articulo, departamento, local, trabajador}
import play.api.mvc.ControllerHelpers._
import services.Consultas
import java.sql.SQLException

@Singleton
class InsertController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {

  ///////////TRABAJADOR////////////
  def nuevoTrabajador = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.insertar.insertarTrabajador(OID.trabajadorOID.trabajadorForm,Consultas.localCod(),Consultas.dptoNombre()))
  }
  def addTrabajador: Action[AnyContent] = Action{implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[trabajador] =>
      BadRequest(views.html.insertar.insertarTrabajador(formWithErrors,Consultas.localCod(),Consultas.dptoNombre()))
    }
    val succesFunction = { data:trabajador =>
      val ct = data.ct
      val nom = data.nombre
      val ape = data.apellidos
      val dir = data.direccion
      val telf = data.telefono
      val sex = data.sexo
      val dnom = data.nombre_dpto
      val lt = data.loc_trab

      try {
          Consultas.anadirTrabajador(trabajador(ct,nom,ape,dir,telf,sex,dnom,lt))
          Redirect("/root/insertar/nuevoTrabajador").flashing("success" -> "Trabajador insertado")
      } catch {
          case e: SQLException => Redirect("/root/insertar/nuevoTrabajador").flashing("error" -> ("Error: "+e.getMessage))
      }
    }
    val formValidationResult = OID.trabajadorOID.trabajadorForm.bindFromRequest()
    formValidationResult.fold(errorFunction,succesFunction)
  }

  ////////LOCAL/////////////
  def nuevoLocal = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.insertar.insertarLocal(OID.localOID.localForm))
  }

  def addLocal: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[local] =>
      BadRequest(views.html.insertar.insertarLocal(formWithErrors))
    }
    val successFunction = { data:local  =>
      val cod = data.cod_local
      val nombre = data.nombre_local
      val tipo = data.tipo_local

      try {
          Consultas.anadirLocal(local(cod,nombre,tipo))
          Redirect("/root/insertar/nuevoLocal").flashing("success" -> "Local creado")
      } catch {
          case e: SQLException => Redirect("/root/insertar/nuevoLocal").flashing("error" -> ("Error: "+e.getMessage))
      }

    }
    val formValidationResult = OID.localOID.localForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  /////////DEPARTAMENTO////////
  def nuevoDepartamento = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.insertar.insertarDpto(OID.departamentoOID.departamentoForm,Consultas.idnombre()))
  }
  def addDepartamento: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[departamento] =>
      BadRequest(views.html.insertar.insertarDpto(formWithErrors,Consultas.idnombre()))
    }
    val successFunction = { data:departamento  =>
      val name = data.nombre_dpto
      val obj = data.objetivo
      val jefe = data.lider

      try {
          Consultas.anadirDpto(departamento(name,obj,jefe))
          Redirect("/root/insertar/nuevoDepartamento").flashing("success" -> "Departamento creado")
      } catch {
          case e: SQLException => Redirect("/root/insertar/nuevoDepartamento").flashing("error" -> ("Error: "+e.getMessage))
      }

    }
    val formValidationResult = OID.departamentoOID.departamentoForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  ///////////ARTICULO///////////
  def nuevoArticulo = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.insertar.insertarArticulo(OID.articuloOID.articuloForm,Consultas.localCod(),Consultas.idnombre()))
  }
  def addArticulo: Action[AnyContent] = Action{implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[articulo] =>
      BadRequest(views.html.insertar.insertarArticulo(formWithErrors,Consultas.localCod(),Consultas.idnombre()))
    }
    val successFunction = { data:articulo  =>
      val ca = data.cod_art
      val na = data.nombre_art
      val da = data.descripcion_art
      val ea = data.estado_art
      val vaa = data.valor_aprox
      val fuma = data.f_ult_mto
      val fpma = data.f_prox_mto
      val respa = data.responsable
      val uba = data.ubicacion

      try {
          Consultas.anadirArticulo(articulo(ca,na,da,ea,vaa,fuma,fpma,respa,uba))
          Redirect("/root/insertar/nuevoArticulo").flashing("success" -> "Articulo insertado")
      } catch {
          case e: SQLException => Redirect("/root/insertar/nuevoArticulo").flashing("error" -> ("Error: "+e.getMessage))
      }
    }
    val formValidationResult = OID.articuloOID.articuloForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
}