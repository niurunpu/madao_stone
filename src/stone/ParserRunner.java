package stone;
import ast.ASTree;
import ast.Environment;

public class ParserRunner 
{
    public static void main(String[] args) throws ParseException
    {
    	Lexer l = new Lexer(new CodeDialog());
    	ArrayParser cp = new ArrayParser();
    	NestedEnv e = new NestedEnv();
    	Environment env = new Natives().environment(e);
    	
    	while (l.peek(0) != Token.EOF)
    	{
    		ASTree ast = cp.parse(l);
    		//System.out.println("=> " + ast.toString());
    		//System.out.println(ast.eval(env));
    		ast.eval(env);
    	}
    	
    }
}
