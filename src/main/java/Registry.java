import ast.ASTNode;

public class Registry {

    ASTNode root;

    private Registry() {
        root = null;
    }

    private static class SingletonHolder {
        public static final Registry instance = new Registry();
    }

    public static Registry getInstance() {
        return SingletonHolder.instance;
    }

    public ASTNode getRoot() {
        return root;
    }

    public void setRoot(ASTNode root) {
        this.root = root;
    }
}
