package eu.libal.intrographs.presentation.layout;

import java.io.Serializable;
import java.util.HashMap;

public class LayoutMessage implements Serializable {

    static final long serialVersionUID = -12345642L;

    private final String layoutAlgorithm;
    private final HashMap<String, Double> variables;

    public LayoutMessage(String layoutAlgorithm, HashMap<String, Double> variables) {
        this.layoutAlgorithm = layoutAlgorithm;
        this.variables = variables;
    }

    public String getLayoutAlgorithm() {
        return layoutAlgorithm;
    }

    public HashMap<String, Double> getVariables() {
        return variables;
    }
}
