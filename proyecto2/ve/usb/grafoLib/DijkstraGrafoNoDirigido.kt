package ve.usb.grafoLib
import java.util.Stack

/*
DESCRIPCION: Implementación del algoritmo de Dijkstra para encontrar los caminos de costo mínimo desde un vértice 
fuente s fijo, a partir de una clases que contiene funciones relacionada y/o necesarias para la
implementacion.
*/
public class DijkstraGrafoNoDirigido(val g: GrafoNoDirigido, val s: Int) {

    var n = g.obtenerNumeroDeVertices()
    var pred = Array(n){(-1)}
    var distancia = Array(n){(Double.POSITIVE_INFINITY)}
    var ese = mutableListOf<Int>()
    var q = mutableListOf<Int>()
    
    /*
    DESCRIPCION: Implementacion del algoritmo Dijkstra a través de una cola de prioridad, para reservar 
    en orden de costos (de menor a mayor), de manera que se obtiene el costo de los caminos mínimos desde 
    s a cualquier vertice, lo cual se guarda en un arreglo mientras que los predecesores se reserva en otro
    arreglo.
    PRECONDICION: (para todo (u,v) perteneciente E : w(u,v) >= 0)
    POSTCONDICION: (para todo v perteniciente V : distancia[v] >= 0)
    TIEMPO: O(|E|log|V|)
    */
    init {
        if (this.existeCostoNeg()) {
            throw RuntimeException("El grafo tiene costo negativo")
        }

        distancia[s] = 0.0
        var vertice  =  s 
        for (i in 0..n-1) {
            q.add(i)
        }

    
        while (!q.isEmpty()) {
            var min = Double.POSITIVE_INFINITY
            for (i in q) {
                //var min = Double.POSITIVE_INFINITY
                if (min > distancia[i]) {
                    min = distancia[i]
                    vertice = i
                }
            }
            var u = vertice
            q.remove(vertice)
            ese.add(u)
            g.adyacentes(u).forEach { arista ->
                var z = arista.cualquieraDeLosVertices()
                if (z == u) {
                    var v = arista.elOtroVertice(z)
                    var w = arista.peso()
                    var suma = distancia[u] + w
                    if (distancia[v] > suma) {
                        distancia[v] = suma
                        pred[v] = u 
                    }
                } else {
                    var w = arista.peso()
                    var suma = distancia[u] + w
                    if (distancia[z] > suma) {
                        distancia[z] = suma
                        pred[z] = u 
                    }
                }

            }
        }
    }

    /*
    DESCRIPCION: Funcion que retorna si existe un camino desde la fuente s hasta el vertice v
    PRECONDICION: (v pertenece a V)
    POSTCONDICION: (verificar == (dV != Double.POSITIVE_INFINITY))
    TIEMPO: O(|V|)
    */
    fun existeUnCamino(v: Int) : Boolean { 
        if (!(v in 0..n-1)) {
            throw RuntimeException ("v no es un vertice del grafo")
        }
        var dV = distancia[v]
        var verificar = (dV != Double.POSITIVE_INFINITY)
        return verificar
    }

    /*
    DESCRIPCION: Funcion que retorna el costo del CCM hasta el vertice v desde la fuente, el cual 
    se encuentra en el arreglo distancia.
    PRECONDICION: (v pertence a V)
    POSTCONDICION: (dV == distancia[v])
    TIEMPO: O(|V|)
    */
    fun costoHasta(v: Int) : Double { 
        if (!(v in 0..n-1)) {
            throw RuntimeException ("v no es un vertice del grafo")
        }
        var dV = distancia[v]
        return dV
    }

    /*
    DESCRIPCION:
    PRECONDICION:
    POSTCONDICION:
    TIEMPO: O (|V|*|E|)
    */
    fun obtenerCaminoDeCostoMinimo(v: Int) : Iterable<Arista> { 
        if (!(v in 0..n-1)) {
            throw RuntimeException ("v no es un vertice del grafo")
        }
        var c = mutableListOf<Arista>()
        var camino = mutableListOf<Arista>()
        var stack = Stack<Arista>()
        var existe = this.existeUnCamino(v)
        if (existe) {
           var aux = v
           while (aux != s) {
               g.aristas().forEach {arista ->
                    var fuente = arista.cualquieraDeLosVertices()
                    var sumidero = arista.elOtroVertice(fuente)
                    var peso = arista.peso()
                    if ((fuente == pred[aux]) && (sumidero == aux)) {
                        c.add(arista)
                        aux = pred[aux]
                    }
               }
           }
        }
        stack.addAll(c)
        while (!stack.isEmpty()) {
            var aux = stack.pop()
            camino.add(aux)
        }
        return camino
    }

    /*
    DESCRIPCION: Funcion que encuentra un costo negativo en los lados de un digrafo, retornando una 
    variable booleana.
    PRECONDICION: (existe w: E -> R)
    POSTCONDICION: (neg == (existe w(u,v) : (para todo u,v perteneciente a V : 0 > w(u,v) )))
    TIEMPO: O(|E|)
    */
    fun existeCostoNeg() : Boolean {
        var neg = false
        g.aristas().forEach {arista ->
            var w = arista.peso()
            if (w < 0) {
                neg = true
                return neg
            }
        }
        return neg
    }
}
