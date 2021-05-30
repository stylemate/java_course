package com.example.study.ifs;

import com.example.study.model.network.Header;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CrudInterface<Req, Res> {
    Header<Res> create(Header<Req> req);
    Header<Res> read(Long id);
    Header<Res> update(Header<Req> req);
    Header delete(Long id);
    Header<List<Res>> search(Pageable pageable);
}
