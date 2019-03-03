package orgraph

class Graph {
    var numOfVertices = 0
    private val vertices = mutableMapOf<String, Vertex>()

    inner class Vertex(val name: String) {
        val neighborsIn = mutableMapOf<Vertex, Int>()
        val neighborsOut = mutableMapOf<Vertex, Int>()
        val countingNumber = numOfVertices
    }

    private operator fun get(name: String) = vertices[name] ?: throw IllegalArgumentException()

    fun getWeight(nameFrom: String, nameTo: String) = vertices[nameFrom]!!.neighborsOut[vertices[nameTo]]!!

    fun addVertex(name: String) {
        if(name !in vertices.keys) {
            vertices[name] = Vertex(name)
            numOfVertices++
        } else throw IllegalArgumentException("Выберите другое имя, это уже занято")
    }

    fun deleteVertex(name: String) {
       for(v in vertices[name]!!.neighborsIn.keys){
           v.neighborsOut.remove(vertices[name])
       }
        vertices.remove(name)
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

    fun changeWeight(nameFrom: String, nameTo: String, newWeight: Int) {
        vertices[nameFrom]!!.neighborsOut[vertices[nameTo]!!] = newWeight
        vertices[nameTo]!!.neighborsIn[vertices[nameFrom]!!] = newWeight
    }

    private fun connect(first: Vertex, second: Vertex, weight: Int) {
        first.neighborsOut[second] = weight
        second.neighborsIn[first] = weight
    }

    fun addEdge(first: String, second: String, weight: Int) = connect(this[first], this[second], weight)

    fun deleteEdge(nameFrom: String, nameTo: String) {
        try {
            vertices[nameFrom]!!.neighborsOut.remove(vertices[nameTo])
        vertices[nameTo]!!.neighborsIn.remove(vertices[nameFrom])
        } catch (e: IllegalArgumentException) {
            println("Не существует такой дуги")}
    }

    fun neighborsIn(name: String) = vertices[name]?.neighborsIn?.keys?.map { it.name } ?: listOf()

    fun neighborsOut(name: String) = vertices[name]?.neighborsOut?.keys?.map { it.name } ?: listOf()
}