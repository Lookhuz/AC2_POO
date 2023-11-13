package BasedeDados;

import java.time.LocalDate;

public class efetuarcompra extends Item {

    private int quantidade;
    private String nomeProduto;
    private double valorUnitario;
    private double valorTotal;
    private LocalDate dataAtual;
    private int numeroCompra; // Novo atributo para representar o número da compra

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public efetuarcompra() {
        dataAtual = LocalDate.now();
        System.out.println("A data atual é: " + dataAtual);
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getDataAtual(){
        return dataAtual;
    }

    public int getNumeroCompra() {
        return numeroCompra;
    }

    public void setNumeroCompra(int numeroCompra) {
        this.numeroCompra = numeroCompra;
    }

    // criar um override para o toString
    @Override
    public String toString() {
        return quantidade +
                "," + nomeProduto +
                "," + valorUnitario +
                "," + valorTotal +
                "," + dataAtual +
                "," + numeroCompra; // Adicionado ao toString
    }
}
