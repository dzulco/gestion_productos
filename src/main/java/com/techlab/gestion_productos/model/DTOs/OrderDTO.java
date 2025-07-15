package com.techlab.gestion_productos.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String clientName;
    private String clientSurname;
    private String state;
    private Double total;
    private List<OrderLineDTO> orderLines;
}
