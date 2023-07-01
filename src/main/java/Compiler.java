
import ast.ASTNode;
import ast.ASTVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Compiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Compiler.class);

    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.info("Usage : java Compiler [ --encoding <name> ] <inputfile(s)>");
        } else {
            int firstFilePos = 0;
            String encodingName = "UTF-8";
            if (args[0].equals("--encoding")) {
                firstFilePos = 2;
                encodingName = args[1];
                try {
                    java.nio.charset.Charset.forName(encodingName); // Side-effect: is encodingName valid? 
                } catch (Exception e) {
                    LOGGER.error("Invalid encoding '" + encodingName + "'");
                    return;
                }
            }
            for (int i = firstFilePos; i < args.length; i++) {
                Lexer lexer = null;
                try {
                    java.io.FileInputStream stream = new java.io.FileInputStream(args[i]);
                    LOGGER.info("Scanning file " + args[i]);
                    java.io.Reader reader = new java.io.InputStreamReader(stream, encodingName);
                    lexer = new Lexer(reader);
                    
                    // parse
                    parser p = new parser(lexer);
                    ASTNode program = (ASTNode) p.parse().value;
                    LOGGER.info("Constructed AST");

                    // keep global instance of program
				    Registry.getInstance().setRoot(program);
                    
                    // build symbol table
				    LOGGER.debug("Building symbol tables");
				    program.accept(new SymTableBuilderASTVisitor());
                    
                    // construct types
				    LOGGER.debug("Semantic check (1st pass)");
				    program.accept(new CollectSymbolsASTVisitor());
				    // LOGGER.debug("Semantic check (2nd pass)");
				    // program.accept(new CollectTypesASTVisitor());                  
                    
                    // print program
                    LOGGER.info("Input:");
                    ASTVisitor printVisitor = new PrintASTVisitor();
                    program.accept(printVisitor);

                    // build symbol table
                    ASTVisitor symTableVisitor = new SymTableBuilderASTVisitor();
                    program.accept(symTableVisitor);
                    
                    LOGGER.info("Compilation done");
                } catch (java.io.FileNotFoundException e) {
                    LOGGER.error("File not found : \"" + args[i] + "\"");
                } catch (java.io.IOException e) {
                    LOGGER.error("IO error scanning file \"" + args[i] + "\"");
                    LOGGER.error(e.toString());
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

}