package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._


case class Client(val db_id: String, val db_pass: String)

object Client{
	
	val client = {
		get[String]("c_id") ~
		get[String]("c_password") map {
			case c_id ~ c_password => Client(c_id,c_password)
		}

	}

	def getClient(id: String) = DB.withConnection { implicit connection =>
		val sql = "select * from Client where c_id = '" + id + "'"
		SQL(sql).as(client *)
	}


	def matchClient(log_pass: String, cli_pass: String) = {
		if(log_pass == cli_pass)
			false
		else
			true
	}
}