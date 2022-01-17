package ve.usb.grafoLib

fun Linea24 (g: GrafoNoDirigido) : Pair(Iterable<Int,Double){
    
    
    var verticesCE = mutableListOf<Int>()
    var numVertices = g.obtenerNumeroDeVertices()
    var listaAristas = g.aristas()
    var grafo2 = GrafoNoDirigido2(numVertices)
    listaAristas.forEach { a ->
        grafo2.agregarArista2(a)
    }


    var ciclo = CicloEuleriano(grafo2).obtenerCicloEuleriano()
    var costo = CicloEuleriano(grafo2).obtenerCosto()
  //  var aristasCE = funcCE.obtenerCicloEuleriano()

    /* 
    aristasCE.forEach {a ->
        var vertice = a.cualquieraDeLosVertices()
        verticesCE.add(vertice)
    }
    */
    //var mapa = mutableMapOf(ciclo,costo)

     return Pair(ciclo,costo)
}