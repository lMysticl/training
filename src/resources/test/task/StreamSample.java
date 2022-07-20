package task;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by indigo on 2017-08-13.
 */
public class StreamSample {

    /*TODO ---------- Простое использование Stream ----------- TODO*/

    private String memory;

    // пример того как простая выборка данных из коллекций была бы реализована в java7
    @Test
    public void testSimpleStreamDemo_java7() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "Stiven Pupkin"),
                new User(123, Role.GUEST, "Hakuna Matata"),
                new User(11, Role.USER, "Super Puper"),
                new User(13, Role.USER, "Super Man"),
                new User(12, Role.USER, "Super Woman")
        );

        // filtering - тут мы фильтруем данные, которые нам нужны далее
        List<User> filtered = new LinkedList<>();
        for (User user : users) {
            if (user.getRole() == Role.USER) {
                filtered.add(user);
            }
        }

        // sorting - а тут мы сортируем результат фильтрования
        // по какому-то критерию
        Collections.sort(filtered, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });

        // map - функция высшего порядка, которая значит -
        // "применить-ко-всем"
        List<String> names = new LinkedList<>();
        for (User user : filtered) {
            names.add(user.getName());
        }

        // then
        assertEquals("[Super Man, Super Woman, Super Puper]", names.toString());
    }

    // а теперь то же самое, только со stream
    @Test
    public void testSimpleStreamDemo_java8() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "Stiven Pupkin"),
                new User(123, Role.GUEST, "Hakuna Matata"),
                new User(11, Role.USER, "Super Puper"),
                new User(13, Role.USER, "Super Man"),
                new User(12, Role.USER, "Super Woman")
        );

        // С помощью stream - все упростилось
