package hexlet.code.app.service;

import hexlet.code.app.dto.label.LabelCreateDTO;
import hexlet.code.app.dto.label.LabelDTO;
import hexlet.code.app.dto.label.LabelUpdateDTO;

import java.util.List;

public interface LabelService {
    List<LabelDTO> findAll();
    LabelDTO findById(Long id);
    LabelDTO findByName(String name);
    LabelDTO create(LabelCreateDTO dto);
    LabelDTO updateById(LabelUpdateDTO dto, Long id);
    void deleteById(Long id);
    boolean existsByName(String name);
}
