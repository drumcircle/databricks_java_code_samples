package parser;

import gudusoft.gsqlparser.EDbVendor;
import gudusoft.gsqlparser.TCustomSqlStatement;
import gudusoft.gsqlparser.TGSqlParser;
import gudusoft.gsqlparser.nodes.TObjectName;
import gudusoft.gsqlparser.nodes.TTable;

import java.util.HashSet;
import java.util.Set;

public class SqlStatementParser {

        public ParserResult parseTokens(String sql)
        {
            ParserResult result=new ParserResult();
            TGSqlParser sqlparser = new TGSqlParser(EDbVendor.dbvmssql);
            sqlparser.sqltext  = sql;

            int ret = sqlparser.parse();
            if (ret == 0){
                Set<ColumnToken> columnExpressions=new HashSet<>();
                TCustomSqlStatement stmt= sqlparser.sqlstatements.get(0);
                iterateStmt( stmt, columnExpressions);
                result.setTokens(columnExpressions);
                result.setSuccess(true);
            }else{
                result.setSuccess(false);
                result.setErrMsg(sqlparser.getErrormessage());
            }
            return result;

        }

        protected static void iterateStmt(TCustomSqlStatement stmt,Set<ColumnToken> columnExpressions){

            for(int t=0;t<stmt.tables.size();t++){
                TTable table = stmt.tables.getTable(t);
                String schema_name=table.getPrefixSchema();
                String table_name = table.getName();
                boolean isBaseTable= table.isBaseTable();
                if (isBaseTable) {
                    ColumnToken baseTableExpression = ColumnToken.withTable(schema_name, table_name);
                    columnExpressions.add(baseTableExpression);
                }
                for (int c=0; c < table.getLinkedColumns().size(); c++) {
                    TObjectName objectName = table.getLinkedColumns().getObjectName(c);
                    String column_name = objectName.getColumnNameOnly().toLowerCase();
                    ColumnToken baseTableExpression;
                    if (!objectName.isTableDetermined()) {
                        column_name = "?."+ objectName.getColumnNameOnly().toLowerCase();
                        baseTableExpression= ColumnToken.withColumn(schema_name, table_name,column_name);
                    } else {
                        baseTableExpression= ColumnToken.withColumn(schema_name, table_name,column_name);
                    }
                    if (isBaseTable) {
                        columnExpressions.add(baseTableExpression);
                    }
                }
            }

            for (int i=0;i<stmt.getStatements().size();i++){
                iterateStmt(stmt.getStatements().get(i),columnExpressions);
            }

        }


}