//        List<String> names = users.stream()
//                // промежуточные операции, ничего не делают - мы только "билдим query"
//                // можно сказать они "lazy"
//                .filter(user -> user.getRole() == Role.USER)
//                .sorted((o1, o2) -> Long.compare(o2.getId(), o1.getId()))
//                .map(user -> user.getName())
//                // терминальная операция - приводит к вычислениям
//                .collect(toList());

        // Можно еще немного упростить, так как есть специальные приспособы
        List<String> names = users.stream()
                .filter(user -> user.getRole() == Role.USER)
                .sorted(Comparator.comparing(User::getId).reversed()) // TODO
                .map(User::getName)
                .collect(Collectors.toList());

        // then
        assertEquals("[Super Man, Super Woman, Super Puper]", names.toString());
    }

    //
    // Stream<T> filter(Predicate<? super T> predicate)
    //
    // фильтруем по любому предикату
    @Test
    public void testFilter() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4")
        );

        List<User> result = users.stream()
                // условие фильтрования (выбираем только четные id)
                .filter(user -> user.getId() % 2 == 0)
                // и собираем в список
                .collect(toList());

        // then
        assertEquals("[[2=User 2], [4=User 4]]", result.toString());
    }

    /*TODO ---------- Фильтрование в Stream ----------- TODO*/

    //
    // Stream<T> distinct()
    // S unordered()
    //
    // возвращает уникальные элементы, опираясь на equals метод
    @Test
    public void testDistinct() {
        // given
        Collection<User> users = Arrays.asList(
                new EqualsUser(1, Role.USER, "User 1"),
                new EqualsUser(2, Role.USER, "User 2"),
                new EqualsUser(3, Role.USER, "User 3"),
                new EqualsUser(4, Role.USER, "User 4"),
                // тут две дублирующиеся записи с одинаковыми айдишками
                new EqualsUser(2, Role.USER, "User 2 updated"),
                new EqualsUser(3, Role.USER, "User 3 updated")
        );

        Collection<User> result = users.stream()
                // если не важен порядок, то по производительности лучше
                // если сделаем stream перед тем неупорядоченным
                .unordered()
                // удаляем дубликаты
                .distinct()
                // и собираем в список
                .collect(toList());

        // then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4]]",
                result.toString());
    }

    //
    // Stream<T> limit(long maxSize)
    //
    // отсекает лишнее в хвосте
    @Test
    public void testLimit() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );

        List<User> result = users.stream()
                // берем только первых два
                .limit(2)
                // и представляем как список
                .collect(toList());

        // внимание! limit - это "short-circuiting stateful intermediate operation"
        // short-circuiting - для бесконченого потока она может
        //     вренуть конечный поток, т.е. отрезая хвост освобождает от необходиомсти
        //     просчитывать предыдущие операции на всей коллекции.
        // stateful - для работы ей может потребоваться все данные, обработанные
        //     на прошлом этапе
        // intermediate operation - она не терминальная, т.е. в ходе ее выполнения
        //     мы получаем другой Stream, а не данные

        // then
        assertEquals("[[1=User 1], [2=User 2]]", result.toString());
    }

    //
    // Stream<T> skip(long n)
    //
    // отсекает лишнее на старте
    @Test
    public void testSkip() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );

        List<User> result = users.stream()
                // исключаем первых два
                .skip(2)
                // и представляем как список
                .collect(toList());

        // skip - уже "stateful intermediate operation",
        // в отличие от limit, которая еще и short-circuiting
        // все потому, что она работает со всеми данными стрима,
        // отрезая только первых несколько

        // then
        assertEquals("[[3=User 3], [4=User 4], [5=User 5]]", result.toString());
    }

    //
    // Stream<T> sorted(Comparator<? super T> comparator)
    //
    // метод служит сортировке
    // есть два вида с Comparator и без (объект должен реализовать Comparable)
    @Test
    public void testSortedWithComparator() {
        // given
        Collection<User> users = Arrays.asList(
                new User(4, Role.USER, "User 4"),
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3")
        );

        List<User> result = users.stream()
                // сортируем с компаратором
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                // и представляем как список
                .collect(toList());

        // then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4]]",
                result.toString());
    }

    //
    // Stream<T> sorted()
    //
    // тут объект должен реализовать Comparable интерфейс,
    // тогда это позволит сортировать без Comparator
    @Test
    public void testSortedWithComparable() {
        // given
        Collection<User> users = Arrays.asList(
                new ComparableUser(4, Role.USER, "User 4"),
                new ComparableUser(1, Role.USER, "User 1"),
                new ComparableUser(2, Role.USER, "User 2"),
                new ComparableUser(3, Role.USER, "User 3")
        );

        List<User> result = users.stream()
                // сортируем без компаратора, поскольку объекты сравниваемые
                .sorted()
                // и представляем как список
                .collect(toList());

        // then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4]]",
                result.toString());
    }

    /*TODO ---------- Сортировка в Stream ----------- TODO*/

    @Test
    public void testIntermediateAndTerminalOperations() {
        // given
        List<String> phases = new LinkedList<>();
        Collection<Integer> users = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> names = users.stream()
                // "промежуточные операции" ничего не делают -
                // мы только "билдим query", можно сказать они "lazy"
                // их порядок важен, от этого зависит что будет в результате
                .filter(n -> {
                    phases.add("f-" + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    phases.add("m-" + n);
                    return n * n;
                })
//                .sorted((n1, n2) -> {
//                    phases.add("s-" + n1 + "-" + n2);
//                    return Integer.compare(n2, n1);
//                })
                // некотоыре функции влияют на выполнение других
//                .limit(2)
                // и только "терминальная операция" - приводит к вычислениям
                .collect(toList());

        // then
        assertEquals("[4, 16, 36, 64][f-1, f-2, m-2, f-3, f-4, m-4, f-5, f-6, m-6, f-7, f-8, m-8]", names.toString() + phases.toString());

        // а теперь расскомментируй .limit(2) и получишь
        // случилось это из за того, что limit - это short-circuiting операция
        // результатом ее выполнения может быть и меньшая коллекция
        // а значит не надо выполянть все предварительные вычисления
//        assertEquals("[4, 16][f-1, f-2, m-2, f-3, f-4, m-4]", names.toString() + phases.toString());

        // добавь sorted и получишь
        // обрати внимание что идет фильтрация и мэппинг всех элементов снова
        // это случилось из за того, что sorting - это statefull операция
//        assertEquals("[64, 36][f-1, f-2, m-2, f-3, f-4, m-4, f-5, f-6, m-6, f-7, f-8, m-8, s-16-4, s-36-16, s-64-36]", names.toString() + phases.toString());

        // если теперь мы заменим users.stream() на users.parallelStream()
        // то угадать исход вычислений мы не сможем - они будут выполняться
        // параллельно на всех ядрах процессора
    }

    //
    // Stream<T> peek(Consumer<? super T> action)
    //
    // своеобразный "сачок", который мы подставляем в промежуточные вычисления,
    // для того, чтобы скормить все данные на этот момент в Consumer
    @Test
    public void testPeek() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );

        List<User> sorted = new LinkedList<>();

        // when
        List<String> names = users.stream()
                // посортировали в обратном порядке по id
                .sorted(Comparator.comparing(User::getId).reversed())
                // и перегоняем юзеров параллельно в другой список
                // с помощью Consumer
                .peek(user -> sorted.add(user))
                // а дальше мы берем только имена
                .map(User::getName)
                // и собираем их в список
                .collect(toList());

        // then
        assertEquals("[User 5, User 4, User 3, User 2, User 1]", names.toString());
        assertEquals("[[5=User 5], [4=User 4], [3=User 3], [2=User 2], [1=User 1]]", sorted.toString());
    }

    //
    // void forEach(Consumer<? super T> action)
    //
    // терминальная операция, результатом выполнения которой будет
    // перенаправление всего потока на конкретный Consumer, который и примет решение
    // что делать с данными дальше.
    // Функция не гарантирует порядка обработки элементов - не стоит на это полагаться.
    @Test
    public void testForEach() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );

        List<String> names = new LinkedList<>();

        // when
        users.stream()
                // берем только имена
                .map(User::getName)
                // после всех операция отправляем все данные на Consumer
                // эта функция - терминальная (terminal)
                .forEach(name -> names.add(name));

        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());
    }

    /*TODO ---------- Промежуточные и терминальные операции в Stream ----------- TODO*/

    //
    // void forEachOrdered(Consumer<? super T> action)
    //
    // так же как терминальна как forEach, но в отличие от нее
    // обрабатывает элементы в порядке потока (если этот порядок есть)
    // жертвуя при этом эффективностью параллеллизма.
    @Test
    public void testForEachOrdered() {
        // given
        List<User> users = Arrays.asList(
                new User(1, Role.USER, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.USER, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.USER, "User 5")
        );

        List<String> names = new LinkedList<>();

        // when
        users.stream()
                // берем только имена, как мы это делали раньше
                .map(User::getName)
                // после всех операция отправляем все данные на Consumer
                // эта функция - терминальная (terminal)
                .forEachOrdered(name -> names.add(name));

        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());
    }

    /*TODO ---------- Перебор элементов в Stream ----------- TODO*/

    //
    // <R> Stream<R> map(Function<? super T, ? extends R> mapper)
    //
    // функция высшего порядка, которая значит "применить-ко-всем"
    // результатом ее выполнения будет другой stream длинной в N элементов
    @Test
    public void testMap() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        List<String> names = users.stream()
                // берем только имена
                .map(user -> user.getName())
                // тут же можно использовать оператор квадроточия
