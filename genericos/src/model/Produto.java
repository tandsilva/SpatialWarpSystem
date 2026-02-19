package model;

// Remova todos os imports e anotações Lombok
public class Produto implements Comparable<Produto> {

    private String name;
    private Double price;  // Já corrigido

    // Adicione construtores manuais
    public Produto() {}

    public Produto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    // Adicione getters e setters manuais
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Produto other) {
        return this.price.compareTo(other.price);
    }
}