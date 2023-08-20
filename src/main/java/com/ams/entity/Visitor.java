package com.ams.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serializable;
import java.sql.Types;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VISITOR")
public class Visitor implements Serializable {

    private static final long serialVersionUID = -5320073797897703602L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "visitor_id", nullable = false)
    @JdbcTypeCode(Types.BINARY)
    private UUID visitorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "govt_issued_identifier")
    private String govtIssuedIdentifier;

    @Column(name = "mobile_number")
    private String mobileNumber;

}
