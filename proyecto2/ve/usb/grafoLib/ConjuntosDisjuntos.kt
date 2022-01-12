package ve.usb.grafoLib

/*
 Implementación de las estructuras de datos para conjuntos disjuntos (Disjoint Sets)
 Los conjuntos disjuntos son representado como árboles.
 El constructor recibe como entrada en número elementos que van a conformar los cojuntos disjuntos. 
 Los elementos de los conjuntos disjuntos están identificados en el intervalo [0 .. n-1]. 
 Cuando se ejecuta el constructor, se crean n conjuntos disjuntos iniciales, es decir,
 se debe ejecutar make-set(i) para todo i en el intervalo [0 .. n-1]. 
*/
public class ConjuntosDisjuntos(val n: Int) {
    var listaDeConjuntos:MutableList<MutableList<Int?>> = mutableListOf()
    var listaDeRepresentantes = mutableListOf<Int>()
    var listaDeRepresentantesNoNulos = mutableListOf<Int>()
    var vertices  = mutableListOf<Int>()
    var conjuntosNoNulos :MutableList<MutableList<Int?>> = mutableListOf()
    init {
        for (i in 0..n-1) {
        this.listaDeConjuntos.add(mutableListOf(i))
        this.listaDeRepresentantes.add(i)
        this.listaDeRepresentantesNoNulos.add(i)
        this.vertices.add(i)
        } 

       // println("Esta es la lista de conjuntos ${this.listaDeConjuntos}")
       // println("Esta es la lista de Representantes ${this.listaDeRepresentantes}")
    }
    

    
    
    /*
     Realiza la unión de las dos componentes conexas conexas de las cuales pertenecen
     los elementos v y u. Retorna true si los dos vertices están en diferentes componentes conexas, 
     en caso contrario retorna falso. Si el algunos de los dos elementos de
     entrada, no pertenece al grafo, entonces se lanza un RuntineException
     */
    fun union(v: Int, u: Int) : Boolean {
        if (v in this.vertices && u in this.vertices ) {
            if (this.listaDeRepresentantes[v] != this.listaDeRepresentantes[u] ) {
                if (v < u) {
                    // Hacemos la union 
                    this.listaDeConjuntos[this.listaDeRepresentantes[v]].addAll(this.listaDeConjuntos[this.listaDeRepresentantes[u]])
                    
                    //Eliminamos el elemento en la lista de conjuntos
                    this.listaDeConjuntos.set(this.listaDeRepresentantes[u],mutableListOf(null))
                    //Cambiamos el Representante
                   
                  
                    var valorCambiar = this.listaDeRepresentantes[u]
                    this.listaDeRepresentantes.forEachIndexed{ index, r ->
                        var valorActual = this.listaDeRepresentantes[index]
                        if( valorActual == valorCambiar) {
                            this.listaDeRepresentantes[index] = this.listaDeRepresentantes[v]
                        }
                    }
                   
                    
                } else {
                    // Hacemos la union 
                    this.listaDeConjuntos[this.listaDeRepresentantes[u]].addAll(this.listaDeConjuntos[this.listaDeRepresentantes[v]])
                    
                    //Eliminamos el elemento en la lista de conjuntos
                    this.listaDeConjuntos.set(this.listaDeRepresentantes[v],mutableListOf(null))
                    
                    //Cambiamos el Representante
                    var valorCambiar = this.listaDeRepresentantes[v]
                    this.listaDeRepresentantes.forEachIndexed{ index, r ->
                        var valorActual = this.listaDeRepresentantes[index]
                        if( valorActual == valorCambiar) {
                            this.listaDeRepresentantes[index] = this.listaDeRepresentantes[u]
                        }
                    }
                   
                }
               
              //  println("Esta es la lista de conjuntos luego del metodo de union ${this.listaDeConjuntos}")
               // println("Esta es la lista de Representantes luego del metodo de union ${this.listaDeRepresentantes}")
                return true
            } else {
              //  println("Esta es la lista de conjuntos luego del metodo de union ${this.listaDeConjuntos}")
              //  println("Esta es la lista de Representantes luego del metodo de union ${this.listaDeRepresentantes}")
                return false
            }
            
        } else {
            throw Exception("El vertice $v o el vertice $u no pertenecen al grafo")
        }
       
    }

    /*
     Retorna el elemento representante de un elemento en un conjunto disjunto.
     Se recibe como entrada al elemento que al que se le quiere determinar el representante.
     Este método corresponde a la función find-set de conjuntos disjuntos. 
     Si el identificador no pertenece a ningun elemento, entonces se lanza una RuntimeException.
     */
   fun encontrarConjunto(v: Int) : Int {
       if (v in this.vertices) {
           return this.listaDeRepresentantes[v]
       } else {
           throw Exception("El vertice $v no pertenece a los conjuntos Disjuntos")
       }
   }

    /* 
     Retorna el número de conjuntos disjuntos acutales que tiene la estructura de conjuntos disjuntos.
     */
   fun numConjuntosDisjuntos() : Int {
       this.conjuntosNoNulos = this.listaDeConjuntos
      this.conjuntosNoNulos.removeAll(listOf(mutableListOf(null)))
      // println("Lista de cojuntosNoNulos : $conjuntosNoNulos")
       return this.conjuntosNoNulos.size
   }

   fun listaComponenteConexas():MutableList<MutableList<Int?>> {
        this.conjuntosNoNulos = this.listaDeConjuntos
        this.conjuntosNoNulos.removeAll(listOf(mutableListOf(null)))
       return this.conjuntosNoNulos
   }

   fun listaDeRepresentantesFinal():MutableList<Int> {
       this.vertices.forEach{v ->
        this.conjuntosNoNulos.forEachIndexed{ index, c ->
            if(c.contains(v)) {
                this.listaDeRepresentantesNoNulos.set(v,index)
            }
         }
       }
       return this.listaDeRepresentantesNoNulos
   }
}
