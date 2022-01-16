package ve.usb.grafoLib
import java.io.File

public class Algoritmo1 (instancia:String) {
    var RPP = GrafoNoDirigido(instancia,true,true)
    var Gr = GrafoNoDirigido(instancia,true,false,true)



    // Para que un grafo no dirigido sea conexo, debe ocurrir que para cada vertice, existe al menos un camino a otro
    // asi debemos revisar que exista al menos un adyacente para cada vertice, en caso contrario el grafo no es conexo 

   init {
       var Gp = this.Gr
      
        // verificaremos que Gp sea conexo primero
        println("Es conexo? : ${Gp.esConexo()}")
        println("Es par? : ${Gp.esPar()}")

        // que sea par es que todos sus vertices tengan un grado par, es decir, grado mod 2 = 0
   } 
}