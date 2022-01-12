package ve.usb.grafoLib

/*
 Determina las componentes conexas de un grafo no dirigido usando
 conjuntos disjuntos, representado como árboles. 
 La componentes conexas se determinan cuando 
 se crea un nuevo objeto de esta clase.
*/
public class ComponentesConexasCD(val g: GrafoNoDirigido) {
    var listaDeAristas = g.aristas()
    var vertices = g.obtenerNumeroDeVertices()
    var listaVertices = g.listaDeVertices()
    var conjuntosDisjuntos = ConjuntosDisjuntos(vertices)
    var componentesConexas:MutableList<MutableList<Int?>> = mutableListOf()
    var listaIdentificadores: MutableList<Int> = mutableListOf()
    var numComponetesConexas = 0
 
    
    init {
        this.listaDeAristas.forEach{ a ->
        var primerVertice = a.primerV
        var segundoVertice = a.segundoV
        if(this.conjuntosDisjuntos.encontrarConjunto(primerVertice) != this.conjuntosDisjuntos.encontrarConjunto(segundoVertice)){
            this.conjuntosDisjuntos.union(primerVertice,segundoVertice)
            }
        
        }
        this.componentesConexas = this.conjuntosDisjuntos.listaComponenteConexas()
        this.listaIdentificadores = this.conjuntosDisjuntos.listaDeRepresentantesFinal()
        this.numComponetesConexas = this.conjuntosDisjuntos.numConjuntosDisjuntos()
     //   println("Esta es la lista de componentes Conexas en el init : ${this.componentesConexas}")
     //   println("Esta es la lista de identificador de componentes Conexas en el init : ${this.listaIdentificadores}")
    }
    

    /*
     Retorna true si los dos vertices están en la misma componente conexa y
     falso en caso contrario. Si el algunos de los dos vértices de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun estanMismaComponente(v: Int, u: Int) : Boolean {
        var estanMismaComponente = false
        if( v in this.listaVertices && u in this.listaVertices) {
            if(this.conjuntosDisjuntos.encontrarConjunto(v) == this.conjuntosDisjuntos.encontrarConjunto(u)){
                estanMismaComponente = true
            }
            return estanMismaComponente
        } else {
            throw Exception("El vertice $v o $u no estan en el grafo")
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
       if (v in this.listaVertices) {
           return this.listaIdentificadores[v]
       } else {
           throw Exception("El vertice $v no pertenece al grafo")
       }
   }

    /* Retorna el número de vértices que conforman una componente conexa dada.
     Se recibe como entrada el identificado de la componente conexa.
     El identificador es un número en el intervalo [0 , numeroDeComponentesConexas()-1].
     Si el identificador no pertenece a ninguna componente conexa, entonces se lanza una RuntimeException
     */
    fun numVerticesDeLaComponente(compID: Int) : Int {
        var listaDeIdentificadores = mutableListOf<Int>()
        for (i in 0..(this.numComponetesConexas -1)){
            listaDeIdentificadores.add(i)
        }
        if(compID in listaDeIdentificadores){
            return this.componentesConexas[compID].size
        } else {
            throw Exception("el identificador $compID no pertenece a una componente Conexa")
        }

    }

}
