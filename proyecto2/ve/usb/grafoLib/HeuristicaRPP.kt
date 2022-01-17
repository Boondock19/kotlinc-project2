package ve.usb.grafoLib
import java.io.File

public class HeuristicaRPP (instancia:String) {
    var RPP = GrafoNoDirigido(instancia,true,true)
    var Gr = GrafoNoDirigido(instancia,true,false,true)
    var componentes = ComponentesConexasDFS(RPP).numeroDeComponentesConexas()


    // Para que un grafo no dirigido sea conexo, debe ocurrir que tenga una sola componente Conexa, si tiene mas no es conexo
    // asi debemos revisar que exista al menos un adyacente para cada vertice, en caso contrario el grafo no es conexo 

   init {
       // Si el grafo original no es conexo entonces no podemos realizar el algoritmo
       val componentesConexasRPP = ComponentesConexasDFS(RPP)
        if (componentesConexasRPP.numeroDeComponentesConexas() != 1) throw RuntimeException("Grafo no es conexo")
       
       var Gp = this.Gr
       var componentes = ComponentesConexasDFS(Gp).numeroDeComponentesConexas()
        var esConexo = true
        // verificaremos que Gp sea conexo primero
        if (componentes > 1 ) {
            esConexo = false
        }
        println("Es conexo? : ${esConexo}")
        println("Es par? : ${Gp.esPar()}")

        if (esConexo) {
            if (Gp.esPar()) {
               /* 
                var vertices = Linea24(Gp)
                println("Resultado de correr Linea24 : ${vertices}")
                */
            } else {
                var g0 = Linea16(Gp,RPP)
                println("Resultado de correr Linea16 : ${g0}")
                var apareamiento = ApareamientoPerfectoAvido(g0)
                var m = apareamiento.obtenerM()
                println("Resultado de correr ApareamientoPerfectoAvido : ${m}")
                var respuesta18 = Linea1823(m,Gr,RPP)
                println("Resultado de correr Linea1823 : ${respuesta18}")
            }
        } else {
                // creamos  GC y luego vamos a crear Vt y Et para generar el GT
                println("Este es Gp que entra en 911 \n${Gp}")
                var grCopia = Linea911(Gp,RPP)
                println("Resultado de correr Linea911  ( ESTO SE INTRODUCE EN linea16 ): \n${grCopia}")
               
                //pirntl
                var g0 = Linea16(grCopia,RPP)
                println("Resultado de correr Linea16 : \n${g0}")
                
                var apareamiento = ApareamientoPerfectoAvido(g0)
               // var apareamiento = ApareamientoVertexScan(g0)
                var m = apareamiento.obtenerM()
                println("Resultado de correr ApareamientoPerfectoAvido : ${m}")
                
                var respuesta18 = Linea1823(m,grCopia,RPP)
                println("Resultado de correr Linea1823 : ${respuesta18}")

                 var numVertices = respuesta18.obtenerNumeroDeVertices()
                var listaAristas = respuesta18.aristas()
                var grafo2 = GrafoNoDirigido2(numVertices)
                listaAristas.forEach { a ->
                    grafo2.agregarArista2(a)
                }

                var ciclo = CicloEuleriano(grafo2).obtenerCicloEuleriano()
                var costo = CicloEuleriano(grafo2).obtenerCosto()
                println("Este es el ciclo ${ciclo}")
                println("Este es el costo del ciclo ${costo}")
                           /* 
                            var vertices = Linea24(respuesta18)
                            println("Resultado de correr Linea24 : ${vertices}")
                            */
                
        }

        // que sea par es que todos sus vertices tengan un grado par, es decir, grado mod 2 = 0
   } 
}