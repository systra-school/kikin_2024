/**
 * ファイル名：ShiftPatternAction.java
 *
 * 変更履歴
 * 1.0  2010/11/02 Kazuya.nishioka
 */
package action.cmn;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
import business.dto.mst.ShiftMstMntDto;
import business.logic.mst.ShiftMstMntLogic;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.cmn.ShiftPatternBean;
import form.cmn.ShiftPatternForm;

/**
 * 凡例表示アクション
 * @author nishioka
 *
 */
public class ShiftPatternAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 凡例表示アクション
     *
     * @param mapping アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return アクションフォワード
     * @author nishioka
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest req, HttpServletResponse res) throws Exception {
        log.info(new Throwable().getStackTrace()[0].getMethodName());

        // セッション
        HttpSession session = req.getSession();

        // フォワードキー
        String forward = "";

        // ログインユーザ情報をセッションより取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);

        // フォーム
        ShiftPatternForm baseShiftMstMntForm = (ShiftPatternForm) form;

        // シフトマスタロジック
        ShiftMstMntLogic shiftMstMntLogic = new ShiftMstMntLogic();
        // シフトマスタの取得
        List<ShiftMstMntDto> shiftMstMntDto = shiftMstMntLogic.getShiftData(loginUserDto);

        // データを変換する（基本シフト凡例）
        List<ShiftPatternBean> shiftPatternBeanList = this.shiftPatternDataToBean(shiftMstMntDto);

        // フォームにデータをセットする
        baseShiftMstMntForm.setShiftPatternBeanList(shiftPatternBeanList);

        forward = CommonConstant.SUCCESS;

        return mapping.findForward(forward);
    }


    /**
     * dtoデータをBeanのリストへ変換する
     * @param shiftMstMntDtoList 勤務実績マップ key 稼働日, val 勤務実績Dto
     * @return
     * @author nishioka
     * @throws ParseException
     */
    private List<ShiftPatternBean> shiftPatternDataToBean(
            List<ShiftMstMntDto> shiftMstMntDtoList
    ) throws ParseException {

        // 戻り値
        List<ShiftPatternBean> returnList = new  ArrayList<ShiftPatternBean>(shiftMstMntDtoList.size());

        for (ShiftMstMntDto shiftMstMntDto: shiftMstMntDtoList) {

            // 勤務実績Bean
            ShiftPatternBean shiftPatternBean = new ShiftPatternBean();
            shiftPatternBean.setShiftName(shiftMstMntDto.getShiftName());
            shiftPatternBean.setSymbol(shiftMstMntDto.getSymbol());
            shiftPatternBean.setTimeZone(shiftMstMntDto.getStartTime() + "&nbsp;&#xFF5E;&nbsp;" + shiftMstMntDto.getEndTime());
            shiftPatternBean.setBreakTime(shiftMstMntDto.getBreakTime());

            returnList.add(shiftPatternBean);
        }

        return returnList;
    }
}
