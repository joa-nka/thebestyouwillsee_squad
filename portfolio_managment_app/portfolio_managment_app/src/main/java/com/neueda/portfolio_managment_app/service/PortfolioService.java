package com.neueda.portfolio_managment_app.service;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.repository.PortfolioItemRepository;
import com.neueda.portfolio_managment_app.repository.PortfolioRepository;
import jakarta.transaction.Transactional;

import java.util.List;

public class PortfolioService {


    private final PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public PortfolioEntity create(String name){ return portfolioRepository.save(new PortfolioEntity(name)); }
    public List<PortfolioEntity> findAll(){ return portfolioRepository.findAll(); }
    public PortfolioEntity findById(Long id){ return portfolioRepository.findById(id).orElseThrow(); }

    @Transactional
    public PortfolioEntity rename(Long id, String newName){
        var p = findById(id); p.setName(newName); return p;
    }
    public void delete(Long id){ portfolioRepository.deleteById(id); }






}
