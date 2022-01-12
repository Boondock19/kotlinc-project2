package ve.usb.grafoLib

/*
 Implementación del Algoritmo de Bellman-Ford para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo. 
 */
public class BellmanFord(val g: GrafoDirigido, val s: Int) {

    var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
    var listaDeArcos = g.arcos()
    var numVertices = g.obtenerNumeroDeVertices()
    var vertices = mutableListOf<Int>()
    var  bellmanFlag = true
    
    /*
        descripcion: Init del algoritmo Bellman-Ford.
         
        precondiciones: que el objeto que invoca al metodo sea un grafo dirigido y s sea un vertice 
        que pertenezca al grafo.

        postcondiciones: Camino de costo minimo para los vertices del grafo respecto al vertice s.

        tiempo de la operacion: O(VE)

     */
    
    init {
	
    // Se ejecuta bellmanFord
        for (i in 0..this.numVertices-1 ) {
            this.vertices.add(i)
        }

        this.listaDeVertices = inicializarFuenteFija(g,s)
        for( i in 1..this.numVertices-1) {
            this.listaDeArcos.forEach{ a ->
                var uDistancia = this.listaDeVertices.get(a.primerV).get("distancia") as Double
                var vDistancia = this.listaDeVertices.get(a.segundoV).get("distancia") as Double
                var weight = a.peso()
                var vPredecesor = 0

                if (vDistancia > uDistancia + weight) {
                    vDistancia = uDistancia + weight
                    vPredecesor = a.primerV
                    this.listaDeVertices.get(a.segundoV).put("distancia",vDistancia)
                    this.listaDeVertices.get(a.segundoV).put("predecesor",vPredecesor)
                }
            }
        }
        this.listaDeArcos.forEach{ a->
            var uDistancia = this.listaDeVertices.get(a.primerV).get("distancia") as Double
            var vDistancia = this.listaDeVertices.get(a.segundoV).get("distancia") as Double
            var weight = a.peso()

            if (vDistancia > uDistancia + weight) {
                this.bellmanFlag = false
                this.listaDeVertices.get(a.segundoV).put("existeCamino",false)
            }
        }
}
    

    /*
        descripcion: Funcion que retorna si el grafo, posee o no un ciclo negativo
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase BellmanFord

        postcondiciones: booleano que indica si existe un ciclo negativo o no

        tiempo de la operacion: O(1) , pero para crear el flag, seria  O(E) (esto ocurre en el init)

     */
    fun tieneCicloNegativo() : Boolean { 
        var flag = false
        if (!this.bellmanFlag) {
            flag = true     
        }

        return flag
    }

    
    /*
        descripcion: Funcion que retorna el ciclo negativo de un digrafo, si es que existe
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase BellmanFord

        postcondiciones: iterable de arcos que representa al ciclo negativo

        tiempo de la operacion: O(V+E) porque utilizamos CFC

     */
    fun obtenerCicloNegativo() : Iterable<Arco> { 
        var cfc = CFC(g)
        var listaDeComponentes = cfc.obtenerCFC()
        var pesoFinal = 0.0
        var numComponentes = cfc.numeroDeCFC()
        var listaCicloNegativo = mutableListOf<Arco>()
        var listaCicloRetorno = mutableListOf<Arco>()

        if (this.tieneCicloNegativo()) {
           listaDeComponentes.forEach { lista ->
                var set = lista
                set.forEach { v ->
                    var identificadorV = cfc.obtenerIdentificadorCFC(v)
                    var adyacentesV = g.adyacentes(v)
                    adyacentesV.forEach{ arco -> 
                        var identificadorSumidero = cfc.obtenerIdentificadorCFC(arco.segundoV)
                        if(identificadorV == identificadorSumidero) {
                            pesoFinal = pesoFinal + arco.peso()
                            listaCicloNegativo.add(arco)
                        }
                    }
                }
                if (pesoFinal < 0.0){
                    pesoFinal = 0.0
                    listaCicloRetorno = listaCicloNegativo
                } else {
                    pesoFinal = 0.0
                    listaCicloNegativo = mutableListOf<Arco>()
                }
            }
        }

        return listaCicloRetorno.asIterable()
    }

    
     /*
        descripcion: Funcion que determina si existe o no un camino desde el vertice
        s hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase BellmanFord

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
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase BellmanFord

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
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase BellmanFord

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

    /*
        descripcion: Funcion que retorna la lista de inicializacion de un grafo
        a partir del procedimiento inicializar fuente fija.

        precondiciones: que el argumento a pasar sea un digrafo

        postcondiciones: digrafo inverso g^-1 

        tiempo de la operacion: O(V + E) siendo V el numero de vertices y E el numero de lados

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

}
