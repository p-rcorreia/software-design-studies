package problema.fase1;

public class Casa {

    private int paredes;
    private int portas;
    private int janelas;
    private String teto;

    public Casa(int paredes, int portas, int janelas, String teto) {
        this.paredes = paredes;
        this.portas = portas;
        this.janelas = janelas;
        this.teto = teto;
    }

    public void exibirDetalhes() {
        System.out.println("Casa simples:");
        System.out.println("Paredes: " + paredes);
        System.out.println("Portas: " + portas);
        System.out.println("Janelas: " + janelas);
        System.out.println("Teto: " + teto);
    }
}
