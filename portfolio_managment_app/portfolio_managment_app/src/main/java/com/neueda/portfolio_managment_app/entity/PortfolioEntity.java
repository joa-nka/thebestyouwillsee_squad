package com.neueda.portfolio_managment_app.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class PortfolioEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;


    public PortfolioEntity(Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    protected PortfolioEntity(){

    }

    public PortfolioEntity(String name){
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @OneToMany(mappedBy =  "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PortfolioItemEntity> items = new ArrayList<>();


}
