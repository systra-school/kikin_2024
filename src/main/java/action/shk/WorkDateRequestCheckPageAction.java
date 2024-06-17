/**
 * ファイル名：WorkDateRequestCheckPageAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.shk;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import constant.CommonConstant;
import form.shk.WorkDateRequestCheckForm;

/**
 * 説明：出勤希望日確認画面ページ変更アクションクラス
 * @author naraki
 *
 */
public class WorkDateRequestCheckPageAction extends WorkDateRequestAbstractAction{

    /**
     * 説明：出勤希望日入力画面表示アクションクラス
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
        String forward = CommonConstant.SUCCESS;

        // フォーム
        WorkDateRequestCheckForm workDateRequestCheckForm = (WorkDateRequestCheckForm) form;

        // 一覧のサイズ
        int listSize = workDateRequestCheckForm.getWorkDateRequestCheckBeanList().size();

        // ページング
        String paging = workDateRequestCheckForm.getPaging();
        int offset = workDateRequestCheckForm.getOffset();
        int countPage = workDateRequestCheckForm.getCountPage();

        int nextOffset = 0;

        if (CommonConstant.NEXT.equals(paging)) {
            // 次ページ
            nextOffset = offset + SHOW_LENGTH;

            if (nextOffset < listSize) {
                // 一覧のサイズ未満の場合は
                offset = nextOffset;
                countPage++;
            }
        } else {
            // 前ページ
            nextOffset = offset - SHOW_LENGTH;

            if (nextOffset < 0) {
                offset = 0;
            } else {
                offset = nextOffset;
                countPage--;
            }
        }

        workDateRequestCheckForm.setOffset(offset);
        workDateRequestCheckForm.setCountPage(countPage);

        return mapping.findForward(forward);
    }

}
