package models

import java.sql.Date


case class articulo(cod_art:String, nombre_art:String, descripcion_art:String, estado_art:String
                   ,valor_aprox:Double, f_ult_mto:Date, f_prox_mto:Date, responsable:String, ubicacion:String)
