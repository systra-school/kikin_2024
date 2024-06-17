/**
 * ファイル名：BaseShiftMstMntInitAction.java
 *
 * 変更履歴
 * 1.0  2010/11/02 Kazuya.nishioka
 */
package action.bse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import business.dto.bse.BaseShiftDto;
import business.dto.mst.ShiftMstMntDto;
import business.logic.bse.BaseShiftLogic;
import business.logic.mst.ShiftMstMntLogic;
import business.logic.utils.ComboListUtilLogic;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.bse.BaseShiftMstMntBean;
import form.bse.BaseShiftMstMntForm;
import form.bse.BaseShiftPatternBean;

/**
 * 説明：基本シフト入力初期処理
 * @author nishioka
 *
 */
public class BaseShiftMstMntInitAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 基本シフト入力初期処理のアクション
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
        BaseShiftMstMntForm baseShiftMstMntForm = (BaseShiftMstMntForm) form;

        // セレクトボックス（シフトマスタ）の取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);

        // シフトマスタロジック
        ShiftMstMntLogic shiftMstMntLogic = new ShiftMstMntLogic();
        // シフトマスタの取得
        List<ShiftMstMntDto> shiftMstMntDto = shiftMstMntLogic.getShiftData(loginUserDto);

        // 基本シフトマスタロジック
        BaseShiftLogic baseShiftLogic = new BaseShiftLogic();
        // 設定済み基本シフトデータの取得
        Map<String, BaseShiftDto> baseShiftDataMap = baseShiftLogic.getBaseShiftData();

        // データを変換する（基本シフト凡例）
        List<BaseShiftPatternBean> shiftPatternBeanList = this.shiftPatternDataToBean(shiftMstMntDto);
        // データを変換する（設定済み基本シフト）
        List<BaseShiftMstMntBean> dateBeanList = this.listDataDtoToBean(baseShiftDataMap, loginUserDto);

        // フォームにデータをセットする
        baseShiftMstMntForm.setBaseShiftMstMntBeanList(dateBeanList);
        baseShiftMstMntForm.setShiftCmbMap(shiftCmbMap);
        baseShiftMstMntForm.setBaseShiftPatternBeanList(shiftPatternBeanList);

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
    private List<BaseShiftPatternBean> shiftPatternDataToBean(
            List<ShiftMstMntDto> shiftMstMntDtoList
    ) throws ParseException {

        // 戻り値
        List<BaseShiftPatternBean> returnList = new  ArrayList<BaseShiftPatternBean>(shiftMstMntDtoList.size());

        for (ShiftMstMntDto shiftMstMntDto: shiftMstMntDtoList) {

            // 勤務実績Bean
            BaseShiftPatternBean baseShiftPatternBean = new BaseShiftPatternBean();
            baseShiftPatternBean.setShiftName(shiftMstMntDto.getShiftName());
            baseShiftPatternBean.setSymbol(shiftMstMntDto.getSymbol());
            baseShiftPatternBean.setTimeZone(shiftMstMntDto.getStartTime() + "&nbsp;&#xFF5E;&nbsp;" + shiftMstMntDto.getEndTime());
            baseShiftPatternBean.setBreakTime(shiftMstMntDto.getBreakTime());

            returnList.add(baseShiftPatternBean);
        }

        return returnList;
    }
    /**
     * dtoデータをBeanのリストへ変換する
     * @param baseShiftDtoMap 基本シフトマップ key 社員ID, val 基本シフトDto
     * @return
     * @author nishioka
     * @throws ParseException
     */
    private List<BaseShiftMstMntBean> listDataDtoToBean(
            Map<String, BaseShiftDto> baseShiftDtoMap,
            LoginUserDto loginUserDto) throws ParseException {

        // 戻り値
        List<BaseShiftMstMntBean> returnList = new  ArrayList<BaseShiftMstMntBean>(baseShiftDtoMap.size());

        Collection <BaseShiftDto> values = baseShiftDtoMap.values();
        for (BaseShiftDto baseShiftDto: values) {

            // 基本シフトBean
            BaseShiftMstMntBean baseShiftMstMntBean = new BaseShiftMstMntBean();
            baseShiftMstMntBean.setEmployeeId(baseShiftDto.getEmployeeId());
            baseShiftMstMntBean.setEmployeeName(baseShiftDto.getEmployeeName());

            baseShiftMstMntBean.setShiftIdOnMonday(baseShiftDto.getShiftIdOnMonday());
            baseShiftMstMntBean.setShiftIdOnTuesday(baseShiftDto.getShiftIdOnTuesday());
            baseShiftMstMntBean.setShiftIdOnWednesday(baseShiftDto.getShiftIdOnWednesday());
            baseShiftMstMntBean.setShiftIdOnThursday(baseShiftDto.getShiftIdOnThursday());
            baseShiftMstMntBean.setShiftIdOnFriday(baseShiftDto.getShiftIdOnFriday());
            baseShiftMstMntBean.setShiftIdOnSaturday(baseShiftDto.getShiftIdOnSaturday());
            baseShiftMstMntBean.setShiftIdOnSunday(baseShiftDto.getShiftIdOnSunday());

            returnList.add(baseShiftMstMntBean);
        }

        return returnList;
    }
}
