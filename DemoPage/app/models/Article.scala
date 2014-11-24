package models

import play.api.db._
import play.api.Play.current

import java.util.Date
import java.text.SimpleDateFormat

import anorm._
import anorm.SqlParser._

case class Article(val art_date: String, val art_title: String, val art_content: String)

object Article{
	val articles = {
		get[String]("art_date") ~
		get[String]("art_title") ~
		get[String]("art_content") map {
			case art_date ~ art_title ~ art_content => Article(art_date, art_title, art_content)
		}
	}

	def getArticles = DB.withConnection { implicit connection =>
		SQL( "select * from article order by art_date desc" ).as(articles.*)		

	}
}

object Change{
	def getArt_date(artTime: String) = {
		val format = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
		val format2 = new SimpleDateFormat("yyyy/MM/dd")
		val d = format.parse(artTime)
		val time = format2.format(d)
		time
	}
	//def getcontent -> 90byte -> <p></p>

}