package problema.fase2;

public class ConfiguracaoGlobal {

    public static String ambiente = "produção";
    public static String urlBancoDados = "jdbc:mysql://localhost:3306/app";
    public static String remetenteEmail = "sistema@empresa.com";
    public static String formatoRelatorio = "PDF";

    public static void exibirResumo() {
        System.out.println("Ambiente: " + ambiente);
        System.out.println("Banco: " + urlBancoDados);
        System.out.println("Remetente: " + remetenteEmail);
        System.out.println("Formato de relatório: " + formatoRelatorio);
    }
}
