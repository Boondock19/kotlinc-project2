package ve.usb.grafoLib

import java.io.File
import kotlin.system.measureTimeMillis

/* 
   Implementación del algoritmo DFS. 
   Con la creación de la instancia, se ejecuta el algoritmo DFS
   desde todos los vértices del grafo
*/
public class HeuristicaRPP(val G: GrafoNoDirigido, val lados:Iterable<Arista>, val av: String) {
    
    init {
        
        validarEntrada()

        val mapaGpaG: MutableList<Int> = mutableListOf()
        val mapaGaGp: MutableMap<Int,Int> = mutableMapOf()

        var nVerticesGR = 0
        lados.forEach{
            if (!mapaGpaG.contains(it.a)) {
                mapaGpaG.add(it.a)
                mapaGaGp.set(it.a, nVerticesGR)
                nVerticesGR++
            }
            if (!mapaGpaG.contains(it.b)) {
                mapaGpaG.add(it.b)
                mapaGaGp.set(it.b, nVerticesGR)
                nVerticesGR++
            }
        }

        val GR = GrafoNoDirigido(nVerticesGR)
        lados.forEach{
            val u = mapaGaGp.get(it.a) as Int
            val v = mapaGaGp.get(it.b) as Int
            GR.agregarArista(Arista(u, v, it.obtenerPeso()))
        }

        // println(mapaGpaG)
        // println(GR)

        val Gp = copiarGrafoNoDirigido(GR)

        val CCGp = ComponentesConexasDFS(Gp)
        val nCC = CCGp.numeroDeComponentesConexas()
        if (nCC > 1) {

            
            val GC = GrafoNoDirigido(nCC)

            val listaDijkstra = mutableListOf<DijkstraGrafoNoDirigido>()
            G.vertices().forEach {
                listaDijkstra.add(DijkstraGrafoNoDirigido(G, it))
            }
            
            val mapaCCM = mutableMapOf<Arista, Iterable<Arista>>()
            (0..nCC-1).forEach{Ci ->
                val VCi = CCGp.verticesDeLaComponente(Ci)
                (Ci+1..nCC-1).forEach{Cj ->
                    if (Ci != Cj) {
                        val VCj = CCGp.verticesDeLaComponente(Cj)
                        var wCiCj = Double.POSITIVE_INFINITY
                        var ccm:Iterable<Arista> = mutableListOf()
                        VCi.forEach{ uCi ->
                            val DJuCi = listaDijkstra[mapaGpaG[uCi]]
                            VCj.forEach{ uCj ->
                                val wccm = DJuCi.costoHasta(mapaGpaG[uCj])
                                if (wccm < wCiCj) { 
                                    wCiCj = wccm 
                                    ccm = DJuCi.obtenerCaminoDeCostoMinimo(mapaGpaG[uCj])
                                }
                            }
                        }
                        val aristaGC = Arista(Ci, Cj, wCiCj) 
                        GC.agregarArista(aristaGC)
                        mapaCCM.set(aristaGC, ccm)
                    }
                }
            }
            // (0..nCC-1).forEach{
            //     println("$it " + CCGR.verticesDeLaComponente(it).map{mapaGpaG[it]})
            // }
            // println(GC)
            // mapaCCM.forEach{
            //     println(""+ it.key + " " + it.value)
            // }

            val Et = mutableListOf<Arista>()
            ArbolMinimoCobertorKruskal(GC).obtenerLados().forEach{ aGC ->

                mapaCCM.get(aGC)!!.forEach{ aG ->
                    if (!Et.contains(aG)) Et.add(aG)
                }  
            }

            Et.forEach{
                if (!mapaGpaG.contains(it.a)) {
                    mapaGpaG.add(it.a)
                    mapaGaGp.set(it.a, Gp.nVertices)
                    Gp.nVertices++
                    Gp.listaAdyacencia.add(mutableListOf())
                }
                if (!mapaGpaG.contains(it.b)) {
                    mapaGpaG.add(it.b)
                    mapaGaGp.set(it.b, Gp.nVertices)
                    Gp.nVertices++
                    Gp.listaAdyacencia.add(mutableListOf())
                }
                val u = mapaGaGp.get(it.a) as Int
                val v = mapaGaGp.get(it.b) as Int
                Gp.agregarArista(Arista(u, v, it.obtenerPeso()))
            }

            // println(mapaGpaG)
            // println(Gp)

            val mapaG0aG: MutableList<Int> = mutableListOf()
            val mapaGaG0: MutableMap<Int,Int> = mutableMapOf()

            var nVerticesG0 = 0

            Gp.vertices().forEach{
                if (Gp.grado(it)%2 == 1) {
                    mapaG0aG.add(mapaGpaG[it])
                    mapaGaG0.set(mapaGpaG[it], nVerticesG0)
                    nVerticesG0++
                }
            }
            val G0 = GrafoNoDirigido(nVerticesG0)

            mapaCCM.clear()
            G0.vertices().forEach{ u->
                (u+1 ..G0.nVertices-1).forEach{ v->
                    val DJu = listaDijkstra[mapaG0aG[u]]
                    val a = Arista(u, v, DJu.costoHasta(mapaG0aG[v]))
                    mapaCCM.set(a, DJu.obtenerCaminoDeCostoMinimo(mapaG0aG[v]))
                    G0.agregarArista(a)
                }
            }

            // println(G0)
            // mapaCCM.forEach{
            //     println(""+ it.key + " " + it.value)
            // }
            // println(mapaG0aG)

            val M = if (av == "a")  ApareamientoPerfectoAvido(G0).M else ApareamientoVertexScan(G0).M

            // println(M)
            // M.forEach{
            //     println("$it "  + mapaCCM.get(it))
            // }

            M.forEach{ m->
                mapaCCM.get(m)!!.forEach{ 
                    if (!mapaGpaG.contains(it.a)) {
                        mapaGpaG.add(it.a)
                        mapaGaGp.set(it.a, Gp.nVertices)
                        Gp.nVertices++
                        Gp.listaAdyacencia.add(mutableListOf())
                    }
                    if (!mapaGpaG.contains(it.b)) {
                        mapaGpaG.add(it.b)
                        mapaGaGp.set(it.b, Gp.nVertices)
                        Gp.nVertices++
                        Gp.listaAdyacencia.add(mutableListOf())
                    }
                    
                    val u = mapaGaGp.get(it.a) as Int
                    val v = mapaGaGp.get(it.b) as Int

                    Gp.agregarArista(Arista(u, v, it.obtenerPeso()))

                }
            }

            // print(Gp)
            val CE = CicloEuleriano(Gp)
            val C = CE.obtenerCicloEuleriano().map{mapaGpaG[it]}
            println(C.joinToString(prefix = "", postfix = "", separator = " "))
            println(CE.costo)

        } 

    }

