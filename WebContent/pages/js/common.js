/**
 * ファイル名：check.js
 * 共通処理
 *
 * 変更履歴
 * 1.0  2010/09/10 Kazuya.Naraki
 */

/**
 *  ログアウトボタンサブミット
 */
function logout() {
    document.forms[0].action = "/kikin/logout.do";
    document.forms[0].submit();
}

/**
 *  戻るボタンサブミット
 */
function doSubmit(action) {
    document.forms[0].action = action;
    document.forms[0].submit();
}