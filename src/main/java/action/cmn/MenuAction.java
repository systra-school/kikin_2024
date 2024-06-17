/**
 * ファイル名：MenuAction.java
 *
 * 変更履歴
 * 1.0  2010/09/13 Kazuya.Naraki
 * 2.0  2024/04/16 Atsuko.Yoshioka
 */
package action.cmn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import constant.CommonConstant;

/**
 * 説明：メニュー画面の処理のロジック
 * @author naraki
 *
 */
public class MenuAction extends Action {
	
    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * メニュー画面の処理のアクション
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

        // フォワードキー
        String forward = null;
        forward = CommonConstant.SUCCESS;

        return mapping.findForward(forward);
    }
}
