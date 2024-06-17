/**
 * ファイル名：BaseShiftMstMntRegisterAction.java
 *
 * 変更履歴
 * 1.0  2010/11/02 Kazuya.nishioka
 */
package action.bse;

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
import business.dto.bse.BaseShiftDto;
import business.logic.bse.BaseShiftLogic;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.bse.BaseShiftMstMntBean;
import form.bse.BaseShiftMstMntForm;

/**
 * 説明：基本シフト入力確認登録処理
 * @author nishioka 西岡
 *
 */
public class BaseShiftMstMntRegisterAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 勤務実績入力確認登録処理のアクション
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
        log.info(baseShiftMstMntForm);

        // 基本シフトロジック
        BaseShiftLogic baseShiftLogic = new BaseShiftLogic();

        // フォームデータをDtoに変換する
        List<BaseShiftDto> baseShiftDto = this.formToDto(baseShiftMstMntForm);

        // 基本シフトデータの更新・登録を行う
        baseShiftLogic.registerBaseShift(baseShiftDto, loginUserDto);

        forward = CommonConstant.SUCCESS;

        return mapping.findForward(forward);
    }

    /**
     * formデータをDtoに変化する
     * @param
     * @return
     * @author nishioka
     */
    private List<BaseShiftDto> formToDto(BaseShiftMstMntForm baseShiftMstMntForm) {

        // 戻り値のリスト
        List<BaseShiftDto> dtoList = new ArrayList<BaseShiftDto>();
        // 画面の一覧
        List<BaseShiftMstMntBean> baseShiftMstMntBean = baseShiftMstMntForm.getBaseShiftMstMntBeanList();

        for (BaseShiftMstMntBean bean : baseShiftMstMntBean) {
            BaseShiftDto baseShiftDto = new BaseShiftDto();

            // 計算以外の部分をセットする
            baseShiftDto.setEmployeeId(bean.getEmployeeId());                   // 社員ID
            baseShiftDto.setShiftIdOnMonday(bean.getShiftIdOnMonday());         // 月曜シフト
            baseShiftDto.setShiftIdOnTuesday(bean.getShiftIdOnTuesday());       // 火曜シフト
            baseShiftDto.setShiftIdOnWednesday(bean.getShiftIdOnWednesday());   // 水曜シフト
            baseShiftDto.setShiftIdOnThursday(bean.getShiftIdOnThursday());     // 木曜シフト
            baseShiftDto.setShiftIdOnFriday(bean.getShiftIdOnFriday());         // 金曜シフト
            baseShiftDto.setShiftIdOnSaturday(bean.getShiftIdOnSaturday());     // 土曜シフト
            baseShiftDto.setShiftIdOnSunday(bean.getShiftIdOnSunday());         // 日曜シフト
            dtoList.add(baseShiftDto);
        }
        return dtoList;
    }
}
