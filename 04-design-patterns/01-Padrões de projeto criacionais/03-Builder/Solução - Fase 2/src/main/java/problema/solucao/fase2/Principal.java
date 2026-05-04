package problema.solucao.fase2;

public class Principal {

    public static void main(String[] args) {

        DiretorConstrucao diretorConstrucao = new DiretorConstrucao();
        CasaBuilderPadrao builder = new CasaBuilderPadrao();

        diretorConstrucao.construirCasaComPiscina(builder);

        Casa casa = builder.construirCasa();
        casa.exibirDetalhes();
    }
}
