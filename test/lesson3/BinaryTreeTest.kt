package lesson3

import org.junit.jupiter.api.Tag
import kotlin.test.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BinaryTreeTest {
    private fun testAdd(create: () -> CheckableSortedSet<Int>) {
        val tree = create()
        assertEquals(0, tree.size)
        assertFalse(tree.contains(5))
        tree.add(10)
        tree.add(5)
        tree.add(7)
        tree.add(10)
        assertEquals(3, tree.size)
        assertTrue(tree.contains(5))
        tree.add(3)
        tree.add(1)
        tree.add(3)
        tree.add(4)
        assertEquals(6, tree.size)
        assertFalse(tree.contains(8))
        tree.add(8)
        tree.add(15)
        tree.add(15)
        tree.add(20)
        assertEquals(9, tree.size)
        assertTrue(tree.contains(8))
        assertTrue(tree.checkInvariant())
        assertEquals(1, tree.first())
        assertEquals(20, tree.last())
    }

    @Test
    @Tag("Example")
    fun testAddKotlin() {
        testAdd { createKotlinTree() }
    }

    @Test
    @Tag("Example")
    fun testAddJava() {
        testAdd { createJavaTree() }
    }

    private fun <T : Comparable<T>> createJavaTree(): CheckableSortedSet<T> = BinaryTree()

    private fun <T : Comparable<T>> createKotlinTree(): CheckableSortedSet<T> = KtBinaryTree()

    private fun testRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val binarySet = create()
            assertFalse(binarySet.remove(42))
            for (element in list) {
                binarySet += element
            }
            val originalHeight = binarySet.height()
            val toRemove = list[random.nextInt(list.size)]
            val oldSize = binarySet.size
            assertTrue(binarySet.remove(toRemove))
            assertEquals(oldSize - 1, binarySet.size)
            println("Removing $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
            assertTrue(
                binarySet.height() <= originalHeight,
                "After removal of $toRemove from $list binary tree height increased"
            )
        }
    }

    private fun testRemove1(create: () -> CheckableSortedSet<Int>) {
        val list = mutableListOf<Int>()
        list.add(576);
        list.add(3);
        list.add(1);
        list.add(354);
        list.add(76);
        list.add(22);
        list.add(34);
        list.add(89);
        list.add(7);

        val binarySet = create()
        assertFalse(binarySet.remove(42))
        assertFalse(binarySet.remove(56))
        assertFalse(binarySet.remove(34))
        assertFalse(binarySet.remove(8))
        assertFalse(binarySet.remove(576))
        for (element in list) {
            binarySet += element
        }
        val originalHeight = binarySet.height()
        val oldSize = binarySet.size
        assertTrue(binarySet.remove(76))
        assertEquals(oldSize - 1, binarySet.size)
        println("Removing 76 from $list")
        for (element in list) {
            val inn = element != 76
            assertEquals(
                inn, element in binarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.remove()")
        assertTrue(
            binarySet.height() <= originalHeight,
            "After removal of 76 from $list binary tree height increased"
        )
    }


    @Test
    @Tag("Normal")
    fun testRemoveKotlin() {
        testRemove { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testRemoveJava() {
        testRemove { createJavaTree() }
        testRemove1 { createJavaTree() }
    }

    private fun testIterator(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val treeIt = treeSet.iterator()
            val binaryIt = binarySet.iterator()
            println("Traversing $list")
            while (treeIt.hasNext()) {
                assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
            }
            val iterator1 = binarySet.iterator()
            val iterator2 = binarySet.iterator()
            println("Consistency check for hasNext $list")
            // hasNext call should not affect iterator position
            while (iterator1.hasNext()) {
                assertEquals(
                    iterator2.next(), iterator1.next(),
                    "Call of iterator.hasNext() changes its state while iterating $treeSet"
                )
            }
        }
    }

    private fun testIterator1(create: () -> CheckableSortedSet<Int>) {
        val list = mutableListOf<Int>()

        list.add(576);
        list.add(3);
        list.add(1);
        list.remove(576);
        list.add(354);
        list.add(76);
        list.add(22);
        list.add(34);
        list.add(33);
        list.add(89);
        list.remove(22);
        list.add(7);
        val treeSet = TreeSet<Int>()
        val binarySet = create()
        assertFalse(binarySet.iterator().hasNext(), "Iterator of empty set should not have next element")
        for (element in list) {
            treeSet += element
            binarySet += element
        }
        val treeIt = treeSet.iterator()
        val binaryIt = binarySet.iterator()
        println("Traversing $list")
        while (treeIt.hasNext()) {
            assertEquals(treeIt.next(), binaryIt.next(), "Incorrect iterator state while iterating $treeSet")
        }
        val iterator1 = binarySet.iterator()
        val iterator2 = binarySet.iterator()
        println("Consistency check for hasNext $list")
        // hasNext call should not affect iterator position
        while (iterator1.hasNext()) {
            assertEquals(
                iterator2.next(), iterator1.next(),
                "Call of iterator.hasNext() changes its state while iterating $treeSet"
            )
        }
    }

    @Test
    @Tag("Normal")
    fun testIteratorKotlin() {
        testIterator { createKotlinTree() }
    }

    @Test
    @Tag("Normal")
    fun testIteratorJava() {
        testIterator { createJavaTree() }
        testIterator1 { createJavaTree() }
    }

    private fun testIteratorRemove(create: () -> CheckableSortedSet<Int>) {
        val random = Random()
        for (iteration in 1..100) {
            val list = mutableListOf<Int>()
            for (i in 1..20) {
                list.add(random.nextInt(100))
            }
            val treeSet = TreeSet<Int>()
            val binarySet = create()
            for (element in list) {
                treeSet += element
                binarySet += element
            }
            val toRemove = list[random.nextInt(list.size)]
            treeSet.remove(toRemove)
            println("Removing $toRemove from $list")
            val iterator = binarySet.iterator()
            var counter = binarySet.size
            while (iterator.hasNext()) {
                val element = iterator.next()
                counter--
                print("$element ")
                if (element == toRemove) {
                    iterator.remove()
                }
            }
            assertEquals(
                0, counter,
                "Iterator.remove() of $toRemove from $list changed iterator position: " +
                        "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
            )
            println()
            assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of $toRemove from $list")
            assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of $toRemove from $list")
            for (element in list) {
                val inn = element != toRemove
                assertEquals(
                    inn, element in binarySet,
                    "$element should be ${if (inn) "in" else "not in"} tree"
                )
            }
            assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
        }
    }

    private fun testIteratorRemove1(create: () -> CheckableSortedSet<Int>) {
        val list = mutableListOf<Int>()
        list.add(31);
        list.add(3);
        list.add(1);
        list.remove(3);
        list.add(354);
        list.add(96);
        list.add(22);
        list.add(0);
        list.add(33);
        list.add(89);
        list.remove(354);
        list.remove(31);
        list.remove(89);
        list.add(7);
        val treeSet = TreeSet<Int>()
        val binarySet = create()
        for (element in list) {
            treeSet += element
            binarySet += element
        }
        treeSet.remove(0)
        println("Removing 0 from $list")
        val iterator = binarySet.iterator()
        var counter = binarySet.size
        while (iterator.hasNext()) {
            val element = iterator.next()
            counter--
            print("$element ")
            if (element == 0) {
                iterator.remove()
            }
        }
        assertEquals(
            0, counter,
            "Iterator.remove() of 0 from $list changed iterator position: " +
                    "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
        )
        println()
        assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of 0 from $list")
        assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of 0 from $list")
        for (element in list) {
            val inn = element != 0
            assertEquals(
                inn, element in binarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
    }

    private fun testIteratorRemove2(create: () -> CheckableSortedSet<Int>) {
        val list = mutableListOf<Int>()
        list.add(99);
        list.add(88);
        list.add(77);
        list.add(66);
        list.add(55);
        list.add(44);
        list.add(11);
        list.add(22);
        list.add(33);
        list.remove(22);
        list.remove(44);
        list.remove(66);
        list.remove(88);
        val treeSet = TreeSet<Int>()
        val binarySet = create()
        for (element in list) {
            treeSet += element
            binarySet += element
        }
        treeSet.remove(55)
        println("Removing 0 from $list")
        val iterator = binarySet.iterator()
        var counter = binarySet.size
        while (iterator.hasNext()) {
            val element = iterator.next()
            counter--
            print("$element ")
            if (element == 55) {
                iterator.remove()
            }
        }
        assertEquals(
            0, counter,
            "Iterator.remove() of 55 from $list changed iterator position: " +
                    "we've traversed a total of ${binarySet.size - counter} elements instead of ${binarySet.size}"
        )
        println()
        assertEquals<SortedSet<*>>(treeSet, binarySet, "After removal of 55 from $list")
        assertEquals(treeSet.size, binarySet.size, "Size is incorrect after removal of 55 from $list")
        for (element in list) {
            val inn = element != 55
            assertEquals(
                inn, element in binarySet,
                "$element should be ${if (inn) "in" else "not in"} tree"
            )
        }
        assertTrue(binarySet.checkInvariant(), "Binary tree invariant is false after tree.iterator().remove()")
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveKotlin() {
        testIteratorRemove { createKotlinTree() }
    }

    @Test
    @Tag("Hard")
    fun testIteratorRemoveJava() {
        testIteratorRemove { createJavaTree() }
        testIteratorRemove1 { createJavaTree() }
        testIteratorRemove2 { createJavaTree() }
    }
}