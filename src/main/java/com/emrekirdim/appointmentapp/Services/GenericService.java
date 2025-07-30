package com.emrekirdim.appointmentapp.Services;

import java.util.List;

public interface GenericService<T, ID> {
    T create(T dto);
    List<T> getAll();
    T update(T dto);
    void delete(ID id);
}
