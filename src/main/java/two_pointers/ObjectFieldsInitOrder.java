package two_pointers;

public class ObjectFieldsInitOrder {
    static int initialize(String message) {
        System.out.println(message);
        return 0;
    }

    static class A {
        int i0 = initialize("i0");

        int i1;
        { i1 = initialize("i1"); }

        int i2 = initialize("i2");

        int i3;
        A() { i3 = initialize("i3"); }
    }

    static class B extends A {
        int i4 = initialize("i4");

        int i5;
        { i5 = initialize("i5"); }

        int i6;
        B() { i6 = initialize("i6"); }
    }

    public static void main(String[] args) {
        new B();
    }
}
//    В результате выполнения получим:
//
//
//        i0
//        i1
//        i2
//        i3
//        i4
//        i5
//        i6
//Следовательно порядок несколько отличен от того к-ый вы указали, а имеено:
//        1. Иниициализация статических полей, выполнение блока статической инициализации
//        суперкласса;
//        2. Иниициализация статических полей, выполнение блока статической инициализации
//        подкласса;
//        3. Иниициализация нестатических полей, выполнение блока нестатической инициализации
//        и инициализация в конструкторе суперкласса;
//        4. Иниициализация нестатических полей, выполнение блока нестатической инициализации
//        и инициализация в конструкторе подкласса.