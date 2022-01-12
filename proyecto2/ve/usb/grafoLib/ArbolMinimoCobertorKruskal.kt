package ve.usb.grafoLib

/*
 Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Kruskal.
 El algoritmo de Kruskal debe estar basado en la clase ConjuntosDisjuntos de esta librería.
 */
public class ArbolMinimoCobertorKruskal(val g: GrafoNoDirigido) {
    var listaDeAristas = g.aristas()
    var listaOrdenada : MutableList<Arista> = mutableListOf()
    var vertices = g.obtenerNumeroDeVertices()
    var listaVertices = g.listaDeVertices()
    var arbolMinCobertor :MutableList<Arista> = mutableListOf()
    var conjuntosDisjuntos = ConjuntosDisjuntos(vertices)
    var componentesConexas:MutableList<MutableList<Int?>> = mutableListOf()
    var listaIdentificadores: MutableList<Int> = mutableListOf()
    var numComponetesConexas = 0 

    init {
      //  print("Lista de arista antes de ser ordenadas ascendentemente por su peso : ${this.listaDeAristas} \n")
        var listaOrdenadaPrev = this.listaDeAristas.sortedBy {it.aristaPeso}
        this.listaOrdenada = listaOrdenadaPrev.toMutableList() 
     //   print("Lista de arista ordenadas ascendentemente por su peso : ${this.listaOrdenada} \n")

        this.listaOrdenada.forEach{ a ->
            var primerVertice = a.primerV
            var segundoVertice = a.segundoV
            if(this.conjuntosDisjuntos.encontrarConjunto(primerVertice) != this.conjuntosDisjuntos.encontrarConjunto(segundoVertice)){
                this.arbolMinCobertor.add(a)
                this.conjuntosDisjuntos.union(primerVertice,segundoVertice)
            }
        }

     //  println("Este es el arbol min cobertor Kruskal : ${this.arbolMinCobertor}")

    }

    // Retorna un objeto iterable que contiene los lados del árbol mínimo cobertor.
    fun obtenerLados() : Iterable<Arista> {
        var arbolMinCobertorIterable = this.arbolMinCobertor.asIterable()
        return arbolMinCobertorIterable
    }
    
    // Retorna el peso del árbol mínimo cobertor. 
    fun obtenerPeso() : Double {
        var sumPeso = 0.0
        this.arbolMinCobertor.forEach {a ->
            sumPeso = sumPeso + a.aristaPeso
        }
        return sumPeso

    }
}
