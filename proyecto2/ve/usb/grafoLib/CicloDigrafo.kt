package ve.usb.grafoLib


public class CicloDigrafo(val g: GrafoDirigido) {
    var dfs = BusquedaEnProfundidad(g)
    var listaDeArcos = g.arcos()
    var cicloFlag = false
    var caminoCiclo = mutableListOf<Int>()
    var ladoSumidero = 0
    var ladoFuente = 0    
   

     /*
        descripcion: Funcion que retorna camino de vertices de un ciclo
         
        precondiciones: que sea invocado por un objeto de la clase cicloDigrafo y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: lista de enteros que representa el camino de un ciclo

        tiempo de la operacion: O(V+E) ya que utiliza el dfs del grafo G,
        ademas iterar sobre los lados para verificar los tipo de edge (tiempo
        O(E) e iterar sobre los predecesores del primer ciclo encontrado O(V))

     */
   fun cicloEncontrado() : Iterable<Int> {
     listaDeArcos.forEach   {l ->
     var edge = dfs.edges(l)
     
     if (edge == "BACK"){
         cicloFlag = true
         
        }
    if( !cicloFlag ){
        throw Exception("El grafo ingresado no posee ciclos")
        }
    if( dfs.edges(l) == "BACK") {
       var ladoFuente = l.fuente()
       var ladoSumidero = l.sumidero()
        caminoCiclo.add(ladoFuente)
        while (  dfs.obtenerPredecesor(ladoFuente) as Int != ladoSumidero){
            ladoFuente = dfs.obtenerPredecesor(ladoFuente) as Int
            caminoCiclo.add(ladoFuente)
            }
       caminoCiclo.add(ladoSumidero)
        }
        if (caminoCiclo.size > 0) return caminoCiclo.asIterable()
    } 
        
   
   return caminoCiclo.asIterable()     
        
    
}

     /*
        descripcion: Funcion que retorna un booleano indicando si el grafo posee un 
        ciclo o no.
         
        precondiciones: que sea invocado por un objeto de la clase cicloDigrafo y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: booleano que indica si el grafo posee un 
        ciclo.

        tiempo de la operacion: O(V+E) ya que utiliza el dfs del grafo G

     */
    fun existeUnCiclo() : Boolean {
        listaDeArcos.forEach{l ->
        var edge = dfs.edges(l)
        if (edge == "BACK"){
            cicloFlag = true
        }
        
      }
        return cicloFlag
    }
    
} 
