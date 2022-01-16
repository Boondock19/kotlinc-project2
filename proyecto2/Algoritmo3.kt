package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue

fun Algoritmo3(g: GrafoNoDirigido) : MutableList<Arista> {
    var m = mutableListOf<Arista>()
    var n = g.obtenerNumeroDeVertices()
    var v_ : Queue<Int> = LinkedList<Int>()
    var e : Queue<Arista> = LinkedList<Arista>()
    var aristas = g.aristas()
    var auxE = mutableListOf<Arista>()
    var auxV = mutableListOf<Int>()

    if (n&2 != 0) {
        throw Exception("El grafo no tiene un numero par de vertices")
    }

    aristas.forEach {arista ->
        var i = arista.cualquieraDeLosVertices()
        var j = arista.elOtroVertice(i)

        if (!(v_.contains(i))) {
            v_.add(i)
            auxV.add(i)
        }
        if (!(v_.contains(j))) {
            v_.add(j)
            auxV.add(i)
        }
    }

    aristas.forEach {arista ->
        auxE.add(arista)
    }

    auxE = auxE.sortedBy { it.peso() }
    auxE.forEach {elem ->
        e.add(elem)
    }

    while (!(v_.isEmpty())) {
        var lado = e.peek()
        var i = lado.cualquieraDeLosVertices()
        var j = lado.elOtroVertice(i)

        m.add(lado)
        v_.remove(i)
        v_.remove(j)

        auxV.forEach {x ->
            var ady = g.adyacentes(x)

            ady.forEach {y ->
                var f = y.primerV
                var s = y.segundoV

                if ((f == i) || (s == i)) {
                    e.remove(y)
                }
                if ((f == j) || (s == j)) {
                    e.remove(y)
                }
            }
        }
    }
    return m
}