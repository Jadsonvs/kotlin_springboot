package br.com.alura.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Usuario(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val email: String,
    val senha: String,

    @JsonIgnore //Irá ignorá esse atributo para exibição
    @ManyToMany(fetch = FetchType.EAGER)//Tipo de relacionamento e irá trazer toda tabela
    @JoinColumn(name = "usuario_role")// Guardará a chave de "usuario_role"
    val role: List<Role> = mutableListOf()
)

