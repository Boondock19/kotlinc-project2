package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue

public class ApareamientoPerfectoAvido(g: GrafoNoDirigido) : MutableList<Arista> {
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


        this.aristas.forEach {arista ->
           this.l.add(arista)
        }

        var lSorted = this.l.sortedBy { it.peso() }

        while (!(this.v_.isEmpty())) {
            var lado = lSorted.peek()
           lSorted.remove()
            var i = lado.cualquieraDeLosVertices()
            var j = lado.elOtroVertice(i)
            if (this.v_.contains(i) && this.v_.contains(j)) {
                this.m.add(lado)
                this.v_.remove(i)
                this.v_.remove(j)
            }
        }
    }
   
}