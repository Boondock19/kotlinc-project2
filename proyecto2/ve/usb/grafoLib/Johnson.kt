package ve.usb.grafoLib


public class Johnson(val g: GrafoDirigido) {
    var numVerticesG = g.obtenerNumeroDeVertices()
    var listaDeVerticesG = g.listaDeVertices()
    var bellmanFlagNuevoG = false
    var listaArcos = g.arcos().toMutableList()
    var verticesNuevoG = mutableListOf<Int>()
    var arrayH = Array<Double>(this.numVerticesG + 1){0.0}
    var d = Array<Array<Double>>(numVerticesG){Array<Double>(numVerticesG){Double.POSITIVE_INFINITY}}
    var pi = Array<Array<Int?>>(numVerticesG){Array<Int?>(numVerticesG){null}}
    var grafoConPesoCambiados = GrafoDirigido(this.numVerticesG)

    init {
        var n = this.numVerticesG + 1 // nuevo valor de cantidad de vertices x + 1 
        var s = this.numVerticesG // el valor s es el ultimo vertice agregado 
        for (i in 0..n-1) {
            this.verticesNuevoG.add(i)

        }
        // generando lista de arcos del nuevo Grafo
        var listaArcosNuevoG = this.listaArcos
        this.listaDeVerticesG.forEach{ v ->
            var newArco = Arco(s,v,0.0)
            listaArcosNuevoG.add(newArco)
        }
        // construimos el nuevo grafo
        var nuevoG = GrafoDirigido(n,listaArcosNuevoG)
        // obtenemos el bellmanFord del nuevo grafo desde s
        var bellmanFordNuevoG = BellmanFord(nuevoG,s)
        // vemos si el bellmanFord del nuevo grafo tiene ciclo Negativos, (true si tiene, en caso contrario false)
        this.bellmanFlagNuevoG = bellmanFordNuevoG.tieneCicloNegativo()
        // obtenemos el valor de h(v)
        this.verticesNuevoG.forEach{ v -> 
            this.arrayH[v] = bellmanFordNuevoG.costoHasta(v)
        }
        // generamos el nuevo peso y cambiamos el valor del peso de cada arco
        listaArcosNuevoG.forEach{ a -> 
            var  primerV = a.primerV
            if (primerV != s) {
                var segundoV = a.segundoV
                var pesoViejo = a.peso()
                var pesoNuevo = pesoViejo + this.arrayH[primerV] - this.arrayH[segundoV]
                this.grafoConPesoCambiados.agregarArco(Arco(primerV,segundoV,pesoNuevo))
            }
         
        }

        // creamos la matriz arriba y aqui la llenamos
        this.listaDeVerticesG.forEach{ u ->
            var dijkstra = Dijkstra(this.grafoConPesoCambiados,u)
            this.listaDeVerticesG.forEach{ v -> 
                var costoHastaV = dijkstra.costoHasta(v)
                this.d[u][v] = costoHastaV + this.arrayH[v] - this.arrayH[u]
                
            }
        }

    }

  
    /*
        descripcion: Funcion que retorna si el grafo, posee o no un ciclo negativo
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Johnson

        postcondiciones: booleano que indica si existe un ciclo negativo o no

        tiempo de la operacion: O(1) , pero para crear el flag, seria  O(V*E) (invocar algoritmo BellmanFord)

     */
    fun hayCicloNegativo() : Boolean { 
        return this.bellmanFlagNuevoG
    }
    
    
    /*
        descripcion: Funcion que retorna la matriz de distancia de los vertices
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Johnson

        postcondiciones: matriz con las distancias del camino de coste minimo de cada vertice.

        tiempo de la operacion: O(1) por ser asignaciones

     */
    fun obtenerMatrizDistancia() : Array<Array<Double>> { 
        if (!this.hayCicloNegativo()) {
            return this.d
        } else {
            throw Exception("Existe un ciclo negativo en el grafo, por lo que no es posible calcular el camino de menor costo")
        }
    } 
    
   
    /*
        descripcion: Funcion que determina el coste del camino de coste minimo desde u 
        hasta v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Johnson

        postcondiciones: Double que indica el costo de un camino de coste minimo desde u hasta v.

        tiempo de la operacion: O(1) por ser asignaciones y comparaciones.

     */
    fun costo(u: Int, v: Int) : Double { 
           if (!this.hayCicloNegativo()) {
            if (u in this.listaDeVerticesG && v in this.listaDeVerticesG) {   
                return this.d[u][v]
            } else {
                throw Exception("El vertice $v o $u no pertenecen al grafo")
            }
        } else {
            throw Exception("Existe un ciclo negativo en el grafo, por lo que no es posible calcular el camino de menor costo")
        }
    }



    /*
        descripcion: Funcion que determina si existe o no un camino desde el vertice
        u hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Johnson

        postcondiciones: boleano que indica si existe un camino o no desde u hasta v.

        tiempo de la operacion: O(1) por ser asiganciones y comparaciones.

     */
    fun existeUnCamino(u: Int, v: Int) : Boolean { 
        if (!this.hayCicloNegativo()) {
            var hayCamino = false 
            if (u in this.listaDeVerticesG && v in this.listaDeVerticesG) {
                var maxValue = Double.POSITIVE_INFINITY
                var distancia = this.costo(u,v)
                hayCamino = distancia !== maxValue
                return hayCamino
            } else {
                throw Exception("El vertice $v o $u no existen")
            }
        } else {
            throw Exception("Existe un ciclo negativo en el grafo, por lo que no es posible calcular el camino de menor costo")
        }
    }

 

    /*
       descripcion: Funcion que determina obtiene el camino de coste minimo desde s, hasta v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Johnson

        postcondiciones: iterable de arcos que  representa el camino de coste minimo desde s, hasta v.

        tiempo de la operacion: O(V*E) por recorrer los vertices  (y cada uno de los adyacentes de v) hasta s
        en el metodo obtenerCaminoDeCostoMinimo de dijkstra.

     */
    
    fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> { 
        if(!this.hayCicloNegativo()) {
            var caminoArcos = mutableListOf<Arco>()
            if (u in this.listaDeVerticesG && v in this.listaDeVerticesG) {
                var dijkstra = Dijkstra(this.grafoConPesoCambiados,u)
                caminoArcos = dijkstra.obtenerCaminoDeCostoMinimo(v).toMutableList()
                return caminoArcos.asIterable()
            } else {
                throw Exception("El vertice $v o $u no existen")
            }

        } else {
            throw Exception("Existe un ciclo negativo en el grafo, por lo que no es posible calcular el camino de menor costo")
        }
    }
    
}
