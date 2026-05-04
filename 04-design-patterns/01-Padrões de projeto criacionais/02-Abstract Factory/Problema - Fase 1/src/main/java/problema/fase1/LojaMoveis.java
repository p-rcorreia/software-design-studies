package src.main.java.problema.fase1;

public class LojaMoveis {
    public void montarSala() {
        CadeiraModerna cadeira = new CadeiraModerna();
        SofaModerno sofa = new SofaModerno();
        MesaDeCentroModerna mesaDeCentro = new MesaDeCentroModerna();

        cadeira.sentar();
        sofa.deitar();
        mesaDeCentro.apoiarObjeto();
    }
}
