package task;

import org.junit.Before;
import org.junit.Test;

public class TasksTest1 {
    private Btn btn1;
    private Btn btn2;
    private Btn btn3;

    @Before
    private void setUp() {
        btn1 = new Btn(1, null, null);
        btn2 = new Btn(2, btn1, btn1);
        btn3 = new Btn(1, null, null);

    }


    @Test
    public void btn() {
        System.out.println(btn1.equals(btn3));
    }


}
