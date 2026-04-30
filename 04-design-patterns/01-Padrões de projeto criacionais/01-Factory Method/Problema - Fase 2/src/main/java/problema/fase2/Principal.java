package src.main.java.problema.fase2;

public class Principal {
    public static void main(String[] args) {
        EmpresaLogistica empresaLogistica = new EmpresaLogistica();

        empresaLogistica.planejarEntrega("viario");
        empresaLogistica.planejarEntrega("maritimo");
    }
}
