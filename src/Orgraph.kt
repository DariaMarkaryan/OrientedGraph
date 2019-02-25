class Graph {
    var numOfVertices = 0

    inner class Vertex(val name: String,) {
        val neighborsIn = mutableMapOf<Vertex, Int>()
        val neighborsOut = mutableMapOf<Vertex, Int>()
        val countingNumber = numOfVertices
    }

    private val vertices = mutableMapOf<String, Vertex>()

    private operator fun get(name: String): Vertex = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
        numOfVertices++
    }

    fun deleteVertex(name: String) {}

    fun changeName(name: String){}

    fun addEdge(nameFrom: String,nameTo: String) {}

    fun changeWeight(nameFrom: String, nameTo: String) {}

    private fun connect(first: Vertex, second: Vertex, weight: Int) {
        first.neighborsOut[second] = weight
        second.neighborsIn[first] = weight
    }

    fun connect(first: String, second: String) = connect(this[first], this[second]){}

    fun deleteEdge(nameFrom: String, nameTo: String) {}

    fun neighborsIn(name: String) = vertices[name]?.neighborsIn?.keys?.map { it.name } ?: listOf()

    fun neighborsOut(name: String) = vertices[name]?.neighborsOut?.keys?.map { it.name } ?: listOf()
}