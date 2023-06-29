package ast;

import java.util.List;

public class LocalDefinition extends Definition {

    private List<Definition> definitions;

    public LocalDefinition(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
    
}
