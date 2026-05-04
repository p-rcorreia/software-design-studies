package problema.solucao.fase1;

public class Principal {

    public static void main(String[] args) {
        Construtora construtora = new Construtora();
        Casa casaSimples = construtora.construirCasaSimples();
        casaSimples.exibirDetalhes();
    }
}
