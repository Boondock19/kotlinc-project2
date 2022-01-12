package ve.usb.grafoLib

/*
  Determina las componentes fuertementes conexas de un grafo 
  La componentes fuertementes conexas se determinan cuando 
  se crea un nuevo objeto de esta clase.
*/
public class CFC(val g: GrafoDirigido) {
    var dfs = BusquedaEnProfundidad(g)
    var ordenTopologico = dfs.ordenTopologicoGrafo().toMutableList()
    var grafoInverso = digrafoInverso(g)
    var dfsInverso = BusquedaEnProfundidad(grafoInverso,ordenTopologico)
    var listaCFC :MutableList<MutableSet<Int>> = mutableListOf()
    var listaArcos = grafoInverso.arcos()
    var numVertices = g.obtenerNumeroDeVertices()
    var listaDeVertices = mutableListOf<Int>()
    
  init {
    for (i in 0..this.numVertices-1){
      this.listaDeVertices.add(i)
    }
    this.listaArcos.forEach{ a ->
      var edge = dfsInverso.edges(a)
      var listaCFCTemp = mutableSetOf<Int>()
      if (edge == "BACK") {
        var ladoFuente = a.fuente()
        var ladoSumidero = a.sumidero()
        listaCFCTemp.add(ladoFuente)
        while (dfsInverso.obtenerPredecesor(ladoFuente) != null  && dfsInverso.obtenerPredecesor(ladoFuente) as Int != ladoSumidero ){
            ladoFuente = dfsInverso.obtenerPredecesor(ladoFuente) as Int
            listaCFCTemp.add(ladoFuente)
          }
        listaCFCTemp.add(ladoSumidero)
        this.listaCFC.add(listaCFCTemp)
        }
      }
    }
    
     /*
        descripcion: Funcion que retorna un booleano indicando si dos componentes conexas estan
        conectadas entre si por un par de vertices
         
        precondiciones: que sea invocado por un objeto de la clase CFC y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: booleano que indica si dos componentes conexas estan conectadas.

        tiempo de la operacion: O(V+E) ya que utiliza el dfs del grafo G, adicionalmente
        iteramos sobre la lista de componentes conexas O(numeroCFC()), sobre los vertices
        de cada componente O(v) y sobre la lista adyacente de cada vertice O(v). 

     */
    fun estanFuertementeConectados(v: Int, u: Int) : Boolean {
      var conectados = false
      if (v in this.listaDeVertices && u in this.listaDeVertices){
        if (this.obtenerIdentificadorCFC(v) != this.obtenerIdentificadorCFC(u)){
          this.listaCFC.forEach{c->
            c.forEach{vertice->
              var adyacentes = g.adyacentes(vertice)
              adyacentes.forEach{a ->
                if(v == a.segundoV && u == a.primerV){
                  conectados = true
                }
                 if(u == a.segundoV && v == a.primerV){
                  conectados = true
                }
              }
            }
          }
        }
      } else {
        throw Exception("El vertice $v o $u no pertenecen al grafo")
      }
      
      return conectados
    }

       /*
        descripcion: Funcion que retorna el numero de componentes que posee el grafo
         
       precondiciones: que sea invocado por un objeto de la clase CFC y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: int que indica la cantidad de componentes conexas

        tiempo de la operacion: O(1) es una asignacion o return

     */
    fun numeroDeCFC() : Int {
      return this.listaCFC.size
    }

     /*
        descripcion: Funcion que retorna la posicion de un vertice en la una componente conexa
        dentro de un mutableSet
         
       precondiciones: que sea invocado por un objeto de la clase CFC y por
        lo tanto que sea llamado por un digrafo, y que el vertice v pertenezca
        al digrafo

        postcondiciones: int que indica la posicion de la componente conexa
        a la que pertenece el vertice

        tiempo de la operacion: O(V) porque iteramos sobre los vertices
        de la lista de componentes.

     */
     
    fun obtenerIdentificadorCFC(v: Int) : Int {
      var indentificador = 0
      if (v in this.listaDeVertices) {
        this.listaCFC.forEachIndexed{index,c ->
          if(c.contains(v)) {
            indentificador = index
            }
        }
      } else {
        throw Exception("El vertice $v no se encuentra en el grafo")
      }

      return indentificador 
    }
    

       /*
        descripcion: Funcion que la lista CFC del grafo
         
       precondiciones: que sea invocado por un objeto de la clase CFC y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: lista que contiene las componentes fuertemente conexas del grafo

        tiempo de la operacion: O(V+E) porque realizamos el DFS del grafo, obtenemos el grafo
        inverso y realizamos DFS del grafo inverso

     */
    fun  obtenerCFC() : Iterable<MutableSet<Int>> {
      return  this.listaCFC.asIterable()
    }
  
         /*
        descripcion: Funcion que retorna el digrafo que se obtiene de las componentes
        fuertemente conexas
         
       precondiciones: que sea invocado por un objeto de la clase CFC y por
        lo tanto que sea llamado por un digrafo

        postcondiciones: digrafo que contiene a las componentes fuertemente conexas y 
        sus lados.

        tiempo de la operacion: O(v^2) porque realizamos un ciclo dentro de un ciclo para poder 
        recorrer la lista de CFC y verificar cada vertice , y asi obtener la direccion de los 
        lados que hay entre las componentes conexas y poder generar el grafo componente resultante

     */
    fun obtenerGrafoComponente() : GrafoDirigido {
      var numVerticesConexos = this.numeroDeCFC()
      var grafoConexo = GrafoDirigido(numVerticesConexos)
      var setArcos = mutableListOf<Arco>()
         this.listaCFC.forEach{c->
            c.forEach{vertice->
              var adyacentes = g.adyacentes(vertice)
              adyacentes.forEach{a ->
                var primerVertice = a.primerV
                var segundoVertice = a.segundoV
                var indentificador1 = 0
                var indentificador2 = 0
              
                if(this.estanFuertementeConectados(primerVertice,segundoVertice)){
                     
                    if(primerVertice == a.segundoV && segundoVertice == a.primerV){
                    indentificador1=this.obtenerIdentificadorCFC(segundoVertice)
                    indentificador2=this.obtenerIdentificadorCFC(primerVertice)
                    var newArco=Arco(indentificador1,indentificador2)
                    setArcos.add(newArco)
                   
                  }
                   
                  if(segundoVertice == a.segundoV && primerVertice == a.primerV){
                    indentificador1=this.obtenerIdentificadorCFC(primerVertice)
                    indentificador2=this.obtenerIdentificadorCFC(segundoVertice)
                    var newArco=Arco(indentificador1,indentificador2)
                    
                    setArcos.add(newArco)
                   
                  }
               }
             }
            }
          }
        var listaArcosFiltrada = setArcos.distinctBy {listOf(it.primerV,it.segundoV)}
        listaArcosFiltrada.forEach{a ->
          grafoConexo.agregarArco(a)
        }
        
      return grafoConexo
    }

}
