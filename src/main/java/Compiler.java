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
                    // LEXER
                    lexer = new Lexer(reader);
                    
                    // PARSER
                    parser p = new parser(lexer);
                    ASTNode program = (ASTNode) p.parse().value;
                    LOGGER.info("Constructed AST");

                    // GLOBAL INSTANCE OF THE PROGRAM
				    Registry.getInstance().setRoot(program);
                    
                    // BUILDING SYMBOL TABLE
				    LOGGER.debug("Building symbol tables");
                    ASTVisitor symTableVisitor = new SymTableBuilderASTVisitor();
				    program.accept(symTableVisitor);
                    System.out.println("Scope counter is: " + ((SymTableBuilderASTVisitor) symTableVisitor).getScopeCounter());
                    
                    // COLLECTING SYMBOLS
				    LOGGER.debug("Semantic check (1st pass)");
				    program.accept(new CollectSymbolsASTVisitor());

                    // COLLECTING TYPES
				    LOGGER.debug("Semantic check (2nd pass)");
				    program.accept(new CollectTypesASTVisitor());                  
                    
                    // PRINTING AST
                    LOGGER.info("Input:");
                    ASTVisitor printVisitor = new PrintASTVisitor();
                    program.accept(printVisitor);

                    // PRINTING 3-ADDRESS CODE
                    LOGGER.info("3-address code:");
                    IntermediateCodeASTVisitor threeAddrVisitor = new IntermediateCodeASTVisitor();
                    program.accept(threeAddrVisitor);
                    String intermediateCode = threeAddrVisitor.getIntermediate().emit();
                    System.out.println(intermediateCode);
                    
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