package com.firmboy.search.sqlParser;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Console;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLOver;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.*;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectJoin;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectSubqueryTableSource;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectTableReference;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author playboy
 * @create 2020-02-21 2:28 下午
 * @description
 * @serviceLogic
 **/
public class DruidSqlParseTest {
    private Logger log = Logger.getLogger(DruidSqlParseTest.class);


    public static void main(String[] args) {
        TimeInterval timer = DateUtil.timer();

        //String sql = "SELECT COUNT(DISTINCT b.BILL_ID) FROM ( SELECT * FROM ( SELECT SSSFM_IO_BILL.* , decode( (SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1) ,NULL ,SSSFM_IO_BILL.INS_TYPE_CODE,(SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1)) INS_TYPE FROM SSSFM_IO_BILL) WHERE IS_VALID = ? AND INS_TYPE_CODE like ?||'%' AND INPUT_CO_CODE = ? AND to_char(BUSI_DATE,'yyyy-MM-dd') BETWEEN ? AND ? ) b RIGHT JOIN ( SELECT BILL_ID FROM ( SELECT b.BILL_ID, t1.COMPO_ID, t1.TAB_ID, t1.USER_ID, b.INPUT_CO_CODE FROM (select * from (select t.*, row_number() over(partition by BILL_ID, COMPO_ID, TAB_ID, USER_ID order by CDATE desc ) rn from ( SELECT BILL_ID, COMPO_ID, TAB_ID, decode(USER_ID,NULL ,?,'',?,USER_ID) USER_ID, CDATE, EVENT_ID, TAB_VAL FROM SSSFM_EVENTOPT_HISTORY_VIEW WHERE COMPO_ID = ? and TAB_ID = ? ) t ) where rn=1 AND TAB_VAL = '1') t1 LEFT JOIN SSSFM_IO_BILL b ON t1.BILL_ID = b.BILL_ID WHERE b.BILL_ID IS NOT NULL ) vin WHERE vin.COMPO_ID = ? AND vin.TAB_ID = ? AND vin.USER_ID = ? AND vin.INPUT_CO_CODE = ? ) v ON b.BILL_ID = v.BILL_ID WHERE b.BILL_ID IS NOT NULL";

        //String sql = "select * from ( select t.*,rownum RN from ( SELECT bl.*,ROWNUM FROM ( SELECT DISTINCT b.BILL_ID, b.BILL_NO, b.SRC_SYS, b.SRC_BILL_ID, b.BILL_TYPE, b.IS_ADJUST, b.IS_VALID, b.FISCAL, b.FIS_PERD, to_char(b.BUSI_DATE, 'yyyy-mm-dd') BUSI_DATE, b.INS_TYPE_CODE, b.PAY_CO_CODE, b.PAY_BANK_CODE, b.PAY_BANK_NAME, b.PAY_BANK_ACC_NO, b.PAY_BANK_ACC_NAME, b.IN_CO_CODE, b.IN_BANK_CODE, b.IN_BANK_NAME, b.IN_BANK_ACC_NO, b.IN_BANK_ACC_NAME, b.AMOUNT, b.PROCESS_INST_ID, b.BILL_STATUS, b.GK_PAY_BILL_ID, b.APP_USER_ID, b.APP_USER_NAME, to_char(b.APP_DATE, 'yyyy-mm-dd hh24:mm:ss') APP_DATE, b.VOU_GUID, b.ACC_USER_ID, b.ACC_USER_NAME, to_char(b.ACC_DATE, 'yyyy-mm-dd hh24:mm:ss') ACC_DATE, b.INPUT_CO_CODE, b.INPUT_USER_ID, b.INPUT_USER_NAME, to_char(b.INPUT_DATE, 'yyyy-mm-dd') INPUT_DATE, b.REMARK, b.INPUT_ORG_CODE, b.IS_PRINT, b.COMP_BATCH, b.COMP_ID, b.LEVY_START, b.LEVY_END, b.INS_TYPE_NAME, b.PAY_CO_NAME, b.IN_CO_NAME, b.PAY_BANKNODE_CODE, b.PAY_BANKNODE_NAME, b.IN_BANKNODE_CODE, b.IN_BANKNODE_NAME, b.SESSION_ID, b.GK_BILL_NO, b.DIST_CODE, b.TC_CODE, b.GATHER_BILL_ID, '['||b.DIST_CODE||']'||b.DIST_NAME DIST_NAME, b.TC_NAME, b.MTD_BATCH_NO, b.INPUT_CO_NAME, b.APPLY_CO_CODE, b.APPLY_CO_NAME, b.APPLY_DIST_CODE, b.OLPAY_DATE PAY_START_DATE, b.VERSIONT, b.REGIST_TIME, '['||b.APPLY_DIST_CODE||']'||b.APPLY_DIST_NAME APPLY_DIST_NAME, '['||b.IN_DIST_CODE||']'||b.IN_DIST_NAME IN_DIST_NAME, '['||b.PAY_DIST_CODE||']'||b.PAY_DIST_NAME PAY_DIST_NAME, b.PAY_DIST_CODE, b.IN_DIST_CODE, decode(con.con,1,item.IO_ITEM_CODE,'') IO_ITEM_CODE, decode(con.con,1,'['||item.IO_ITEM_CODE||']'||item.IO_ITEM_NAME,'...') IO_ITEM_NAME, s.SR_BGITEM_CODE, decode(s.SR_BGITEM_NAME,null,'','['||s.SR_BGITEM_CODE||']'||s.SR_BGITEM_NAME) SR_BGITEM_NAME, s.ZC_BGITEM_CODE, decode(s.ZC_BGITEM_NAME,null,'','['||s.ZC_BGITEM_CODE||']'||s.ZC_BGITEM_NAME) ZC_BGITEM_NAME, g.TITLE, sr.BILL_NO SRC_BILL_NO, vou.VOU_NO, vou.ACCT_NAME FROM ( SELECT * FROM ( SELECT SSSFM_IO_BILL.* , decode( (SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1) ,NULL ,SSSFM_IO_BILL.INS_TYPE_CODE,(SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1)) INS_TYPE FROM SSSFM_IO_BILL) WHERE IS_VALID = ? AND INS_TYPE_CODE like ?||'%' AND INPUT_CO_CODE = ? AND to_char(BUSI_DATE,'yyyy-MM-dd') BETWEEN ? AND ? ) b LEFT JOIN SSSFM_IO_BILL_ITEM item on b.BILL_ID = item.BILL_ID LEFT JOIN ( SELECT bcount.BILL_ID, count(bcount.BILL_ID) con FROM SSSFM_IO_BILL bcount LEFT JOIN SSSFM_IO_BILL_ITEM icount ON bcount.BILL_ID = icount.BILL_ID GROUP BY bcount.BILL_ID ) con ON con.BILL_ID = b.BILL_ID RIGHT JOIN ( SELECT BILL_ID FROM ( SELECT b.BILL_ID, t1.COMPO_ID, t1.TAB_ID, t1.USER_ID, b.INPUT_CO_CODE FROM (select * from (select t.*, row_number() over(partition by BILL_ID, COMPO_ID, TAB_ID, USER_ID order by CDATE desc ) rn from ( SELECT BILL_ID, COMPO_ID, TAB_ID, decode(USER_ID,NULL ,?,'',?,USER_ID) USER_ID, CDATE, EVENT_ID, TAB_VAL FROM SSSFM_EVENTOPT_HISTORY_VIEW WHERE COMPO_ID = ? and TAB_ID = ? ) t ) where rn=1 AND TAB_VAL = '1') t1 LEFT JOIN SSSFM_IO_BILL b ON t1.BILL_ID = b.BILL_ID WHERE b.BILL_ID IS NOT NULL ) vin WHERE vin.COMPO_ID = ? AND vin.TAB_ID = ? AND vin.USER_ID = ? AND vin.INPUT_CO_CODE = ? ) v ON b.BILL_ID = v.BILL_ID LEFT JOIN SSSFM_SR_ZC_REL s ON decode(con.con, 1, item.IO_ITEM_CODE, '')=s.IO_ITEM_CODE AND b.FISCAL = s.FISCAL LEFT JOIN SSSFM_IO_BILL_GATHER g ON b.GATHER_BILL_ID = g.GATHER_BILL_ID LEFT JOIN SSSFM_IO_BILL sr ON b.SRC_BILL_ID = sr.BILL_ID LEFT JOIN ( SELECT gl.VOU_GUID, gl.VOU_TYPE_CODE || '-' || gl.VOU_NO VOU_NO, '[' || gl.ACCT_CODE || ']' || acct.CHR_NAME ACCT_NAME FROM GL_VOU_HEAD gl LEFT JOIN MA_ELE_ACCT acct ON gl.ACCT_CODE = acct.Chr_CODE AND gl.SET_YEAR = acct.SET_YEAR AND gl.RG_CODE = acct.RG_CODE AND gl.AGENCY_CODE = acct.AGENCY_CODE ) vou ON b.VOU_GUID = vou.VOU_GUID WHERE b.BILL_ID IS NOT NULL ) bl ORDER BY bl.BUSI_DATE DESC, bl.BILL_NO DESC ) t ) where rn > (1-1)*50 and rn <= 1*50";

        String sql = "select COMPO_ID,ELEMENT_ID,ELEMENT_NAME,ELEMENT_TYPE,PAGE_TYPE,TAB_ID,IS_READONLY,IS_VISIBLE,IS_INCLUDE,FIELD_ID,DEF_VALUE,to_char(CONTENTS) CONTENTS,SERVICE,FUNCTION,USER_ID,ORD_INDEX,REMARK from SSSFM_PAGE_ELEMENT where COMPO_ID='BZ_JJSR_FILL' and PAGE_TYPE='list' and TAB_ID='{\"tab\":\"*\",\"status\":\"*\"}' order by ORD_INDEX";

        String oracle = JdbcConstants.ORACLE;
        List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, oracle);

