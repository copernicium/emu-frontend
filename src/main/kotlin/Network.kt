package frontend

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class Network {
    companion object {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
        const val JSON_PACKET_SUFFIX = '\u001B'
        const val HOST = "localhost"
        const val RECEIVE_PORT = 11001
        const val TRANSMIT_PORT = 11000

        private fun deserialize(input: String) {
            RobotOutputsManager.instance = GSON.fromJson<RobotOutputs>(input, RobotOutputs::class.java)
        }

        private fun serialize(): String {
            return GSON.toJson(RobotInputsManager.instance)
        }

        private class ReceiveDataThread : Thread() {
            override fun run() {
                try {
                    var client: Socket? = null
                    while (client == null) {
                        client = Socket(HOST, RECEIVE_PORT)
                        println("Connecting...")
                    }
                    println("Connected")

                    val reader = BufferedReader(InputStreamReader(client.getInputStream()))
                    var char: Char
                    var message = ""
                    var received: Int?
                    while (true) {
                        if (reader.ready()) {
                            received = reader.read()

                            if (received != null) {
                                char = received.toChar()
                                if (char == JSON_PACKET_SUFFIX) {
                                    deserialize(message)
                                    message = ""
                                    continue
                                }
                                message = message.plus(char.toString())
                            }
                        }
                    }
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }

        private class SendDataThread : Thread() {
            override fun run() {
                try {
                    var server: Socket? = null
                    while (server == null) {
                        server = Socket(HOST, TRANSMIT_PORT)
                        println("Connecting...")
                    }
                    println("Connected")

                    //TODO
                } catch (e: Exception) {
                    println(e.message)
                }
            }
        }

        fun startReceiver() {
            val thread = ReceiveDataThread()
            thread.start()
        }

        fun startTransmitter() {
            val thread = ReceiveDataThread()
            thread.start()
        }
    }
}