package controllers

import models.{articulo, departamento, local, trabajador}
import org.postgresql.util.PSQLException
import play.api.data.Form
import play.api.mvc.{Action, AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import services.Consultas
import services.Formatos._
import java.sql.SQLException
import javax.inject._

@Singleton
class ModifController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  ///LOCAL///
  def modificarLocal: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.modificar.ListadoLocales(Consultas.obtenerLocales()))
  }
  def detalleLocal(cod:String): Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    try {
        Ok(views.html.detalles.detallesLocal(Consultas.findLocal(cod), OID.localOID.localForm))
    }catch{
        case e:PSQLException => NotFound("El codigo insertado no es valido")
    }
  }
  def updateLocal(cod:String):Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[local] =>
      BadRequest(views.html.detalles.detallesLocal(Consultas.findLocal(cod), formWithErrors))
    }
    val successFunction = { data: local =>
      val codig = data.cod_local
      val nombre = data.nombre_local
      val tipo = data.tipo_local

      try {
          Consultas.updateLocal(cod,local(codig, nombre, tipo))
          Redirect("/root/local/"+codig).flashing("success" -> "Local modificado")
      } catch {
          case e: SQLException => Redirect("/root/local/"+cod).flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = OID.localOID.localForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  ///DEPARTAMENTO///
  def modificarDepartamento: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.modificar.ListadoDepartamentos(Consultas.obtenerDepartamentos()))
  }
  def detalleDepartamento(name:String): Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    try{
        Ok(views.html.detalles.detallesDpto(Consultas.findDepartamento(name),OID.departamentoOID.departamentoForm,liderOrg(name)))
    }catch{
        case e: PSQLException => NotFound("Ha ocurrido un error desconocido "+e.getMessage)
    }
  }
  def updateDepartamento(name:String): Action[AnyContent] =Action{implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[departamento] =>
      BadRequest(views.html.detalles.detallesDpto(Consultas.findDepartamento(name), formWithErrors,liderOrg(name)))
    }
    val successFunction = { data: departamento =>
      val nom = data.nombre_dpto
      val obj = data.objetivo
      val lid = data.lider

      try {
          Consultas.updateDepartamento(name,departamento(nom, obj, lid))
          Redirect("/root/departamento/"+nom).flashing("success" -> "Departamento modificado")
      } catch {
          case e: SQLException => Redirect("/root/departamento/"+name).flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = OID.departamentoOID.departamentoForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
  ///TRABAJADOR///
  def modificarTrabajador: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.modificar.ListadoTrabajadores(Consultas.obtenerTrabajadores()))
  }
  def detalleTrabajador(ct:String): Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    try {
        Ok(views.html.detalles.detallesTrabajador(Consultas.findTrabajador(ct), OID.trabajadorOID.trabajadorForm, dptoOrg(ct), locOrg(ct)))
    }catch{
        case e: PSQLException =>NotFound("Ha ocurrido un error desconocido "+e.getMessage)
    }
  }
  def updateTrabajador(ct:String):Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[trabajador] =>
      BadRequest(views.html.detalles.detallesTrabajador(Consultas.findTrabajador(ct),
        formWithErrors,dptoOrg(ct),locOrg(ct)))
    }
    val succesFunction = { data: trabajador =>
      val ci = data.ct
      val nom = data.nombre
      val ape = data.apellidos
      val dir = data.direccion
      val telf = data.telefono
      val sex = data.sexo
      val dnom = data.nombre_dpto
      val lt = data.loc_trab

      try{
          Consultas.updateTrabajador(ct,trabajador(ci,nom,ape,dir,telf,sex,dnom,lt))
          Redirect("/root/trabajador/"+ci).flashing("success" -> "Trabajador modificado")
      }catch{
          case e:SQLException => Redirect("/root/trabajador/"+ct).flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = OID.trabajadorOID.trabajadorForm.bindFromRequest()
    formValidationResult.fold(errorFunction,succesFunction)
  }
  ///ARTICULO///
  def modificarArticulo: Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.modificar.ListadoArticulos(Consultas.obtenerArticulos()))
  }
  def detalleArticulo(coda:String): Action[AnyContent] = Action{ implicit request: MessagesRequest[AnyContent] =>
    try{
        Ok(views.html.detalles.detallesArticulo(Consultas.findArticulo(coda),OID.articuloOID.articuloForm,respOrg(coda),ubicOrg(coda)))
    }catch{
        case e:PSQLException =>NotFound("Ha ocurrido un error desconocido "+e.getMessage)
    }
  }
  def updateArticulo(coda:String):Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[articulo] =>
      BadRequest(views.html.detalles.detallesArticulo(Consultas.findArticulo(coda),formWithErrors,respOrg(coda),ubicOrg(coda)))
    }
    val successFunction = { data: articulo =>
      val ca = data.cod_art
      val na = data.nombre_art
      val da = data.descripcion_art
      val ea = data.estado_art
      val vaa = data.valor_aprox
      val fuma = data.f_ult_mto
      val fpma = data.f_prox_mto
      val respa = data.responsable
      val uba = data.ubicacion
      val art = articulo(ca,na,da,ea,vaa,fuma,fpma,respa,uba)

      try{
          Consultas.updateArticulo(coda,art)
          Redirect("/root/articulo/"+ca).flashing("success" -> "Articulo modificado")
      }
      catch{
          case e: SQLException => Redirect("/root/articulo/"+coda).flashing("error" -> ("Error: " + e.getMessage))
      }
    }
    val formValidationResult = OID.articuloOID.articuloForm.bindFromRequest()
    formValidationResult.fold(errorFunction, successFunction)
  }
}
