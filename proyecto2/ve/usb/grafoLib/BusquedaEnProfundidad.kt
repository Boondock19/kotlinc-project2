package ve.usb.grafoLib

import java.util.LinkedList
import java.util.Queue


/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/
public class BusquedaEnProfundidad(val g: Grafo, val orden: MutableList<Int> = mutableListOf<Int>()) {
    
    var listaDeVertices :MutableList<MutableMap<String, Any?>> = mutableListOf()
    var numVertices = g.obtenerNumeroDeVertices()
    var tiempo : Int 
    var contador : Int 
    var vertices = mutableListOf<Int>()
    var ordenTopologicoLista = mutableListOf<Int>()
    init {
	
    // Se ejecuta DFS
        for (i in 0..numVertices-1 ) {
            this.listaDeVertices.add(mutableMapOf(
                "color" to Color.BLANCO,
                "predecesor" to null,
                "ti" to 0,
                "tf" to 0,
                "contador" to 0,
            ))

            this.vertices.add(i)
        }
        
        tiempo = 0
        contador = 0
       // println("Esta es la lista de vertices con un mutableMaP :  ${this.listaDeVertices} \n")
       if (orden.size > 0) {
           for (i in 0..numVertices-1){
               if(listaDeVertices.get(orden[i]).get("color") == Color.BLANCO){
                   dfsVisit(g,orden[i])
               }
           }
         //  println("Esta es la lista de vertices con un mutableMaP :  ${this.listaDeVertices} \n")
       } else {
        for (i in 0..numVertices-1 ){
        if (listaDeVertices.get(i).get("color") == Color.BLANCO) {
            contador ++
            dfsVisit(g,i)
            }
        }
        // println("Esta es la lista de vertices con un mutableMaP :  ${this.listaDeVertices} \n")
       }
        
    }

     
    private fun dfsVisit(g: Grafo, u: Int) {
        var adyacencia = g.adyacentes(u)
        this.tiempo = tiempo + 1
        this.listaDeVertices.get(u).put("ti",tiempo)
        this.listaDeVertices.get(u).put("color",Color.GRIS)
        this.listaDeVertices.get(u).put("contador",this.contador)
        adyacencia.forEach { lado ->
            var otroV = lado.elOtroVertice(u)
            if (this.listaDeVertices.get(otroV).get("color")==Color.BLANCO) {
                this.listaDeVertices.get(otroV).put("predecesor",u)
                dfsVisit(g,otroV)                
            }

        }

        this.listaDeVertices.get(u).put("color",Color.NEGRO)
        this.tiempo = tiempo + 1
        this.listaDeVertices.get(u).put("tf",tiempo)
        // lista de orden topologico pero invertida (El primero que termine se agrega, pero al voltearla nos quedara ordenada de mayor a menor en relacion al tiempo final)
        this.ordenTopologicoLista.add(u)

     }
     

     /*
        descripcion: Funcion que retorna al predecesor del vertice v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnProfundidad 

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
        descripcion: Funcion que retorna la el ti y tf de v
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnProfundidad y el
       vertice v pertenezca a G

        postcondiciones: Par de int que indica el ti y tf de v

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
     
    fun obtenerTiempos(v: Int) : Pair<Int, Int> { 
        
          if ( v in this.vertices ) {
            val vertice = listaDeVertices.get(v)
            var parTiempo = Pair( vertice.get("ti") as Int , vertice.get("tf") as Int)

            return parTiempo
        } else {
            throw Exception("El vertice ${v} no se encuentra en el grafo")
        }
     }
    
    
       /*
        descripcion: Funcion que retorna el vertice que tengan el tiempo final mayor
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnProfundidad 

        postcondiciones: vertice con el tiempo final mayor

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
   fun  comparacionTiempo(a:Int,b:Int): Int? {
       val tiempoIa = this.listaDeVertices.get(a).get("ti") as Int
       val tiempoFa = this.listaDeVertices.get(a).get("tf") as Int
       val tiempoIb = this.listaDeVertices.get(b).get("ti") as Int
       val tiempoFb = this.listaDeVertices.get(b).get("tf") as Int
       if (tiempoIa < tiempoIb && tiempoFb < tiempoFa) return a
        else if (tiempoIb < tiempoIa && tiempoFa < tiempoFb) return b
        else return null
   }

  
       /*
        descripcion: Funcion que retorna el tipo de edge de un lado del grafo
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnProfundidad 

        postcondiciones: Par de int que indica el ti y tf de v

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */
    fun edges(l:Lado): String {
        if (obtenerPredecesor(l.segundoV) == l.primerV) return "TREE"
        if (g is GrafoNoDirigido || l.primerV == l.segundoV) return "BACK"
        when(comparacionTiempo(l.primerV,l.segundoV)) {
            l.primerV  -> return "FORWARD"
            l.segundoV  -> return "BACK"
            else -> return "CROSS"
        }
    }

    /*
        descripcion: Funcion que retorna el orden del tiempo final de los vertices, desde
        mayor a menor del dfs del grafo
         
       precondiciones: que sea invocado por un objeto de la clase BusquedaEnProfundidad 

        postcondiciones: lista de int con el orden de mayor a menor del tiempo final de cada
        vertice

        tiempo de la operacion: O(1) porque son comparaciones y asignaciones.

     */

    fun ordenTopologicoGrafo():Iterable<Int>{
        var ordenTopologicoListaInversa = this.ordenTopologicoLista.reversed()
        return ordenTopologicoListaInversa.asIterable()
    }

    fun obtenerListaVertices():MutableList<MutableMap<String, Any?>>{
        return this.listaDeVertices
    }
    
    fun obtenerNumComponentesConexas():Int{
        return this.contador
    }
}
