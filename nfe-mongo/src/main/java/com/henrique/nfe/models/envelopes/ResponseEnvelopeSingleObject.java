package com.henrique.nfe.models.envelopes;

import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class ResponseEnvelopeSingleObject<Entity, Dto> {

    @JsonIgnore
    private final Integer httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Dto content;

    public ResponseEnvelopeSingleObject(Dto content) {
        this.httpStatus = HttpStatus.OK.value();
        this.message = null;
        this.content = content;
    }

    public ResponseEnvelopeSingleObject(Integer httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.content = null;
    }

    public static <Entity, Dto> ResponseEnvelopeSingleObject<Entity, Dto> create(
            Entity entity, Function<Entity, Dto> mapper) {
        return new ResponseEnvelopeSingleObject<>(mapper.apply(entity));
    }

    public static <Entity, Dto> ResponseEnvelopeSingleObject<Entity, Dto> create(
            Integer httpStatus, String message) {
        return new ResponseEnvelopeSingleObject<>(httpStatus, message);
    }

    public ResponseEntity<ResponseEnvelopeSingleObject<Entity, Dto>> toResponse() {
        if (httpStatus == null)
            return ResponseEntity.ok(this);
        else
            return new ResponseEntity<>(this, HttpStatus.valueOf(httpStatus));
    }

}
