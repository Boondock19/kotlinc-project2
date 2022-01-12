package ve.usb.grafoLib

/*
 Implementación del algoritmo para encontrar los
 caminos de costo mínimo desde un vértice fuente s fijo en DAGs. 
 Si el digrafo de entrada no es DAG, entonces se lanza una RuntimeException
 */
public class CCMDAG(val g: GrafoDirigido, val s: Int) {
    var ordenTopologicoGrafo = OrdenTopologico(g)
    var ordenTopologico = ordenTopologicoGrafo.obtenerOrdenTopologico()
    var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
    var listaDeArcos = g.arcos()
    var numVertices = g.obtenerNumeroDeVertices()
    var vertices = mutableListOf<Int>()

       /*
        descripcion: Init del algoritmo CCMDAG.
         
        precondiciones: que el objeto que invoca al metodo sea un grafo dirigido y s sea un vertice 
        que pertenezca al grafo.

        postcondiciones: Camino de costo minimo para los vertices del grafo respecto al vertice s.

        tiempo de la operacion: O(V+E) por usar el orden topologico.

     */
    
    
    init{
        
        for (i in 0..this.numVertices-1 ) {
            this.vertices.add(i)
        }

        this.listaDeVertices = inicializarFuenteFija(g,s)
        for(i in this.ordenTopologico){
            var adyacentesI = g.adyacentes(i)
            adyacentesI.forEach{ a ->
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
    }
    
    /*
        descripcion: Funcion que determina si existe o no un camino desde el vertice
        s hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase CCMDAG

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
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase CCMDAG

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
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase CCMDAG

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
