package com.neueda.portfolio_managment_app.controller;

import com.neueda.portfolio_managment_app.entity.PortfolioEntity;
import com.neueda.portfolio_managment_app.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public PortfolioEntity create (@RequestParam String name){
        return portfolioService.create(name);
    }

    @GetMapping
    public List<PortfolioEntity>findAll(){
        return portfolioService.findAll();
    }

    @GetMapping("/{id}")
    public PortfolioEntity findById(@PathVariable Long id){
        return portfolioService.findById(id);
    }

    @PutMapping("/{id}/name")
    public PortfolioEntity rename(@PathVariable Long id,@RequestParam String name){
        return portfolioService.rename(id,name);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        portfolioService.delete(id);
    }
}

