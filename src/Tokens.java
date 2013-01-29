
public class Tokens {
String token = "";
int code;
int lineNumber;

Tokens(String token, int code) {
    this.token = token;
    this.code = code;
    
}

public void setLineNumber(int line){
	this.lineNumber = line;
}

public int getLineNumber(){
	return this.lineNumber;
}
}
