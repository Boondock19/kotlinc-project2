
package ve.usb.grafoLib
import java.io.File

public class GrafoNoDirigido: Grafo {

        val listaArchivo = mutableListOf<String>()
        override  var numVertices = 0
        override  var numLados = 0
        var listaAristas = mutableListOf<Arista>()
        var listaVertices = mutableListOf<Int>()
        var listaLados =  mutableListOf<String>()
        var grafo = Array(numVertices) {mutableListOf<Arista>()}
        
    
    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
	    this.numVertices = numDeVertices
        for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }
        this.grafo = Array(this.numVertices) {mutableListOf<Arista>()}
    
    }

    constructor(nombreArchivo: String, conPeso: Boolean) {
        var flag = conPeso
        
        //fun para leer y almacenar data de txts en forma de lista
        
        File(nombreArchivo).useLines{ lines -> lines.forEach { this.listaArchivo.add(it) }}
        
        // Almacenamos data del grafo como cantidad de vertices y lados
        
       this.numVertices = listaArchivo[0].toInt()
       this.numLados = listaArchivo[1].toInt()
       for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }
       this.grafo = Array(this.numVertices) {mutableListOf<Arista>()}
        
        // Creamos una subLista para poder trabajar con los lados
        
        this.listaLados = listaArchivo.subList(2, listaArchivo.size)
        
        

        /** 
            Creamos los Aristas y los agregamos en la lista de adyacencia dependiendo
            de si el archivo posee formato con peso o no.
        */ 
        
        if (flag) {
            this.listaLados.forEach { 
            val ladoSinSeparador = it.split(" ")
            
            val newArista = Arista(ladoSinSeparador[0].toInt(),ladoSinSeparador[1].toInt(),ladoSinSeparador[2].toDouble())
            this.listaAristas.add(newArista)
            
            }
        } else {
            
            this.listaLados.forEach { 
            val ladoSinSeparador = it.split(" ")
            val newArista = Arista(ladoSinSeparador[0].toInt(),ladoSinSeparador[1].toInt())
            this.listaAristas.add(newArista)
            }   
         } 
            this.grafo.forEachIndexed { index, lista ->
                var aristasFiltrada = this.listaAristas.filter {it.primerV == index }
                this.grafo.set(index, aristasFiltrada.toMutableList()) 
            }

            
    
    }

    // Constructor para el proyecto 2, con doble peso en los lados

    constructor(nombreArchivo: String, conPeso: Boolean,conPesoUToV:Boolean) {
        var flag = conPeso
        
        //fun para leer y almacenar data de txts en forma de lista
        
        File(nombreArchivo).useLines{ lines -> lines.forEach { this.listaArchivo.add(it) }}
        
        // Almacenamos data del grafo como cantidad de vertices y lados
        
       this.numVertices = listaArchivo[2].split("VERTICES : ")[1].split(" ")[1].split(" ")[0].toInt()
       println("Vertices del grafo  : ${this.numVertices}")
       this.numLados = listaArchivo[3].split("ARISTAS_REQ : ")[1].split(" ")[1].split(" ")[0].toInt()
       println("lados requeridos del grafo : ${this.numLados}")

       for ( i in 0..numVertices-1){
            this.listaVertices.add(i)
        }
       this.grafo = Array(this.numVertices) {mutableListOf<Arista>()}
       
        var primeraSeparacion = listaArchivo.indexOf("LISTA_ARISTAS_REQ :")
        var segundaSeparacion = listaArchivo.indexOf("LISTA_ARISTAS_NOREQ :")

        println("Valor de primera separacion ${primeraSeparacion}")
        println("Valor de primera separacion ${segundaSeparacion}")
        var  listaLadosReq = listaArchivo.subList((primeraSeparacion+1), segundaSeparacion)
        
       // println("Esta es  primera separacion ${listaLadosReq}")
       // println("Esta es la posicion de la primera separacion ${primeraSeparacion}")
        
       // println("Esta es la posicion de la primera separacion ${segundaSeparacion}")
        var listaLadosNoRequeridos = listaArchivo.subList((segundaSeparacion+1), listaArchivo.size)
        //println("Esta es  primera separacion ${listaLadosNoRequeridos}")

        // Lectura de una linea para crear una Arista.
        /*
        var aristaFiltro = listaLadosReq[0].split(" ")
        aristaFiltro = aristaFiltro.filterNot { it == "(" }
        aristaFiltro = aristaFiltro.filterNot { it == ")" }
        aristaFiltro = aristaFiltro.filterNot { it == " " }
        aristaFiltro = aristaFiltro.filterNot { it == "" }
         aristaFiltro = aristaFiltro.filterNot { it == "coste" }
         aristaFiltro = aristaFiltro.filterNot { it == "," }
         var filtroSinComas = aristaFiltro[0].replace(",","")
         var filtro = aristaFiltro[1].replace(")","")
         var  newArista = aristaFiltro.toMutableList()
         newArista.set(0,filtroSinComas)
         newArista.set(1,filtro)
         */
         //newArista[1] = filtro
        //newArista.replace("coste","")
        /*  
        newArista = newArista.replace("(","")
        newArista = newArista.replace(")","")
        newArista = newArista.replace("coste","")
        newArista = newArista.replace(" ","")
        */
        /* 
        var aristaFiltro2 = listaLadosReq[70].split(" ")
        aristaFiltro2 = aristaFiltro2.filterNot { it == "(" }
        aristaFiltro2 = aristaFiltro2.filterNot { it == ")" }
        aristaFiltro2 = aristaFiltro2.filterNot { it == " " }
        aristaFiltro2 = aristaFiltro2.filterNot { it == "" }
         aristaFiltro2 = aristaFiltro2.filterNot { it == "coste" }
         aristaFiltro2 = aristaFiltro2.filterNot { it == "," }
         var filtroSinComas2 = aristaFiltro2[0].replace(",","")
         var filtro2 = aristaFiltro2[1].replace(")","")
         var  newArista2 = aristaFiltro2.toMutableList()
         newArista2.set(0,filtroSinComas2)
         newArista2.set(1,filtro2) 
         println("Esta es la expresion original de una linea ${newArista}")
          println("Esta 0 ${newArista[0]}")
           println("Esta 1 ${newArista[1]}")
          println("Esta es la expresion original de una linea ${newArista2}")
        */
           
           listaLadosReq.forEach { line ->
            var aristaFiltro = line.split(" ")
            aristaFiltro = aristaFiltro.filterNot { it == "(" }
            aristaFiltro = aristaFiltro.filterNot { it == ")" }
            aristaFiltro = aristaFiltro.filterNot { it == " " }
            aristaFiltro = aristaFiltro.filterNot { it == "" }
            aristaFiltro = aristaFiltro.filterNot { it == "coste" }
            aristaFiltro = aristaFiltro.filterNot { it == "," }
            var filtroSinComas = aristaFiltro[0].replace(",","")
            var filtro = aristaFiltro[1].replace(")","")
            var  filtrado = aristaFiltro.toMutableList()
            filtrado.set(0,filtroSinComas)
            filtrado.set(1,filtro)
            
            val newArista = Arista(filtrado[0].toInt(),filtrado[1].toInt(),filtrado[2].toDouble(),filtrado[2].toDouble())
            this.listaAristas.add(newArista)
        }

            this.grafo.forEachIndexed { index, lista ->
                var aristasFiltrada = this.listaAristas.filter {it.primerV == index }
                this.grafo.set(index, aristasFiltrada.toMutableList()) 
            }

            println("Print del grafo luego de organizarlo : ${this.grafo.joinToString()}")
             println("Print del listaAristas : ${this.listaAristas}")
        
    }

    // Agrega un lado al grafo no dirigido
    fun agregarArista(a: Arista) {
        val newArista = a
        var aristasFiltrados = mutableListOf<Arista>()
        
        this.listaAristas.forEachIndexed { index,arista ->
            if ((newArista.primerV == arista.primerV && newArista.segundoV == arista.segundoV) || (newArista.primerV == arista.segundoV && newArista.segundoV == arista.primerV)) {
               throw Exception("El arco ${a} ya se encuentra en el grafo")
                }
        }
        
        
        this.listaAristas.add(newArista)
        aristasFiltrados.add(newArista)

        
        this.grafo.set(newArista.primerV,(this.grafo[newArista.primerV]+aristasFiltrados).toMutableList())
        this.numLados += 1 
        
        var stringGrafo = this.toString()
        
        return println("La arista ${a} fue agregado satisfactoriamente al grafo : ${stringGrafo}")

    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        return this.numLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
         return this.numVertices
    }

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<Arista> {
        if (v in this.listaVertices) {
              var aristasAdyacentes = mutableListOf<Arista>()
            this.listaAristas.forEachIndexed {index,arista ->
            if (arista.primerV ==  v || arista.segundoV == v) {
                aristasAdyacentes.add(arista)
            }
        }

        
        
        aristasAdyacentes.asIterable()

        return aristasAdyacentes
        } else (
            throw Exception ("El vertice $v no pertenece al grafo")
        )
      
    }

    // funcion que retorna la lista de vertices de grafo

    fun listaDeVertices(): MutableList<Int> {
        return this.listaVertices
    }
   
    
    // Grado del grafo
    override fun grado(v: Int) : Int {
        if (v in this.listaVertices) {
             var grafoGrado = 0
            val adyacentesDeV = this.adyacentes(v)
            adyacentesDeV.forEach{
                grafoGrado = grafoGrado + 1
            }

        
        return grafoGrado
        }
       throw Exception ("El vertice $v no pertenece al grafo")
    }
    

      /*
        descripcion: Funcion que retorna una lista que representa los lados del grafo no dirigido
         
        precondiciones: que el objeto que invoca al metodo sea un grafo no dirigido

        postcondiciones: lista que representa los lados del grafo no dirigido

        tiempo de la operacion: O(1) 

     */
    fun aristas() : Iterable<Arista> {
        var aristaIterables = this.listaAristas.asIterable()
       
       return aristaIterables
    
    }

    /*
        descripcion: Funcion que retorna el la representacion en string de un grafo no dirigido
         
        precondiciones: que el objeto que invoca al metodo sea un grafo no dirigido

        postcondiciones: string que representa a un grafo no dirigido

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
