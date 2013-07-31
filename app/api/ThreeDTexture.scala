package api

import play.api.mvc.Controller
import play.api.mvc.Action
import models.ThreeDTextureDAO
import java.io.FileInputStream
import play.api.libs.json.Json._
import play.api.Logger

object ThreeDTexture extends Controller {
  
    /**
   * Upload a 3D texture file.
   */  
  def uploadTexture() = 
    Authenticated {
    Action(parse.multipartFormData) { implicit request =>
      request.body.file("File").map { f =>        
        Logger.info("Uploading 3D texture file " + f.filename)
        // store file
        val id = ThreeDTextureDAO.save(new FileInputStream(f.ref.file), f.filename, f.contentType)
        Ok(toJson(Map("id"->id)))   
      }.getOrElse {
         BadRequest(toJson("File not attached."))
      }
    }
  }

}