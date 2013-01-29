import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

public class Scanneree {
 
  private String[] symbols = {";", "=", "{", "}", "<", ">", "+", "/", "*", "-", "%", ",", "(", ")",
		                      "<<",">>","==","!=","<=",">=","+=","-=","/=","*=","%=","++","--","!"};
  
  private Tokens[] specialSympols = {
	        new Tokens(";", 101),
	        new Tokens("=", 102),
	        new Tokens("{", 103),
	        new Tokens("}", 104),
	        new Tokens("(", 105),
	        new Tokens(")", 106),
	        new Tokens("+", 107),
	        new Tokens("-", 108),
	        new Tokens("*", 109),
	        new Tokens("/", 110),
	        new Tokens("%", 111),
	        new Tokens("<", 112),
	        new Tokens(">", 113),
	        new Tokens("<<", 114),
	        new Tokens(">>", 115),
	        new Tokens("==", 116),
	        new Tokens("!=", 117),
	        new Tokens("<=", 118),
	        new Tokens(">=", 119),
	        new Tokens(",", 120),
	        new Tokens("+=", 121),
	        new Tokens("-=", 122),
	        new Tokens("/=", 123),
	        new Tokens("*=", 124),
	        new Tokens("%=", 125),
	        new Tokens("++", 126),
	        new Tokens("--", 127)};
  
  ArrayList<Tokens> listOfTokens;
  private Tokens[] reservedWords = {
	        new Tokens("halt", 1),
	        new Tokens("#include", 2),
	        new Tokens("main()", 3),
	        new Tokens("constants", 4),
	        new Tokens("int", 5),
	        new Tokens("float", 6),
	        new Tokens("char", 7),
	        new Tokens("cin", 8),
	        new Tokens("cout", 9),
	        new Tokens("if", 10),
	        new Tokens("endif", 11),
	        new Tokens("else", 12),
	        new Tokens("while", 13),
	        new Tokens("do", 14),
	        new Tokens("repeat", 15),
	        new Tokens("until", 16)};
  
  
  String sourceCode = "";
  public Scanneree(){
	  listOfTokens = new ArrayList<Tokens>();
  }
  public void setSource(String sourceCode){
	  this.sourceCode = sourceCode ;
  }
  public ArrayList<Tokens> getTokens(){
	  int tokenLine = 1;
	  StringTokenizer tokenizer = new StringTokenizer(sourceCode,"\n");
	  while(tokenizer.hasMoreTokens()){
		  String currentLine = tokenizer.nextToken().trim();
		  if(!currentLine.equals("")){
			  ArrayList<String> list = this.splitIntoTokens(currentLine);
			  for(int i=0 ; i<list.size(); i++){
				  this.selectTokenType(list.get(i),tokenLine);
			  }
		  }
		  tokenLine++;
	  }
	  return listOfTokens;
  }
  
  private ArrayList<String> splitIntoTokens(String line){
	  ArrayList<String> tokens = new ArrayList<String>();
	  StringTokenizer tokenizer = new StringTokenizer(line);
	  while(tokenizer.hasMoreTokens()){
		  String nextToken = tokenizer.nextToken();
		  char[]arrOfChars = nextToken.toCharArray();
		  String beforString = "";
		  String numberSign = "";
		  int flag = 0;
		  for(int i=0 ; i<arrOfChars.length ; i++){
			 if(isSymbol(Character.toString(arrOfChars[i])))
			 {
				 if(!beforString.equals(""))
				 {
					 /*if(i<arrOfChars.length-1 && arrOfChars[i]=='(' && arrOfChars[i+1]==')' && beforString.equals("main")){
						 tokens.add(beforString+arrOfChars[i]+arrOfChars[i+1]);
						 beforString = "";
						 i++;
						 flag = 1;
					 }
					 else{*/
					     tokens.add(beforString);
					     beforString = "";
					 //}	 
				 }
				 if(i<arrOfChars.length-1 &&isSymbol(Character.toString(arrOfChars[i])+Character.toString(arrOfChars[i+1])))
				 {
					 tokens.add(Character.toString(arrOfChars[i])+Character.toString(arrOfChars[i+1]));
					 i++;
				 }
				 else if(flag == 0){
					 tokens.add(arrOfChars[i]+"");
				 }
				 
			 }
			 else{
				 beforString += Character.toString(arrOfChars[i]);
			 }
		  }
		  if(!beforString.equals("")){
			  tokens.add(beforString);
			  beforString = "";
		  }  
	  }
	  
	  return tokens;
  }
  
