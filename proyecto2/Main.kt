import ve.usb.grafoLib.*


fun main(args: Array<String>) {
    println("Prueba un grafo no dirigo con el nuevo formato : \n")
    var archivoALeer = "Instancias_RPP/URPP/UR767"
    var grafoNoDirigidoRPP = GrafoNoDirigido("pruebaInstancia.txt",true,true)
    var grafoNoDirigidoGr = GrafoNoDirigido("pruebaInstancia.txt",true,false,true)
    
    var vertice =3
    
    print("\nEste es el grafo generado por el archivo de instacias \n${grafoNoDirigidoRPP.toString()}")
    print("\nEsta es la cantidad de numVertices \n${grafoNoDirigidoRPP.obtenerNumeroDeVertices()}")
    print("\nEsta es la cantidad de numLados \n${grafoNoDirigidoRPP.obtenerNumeroDeLados()}")
    //print("\nEsta es la lista de vertices \n${grafoNoDirigidoRPP.listaDeVertices()}")
    print("\nEsta es la lista de aristas \n${grafoNoDirigidoRPP.aristas()}")

    print("\nEsta es la lista Adyacencias de ${vertice} en el grafo RRP \n${grafoNoDirigidoRPP.adyacentes(vertice)}")
    
    print("\nEste es el grafo generado por el archivo de instacias \n${grafoNoDirigidoGr.toString()}")
    print("\nEsta es la cantidad de numVertices \n${grafoNoDirigidoGr.obtenerNumeroDeVertices()}")
    print("\nEsta es la cantidad de numLados \n${grafoNoDirigidoGr.obtenerNumeroDeLados()}")
    print("\nEsta es la lista de vertices \n${grafoNoDirigidoGr.listaDeVertices()}")
   print("\nEsta es la lista de aristas \n${grafoNoDirigidoGr.aristas()}")

    print("\nEsta es la lista Adyacencias de ${vertice} en el grafo Gr \n${grafoNoDirigidoGr.adyacentes(vertice)}")

    
    print("\n ESTOS SON LOS VALORES DE LOS ALGORITMO 1")
    var algoritmo1 = HeuristicaRPP("pruebaInstancia.txt")

    /* 
    var grafoDijkstra = GrafoNoDirigido("prueba2.txt",true)
    var dijkstra = DijkstraGrafoNoDirigido(grafoDijkstra,0)
    print("\nEste es el grafo generado por el archivo de dijkstra \n${grafoDijkstra.toString()}")
    println("Costo del camino min desde 0 hasta 1 : ${dijkstra.costoHasta(1)}")
    println("Costo del camino min desde 0 hasta 2 : ${dijkstra.costoHasta(2)}")
    println("Costo del camino min desde 0 hasta 3 : ${dijkstra.costoHasta(3)}")
    println("Costo del camino min desde 0 hasta 4 : ${dijkstra.costoHasta(4)}")
    */
    
}
