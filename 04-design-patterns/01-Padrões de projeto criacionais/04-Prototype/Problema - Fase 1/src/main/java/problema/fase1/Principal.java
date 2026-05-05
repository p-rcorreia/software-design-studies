package problema.fase1;

public class Principal {

    public static void main(String[] args) {
        GeradorRelatorios geradorRelatorios = new GeradorRelatorios();

        Relatorio relatorio = geradorRelatorios.criarRelatorioMensal();
        relatorio.exibir();
    }
}
