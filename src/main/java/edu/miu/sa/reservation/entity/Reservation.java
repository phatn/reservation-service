package edu.miu.sa.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Reservation {

    @PrimaryKey
    @Column
    private UUID id;

    @Column
    private UUID propertyId;

    @Column
    private String paymentType;

    @Column
    private double price;

    @Column
    private int night;

    @Column
    private double total;
}
