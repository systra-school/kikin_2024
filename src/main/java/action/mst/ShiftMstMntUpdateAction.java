/**
 * ファイル名：ShiftMstMntUpdateAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.mst;

import java.util.ArrayList;
import java.util.Collection;
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
import business.logic.utils.CheckUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.mst.ShiftMstMntBean;
import form.mst.ShiftMstMntForm;

/**
 * 説明：シフトマスタメンテナンス更新系アクションクラス
 * @author naraki
 *
 */
public class ShiftMstMntUpdateAction extends Action{

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * シフトマスタメンテナンス更新系アクションクラス
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
        ShiftMstMntForm shiftMstMntForm = (ShiftMstMntForm) form;

        // リクエスト内容をDtoに変換する
        List<ShiftMstMntDto> shiftMstMntDtoList = this.formToDto(shiftMstMntForm);

        // ロジック生成
        ShiftMstMntLogic shiftMstMntLogic = new ShiftMstMntLogic();

        // 更新・削除処理
        shiftMstMntLogic.updateShiftMst(shiftMstMntDtoList, loginUserDto);

        // シフトマスタ情報を再検索する
        shiftMstMntDtoList = shiftMstMntLogic.getShiftData(loginUserDto);

        if (CheckUtils.isEmpty(shiftMstMntDtoList)) {
            // データなし
            forward = CommonConstant.NODATA;
        } else {
            // フォームへ一覧をセットする
            shiftMstMntForm.setShiftMstMntBeanList(dtoToForm(shiftMstMntDtoList));
        }

        return mapping.findForward(forward);
    }

    /**
     * リクエスト情報をDtoのリストにセットする。
     * @param employeeMstMntForm シフトマスタフォーム
     * @return シフトマスタDtoリスト
     * @author naraki
     */
    private List<ShiftMstMntDto> formToDto(ShiftMstMntForm employeeMstMntForm) {
        // 返却用Dtoリスト
        List<ShiftMstMntDto> shiftMstMntDtoList = new ArrayList<ShiftMstMntDto>();
        List<ShiftMstMntBean> shiftMstMntBeanList = employeeMstMntForm.getShiftMstMntBeanList();

        // 画面一覧のサイズ分処理を繰り返す
        for (ShiftMstMntBean shiftMstMntBean : shiftMstMntBeanList) {
            ShiftMstMntDto shiftMstMntDto = new ShiftMstMntDto();

            // Dtoに値をセットする
            shiftMstMntDto.setShiftId(shiftMstMntBean.getShiftId());
            shiftMstMntDto.setShiftName(shiftMstMntBean.getShiftName());
            shiftMstMntDto.setSymbol(shiftMstMntBean.getSymbol());
            shiftMstMntDto.setStartTime(shiftMstMntBean.getStartTime());
            shiftMstMntDto.setEndTime(shiftMstMntBean.getEndTime());
            shiftMstMntDto.setBreakTime(shiftMstMntBean.getBreakTime());
            shiftMstMntDto.setDeleteFlg(shiftMstMntBean.getDeleteFlg());

            shiftMstMntDtoList.add(shiftMstMntDto);
        }

        return shiftMstMntDtoList;
    }

    /**
     * DtoからFormへ変換する
     * @param
     * @return
     * @author naraki
     */
    private List<ShiftMstMntBean> dtoToForm(Collection<ShiftMstMntDto> colection) {
        List<ShiftMstMntBean> shiftMstMntBeanList = new ArrayList<ShiftMstMntBean>();

        for (ShiftMstMntDto dto : colection) {
            ShiftMstMntBean shiftMstMntBean = new ShiftMstMntBean();
            shiftMstMntBean.setShiftId(dto.getShiftId());
            shiftMstMntBean.setShiftName(dto.getShiftName());
            shiftMstMntBean.setSymbol(dto.getSymbol());
            shiftMstMntBean.setStartTime(dto.getStartTime());
            shiftMstMntBean.setEndTime(dto.getEndTime());
            shiftMstMntBean.setBreakTime(dto.getBreakTime());

            shiftMstMntBeanList.add(shiftMstMntBean);

        }
        return shiftMstMntBeanList;
    }
}
