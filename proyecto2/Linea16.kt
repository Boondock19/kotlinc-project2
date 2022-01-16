package ve.usb.grafoLib

//Se tiene como entrada una copia de Gr y el grafo G
fun Linea16 (g: GrafoNoDirigido, g1: GrafoNoDirigido) : GrafoNoDirigido {
    var aristas = g.aristas()
    var v0 = mutableListOf<Int>()
    var numV0 = 0
    var aristas1 = g1.aristas()
    var n1 = g1.obtenerNumeroDeVertices()
    var listaAux = mutableListOf<Arista>()

    //Buscamos los Vertices en R
    aristas.forEach {arista ->
        var i = arista.cualquieraDeLosVertices()
        var j = arista.elOtroVertice(i)

        if (!(v0.contains(i))) {
            v0.add(i)
            numV0 = numV0 + 1
        }
        if (!(v0.contains(j))) {
            v0.add(j)
            numV0 = numV0 + 1
        }
    }

    if (numV0&2 == 0) {
        throw Exception("El grafo tiene un numero par de vertices, existe un error")
    }
    
    //Convertimos el grafo no dirigido original en un grafo dirigido para poder computar Johnson
    //y obtener los caminos de costo minimo
    var gdir = GrafoDirigido(n1)
    aristas1.forEach {lado ->
        var f = lado.cualquieraDeLosVertices()
        var s = lado.elOtroVertice(f)
        var p = lado.peso()
        var arco = Arco(f,s,p)
        gdir.agregarArco(arco)
    }
    var johnson = Johnson(gdir)
    var costos = johnson.obtenerMatrizDistancia()
    
    aristas.forEach {elem ->
        var i = elem.cualquieraDeLosVertices()
        var j = elem.elOtroVertice(i)
        if ((v0.contains(i)) && (v0.contains(j))) {
            var costoNuevo = costo[i][j]
            var aristaNueva = Arista(i,j,costoNuevo)
            listaAux.add(aristaNueva)
        }
    }

    //Armamos el grafo de salida G0
    var g0 = GrafoNoDirigido(n1)
    listaAux.forEach {e0 ->
        g0.agregarArista(e0)
    }
    
    return g0
}