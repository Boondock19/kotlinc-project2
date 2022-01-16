package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue

fun Algoritmo2(g: GrafoNoDirigido) : MutableList<Arista> {
    
    var m = mutableListOf<Arista>()
    var n = g.obtenerNumeroDeVertices()
    var v_ : Queue<Int> = LinkedList<Int>()
    var l : Queue<Arista> = LinkedList<Arista>()
    var aristas = g.aristas()

    if (n&2 != 0) {
        throw Exception("El grafo no tiene un numero par de vertices")
    }

    aristas.forEach {arista ->
        var i = arista.cualquieraDeLosVertices()
        var j = arista.elOtroVertice(i)

        if (!(v_.contains(i))) {
            v_.add(i)
        }
        if (!(v_.contains(j))) {
            v_.add(j)
        }
    }

    aristas.forEach {arista ->
        l.add(arista)
    }

    l = l.sortedBy { it.peso() }

    while (!(v_.isEmpty())) {
        var lado = l.peek()
        l.remove()
        var i = lado.cualquieraDeLosVertices()
        var j = lado.elOtroVertice(i)
        if (v_.contains(i) && v_.contains(j)) {
            m.add(lado)
            v_.remove(i)
            v_.remove(j)
        }
    }
    return m
}