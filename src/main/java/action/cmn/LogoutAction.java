/**
 * ファイル名：LogoutAction.java
 *
 * 変更履歴
 * 1.0  2010/08/21 Kazuya.Naraki
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

import constant.CommonConstant;


/**
 * 説明：ログアウト処理のロジック
 * @author naraki
 *
 */
public class LogoutAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * ログアウト処理のアクション
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

        // フォワードキー
        String forward = CommonConstant.SUCCESS;

        // 全てのセッションを削除する。
        Enumeration<String> sessionEnum = session.getAttributeNames();

        while (sessionEnum.hasMoreElements()) {
            String sessionKey = sessionEnum.nextElement();
            session.removeAttribute(sessionKey);
        }

        return mapping.findForward(forward);
    }
}