  private void selectTokenType(String token,int line){
	  int i;
	  if((i=isSpecial(token)) != -1){
		  Tokens tmp = new Tokens(token,specialSympols[i].code);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
	  }
	  else if((i=isReservedWord(token))!= -1){
		 Tokens tmp = new Tokens(token,reservedWords[i].code);
		 tmp.setLineNumber(line);
		 listOfTokens.add(tmp);
	  }
	  else if(isUserDefinedName(token)){
		  Tokens tmp = new Tokens(token,202);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
	  }
	  else if(isInteger(token)){
		  Tokens tmp = new Tokens(token,203);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
	  }
	  else if(isFloat(token)){
		  Tokens tmp = new Tokens(token,204);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
		  
	  }
	  else if(isFileName(token)){
		  Tokens tmp = new Tokens(token,205);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
		  
	  }else{
		  Tokens tmp = new Tokens(token,404);
		  tmp.setLineNumber(line);
		  listOfTokens.add(tmp);
	  }
	  
	  
  }
  
  private boolean isInteger(String token){
	  
	  for(int i=0 ; i<token.length() ; i++){
		  if(!Character.isDigit(token.charAt(i)))
			  return false;
	  }
	//  JOptionPane.showMessageDialog(null, token);
	  return true;
  }
  
  private boolean isFloat(String token){ 
	  for (int i = 1; i < token.length(); i++) {
          if(!Character.isDigit(token.charAt(i)) && token.charAt(i)!='.')
        	  return false; 
	  }
	  if(!Character.isDigit(token.charAt(0)) || !Character.isDigit(token.charAt(token.length()-1)))
		  return false;
	  
	  int numberOfFloatingPoint = 0;
      for (int i = 0; i < token.length(); i++) {
          if (token.charAt(i) == '.') {
              numberOfFloatingPoint++;
          }
      }
      if (numberOfFloatingPoint> 1) {
          return false;
      }
    //  JOptionPane.showMessageDialog(null, token);
	  return true;
  }
  private boolean isFileName(String token){
	  if(token.length() < 3)
		  return false;
	  StringTokenizer t = new StringTokenizer(token,".");
	  if(t.countTokens() != 2)
		  return false;
	  else{
		  String befordot = t.nextToken();
		  String afterdot = t.nextToken();
		  for(int i=0 ; i<befordot.length() ; i++)
			  if(!Character.isLetterOrDigit(befordot.charAt(i)) && befordot.charAt(i) != '_')
				  return false;
		  
		  for(int i=0 ; i<afterdot.length() ; i++)
			  if(!Character.isLetter(afterdot.charAt(i)))
				  return false;  
	  }
	  return true;
	  
  }
  boolean isUserDefinedName(String token){
	  if(Character.isDigit(token.charAt(0)))
		  return false;
	  for(int i=0 ; i<token.length() ; i++){
		 if(!Character.isLetterOrDigit(token.charAt(i))&&token.charAt(i)!='_'){
			 return false;
		 }
	  }
	  
	  return true;
  }
  public int isReservedWord(String token){
	  for(int i=0 ; i<reservedWords.length ; i++){
		  if(token.equals(reservedWords[i].token))
			  return i;
	  }
	  return -1;
  }
  public boolean isSymbol(String token){
	  for(int i=0 ; i<symbols.length ; i++){
		  if(token.equals(symbols[i]))
				  return true;
	  }
	  return false;
  }
  public int isSpecial(String token){
	  for(int i=0 ; i<symbols.length ; i++){
		  if(token.equals(symbols[i]))
			  return i;
	  }
	  return -1;
  }
  
  
  
}
