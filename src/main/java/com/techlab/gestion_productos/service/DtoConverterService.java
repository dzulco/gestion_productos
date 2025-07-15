package com.techlab.gestion_productos.service;

import com.techlab.gestion_productos.model.Client;
import com.techlab.gestion_productos.model.DTOs.*;
import com.techlab.gestion_productos.model.Order;
import com.techlab.gestion_productos.model.OrderLine;
import com.techlab.gestion_productos.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoConverterService {

    @Autowired
    private ModelMapper modelMapper;

    public ProductDTO toProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public OrderLineDTO toOrderLineDTO(OrderLine line) {
        OrderLineDTO dto = modelMapper.map(line, OrderLineDTO.class);
        dto.setProduct(toProductDTO(line.getProduct()));
        return dto;
    }

    public OrderDTO toOrderDTO(Order order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        List<OrderLineDTO> lines = order.getOrderLines().stream()
                .map(this::toOrderLineDTO)
                .collect(Collectors.toList());
        dto.setOrderLines(lines);
        return dto;
    }

    public ClientDTO toClientDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }

    public Order fromOrderRequestDTO(OrderRequestDTO dto) {
        Order order = new Order();

        // Asociar cliente por ID (sin cargar de DB)
        Client client = new Client();
        client.setId(dto.clientId());
        order.setClient(client);

        List<OrderLine> orderLines = new ArrayList<>();

        for (OrderLineRequestDTO lineDto : dto.lineRequestDTOList()) {
            Product product = new Product(lineDto.productId());

            OrderLine line = new OrderLine();
            line.setProduct(product);
            line.setQuantity(lineDto.quantity());
            line.setOrder(order); // relación inversa

            orderLines.add(line);
        }

        order.setOrderLines(orderLines);
        return order;
    }
}
