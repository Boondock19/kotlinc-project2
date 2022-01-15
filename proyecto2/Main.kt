import ve.usb.grafoLib.*


fun main(args: Array<String>) {
    println("Prueba un grafo no dirigo con el nuevo formato : \n")
    var archivoALeer = "Instancias_RPP/URPP/UR132"
    var grafoNoDirigidoPrueba = GrafoNoDirigido(archivoALeer,true,true)
   var vertice = 229
   print("\nEste es el grafo generado por el archivo de instacias \n${grafoNoDirigidoPrueba.toString()}")
   print("\nEsta es la cantidad de numVertices \n${grafoNoDirigidoPrueba.obtenerNumeroDeVertices()}")
   print("\nEsta es la cantidad de numLados \n${grafoNoDirigidoPrueba.obtenerNumeroDeLados()}")
   print("\nEsta es la lista de vertices \n${grafoNoDirigidoPrueba.listaDeVertices()}")
   print("\nEsta es la lista de aristas \n${grafoNoDirigidoPrueba.aristas()}")

   print("\nEsta es la lista Adyacencias de ${vertice} \n${grafoNoDirigidoPrueba.adyacentes(vertice)}")
    
   
}
