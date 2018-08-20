package frontend

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket

class Network {
    companion object {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
        const val HOST = "localhost"
        const val PORT = 11001

        fun deserialize(input: String){
            RobotOutputsManager.instance = GSON.fromJson<RobotOutputs>(input, RobotOutputs::class.java)
        }
    }

    private class ReceiveDataThread: Thread(){
        public override fun run(){
            try{
                var client: Socket? = null
                while(client == null){
                    client = Socket(HOST,PORT)
                    println("Connecting")
                }

                var reader = BufferedReader(InputStreamReader(client.getInputStream()))
                var char: Char
                var message = ""
                while(true) {
                    if (reader.ready()) {
                        var received: Int? = reader.read()

                        if (received == null) {
                            println("Empty")
                        } else {
                            char = received.toChar()
                            if(char == '\u001B'){
                                deserialize(message)
                                message = ""
                                continue
                            }
                            message = message.plus(char.toString())
                        }
                    }
                }
            } catch (e: Exception){
                println(e.message)
            }
        }
    }

    fun receiveData(){
        val thread = ReceiveDataThread()
        thread.start()
        while(true){
            println("Read out: ${GSON.toJson(RobotOutputsManager.instance)}")
        }
    }

    fun sendData(input: String){

    }

    fun serialize(): String{
        return GSON.toJson(RobotInputsManager.instance)
    }
}

fun main(args: Array<String>){
    println("Emulator Frontend Started")
    var a = Network()
    a.receiveData()
}