package problema.fase2;

public class Principal {

    public static void main(String[] args) {
        GeradorRelatorios geradorRelatorios = new GeradorRelatorios();

        Relatorio janeiro = geradorRelatorios.criarRelatorioFinanceiroJaneiro();
        Relatorio fevereiro = geradorRelatorios.criarRelatorioFinanceiroFevereiro();
        Relatorio marco = geradorRelatorios.criarRelatorioFinanceiroMarco();

        janeiro.exibir();
        System.out.println();

        fevereiro.exibir();
        System.out.println();

        marco.exibir();
    }
}
