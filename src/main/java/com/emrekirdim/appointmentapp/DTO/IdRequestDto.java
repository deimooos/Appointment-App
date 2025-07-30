package com.emrekirdim.appointmentapp.DTO;

import javax.validation.constraints.NotNull;

public class IdRequestDto<T> {

    @NotNull(message = "ID cannot be null.")
    private T id;

    public IdRequestDto() {
    }

    public IdRequestDto(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
