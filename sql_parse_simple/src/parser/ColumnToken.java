package parser;

import java.util.Objects;

public class ColumnToken {
    public String schema;
    public String table;
    public String column;
    public String expr;

    public static ColumnToken withTable(String schema, String table) {
        ColumnToken ce=new ColumnToken();
        ce.schema = schema;
        ce.table = table;
        return ce;
    }

    public static ColumnToken withColumn(String schema, String table, String column) {
        ColumnToken ce=new ColumnToken();
        ce.schema = schema;
        ce.table = table;
        ce.column = column;
        return ce;
    }

    public static ColumnToken withExpr(String schema, String table, String column, String expr) {
        ColumnToken ce=new ColumnToken();
        ce.schema = schema;
        ce.table = table;
        ce.column = column;
        ce.expr = expr;
        return ce;
    }

    @Override
    public String toString(){
        if (this.column!=null) {
            return getEscapedString(this.schema)+ "." + getEscapedString(this.table) + "." + getEscapedString(this.column) ;
        } else {
            return getEscapedString(this.schema)+ "." + getEscapedString(this.table);
        }
    }

    private String getEscapedString(String token){
        if (token.indexOf(" ")>-1){
            return "`" + token+ "`.`";
        } else {
            return token;
        }
    }

    // Constructor and other methods...

    @Override
    public int hashCode() {
        return Objects.hash(schema, table, column, expr);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColumnToken other = (ColumnToken) obj;
        return Objects.equals(schema, other.schema) &&
                Objects.equals(table, other.table) &&
                Objects.equals(column, other.column) &&
                Objects.equals(expr, other.expr);
    }

}
