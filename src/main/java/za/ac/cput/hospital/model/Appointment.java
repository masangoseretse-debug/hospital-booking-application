package za.ac.cput.hospital.model;
import java.time.LocalDate;
import java.time.LocalTime;
public record Appointment(int id, int patientId, int doctorId, String patientName, String doctorName,
                          String department, LocalDate date, LocalTime time, String status, String reason,
                          String cancellationReason) {}
