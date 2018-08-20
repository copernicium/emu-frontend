package frontend

fun main(args: Array<String>){
    println("Emulator Frontend Started")
    val a = Network()
    a.startReceiver()
    GUI.run()
}