package com.vladveretilnyk.clinic.model.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Note {
    private Long id;

    LocalDate creationDate;

    private String diagnosis;

    private Long doctorIdWhoCreatedNote;

    private boolean isProceduresDone;

    private boolean isSurgeryDone;

    private Long personIdWhoMadeProcedures;

    private String procedures;

    private String surgery;

    private Long patientId;
}