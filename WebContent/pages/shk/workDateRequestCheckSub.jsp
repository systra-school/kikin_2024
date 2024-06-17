<!-- workDateRequestCheckSub.jsp -->
<%@page import="constant.CommonConstant.DayOfWeek"%>
<%@page import="business.logic.utils.CheckUtils"%>
<%@page import="form.common.DateBean"%>
<%@page import="java.util.List"%>
<%@page import="form.shk.WorkDateRequestCheckForm"%>
<%
/**
 * ファイル名：workDateRequestCheckSub.jsp
 *
 * 変更履歴
 * 1.0  2010/09/13 Kazuya.Naraki
 * 2.0  2024/04/25 Atsuko.Yoshioka
 */
%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="constant.RequestSessionNameConstant"%>
<%@ page import="constant.CommonConstant"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<bean:size id="dateBeanListSize" name="workDateRequestCheckForm"  property="dateBeanList"/>
<bean:define id="offset" name="workDateRequestCheckForm" property="offset" />
<bean:define id="color" value="" type="java.lang.String"/>
<bean:define id="showLength" value="18" type="java.lang.String"/>
<bean:define id="symbol" value="" type="java.lang.String"/>

<html>
  <head>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="Thu, 01 Dec 1994 16:00:00 GMT">
    <script type="text/javascript" src="/kikin/pages/js/common.js"></script>
    <script type="text/javascript" src="/kikin/pages/js/checkCommon.js"></script>
    <script type="text/javascript" src="/kikin/pages/js/message.js"></script>
    <script type="text/javascript" language="Javascript1.1">
    /**
     * 検索
     */
    function submitSearch() {
        doSubmit('/kikin/workDateRequestCheckSubSearch.do');
    }
    </script>
    <title>出勤希望確認画面</title>

    <link href="/kikin/pages/css/common.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <table>
          <tr>
            <td id="headLeft">
              　
            </td>
            <td id="headCenter">
              出勤希望確認
            </td>
            <td id="headRight">
              　
            </td>
          </tr>
        </table>
      </div>
      <div id="businessBody" style="overflow: auto; height : auto;">
        <div id = "resize">
          <html:form action="/workDateRequestCheckInit" >
            表示年月：
            <html:select name="workDateRequestCheckForm" property="yearMonth" onchange="submitSearch()">
            <html:optionsCollection name="workDateRequestCheckForm"
                                    property="yearMonthCmbMap"
                                    value="key"
                                    label="value"/>
            </html:select>
            <html:link href="/kikin/workDateRequestCheckSubPage.do?paging=back">前へ</html:link>
            <html:link href="/kikin/workDateRequestCheckSubPage.do?paging=next">次へ</html:link>
            <bean:write name="workDateRequestCheckForm" property="countPage"/>/
            <bean:write name="workDateRequestCheckForm" property="maxPage"/>

              <table width="950px" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="150px" valign="top">
                    <table border="1" cellpadding="0" cellspacing="0">
                      <tr class="tableHeader" >
                        <td width="150px" align="center">
                          &nbsp;
                        </td>
                      </tr>
                      <tr class="tableHeader" >
                        <td width="150px" align="center">
                        社員名
                        </td>
                      </tr>
                      <logic:iterate offset="offset" length="<%=showLength %>" id="workDateRequestCheckBeanList" name="workDateRequestCheckForm" property="workDateRequestCheckBeanList">
                        <tr class="tableBody" >
                          <td width="150px" align="center">
                            <bean:write property="employeeName" name="workDateRequestCheckBeanList"/><br>
                          </td>
                        </tr>
                      </logic:iterate>
                    </table>
                  </td>
                  <td>
                    <div style="overflow-x: auto;overflow-y: hidden; width:800px;height: 100%; ">
                      <table border="1" cellpadding="0" cellspacing="0">
                        <tr class="tableHeader" >
                          <% for(int i = 1;i <= dateBeanListSize;i++ ) { %>
                        	 <td width="40px" align="center" valign="middle">
                          		<%= i %> 
                          	 </td>
                      	  <% } %>
                        </tr>
                        <tr class="tableHeader">
						<logic:iterate id="dateBeanList" name="workDateRequestCheckForm" property="dateBeanList">
                          <bean:define id="weekDayEnum" name="dateBeanList" property="weekDayEnum"/>
                          <bean:define id="publicHolidayFlg" name="dateBeanList" property="publicHolidayFlg"/>
                              <%
                              if (DayOfWeek.SATURDAY.equals(weekDayEnum)) {
                                  color = "fontBlue";
                              } else if (DayOfWeek.SUNDAY.equals(weekDayEnum) || ((boolean)publicHolidayFlg)) {
                                  color = "fontRed";
                              } else {
                                  color = "fontBlack";
                              }
                              %>
                              <td width="40px" align="center" valign="middle" class="<%=color %>">
                                <bean:write property="weekDay" name="dateBeanList"/>
                              </td>
                          </logic:iterate>
                        </tr>
                        <logic:iterate offset="offset"  length="<%=showLength %>" id="workDateRequestCheckBeanList" name="workDateRequestCheckForm" property="workDateRequestCheckBeanList">
                          <tr class="tableBody" >
                          	<% for(int i = 1; i <= dateBeanListSize; i++) {%>
    							<td width="40px" align="center" valign="middle" class="tableBody">  
    								<%if(i < 10){
    									symbol = "symbol0" + i;
    								}else{
    								 	symbol = "symbol" + i;
    								} %>
    								<bean:write property="<%=symbol %>" name="workDateRequestCheckBeanList" />
    							</td>
    						<% } %> 							
                          </tr>
                        </logic:iterate>
                      </table>
                    </div>
                  </td>
                </tr>
              </table>
          </html:form>
        </div>
      </div>
      <div id="footer">
        <table>
          <tr>
              <td id="footLeft">
                　
              </td>
              <td id="footCenter">
                　
              </td>
              <td id="footRight">
                　
              </td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>