package com.atividade1.atividade1.serviceImpl;

import com.atividade1.atividade1.model.alimentoModel;
import com.atividade1.atividade1.service.alimentoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import com.atividade1.atividade1.repository.alimentoRepository;
import org.springframework.stereotype.Service;

@Service
public class alimentoServiceImpl implements alimentoService {

    @Autowired alimentoRepository alimentoRepository;
    @Override
    public List<alimentoModel> findAll() {
        return alimentoRepository.findAll();
    }

    @Override
    public alimentoModel findById(long id) {
        return alimentoRepository.findById(id).get();
    }

    @Override
    public alimentoModel save(alimentoModel alimentoModel) {
        return alimentoRepository.save(alimentoModel);
    }

    @Override
    public List<alimentoModel> findAlimentosByCalorias(int calorias) {
        return alimentoRepository.findAlimentosByCalorias(calorias);
    }

    @Override
    public List<alimentoModel> findAlimentosByNomeLike(String nome) {
        return alimentoRepository.findAlimentosByNomeLike(nome);
    }
}
