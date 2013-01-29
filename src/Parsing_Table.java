
import java.util.*;

public class Parsing_Table {
	public String[]terminals = {"halt","#include",";","main()","{","}","constants","=",",","int","float","char","+=",
		            "-=","*=","/=","%=","(",")","+","-","*","/","%","cin",">>","cout","<<","if","endif",
		            "else","while","do","repeat","until","++","--","==","!=","<","<=",">",">=","const-name",
		            "var-name","float-number","int-number","file-name","$"};

public String[]nonTerminals = {"program","prog-decl","lib-decl","heading","block","declarations","const-decl","var-decl",
		                "name-list","more-names","data-type","stmt-list","statement","ass-stmt","ass-oper",
		                "exp","exp-prime","term","term-prime","factor","add-sign","mul-sign","value",
		                "inout-stmt","if-stmt","else-part","while-stmt","repeat-stmt","inc-stmt","inc-oper",
		                "bool-exp","name-value","relational-oper"};
public ProductionRules[] production = new ProductionRules[71];
public ProductionRules[][] parsingTable = new ProductionRules[33][49];
public FirstAndFollow[] firstFollowSets = new FirstAndFollow[33];
public Parsing_Table(){
	//build the production rules
	production[0] = new ProductionRules("program","prog-decl@$");
	production[1] = new ProductionRules("prog-decl","lib-decl@heading@declarations@block@halt");
	production[2] = new ProductionRules("lib-decl","#include@<@file-name@>@;@lib-decl");
	production[3] = new ProductionRules("lib-decl","lambda");
	production[4] = new ProductionRules("heading","main()");
	production[5] = new ProductionRules("block","{@stmt-list@}");
	production[6] = new ProductionRules("declarations","const-decl@var-decl");
	production[7] = new ProductionRules("const-decl","constants@data-type@const-name@=@value@;@const-decl");
	production[8] = new ProductionRules("const-decl","lambda");
	production[9] = new ProductionRules("var-decl","data-type@name-list@;@var-decl");
	production[10] = new ProductionRules("var-decl","lambda");
	production[11] = new ProductionRules("name-list","var-name@more-names");
	production[12] = new ProductionRules("more-names",",@name-list");
	production[13] = new ProductionRules("more-names","lambda");
	production[14] = new ProductionRules("data-type","int");
	production[15] = new ProductionRules("data-type","float");
	production[16] = new ProductionRules("data-type","char");
	production[17] = new ProductionRules("stmt-list","statement@;@stmt-list");
	production[18] = new ProductionRules("stmt-list","lambda");
	production[19] = new ProductionRules("statement","ass-stmt");
	production[20] = new ProductionRules("statement","inout-stmt");
	production[21] = new ProductionRules("statement","if-stmt");
	production[22] = new ProductionRules("statement","while-stmt");
	production[23] = new ProductionRules("statement","inc-stmt");
	production[24] = new ProductionRules("statement","repeat-stmt");
	production[25] = new ProductionRules("statement","block");
	production[26] = new ProductionRules("statement","lambda");
	production[27] = new ProductionRules("ass-stmt","var-name@ass-oper@exp");
	production[28] = new ProductionRules("ass-oper","=");
	production[29] = new ProductionRules("ass-oper","+=");
	production[30] = new ProductionRules("ass-oper","-=");
	production[31] = new ProductionRules("ass-oper","*=");
	production[32] = new ProductionRules("ass-oper","/=");
	production[33] = new ProductionRules("ass-oper","%=");
	production[34] = new ProductionRules("exp","term@exp-prime");
	production[35] = new ProductionRules("exp-prime","add-sign@term@exp-prime");
	production[36] = new ProductionRules("exp-prime","lambda");
	production[37] = new ProductionRules("term","factor@term-prime");
	production[38] = new ProductionRules("term-prime","mul-sign@factor@term-prime");
	production[39] = new ProductionRules("term-prime","lambda");
	production[40] = new ProductionRules("factor","(@exp@)");
	production[41] = new ProductionRules("factor","var-name");
	production[42] = new ProductionRules("factor","const-name");
	production[43] = new ProductionRules("factor","value");
	production[44] = new ProductionRules("add-sign","+");
	production[45] = new ProductionRules("add-sign","-");
	production[46] = new ProductionRules("mul-sign","*");
	production[47] = new ProductionRules("mul-sign","/");
	production[48] = new ProductionRules("mul-sign","%");
	production[49] = new ProductionRules("value","float-number");
	production[50] = new ProductionRules("value","int-number");
	production[51] = new ProductionRules("inout-stmt","cin@>>@name-list");
	production[52] = new ProductionRules("inout-stmt","cout@<<@name-list");
	production[53] = new ProductionRules("if-stmt","if@(@bool-exp@)@statement@else-part@endif");
	production[54] = new ProductionRules("else-part","else@statement");
	production[55] = new ProductionRules("else-part","lambda");
	production[56] = new ProductionRules("while-stmt","while@(@bool-exp@)@do@statement");
	production[57] = new ProductionRules("repeat-stmt","repeat@stmt-list@until@(@bool-exp@)");
	production[58] = new ProductionRules("inc-stmt","inc-oper@var-name");
	production[59] = new ProductionRules("inc-oper","++");
	production[60] = new ProductionRules("inc-oper","--");
	production[61] = new ProductionRules("bool-exp","name-value@relational-oper@name-value");
	production[62] = new ProductionRules("name-value","var-name");
	production[63] = new ProductionRules("name-value","const-name");
	production[64] = new ProductionRules("name-value","value");
	production[65] = new ProductionRules("relational-oper","==");
	production[66] = new ProductionRules("relational-oper","!=");
	production[67] = new ProductionRules("relational-oper","<");
	production[68] = new ProductionRules("relational-oper","<=");
	production[69] = new ProductionRules("relational-oper",">");
	production[70] = new ProductionRules("relational-oper",">=");
	
	//initialize first and follow for each nonterminal
	firstFollowSets[0] = new FirstAndFollow("program");
	firstFollowSets[1] = new FirstAndFollow("prog-decl");
	firstFollowSets[2] = new FirstAndFollow("lib-decl");
	firstFollowSets[3] = new FirstAndFollow("heading");
	firstFollowSets[4] = new FirstAndFollow("block");
	firstFollowSets[5] = new FirstAndFollow("declarations");
	firstFollowSets[6] = new FirstAndFollow("const-decl");
	firstFollowSets[7] = new FirstAndFollow("var-decl");
	firstFollowSets[8] = new FirstAndFollow("name-list");
	firstFollowSets[9] = new FirstAndFollow("more-names");
	firstFollowSets[10] = new FirstAndFollow("data-type");
	firstFollowSets[11] = new FirstAndFollow("stmt-list");
	firstFollowSets[12] = new FirstAndFollow("statement");
	firstFollowSets[13] = new FirstAndFollow("ass-stmt");
	firstFollowSets[14] = new FirstAndFollow("ass-oper");
	firstFollowSets[15] = new FirstAndFollow("exp");
	firstFollowSets[16] = new FirstAndFollow("exp-prime");
	firstFollowSets[17] = new FirstAndFollow("term");
	firstFollowSets[18] = new FirstAndFollow("term-prime");
	firstFollowSets[19] = new FirstAndFollow("factor");
	firstFollowSets[20] = new FirstAndFollow("add-sign");
	firstFollowSets[21] = new FirstAndFollow("mul-sign");
	firstFollowSets[22] = new FirstAndFollow("value");
	firstFollowSets[23] = new FirstAndFollow("inout-stmt");
	firstFollowSets[24] = new FirstAndFollow("if-stmt");
	firstFollowSets[25] = new FirstAndFollow("else-part");
	firstFollowSets[26] = new FirstAndFollow("while-stmt");
	firstFollowSets[27] = new FirstAndFollow("repeat-stmt");
	firstFollowSets[28] = new FirstAndFollow("inc-stmt");
	firstFollowSets[29] = new FirstAndFollow("inc-oper");
	firstFollowSets[30] = new FirstAndFollow("bool-exp");
	firstFollowSets[31] = new FirstAndFollow("name-value");
	firstFollowSets[32] = new FirstAndFollow("relational-oper");
	
	
	
	computeFirst();
	computeFollow();
	buildParsingTable(production);
}

public void buildParsingTable(ProductionRules[] production){
	//initialize first row in parsing table
	FirstAndFollow startSymbol = findNonterminal("prog-decl");
	String[]first0 = startSymbol.getFirst();
	boolean containsLambda = false;
	for(int k=0 ; k<first0.length ; k++){
		if(first0[k].equals("lambda"))
			containsLambda = true;
		else{
			int column = getTerminalIndex(first0[k]);
			int row = 0;
			parsingTable[row][column] = production[0];
		}
	}
	if(containsLambda){
		String[]follow = startSymbol.getFollow();
		for(int k=0 ; k<follow.length ; k++){
			int column = getTerminalIndex(follow[k]);
			int row = 0;
			parsingTable[row][column] = production[0];
		}
	}
	
	
	//fill the reset of parsing table
	for(int i=1 ; i<production.length ; i++){
		String leftSide = production[i].getLeftSide();
		String[]rightSide = production[i].getRightSide().split("@");
	  boolean containLambda = false;
	  for(int j=0 ; j<rightSide.length ; j++){
		  containLambda = false;
		if(isTerminal(rightSide[j]) == 1){
			int column = getTerminalIndex(rightSide[j]);
			int row = getNonTerminalIndex(leftSide);
			parsingTable[row][column] = production[i];
			break;
				
		}
		else if(rightSide[j].equals("lambda")){
			FirstAndFollow leftSymbol = findNonterminal(leftSide);
			String[]follow = leftSymbol.getFollow();
			for(int k=0 ; k<follow.length ; k++){
				int column = getTerminalIndex(follow[k]);
				int row = getNonTerminalIndex(leftSide);
				parsingTable[row][column] = production[i];
			}
		}
		else{  
		     FirstAndFollow nextSymbol = findNonterminal(rightSide[j]);
		     String[]firstSet = nextSymbol.getFirst();
		     for(int l=0 ; l<firstSet.length; l++){
			     if(firstSet[l].equals("lambda"))
			    	containLambda = true;
			     else{
			    	int column = getTerminalIndex(firstSet[l]);
					int row = getNonTerminalIndex(leftSide);
					parsingTable[row][column] = production[i];
			    }
			    		   
			   }
		    	 if(!containLambda)
			    	 break;
		    	 if(j == rightSide.length-1){
		    		 FirstAndFollow leftSymbol = findNonterminal(leftSide);
		    		 String[]follow = leftSymbol.getFollow();
		    		 for(int k=0 ; k<follow.length ; k++){
		    			 int column = getTerminalIndex(follow[k]);
		    			 int row = getNonTerminalIndex(leftSide);
		    			 parsingTable[row][column] = production[i];
		    		   }
		    			     
		    	   }
		    	   
		       
			/*FirstAndFollow rightSymbol = findNonterminal(firstSymbol);
			String[]first = rightSymbol.getFirst();
			boolean containLambda = false;
				
			for(int k=0 ; k<first.length ; k++){
				if(first[k].equals("lambda"))
					containLambda = true;
				else{
					int column = getTerminalIndex(first[k]);
					int row = getNonTerminalIndex(leftSide);
					parsingTable[row][column] = production[i];
				}
			}
			if(containLambda){
				FirstAndFollow leftSymbol = findNonterminal(leftSide);
				String[]follow = leftSymbol.getFollow();
				for(int k=0 ; k<follow.length ; k++){
					int column = getTerminalIndex(follow[k]);
					int row = getNonTerminalIndex(leftSide);
					parsingTable[row][column] = production[i];
				}
			}*/
		  
		}
   }
	
 }
	for(int i=0; i<parsingTable.length ; i++){
		for(int j=0 ; j<parsingTable[0].length ; j++){
			if(parsingTable[i][j] == null){
				ProductionRules p = new ProductionRules("","Error");
				parsingTable[i][j] = p;
			}
				
		}
	}
	
	
}

public int getTerminalIndex(String terminal) {
    for (int i = 0; i < terminals.length; i++) {
        if (terminal.equals(terminals[i])) {
            return i;
        }
    }
    return -1;
}
public int getNonTerminalIndex(String nonteminal) {
    for (int i = 0; i < nonTerminals.length; i++) {
        if (nonTerminals[i].equals(nonteminal)) {
            return i;
        }
    }
    return -1;
}

public void computeFollow(){
	boolean mustcontinue;
	
	do{
		mustcontinue = false;
		
		for(int i=0 ; i<production.length ; i++){
			
			String[]rightSide = production[i].getRightSide().split("@");
			
			for(int j=0 ; j<rightSide.length ; j++){
				String symbol = rightSide[j];
				
				if(isTerminal(symbol) == 1)continue;
				if(symbol.equals("lambda"))continue;
				
			    FirstAndFollow currentSymbol = findNonterminal(symbol);	
			    if(j+1 == rightSide.length){
			    	if(addLeftSideFollow(currentSymbol,production[i]) == 1)
			    		mustcontinue = true;
			    }
			    else if(isTerminal(rightSide[j+1]) == 1){
				   if(currentSymbol.addNewFollow(rightSide[j+1]) == 1)
					   mustcontinue = true;
			    }else{
			       boolean containLambda = false;
			       for(int k=j+1 ; k<rightSide.length ; k++){
			    	   containLambda = false;
			    	   if(isTerminal(rightSide[k]) == 1){
			    		   if(currentSymbol.addNewFollow(rightSide[k]) == 1)
			    			   mustcontinue = true;
			    		   break;
			    	   }
			    	   else{
			    		   
			    		   FirstAndFollow nextSymbol = findNonterminal(rightSide[k]);
			    		   String[]firstSet = nextSymbol.getFirst();
			    		   for(int l=0 ; l<firstSet.length; l++){
			    			   
				    		   if(firstSet[l].equals("lambda"))
				    			   containLambda = true;
				    		   else{
				    			   if(currentSymbol.addNewFollow(firstSet[l]) == 1)
				    				   mustcontinue = true;
				    		   }
				    		   
				    	   }
			    		   if(!containLambda)
				    		   break;
			    		   if(k == rightSide.length-1)
			    			   if(addLeftSideFollow(currentSymbol,production[i])==1)
								   mustcontinue = true;  
			    	   }
			    	   
			       }
				   /*FirstAndFollow nextSymbol = findNonterminal(rightSide[j+1]);
				   String[]firstSet = nextSymbol.getFirst();
				   for(int k=0 ; k<firstSet.length ; k++){
					   if(firstSet[k].equals("lambda"))continue;
					   
					   if(currentSymbol.addNewFollow(firstSet[k]) == 1){
						   mustcontinue = true;
					   }   
				   }
				   
				   for(int k=0 ; k<firstSet.length ; k++){
					   if(firstSet[k].equals("lambda")){
						   if(addLeftSideFollow(currentSymbol,production[i])==1)
							   mustcontinue = true;
						   break;
					   }
				   }*/
			    }
				   
				
			}
			
		}
	
	}while(mustcontinue);
	/*FirstAndFollow follow = findNonterminal("statement");
	String[]arr = follow.getFollow();
	for(int i=0 ; i<arr.length ; i++)
		System.out.println(arr[i]);*/
	
}

public void computeFirst(){
	boolean mustcontinue;
	
	do{
		mustcontinue = false;
		
		for(int i=0 ; i<production.length ; i++){
		    String[]rightSide = production[i].getRightSide().split("@");
		    String leftSide = production[i].getLeftSide();
		    FirstAndFollow leftSymbol = findNonterminal(leftSide);
		    if(rightSide[0].equals("lambda")){
		    	   if(leftSymbol.addNewFirst(rightSide[0])==1)
		    		   mustcontinue = true;
		    	   
		    }
		    else{
		    	
		    for(int j=0 ; j<rightSide.length ; j++){
		       
		       if(isTerminal(rightSide[j]) == 1){
		    	   if(leftSymbol.addNewFirst(rightSide[j]) == 1)
		    		   mustcontinue = true;
		    		   
		    	   break;
		       }
		       else{
		    	   boolean containLambda = false;
		    	   FirstAndFollow currentSymbol = findNonterminal(rightSide[j]);
		    	   String[]first = currentSymbol.getFirst();
		    	   for(int k=0 ; k<first.length; k++){
		    		   if(first[k].equals("lambda"))
		    			   containLambda = true;
		    		   else{
		    			   if(leftSymbol.addNewFirst(first[k]) == 1)
		    				   mustcontinue = true;
		    		   }
		    	   }
		    	   if(!containLambda)
		    		   break;
		    	   if(j == rightSide.length-1)
		    		   if(leftSymbol.addNewFirst("lambda") == 1)
		    			   mustcontinue = true;
		       }
		    }
		}
		}	
	}while(mustcontinue);
	/*FirstAndFollow first = findNonterminal("prog-decl");
	String[]arr = first.getFirst();
	for(int i=0 ; i<arr.length ; i++)
		System.out.println(arr[i]);*/
}

public int isTerminal(String symbol){
	for(int i=0 ; i<terminals.length ; i++){
		if(symbol.equals(terminals[i]))
			return 1;
	}
	return 0;
}
public FirstAndFollow findNonterminal(String symbol){
	FirstAndFollow tmp = null;
	for(int k=0 ; k<firstFollowSets.length ; k++){
	    if(symbol.equals(firstFollowSets[k].getNonTerminal())){
	    	tmp = firstFollowSets[k];
	    	break;
	    }
   }
	return tmp;
}
public int addLeftSideFollow(FirstAndFollow currentSymbol, ProductionRules currentProduction){
	   int mustcontinue = 0;
	   String leftSide = currentProduction.getLeftSide();
	   FirstAndFollow leftSymbol = findNonterminal(leftSide);
	   String[]follow = leftSymbol.getFollow();
	   if(currentSymbol.addAllFollow(follow)== 1)
		   mustcontinue = 1;
	   return mustcontinue;
}


}
