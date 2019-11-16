package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        final T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        }
        else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        }
        else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    public int height() {
        return height(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    private int height(Node<T> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    //Трудоемкость: T = O(n)
    // Ресурсоемкость: R = O(1)
    @Override
    public boolean remove(Object o) {
        T base = (T) o;
        boolean rightChild = false;
        Node<T> parent = root;
        Node<T> current = root;
       if (!contains(o)) return false;
        while (base != current.value) {
            parent = current;
            if (base.compareTo(current.value) < 0) {
                current = current.left;
                rightChild = false;
            } else {
                current = current.right;
                rightChild = true;
            }
        }
        if (current.left == null) {
            if (current == root) root = current.right;
             else if (rightChild) parent.right = current.right;
             else parent.left = current.right;
        }
        else if (current.right == null) {
            if (current == root) root = current.left;
             else if (rightChild)  parent.right = current.left;
             else  parent.left = current.left;
        } else {
            Node<T> heir = current.left;
            Node<T> heirParent = current;

            while (heir.right != null) {
                heirParent = heir;
                heir = heir.right;
            }
            if (current == root) root = heir;
            else if (rightChild) parent.right = heir;
             else parent.left = heir;
            if (heir != current.left) {
                heirParent.right = heir.left;
                heir.left = current.left;
            }
            heir.right = current.right;
        }
        size--;
        return true;
    }


    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        }
        else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        }
        else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        Node<T> node = null;

        Stack<Node<T>> stack = new Stack<Node<T>>();
        private Node<T> current;

        private BinaryTreeIterator() {
            // Добавьте сюда инициализацию, если она необходима
            current = root;
            while (current != null){
                stack.push(current);
                current = current.left;
            }
        }

        /**
         * Проверка наличия следующего элемента
         * Средняя
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Node<T> findNext() {
            return stack.pop();
        }

        //Ресурсоёмкость R = O(1)
        //Трудоёмкость Т = O(n)

        /**
         * Поиск следующего элемента
         * Средняя
         */
        @Override
        public T next() {
            current = findNext();
            node = current;
            if (current == null) throw new NoSuchElementException();
            if (node.right != null) {
                node = node.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
            return current.value;
        }



        /**
         * Удаление следующего элемента
         * Сложная
         */

        //Ресурсоёмкость R = O(1)
        //Трудоёмкость Т = O(n)
        @Override
        public void remove() {
            BinaryTree.this.remove(current.value);
        }

    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }



    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */

    //Ресурсоёмкость R = O(n)
    //Трудоёмкость Т = O(n)


    private SortedSet<T> set2 = new TreeSet<>();
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
       BinaryTreeIterator iter = new BinaryTreeIterator();
       while (iter.hasNext()) {
           T val = (T) iter.next();
           if ((val.compareTo(toElement)) < 0) set2.add(val);
       }
        System.out.println(set2);
       return set2;
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */


    //Ресурсоёмкость R = O(n)
    //Трудоёмкость Т = O(n)

    private SortedSet<T> set1 = new TreeSet<>();
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        BinaryTreeIterator iter = new BinaryTreeIterator();
        while (iter.hasNext()) {
            T val = (T) iter.next();
            if ((val.compareTo(fromElement)) >= 0) set1.add(val);
        }
        System.out.println(set1);
        return set1;
    }

    @Override
    public T first() {
        Node<T> current = root;
        if (root == null) throw new NoSuchElementException();
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        Node<T> current = root;
        if (root == null) throw new NoSuchElementException();
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
