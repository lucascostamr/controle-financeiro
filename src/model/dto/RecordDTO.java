package model.dto;

import java.time.LocalDate;

public record RecordDTO(Long id, String name, String classification, float value, LocalDate entryDate, LocalDate registrationDate) {

}
