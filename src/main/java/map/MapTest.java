package map;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */

public class MapTest {
    public static void main(String[] args) {
        Map map= new HashMap();
        map.put(null,null);
        map.forEach((key, value) -> {
            System.out.println("Key : " + key + " Value : " + value);
        });
    }
}
