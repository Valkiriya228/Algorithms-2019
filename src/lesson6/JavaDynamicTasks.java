package lesson6;



import kotlin.NotImplementedError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.*;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * @return
     ** Трудоемкость: T = o(n * m) n = lenf, m = lens, т.е n - кол-во столбцов в нашем двумерном массиве,
     ** Ресурсоемкость: R = o(n * m) m -кол-во строк в нашем двумерном массиве
     */


    public static String longestCommonSubSequence(String first, String second) {
        int lenf;
        lenf = first.length();
        int lens;
        lens = second.length();
        int[][] mas = new int[1 + lens][1 + lenf];
        String res = "";

        for (int i = 1; i < (lens + 1); i++) {
            for (int j = 1; j < (lenf + 1); j++) {
                if (second.charAt(i - 1) == first.charAt(j - 1)) mas[i][j] = 1 + mas[i -1][j - 1];
                 else mas[i][j] = Math.max(mas[i][j - 1], mas[i - 1][j]);
            }
        }
        while (mas[lens][lenf] > 0) {
            if (mas[lens][lenf] == mas[lens - 1][lenf]) lens -= 1;
            else if (mas[lens][lenf] == mas[lens][lenf - 1]) {
                lenf -= 1;
            } else {
                lenf -= 1;
                lens -= 1;
                res = String.format("%s%s", first.charAt(lenf), res);
            }
        }
  return res;
    }


    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     ** Трудоемкость: T = o(n*n) n = listSize, т.е размер списка list
     ** Ресурсоемкость: R = o(n), n = listSize, т.е размер списка list
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list)  {
        int listSize = list.size();
        if (listSize <= 1) return list;
        int k = 0;
        ArrayList<Integer> res = new ArrayList<>(); //лист результата
        ArrayList<Integer> len = new ArrayList<>(); //лист длины последовательности
        ArrayList<Integer> pre = new ArrayList<>();//лист предшественников

        for (int i = 0; i < listSize; i++) { // напомняем массивы
            len.add(1);
            pre.add(-1);
        }

        for (int i = 0; i < listSize; i++) {
            for (int j = 0; j < i; j++) {
                if (list.get(i) > list.get(j) && len.get(i) < len.get(j) + 1) {
                    pre.set(i, j);
                    len.set(i, len.get(j) + 1);
                }
            }
        }
        int dl = len.get(0);
        for (int i = 0; i < listSize; i++) {
            if (dl < len.get(i)) {
                dl = len.get(i);
                k = i;
            }
        }
        while (k != -1) {
            res.add(list.get(k));
            k = pre.get(k);
        }
        ArrayList<Integer> result;
        result = res;
        reverse(result);
        return result;
     }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */


    public static int shortestPathOnField(String inputName) {
        ArrayList<Integer> listOfDigits = new ArrayList<>();
        ArrayList<Integer> finish = new ArrayList<>();

        int result = 0;
        try {
            FileReader fr;
            fr = new FileReader(inputName);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            int stroka = 0;
            int stolbets = line.split(" ").length;
            while (line != null) {
                stroka ++;
                for (String str: line.split(" ")) {
                    listOfDigits.add(Integer.valueOf(str));
                }
                line = reader.readLine();
            }
            int[][] matrix = new int[stroka][stolbets];
            int k = 0;
            for (int i = 0; i < stroka; i++) {
                for (int j = 0; j < stolbets; j++) {
                    matrix[i][j] = listOfDigits.get(k);
                    k++;
                }
            }
            result = min(ways(0, 0, stroka, stolbets, 0, finish, matrix));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    private static ArrayList<Integer> ways(int startx, int starty, int stroka, int stolbets, int result, ArrayList<Integer> finWays, int[][] arr){
        int matrix = arr[startx][starty];
        if (startx + 1 <= stroka - 1 && starty + 1 <= stolbets - 1){
            ways(startx + 1, starty + 1, stroka, stolbets, result + matrix, finWays, arr);
        }
        if (startx + 1 <= stroka - 1) {
            ways(startx + 1, starty, stroka, stolbets, result + matrix, finWays, arr);
        }
        if (starty + 1 <= stolbets - 1) {
            ways(startx, starty + 1, stroka, stolbets, result + matrix, finWays, arr);
        }
        if (startx == stroka - 1 && starty == stolbets - 1) finWays.add(result);
        return finWays;
    }


    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
