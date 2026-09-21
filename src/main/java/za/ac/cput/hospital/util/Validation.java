package za.ac.cput.hospital.util;
import java.time.LocalDate;
import java.util.regex.Pattern;

public final class Validation {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private Validation() {}
    public static boolean validEmail(String value) { return value != null && EMAIL.matcher(value.trim()).matches(); }
    public static boolean strongEnoughPassword(String value) { return value != null && value.length() >= 8; }
    public static boolean bookableDate(LocalDate value) { return value != null && !value.isBefore(LocalDate.now().plusDays(1)); }
    public static String clean(String value) { return value == null ? "" : value.trim(); }
}
