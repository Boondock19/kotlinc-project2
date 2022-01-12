package ve.usb.grafoLib
import kotlin.math.min

/*
 Implementación del algoritmo de Floyd-Warshall para encontrar los
 caminos de costo mínimo todos los pares de vértices de un grafo.
 El constructor recibe como entrada una matriz de costos, que corresponde
 a la matriz de costos asociado a un digrafo con pesos en los lados.
 La matriz de costos es construida con la función dada en clase.
 Se asume que la matriz de costos corresponde a un digrafo sin ciclos negativos.
 Si el digrafo tiene ciclo negativo, el resultado del algoritmo no es especificado.
 La matriz de entrada es cuadrada de dimensiones nxn, donde n es el número de 
 vértices del grafo. Si la matriz de entrada no es cuadrada, entoces se lanza una RuntimeException.
 */
public class FloydWarshall(val W : Array<Array<Double>>) {
    var n = W.size
    var d = W
    var vertices = mutableListOf<Int>()
    var pi = Array<Array<Int?>>(n){Array<Int?>(n){null}}
    init {
    /*
        descripcion: Init del algoritmo de FloydWarshall
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase FloydWarshall

        postcondiciones: camino de coste minimos desde todo par de vertices (u,v)

        tiempo de la operacion: O(V^3)
 
     */
        if (W.size != W[0].size) {
         throw Exception("No es una matriz cuadrada")
        }

        for (i in 0..n-1) {
            this.vertices.add(i)
        }

        // construccion de pi^0
        for (i in 0..n-1) {
            for (j in 0..n-1) {
                if (i == j || this.d[i][j] == Double.POSITIVE_INFINITY) {
                    this.pi[i][j] = null
                } else {
                    this.pi[i][j] = i
                }
            }
        }

  

        for ( k in 0..n-1) {
            for (i in 0..n-1) {
                for (j in 0..n-1) {
                    var suma = this.d[i][k] + this.d[k][j]
                    var valorIj = this.d[i][j]
                    if (valorIj <= suma) {
                        this.d[i][j] = this.d[i][j]
                        this.pi[i][j] = this.pi[i][j]
                    } else {
                        this.d[i][j] = this.d[i][k] + this.d[k][j]
                        this.pi[i][j] = this.pi[k][j]
                    }
                }
            }
        }
          
    }
    

    
         /*
        descripcion: Funcion que retorna la matriz de distancia de los vertices
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase FloydWarshall

        postcondiciones: matriz con las distancias del camino de coste minimo de cada vertice.

        tiempo de la operacion: O(1) por ser asiganciones 

     */
    fun obtenerMatrizDistancia() : Array<Array<Double>> { 
        return this.d
    } 

    

          /*
        descripcion: Funcion que retorna la matriz de predecesores de los vertices
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase FloydWarshall

        postcondiciones: matriz con los predecesores de cada vertice.

        tiempo de la operacion: O(1) por ser asiganciones 

     */
   fun obtenerMatrizPredecesores() : Array<Array<Int?>> { 
       return this.pi
   } 
    
    
       /*
        descripcion: Funcion que determina la distancia del camino de menor coste  del vertice
        u hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase FloydWarshall

        postcondiciones: double que representa que la distancia desde u hasta v.

        tiempo de la operacion: O(1) por ser asiganciones y comparaciones.

     */
    fun costo(u: Int, v: Int) : Double { 
        var distancia = 0.0 
        if (u in this.vertices && v in this.vertices) {
            distancia = d[u][v] 
            return distancia  
        } else {
            throw Exception("El vertice $v o $u no existen")
        }
    }


      /*
        descripcion: Funcion que determina si existe o no un camino desde el vertice
        u hasta el vertice v.
         
        precondiciones: que el objeto que invoca al metodo sea un uno de la clase FloydWarshall

        postcondiciones: boleano que indica si existe un camino o no desde u hasta v.

        tiempo de la operacion: O(1) por ser asiganciones y comparaciones.

     */
   fun existeUnCamino(u: Int, v: Int) : Boolean { 
        var hayCamino = false 
        if (u in this.vertices && v in this.vertices) {
            var maxValue = Double.MAX_VALUE
            var distancia = this.costo(u,v)
            hayCamino = distancia !== maxValue
            return hayCamino
        } else {
            throw Exception("El vertice $v o $u no existen")
        }
   }

  

        /*
        descripcion: Funcion que retorna la lista de inicializacion de un grafo
        a partir del procedimiento inicializar fuente fija.

        precondiciones: que invoque sobre un objeto de la clase  FloydWarshall y que los vertices
        u y v pertenezcan al grafo

        postcondiciones: Iterable de arcos que representan el camino de costo minimo desde u
        hasta v. 

        tiempo de la operacion: O(V) 

     */
   
   fun obtenerCaminoDeCostoMinimo(u: Int, v: Int) : Iterable<Arco> { 
        var caminoArcos = mutableListOf<Arco>() 
        if (u in this.vertices && v in this.vertices) {
            var verticeAnterior = v
            //var flag : Int? 
            while ( this.pi[u][verticeAnterior] != u ) {
                var predecesor = this.pi[u][verticeAnterior]
                var predecesorNoNulo = predecesor as Int
                var newArco = Arco(predecesorNoNulo,verticeAnterior)
                caminoArcos.add(newArco)
                verticeAnterior = predecesor as Int
            }
            var ultimoArco = Arco(u,verticeAnterior)
             caminoArcos.add(ultimoArco)
            return caminoArcos.reversed().asIterable()
        } else {
            throw Exception("El vertice $v o $u no existen")
        }
   }
    
}
