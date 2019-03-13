import java.lang.NullPointerException

class Orgraph(vararg names: String) {
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

        val temp1 = vertices[nameFrom] ?: throw IllegalArgumentException("Неверно введены вершины")
        val temp2 = vertices[nameTo] ?: throw IllegalArgumentException("Неверно введены вершины")

            return temp1.neighborsOut[temp2] ?: throw IllegalArgumentException("Нет такой дуги")
    }

    fun addVertices(vararg names: String) {
        for (name in names) {
            if (name !in vertices.keys) {
                vertices[name] = Vertex(name)
            } else {throw IllegalArgumentException("Выберите другое имя, имя $name уже занято")}
        }
    }

    fun isVertexIn(name: String)= name in vertices.keys

    fun deleteVertex(name: String) {
        val temp = vertices[name] ?: throw IllegalArgumentException("Такой вершины нет в графе")

            for (v in temp.neighborsOut.keys) {
                v.neighborsIn.keys.remove(temp)
            }
            for (v in temp.neighborsIn.keys) {
                v.neighborsOut.keys.remove(temp)
            }
            vertices.remove(name)
    }

    fun changeName(name: String, newName: String) {
        if(newName in vertices.keys || name !in vertices.keys)
            throw IllegalArgumentException("Неверные данные")
        else {
           vertices[name]!!.name = newName
            vertices[newName] = vertices[name]!!
            vertices.remove(name)
        }
    }

    fun changeWeight(nameFrom: String, nameTo: String, newWeight: Int) {
        val temp1 = vertices[nameFrom] ?: throw IllegalArgumentException("Неверные данные")
        val temp2 = vertices[nameTo] ?: throw IllegalArgumentException("Неверные данные")
            if  (!temp1.neighborsOut.containsKey(temp2)) throw IllegalArgumentException("Нет такой дуги")
            temp1.neighborsOut[temp2] = newWeight
            temp2.neighborsIn[temp1] = newWeight

    }

    private fun connect(first: Vertex, second: Vertex, weight: Int) {
        first.neighborsOut[second] = weight
        second.neighborsIn[first] = weight
    }

    fun addEdge(first: String, second: String, weight: Int) = connect(this[first], this[second], weight)

    fun deleteEdge(nameFrom: String, nameTo: String) {

        val temp1 = vertices[nameFrom] ?: throw IllegalArgumentException("Неверно введены вершины")
        val temp2 = vertices[nameTo] ?: throw IllegalArgumentException("Неверно введены вершины")

            if (!temp1.neighborsOut.containsKey(temp2) ||
                    !temp2.neighborsIn.containsKey(temp1))
                throw IllegalArgumentException("Нет такой дуги")

        temp1.neighborsOut.remove(temp2)
        temp2.neighborsIn.remove(temp1)
        }

    fun neighborsIn(name: String) = vertices[name]?.neighborsIn?.keys?.map { it.name } ?: listOf()

    fun neighborsOut(name: String) = vertices[name]?.neighborsOut?.keys?.map { it.name } ?: listOf()
}
