package com.emrekirdim.appointmentapp.Controllers;

import com.emrekirdim.appointmentapp.Services.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public abstract class GenericController<T, ID> {

    protected abstract GenericService<T, ID> getService();

    protected abstract String getCreateMessage();
    protected abstract String getUpdateMessage();
    protected abstract String getDeleteMessage();

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody T dto) {
        getService().create(dto);
        return ResponseEntity.ok(getCreateMessage());
    }

    @GetMapping("/all")
    public ResponseEntity<List<T>> getAll() {
        return ResponseEntity.ok(getService().getAll());
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody T dto) {
        getService().update(dto);
        return ResponseEntity.ok(getUpdateMessage());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Map<String, ID> body) {
        ID id = body.get("id");
        getService().delete(id);
        return ResponseEntity.ok(getDeleteMessage());
    }
}
