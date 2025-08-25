package com.neueda.portfolio_managment_app.controller;

import com.neueda.portfolio_managment_app.entity.PortfolioItemEntity;
import com.neueda.portfolio_managment_app.service.PortfolioItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PortfolioItemController {

    private final PortfolioItemService portfolioItemService;

    public PortfolioItemController(PortfolioItemService portfolioItemService) {
        this.portfolioItemService = portfolioItemService;
    }

    @PostMapping("/portfolios/{portfolioId}/items")
    public PortfolioItemEntity add(@PathVariable Long portfolioId, @RequestParam String assetCode, @RequestParam int quantity){
        return portfolioItemService.addItem(portfolioId, assetCode, quantity);
    }

    @GetMapping("/portfolios/{portfolioId}/items")
    public List<PortfolioItemEntity> list(@PathVariable Long portfolioId){
        return portfolioItemService.findByPortfolio(portfolioId);
    }

    @GetMapping("/items/{itemId}")
    public PortfolioItemEntity get(@PathVariable Long itemId){
        return portfolioItemService.findOne(itemId);
    }

    @PutMapping("/items/{itemId}/quantity")
    public PortfolioItemEntity changeQty(@PathVariable Long itemId, @RequestParam int quantity){
        return portfolioItemService.changeQty(itemId, quantity);
    }

    @PutMapping("/items/{itemId}/asset")
    public PortfolioItemEntity changeAsset(@PathVariable Long itemId, @RequestParam String assetCode){
        return portfolioItemService.changeAssetCode(itemId, assetCode);
    }

    @DeleteMapping("/items/{itemId}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long itemId){
        portfolioItemService.delete(itemId);
    }
}
