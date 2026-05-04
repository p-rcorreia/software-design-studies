package problema.fase2;

public class Construtora {

    public Casa construirCasaSimples() {
        return new Casa(
                4,
                1,
                2,
                "Telhado comum",
                false,
                false,
                false,
                false,
                true,
                true,
                "Alvenaria",
                null);
    }

    public Casa construirCasaComPiscina() {
        return new Casa(
                8,
                3,
                6,
                "Telhado colonial",
                true,
                true,
                true,
                false,
                true,
                true,
                "Alvenaria",
                "Area externa com churrasqueira");
    }

    public Casa construirCasaDeMadeira() {
        return new Casa(
                4,
                1,
                4,
                "Telhado inclinado",
                false,
                true,
                false,
                true,
                true,
                true,
                "Madeira",
                "Casa compacta para regiao fria");
    }
}
