package problema.solucao.fase2;

public interface CasaBuilder {

    void construirParedes(int numeroDeParedes);
    void construirPortas(int numeroDePortas);
    void construirJanelas(int numeroDeJanelas);
    void construirTeto(String teto);
    void construirGaragem();
    void construirJardim();
    void construirPiscina();
    void construirAquecimento();
    void construirEncanamento();
    void construirEnergiaEletrica();
    void definirMaterial(String material);
    void adicionarObservacao(String observacao);
    Casa construirCasa();
}
