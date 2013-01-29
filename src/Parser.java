import java.util.ArrayList;
import java.util.Stack;


public class Parser {
public Parsing_Table parser;
private Scanneree S;
private Stack<String> stack;
private ArrayList<Tokens> tokens;
private Tokens[]remainingInput;
public String errorMessage = "";
 public Parser(Scanneree scanere){
	 this.S = scanere;
	 parse();
 }
 
 private String parse(){
	 parser = new Parsing_Table();
	 stack = new Stack<String>();
	 tokens = S.getTokens();
	 remainingInput = new Tokens[tokens.size()+1];
	 for(int i=0 ; i<remainingInput.length-1 ; i++){
		 remainingInput[i] = tokens.get(i) ;
	 }
	 remainingInput[remainingInput.length-1] = new Tokens("$",0);
	 remainingInput[remainingInput.length-1].setLineNumber(remainingInput[remainingInput.length-2].getLineNumber());
	 stack.push("program");
	 int pointer = 0;
	 boolean isInvalid = false;
	 String sign = "";
	 
	 while(!stack.isEmpty() && pointer<remainingInput.length){
	     if(pointer+2<remainingInput.length-1&&remainingInput[pointer].token.equals("main")&& 
	        remainingInput[pointer+1].token.equals("(")&&remainingInput[pointer+2].token.equals(")")){
	    	 String str = remainingInput[pointer].token+""+remainingInput[pointer+1].token+remainingInput[pointer+2].token;
	    	 pointer = pointer+2;
	    	 remainingInput[pointer].token = str;
	     }
	     if(remainingInput[pointer].token.equals("-")|| remainingInput[pointer].token.equals("+")){
	    	 
	    	 if(pointer+1 < remainingInput.length-1 && pointer-1>=0 &&S.isSpecial(remainingInput[pointer-1].token) != -1){
	    		 if(remainingInput[pointer+1].code == 203 || remainingInput[pointer+1].code == 204){
	    		 sign = remainingInput[pointer].token;
	    		 pointer++;
	    		 }
	    	 }
	     }
		 if(stack.peek().trim().equals(remainingInput[pointer].token) || 
				 (stack.peek().trim().equals("var-name") && remainingInput[pointer].code == 202) ||
				 (stack.peek().trim().equals("const-name") && remainingInput[pointer].code == 202) ||
				 (stack.peek().trim().equals("file-name") && remainingInput[pointer].code == 205) ||
				 (stack.peek().trim().equals("int-number") && remainingInput[pointer].code == 203) ||
				 (stack.peek().trim().equals("float-number")&& remainingInput[pointer].code == 204)){
			 stack.pop();
			 pointer++;
			 if(!sign.equals(""))
				 sign = "";
			 		 
		 }
		else if(parser.isTerminal(stack.peek().trim()) == 1){
			 if(remainingInput[pointer].token.equals("$")&&!stack.peek().trim().equals("$")&&pointer==remainingInput.length-1){
				 errorMessage +="missing token at line: "+remainingInput[pointer].getLineNumber()+" expecting  '" +
				 		stack.peek().trim()+"'";
				 break;
			 }
			 else if(!remainingInput[pointer].token.equals("$")&&stack.peek().trim().equals("$")){
				 errorMessage +="syntax error at token '"+remainingInput[pointer].token+"' at line: "+
				 remainingInput[pointer].getLineNumber()+" delete extra token '"+remainingInput[pointer].token+"'";
				 break;
			 }
			 
			 errorMessage += "syntax error at token: '"+sign+remainingInput[pointer].token+"'  at line "+
			 remainingInput[pointer].getLineNumber()+" "+"expecting  "+stack.peek().trim();
			 sign = "";
			 break;
		 }
		 else{
			 
			 String currentInput = remainingInput[pointer].token;
			 if(!S.isSymbol(currentInput) && S.isReservedWord(currentInput) == -1){
				
			    if(S.isUserDefinedName(currentInput)){
			        for(int i=0 ; i<remainingInput.length ; i++){
			        	if(i>=2 && currentInput.equals(remainingInput[i].token)
			        	   &&remainingInput[i-2].token.equals("constants")){
			        		currentInput = "const-name";
			        	}
			        	else{
			        		currentInput = "var-name";
			        	}
			        }
			    }
			    
			    else if(remainingInput[pointer].code == 203){
			    	
			    	currentInput = "int-number";
			    }
			    
			    else if(remainingInput[pointer].code == 204){
			    	
			    	currentInput = "float-number";
			    }
			    else if(remainingInput[pointer].code == 404){
			    	
			    	isInvalid = true;
			    	
			    }
			    
			 }
			 
			    if(isInvalid){
			    	
			    	errorMessage += "syntax error at token: '"+sign+remainingInput[pointer].token+"'  at line: "
			    	+remainingInput[pointer].getLineNumber();
			    	String nonterminal = stack.peek().trim();
			    	sign = "";
			    	int row = parser.getNonTerminalIndex(nonterminal);
			    	 
			    	String tmp = "";
			    	tmp+=" expecting  ";
			    	for(int i=0 ; i<parser.parsingTable[row].length-1 ; i++){
			    		
			    		if(!parser.parsingTable[row][i].getRightSide().equals("Error")){
			    			tmp+=parser.terminals[i]+" or ";
			    		}
			    	}
			    	if(tmp.charAt(tmp.length()-2)=='r' && tmp.charAt(tmp.length()-3)=='o')
			    		for(int i=0 ; i<tmp.length()-3 ; i++)
			    			errorMessage+= tmp.charAt(i);
			    	else
			    		errorMessage += tmp;
			    	
			    	
			    	break;
			    	
			    }
			    else{
			    	
                    String nonterminal = stack.peek().trim();
			    	int row = parser.getNonTerminalIndex(nonterminal);
			    	int column = parser.getTerminalIndex(currentInput);
			    	String action = parser.parsingTable[row][column].getRightSide();
			    	if(!action.equals("Error"))
			    		pushStack(action);
			    	else{
			    		
			    		if(remainingInput[pointer].token.equals("$") && pointer == remainingInput.length-1){
			    			 
							 errorMessage +="missing token at line: "+sign+remainingInput[pointer].getLineNumber();
							 sign = "";
						 }else{
			    		errorMessage += "syntax error at token: '"+sign+remainingInput[pointer].token+"'  at line: "
				    	+remainingInput[pointer].getLineNumber();
			    		sign = "";
						 }
				    	String tmp = "";
				    	tmp+=" expecting  ";
				    	
				    	for(int i=0 ; i<parser.parsingTable[row].length-1 ; i++)
				    		if(!parser.parsingTable[row][i].getRightSide().equals("Error")){
				    			tmp+=parser.terminals[i]+" or ";
				    			
				    	}
				    	if(tmp.charAt(tmp.length()-2)=='r' && tmp.charAt(tmp.length()-3)=='o')
				    		for(int i=0 ; i<tmp.length()-3 ; i++)
				    			errorMessage+= tmp.charAt(i);
				    	else
				    		errorMessage += tmp;
				    	break;
			    	
			    }
			
			   
			 }
			 
			 
		
		 }		 
	 }
	 
	 
	 if(errorMessage.equals("")){
		 errorMessage = "parsing Successful, no syntax error";
		 System.out.println(errorMessage);
		 return errorMessage;
	 }
	print(errorMessage);
	 return errorMessage;
 }
 private void pushStack(String action){
	
	 String[] array = action.split("@");
	 if(action.equals("lambda")){
		 stack.pop();
	 }else{
         stack.pop();
         for (int i = array.length - 1; i >= 0; i--) {
              stack.push(array[i].trim());
     }
   }
 }
 void print(String errorMsg){
	 String[]arr = errorMsg.split("expecting ");
	 if(arr.length == 2){
	 String[]arr2 = arr[1].split("or");
	 System.out.println(arr[0]+" expecting  one of the following:");
	 for(int i=0 ; i<arr2.length ; i++){
		 System.out.println(arr2[i]);
	 }
 }
	 else{
		 System.out.println(arr[0]);
	 }
}
}
