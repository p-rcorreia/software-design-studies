package problema.fase1;

public class Principal {

    public static void main(String[] args) {
        Construtora construtora = new Construtora();

        Casa casa = construtora.construirCasaSimples();
        casa.exibirDetalhes();
    }
}
