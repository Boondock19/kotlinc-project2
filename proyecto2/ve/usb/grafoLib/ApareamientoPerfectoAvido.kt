package ve.usb.grafoLib

import java.util.LinkedList
import java.util.Queue

public class ApareamientoPerfectoAvido(g: GrafoNoDirigido) {
    
    var m = mutableListOf<Arista>()
    var n = g.obtenerNumeroDeVertices()
    var v_ : Queue<Int> = LinkedList<Int>()
    var l : Queue<Arista> = LinkedList<Arista>()
    var aristas = g.aristas()

    init {
    
        this.aristas.forEach {arista ->
            var i = arista.cualquieraDeLosVertices()
            var j = arista.elOtroVertice(i)

            if (!(this.v_.contains(i))) {
                this.v_.add(i)
            }
            if (!(this.v_.contains(j))) {
                this.v_.add(j)
            }
        }

       var aristasSorted = this.aristas.sortedBy { it.peso() }
        aristasSorted.forEach {arista ->
           this.l.add(arista)
        }

        //var lSorted = this.l.sortedBy { it.peso() }
        println("Esta es la cola L antes de entrar en el while : ${this.l}")
        println("Esta es v_ antes de entrar en el while : ${this.v_}")
        while (!(this.v_.isEmpty())) {
            var lado = this.l.peek()
            println("Este es el lado de L en el while : ${lado}")
            this.l.remove(lado)
            var i = lado.primerV
            var j = lado.segundoV
            if (this.v_.contains(i) && this.v_.contains(j)) {
                this.m.add(lado)
                this.v_.remove(i)
                this.v_.remove(j)
                println("Esta es v_ antes de entrar en el while : ${this.v_}")
            }
        }
    }
   
   fun obtenerM() : MutableList<Arista> {
       return this.m
   }
}