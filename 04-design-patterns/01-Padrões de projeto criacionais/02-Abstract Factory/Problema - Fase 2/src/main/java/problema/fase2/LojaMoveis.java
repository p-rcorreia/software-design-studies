package src.main.java.problema.fase2;

import src.main.java.problema.solucao.CadeiraModerna;
import src.main.java.problema.solucao.CadeiraVitoriana;
import src.main.java.problema.solucao.MesaDeCentroModerna;
import src.main.java.problema.solucao.SofaModerno;
import src.main.java.problema.solucao.SofaVitoriano;

public class LojaMoveis {
    public void montarSala(String estilo) {
        if ("moderno".equalsIgnoreCase(estilo)) {
            CadeiraModerna cadeira = new CadeiraModerna();
            SofaModerno sofa = new SofaModerno();
            MesaDeCentroModerna mesaDeCentro = new MesaDeCentroModerna();

            cadeira.sentar();
            sofa.deitar();
            mesaDeCentro.apoiarObjeto();
            return;
        }

        if ("vitoriano".equalsIgnoreCase(estilo)) {
            CadeiraVitoriana cadeira = new CadeiraVitoriana();
            SofaVitoriano sofa = new SofaVitoriano();
            MesaDeCentroModerna mesaDeCentro = new MesaDeCentroModerna();

            cadeira.sentar();
            sofa.deitar();
            mesaDeCentro.apoiarObjeto();
            return;
        }

        throw new IllegalArgumentException("Estilo desconhecido: " + estilo);
    }
}
