package hexlet.code.app.service.impl;

import hexlet.code.app.dto.label.LabelCreateDTO;
import hexlet.code.app.dto.label.LabelDTO;
import hexlet.code.app.dto.label.LabelUpdateDTO;
import hexlet.code.app.exception.ResourceNotFoundException;
import hexlet.code.app.mapper.LabelMapper;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository repository;

    @Autowired
    private LabelMapper mapper;

    @Override
    public List<LabelDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    @Override
    public LabelDTO findById(Long id) {
        var model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Label with id %d not found", id)));

        return mapper.map(model);
    }

    @Override
    public LabelDTO findByName(String name) {
        var model = repository.findByName(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException(String.format("Label with name %s is not found", name)));

        return mapper.map(model);
    }

    @Override
    public LabelDTO create(LabelCreateDTO dto) {
        var model = mapper.map(dto);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public LabelDTO updateById(LabelUpdateDTO dto, Long id) {
        var model = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Label with id %d not found", id)));

        mapper.update(dto, model);
        repository.save(model);
        return mapper.map(model);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
