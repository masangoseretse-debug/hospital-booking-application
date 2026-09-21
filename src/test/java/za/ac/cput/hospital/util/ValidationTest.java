package za.ac.cput.hospital.util;
import org.junit.jupiter.api.Test;import java.time.LocalDate;import static org.junit.jupiter.api.Assertions.*;
class ValidationTest{
 @Test void acceptsValidEmail(){assertTrue(Validation.validEmail("student@cput.ac.za"));}
 @Test void rejectsInvalidEmail(){assertFalse(Validation.validEmail("student-at-cput"));}
 @Test void requiresEightCharacterPassword(){assertFalse(Validation.strongEnoughPassword("short"));assertTrue(Validation.strongEnoughPassword("Password1"));}
 @Test void rejectsPastBookingDate(){assertFalse(Validation.bookableDate(LocalDate.now().minusDays(1)));}
}
