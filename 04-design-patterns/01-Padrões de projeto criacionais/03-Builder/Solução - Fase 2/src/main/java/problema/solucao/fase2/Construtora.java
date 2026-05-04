package problema.solucao.fase2;

public class Construtora {

    public Casa construirCasaSimples() {
        CasaBuilder builder = new CasaBuilderPadrao();

        builder.construirParedes(4);
        builder.construirPortas(1);
        builder.construirJanelas(2);
        builder.construirTeto("Telhado comum");
        builder.construirEncanamento();
        builder.construirEnergiaEletrica();
        builder.definirMaterial("Alvenaria");

        return builder.construirCasa();
    }   
}
