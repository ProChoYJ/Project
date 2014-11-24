package models

import play.api.db._
import play.api.Play.current

import java.util._
import java.text.SimpleDateFormat

import anorm._
import anorm.SqlParser._

case class Email_input(val recipient: String, val title: String, val content: String)

object Email_input{

	def getArt_date(): String = {
		val time = new Date()
		val format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
		val artTime = format.format(time)
		artTime
	}

	def insert(email: Email_input, sender: String){
		DB.withConnection { implicit connection =>
			val sql = "insert into " + email.recipient + "_email(sender, title, content, date) values " +
						"('" + sender + "'," + "'" + email.title + "'," + "'" + email.content + "'," + "'" + getArt_date + "')"
			println("sql : " + sql)
			SQL(sql).executeInsert()
		}
	}

}