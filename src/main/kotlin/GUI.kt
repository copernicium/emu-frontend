package frontend

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.layout.StackPane
import java.io.File

class GUI: Application() {
    companion object {
        private const val STYLESHEET_SOURCE: String = "src/resources/stylesheet.css"

        fun main(args: Array<String>) {
            Application.launch(GUI::class.java)
        }
    }

    override fun start(primaryStage: Stage?) {
        if(primaryStage != null) {
            primaryStage.title = "Hello World!"

            val btn = Button()
            btn.text = "Test"
            btn.setOnAction{ println("hello")}

            val root = StackPane()
            root.children.add(btn)
            primaryStage.scene = Scene(root, 300.0, 250.0)
            val stylesheet = File(STYLESHEET_SOURCE)
            primaryStage.scene.stylesheets.add("file:///" + stylesheet.absolutePath)
            primaryStage.show()
        }
    }
}