/**
 * ファイル名：LoginAction.java
 *
 * 変更履歴
 * 1.0  2010/07/19 Kazuya.Naraki
 */
package action.cmn;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.LoginUserDto;
import business.dto.cmn.LoginDto;
import business.logic.cmn.LoginLogic;
import business.logic.utils.CheckUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.cmn.LoginForm;

/**
 * 説明：ログイン処理のアクション
 *
 * @author naraki
 *
 */
public class LoginAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * ログイン処理のアクション
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return アクションフォワード
     * @author naraki
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) throws Exception {
        log.info(new Throwable().getStackTrace()[0].getMethodName());

        // セッション
        HttpSession session = req.getSession();

        // 全てのセッションを削除する。
        Enumeration<String> sessionEnum = session.getAttributeNames();

        while (sessionEnum.hasMoreElements()) {
            String sessionKey = sessionEnum.nextElement();
            session.removeAttribute(sessionKey);
        }

        // フォワードキー
        String forward = "";

        // フォーム
        LoginForm loginForm = (LoginForm) form;

        // ロジック生成
        LoginLogic loginLogic = new LoginLogic();

        // 社員情報を取得する
        LoginDto loginDto = loginLogic.getEmployeeData(loginForm);

        if (CheckUtils.isEmpty(loginDto)) {
            forward = "error";
          
            session.setAttribute("error","ログインIDまたはパスワードが違います。");
          
            } else {

            // ログインユーザ保持用Dtoを作成する
            this.createLoginUserData(session, loginDto);

            forward = CommonConstant.SUCCESS;
        }

        return mapping.findForward(forward);
    }

    /**
     * ログインユーザ情報をセッションに登録する。
     *
     * @param session セッション
     * @param loginDto 取得したログイン処理Dto
     * @author naraki
     */
    private void createLoginUserData(HttpSession session, LoginDto loginDto) {

        // ログインユーザの社員ID
        session.setAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_ID, loginDto.getEmployeeId());
        // ログインユーザの社員名
        session.setAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_NAME, loginDto.getEmployeeName());
        // ログインユーザの社員名カナ
        session.setAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_NAME_KANA, loginDto.getEmployeeNameKana());
        // ログインユーザの権限ID
        session.setAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_AUTHORITY_ID, loginDto.getAuthorityId());

        // ログインユーザ情報の設定
        LoginUserDto loginUserDto = new LoginUserDto();
        loginUserDto.setEmployeeId(loginDto.getEmployeeId());
        loginUserDto.setEmployeeName(loginDto.getEmployeeName());
        loginUserDto.setEmployeeNameKana(loginDto.getEmployeeNameKana());
        loginUserDto.setAuthorityId(loginDto.getAuthorityId());

        session.setAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO, loginUserDto);


    }

}