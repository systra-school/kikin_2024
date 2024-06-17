/**
 * ファイル名：DailyShiftPageAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.day;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.day.DailyShiftDto;
import business.logic.day.DailyShiftLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import form.day.DailyShiftForm;

/**
 * 説明：日別シフト確認画面ページ変更アクションクラス
 * @author naraki
 *
 */
public class DailyShiftPageAction extends DailyShiftAbstractAction {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 説明：日別シフト確認ページ表示アクションクラス
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
        DailyShiftForm dailyShiftForm = (DailyShiftForm) form;

        // 表示年月
        String yearMonthDay = dailyShiftForm.getYearMonthDay();

        // ページング
        String paging = dailyShiftForm.getPaging();

        List<DailyShiftDto> dailyShiftDtoList;

        // ロジック生成
        DailyShiftLogic dailyShiftLogic = new DailyShiftLogic();

        if (CommonConstant.NEXT.equals(paging)) {
            // 次ページ
            yearMonthDay = CommonUtils.add(yearMonthDay, 0, 0, 1);

            // システム日付のシフトデータを取得する
             dailyShiftDtoList = dailyShiftLogic.getDailyShiftDtoList(yearMonthDay);

        } else {
            // 前ページ
            yearMonthDay = CommonUtils.add(yearMonthDay, 0, 0, -1);

            // システム日付のシフトデータを取得する
            dailyShiftDtoList = dailyShiftLogic.getDailyShiftDtoList(yearMonthDay);
        }

        if (CheckUtils.isEmpty(dailyShiftDtoList)) {
            forward = CommonConstant.NODATA;
        }

        // フォームへ一覧をセットする
        dailyShiftForm.setDailyShiftBeanList(dtoToForm(dailyShiftDtoList));
        dailyShiftForm.setYearMonthDay(yearMonthDay);
        dailyShiftForm.setYearMonthDayDisplay(CommonUtils.changeFormat(yearMonthDay, CommonConstant.YEARMONTHDAY_NOSL, CommonConstant.YEARMONTHDAY));

        return mapping.findForward(forward);
    }

}
