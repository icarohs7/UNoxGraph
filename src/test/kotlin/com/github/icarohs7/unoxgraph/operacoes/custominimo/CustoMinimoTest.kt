package com.github.icarohs7.unoxgraph.operacoes.custominimo

import com.github.icarohs7.unoxgraph.estatico.ArestaNegativaException
import com.github.icarohs7.unoxgraph.grafos.Grafo
import com.github.icarohs7.unoxgraph.grafos.Grafo.Aresta
import com.github.icarohs7.unoxkcommons.extensoes.cells
import io.kotlintest.Description
import io.kotlintest.inspectors.forAll
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec

class CustoMinimoTest : StringSpec() {
	private lateinit var cases: MutableList<Triple<Grafo.Ponderado, Int, Array<Double>>>
	
	init {
		"Deve calcular o caminho mínimo com o algoritmo de Dijkstra" {
			cases.forAll {
				val g = it.first
				val src = it.second
				val dist = it.third
				
				if (g.matrizAdjacencia.cells.any { it.value < 0 }) {
					shouldThrow<ArestaNegativaException> { g.custoMinimoDijkstra(src) }
				} else {
					g.custoMinimoDijkstra(src).distancias shouldBe dist
				}
			}
		}
		
		"Deve calcular o caminho mínimo com o algoritmo de Bellman Ford" {
			cases.forAll {
				val g = it.first
				val src = it.second
				val dist = it.third
				
				g.custoMinimoBellmanFord(src).distancias shouldBe dist
			}
		}
		
		"Deve calcular o caminho mínimo com o algoritmo de Floyd Warshall" {
			cases.forAll {
				val g = it.first
				val src = it.second
				val dist = it.third
				
				g.custoMinimoFloydWarshall().distancias[src] shouldBe dist
			}
		}
	}
	
	override fun beforeTest(description: Description) {
		cases = mutableListOf()
		
		val g = Grafo.Ponderado.ofASize(5, direcionado = true).also { grafo ->
			grafo += Aresta(0, 1, 1.0)
			grafo += Aresta(0, 4, 10.0)
			grafo += Aresta(0, 3, 3.0)
			
			grafo += Aresta(1, 2, 5.0)
			
			grafo += Aresta(2, 4, 1.0)
			
			grafo += Aresta(3, 2, 2.0)
			grafo += Aresta(3, 4, 6.0)
		}
		val d = arrayOf(0.0, 1.0, 5.0, 3.0, 6.0)
		cases.add(Triple(g, 0, d))
		
		val g2 = Grafo.Ponderado.ofASize(6, direcionado = true).also { grafo ->
			grafo += Aresta(0, 1, 1.0)
			grafo += Aresta(0, 2, 3.0)
			
			grafo += Aresta(1, 2, 1.0)
			grafo += Aresta(1, 3, 3.0)
			grafo += Aresta(1, 4, 2.0)
			
			grafo += Aresta(2, 3, 2.0)
			
			grafo += Aresta(3, 5, 2.0)
			
			grafo += Aresta(4, 3, -3.0)
			
			grafo += Aresta(5, 4, 3.0)
		}
		val d2 = arrayOf(0.0, 1.0, 2.0, 0.0, 3.0, 2.0)
		cases.add(Triple(g2, 0, d2))
		
		val g3 = Grafo.Ponderado.ofASize(5, direcionado = true).also { grafo ->
			grafo += Aresta(0, 1, 2.0)
			grafo += Aresta(0, 2, 4.0)
			grafo += Aresta(0, 4, 3.0)
			
			grafo += Aresta(1, 0, 2.0)
			grafo += Aresta(1, 2, 8.0)
			grafo += Aresta(1, 4, 1.0)
			
			grafo += Aresta(2, 0, 6.0)
			grafo += Aresta(2, 1, 2.0)
			grafo += Aresta(2, 3, 4.0)
			grafo += Aresta(2, 4, 3.0)
			
			grafo += Aresta(3, 0, 1.0)
			grafo += Aresta(3, 4, 5.0)
			
			grafo += Aresta(4, 3, 1.0)
		}
		val d3 = arrayOf(2.0, 0.0, 6.0, 2.0, 1.0)
		cases.add(Triple(g3, 1, d3))
	}
}