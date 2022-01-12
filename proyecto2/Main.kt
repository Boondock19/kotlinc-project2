import ve.usb.grafoLib.*


fun main(args: Array<String>) {
    println("Prueba de la metodos de Johnson un grafo dirigido : \n")
    var grafoDirigidoPrueba = GrafoDirigido("jonhson.txt",true)
    println("ESTE ES EL DIGRAFO DE Johnson : ${grafoDirigidoPrueba}")
    println("ESTE ES LA LISTA DE ARCOS DIGRAFO DE Johnson : ${grafoDirigidoPrueba.arcos()}")
    
   var pruebaJonhson = Johnson(grafoDirigidoPrueba)
    println("Existe un ciclo Negativo ? ${pruebaJonhson.hayCicloNegativo()}")
    println("Matriz de distancias ${pruebaJonhson.obtenerMatrizDistancia()}")
    var w = pruebaJonhson.obtenerMatrizDistancia()
    // Displaying the result
    println("Este es la matriz d  : ")
    for (row in w) {
        for (column in row) {
            print("$column    ")
        }
        println()
    }

    println("Existe un camino desde 4 hasta 0: ${pruebaJonhson.existeUnCamino(4,0)}")
    println("Existe un camino desde 4 hasta 0: ${pruebaJonhson.costo(4,0)}")
    println("Camino de coste minimo desde 4 hasta 0: ${pruebaJonhson.obtenerCaminoDeCostoMinimo(4,0)}")
   
}
