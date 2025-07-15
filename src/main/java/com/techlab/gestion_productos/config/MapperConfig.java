package com.techlab.gestion_productos.config;

import com.techlab.gestion_productos.model.DTOs.OrderDTO;
import com.techlab.gestion_productos.model.Order;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setClientName(source.getClient().getName());
                map().setClientSurname(source.getClient().getSurname());
            }
        });

        return mapper;
    }
}
