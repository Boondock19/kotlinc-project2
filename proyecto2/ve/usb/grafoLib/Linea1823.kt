package ve.usb.grafoLib

fun Linea1823 (m: MutableList<Arista>, gr:GrafoNoDirigido, grpp: GrafoNoDirigido) : GrafoNoDirigido {
    var mCopia = m
    var grCopia = gr
    var aristasGR = gr.aristas()
    var vrCopia = mutableListOf<Int>()

    m.forEach {lado1 ->
        var vi = lado1.cualquieraDeLosVertices()
        var vj = lado1.elOtroVertice(vi)
        var dijmod = DijkstraGrafoNoDirigido(grpp,vi)
        var ccm = dijmod.obtenerCaminoDeCostoMinimo(vj)
        ccm.forEach {lado2 ->
            if (!(aristasGR.contains(lado2))) {
                grCopia.agregarArista(lado2)
            }
        }
    }
    aristasGR.forEach {arista ->
        var i = arista.cualquieraDeLosVertices()
        var j = arista.elOtroVertice(i)

        if (!(vrCopia.contains(i))) {
            vrCopia.add(i)
        }
        if (!(vrCopia.contains(j))) {
            vrCopia.add(j)
        }
    }
    
    //Verificamos que los vertices en Gr sea una cantidad par
    var numvRC = vrCopia.size
    /* 
    if (numvRC%2 != 0) {
        throw Exception("El grafo no tiene un numero par de vertices")
    }
    */
    
    return grCopia
}