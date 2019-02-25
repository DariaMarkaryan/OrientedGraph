class Graph {
    var numOfVertices = 0

    private val vertices = mutableMapOf<String, Vertex>()

    inner class Vertex(val name: String) {
        val neighborsIn = mutableMapOf<Vertex, Int>()
        val neighborsOut = mutableMapOf<Vertex, Int>()
        val countingNumber = numOfVertices
    }

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun addVertex(name: String) {
        vertices[name] = Vertex(name)
        numOfVertices++
    }

    fun deleteVertex(name: String) {
        numOfVertices--
    }

    fun changeName(name: String, newName: String) {
        if(newName in vertices.keys || name !in vertices.keys) throw IllegalArgumentException()
        else {
            vertices[newName] = vertices[name]!!
            for (neighbour in vertices[newName]!!.neighborsIn.keys) {
                neighbour.neighborsOut.remove(vertices[name])
                neighbour.neighborsOut[vertices[newName]!!] = getWeight(neighbour.name, newName)
            }
            for (neighbour in vertices[newName]!!.neighborsOut.keys) {
                neighbour.neighborsIn.remove(vertices[name])
                neighbour.neighborsOut[vertices[name]!!] = getWeight(newName, neighbour.name)
            }
                vertices.remove(name)
        }
    }

    fun addEdge(nameFrom: String,nameTo: String) {}

    fun changeWeight(nameFrom: String, nameTo: String) {}

    fun getWeight(nameFrom: String, nameTo: String) = vertices[nameFrom]!!.neighborsOut[vertices[nameTo]]!!

    private fun connect(first: Vertex, second: Vertex, weight: Int) {
        first.neighborsOut[second] = weight
        second.neighborsIn[first] = weight
    }

    fun connect(first: String, second: String, weight: Int) = connect(this[first], this[second], weight)

    fun deleteEdge(nameFrom: String, nameTo: String) {}

    fun neighborsIn(name: String) = vertices[name]?.neighborsIn?.keys?.map { it.name } ?: listOf()

    fun neighborsOut(name: String) = vertices[name]?.neighborsOut?.keys?.map { it.name } ?: listOf()
}