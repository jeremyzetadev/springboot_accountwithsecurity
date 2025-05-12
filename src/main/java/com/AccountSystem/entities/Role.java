package com.vikingbank.entities;


import javax.persistence.*;


@Entity

@Table(name = "tb_role")

public class Role {


    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;


    public Role() {


    }


    public Role(String name) {

        super();

        this.name = name;

    }


    public String toString() {

        return name;

    }


    public Long getId() {

        return id;

    }


    private void setId(Long id) {

        this.id = id;

    }


    public String getName() {

        return name;

    }


    private void setName(String name) {

        this.name = name;

    }

}
