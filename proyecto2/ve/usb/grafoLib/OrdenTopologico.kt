package ve.usb.grafoLib


public class OrdenTopologico(val g: GrafoDirigido) {
    var dfs = BusquedaEnProfundidad(g)
    var listaDeArcos = g.arcos()
    
      /*
        descripcion: Funcion que retorna el si un grafo es un DAG
         
        precondiciones: que sea invocado por un objeto de la clase OrdenTopologico

        postcondiciones: booleano que indica si el grafo pasado como argumento a la
        clase es un DAG 

        tiempo de la operacion: O(E) E el numero de lados

     */
    fun esUnGrafoAciclicoDirecto() : Boolean {
      listaDeArcos.forEach{l ->
        var edge = dfs.edges(l)
        if (edge == "BACK") return false
      }
      return true
    }

     /*
        descripcion: Funcion que retorna el orden topologico de un grafo  DAG
         
        precondiciones: que sea invocado por un objeto de la clase OrdenTopologico

        postcondiciones: lista o iterable de enteros que representa el orden topologico del
        grafo

        tiempo de la operacion: O(V + E ) porque se necesita el dfs para generar la lista de orden 
        topologico

     */
    fun obtenerOrdenTopologico() : Iterable<Int> {
      if (esUnGrafoAciclicoDirecto() == false) throw Exception("El grafo no es un DAG")
      return dfs.ordenTopologicoGrafo()
    }
}
