package problema.solucao.fase2;

public class Casa {

    private int paredes;
    private int portas;
    private int janelas;
    private String teto;
    private boolean garagem;
    private boolean jardim;
    private boolean piscina;
    private boolean aquecimento;
    private boolean encanamento;
    private boolean energiaEletrica;
    private String material;
    private String observacao;

    public Casa(
            int paredes,
            int portas,
            int janelas,
            String teto,
            boolean garagem,
            boolean jardim,
            boolean piscina,
            boolean aquecimento,
            boolean encanamento,
            boolean energiaEletrica,
            String material,
            String observacao) {
        this.paredes = paredes;
        this.portas = portas;
        this.janelas = janelas;
        this.teto = teto;
        this.garagem = garagem;
        this.jardim = jardim;
        this.piscina = piscina;
        this.aquecimento = aquecimento;
        this.encanamento = encanamento;
        this.energiaEletrica = energiaEletrica;
        this.material = material;
        this.observacao = observacao;
    }

    public void exibirDetalhes() {
        System.out.println("Casa:");
        System.out.println("Paredes: " + paredes);
        System.out.println("Portas: " + portas);
        System.out.println("Janelas: " + janelas);
        System.out.println("Teto: " + teto);
        System.out.println("Garagem: " + simOuNao(garagem));
        System.out.println("Jardim: " + simOuNao(jardim));
        System.out.println("Piscina: " + simOuNao(piscina));
        System.out.println("Aquecimento: " + simOuNao(aquecimento));
        System.out.println("Encanamento: " + simOuNao(encanamento));
        System.out.println("Energia eletrica: " + simOuNao(energiaEletrica));
        System.out.println("Material: " + valorOuNenhum(material));
        System.out.println("Observacao: " + valorOuNenhum(observacao));
    }

    private String simOuNao(boolean valor) {
        return valor ? "Sim" : "Nao";
    }

    private String valorOuNenhum(String valor) {
        if (valor == null || valor.isBlank()) {
            return "Nenhum";
        }

        return valor;
    }
}
