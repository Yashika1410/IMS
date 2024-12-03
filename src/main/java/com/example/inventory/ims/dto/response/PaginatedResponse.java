package com.example.inventory.ims.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatedResponse<T> {
    private int page;
    private int pageSize;
    private int totalPages;
    private int total;
    private List<T> list;
}
