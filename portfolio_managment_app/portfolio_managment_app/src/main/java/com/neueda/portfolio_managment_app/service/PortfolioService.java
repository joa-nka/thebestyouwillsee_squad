package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioService {


    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public PortfolioEntity create(String name){
        return portfolioRepository.save(new PortfolioEntity(name));
    }

    public List<PortfolioEntity> findAll(){
        return portfolioRepository.findAll();
    }

    public PortfolioEntity findById(Long id){
        return portfolioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Portfolio not found: " + id));
    }

    @Transactional
    public PortfolioEntity rename(Long id, String newName){
        PortfolioEntity portfolioEntity = findById(id);
        portfolioEntity.setName(newName);
        return portfolioEntity;
    }

    public void delete(Long id){
        portfolioRepository.deleteById(id);
    }


}
