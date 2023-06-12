package com.scos.data_model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "\"TABLE_TEST\"")
@Table(name = "\"TEST\"")
public class TEST {

    @Id
    @Column(name = "\"ID\"", nullable = false)
    private Integer id;

    @Column(name = "\"NAME\"")
    private String firstName;

    @Column(name = "\"SURNAME\"")
    private String lastName;

}
