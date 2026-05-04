package problema.solucao.fase2;

public class DiretorConstrucao {

    public void construirCasaSimples(CasaBuilder builder) {
        builder.construirParedes(4);
        builder.construirPortas(1);
        builder.construirJanelas(2);
        builder.construirTeto("Telhado comum");
        builder.construirEncanamento();
        builder.construirEnergiaEletrica();
        builder.definirMaterial("Alvenaria");
    }

    public void construirCasaComPiscina(CasaBuilder builder) {
        builder.construirParedes(8);
        builder.construirPortas(3);
        builder.construirJanelas(6);
        builder.construirTeto("Telhado colonial");
        builder.construirGaragem();
        builder.construirJardim();
        builder.construirPiscina();
        builder.construirEncanamento();
        builder.construirEnergiaEletrica();
        builder.definirMaterial("Alvenaria");
        builder.adicionarObservacao("Área externa com churrasqueira");
    }

    public void construirCasaDeMadeira(CasaBuilder builder) {
        builder.construirParedes(4);
        builder.construirPortas(1);
        builder.construirJanelas(4);
        builder.construirTeto("Telhado inclinado");
        builder.construirJardim();
        builder.construirAquecimento();
        builder.construirEncanamento();
        builder.construirEnergiaEletrica();
        builder.definirMaterial("Madeira");
        builder.adicionarObservacao("Casa compacta para região fria");
    }
}