//                .map(User::getName)
                .collect(toList());

        // then
        assertEquals("[User 1, User 2, User 3, User 4, User 5]", names.toString());

        // when
        List<Long> ids = users.stream()
                // берем только имена
                .map(user -> user.getId())
                // тут же можно использовать оператор квадроточия
//                .map(User::getId)
                .collect(toList());

        // then
        assertEquals("[1, 2, 3, 4, 5]", ids.toString());
    }

    //
    // LongStream mapToLong(ToLongFunction<? super T> mapper)
    //
    // если мы хотим продолжить работу с текущим стримом как LongStream
    @Test
    public void testMapToLong() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        long[] ids = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // и в конце нам нужны айдишки
                // преобразователем у нас будет ToLongFunction
                .mapToLong(user -> user.getId())
                // далее мы получаем LongStream
                // поток, который работает несколько иначе чем [Object]Stream
                .toArray();

        // then
        assertEquals("[1, 3, 2, 4, 5]", Arrays.toString(ids));
    }

    //
    // IntStream mapToInt(ToIntFunction<? super T> mapper)
    //
    // если мы хотим продолжить работу с текущим стримом как IntStream
    @Test
    public void testMapToInt() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        int[] data = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // и в конце нам нужны суммы айдишек и длинн названия ролей :) (бред)
                // преобразователем у нас будет ToIntFunction
                .mapToInt(user -> (int) (user.getId() + user.getRole().name().length()))
                // далее мы получаем IntStream
                // поток, который работает несколько иначе чем [Object]Stream
                .toArray();

        // then
        assertEquals("[6, 8, 6, 8, 10]", Arrays.toString(data));
    }

    /*TODO ---------- "Мэппинг" или "применить ко всем" в Stream ----------- TODO*/

    //
    // int sum()
    // OptionalDouble average()
    // IntSummaryStatistics summaryStatistics()
    //
    // у IntStream есть специфические методы для работы с числовым рядом
    @Test
    public void testMapToInt_sum_average_summaryStatistics() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when then
        assertEquals(15, users.stream()
                .mapToInt(user -> (int) user.getId())
                // далее мы получаем IntStream
                // в нем мы можем сделать суммирование, например
                .sum());

        // when then
        assertEquals("OptionalDouble[3.0]", users.stream()
                .mapToInt(user -> (int) user.getId())
                // или посчитать среднее арифметическое
                // результатом будет OptionalDouble
                .average().toString());

        // when then
        assertEquals("IntSummaryStatistics{count=5, sum=15, min=1, average=3,000000, max=5}",
                users.stream()
                        .mapToInt(user -> (int) user.getId())
                        // или взять эти значения в одном объекте
                        .summaryStatistics().toString());
    }

    //
    // <U> Stream<U> mapToObj(IntFunction<? extends U> mapper)
    //
    // если мы хотим преобразовать числовой ряд в объектный, нам этот метод поможет
    @Test
    public void testMapToInt_mapToObject() {
        // given
        List<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        List<User> result = users.stream()
                .mapToInt(user -> (int) user.getId())
                // далее мы получаем IntStream
                // и можем сделать с ним обратное
                .mapToObj(id -> new User(id, users.get(id - 1).getRole(), "User " + id))
                .collect(toList());

        // then
        assertEquals("[[1=User 1], [2=User 2], [3=User 3], [4=User 4], [5=User 5]]",
                result.toString());
    }

    //
    // DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper)
    //
    // если мы хотим продолжить работу с текущим стримом как DoubleStream
    @Test
    public void testMapToDouble() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        double[] data = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // и в конце нам нужна та же информация, но в более хитром формате (бред)
                // преобразователем у нас будет ToIntFunction
                .mapToDouble(user -> {
                    int roleLength = user.getRole().name().length();
                    return user.getId() + ((double) roleLength / Math.pow(10, Math.floor(Math.log10(roleLength)) + 1));
                })
                // далее мы получаем IntStream
                // поток, который работает несколько иначе чем [Object]Stream
                .toArray();

        // then
        assertEquals("[1.5, 3.5, 2.4, 4.4, 5.5]", Arrays.toString(data));
    }

    //
    // <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
    //
    // превращает стрим объектов (длинной в N) в стрим других объектов
    // (длинной >> N), элементы которого строятся функцией обрабатывающей
    // исходные объекты. Другимим словами мы превращаем дерево в плоскую структуру.
    @Test
    public void testFlatMap() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        List<String> data = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // мы хотим развернуть некоторое дерево в плоскую структуру
                // для этго нам нужна Function принимающая объект "стрима"
                // и возвращающая "стрим" результатов
                // flatMap объединит все эти stream в один результирующий
                .flatMap(user -> Stream.of(
                        "id:" + user.getId(),
                        "role:" + user.getRole(),
                        "name:" + user.getName()))
                // и тут мы получаем из него список
                .collect(toList());

        // then
        assertEquals("[id:1, role:ADMIN, name:User 1, " +
                "id:3, role:ADMIN, name:User 3, " +
                "id:2, role:USER, name:User 2, " +
                "id:4, role:USER, name:User 4, " +
                "id:5, role:GUEST, name:User 5]", data.toString());
    }

    //
    // IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper)
    //
    // принцип действия сходен с flatMap, только на выходе у нас
    // IntStream
    @Test
    public void testFlatMapToInt() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        List<Integer> data = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // мы хотим развернуть некоторое дерево в плоскую структуру
                // для этго нам нужна Function принимающая объект "стрима"
                // и возвращающая "стрим" результатов
                // flatMapToInt объединит все эти stream в один результирующий IntStream
                .flatMapToInt(user -> IntStream.of(
                        (int) user.getId(),
                        user.getRole().name().length(),
                        user.getName().length()))
                // и тут мы получаем из него список
                .collect(() -> new LinkedList<Integer>(),
                        (list, value) -> list.add(value),
                        (list, list2) -> list.addAll(list2));

        // then
        assertEquals("[1, 5, 6, " +
                "3, 5, 6, " +
                "2, 4, 6, " +
                "4, 4, 6, " +
                "5, 5, 6]", data.toString());
    }

    //
    // Object[] toArray();
    //
    // превращает стрим в массив. Операция терминальная.
    @Test
    public void testToArray() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Object[] sorted = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // больше ничего не хотим - дайте нам массив!
                .toArray();

        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]",
                Arrays.toString(sorted));
    }

    //
    // <A> A[] toArray(IntFunction<A[]> generator)
    //
    // превращает стрим в массив. Операция терминальная.
    @Test
    public void testToArrayWithIntFunction() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        String[] sorted = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем все в String
                .map(User::toString)
                // больше ничего не хотим - дайте нам массив!
                .toArray(length -> new String[length]);
        // иил в сокращенной версии
