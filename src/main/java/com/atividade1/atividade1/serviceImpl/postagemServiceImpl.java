package com.atividade1.atividade1.serviceImpl;

import com.atividade1.atividade1.model.postagemModel;
import com.atividade1.atividade1.service.postagemService;
import org.springframework.beans.factory.annotation.Autowired;
import com.atividade1.atividade1.repository.postagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class postagemServiceImpl implements postagemService {

    @Autowired
    postagemRepository postagemRepository;
    @Override
    public List<postagemModel> findAll() {
        return postagemRepository.findAll();
    }

    @Override
    public postagemModel findById(long id) {
        return postagemRepository.findById(id).get();
    }

    @Override
    public postagemModel save(postagemModel postagemModel) {
        return postagemRepository.save(postagemModel);
    }
}