    fun validarEntrada() {
        val CC = ComponentesConexasDFS(G)
        
        if (CC.numeroDeComponentesConexas() != 1) throw RuntimeException("Grafo no es conexo")
        
        lados.forEach{
            if (G.arista(it.a, it.b, it.obtenerPeso()) == null) throw RuntimeException("$it no es una arista en G")
        }
    }

}

fun leerInstancia(nombreArchivo:String):GrafoNoDirigido {

    val data:MutableList<String> = mutableListOf()
    File(nombreArchivo).forEachLine() { data.add(it) }



    var n = data[2].replace("VERTICES :  ", "").toInt()
    val G = GrafoNoDirigido(n)
    val ar = data[3].replace("ARISTAS_REQ :  ", "").toInt()
    val an = data[4].replace("ARISTAS_NOREQ :  ", "").toInt()

    

    (6..5+ar).forEach{ i->
        var parsedline = data[i].split(" ").filter{it !=""}
        var u = parsedline[1].replace(",", "").toInt()-1
        var v = parsedline[2].replace(")", "").toInt()-1
        var w = parsedline[4].toDouble()

        G.agregarArista(Arista(u, v, w))
    }

    (7+ar..6+ar+an).forEach{ i->
        var parsedline = data[i].split(" ").filter{it !=""}
        var u = parsedline[1].replace(",", "").toInt()-1
        var v = parsedline[2].replace(")", "").toInt()-1
        var w = parsedline[4].toDouble()

        val a = Arista(u, v, w)
        G.agregarArista(a)
        G.req.add(a)

    }

    return G
}

fun main(args:Array<String>) {

    val av = args[0]
    if (av != "a" && av != "v") throw RuntimeException("parametros de entrada incorrectos: $av")

    val time = measureTimeMillis{

        val G = leerInstancia(args[1])
        HeuristicaRPP(G, G.req, av)
    }

    println("" + time + " segs.")
    
}
