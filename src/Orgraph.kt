import java.lang.NullPointerException

class Orgraph(vararg names: String) {
    var numOfVertices = 0
    private val vertices = mutableMapOf<String, Vertex>()

    inner class Vertex(var name: String) {
        val neighborsIn = mutableMapOf<Vertex, Int>()
        val neighborsOut = mutableMapOf<Vertex, Int>()
    }

    init {
            this.addVertices(*names)
    }

    private operator fun get(name: String) =
            vertices[name] ?: throw IllegalArgumentException("нет такой вершины")

    fun getWeight(nameFrom: String, nameTo: String): Int {
        try {
            if (!vertices[nameFrom]!!.neighborsOut.containsKey(vertices[nameTo]) &&
                    !vertices[nameTo]!!.neighborsIn.containsKey(vertices[nameFrom])) throw IllegalArgumentException("Нет такой дуги")
            return vertices[nameFrom]!!.neighborsOut[vertices[nameTo]]!!
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("Неверно введены вершины")
        }
    }

    fun addVertices(vararg names: String) {
        for (name in names) {
            if (name !in vertices.keys) {
                vertices[name] = Vertex(name)
                numOfVertices++
            } else {throw IllegalArgumentException("Выберите другое имя, имя $name уже занято")}
        }
    }

    fun isVertexIn(name: String)= name in vertices.keys

    fun deleteVertex(name: String) {
        try {
            for (v in vertices[name]!!.neighborsOut.keys) {
                v.neighborsIn.keys.remove(vertices[name])
            }
            for (v in vertices[name]!!.neighborsIn.keys) {
                v.neighborsOut.keys.remove(vertices[name])
            }
            vertices.remove(name)
            numOfVertices--
        } catch (e: NullPointerException) {
            throw IllegalArgumentException("Такой вершины нет в графе")
        }
    }

    fun changeName(name: String, newName: String) {
        if(newName in vertices.keys || name !in vertices.keys) throw IllegalArgumentException("Неверные данные")
        else {
           vertices[name]!!.name = newName
            vertices[newName] = vertices[name]!!
            vertices.remove(name)
        }
    }

    fun changeWeight(nameFrom: String, nameTo: String, newWeight: Int) {
        try{
            if (!vertices[nameFrom]!!.neighborsOut.containsKey(vertices[nameTo]) &&
                    !vertices[nameTo]!!.neighborsIn.containsKey(vertices[nameFrom])) throw IllegalArgumentException("Нет такой дуги")
            vertices[nameFrom]!!.neighborsOut[vertices[nameTo]!!] = newWeight
            vertices[nameTo]!!.neighborsIn[vertices[nameFrom]!!] = newWeight
        } catch(e: NullPointerException) {
            throw IllegalArgumentException("Неверные данные")
        }
    }

    private fun connect(first: Vertex, second: Vertex, weight: Int) {
        first.neighborsOut[second] = weight
        second.neighborsIn[first] = weight
    }

    fun addEdge(first: String, second: String, weight: Int) = connect(this[first], this[second], weight)

    fun deleteEdge(nameFrom: String, nameTo: String) {
        try {
            if (!vertices[nameFrom]!!.neighborsOut.containsKey(vertices[nameTo]) ||
                    !vertices[nameTo]!!.neighborsIn.containsKey(vertices[nameFrom]))
                throw IllegalArgumentException("Нет такой дуги")

            vertices[nameFrom]!!.neighborsOut.remove(vertices[nameTo])
            vertices[nameTo]!!.neighborsIn.remove(vertices[nameFrom])
        } catch (e: NullPointerException){
            throw IllegalArgumentException("Неверно введены вершины")
        }
    }

    fun neighborsIn(name: String) = vertices[name]?.neighborsIn?.keys?.map { it.name } ?: listOf()

    fun neighborsOut(name: String) = vertices[name]?.neighborsOut?.keys?.map { it.name } ?: listOf()
}