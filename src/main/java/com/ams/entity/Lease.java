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
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "LEASE")
public class Lease implements Serializable {

    private static final long serialVersionUID = 44601175625278557L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "lease_id", updatable = false, nullable = false)
    @ColumnDefault("random_uuid()")
    @JdbcTypeCode(Types.BINARY)
    private UUID leaseId;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(name = "security_deposit")
    private Double securityDeposit;

    @Column(name = "rental_fee")
    private Double rentalFee;

    @Column(name = "rental_date")
    @Temporal(TemporalType.DATE)
    private LocalDate rentalDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenantId", nullable = false)
    private Tenant tenant;

}
