package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalDefinition extends Definition {

    private List<Definition> definitions;

    public LocalDefinition(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<Definition> getDefinitions() {
        List<Definition> reversedDefinitions = new ArrayList<>(definitions);
        Collections.reverse(reversedDefinitions);
        return reversedDefinitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
