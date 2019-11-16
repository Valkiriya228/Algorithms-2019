package lesson3

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import kotlin.test.Test

class JavaHeadSetTest : AbstractHeadTailTest() {

    @BeforeEach
    fun fillTree() {
        fillTree { BinaryTree() }
    }

    @Test
    @Tag("Normal")
    fun headSetTest() {
        doHeadSetTest()
    }

    @Test
    @Tag("Hard")
    fun headSetRelationTest() {
        doHeadSetRelationTest()
        doHeadSetRelationTest1()
    }

    @Test
    @Tag("Normal")
    fun tailSetTest() {
        doTailSetTest()
    }

    @Test
    @Tag("Hard")
    fun tailSetRelationTest() {
        doTailSetRelationTest()
        doTailSetRelationTest1()
    }

    @Test
    @Tag("Impossible")
    fun subSetTest() {
        doSubSetTest()
        doSubSetRelationTest()
    }
}