package models

import play.api.db._
import play.api.Play.current


case class State(var current_user: String, var error: String)

object State{
	val error1 = "1" //"non-existent ID"
	val error2 = "2" //"wrong Password"

	def init(state: State){
		state.error = ""
	}
}