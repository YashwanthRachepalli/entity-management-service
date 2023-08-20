package com.ams.entity;

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
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BUILDING")
public class Building implements Serializable {

    private static final long serialVersionUID = 7183956338934038472L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "building_id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @JdbcTypeCode(Types.BINARY)
    private UUID buildingId;

    @Column(name = "building_name", nullable = false)
    private String name;

    @Column(name = "address_line", nullable = false)
    private String address;

    @Column(name = "pin_code", nullable = false)
    private String pinCode;

}
