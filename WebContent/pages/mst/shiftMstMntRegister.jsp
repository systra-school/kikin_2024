<!-- shiftMstMntRegister.jsp -->
<%@page import="business.logic.utils.CheckUtils"%>
<%@page import="form.mst.ShiftMstMntBean"%>
<%@page import="java.util.List"%>
<%@page import="form.mst.ShiftMstMntForm"%>
<%
/**
 * ファイル名：shiftMstMntRegister.jsp
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
     * 登録処理を行う
     */
    function shiftMstMntRegister() {

        // 開始時間エラーメッセージ
        var startTimeErrMsg = '';
        // 終了時間エラーメッセージ
        var endTimeErrMsg = '';
        // 休憩時間エラーメッセージ
        var breakTimeErrMsg = '';
        // エラーメッセージ
        var errorMsg = '';
        // From - To エラーメッセージ
        var fromToErrMsg = '';

        // 時間チェック
        with (document.forms[0]) {
          // 開始時間を取得する。
          var varStartTime = startTime.value;
          // 終了時間を取得する。
          var varEndTime = endTime.value;
          // 休憩時間を取得する。
          var varBreakTime = breakTime.value;

          // 背景色をクリアする
          startTime.style.backgroundColor = 'white';
          endTime.style.backgroundColor = 'white';
          breakTime.style.backgroundColor = 'white';

          if (!checkTime(varStartTime)) {
              var strArr = ['開始時間'];
              startTimeErrMsg = getMessage('E-MSG-000004', strArr);
              startTime.style.backgroundColor = 'red';
          }

          if (!checkTime(varEndTime)) {
              var strArr = ['終了時間'];
              endTimeErrMsg = getMessage('E-MSG-000004', strArr);
              endTime.style.backgroundColor = 'red';
          }

          if (!checkTime(varBreakTime)) {
              var strArr = ['休憩時間'];
              breakTimeErrMsg = getMessage('E-MSG-000004', strArr);
              breakTime.style.backgroundColor = 'red';
          }

          // from - to のチェック
          if (!checkTimeCompare(varStartTime, varEndTime)) {
            if (checkTime(varStartTime) && checkTime(varEndTime)) {
                fromToErrMsg = getMessageCodeOnly('E-MSG-000005');
                startTime.style.backgroundColor = 'red';
                endTime.style.backgroundColor = 'red';
            }
          }
        }

        // エラーメッセージ
        errorMsg = startTimeErrMsg + endTimeErrMsg + breakTimeErrMsg + fromToErrMsg;

        if (errorMsg) {
            alert(errorMsg);
            // エラー
            return false;
        }

        document.forms[0].submit();
    }
    </script>

    <title>シフトマスタメンテナンス画面</title>

    <link href="/kikin/pages/css/common.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
    <div id="wrapper">
      <div id="header">
        <table>
          <tr>
            <td id="headLeft">
              <input value="戻る" type="button" class="smallButton"  onclick="doSubmit('/kikin/shiftMstMntRegisterBack.do')" />
            </td>
            <td id="headCenter">
              シフトマスタメンテナンス（新規登録）
            </td>
            <td id="headRight">
              <input value="ログアウト" type="button" class="smallButton"  onclick="logout()" />
            </td>
          </tr>
        </table>
      </div>
      <div id="businessBody">
        <html:form action="/shiftMstMntRegister">
          <div style="width: 630px; margin-left:300px;">
            <table class="tableHeader" border="1" cellpadding="0" cellspacing="0">
              <tr>
                <td width="180px" align="center">
                  シフト名
                </td>
                <td width="70px" align="center">
                  シンボル
                </td>
                <td width="270px" align="center">
                  時間
                </td>
                <td width="100px" align="center">
                  休憩
                </td>
              </tr>
            </table>
          </div>
          <div style="overflow: auto; height: 400px; width: 630px; margin-left:300px;">
            <table class="tableBody" border="1" cellpadding="0" cellspacing="0">
              <tr>
                <td width="180px"  align="center">
                  <html:text property="shiftName" size="20" maxlength="10" />
                </td>
                <td width="70px"  align="center">
                  <html:text property="symbol" size="2" maxlength="2" />
                </td>
                <td width="270px"  align="center">
                  <table width="100%" >
                    <tr>
                      <td align="center">
                        <html:text property="startTime" size="5" maxlength="10" />
                      </td>
                      <td align="center">
                          &#xFF5E;
                      </td>
                      <td align="center">
                        <html:text property="endTime" size="5" maxlength="10" />
                      </td>
                    </tr>
                  </table>
                </td>
                <td width="100px"  align="center">
                  <html:text property="breakTime" size="5" maxlength="10" />
                </td>
              </tr>
            </table>
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
                <input value="登録" type="button" class="smallButton"  onclick="shiftMstMntRegister()" />
            </td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>