package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue

fun Algoritmo2(g: GrafoNoDirigido) : MutableList<Arista> {
    var m = mutableListOf<Arista>()
    var n = g.obtenerNumeroDeVertices()
    var v_ : Queue<Int> = LinkedList<Int>()
    var l : Queue<Arista> = LinkedList<Arista>()
    var aristas = g.aristas()

    for (i in 0..n-1) {
        v_.add(i)
    }

    aristas.forEach {arista ->
        l.add(arista)
    }

    var lSorted = l.sortedBy { it.peso() }

    while (!(v_.isEmpty())) {
        var lado = lSorted.peek()
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