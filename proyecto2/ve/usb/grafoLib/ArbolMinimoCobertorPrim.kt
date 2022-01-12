package ve.usb.grafoLib
import java.util.LinkedList
import java.util.Queue
/*
 Determina el árbol mínimo cobertor de un grafo no dirigido usando el algoritmo de Prim.
 La implementación del algoritmo de Prim debe estar basada en el uso de Binary heaps.
*/
public class ArbolMinimoCobertorPrim(val g: GrafoNoDirigido) {
    var listaDeAristas = g.aristas()
    var listaOrdenada : MutableList<Arista> = mutableListOf()
    var numVertices = g.obtenerNumeroDeVertices()
    var vertices = g.listaDeVertices()
    var arbolMinCobertor :MutableList<Arista> = mutableListOf()
    var listaPredecesores:MutableList<Int?> = mutableListOf()
    var listaKeys: MutableList<Double> =  mutableListOf()
 

    
    init {
        var r = 0
        for(i in 0..this.numVertices-1){
            this.listaKeys.add(i,Double.MAX_VALUE)
            this.listaPredecesores.add(i,null)
        }
        this.listaKeys[r] = 0.0
        var cola : Queue<Int> = LinkedList<Int>()
        cola.add(r)
        for( i in 0..this.numVertices-1) {
            if(this.listaKeys[i] != 0.0) {
                cola.add(i)
            }
        }

        while (!cola.isEmpty()){
            var u = cola.peek()
            cola.remove()
            var adyacentes = g.adyacentes(u)
            adyacentes.forEach{ a ->
                var otroV = a.elOtroVertice(u)
                if (cola.contains(otroV) && (a.aristaPeso < this.listaKeys[otroV])){
                    this.listaPredecesores[otroV] = u
                    this.listaKeys[otroV] = a.aristaPeso
                }
            }
        }
    }

    // Retorna un objeto iterable que contiene los lados del árbol mínimo cobertor.
    fun obtenerLados() : Iterable<Arista> {
        this.listaDeAristas.forEach{a ->
            var primerVertice = a.primerV
            var segundoVertice = a.segundoV
            var weight = a.aristaPeso
            for(i in 0..this.numVertices-1){
                if((primerVertice == this.listaPredecesores[i] && segundoVertice == i && weight == this.listaKeys[i]) || 
                (segundoVertice == this.listaPredecesores[i] && primerVertice == i && weight == this.listaKeys[i])){
                    this.arbolMinCobertor.add(a)
                }
            }
        }

        var arbolMinCobertorArreglado = this.arbolMinCobertor.sortedBy{it.aristaPeso}
        var arbolMinCobertorIterable = arbolMinCobertorArreglado.asIterable()
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
