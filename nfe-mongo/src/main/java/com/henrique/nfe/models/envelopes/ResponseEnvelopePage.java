package com.henrique.nfe.models.envelopes;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ResponseEnvelopePage<Entity, Dto> {

    @JsonIgnore
    private final Integer httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<Dto> content;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long totalElements;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer totalPages;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer number;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer numberOfElements;

    public ResponseEnvelopePage(Page<Entity> entityPage, List<Dto> content) {
        this.httpStatus = null;
        this.message = null;
        this.totalElements = entityPage.getTotalElements();
        this.totalPages = entityPage.getTotalPages();
        this.number = entityPage.getNumber();
        this.numberOfElements = entityPage.getNumberOfElements();
        this.content = content;
    }

    public ResponseEnvelopePage(Integer httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.totalElements = null;
        this.totalPages = null;
        this.number = null;
        this.numberOfElements = null;
        this.content = null;
    }

    public static <Entity, Dto> ResponseEnvelopePage<Entity, Dto> create(
            Page<Entity> entityPage, Function<Entity, Dto> mapper) {
        return new ResponseEnvelopePage<>(entityPage,
                entityPage.getContent().stream().map(mapper::apply).collect(Collectors.toList()));
    }

    public static <Entity, Dto> ResponseEnvelopePage<Entity, Dto> create(
            Integer httpStatus, String message) {
        return new ResponseEnvelopePage<>(httpStatus, message);
    }

    public ResponseEntity<ResponseEnvelopePage<Entity, Dto>> toResponse() {
        if (httpStatus == null)
            return ResponseEntity.ok(this);
        else
            return new ResponseEntity<>(this, HttpStatus.valueOf(httpStatus));
    }

}
