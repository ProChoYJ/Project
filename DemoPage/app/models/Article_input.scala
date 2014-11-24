package models

import play.api.db._
import play.api.Play.current

import java.util.Date
import java.text._

import anorm._
import anorm.SqlParser._


case class Article_input(val art_title: String, val art_content: String)

object Article_input{
	// no need insert, need get
	// val article = {
	// 	get[String]("art_title") ~
	// 	get[String]("art_content") map {
	// 		case art_title~art_content => Article_input(art_title, art_content)
	// 	}
	// }

	def getArt_date(): String = {
		val time = new Date()
		val format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
		val artTime = format.format(time)
		artTime
	}

	def insert(article: Article_input){
		DB.withConnection { implicit connection =>
			// println(getArt_date)
			// println(article.art_title)
			// println(article.art_content)
			val sql = "insert into article(art_date, art_title, art_content) values " +
				 "('" + getArt_date + "','" +article.art_title + "','" + article.art_content + "')";
			println(sql)
			SQL(sql).executeInsert()

		}
	}
}	