package com.ams.entity;

import com.ams.model.ApartmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "APARTMENT")
public class Apartment implements Serializable {

    private static final long serialVersionUID = -6945611443505713556L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "apartment_id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @JdbcTypeCode(Types.BINARY)
    private UUID apartmentId;

    @Column(name = "apartment_name", unique = true)
    private String apartmentName;

    @Column(name = "apartment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApartmentType apartmentType;

    @Column(name = "apartment_size", nullable = false)
    private Double size;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "building_id")
    private Building building;

}
