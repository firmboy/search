package com.firmboy.search.stack;

import cn.hutool.core.lang.Console;
import org.apache.log4j.Logger;

import java.util.Stack;

/**
 * @author playboy
 * @create 2020-02-21 9:38 上午
 * @description 栈测试
 * @serviceLogic
 **/
public class StackTest {
    private Logger log = Logger.getLogger(StackTest.class);

    public static void main(String[] args) {
        String sql = "SELECT COUNT(DISTINCT b.BILL_ID) FROM ( SELECT * FROM ( SELECT SSSFM_IO_BILL.* , decode( (SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1) ,NULL ,SSSFM_IO_BILL.INS_TYPE_CODE,(SELECT m.INS_TYPE_CODE FROM SSSFM_IO_BILL_ITEM m WHERE m.BILL_ID = SSSFM_IO_BILL.BILL_ID AND ROWNUM <= 1)) INS_TYPE FROM SSSFM_IO_BILL) WHERE IS_VALID = ? AND INS_TYPE_CODE like ?||'%' AND INPUT_CO_CODE = ? AND to_char(BUSI_DATE,'yyyy-MM-dd') BETWEEN ? AND ? ) b RIGHT JOIN ( SELECT BILL_ID FROM ( SELECT b.BILL_ID, t1.COMPO_ID, t1.TAB_ID, t1.USER_ID, b.INPUT_CO_CODE FROM (select * from (select t.*, row_number() over(partition by BILL_ID, COMPO_ID, TAB_ID, USER_ID order by CDATE desc ) rn from ( SELECT BILL_ID, COMPO_ID, TAB_ID, decode(USER_ID,NULL ,?,'',?,USER_ID) USER_ID, CDATE, EVENT_ID, TAB_VAL FROM SSSFM_EVENTOPT_HISTORY_VIEW WHERE COMPO_ID = ? and TAB_ID = ? ) t ) where rn=1 AND TAB_VAL = '1') t1 LEFT JOIN SSSFM_IO_BILL b ON t1.BILL_ID = b.BILL_ID WHERE b.BILL_ID IS NOT NULL ) vin WHERE vin.COMPO_ID = ? AND vin.TAB_ID = ? AND vin.USER_ID = ? AND vin.INPUT_CO_CODE = ? ) v ON b.BILL_ID = v.BILL_ID WHERE b.BILL_ID IS NOT NULL";


        final String start = "SELECT";
        final String end = "FROM";

        Stack<String> stack = new Stack<>();

        String[] s = sql.split(" ");
        for(String str :s){
            stack.push(str);
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.empty()){
            String pop = stack.pop();
            sb.insert(0,pop+" ");
        }
        
        Console.log(sb.toString());


    }

}
