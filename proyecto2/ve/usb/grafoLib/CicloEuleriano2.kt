
package ve.usb.grafoLib

public class CicloEuleriano(val g: GrafoNoDirigido2) {

    val cicloEuleriano:MutableList<Int> = mutableListOf()
    var costo = 0.0
    
    init {


        

        val camino:MutableList<Int>  = mutableListOf()

        camino.add(0)
        var v = 0
        var u = 0
        var size = camino.size
        var adyacentesGenerales : MutableList<MutableList<Arista>> = mutableListOf()
        for (i in 0..g.obtenerNumeroDeVertices()-1){ 
            var ady = g.adyacentes(i).toMutableList()
            adyacentesGenerales.add(ady)
        }

        println("Adyancentes generales ${adyacentesGenerales}")

        while (camino.size > 0) {
            if (adyacentesGenerales[v].size > 0) {
                camino.add(v)
                val a = adyacentesGenerales[v][0]
                u = a.elOtroVertice(v)

                costo += a.obtenerPeso()
                adyacentesGenerales[v].remove(a)
                adyacentesGenerales[u].remove(a)

                v = u
            }
            else {
                cicloEuleriano.add(v)
                v = camino.removeAt(camino.size-1)
            }
        }
    }
    
  
 
    
    
    fun obtenerCicloEuleriano() : Iterable<Int> {
        return cicloEuleriano
    }
    
    fun obtenerCosto() : Double {
        return costo
    }

    
}