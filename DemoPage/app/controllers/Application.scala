package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation._
import play.api.mvc.Controller

import anorm._

import models._

object Application extends Controller {

  var current = State("","")
  val title = "myPage"
  
  def index = Action {
    println("State_id : " + current.current_user)
    println("State_error : " + current.error)
    
    Ok(views.html.index(title)(views.html.first(Email.getEmail(current.current_user)))(views.html.content(Article.getArticles))(views.html.footer())(views.html.third(Files.printList))(current))
      
  }


  // article
  // form need insert, not select
  val article_input_form = Form(
  		mapping("art_title" -> text, "art_content" -> text)(Article_input.apply)(Article_input.unapply)
  	)

  val article_form = Form(
      mapping("art_date" -> text, "art_title" -> text, "art_content" -> text)(Article.apply)(Article.unapply)
    )

  def art_save = Action { implicit request =>
  	article_input_form.bindFromRequest.fold (
  	    formWithErrors => BadRequest( "You need to pass a 'xxx' value!" ),
  	    article => { 
  	    	println("insert ready")
  	    	Article_input.insert(article)
  	    	println("insert!")
  	    	Redirect( routes.Application.index() )
  	    }
  	)		

  }

    // login
  val login_form = Form(
      mapping("c_id" -> text, "c_password" -> text)(Login.apply)(Login.unapply)
    )

  def userLogin = Action { implicit request =>
    login_form.bindFromRequest.fold (
        formWithErrors => BadRequest(""),
        login => {
          val list_Client = Client.getClient(login.id)
          if(list_Client.isEmpty){
            current = State("",State.error1)
          }else if(Client.matchClient(login.password, list_Client(0).db_pass)){
            current = State("",State.error2)
          }else{
            current = State(login.id,"")
          }
          println("State_id : " + current.current_user)
          println("State_error : " + current.error)
          Ok(views.html.index(title)(views.html.first(Email.getEmail(current.current_user)))(views.html.content(Article.getArticles))(views.html.footer())(views.html.third(Files.printList))(current))

        }
      )
  }

  //logout

  def logout = Action {
    current = State("","")
    Redirect( routes.Application.index() )
  }

  // email
  val email_input_form = Form(
      mapping("recipient" -> text, "title" -> text, "content" -> text)(Email_input.apply)(Email_input.unapply)
    )



  def email_send = Action { implicit request =>
    email_input_form.bindFromRequest.fold (
        formWithErrors => BadRequest( "You need to pass a 'xxx' value!" ),
        email => { 
          val list_Client = Client.getClient(email.recipient)
          if(list_Client.isEmpty){
            current.error = State.error1
            Redirect( routes.Application.index() )
          }else{
            current.error = ""
            Email_input.insert(email, current.current_user)
            Redirect( routes.Application.index() )  
          }
          
        }
    )   

  }

  //file

  def upload = Action(parse.multipartFormData){ request =>
    request.body.file("up_file").map{ up_file =>
      import java.io.File
      val filename = up_file.filename
      val contentype = up_file.contentType
      up_file.ref.moveTo(new File("/home/john/Document/" +filename))
      Redirect(routes.Application.index)
    }getOrElse{
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file"
      )
    } 
  }

  def serve(filename: String) = Action{
    Ok.sendFile(
        content = new java.io.File("/home/john/Document/" + filename)
      )
    
  }


}