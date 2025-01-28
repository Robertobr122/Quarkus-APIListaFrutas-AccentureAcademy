package acc.br.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Fruta extends PanacheEntity {

    @NotBlank(message = "O nome da fruta não pode estar vazio.")
    public String nome;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1.")
    public int qtd;
}
