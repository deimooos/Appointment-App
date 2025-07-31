package com.emrekirdim.appointmentapp.Services;

import java.util.List;

public interface AdvancedGenericService<CreateDto, UpdateDto, ResponseDto, ID> {
    ResponseDto create(CreateDto dto);
    List<ResponseDto> getAll();
    ResponseDto update(UpdateDto dto);
    void delete(ID id);
}
