
package ve.usb.grafoLib
import java.io.File

public class GrafoDirigido : Grafo {

            val listaArchivo = mutableListOf<String>()
            override var numVertices = 0
            override var numLados = 0
            var listaArcos = mutableListOf<Arco>()
            var listaVertices = mutableListOf<Int>()
            var listaLados =  mutableListOf<String>()
            var grafo = Array(numVertices) {mutableListOf<Arco>()}
        

    
    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        
        this.numVertices = numDeVertices
        this.grafo = Array(this.numVertices) {mutableListOf<Arco>()}
        for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }

        
        
    }

    // Se construye un Digrafo a partir de su numero de vertices y lista de Arcos

    constructor(numDeVertices:Int,listaDeArcos: MutableList<Arco>) {
        this.numVertices = numDeVertices
        this.grafo = Array(this.numVertices) {mutableListOf<Arco>()}
          for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }
        this.listaArcos = listaDeArcos
        this.numLados = this.listaArcos.size

        this.grafo.forEachIndexed { index, lista ->
                var arcosFiltrada = this.listaArcos.filter {it.primerV == index }
                this.grafo.set(index, arcosFiltrada.toMutableList()) 
            }
    }

   
    constructor(nombreArchivo: String, conPeso: Boolean) {
        
        var flag = conPeso
        
        //fun para leer y almacenar data de txts en forma de lista
        
        File(nombreArchivo).useLines{ lines -> lines.forEach { this.listaArchivo.add(it) }}
        
        // Almacenamos data del grafo como cantidad de vertices y lados
        
       this.numVertices = listaArchivo[0].toInt()
       this.numLados = listaArchivo[1].toInt()
       this.grafo = Array(this.numVertices) {mutableListOf<Arco>()}
       for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }
        
        // Creamos una subLista para poder trabajar con los lados
        
        this.listaLados = listaArchivo.subList(2, listaArchivo.size)
        
        

        /** 
            Creamos los Arcos y los agregamos en la lista de adyacencia dependiendo
            de si el archivo posee formato con peso o no.
        */ 
        
        if (flag) {
            this.listaLados.forEach { 
            val ladoSinSeparador = it.split(" ")
            
            val newArco = Arco(ladoSinSeparador[0].toInt(),ladoSinSeparador[1].toInt(),ladoSinSeparador[2].toDouble())
            this.listaArcos.add(newArco)
            
            }
        } else {
            
            this.listaLados.forEach { 
            val ladoSinSeparador = it.split(" ")
            val newArco = Arco(ladoSinSeparador[0].toInt(),ladoSinSeparador[1].toInt())
            this.listaArcos.add(newArco)
            }   
         } 
            this.grafo.forEachIndexed { index, lista ->
                var arcosFiltrada = this.listaArcos.filter {it.primerV == index }
                this.grafo.set(index, arcosFiltrada.toMutableList()) 
            }
            
    }      

      /*
        descripcion: Funcion que retorna un mensaje al agregar satisfactoriamente el arco
        y muestra al grafo luego de asignar el arco.
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo y se le pase un arco,
        adicionalmente que no se encuentre el arco que se desea agregar en el digrafo

        postcondiciones: string que representa a un digrafo luego de agregar el arco

        tiempo de la operacion: O(n) siendo n el size de los numeros de lado

     */
    fun agregarArco(a: Arco) {
        val newArco = a
        var arcosFiltrados = mutableListOf<Arco>()
        
        this.listaArcos.forEachIndexed { index,arco ->
            if (newArco.primerV == arco.primerV && newArco.segundoV == arco.segundoV) {
              throw Exception("El arco ${a} ya se encuentra en el grafo")
        }
        }
        
        
        this.listaArcos.add(newArco)
        arcosFiltrados.add(newArco)

        
        this.grafo.set(newArco.primerV,(this.grafo[newArco.primerV]+arcosFiltrados).toMutableList())
        this.numLados += 1 
        
        var stringGrafo = this.toString()
        
        //return println("El arco ${a} fue agregado satisfactoriamente al grafo : ${stringGrafo}")
    
    }

 
    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
        var gradoVerticeExterior = this.gradoExterior(v)
        var gradoVerticeInterior = this.gradoInterior(v)
        return gradoVerticeExterior + gradoVerticeInterior
    }

    

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        var gradoVerticeExterior = 0
        val adyacentesDeV = this.adyacentes(v)
        adyacentesDeV.forEach{
            gradoVerticeExterior = gradoVerticeExterior + 1
        }

        
        return gradoVerticeExterior

    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        var gradoVerticeInterior = 0
        val adyacentesDeV = this.adyacentesSumidero(v)
        adyacentesDeV.forEach{
            gradoVerticeInterior = gradoVerticeInterior + 1
        }

        
        return gradoVerticeInterior
    }

    /*
        descripcion: Funcion que retorna un int que representa la cantidad de lados del digrafo
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo

        postcondiciones: int que representa la cantidad de lados o arcos de un digrafo

        tiempo de la operacion: O(1) 

     */
  override  fun obtenerNumeroDeLados() : Int {
        return this.numLados
    
    }

     /*
        descripcion: Funcion que retorna un int que representa la cantidad de vertices del digrafo
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo

        postcondiciones: int que representa la cantidad de vertices  de un digrafo

        tiempo de la operacion: O(1) 

     */
    override fun obtenerNumeroDeVertices() : Int {
        return this.numVertices
    
    }

    
    // funcion que retorna la lista de vertices de grafo

    fun listaDeVertices(): MutableList<Int> {
        return this.listaVertices
    }

   
     /*
        descripcion: Funcion que retorna una lista que contiene los arcos que 
        contengan como vertice inicial a v. 
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo y se le pase un int

        postcondiciones: lista que contiene los arcos que 
        contengan como vertice inicial a v

        tiempo de la operacion: O(n) siendo n el size de los numeros de lados

     */
    override fun adyacentes(v: Int) : Iterable<Arco> {
        if(v in this.listaVertices){
            var arcosAdyacentes = mutableListOf<Arco>()
            this.listaArcos.forEachIndexed {index,arco ->
            if (arco.primerV ==  v) {
                arcosAdyacentes.add(arco)
            }
        }
        
        arcosAdyacentes.asIterable()

        return arcosAdyacentes
        } else (
            throw Exception ("El vertice $v no pertenece al grafo")
        ) 
        
    
    }

     /*
        descripcion: Funcion que retorna una lista que contiene los arcos que 
        contengan como vertice sumidero a v. 
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo y se le pase un int

        postcondiciones: lista que contiene los arcos que 
        contengan como vertice sumidero a v

        tiempo de la operacion: O(n) siendo n el size de los numeros de lados

     */
    fun adyacentesSumidero(v: Int) : Iterable<Arco> {
        if(v in this.listaVertices){
            var arcosAdyacentes = mutableListOf<Arco>()
            this.listaArcos.forEachIndexed {index,arco ->
            if (arco.segundoV ==  v) {
                arcosAdyacentes.add(arco)
            }
        }
        
        arcosAdyacentes.asIterable()

        return arcosAdyacentes
        }  else (
            throw Exception ("El vertice $v no pertenece al grafo")
        )
        
    
    }
    

      /*
        descripcion: Funcion que retorna una lista que representa los lados del digrafo
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo

        postcondiciones: lista que representa los lados del digrafo

        tiempo de la operacion: O(1) 

     */
    fun arcos() : Iterable<Arco> {
       var arcosIterables = this.listaArcos.asIterable()
       
       return arcosIterables
	
    }
    
    /*
        descripcion: Funcion que retorna el la representacion en string de un digrafo
         
        precondiciones: que el objeto que invoca al metodo sea un digrafo

        postcondiciones: string que representa a un digrafo

        tiempo de la operacion: O(n) siendo n el size del grafo

     */
    override fun toString() : String {
        var representacionGrafo = ""
        this.grafo.forEachIndexed { index, lista ->
            
            representacionGrafo += "${index} : ${this.grafo[index]} \n"
            
         }
            
        return " ${representacionGrafo}"
    
     }
}
