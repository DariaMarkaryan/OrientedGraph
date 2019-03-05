import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import kotlin.test.assertEquals
import kotlin.test.*

internal class OrgraphTest {

    @Test()
    fun getWeight() {
        val subject = Orgraph("A","B","C")
        subject.addEdge("A", "B",12)
        subject.addEdge("B","B",0)
        assertEquals(12, subject.getWeight("A","B"))
        assertEquals(0,subject.getWeight("B","B"))
        val e = Assertions.assertThrows(IllegalArgumentException::class.java){
            subject.getWeight("B","C")
        }
        assertEquals("Некорректно введены данные",e.message)
    }

    @Test
    fun addVertices() {
        val subject = Orgraph("A")
        val e = Assertions.assertThrows(IllegalArgumentException::class.java){
            subject.addVertices("A")
        }
        assertEquals("Выберите другое имя, имя A уже занято",e.message)
        subject.addVertices("Spam")
        assertTrue(subject.isVertexIn("Spam"))
        assertFalse(subject.isVertexIn("B"))
    }

    @Test
    fun deleteVertex() {
        val subject = Orgraph("A")
        subject.deleteVertex("A")
        val e = Assertions.assertThrows(IllegalArgumentException::class.java){
            subject.deleteVertex("A")
        }
        assertEquals("Такой вершины нет в графе",e.message)

    }

    @Test
    fun changeName() {
        val subject = Orgraph("A")
        val e = Assertions.assertThrows(IllegalArgumentException::class.java){
            subject.changeName("B", "newName")
        }
        assertEquals("Неверные данные",e.message)
        subject.changeName("A","NewName")
        assertTrue(subject.isVertexIn("NewName"))
        assertFalse(subject.isVertexIn("A"))
    }

    @Test
    fun changeWeight() {
        val subject = Orgraph("A","B")
        subject.addEdge("A", "B",12)
        subject.addEdge("B","B",0)
        val e = Assertions.assertThrows(IllegalArgumentException::class.java){
            subject.changeWeight("B","A",100)
        }
        assertEquals("Неверные данные",e.message)
        subject.changeWeight("B","B",100)
        assertEquals(100, subject.getWeight("B","B"))
    }

    @Test
    fun addEdge() {
    }

    @Test
    fun deleteEdge() {
    }

    @Test
    fun neighborsIn() {
    }

    @Test
    fun neighborsOut() {
    }
}