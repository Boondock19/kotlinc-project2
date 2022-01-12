package ve.usb.grafoLib

public class Arista(val v: Int,val u: Int, val peso: Double =0.0) : Comparable<Arista>, Lado(v, u) {
     
       // val arista = Triple(v,u,peso)
     
        val aristaPeso = peso

     
     
     /*
        descripcion: Funcion que retorna el valor del peso de una arista 
         
        precondiciones: que el objeto que invoca al metodo sea una arista

        postcondiciones: numero real que representa al peso de la arista

        tiempo de la operacion: O(1)

     */
    fun peso() : Double {
         return aristaPeso
    }

    /*
        descripcion: Funcion que retorna la representacion en string de una arista
         
        precondiciones: que el objeto que invoca al metodo sea un arista

        postcondiciones: string que representa a una arista

        tiempo de la operacion: O(1)

     */
    override fun toString() : String {
        return "($primerV,$segundoV,$aristaPeso)"
    // Completar
    }

   
     /*
        descripcion: Funcion que retorna un entero dependiendo de si el peso
        de la arista actual es mayor,menor o igual en cuyos casos retorna
        1,-1 o 0 respectivamente.
         
        precondiciones: que el objeto que invoca al metodo sea una arista y se le pase otra arista

        postcondiciones: entero que representa que arista pesa mas

        tiempo de la operacion: O(1)

     */
     override fun compareTo(other: Arista): Int {
         val otherPeso = other.peso();
         val aristaPeso = this.peso()
         if (aristaPeso > otherPeso) {
             return 1
         } else if (aristaPeso < otherPeso ) {
             return -1
         } else {
             return 0
         }
     // Completar
     }
} 
