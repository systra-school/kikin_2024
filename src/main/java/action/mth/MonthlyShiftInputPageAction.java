/**
 * ファイル名：MonthlyShiftInputPageAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.mth;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import constant.CommonConstant;
import form.mth.MonthlyShiftInputBean;
import form.mth.MonthlyShiftInputForm;

/**
 * 説明：月別シフト入力画面ページ変更アクションクラス
 * @author naraki
 *
 */
public class MonthlyShiftInputPageAction extends MonthlyShiftInputAbstractAction{

    /**
     * 説明：月別シフト入力画面ページ変更アクションクラス
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
        MonthlyShiftInputForm monthlyShiftForm = (MonthlyShiftInputForm) form;

        // 一覧のサイズ
        int listSize = monthlyShiftForm.getMonthlyShiftInputBeanList().size();

        // ページング
        String paging = monthlyShiftForm.getPaging();
        
        int offset = monthlyShiftForm.getOffset();
        
        int countPage = monthlyShiftForm.getCountPage();

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
        monthlyShiftForm.setOffset(offset);
        monthlyShiftForm.setCountPage(countPage);

        // 登録フラグ初期化
        List<MonthlyShiftInputBean> monthlyShiftBeanList = monthlyShiftForm.getMonthlyShiftInputBeanList();
        for (MonthlyShiftInputBean monthlyShiftBean : monthlyShiftBeanList) {
            monthlyShiftBean.setRegisterFlg(false);
        }

        return mapping.findForward(forward);
    }

}
