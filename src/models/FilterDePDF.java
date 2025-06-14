package models;

import java.util.ArrayList;
import java.util.List;

public class FilterDePDF<T extends ArquivoPDF> {
    private final Class<T> tipo;

    public FilterDePDF(Class<T> tipo) {
        this.tipo = tipo;
    }

    public List<T> filter(List<ArquivoPDF> list) {
        List<T> resultado = new ArrayList<>();
        for (ArquivoPDF pdf : list) {
            if (tipo.isInstance(pdf)) {
                resultado.add(tipo.cast(pdf));
            }
        }
        return resultado;
    }
}

