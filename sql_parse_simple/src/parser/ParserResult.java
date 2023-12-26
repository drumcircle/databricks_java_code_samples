package parser;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ParserResult {
    private boolean success=false;
    private String errMsg = "";
    private Set<ColumnToken> tokens=new HashSet<>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Set<ColumnToken> getTokens() {
        return tokens;
    }

    public void setTokens(Set<ColumnToken> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String toString(){
        StringBuffer sb=new StringBuffer();
        Iterator<ColumnToken> tokenIterator=tokens.iterator();
        while (tokenIterator.hasNext()){
            ColumnToken columnExpression=tokenIterator.next();
            sb.append(columnExpression.toString()+System.lineSeparator());
        }
        return sb.toString();
    }
}
