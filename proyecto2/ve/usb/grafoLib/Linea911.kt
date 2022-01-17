package ve.usb.grafoLib

fun Linea911 (gr: GrafoNoDirigido, grpp: GrafoNoDirigido) : GrafoNoDirigido {
    var ccddfs = ComponentesConexasDFS(gr)
    var numccddfs = ccddfs.numeroDeComponentesConexas()
    var numCompMayor1 = 0
    var listaCompMayor1:MutableList<MutableSet<Int>> = mutableListOf()
    var n = gr.obtenerNumeroDeVertices()
    var ladosGRPP = grpp.aristas()
    var grCopia = gr

    //Obtenemos las componentes
    for (i in 0..n-1) {
        var comp = mutableSetOf<Int>()
        var nci = ccddfs.obtenerComponente(i)
        comp.add(i)
        for (j in 0..n-1) {
            var ncj = ccddfs.obtenerComponente(j)
            if (nci == ncj) {
                comp.add(j)
            }
        }
        var ncomp = comp.size
        if (!(listaCompMayor1.contains(comp))) {
            listaCompMayor1.add(comp)
        }
    }
   // println("Esto es lo que esta en ListaCompMayor1 : ${listaCompMayor1}")
    
    //Obtenemos la cantidad de componentes de R 
    numCompMayor1 = listaCompMayor1.size
    
    //Buscamos los costos minimos entre componentes y seran guardados en una matriz |numccddfs|x|numccddfs|
    var mcostos = Array<Array<Double>>(numccddfs){Array<Double>(numccddfs){Double.POSITIVE_INFINITY}}
    var listaver =  mutableListOf<Arista>()
    listaCompMayor1.forEach {comp1 ->
        var min = Double.POSITIVE_INFINITY
        comp1.forEach {i ->
            var dijmod = DijkstraGrafoNoDirigido(grpp,i)
            listaCompMayor1.forEach {comp2 ->
                if (comp2 != comp1) {
                    comp2.forEach {j ->
                        var costo = dijmod.costoHasta(j)
                        if  (min > costo) {
                            min = costo
                            var nc1 = ccddfs.obtenerComponente(i)
                            var nc2 = ccddfs.obtenerComponente(j)
                            mcostos[nc1][nc2] = min
                            var agregar = Arista(i,j,min)
                            listaver.add(agregar)
                        }   
                    }
                }
            }
        }
    }

   // println("Listaver ${listaver}")
    //Se arma el grafo componente 
    var gComp = GrafoNoDirigido(numCompMayor1)
    for (i in 0..numCompMayor1-1){
        for (j in 0..numCompMayor1-1) {
            if (i != j) {
                listaCompMayor1.forEach {comp1 ->
                    var uno = comp1.first()
                    listaCompMayor1.forEach {comp2 ->
                        var dos = comp2.first()
                        var nc1 = ccddfs.obtenerComponente(uno)
                        var nc2 = ccddfs.obtenerComponente(dos)
                        var agregar = Arista(i, j, mcostos[nc1][nc2])
                        gComp.agregarArista(agregar)
                    }
                }
            }
        }
    }
    //Buscamos el arbol minimo cobertor
    var arbolMC = ArbolMinimoCobertorKruskal(gComp)
   // println("Este es el arbol de kruskal ${arbolMC.obtenerLados()}")
    var eAux = arbolMC.obtenerLados()
    var arrayAux = Array(numCompMayor1) {mutableSetOf<Int>()}
    listaCompMayor1.forEachIndexed {k,comp ->
        arrayAux[k] = comp
    }
    
    //Buscamos los lados correspondientes al grafo GRPP, que se encuentran en los lados del AMC
    
    var et = mutableListOf<Arista>()
   // println("arrayAux ${arrayAux.joinToString()}")
   // println("aristas del GRPP ${ladosGRPP}")
    eAux.forEach {lado ->
        var u = lado.cualquieraDeLosVertices()
        var v = lado.elOtroVertice(u)
        var peso = lado.peso()
        listaver.forEach {l ->
            var l1 = l.cualquieraDeLosVertices()
            var l2 = l.elOtroVertice(l1)
            var p = l.peso()
            if (peso == p) {
                if (arrayAux[u].contains(l1) || arrayAux[v].contains(l1)) {
                    et.add(l)
                }
            }
        }
    }

   // println("Este es et ${et}")
     var et1 = et.distinctBy {listOf(it.segundoV,it.primerV)}
    //println("Este es et1 ${et1}")
        
    var copia =GrafoNoDirigido2(n)
    var listaAristasGr = gr.aristas()
    listaAristasGr.forEach{ a ->
        copia.agregarArista2(a)
    }
   // println("Esta son las aristas de la copia luego de agregarle las aristas de gr ${copia.aristas()}")
    et.forEach{ lEt ->
        copia.agregarArista2(lEt)
    }

   // println("Esta son las aristas de la copia luego de agregarle ET ${copia.aristas()}")
    var listaAristasCopia = copia.aristas()
    //Agregamos los lados de Et al grafo gR
    var grafoRetornable = GrafoNoDirigido(n) // n es el tamano de Gr
    listaAristasCopia.forEach {lado ->
        grafoRetornable.agregarArista(lado)
    }
    
    return grafoRetornable
}