package com.example.study.controller;

import com.example.study.ifs.CrudInterface;
import com.example.study.model.network.Header;
import com.example.study.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Component
public abstract class CrudController<Req, Res, Entity> implements CrudInterface<Req, Res> {

    @Autowired(required = false)
    protected BaseService<Req, Res, Entity> baseService;

    @GetMapping("")
    public Header<List<Res>> search(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 15) Pageable pageable) {
        log.info("{}", pageable);
        return baseService.search(pageable);
    }

    @Override
    @PostMapping("")
    public Header<Res> create(@RequestBody Header<Req> req) {
        log.info("{}", req);
        return baseService.create(req);
    }

    @Override
    @GetMapping("{id}")
    public Header<Res> read(@PathVariable Long id) {
        log.info("{}", id);
        return baseService.read(id);
    }

    @Override
    @PutMapping("")
    public Header<Res> update(@RequestBody Header<Req> req) {
        log.info("{}", req);
        return baseService.update(req);
    }

    @Override
    @DeleteMapping("{id}")
    public Header delete(@PathVariable Long id) {
        log.info("{}", id);
        return baseService.delete(id);
    }
}
