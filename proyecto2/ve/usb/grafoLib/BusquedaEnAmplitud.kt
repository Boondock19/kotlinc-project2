package ve.usb.grafoLib

import java.util.LinkedList
import java.util.Queue


/* 
   Implementación del algoritmo BFS. 
   Con la creación de la instancia, se ejecuta el algoritmo BFS
   desde el vértice s
*/
public class BusquedaEnAmplitud(val g: Grafo, val s: Int) {
        
        var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
        var numVertices = g.obtenerNumeroDeVertices()
        var vertices = mutableListOf<Int>() 
        var listaDeCamino = Array(numVertices) {mutableListOf<Int>()}
        
    
    
    init {
	// Se ejecuta BFS
        var cola : Queue<Int> = LinkedList<Int>()
        var u = 0
        var listaLados = g
        var adyacentes = g.adyacentes(s)
        //var flag = 0
         for (i in 0..numVertices-1) {
           this.listaDeVertices.add(mutableMapOf(
                "color" to Color.BLANCO,
                "distancia" to 0,
                "predecesor" to null
            ))
        
              this.vertices.add(i)
            
        }
             
            this.listaDeVertices.get(s).put("color", Color.GRIS)
            cola.add(this.vertices.get(s))
            while (!cola.isEmpty()) {
                u = cola.peek()
                cola.remove()
                var adyacentes = g.adyacentes(u)
                adyacentes.forEach {lado ->
                   
                    
                    var otroV = lado.elOtroVertice(u)
                    if ( this.listaDeVertices.get(otroV).get("color") == Color.BLANCO ) {
                        this.listaDeVertices.get(otroV).put( "color" ,Color.GRIS)
                        var distancia = this.listaDeVertices.get(otroV).get("distancia") as Int
                        distancia++
                        this.listaDeVertices.get(otroV).put("distancia",distancia)
                        this.listaDeVertices.get(otroV).put("predecesor",u)
                       
                        cola.add(this.vertices.get(otroV))
                    }
                    this.listaDeVertices.get(u).put("color", Color.NEGRO) 
                }
            }
            

       
    }

  /*
        descripcion: Funcion que retorna al predecesor del vertice v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnAmplitud 

        postcondiciones: int que representa al predecesor de v

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
    fun obtenerPredecesor(v: Int) : Int? {  
        if ( v in this.vertices ) {
            return this.listaDeVertices.get(v).get("predecesor") as Int?
        } else {
            throw Exception("El vertice ${v} no se encuentra en el grafo")
        }
    }

    /*
        descripcion: Funcion que retorna la distancia mas corta desde s hasta v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnAmplitud 

        postcondiciones: int que representa la menor distancia desde s hasta v

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
   fun obtenerDistancia(v: Int) : Int {  
          if ( v in this.vertices ) {
            return this.listaDeVertices.get(v).get("distancia") as Int
        } else {
            throw Exception("El vertice ${v} no se encuentra en el grafo")
        }
   }

     /*
        descripcion: Funcion que retorna un boleano para saber si un hay un camino
        desde s hasta un vertice v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnAmplitud 

        postcondiciones: booleano que indica si hay un camino hasta v desde s.

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
    fun hayCaminoHasta(v: Int) : Boolean { 
           if ( v in this.vertices ) {
               if (this.listaDeVertices.get(v).get("distancia") as Int != 0){
                   return true
               } else {
                   return false
               }
            } else {
            throw Exception("El vertice ${v} no se encuentra en el grafo")
        }
     }

    /*
        descripcion: Funcion que retorna el camino de menor tamaño desde un vertice s hasta v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnAmplitud 

        postcondiciones: lista que representa al camino mas corto desde s hasta v

        tiempo de la operacion: O(v) ya que se recorren los vertices para obtener el camino.

     */
    
    
    fun caminoConMenosLadosHasta(v: Int) : Iterable<Int>  { 
        var caminoPCorto = mutableListOf<Int?>()
        var caminoNulo = mutableListOf<Int>()
        var verticeAnterior : Int
         if ( v in this.vertices ) {
            verticeAnterior = v
            if (this.listaDeVertices.get(verticeAnterior).get("predecesor") as Int? == null) {
                var caminoCorto = caminoPCorto.filterNotNull()
                caminoCorto = caminoCorto.reversed()
                return  caminoCorto.asIterable()
                
            }
            caminoPCorto.add(v)
             while (this.listaDeVertices.get(verticeAnterior).get("predecesor") as Int? != s){
                var proxPredecesor = obtenerPredecesor(verticeAnterior) as Int
                caminoPCorto.add(proxPredecesor)
                verticeAnterior = proxPredecesor
            }
            caminoPCorto.add(s)
           var caminoCorto = caminoPCorto.filterNotNull()
           caminoCorto = caminoCorto.reversed()
            return caminoCorto.asIterable()
        } else {
            throw Exception("El vertice ${v} no se encuentra en el grafo")
        }
     }

     
}
