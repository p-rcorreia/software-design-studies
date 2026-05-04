package problema.fase2;

public class Principal {

    public static void main(String[] args) {
        Construtora construtora = new Construtora();

        Casa casaSimples = construtora.construirCasaSimples();
        Casa casaComPiscina = construtora.construirCasaComPiscina();
        Casa casaDeMadeira = construtora.construirCasaDeMadeira();

        casaSimples.exibirDetalhes();
        System.out.println();

        casaComPiscina.exibirDetalhes();
        System.out.println();

        casaDeMadeira.exibirDetalhes();
    }
}
