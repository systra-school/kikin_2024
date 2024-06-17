<!-- workRecordCheck.jsp -->
<%@page import="constant.CommonConstant.DayOfWeek"%>
<%
/**
 * ファイル名：workRecordCheck.jsp
 *
 * 変更履歴
 * 1.0  2010/09/13 Kazuya.Naraki
 */
%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="constant.RequestSessionNameConstant"%>
<%@ page import="constant.CommonConstant"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
String color = "";
%>
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
        doSubmit('/kikin/workRecordCheckSearch.do');
    }

    </script>
    <title>勤務実績確認画面</title>

    <link href="/kikin/pages/css/common.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <table width="100%">
          <tr>
            <td id="headLeft">
              <input value="戻る" type="button" class="smallButton"  onclick="doSubmit('/kikin/menu.do')" />
            </td>
            <td id="headCenter">
              勤務実績確認
            </td>
            <td id="headRight">
              <input value="ログアウト" type="button" class="smallButton"  onclick="logout()" />
            </td>
          </tr>
        </table>
      </div>
      <div id="businessBody">
        <html:form action="/employeeMstMntRegister" >
          <div style="float: left; width: 100%;">
            <div style="float: left; width: 804px; text-align: left; margin-left:100px;">
              表示年月：
              <html:select name="workRecordCheckForm" property="yearMonth" onchange="submitSearch()">
              <html:optionsCollection name="workRecordCheckForm"
                                      property="yearMonthCmbMap"
                                      value="key"
                                      label="value"/>
              </html:select>
            </div>
            <div style="float: left; width: 244px; text-align: left;">
              社員名：
              <html:select name="workRecordCheckForm" property="employeeId" onchange="submitSearch()">
              <html:optionsCollection name="workRecordCheckForm"
                                      property="employeeCmbMap"
                                      value="key"
                                      label="value"/>
              </html:select>
            </div>
          </div>
          <div id="left">
          <div style="width: 1088px;">
            <table class="tableHeader" border="1" cellpadding="0" cellspacing="0">
              <tr>
                <td width="70px" align="center">
                  日付
                </td>
                <td width="50px" align="center">
                  曜日
                </td>
                <td width="70px" align="center">
                  シフト
                </td>
                <td width="100px" align="center">
                  開始時刻
                </td>
                <td width="100px" align="center">
                  終了時刻
                </td>
                <td width="100px" align="center">
                  休憩
                </td>
                <td width="100px" align="center">
                  実働時間
                </td>
                <td width="100px" align="center">
                  時間外
                </td>
                <td width="100px" align="center">
                  休日
                </td>
                <td width="250px" align="center">
                  備考
                </td>
              </tr>
            </table>
          </div>
          <div style="overflow: auto; height: 400px; width: 1088px;" >
            <logic:iterate id="workRecordInputList" name="workRecordCheckForm" property="workRecordInputList" indexId="idx">
              <table class="tableBody" border="1" cellpadding="0" cellspacing="0">
                <tr>
                  <html:hidden name="workRecordInputList" property="employeeId" />
                  <td width="70px" align="center">
                    <bean:write name="workRecordInputList" property="workDayDisp" /><br>
                  </td>
                  <bean:define id="weekDay" name="workRecordInputList" property="weekDay"/>
				  <bean:define id="publicHolidayFlg" name="workRecordInputList" property="publicHolidayFlg"/>
				  
                  <%
                  if (DayOfWeek.SATURDAY.getWeekdayShort().equals(weekDay)) {
                      color = "fontBlue";
                  } else if (DayOfWeek.SUNDAY.getWeekdayShort().equals(weekDay) || ((boolean)publicHolidayFlg)) {
                      color = "fontRed";
                  } else {
                      color = "fontBlack";
                  }
                  %>

                  <td width="50px" align="center" class='<%=color %>'">
                    <bean:write name="workRecordInputList" property="weekDay" /><br>
                  </td>
                  <td width="70px" align="center" style="vertical-align: middle;">
                    <bean:write name="workRecordInputList" property="symbol" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="startTime" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="endTime" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="breakTime" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="actualWorkTime" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="overTime" /><br>
                  </td>
                  <td width="100px" align="center">
                    <bean:write name="workRecordInputList" property="holidayTime" /><br>
                  </td>
                  <td width="250px" align="left">
                    <bean:write name="workRecordInputList" property="remark" /><br>
                  </td>
                </tr>
              </table>
            </logic:iterate>
          </div>
          </div>
      </html:form>
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