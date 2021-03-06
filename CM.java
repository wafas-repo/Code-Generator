/*
  Created by: Fei Song
  File Name: Main.java
  To Build: 
  After the scanner, tiny.flex, and the parser, tiny.cup, have been created.
    javac Main.java
  
  To Run: 
    java -classpath /usr/share/java/cup.jar:. Main gcd.tiny

  where gcd.tiny is an test input file for the tiny language.
*/
   
import java.io.*;
import absyn.*;
   
class CM {
  public static boolean SHOW_TREE = false;
  public static boolean SHOW_TABLE = false;
  public static boolean CODE_GEN = false;
  public static final String EXT_ABS_STRING = ".abs";
  public static final String EXT_SYM_STRING = ".sym";
  public static final String EXT_TM_STRING = ".tm";
  public static boolean SEMANTIC_ERROR = false;

  static public void main(String argv[]) {    
    /* Start the parser */
    int n = 0;
    String st = "-a";
    String file = "";

    for (int i = 0; i < argv.length; i++) {
        if(argv[i].equals("-a")){
          SHOW_TREE = true;
        } else if (argv[i].equals("-s")){
          SHOW_TABLE = true;
        } else if (argv[i].equals("-c")){
          CODE_GEN = true;
        } else if (argv[i].endsWith(".cm")) {
          file = argv[i];
        }
    }

    try {
      parser p = new parser(new Lexer(new FileReader(file)));
      Absyn result = (Absyn)(p.parse().value);
      if(file != null && file.contains(".")) 
        file = file.substring(0, file.lastIndexOf('.'));
      if (SHOW_TREE && result != null) {
        String output_file = file + EXT_ABS_STRING; 
        PrintStream o = new PrintStream(new File(output_file));
        PrintStream console = System.out;  
        System.setOut(o);  
        System.out.println("The abstract syntax tree is:");
        ShowTreeVisitor visitor = new ShowTreeVisitor();
        result.accept(visitor, 0, false, 0);
      }
      if (SHOW_TABLE) {
        String output_file = file + EXT_SYM_STRING; 
        PrintStream o = new PrintStream(new File(output_file));
        PrintStream console = System.out;  
        System.setOut(o);  
        SemanticAnalyzer analyzer = new SemanticAnalyzer();
        result.accept(analyzer, 0, false, 0);
        if(analyzer.SEMANTIC_ERROR == true) SEMANTIC_ERROR = true;
      }

      if(SEMANTIC_ERROR == true) {
        System.out.println("\n\nErrors are present in " + argv[0] + ", cannot compile");
        System.exit(0);
      }

      if ((CODE_GEN == true) && (SEMANTIC_ERROR != true)) {
        String output_file = file + EXT_TM_STRING;
        PrintStream o = new PrintStream(new File(output_file));
        PrintStream console = System.out;
        System.setOut(o);
       // CodeGenerator.prelude(output_file);
        CodeGenerator generator = new CodeGenerator();
        generator.visit((DecList) result);
        //result.accept(generator, 0, false);
       // CodeGenerator.finale();

      }


    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


