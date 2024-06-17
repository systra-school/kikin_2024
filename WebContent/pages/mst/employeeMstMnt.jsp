<!-- employeeMstMnt.jsp -->
<%
/**
 * ファイル名：employeeMstMnt.jsp
 *
 * 変更履歴
 * 1.0  2010/09/13 Kazuya.Naraki
 */
%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@page import="business.logic.utils.CheckUtils"%>
<%@page import="form.mst.EmployeeMstMntForm"%>
<%@page import="java.util.List"%>
<%@page import="form.mst.EmployeeMstMntBean"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="constant.RequestSessionNameConstant"%>
<%@ page import="constant.CommonConstant"%>

<bean:size id="employeeMstMntBeanListSize" name="employeeMstMntForm" property="employeeMstMntBeanList"/>
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
     * チェックボックスがチェックされたら true、されていなければ false
     * param index 対象行番号
     */
    function checkDeleteFlg(index) {
        var deleteEmployeeId = document.forms[0].elements.deleteEmployeeId;
        var isCheck = false;

        if (deleteEmployeeId.length > 1) {
            isCheck = document.forms[0].elements.deleteEmployeeId[index].checked;
        } else {
            isCheck = deleteEmployeeId.checked;
        }

        document.forms[0].elements.namedItem('employeeMstMntBeanList['+ index +'].deleteFlg').value = isCheck;
    }

    /**
     * 新規登録画面へ
     */
    function employeeMstMntRegisterInit() {
        document.forms[0].action = "/kikin/employeeMstMntRegisterInit.do";
        document.forms[0].submit();
    }

    /**
     * 更新処理を行う
     */
    function employeeMstMntUpdate() {
        // 一覧のサイズ
        var listSize = <%= employeeMstMntBeanListSize %>;

        // パスワードエラーメッセージ
        var passwordErrorMsg = '';
        // 社員名カナエラーメッセージ
        var employeeNameKanaErrorMsg = '';
        var errorMsg = '';

        with(document.forms[0].elements) {
            for (var i = 0; i < listSize; i++) {
                // パスワードを取得する。
                var password = namedItem('employeeMstMntBeanList['+ i +'].password').value;
                // 社員名カナを取得する。
                var employeeNameKana = namedItem('employeeMstMntBeanList['+ i +'].employeeNameKana').value;

                // 背景色をクリアする
                namedItem('employeeMstMntBeanList['+ i +'].password').style.backgroundColor = 'white';
                namedItem('employeeMstMntBeanList['+ i +'].employeeNameKana').style.backgroundColor = 'white';

                // パスワードチェック
                if (!passwordErrorMsg) {
                    if (!checkRequired(password)) {
                        var strArr = ['パスワード'];
                        passwordErrorMsg = getMessage('E-MSG-000001', strArr);
                        namedItem('employeeMstMntBeanList['+ i +'].password').style.backgroundColor = 'red';
                    }
                }
                // 社員名カナチェック
                if (!employeeNameKanaErrorMsg) {
                    if (!checkHalfWidthKana(employeeNameKana)) {
                        var strArr = ['社員名カナ'];
                        employeeNameKanaErrorMsg = getMessage('E-MSG-000003', strArr);
                        namedItem('employeeMstMntBeanList['+ i +'].employeeNameKana').style.backgroundColor = 'red';
                    }
                }

                if (passwordErrorMsg && employeeNameKanaErrorMsg) {
                    // パスワード 、社員名カナが共にエラーの場合
                    break;
                }
            }
        }
        // エラーメッセージ
        errorMsg = passwordErrorMsg + employeeNameKanaErrorMsg;

        if (errorMsg) {
            alert(errorMsg);
            // エラー
            return false;
        }

        document.forms[0].submit();
    }
    </script>

    <title>社員マスタメンテナンス画面</title>

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
              社員マスタメンテナンス
            </td>
            <td id="headRight">
              <input value="ログアウト" type="button" class="smallButton"  onclick="logout()" />
            </td>
          </tr>
        </table>
      </div>
      <div id="businessBody">
        <html:form action="/employeeMstMntUpdate">
          <div style="overflow:hidden; margin-left:100px; width:1030px;">
            <table class="tableHeader" border="1"   cellpadding="0" cellspacing="0">
              <tr>
                <td width="200px" align="center">
                  社員ＩＤ
                </td>
                <td width="200px" align="center">
                  パスワード
                </td>
                <td width="200px" align="center">
                  社員名
                </td>
                <td width="200px" align="center">
                  社員名カナ
                </td>
                <td width="100px" align="center">
                  権限
                </td>
                <td width="100px" align="center">
                  削除
                </td>
              </tr>
            </table>
          </div>
          <div style="overflow: auto; height:440px; width:1030px; margin-left:100px ">
            <table class="tableBody" border="1"  cellpadding="0" cellspacing="0" >
              <logic:iterate indexId="idx" id="employeeMstMntBeanList" name="employeeMstMntForm"  property="employeeMstMntBeanList">
                <bean:define id="employeeId" name= "employeeMstMntBeanList" property="employeeId" type="java.lang.String"/>
                <bean:define id="selectAuthorityId" name= "employeeMstMntBeanList" property="authorityId" type="java.lang.String"/>
                <tr>
                  <td width="200px"  align="center">
                    <bean:write property="employeeId" name="employeeMstMntBeanList"/>
                    <html:hidden property="employeeId" name="employeeMstMntBeanList" indexed="true"/>
                  </td>
                  <td width="200px"  align="center">
                    <html:password property="password" name="employeeMstMntBeanList"  size="10" maxlength="6" indexed="true" />
                  </td>
                  <td width="200px"  align="center">
                    <html:text property="employeeName" name="employeeMstMntBeanList" size="20" maxlength="10" indexed="true" />
                  </td>
                  <td width="200px"  align="center">
                    <html:text property="employeeNameKana" name="employeeMstMntBeanList"  size="20" maxlength="10" indexed="true" />
                  </td>
                  <td width="100px"  align="center">
                    <html:select property="authorityId" name="employeeMstMntBeanList" value="<%= selectAuthorityId %>" indexed="true">
                      <html:optionsCollection name="employeeMstMntForm"
                                              property="authorityCmbMap"
                                              value="key"
                                              label="value"/>
                    </html:select>
                  </td>
                  <td width="100px"  align="center">
                    <html:checkbox property="deleteEmployeeId" name="employeeMstMntBeanList" value="<%= employeeId %>" onchange='<%="checkDeleteFlg(" + idx + ")" %>' ></html:checkbox>
                    <html:hidden property="deleteFlg" name="employeeMstMntBeanList" value="false" indexed="true"/>
                  </td>
                </tr>
              </logic:iterate>
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
              <input value="新規登録" type="button" class="smallButton"  onclick="employeeMstMntRegisterInit()" />
              <input value="更新" type="button" class="smallButton"  onclick="employeeMstMntUpdate()" />
            </td>
          </tr>
        </table>
      </div>
    </div>
  </body>
</html>