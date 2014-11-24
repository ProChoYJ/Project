package models

import play.api.db._
import play.api.Play.current

import java.util._
import java.text.SimpleDateFormat

import anorm._
import anorm.SqlParser._

case class Email(val sender: String, val title: String, val content: String, val date: String)

object Email{
	val email = {
		get[String]("sender") ~
		get[String]("title") ~
		get[String]("content") ~
		get[String]("date") map {
			case sender ~ title ~ content ~ date => Email(sender,title,content,date)
		}
	}

	def getEmail(user: String) = DB.withConnection { implicit connection =>
		val sql = "select * from " + user + "_email order by date desc"
		SQL(sql).as(email.*)		
	}
}

object Counting{
	var num: Int = 0;
	def numcount = {
		num -= 1
		num
	}
	def numinit(size: Int){
		num = size + 1
	}
}