package frontend

fun main(args: Array<String>){
    println("Emulator Frontend Started")
    Network.startReceiver()
    GUI.run()
}