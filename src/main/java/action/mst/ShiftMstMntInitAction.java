/**
 * ファイル名：ShiftMstMntInitAction.java
 *
 * 変更履歴
 * 1.0  2010/08/23 Kazuya.Naraki
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
 * 説明：シフトマスタメンテナンス初期表示アクションクラス
 * @author naraki
 *
 */
public class ShiftMstMntInitAction extends Action {

    // ログ出力クラス
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * シフトマスタメンテナンス初期表示アクションクラス
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

        // セッション
        HttpSession session = req.getSession();

        // ログインユーザ情報をセッションより取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);

        // フォーム
        ShiftMstMntForm shiftMstMntForm = (ShiftMstMntForm) form;

        // フォワードキー
        String forward = CommonConstant.SUCCESS;

        // ロジック生成
        ShiftMstMntLogic shiftMstMntLogic = new ShiftMstMntLogic();

        // シフト情報を取得する
        Collection<ShiftMstMntDto> mshiftDataList = shiftMstMntLogic.getShiftData(loginUserDto);

        if (CheckUtils.isEmpty(mshiftDataList)) {
            forward = CommonConstant.NODATA;
        }

        // フォームへ一覧をセットする
        shiftMstMntForm.setShiftMstMntBeanList(dtoToForm(mshiftDataList));

        // 戻り先を保存
        req.setAttribute(RequestSessionNameConstant.REQUEST_BACK_PAGE, "");

        return mapping.findForward(forward);
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
