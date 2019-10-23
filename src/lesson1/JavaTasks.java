package lesson1;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     * Оценка трудоемкости: T = O(N)
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        ArrayList<Integer> arram = new ArrayList<>();
        ArrayList<Integer> arrpm = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputName)));
        String line;
        line = reader.readLine();
        while (line != null) {
            if (!line.matches("(([0-9]{2}:[0-9]{2}:[0-9]{2}) (AM|PM))")) {
                throw new IllegalArgumentException();
            }
            String[] str = line.replace(":", "").split(" ");
            Integer digit = Integer.parseInt(str[0]);
            if (str[1].equals("AM")) {
                String one = str[0].substring(0, 2);
                if (one.equals("12")) {
                    arram.add(digit - 120000);
                } else
                arram.add(digit);
            } else  {
                String one = str[0].substring(0, 2);
                if (one.equals("12")) {
                    arrpm.add(digit - 120000);
                } else arrpm.add(digit);
            }
            line = reader.readLine();
        }
        System.out.println(arram);
        System.out.println(arrpm);
        Collections.sort(arram);
        Collections.sort(arrpm);
        String result = "";
        for (int i = 0; i < arram.size(); i++) {
            String arr = arram.get(i).toString();
            if (arram.get(i).toString().length() == 5) {
                arr = "0" + arram.get(i);
            }
            if (arram.get(i).toString().length() == 4) {
                arr = "12" + arram.get(i);
            }
            if (arram.get(i).toString().length() == 3) {
                arr = "120" + arram.get(i);
            }
            if (arram.get(i).toString().length() == 2) {
                arr = "1200" + arram.get(i);
            }
            if (arram.get(i).toString().length() == 1) {
                arr = "12000" + arram.get(i);
            }
            String hours = arr.substring(0, 2);
            String minutes = arr.substring(2, 4);
            String seconds = arr.substring(4);
             result += (hours + ":" + minutes + ":" + seconds + " AM" + "\n");
        }
        for (int i = 0; i < arrpm.size(); i++) {
            String arr = arrpm.get(i).toString();
            if (arrpm.get(i).toString().length() == 5) {
                arr = "0" + arrpm.get(i);
            }
            if (arrpm.get(i).toString().length() == 4) {
                arr = "12" + arrpm.get(i);
            }
            if (arrpm.get(i).toString().length() == 3) {
                arr = "120" + arrpm.get(i);
            }
            if (arrpm.get(i).toString().length() == 2) {
                arr = "1200" + arrpm.get(i);
            }
            if (arrpm.get(i).toString().length() == 1) {
                arr = "12000" + arrpm.get(i);
            }
            String hours = arr.substring(0, 2);
            String minutes = arr.substring(2, 4);
            String seconds = arr.substring(4);
            result += (hours + ":" + minutes + ":" + seconds + " PM" + "\n");
        }
        writer.write(result);
        writer.close();
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) throws IOException {
        throw new NotImplementedError();

    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     * /* Оценка ресурсоемкости: R = O(1)
     *         Оценка трудоемкости: T = O(N)
     *          */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputName)));
        ArrayList<Double> temp = new ArrayList<>();
        String line;
        line = reader.readLine();
        while (line != null) {
            if (!line.matches("(-?\\d{1,3}\\.\\d)")) {
                throw new IllegalArgumentException();
            }
            temp.add(Double.valueOf(line));
            line = reader.readLine();
        }
        Collections.sort(temp);
        for (int i = 0; i < temp.size(); i++){
            if (temp.get(i) >= -273.0 && temp.get(i) <= 500.0)
            writer.write(temp.get(i) + "\n");
        }
        writer.close();
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(inputName)));
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outputName)));
        ArrayList<Long> temp2 = new ArrayList<>();
        Set<Long> numberSet = new HashSet<>();
        String line;
        line = reader.readLine();
        while (line != null) {
            numberSet.add(Long.parseLong(line));
            temp2.add(Long.valueOf((line)));
            line = reader.readLine();
        }
        Object[] temp1 = numberSet.toArray();
        long k = Collections.frequency(temp2, temp1[0]);
        long mindigit = (long) temp1[0];

        for (int i = 0; i < temp1.length; i++) {
            long a = Collections.frequency(temp2, temp1[i]);
            long digit = (long) temp1[i];
            if (a > k) {
                k = a;
                mindigit = digit;
            } else if (a == k) {
                if (mindigit > digit) mindigit = digit;
            }
        }
        System.out.println(mindigit);
        System.out.println(k);
        for (int i = 0; i < temp2.size(); i++) {
            if (!temp2.get(i).equals(mindigit)) writer.write(temp2.get(i) + "\n");
        }
        int a = 0;
        while (a != k) {
            writer.write(mindigit + "\n");
            a++;
        }
        writer.close();
    }


    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     * Оценка трудоемкости: T = O(N)
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        for (int j = 0; j < first.length; j++) {
            second[j] = first[j];
        }
        Arrays.sort(second);
     }
}
