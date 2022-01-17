package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue

public class ApareamientoVertexScan(g: GrafoNoDirigido) {
    var m = mutableListOf<Arista>()
    var n = g.obtenerNumeroDeVertices()
    var v_ : Queue<Int> = LinkedList<Int>()
    var e : Queue<Arista> = LinkedList<Arista>()
    var aristas = g.aristas()
    var auxE = mutableListOf<Arista>()
    var auxV = mutableListOf<Int>()

    init {
        /* 
        if (n&2 != 0) {
        throw Exception("El grafo no tiene un numero par de vertices")
        }
        */

        this.aristas.forEach {arista ->
        var i = arista.cualquieraDeLosVertices()
        var j = arista.elOtroVertice(i)

        if (!(this.v_.contains(i))) {
            this.v_.add(i)
            auxV.add(i)
        }
        if (!(this.v_.contains(j))) {
            this.v_.add(j)
            auxV.add(i)
        }
    }

        aristas.forEach {arista ->
            this.auxE.add(arista)
        }

        var lista = this.auxE.sortedBy { it.peso() }
        this.auxE = lista.toMutableList()
        this.auxE.forEach {elem ->
            this.e.add(elem)
        }

        while (!(this.v_.isEmpty())) {
            var lado = this.e.peek()
            var i = lado.cualquieraDeLosVertices()
            var j = lado.elOtroVertice(i)

            this.m.add(lado)
            this.v_.remove(i)
            this.v_.remove(j)

            this.auxV.forEach {x ->
                var ady = g.adyacentes(x)

                ady.forEach {y ->
                    var f = y.primerV
                    var s = y.segundoV

                    if ((f == i) || (s == i)) {
                        this.e.remove(y)
                    }
                    if ((f == j) || (s == j)) {
                        this.e.remove(y)
                    }
                }
            }
        }
    }
    
    fun obtenerM() : MutableList<Arista> {
       return this.m
   }
}