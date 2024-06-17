/**
 * ファイル名：DailyShiftInitAction.java
 *
 * 変更履歴
 * 1.0  2010/10/25 Kazuya.Naraki
 */
package action.day;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.day.DailyShiftDto;
import business.logic.day.DailyShiftLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.day.DailyShiftForm;

/**
 * 説明：日別シフト初期処理のアクション
 * @author naraki
 *
 */
public class DailyShiftInitAction extends DailyShiftAbstractAction {

    /**
     * 日別シフト初期処理のアクション
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

        // フォーム
        DailyShiftForm dailyShiftForm = (DailyShiftForm) form;

        // フォワードキー
        String forward = CommonConstant.SUCCESS;

        // ロジック生成
        DailyShiftLogic dailyShiftLogic = new DailyShiftLogic();

        // 現在日付
        String yearMonthDay = CommonUtils.getFisicalDay();
        // 現在日付（画面表示用）
        String yearMonthDayDisplay = CommonUtils.changeFormat(yearMonthDay, CommonConstant.YEARMONTHDAY_NOSL, CommonConstant.YEARMONTHDAY);

        // システム日付のシフトデータを取得する
        List<DailyShiftDto> dailyShiftDtoList = dailyShiftLogic.getDailyShiftDtoList(yearMonthDay);

        if (CheckUtils.isEmpty(dailyShiftDtoList)) {	
            forward = CommonConstant.NODATA;
        }

        // フォームへ一覧をセットする
        dailyShiftForm.setDailyShiftBeanList(dtoToForm(dailyShiftDtoList));
        dailyShiftForm.setYearMonthDay(yearMonthDay);
        dailyShiftForm.setYearMonthDayDisplay(yearMonthDayDisplay);

        yearMonthDay = CommonUtils.add(yearMonthDay, 0, 0, -1);


        // 戻り先を保存
        req.setAttribute(RequestSessionNameConstant.REQUEST_BACK_PAGE, "");

        return mapping.findForward(forward);
    }
}
