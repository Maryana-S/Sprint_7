package ru.practicum.services.qa.scooter.models.orders;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageInfo {

    private Integer page;

    private Integer total;

    private Integer limit;

}