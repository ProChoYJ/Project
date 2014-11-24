package models

import play.api.db._
import play.api.Play.current

case class Login(val id: String, val password: String)