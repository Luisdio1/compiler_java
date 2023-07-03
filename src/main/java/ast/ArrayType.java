package ast;

import java.util.List;
import org.objectweb.asm.Type;

public class ArrayType extends ASTNode {
    
    private Type type;
    private List<Integer> size;
    private boolean brack;
    
    public ArrayType(Type type, List<Integer> size, boolean brack) {
        this.type = type;
        this.size = size;
        this.brack = brack;
    }

    public ArrayType(Type type, List<Integer> size) {
        this.type = type;
        this.size = size;
    }
    
    public Type getType() {
        return type;
    }

    public List<Integer> getSize() {
        return size;
    }

    public boolean getBrack() {
        return brack;
    }

    public Type getArrayType() {
        Type newtype;
        if ((this.size.size() > 0) || this.brack) {
            newtype = Type.getType("[" + this.type.getDescriptor());
        } else {
            newtype = this.type;
        }
        return newtype;
    }
    
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
        visitor.visit(this);
    }
}
