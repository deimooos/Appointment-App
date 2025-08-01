package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.DTO.IdRequestDto;
import com.emrekirdim.appointmentapp.Services.AdvancedGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class AdvancedGenericController<CreateDto, UpdateDto, ResponseDto, ID> {

    protected abstract AdvancedGenericService<CreateDto, UpdateDto, ResponseDto, ID> getService();

    protected abstract String getCreateMessage();
    protected abstract String getUpdateMessage();
    protected abstract String getDeleteMessage();

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody CreateDto dto) {
        getService().create(dto);
        return ResponseEntity.ok(getCreateMessage());
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseDto>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody UpdateDto dto) {
        getService().update(dto);
        return ResponseEntity.ok(getUpdateMessage());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@Valid @RequestBody IdRequestDto<ID> request) {
        getService().delete(request.getId());
        return ResponseEntity.ok(getDeleteMessage());
    }
}
