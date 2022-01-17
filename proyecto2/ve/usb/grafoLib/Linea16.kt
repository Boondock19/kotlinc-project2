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
        var gradoi = g.grado(i)
        var gradoj = g.grado(j)

        if (!(v0.contains(i)) && gradoi%2 !=0) {
            v0.add(i)
            numV0 = numV0 + 1
        }
        if (!(v0.contains(j)) && gradoj%2 !=0) {
            v0.add(j)
            numV0 = numV0 + 1
        }
    }

   /*
    if (numV0%2 == 0) {
        throw Exception("El grafo tiene un numero par de vertices, existe un error")
    }
     */
   // println("v0 LUEGO de llenarse ${v0}")
    //Calculamos los caminos de costo minimo con DijkstraNoDirigido
    v0.forEach {i ->
        v0.forEach {j ->
            var dijmod = DijkstraGrafoNoDirigido(g1,i)
            var costoNuevo = dijmod.costoHasta(j)
            if ( i != j ) {
                var aristaNueva = Arista(i,j,costoNuevo)
                listaAux.add(aristaNueva)
            } 
        }
    }
    var grafoFiltro = GrafoNoDirigido2(n1)
    listaAux.forEach { a ->
        grafoFiltro.agregarArista2(a)
    }
   // println("Lista Aux LUEGO del ${listaAux}")
  //  println("grafo filtro LUEGO del ${grafoFiltro}")
    //Armamos el grafo de salida G0
    var aristasFiltro = grafoFiltro.aristas()
    var g0 = GrafoNoDirigido(n1)
    aristasFiltro.forEach {e0 ->
        g0.agregarArista(e0)
    }
    return g0

}