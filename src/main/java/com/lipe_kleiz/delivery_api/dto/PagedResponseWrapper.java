package com.lipe_kleiz.delivery_api.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Wrapper para respostas paginadas")
public class PagedResponseWrapper<T> {

@Schema(description = "Conteúdo da página")
private List<T> content;

@Schema(description = "Informações de paginação")
private PageInfo page;

public PagedResponseWrapper(Page<T> page) {

    this.content = page.getContent();

    this.page = new PageInfo(
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast()
    );
}

@Data
@Schema(description = "Informações da página")
public static class PageInfo {

    @Schema(
        description = "Número da página atual",
        example = "0"
    )
    private int number;

    @Schema(
        description = "Quantidade de registros por página",
        example = "10"
    )
    private int size;

    @Schema(
        description = "Total de registros",
        example = "50"
    )
    private long totalElements;

    @Schema(
        description = "Total de páginas",
        example = "5"
    )
    private int totalPages;

    @Schema(
        description = "Indica se é a primeira página",
        example = "true"
    )
    private boolean first;

    @Schema(
        description = "Indica se é a última página",
        example = "false"
    )
    private boolean last;

    public PageInfo(
            int number,
            int size,
            long totalElements,
            int totalPages,
            boolean first,
            boolean last) {

        this.number = number;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }
}

}
