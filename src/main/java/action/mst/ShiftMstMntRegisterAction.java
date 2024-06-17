/**
 * ファイル名：ShiftMstMntRegisterAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.mst;


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
import form.mst.ShiftMstMntRegisterForm;

/**
 * 説明：シフトマスタメンテナンス登録アクションクラス
 * @author naraki
 *
 */
public class ShiftMstMntRegisterAction extends Action{

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * シフトマスタメンテナンス登録アクションクラス
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

        // セッション
        HttpSession session = req.getSession();

        // ログインユーザ情報をセッションより取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);

        // フォーム
        ShiftMstMntRegisterForm shiftMstMntRegisterForm = (ShiftMstMntRegisterForm) form;

        // リクエスト内容をDtoに変換する
        ShiftMstMntDto shiftMstMntDto = this.formToDto(shiftMstMntRegisterForm);

        // ロジック生成
        ShiftMstMntLogic shiftMstMntLogic = new ShiftMstMntLogic();

        // 登録
        shiftMstMntLogic.registerMshift(shiftMstMntDto, loginUserDto);

        return mapping.findForward(forward);
    }

    /**
     * リクエスト情報をDtoのリストにセットする。
     * @param shiftMstMntRegisterForm シフトマスタ登録フォーム
     * @return シフトマスタDtoリスト
     * @author naraki
     */
    private ShiftMstMntDto formToDto(ShiftMstMntRegisterForm shiftMstMntRegisterForm) {
        // シフトマスタDto
        ShiftMstMntDto shiftMstMntDto = new ShiftMstMntDto();

        String shiftName = shiftMstMntRegisterForm.getShiftName();
        String symbol = shiftMstMntRegisterForm.getSymbol();
        String startTime = shiftMstMntRegisterForm.getStartTime();
        String endTime = shiftMstMntRegisterForm.getEndTime();
        String breakTime = shiftMstMntRegisterForm.getBreakTime();

        // Dtoに値をセットする
        shiftMstMntDto.setShiftName(shiftName);
        shiftMstMntDto.setSymbol(symbol);
        shiftMstMntDto.setStartTime(startTime);
        shiftMstMntDto.setEndTime(endTime);
        shiftMstMntDto.setBreakTime(breakTime);

        return shiftMstMntDto;
    }

}
