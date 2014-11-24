package models

import java.io.File

class Files(val name: String)

object Files{
 val path =  "/home/john/Document/"
 val files = new File(path)

 def printList = {
 	val list = for(a <- files.listFiles()) yield new Files(a.getName())
 	list
 }
}