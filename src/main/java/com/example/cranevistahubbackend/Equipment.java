package com.example.cranevistahubbackend;

import javax.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "equipment")
@Data // Lombok annotation to generate getters, setters, toString, etc.
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    private String location;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @Column(name = "working_hours")
    private Integer workingHours;

    @Column(name = "equipment_condition")
    private String equipmentCondition;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // Crane specific
    @Column(name = "cabin_temp_inside")
    private Double cabinTempInside;

    @Column(name = "cabin_temp_outside")
    private Double cabinTempOutside;

    private Double humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    // Mixer specific
    @Column(name = "mixing_temp")
    private Double mixingTemp;

    @Column(name = "mixing_speed")
    private Double mixingSpeed;

    @Column(name = "production_rate")
    private Double productionRate;

    // Maintenance info
    @Column(name = "last_maintenance")
    @Temporal(TemporalType.DATE)
    private Date lastMaintenance;

    @Column(name = "next_maintenance")
    @Temporal(TemporalType.DATE)
    private Date nextMaintenance;

}
