package epam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class BusinessDaysExamples {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2020, 5, 5);

        //Add one holiday for testing
        List<LocalDate> holidays = new ArrayList<>();
        holidays.add(LocalDate.of(2020, 5, 11));
        holidays.add(LocalDate.of(2020, 5, 1));

        System.out.println(addBusinessDays(today, 8, Optional.empty()));        // 2020-05-15
        System.out.println(addBusinessDays(today, 8, Optional.of(holidays)));   // 2020-05-18
    }

    private static LocalDate addBusinessDays(LocalDate localDate, int days,
                                             Optional<List<LocalDate>> holidays) {
        if (localDate == null || days <= 0 || holidays == null) {
            throw new IllegalArgumentException("Invalid method argument(s) "
                    + "to addBusinessDays(" + localDate + "," + days + "," + holidays + ")");
        }

        Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;

        Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;

        LocalDate result = localDate;
        while (days > 0) {
            result = result.plusDays(1);
            if (isHoliday.or(isWeekend).negate().test(result)) {
                days--;
            }
        }
        return result;
    }
}
