package ve.usb.grafoLib

  /*
        descripcion: Funcion que retorna el grafo inverso de un digrafo
         
        precondiciones: que el argumento a pasar sea un digrafo

        postcondiciones: digrafo inverso g^-1 

        tiempo de la operacion: O(V + E) siendo V el numero de vertices y E el numero de lados

     */

fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    var listaDeArcos = g.arcos()
    var listaDeArcosInversos = mutableListOf<Arco>()
    var numDeVertices = g.obtenerNumeroDeVertices()

    listaDeArcos.forEach{ a ->
        val primerVerticeInverso = a.segundoV
        val segundoVerticeInverso = a.primerV
        var arcoInverso = Arco(primerVerticeInverso,segundoVerticeInverso)
        listaDeArcosInversos.add(arcoInverso)
    }
    var digrafoInv = GrafoDirigido(numDeVertices,listaDeArcosInversos)
    
    return digrafoInv
}





