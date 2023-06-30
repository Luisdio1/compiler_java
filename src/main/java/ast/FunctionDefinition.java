package ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionDefinition extends Definition {
    
    private Definition header;
    private List<Definition> definitions;
    private Statement block;

    public FunctionDefinition(Definition header, List<Definition> definitions, Statement block) {
        this.header = header;
        this.definitions = definitions;
        this.block = block;
    }

    public Definition getHeader() {
        return header;
    }

    public void setHeader(Definition header) {
        this.header = header;
    }

    public List<Definition> getDefinitions() {
        List<Definition> reversedDefinitions = new ArrayList<>(definitions);
        Collections.reverse(reversedDefinitions);
        return reversedDefinitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    public Statement getBlock() {
        return block;
    }

    public void setBlock(Statement block) {
        this.block = block;
    }

    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
