package ve.usb.grafoLib

public class Arco(val inicio: Int, val fin: Int, val peso: Double =0.0) : Lado(inicio, fin) {

  //  val arco = Triple(inicio,fin,peso)
   
    var arcoPeso = peso

    /*
        descripcion: Funcion que retorna el valor base de un arco, es decir , 
        retorna el vertice de salida.
         
        precondiciones: que el objeto que invoca al metodo sea un arco

        postcondiciones: entero que representa a la vertice de salida del arco

        tiempo de la operacion: O(1)

     */
    fun fuente() : Int {
        return primerV
    // Completar
    }

     /*
        descripcion: Funcion que retorna el valor final de un arco, es decir , 
        retorna el vertice de llegada.
         
        precondiciones: que el objeto que invoca al metodo sea un arco

        postcondiciones: entero que representa a la vertice de llegada del arco

        tiempo de la operacion: O(1)

     */
    fun sumidero() : Int {
        return segundoV
    // Completar
    }

     /*
        descripcion: Funcion que retorna el valor del peso de un arco 
         
        precondiciones: que el objeto que invoca al metodo sea un arco

        postcondiciones: numero real que representa al peso del arco

        tiempo de la operacion: O(1)

     */
    fun peso() : Double {
        return arcoPeso
    }

      /*
        descripcion: Funcion que retorna el la representacion en string de un arco
         
        precondiciones: que el objeto que invoca al metodo sea un arco

        postcondiciones: string que representa a un arco

        tiempo de la operacion: O(1)

     */
    override fun toString() : String {
        return "($primerV,$segundoV,$peso)"
     }
} 