//                .toArray(String[]::new);

        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]",
                Arrays.toString(sorted));
    }

    //
    // <R> R collect(Supplier<R> supplier,
    //               BiConsumer<R, ? super T> accumulator,
    //               BiConsumer<R, R> combiner)
    //
    // функция позволяет собрать элементы "стрима" в любой объект контейнер
    @Test
    public void testCollect() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
//        List<String> result = users.stream()
//                // допустим тут некоторое полезное действие
//                .sorted(Comparator.comparing(User::getRole))
//                // преобразуем все в String
//                .map(User::toString)
//                // и получаем результирующий список
//                .collect(
//                        // этот Supplier создает контейнер
//                        () -> new LinkedList<String>(),
//                        // этот BiConsumer (Accumulator) добавляет один элемент в контейнер
//                        (container, name) -> container.add(name),
//                        // этот BiConsumer (Combiner) добавляет все элементы в контейнер
//                        (container, sub) -> container.addAll(sub)
//                );

        // или сокращенно с оператором "квадроточие" можно было написать так
        List<String> result = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем все в String
                .map(User::toString)
                // и получаем результирующий список
                .collect(
                        LinkedList<String>::new,
                        List::add,
                        List::addAll
                );

        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]",
                result.toString());
    }

    //
    // LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper)
    // DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper)
    //
    // принцип действия сходен с flatMap,
    // только на выходе у нас Long/DoubleStream
    // их мы рассматривать не будем :) это остается тебе на домашку

    /*TODO ---------- Терминальные преобразования в Stream ----------- TODO*/

    //
    // <R, A> R collect(Collector<? super T, A, R> collector)
    //
    // функция позволяет собрать элементы "стрима" в любой объект контейнер
    // самая общая реализация - DIY
    @Test
    public void testCollectWithCollector_withFinisher() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Set<String> result = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем все в String
                .map(User::toString)
                // и получаем результирующий список
                .collect(Collector.of(
                        // этот Supplier создает контейнер
                        LinkedList<String>::new,
                        // этот BiConsumer (Accumulator) добавляет один элемент в контейнер
                        List::add,
                        // этот BinaryOperator (Combiner) добавляет все элементы в контейнер
                        (container, sub) -> {
                            container.addAll(sub);
                            return container;
                        },
                        // эта Function перепаковывает рабочий контейнер в результат
                        (container) -> new LinkedHashSet<String>() {{
                            addAll(container);
                        }},
                        // далее характеристики (их можно использовать несколько)
                        // thanks to http://www.izebit.ru/2016/01/collect.html
                        // говорит, что коллектор многопоточнобезопасный
                        // безопасно должно быть в combiner
                        Collector.Characteristics.CONCURRENT,

                        // говорит, что функция finisher может быть пропущена,
                        // перед получением итогового результата
                        // но так как мы указали finisher Function то нам он не нужен
                        // Collector.Characteristics.IDENTITY_FINISH,

                        // говорит, что если на входе поток был упорядочен,
                        // то на выходе он не будет таковым
                        Collector.Characteristics.UNORDERED
                ));

        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]",
                result.toString());
    }

    //
    // <R, A> R collect(Collector<? super T, A, R> collector)
    // Collector<T, ?, List<T>> toList()
    //
    // вариант коллетора toList
    @Test
    public void testCollectWithCollector_toListCollector() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        List<String> result = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем все в String
                .map(User::toString)
                // и получаем результирующий список
                .collect(Collectors.toList());

        // then
        assertEquals("[[1=User 1], [3=User 3], [2=User 2], [4=User 4], [5=User 5]]",
                result.toString());
    }

    //
    // <R, A> R collect(Collector<? super T, A, R> collector)
    // <T> Collector<T, ?, Set<T>> toSet()
    //
    // вариант коллетора toSet
    @Test
    public void testCollectWithCollector_toSetCollector() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Set<String> result = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // преобразуем все в String
                .map(User::toString)
                // и получаем результирующий набор
                .collect(Collectors.toSet());

        // then
        assertTrue(result.contains("[1=User 1]"));
        assertTrue(result.contains("[2=User 2]"));
        assertTrue(result.contains("[3=User 3]"));
        assertTrue(result.contains("[4=User 4]"));
        assertTrue(result.contains("[5=User 5]"));
    }

    //
    // <R, A> R collect(Collector<? super T, A, R> collector)
    // <T, K, U> Collector<T, ?, Map<K,U>>
    //           toMap(Function<? super T, ? extends K> keyMapper,
    //                 Function<? super T, ? extends U> valueMapper)
    //
    // вариант коллетора toMap
    @Test
    public void testCollectWithCollector_toMapCollector() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Map<Long, String> result = users.stream()
                // допустим тут некоторое полезное действие
                .sorted(Comparator.comparing(User::getRole))
                // и получаем результирующую карту
                .collect(Collectors.toMap(
                        user -> user.getId(),
                        user -> user.toString()
                ));

        // then
        assertEquals(5, result.size());
        assertEquals("[1=User 1]", result.get(1L));
        assertEquals("[2=User 2]", result.get(2L));
        assertEquals("[3=User 3]", result.get(3L));
        assertEquals("[4=User 4]", result.get(4L));
        assertEquals("[5=User 5]", result.get(5L));
    }

    //
    // Optional<T> reduce(BinaryOperator<T> accumulator)
    //
    // преобразует весь поток к одному объекту
    // результат Optional - т.е. его может не быть
    @Test
    public void testReduceOptional() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<Long> result = users.stream()
                // получаем только id
                .map(User::getId)
                // и получаем результат
                .reduce((a, b) -> a + b);
        // а могли и по проще
