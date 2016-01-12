import java.util.*;

public abstract class TypeChecker extends Parser
{
	public static HashMap<String,TypeVal> typeMap = new HashMap<String,TypeVal>(); // records declared types of variables
	public static HashMap<String,Integer> addressMap = new HashMap<String,Integer>();

	public final static Int_Type int_Type = new Int_Type();
	public final static Float_Type float_Type = new Float_Type();
	public final static Boolean_Type boolean_Type = new Boolean_Type();
	public final static TypeCorrect typeCorrect = new TypeCorrect();
	public final static TypeError typeError = new TypeError();
	public static int address = 0;
	public static int gt = 1;

	public static void main(String argv[])
	{
		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + "  -- unexpected symbol");
		else if ( ! syntaxErrorFound )
		{
			boolean varDecErrorFound = program.buildTypeMap();

			displayln("");
			displayln("Display types of variables:");
			displayln("");
			displayln(typeMap.toString());
			displayln("");

			if ( ! varDecErrorFound )
			{
				TypeVal programType = program.typeEval(); // perform type checking
				if ( programType == typeCorrect )
					displayln("The program has passed type checking.");
				displayln("");
				displayln("Display addresses and base addresses:");
				displayln("");
				display(addressMap.toString());
				
				closeIO();
				setIO( argv[0], argv[2] );
				program.emitInstructions();
			}
		}

		closeIO();
	}
}