        sqlStatements.forEach(sqlStatement -> {
            SQLSelectStatement selectStatement = (SQLSelectStatement)sqlStatement;
            SQLSelect select = selectStatement.getSelect();
            dealSelect(select);
        });

        String s = SQLUtils.toSQLString(sqlStatements, oracle);

        Console.log(s);
        long interval = timer.interval();
        System.out.println("耗时"+interval);
    }

    public static void dealSelect(SQLSelect select){
        SQLSelectQuery query = select.getQuery();
        if(query instanceof SQLSelectQueryBlock){
            SQLSelectQueryBlock queryBlock = (SQLSelectQueryBlock)query;
            List<SQLSelectItem> selectList = queryBlock.getSelectList();
            selectList.forEach(sqlSelectItem -> {
                String alias = sqlSelectItem.getAlias();
                if(alias != null){
                    sqlSelectItem.setAlias(dealName(alias));
                }
                SQLExpr expr = sqlSelectItem.getExpr();
                dealExpr(expr);
            });
            SQLTableSource from = queryBlock.getFrom();
            dealTableSource(from);
            SQLExpr where = queryBlock.getWhere();
            dealExpr(where);
        }else if(query instanceof SQLUnionQuery){
            SQLUnionQuery sqlUnionQuery = (SQLUnionQuery)query;
            SQLSelectQuery left = sqlUnionQuery.getLeft();
            SQLSelectQuery right = sqlUnionQuery.getRight();
        }
    }

    /**
     * OracleSelectJoin  联查
     * OracleSelectSubqueryTableSource   字表
     * OracleSelectTableReference
     * @param tableSource
     */
    public static void dealTableSource(SQLTableSource tableSource){
        if(tableSource instanceof OracleSelectJoin){
            OracleSelectJoin oracleSelectJoin = (OracleSelectJoin)tableSource;
            SQLTableSource left = oracleSelectJoin.getLeft();
            dealTableSource(left);
            SQLTableSource right = oracleSelectJoin.getRight();
            dealTableSource(right);
            SQLExpr condition = oracleSelectJoin.getCondition(); //TODO
            dealExpr(condition);
        }else if(tableSource instanceof OracleSelectSubqueryTableSource){
            OracleSelectSubqueryTableSource oracleSelectSubqueryTableSource = (OracleSelectSubqueryTableSource)tableSource;
            SQLSelect select = oracleSelectSubqueryTableSource.getSelect();
            dealSelect(select);
        }else if(tableSource instanceof OracleSelectTableReference){
            OracleSelectTableReference oracleSelectTableReference = (OracleSelectTableReference)tableSource;
        }
    }

    public static void dealExpr(SQLExpr expr){
        if(expr instanceof SQLIdentifierExpr){//BILL_ID
            SQLIdentifierExpr sqlIdentifierExpr = (SQLIdentifierExpr)expr;
            String name = sqlIdentifierExpr.getName();
            sqlIdentifierExpr.setName(dealName(name));
        }else if(expr instanceof SQLAggregateExpr){//COUNT(DISTINCT b.BILL_ID)
            SQLAggregateExpr sqlAggregateExpr = (SQLAggregateExpr)expr;
            if(sqlAggregateExpr.getMethodName().equalsIgnoreCase("ROW_NUMBER")){
                SQLOver over = sqlAggregateExpr.getOver();
                List<SQLExpr> partitionBys = over.getPartitionBy();
                partitionBys.forEach(partitionBy->{
                    dealExpr(partitionBy);
                });
            }else{
                List<SQLExpr> arguments = sqlAggregateExpr.getArguments();
                arguments.forEach(argument->{
                    dealExpr(argument);
                });
            }
        }else if(expr instanceof SQLPropertyExpr){
            SQLPropertyExpr sqlPropertyExpr = (SQLPropertyExpr)expr;
            String name = sqlPropertyExpr.getName();
            if(!name.equals("*")){
                sqlPropertyExpr.setName(dealName(name));
            }
        }else if(expr instanceof SQLMethodInvokeExpr){
            SQLMethodInvokeExpr sqlMethodInvokeExpr = (SQLMethodInvokeExpr)expr;
            List<SQLExpr> parameters = sqlMethodInvokeExpr.getParameters();
            parameters.forEach(parameter->{
                dealExpr(parameter);
            });
        }else if(expr instanceof SQLQueryExpr){
            SQLQueryExpr sqlQueryExpr = (SQLQueryExpr)expr;
            SQLSelect subQuery = sqlQueryExpr.getSubQuery();
            dealSelect(subQuery);
        }else if(expr instanceof SQLBinaryOpExpr){
            SQLBinaryOpExpr sqlBinaryOpExpr = (SQLBinaryOpExpr)expr;
            SQLExpr left = sqlBinaryOpExpr.getLeft();
            dealExpr(left);
            SQLExpr right = sqlBinaryOpExpr.getRight();
            dealExpr(right);
        }else if(expr instanceof SQLBetweenExpr){
            SQLBetweenExpr sqlBetweenExpr = (SQLBetweenExpr)expr;
            SQLExpr testExpr = sqlBetweenExpr.getTestExpr();
            dealExpr(testExpr);
        }
    }

    public static String dealName(String name){
        return "\""+name+"\"";
    }

}