//                .reduce(Long::sum);

        // then
        // результат Optional - т.е. его может не быть
        assertTrue(result.isPresent());
        assertEquals(15L, result.get().longValue());
    }

    //
    // T reduce(T identity, BinaryOperator<T> accumulator)
    //
    // преобразует весь поток к одному объекту
    // и этот объект точно есть, потому что начинаем мы со значения identity
    @Test
    public void testReduceWithIdentity() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Long result = users.stream()
                // получаем только id
                .map(User::getId)
                // и получаем результат
                .reduce(300L, Long::sum);

        // then
        assertEquals(315L, result.longValue());
    }

    //
    // <U> U reduce(U identity,
    //              BiFunction<U, ? super T, U> accumulator,
    //              BinaryOperator<U> combiner)
    //
    // преобразует весь поток к одному объекту по несколько другому алгоритму
    // и этот объект точно есть, потому что начинаем мы со значения identity
    @Test
    public void testReduce() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        StringBuilder result = users.stream()
                // и получаем результат
                .reduce(
                        new StringBuilder(),
                        (builder, user) -> builder.append(user.toString()),
                        (builder, anotherBuilder) -> builder.append(anotherBuilder.toString())
                );

        // then
        assertEquals("[1=User 1][2=User 2][3=User 3][4=User 4][5=User 5]",
                result.toString());
    }

    // другие варианты коллекторов на домашку :)
    // thanks to https://habrahabr.ru/company/luxoft/blog/270383/

    // представляют стрим в виде списка, коллекции или множества
    //+<T> Collector<T, ?, List<T>> toList()
    //+<T> Collector<T, ?, Set<T>> toSet()
    // <T> <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)

    // контактенирует элементы в строку с разделителями или без
    // Collector<CharSequence, ?, String> joining()
    // Collector<CharSequence, ?, String> joining(CharSequence delimiter)
    // Collector<CharSequence, ?, String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)

    // дополнительные преобразования значений для сложных Collector'ов
    // <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream)
    // <T,A,R,RR> Collector<T,A,RR> collectingAndThen(Collector<T,A,R> downstream, Function<R,RR> finisher)

    // возвращает количество
    // <T> Collector<T, ?, Long> counting()

    // возвращает min/max значения
    // <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator)
    // <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator)

    // возвращает сумму
    // <T> Collector<T, ?, Integer> summingInt(ToIntFunction<? super T> mapper)
    // <T> Collector<T, ?, Long> summingLong(ToLongFunction<? super T> mapper)
    // <T> Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> mapper)

    // возвращают среднее значение
    // <T> Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper)
    // <T> Collector<T, ?, Double> averagingLong(ToLongFunction<? super T> mapper)
    // <T> Collector<T, ?, Double> averagingDouble(ToDoubleFunction<? super T> mapper)

    // объединяют стрим в один объект
    // <T> Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)
    // <T> Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)
    // <T, U> Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)

    // разделяет коллекцию на несколько частей и возвращает Map<N, List<T>>
    // <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
    // <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
    // <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
    // <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> classifier)
    // <T, K, A, D> Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
    // <T, K, A, D, M extends ConcurrentMap<K, D>> Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)

    // разделяет коллекцию на две части по соответствию условию и возвращает их как Map<Boolean, List>
    // <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
    // <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)

    // позволяют преобразовать стрим в map
    //+<T, K, U> Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
    // <T, K, U> Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
    // <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
    // то же но для потокобезопасных map
    // <T, K, U> Collector<T, ?, ConcurrentMap<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
    // <T, K, U> Collector<T, ?, ConcurrentMap<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
    // <T, K, U, M extends ConcurrentMap<K, U>> Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)

    // возвращают SummaryStatistics с разными агрегатными значениями
    // <T> Collector<T, ?, IntSummaryStatistics> summarizingInt(ToIntFunction<? super T> mapper)
    // <T> Collector<T, ?, LongSummaryStatistics> summarizingLong(ToLongFunction<? super T> mapper)
    // <T> Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper)

    /*TODO ---------- Reduce преобразования в Stream ----------- TODO*/

    /*TODO ---------- Поиск в Stream ----------- TODO*/
    //
    // Optional<T> min(Comparator<? super T> comparator)
    //
    // поиск минимального значения опираясь на Comparator
    @Test
    public void testMin() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<User> result = users.stream()
                .min(Comparator
                        .comparing(User::getRole)
                        .thenComparing(Comparator.comparing(User::getName).reversed())
                );

        // then
        assertEquals("Optional[[3=User 3]]", result.toString());
    }

    //
    // Optional<T> max(Comparator<? super T> comparator)
    //
    // поиск максимлаьонго значения опираясь на Comparator
    @Test
    public void testMax() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<User> result = users.stream()
                .max(Comparator
                        .comparing(User::getRole)
                        .thenComparing(Comparator.comparing(User::getName).reversed())
                );

        // then
        assertEquals("Optional[[5=User 5]]", result.toString());
    }

    //
    // long count()
    //
    // количество элементов на данный момент
    @Test
    public void testCount() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        long result = users.stream()
                // фильтруем
                .filter(user -> user.getRole() == Role.USER)
                // сколько нафильтровали?
                .count();

        // then
        assertEquals(2, result);
    }

    //
    // boolean anyMatch(Predicate<? super T> predicate)
    //
    // говорит о том, есть ли совпадение Predicate хотябы по одному элементу
    @Test
    public void testAnyMatch() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when then
        assertEquals(true, users.stream()
                // проверяем есть ли хотя бы один пользователь-юзер?
                .anyMatch(user -> user.getRole() == Role.USER));

        // when then
        assertEquals(false, users.stream()
                // проверяем есть ли хотя бы один пользователь имя которого содержит '6'?
                .anyMatch(user -> user.getName().contains("6")));

        // when then
        assertEquals(true, users.stream()
                // проверяем есть ли хотя бы один пользователь с четным id
                .anyMatch(user -> user.getId() % 2 == 0));

        // when then
        assertEquals(true, users.stream()
                // проверяем есть ли хотя бы один пользователь содержащий 'User'?
                .anyMatch(user -> user.getName().contains("User")));
    }

    //
    // boolean allMatch(Predicate<? super T> predicate)
    //
    // говорит о том, совпадает ли Predicate по всем элементам
    @Test
    public void testAllMatch() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when then
        assertEquals(false, users.stream()
                // проверяем все ли у нас пользователи - юзера?
                .allMatch(user -> user.getRole() == Role.USER));

        // when then
        assertEquals(false, users.stream()
                // проверяем все ли у нас пользователи содержат '6'?
                .allMatch(user -> user.getName().contains("6")));

        // when then
        assertEquals(false, users.stream()
                // проверяем все ли у нас пользователи с четным id
                .allMatch(user -> user.getId() % 2 == 0));

        // when then
        assertEquals(true, users.stream()
                // проверяем все ли у нас пользователи содержат 'User'?
                .allMatch(user -> user.getName().contains("User")));
    }

    //
    // boolean noneMatch(Predicate<? super T> predicate)
    //
    // говорит о том, что нет ни одного элемента которые совпадает с Predicate
    @Test
    public void testNoneMatch() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when then
        assertEquals(false, users.stream()
                // проверяем нет ли у нас вообще пользователей - юзеров?
                .noneMatch(user -> user.getRole() == Role.USER));

        // when then
        assertEquals(true, users.stream()
                // проверяем нет ли у нас вообще пользователей содержащих '6'?
                .noneMatch(user -> user.getName().contains("6")));

        // when then
        assertEquals(false, users.stream()
                // проверяем нет ли у нас вообще пользователей с четным id
                .noneMatch(user -> user.getId() % 2 == 0));

        // when then
        assertEquals(false, users.stream()
                // проверяем нет ли у нас вообще пользователей содержащих 'User'?
                .noneMatch(user -> user.getName().contains("User")));
    }

    //
    // Optional<T> findFirst()
    //
    // находит первый попашийся (если он есть)
    // для неупорядоченного стрима возвращается любой
    @Test
    public void testFindFirst() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<User> result = users.stream()
                .findFirst();

        // then
        assertEquals("Optional[[1=User 1]]", result.toString());
    }

    //
    // Optional<T> findAny()
    //
    // находит любой элемент (если он есть)
    @Test
    public void testFindAny() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<User> result = users.stream()
                .findAny();

        // then
        assertEquals(true, result.toString().contains("User"));
    }

    /*TODO ---------- Фабрики Stream ----------- TODO*/
    //
    // static<T> Stream<T> empty()
    //
    // А вдруг нам понадобился пустой stream?
    @Test
    public void testEmptyStream() {
        // given
        Stream<Object> empty = Stream.empty();

        // when
        Optional<Object> result = empty.findAny();

        // then
        assertEquals("Optional.empty", result.toString());
    }

    //
    // static<T> Stream<T> of(T... values)
    // static<T> Stream<T> of(T t)
    //
    // Stream из заданного числа элементов (или одного)
    @Test
    public void testStreamOfElements() {
        // given

        // так мы делали раньше
//        Collection<User> users = Arrays.asList(...);
//        Stream<User> stream = users.stream();

        // а можно было и проще
        Stream<User> stream = Stream.of(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        // when
        Optional<User> result = stream.findAny();

        // then
        assertEquals(true, result.toString().contains("User"));
    }

    //
    // <T> Builder<T> builder()
    //
    // еще один способ собрать стрим из элементов
    @Test
    public void testStreamBuilder() {
        // given
        Stream<User> stream = Stream.<User>builder()
                .add(new User(1, Role.ADMIN, "User 1"))
                .add(new User(2, Role.USER, "User 2"))
                .add(new User(3, Role.ADMIN, "User 3"))
                .add(new User(4, Role.USER, "User 4"))
                .add(new User(5, Role.GUEST, "User 5")).build();

        // when
        Optional<User> result = stream.findAny();

        // then
        assertEquals(true, result.toString().contains("User"));
    }

    //
    // <T> Stream<T> stream(T[] array)
    //
    // Stream из заданного массива
    @Test
    public void testArraysStream() {
        // given
        User[] array = {
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        };

        // when
        Stream<User> stream = Arrays.stream(array);

        // then
        assertEquals(true, stream.findAny().toString().contains("User"));
    }

    //
    // static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f)
    //
    // Позволяет создать бесконечные stream
    @Test
    public void testStreamIterate() {
        // given
        int start = 1234;

        // when
        List<Integer> result = Stream
                // создаем сираку́зскую последовательность
                // thanks to https://ru.wikipedia.org/wiki/%D0%93%D0%B8%D0%BF%D0%BE%D1%82%D0%B5%D0%B7%D0%B0_%D0%9A%D0%BE%D0%BB%D0%BB%D0%B0%D1%82%D1%86%D0%B0
                .iterate(start, n -> (n % 2 == 0) ? (n / 2) : (n * 3 + 1))
                // ограничиваем ряд
                .limit(150)
                // приводим к списку
                .collect(toList());

        // then
        assertEquals("[1234, 617, 1852, 926, 463, 1390, 695, 2086, 1043, " +
                "3130, 1565, 4696, 2348, 1174, 587, 1762, 881, 2644, " +
                "1322, 661, 1984, 992, 496, 248, 124, 62, 31, 94, 47, " +
                "142, 71, 214, 107, 322, 161, 484, 242, 121, 364, 182, " +
                "91, 274, 137, 412, 206, 103, 310, 155, 466, 233, 700, " +
                "350, 175, 526, 263, 790, 395, 1186, 593, 1780, 890, " +
                "445, 1336, 668, 334, 167, 502, 251, 754, 377, 1132, " +
                "566, 283, 850, 425, 1276, 638, 319, 958, 479, 1438, " +
                "719, 2158, 1079, 3238, 1619, 4858, 2429, 7288, 3644, " +
                "1822, 911, 2734, 1367, 4102, 2051, 6154, 3077, 9232, " +
                "4616, 2308, 1154, 577, 1732, 866, 433, 1300, 650, 325, " +
                "976, 488, 244, 122, 61, 184, 92, 46, 23, 70, 35, 106, " +
                "53, 160, 80, 40, 20, 10, 5, 16, 8, 4, 2, 1, 4, 2, 1, 4, " +
                "2, 1, 4, 2, 1, 4, 2, 1, 4, 2, 1, 4, 2]", result.toString());
    }

    //
    // static<T> Stream<T> generate(Supplier<T> s)
    //
    // генерируем произвольные stream с помощью Supplier
    @Test
    public void testStreamGenerate() {
        // given when
        List<Integer> result = Stream
                // рендомный ряд
                .generate(() -> new Random().nextInt())
                // ограничиваем ряд
                .limit(150)
                // приводим к списку
                .collect(toList());

        // then
        assertEquals(true,
                // хоть один но должен быть не ноль (хотя, не факт)
                result.stream().anyMatch(n -> n != 0));
    }

    //
    // static <T> Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b)
    //
    // объединяет стримы
    @Test
    public void testStreamConcat() {
        // given
        Stream<Integer> stream1 = Stream.iterate(1, n -> n * 2).limit(10);
        Stream<Integer> stream2 = Stream.iterate(1, n -> n * 3).limit(10);

        // when
        Stream<Integer> stream3 = Stream.concat(stream1, stream2);
        List<Integer> result = stream3.collect(toList());

        // then
        assertEquals("[1, 2, 4, 8, 16, 32, 64, 128, 256, 512, " +
                        "1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683]",
                result.toString());
    }

    /*TODO ---------- Параллелизм в Stream ----------- TODO*/
    //
    // Stream<E> stream()
    // Stream<E> parallelStream()
    // S parallel();
    // S sequential();
    //
    // для распределенного вычисления можно использовать фишки параллелизма в stream
    @Test
    public void testParallelSequential() {
        // given
        Collection<User> users = Arrays.asList(
                new User(1, Role.ADMIN, "User 1"),
                new User(2, Role.USER, "User 2"),
                new User(3, Role.ADMIN, "User 3"),
                new User(4, Role.USER, "User 4"),
                new User(5, Role.GUEST, "User 5")
        );

        List<String> names = new LinkedList<>();

        // when
        StringBuilder result =
                // изначально sequential stream
                // но мог быть parallel
                // users.parallelStream()
                users.stream()
                        // эта операция будет выполняться последовательно
                        .peek(user -> names.add(user.getName()))
                        // сделали parallel stream
                        .parallel()
                        // эта операция будет выполняться параллельно
                        .map(user -> user.getName())
                        // потом снова sequential stream
                        .sequential()
                        // эта операция будет выполняться снова последовательно
                        .reduce(new StringBuilder(),
                                (builder, name) -> builder.append(name),
                                (builder, anotherBuilder) -> builder.append(anotherBuilder));
        // then
        assertEquals("User 1User 2User 3User 4User 5", result.toString());
    }

    //
    // boolean isParallel()
    //
    // Можно так же узнать какой стрим сейчас Parallel или Sequential
    @Test
    public void testIsParallelOrSequential() {
        // given
        Stream<Object> stream1 = Stream.empty();

        // when then
        assertEquals(false, stream1.isParallel());

        // when
        Stream<Object> stream2 = stream1.parallel();

        // then
        assertEquals(true, stream2.isParallel());

        // when
        Stream<Object> stream3 = stream2.sequential();

        // then
        assertEquals(false, stream3.isParallel());
    }

    @Test
    public void testOptional() {
        // given
        // допустим у нас есть объект Optional
        Optional<String> value = Stream.of("1", "2", "3").findFirst();

        // мы можем узнать есть ли в нем какое-то значение
        assertEquals(true, value.isPresent());

        // мы можем так же получить его
        assertEquals("1", value.get());

        // если его там нет, то можем получить вместо него default value
        assertEquals("1", value.orElse("0"));

        //               ...или получить это default value в момент получения значения
        assertEquals("1", value.orElseGet(() -> "0"));

        //               ...или получить исклюение в момент получения значения
        assertEquals("1", value.orElseThrow(() -> new RuntimeException()));

        // а если оно там есть, то скормить его Consumer
        this.memory = null;
        value.ifPresent(data -> this.memory = data);
        assertEquals("1", this.memory);

        // given
        // как вот тут: допустим у нас есть объект Optional
        Optional<String> empty =
                Stream.of("1", "2", "3").filter(a -> a.equals("5")).findAny();

        // мы точно знаем что там его нет
        assertEquals(false, empty.isPresent());
        try {
            // если мы попробуем получить его, то поулчим исключение
            assertEquals(null, empty.get());
            fail("Ждем исключение");
        } catch (NoSuchElementException e) {
            assertEquals("No value present", e.getMessage());
        }

        // или default значение
        assertEquals("0", empty.orElse("0"));

        // или default значение в формате lazy
        assertEquals("0", empty.orElseGet(() -> "0"));

        try {
            // или исключение
            assertEquals("1", empty.orElseThrow(() -> new RuntimeException("Something wrong!")));
            fail("Ждем исключение");
        } catch (RuntimeException e) {
            assertEquals("Something wrong!", e.getMessage());
        }

        // Consumer не получит ничего!
        this.memory = null;
        empty.ifPresent(data -> this.memory = data);
        assertEquals(null, this.memory);
    }

    /*TODO ---------- Другие вкусности в Stream ----------- TODO*/
    // мы можем прочитать файл в stream
    @Test
    public void testStreamFromFile() throws IOException {
        // given when
        Stream<String> stream = Files.lines(Paths.get("src/main/resources/test.txt"));

        // then
        assertEquals("[line1, line2, line3, line4]",
                stream.collect(toList()).toString());
    }

    // мы можем получить строку в stream
    @Test
    public void testStreamFromString() throws IOException {
        // given when
        IntStream stream = "Hello World!".chars();

        // then
        assertEquals("[72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100, 33]",
                Arrays.toString(stream.toArray()));
    }

    /*TODO ---------- Optional объект в Stream ----------- TODO*/
    //
    // boolean isPresent()
    // T get()
    // T orElse(T other)
    // T orElseGet(Supplier<? extends T> other)
    // <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X
    // void ifPresent(Consumer<? super T> consumer)
    //
    // можно так же узнать какой стрим сейчас Parallel или Sequential

    enum Role {
        ADMIN, USER, GUEST;
    }

    static class User {
        private long id;
        private String name;
        private Role role;

        User(long id, Role role, String name) {
            this.id = id;
            this.name = name;
            this.role = role;
        }

        public Role getRole() {
            return role;
        }

        public String getName() {
            return name;
        }

        public long getId() {
            return id;
        }

        @Override
        public String toString() {
            return "[" + id + "=" + name + "]";
        }
    }

    // Тот же юзер, только умеет сравнивать себя с другими посредством equals
    static class EqualsUser extends User {

        EqualsUser(long id, Role role, String name) {
            super(id, role, name);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            return getId() == user.getId();
        }

        @Override
        public int hashCode() {
            return (int) (getId() ^ (getId() >>> 32));
        }
    }

    // Тот же юзер, только умеет сравнивать себя с другими посредством compareTo
    static class ComparableUser extends User implements Comparable<User> {

        ComparableUser(long id, Role role, String name) {
            super(id, role, name);
        }

        @Override
        public int compareTo(User o) {
            return getName().compareTo(o.getName());
        }
    }
}
