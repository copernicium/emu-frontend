package frontend

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.layout.StackPane
import java.io.File
import javafx.scene.control.ListView
import javafx.scene.control.TableView
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import java.net.URL
import java.util.*

class GUI: Application() {
    private class GUIThread : Thread() {
        override fun run() {
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

    private class RoboRIO {
        var pwm_hdrs = ListView<String>()
        var can_motor_controllers = ListView<String>()
    }

    private var roborio = RoboRIO()
    private var stage = Stage()
    private var root = HBox()//TableView<Double>()

    override fun start(primaryStage: Stage?) {
        if (primaryStage != null) {
            stage = primaryStage
            stage.title = "Emulator Frontend"
            primaryStage.setOnCloseRequest {
                Platform.exit()
                System.exit(0)
            }

            roborio.pwm_hdrs.items.add("PWM Headers")
            for (i in 0 until RobotOutputs.NUM_PWM_HDRS) {
                roborio.pwm_hdrs.items.add("0.0")
            }
            root.children.add(roborio.pwm_hdrs)

            roborio.can_motor_controllers.items.add("CAN Motor Controllers")
            root.children.add(roborio.can_motor_controllers)

            //val fxml = File("src/resources/application.fxml")
            //root = FXMLLoader.load(URL("file://" + fxml.absolutePath))

            stage.scene = Scene(root, 600.0, 700.0)
            val stylesheet = File(STYLESHEET_SOURCE)
            stage.scene.stylesheets.add("file:///" + stylesheet.absolutePath)
            stage.show()

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    Platform.runLater { update() }
                }
            }, 1000, 2)
        }
    }

    private fun update() {
        for (i in 0 until RobotOutputsManager.instance.roborio.pwm_hdrs.size) {
            if((i + 1) >= roborio.pwm_hdrs.items.size){
                roborio.pwm_hdrs.items.add(RobotOutputsManager.instance.roborio.pwm_hdrs[i].toString())
            } else {
                roborio.pwm_hdrs.items[i + 1] = RobotOutputsManager.instance.roborio.pwm_hdrs[i].toString() //skip first element (header)
            }
        }

        for (i in 0 until RobotOutputsManager.instance.roborio.can_motor_controllers.size) {
            if((i + 1) >= roborio.can_motor_controllers.items.size) {
                roborio.can_motor_controllers.items.add(RobotOutputsManager.instance.roborio.can_motor_controllers[i].toString())
            } else {
                roborio.can_motor_controllers.items[i + 1] = RobotOutputsManager.instance.roborio.can_motor_controllers[i].toString() //skip first element (header)
            }
        }
    }
}