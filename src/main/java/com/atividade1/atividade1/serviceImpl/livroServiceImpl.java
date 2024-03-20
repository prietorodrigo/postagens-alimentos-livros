package com.atividade1.atividade1.serviceImpl;

import com.atividade1.atividade1.model.livroModel;
import com.atividade1.atividade1.service.livroService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.atividade1.atividade1.repository.livroRepository;
import org.springframework.stereotype.Service;

@Service
public class livroServiceImpl implements livroService {

    @Autowired livroRepository livroRepository;
    @Override
    public List<livroModel> findAll() {
        return livroRepository.findAll();
    }

    @Override
    public livroModel findById(long id) {
        return livroRepository.findById(id).get();
    }

    @Override
    public livroModel save(livroModel livroModel) {
        return livroRepository.save(livroModel);
    }
}
