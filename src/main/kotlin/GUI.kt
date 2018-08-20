package frontend

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.control.TextArea
import javafx.scene.layout.StackPane
import java.io.File
import java.util.Timer
import java.util.TimerTask


class GUI: Application() {
    private class GUIThread: Thread(){
        override fun run(){
            Application.launch(GUI::class.java)
        }
    }

    companion object {
        private const val STYLESHEET_SOURCE: String = "src/resources/stylesheet.css"

        fun run() {
            val thread = GUIThread()
            thread.start()
        }
    }

    private var stage = Stage()
    private var root = StackPane()
    private var json = TextArea()

    override fun start(primaryStage: Stage?) {
        if (primaryStage != null) {
            stage = primaryStage
            stage.title = "Emulator Frontend"

            root.children.add(json)

            stage.scene = Scene(root, 600.0, 700.0)
            val stylesheet = File(STYLESHEET_SOURCE)
            stage.scene.stylesheets.add("file:///" + stylesheet.absolutePath)
            stage.show()

            update()

            Timer().scheduleAtFixedRate(object : TimerTask(){
                override fun run(){
                    update()
                }
            }, 1000, 2)
        }
    }

    private fun update(){
        json.text = Network.GSON.toJson(RobotOutputsManager.instance)
    }
}