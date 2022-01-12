package ve.usb.grafoLib

/*
 Implementación del algoritmo de Dijkstra para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo. La
 implementación debe usar como cola de prioridad un min-heap.
 Si el grafo de entrada tiene un lado con costo negativo, 
 entonces se retorna un RuntimeException.
 */
public class Dijkstra(val g: GrafoDirigido, val s: Int) {

    var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
    var listaDeArcos = g.arcos()
    var numVertices = g.obtenerNumeroDeVertices()
    var vertices = mutableListOf<Int>()
    var listaCaminoMinimo = mutableListOf<Int>()
    var weights:MutableMap<Int, Double>  = mutableMapOf()

    init {
        
       /*
        descripcion: Init del algoritmo de Dijkstra
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Dijkstra

        postcondiciones: camino de coste minimos desde s hasta los vertices del grafo

        tiempo de la operacion: O((V + E) log(V)) = O(E log (V)) por utilizar min-heap
 
     */
        // Se ejecuta Disjktra
        // Inicializar fuente fija para la Cola
        for (i in 0..this.numVertices-1 ) {
            this.vertices.add(i)
            weights.put(i, Double.POSITIVE_INFINITY)
            
        }
        weights.put(s, 0.0)
        val queue = g.listaDeVertices().toMutableList()
        this.listaDeVertices = inicializarFuenteFija(g,s)
        heap(queue)

        while (queue.size != 0) {
            val u = extraerMin(queue)
            listaCaminoMinimo.add(u)
            g.adyacentes(u).forEach{ a ->
                //relajacion
                var uDistancia = this.weights.get(a.primerV) as Double
                var vDistancia = this.weights.get(a.segundoV) as Double
                var weight = a.peso()
                var vPredecesor = 0
                var newPeso = uDistancia + weight

                if (vDistancia > newPeso) {
                    vDistancia = newPeso
                    vPredecesor = a.primerV
                    this.listaDeVertices.get(a.segundoV).put("distancia",vDistancia)
                    this.weights.put(a.segundoV,vDistancia)
                    this.listaDeVertices.get(a.segundoV).put("predecesor",vPredecesor)
                    decrementarPeso(queue, a.segundoV)
                }
            }
        }

    }

    /*
        descripcion: Funcion que determina si existe o no un camino desde el vertice
        s hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Dijkstra

        postcondiciones: boleano que indica si existe un camino o no desde s hasta v.

        tiempo de la operacion: O(1) por ser asiganciones y comparaciones.

     */

   fun existeUnCamino(v: Int) : Boolean { 
        if (v in this.vertices) {
            var existeCamino = this.listaDeVertices.get(v).get("distancia") as Double
            var maxValue = Double.MAX_VALUE
            var flagE = existeCamino !== maxValue  // SI son distintos devuelve true, si son iguales devuelve false
            return flagE  
        } else {
            throw Exception("El vertice $v no existe en el grafo")
        }
    }

      /*
        descripcion: Funcion que determina la distacia minima de un vertice v, respecto al vertice s
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Dijkstra

        postcondiciones: double que representa la distancia minima de v, hasta s.

        tiempo de la operacion: O(1) por ser asiganciones y comparaciones.

     */

    fun costoHasta(v: Int) : Double { 
        var distanciaCorta = 0.0
        if ( v in this.vertices) {
            distanciaCorta = this.listaDeVertices.get(v).get("distancia") as Double
            return distanciaCorta
        } else {
            throw Exception("El vertice $v no existe en el grafo")
        }
    }

    /*
        descripcion: Funcion que determina obtiene el camino de coste minimo desde s, hasta v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase Dijkstra

        postcondiciones: iterable de arcos que  representa el camino de coste minimo desde s, hasta v.

        tiempo de la operacion: O(V) por recorrer los vertices hasta s.

     */
   fun obtenerCaminoDeCostoMinimo(v: Int) : Iterable<Arco> { 
        var listaCamino = mutableListOf<Arco>()
        var verticeAnterior = v
        var fuenteAnterior = 0
       if (v in this.vertices){
           if(this.existeUnCamino(v)) {
                while(this.listaDeVertices.get(verticeAnterior).get("predecesor") as Int != s){
                var proximoVertice = this.listaDeVertices.get(verticeAnterior).get("predecesor") as Int
                var newArco = Arco(proximoVertice,verticeAnterior)
                listaCamino.add(newArco)
                verticeAnterior = proximoVertice
                }
                var ultimoArco = Arco(s,verticeAnterior)
                listaCamino.add(ultimoArco)
                var listaInversa = listaCamino.reversed()
                return  listaInversa.asIterable()
           } else {
                return  listaCamino.asIterable()
           }
           
       } else {
           throw Exception("El vertice $v no se encuentra en el grafo")
       }
   }


    
    fun minHeapify (queue:MutableList<Int>, i:Int, n:Int) {
        val left:Int = 2*i
        val right:Int = 2*i + 1
        val iW:Double =  weights.get(queue.get(i)) as Double
        var min:Int?
        if(left<= n && (weights.get(queue.get(left)) as Double )< iW ){
              min = left
            }
        else{
            min = i
        }

        val minW = weights.get(queue.get(min)) as Double
        if(right <= n && (weights.get(queue.get(right)) as Double) < minW )
            min = right
        if(min != i )
        {
            queue.set(i, queue.get(i) + queue.get(min))
            queue.set(min, queue.get(i) - queue.get(min))
            queue.set(i, queue.get(i) - queue.get(min))
            minHeapify(queue, min, n)
        } 
     }

     /*
        descripcion: Funcion que retorna la lista de inicializacion de un grafo
        a partir del procedimiento inicializar fuente fija.

        precondiciones: que el argumento a pasar sea un digrafo

        postcondiciones: lista con valores definidos de distancia y predecesores, y
        estado inicial de s. 

        tiempo de la operacion: O(V) 

     */
    
    fun inicializarFuenteFija(g: GrafoDirigido,s: Int) : MutableList<MutableMap<String, Any?>> {
        var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
        var numVertices = g.obtenerNumeroDeVertices()
        var distancia : Int 
            for (i in 0..numVertices-1 ) {
                listaDeVertices.add(mutableMapOf(
                    "distancia" to Double.MAX_VALUE,
                    "predecesor" to null,
                    "existeCamino" to true,
                ))
            }
       
       listaDeVertices.get(s).put("distancia",0.0)
        
        return listaDeVertices
}
    
    fun heap(queue:MutableList<Int>)  {
        for(i in queue.size/2 downTo 0){
            minHeapify (queue, i, queue.size-1)
        }
    }


     fun extraerMin(queue:MutableList<Int>):Int {
        val u = queue.get(0)
        queue.set(0, queue.get(queue.size-1))
        queue.removeAt(queue.size-1)
        if (queue.size != 0) { 
            minHeapify(queue, 0, queue.size-1)
        }
        return u
    }

    fun decrementarPeso(queue:MutableList<Int>, v:Int) {
        var i = queue.indexOf(v)
        while(i>0 && weights.get(queue.get(i/2)) as Double > weights.get(queue.get(i)) as Double) {
            
            queue.set(i, queue.get(i) + queue.get(i/2))
            queue.set(i/2, queue.get(i) - queue.get(i/2))
            queue.set(i, queue.get(i) - queue.get(i/2))
            i = i/2
        }
    }
}
