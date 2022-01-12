package ve.usb.grafoLib

/*
  Determina las componentes conexas de un grafo no dirigido usando DFS. 
  La componentes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasDFS(val g: GrafoNoDirigido) {
    var dfs = BusquedaEnProfundidad(g)
    var listaDeAristas = g.aristas()
    var vertices = g.listaDeVertices()
    var listaVertices = dfs.obtenerListaVertices()
    var numComponetesConexas = dfs.obtenerNumComponentesConexas()
    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
      var coexisten = false
      if (v in this.vertices && u in this.vertices ) {
        var a = this.listaVertices.get(v).get("contador")
        var b = this.listaVertices.get(u).get("contador")
        if(a == b) coexisten = true

        return  coexisten
      } else {
        throw Exception("EL vertice $v o $u no pertenecen al grafo")
      }
      
      
      
    }

    // Indica el número de componentes conexas
    fun numeroDeComponentesConexas() : Int {
      return this.numComponetesConexas
    }

    /*
     Retorna el identificador de la componente conexa donde está contenido 
     el vértice v. El identificador es un número en el intervalo [0 , numeroDeComponentesConexas()-1].
     Si el vértice v no pertenece al grafo g se lanza una RuntimeException
     */
    fun obtenerComponente(v: Int) : Int {
      if (v in this.vertices) {
        var identificador = this.listaVertices.get(v).get("contador") as Int 

        return identificador - 1
      } else {
        throw Exception("El vertive $v no se encuentra en el grafo")
      }
    }

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Se recibe como entrada el identificado de la componente conexa.
     El identificador es un número en el intervalo [0 , numeroDeComponentesConexas()-1].
     Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
      var listaIdentificadores = mutableListOf<Int>()
      for (i in 0..(this.numComponetesConexas -1)){
        listaIdentificadores.add(i)
      }
      if( compID in listaIdentificadores){
        var counter = 0
        this.listaVertices.forEach{v ->
          var identificador_1To = v.get("contador") as Int
          var identificador_0To = identificador_1To - 1
          if (identificador_0To == compID) counter ++
        }

        return counter
      } else {
        throw Exception("El identificador $compID no pertenece a una componente conexa")
      }
    }

}
