package edu.miu.sa.reservation.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private String paymentType;

    private int propertyId;

    private double price;

    private int night;

}
