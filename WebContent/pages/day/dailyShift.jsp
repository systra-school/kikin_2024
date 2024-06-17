<!-- dailyShift.jsp -->
<%@page import="business.logic.utils.CheckUtils"%>
<%@page import="form.common.DateBean"%>
<%@page import="java.util.List"%>
<%@page import="form.shk.WorkDateRequestCheckForm"%>
<%
/**
 * ファイル名：dailyShift.jsp
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
<bean:size id="dailyShiftBeanListSize" name="dailyShiftForm"  property="dailyShiftBeanList"/>
<%
    int bodyRightDivWidth = 0;
    int bodyRightDivHeight = 0;
    int bodyLeftDivHeight = 0;
    // ボディテーブルの td の幅
    int tdWidth = 150;
    // ボディテーブルの tr の縦
    int trHeight = 50;
    // 縦スクロールバーの幅
    int scrollBarSize = 20;
    if (dailyShiftBeanListSize < 6) {
        bodyRightDivWidth = dailyShiftBeanListSize * tdWidth + scrollBarSize;
        bodyRightDivHeight = 402;
        bodyLeftDivHeight = 402;
    } else {
        bodyRightDivWidth = 918;
        bodyRightDivHeight = 418;
        bodyLeftDivHeight = 402;
    }


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
        doSubmit('/kikin/workDateRequestCheckShow.do');
    }

    /**
     * スクロールを同期させる
     */
    function onScroll() {
        headRightTbl.scrollLeft = bodyRightTbl.scrollLeft;
        bodyLeftTbl.scrollTop = bodyRightTbl.scrollTop;
    }

    </script>
    <title>日別シフト確認画面</title>

    <link href="/kikin/pages/css/common.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <table>
          <tr>
            <td id="headLeft">
              <input value="戻る" type="button" class="smallButton"  onclick="doSubmit('/kikin/menu.do')" />
            </td>
            <td id="headCenter">
              日別シフト確認画面
            </td>
            <td id="headRight">
              <input value="ログアウト" type="button" class="smallButton"  onclick="logout()" />
            </td>
          </tr>
        </table>
      </div>
      <div id="businessBody" style="overflow: hidden;">
        <div style="margin-left:160px;">
          <html:form>
            <div style="height: 20px; margin-left:405px;">
              表示年月：
              <html:link href="/kikin/dailyShiftPage.do?paging=back">前日</html:link>
              <bean:write name="dailyShiftForm" property="yearMonthDayDisplay"/>
              <html:link href="/kikin/dailyShiftPage.do?paging=next">翌日</html:link>
            </div>
            <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td width="154px" >
                  <div id="headLeftTbl" style="overflow-x: hidden;overflow-y: hidden;width: 154px;">
                    <table border="1" cellpadding="0" cellspacing="0" class="tableHeader">
                      <tr>
                        <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                        時間
                        </td>
                      </tr>
                    </table>
                  </div>
                </td>
                <td width="100%" valign="top">
                  <div id="headRightTbl" style="overflow-y: hidden;overflow-x: hidden;width: <%=bodyRightDivWidth%>px; ">
                    <table border="1" cellpadding="0" cellspacing="0" class="tableHeader">
                      <tr>
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                            <bean:write name="dailyShiftBean" property="employeeName"/><br>
                          </td>
                        </logic:iterate>
                      </tr>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
            <table border="0" cellpadding="0" cellspacing="0">
              <tr height="100%">
              <td valign="top">
                <div id="bodyLeftTbl" style="overflow-x: auto;overflow-y: hidden;width: 154px;height:<%=bodyLeftDivHeight %>px;">
                  <table border="1" cellpadding="0" cellspacing="0" class="tableBody" style="font-size:15px;">
                  <% 
                    for (int i = 0; i < 24; i++) { 
                  %>
                    <tr height="<%=trHeight %>px">
                      <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                      <%= String.format("%02d", i) %>：00 &#xFF5E; <%= String.format("%02d", i + 1) %>：00
                      </td>
                    </tr>
                    <% 
                    } 
                    %>
                  </table>
                 </div>
                </td>
                <td width="100%" valign="top">
                  <div id="bodyRightTbl"  style="overflow-x: auto;overflow-y: auto;width: <%=bodyRightDivWidth %>px;height:<%=bodyRightDivHeight %>px;" onScroll="onScroll();">
                    <table border="1" cellpadding="0" cellspacing="0" class="tableBody" style="font-size:13px;">
                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime00">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime00" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime00" >

                                <bean:write name="dailyShiftBean" property="strTime00"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime00">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime00">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime00" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime00" >
                                <bean:write name="dailyShiftBean" property="strTime00"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime01">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime01" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime01" >

                                <bean:write name="dailyShiftBean" property="strTime01"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime01">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime01">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime01" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime01" >
                                <bean:write name="dailyShiftBean" property="strTime01"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime02">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime02" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime02" >

                                <bean:write name="dailyShiftBean" property="strTime02"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime02">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime02">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime02" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime02" >
                                <bean:write name="dailyShiftBean" property="strTime02"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime03">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime03" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime03" >

                                <bean:write name="dailyShiftBean" property="strTime03"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime03">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime03">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime03" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime03" >
                                <bean:write name="dailyShiftBean" property="strTime03"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime04">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime04" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime04" >

                                <bean:write name="dailyShiftBean" property="strTime04"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime04">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime04">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime04" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime04" >
                                <bean:write name="dailyShiftBean" property="strTime04"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime05">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime05" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime05" >

                                <bean:write name="dailyShiftBean" property="strTime05"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime05">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime05">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime05" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime05" >
                                <bean:write name="dailyShiftBean" property="strTime05"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime06">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime06" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime06" >

                                <bean:write name="dailyShiftBean" property="strTime06"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime06">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime06">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime06" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime06" >
                                <bean:write name="dailyShiftBean" property="strTime06"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime07">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime07" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime07" >

                                <bean:write name="dailyShiftBean" property="strTime07"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime07">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime07">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime07" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime07" >
                                <bean:write name="dailyShiftBean" property="strTime07"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime08">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime08" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime08" >

                                <bean:write name="dailyShiftBean" property="strTime08"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime08">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime08">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime08" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime08" >
                                <bean:write name="dailyShiftBean" property="strTime08"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime09">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime09" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime09" >

                                <bean:write name="dailyShiftBean" property="strTime09"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime09">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime09">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime09" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime09" >
                                <bean:write name="dailyShiftBean" property="strTime09"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime10">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime10" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime10" >

                                <bean:write name="dailyShiftBean" property="strTime10"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime10">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime10">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime10" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime10" >
                                <bean:write name="dailyShiftBean" property="strTime10"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime11">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime11" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime11" >

                                <bean:write name="dailyShiftBean" property="strTime11"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime11">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime11">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime11" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime11" >
                                <bean:write name="dailyShiftBean" property="strTime11"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime12">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime12" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime12" >

                                <bean:write name="dailyShiftBean" property="strTime12"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime12">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime12">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime12" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime12" >
                                <bean:write name="dailyShiftBean" property="strTime12"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime13">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime13" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime13" >

                                <bean:write name="dailyShiftBean" property="strTime13"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime13">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime13">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime13" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime13" >
                                <bean:write name="dailyShiftBean" property="strTime13"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime14">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime14" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime14" >

                                <bean:write name="dailyShiftBean" property="strTime14"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime14">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime14">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime14" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime14" >
                                <bean:write name="dailyShiftBean" property="strTime14"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime15">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime15" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime15" >

                                <bean:write name="dailyShiftBean" property="strTime15"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime15">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime15">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime15" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime15" >
                                <bean:write name="dailyShiftBean" property="strTime15"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime16">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime16" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime16" >

                                <bean:write name="dailyShiftBean" property="strTime16"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime16">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime16">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime16" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime16" >
                                <bean:write name="dailyShiftBean" property="strTime16"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime17">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime17" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime17" >

                                <bean:write name="dailyShiftBean" property="strTime17"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime17">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime17">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime17" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime17" >
                                <bean:write name="dailyShiftBean" property="strTime17"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime18">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime18" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime18" >

                                <bean:write name="dailyShiftBean" property="strTime18"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime18">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime18">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime18" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime18" >
                                <bean:write name="dailyShiftBean" property="strTime18"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime19">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime19" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime19" >

                                <bean:write name="dailyShiftBean" property="strTime19"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime19">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime19">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime19" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime19" >
                                <bean:write name="dailyShiftBean" property="strTime19"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime20">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime20" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime20" >

                                <bean:write name="dailyShiftBean" property="strTime20"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime20">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime20">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime20" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime20" >
                                <bean:write name="dailyShiftBean" property="strTime20"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime21">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime21" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime21" >

                                <bean:write name="dailyShiftBean" property="strTime21"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime21">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime21">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime21" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime21" >
                                <bean:write name="dailyShiftBean" property="strTime21"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime22">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime22" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime22" >

                                <bean:write name="dailyShiftBean" property="strTime22"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime22">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime22">
                          <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime22" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime22" >
                                <bean:write name="dailyShiftBean" property="strTime22"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>

                      <tr height="<%=trHeight %>px">
                        <logic:iterate id="dailyShiftBean" name="dailyShiftForm" property="dailyShiftBeanList">
                          <logic:equal value="true" name="dailyShiftBean" property="boolTime23">
                          <% // 出勤予定の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center" bgcolor="LightPink">
                              <logic:empty name="dailyShiftBean" property="strTime23" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime23" >

                                <bean:write name="dailyShiftBean" property="strTime23"/>：

                                <logic:equal value="出勤" name="dailyShiftBean" property="strTime23">
                                  <bean:write name="dailyShiftBean" property="startTime"/>
                                  &#xFF5E;
                                  <bean:write name="dailyShiftBean" property="endTime"/>
                                  <br><bean:write name="dailyShiftBean" property="breakTime"/>
                                </logic:equal>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                          <logic:equal value="false" name="dailyShiftBean" property="boolTime23">
                            <% // 出勤予定外の時間帯 %>
                            <td width="<%=tdWidth %>px" nowrap="nowrap" align="center">
                              <logic:empty name="dailyShiftBean" property="strTime23" >
                                <br>
                              </logic:empty>
                              <logic:notEmpty name="dailyShiftBean" property="strTime23" >
                                <bean:write name="dailyShiftBean" property="strTime23"/>
                              </logic:notEmpty>
                            </td>
                          </logic:equal>
                        </logic:iterate>
                      </tr>
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