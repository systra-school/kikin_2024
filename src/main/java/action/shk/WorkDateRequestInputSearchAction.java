/**
 * ファイル名：WorkDateRequestInputSearchAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.shk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import business.dto.LoginUserDto;
import business.dto.shk.WorkDateRequestCheckDto;
import business.logic.comparator.MethodComparator;
import business.logic.shk.WorkDateRequestLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.common.DateBean;
import form.shk.WorkDateRequestInputBean;
import form.shk.WorkDateRequestInputForm;

public class WorkDateRequestInputSearchAction extends WorkDateRequestAbstractAction {
	 /**
     * 説明：出勤希望シフト入力検索アクションクラス
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
        
        // ログインユーザ情報をセッションよる取得
        LoginUserDto loginUserDto = (LoginUserDto) session.getAttribute(RequestSessionNameConstant.SESSION_CMN_LOGIN_USER_INFO);
        
        // フォーム
        WorkDateRequestInputForm workDateRequestInputForm = (WorkDateRequestInputForm) form;
        
        // 対象年月
        String yearMonth = workDateRequestInputForm.getYearMonth();
        
        // ロジック生成
        WorkDateRequestLogic workDateRequestLogic = new WorkDateRequestLogic	();
        
        // 対象年月の月情報を取得する
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);
        
        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);
        
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);
        
        // シフトIDを取得する
        List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList = workDateRequestLogic.getWorkDateRequestCheckDtoList(yearMonth);
        List<WorkDateRequestInputBean> workDateRequestInputBeanList = new ArrayList<WorkDateRequestInputBean>();
        
        if (CheckUtils.isEmpty(workRequestCheckDtoNestedList)) {
            // データなし
            WorkDateRequestInputBean workDateRequestInputBean = new WorkDateRequestInputBean();
            workDateRequestInputBean.setEmployeeId(loginUserDto.getEmployeeId());
            workDateRequestInputBean.setEmployeeName(loginUserDto.getEmployeeName());
            workDateRequestInputBean.setRegisterFlg(true);
            workDateRequestInputBeanList.add(workDateRequestInputBean);
        } else {
            // データあり
            workDateRequestInputBeanList = dtoToBean(workRequestCheckDtoNestedList, loginUserDto);

            // フォームにデータをセットする
            workDateRequestInputForm.setShiftCmbMap(shiftCmbMap);
            workDateRequestInputForm.setYearMonthCmbMap(yearMonthCmbMap);
            workDateRequestInputForm.setWorkDateRequestInputBeanList(workDateRequestInputBeanList);
            workDateRequestInputForm.setDateBeanList(dateBeanList);
            workDateRequestInputForm.setYearMonth(yearMonth);
        }
        return mapping.findForward(forward);
    }
	/**
     * DtoからBeanへ変換する
     * @param workDateRequestInputDtoList
     * @param loginUserDto
     * @return 一覧に表示するリスト
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
	private List<WorkDateRequestInputBean> dtoToBean(
			List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList,
			LoginUserDto loginUserDto) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		List<WorkDateRequestInputBean> workDateRequestInputBeanList = new ArrayList<WorkDateRequestInputBean>();
		
		// 社員分のループ
		for (List<WorkDateRequestCheckDto> workRequestCheckDtoList : workRequestCheckDtoNestedList) {
			// 実行するオブジェクトの生成
			WorkDateRequestInputBean workDateRequestInputBean = new WorkDateRequestInputBean();
			
			 // メソッドの取得
            Method[] methods = workDateRequestInputBean.getClass().getMethods();
            
			// メソッドのソートを行う
            Comparator<Method> sortAsc = new MethodComparator();
            
            // 配列をソート
            Arrays.sort(methods, sortAsc);
            int index = 0;
            int listSize = workRequestCheckDtoList.size();
            String employeeId = "";
            String employeeName = "";
            
            for (int i = 0; i < methods.length; i++) {
                // "setShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("setShiftId") && listSize > index) {
                    WorkDateRequestCheckDto workDateRequestCheckDto = workRequestCheckDtoList.get(index);
                    // メソッド実行
                    methods[i].invoke(workDateRequestInputBean, workDateRequestCheckDto.getMyRequestShiftId());
                    employeeId = workDateRequestCheckDto.getEmployeeId();
                    employeeName = workDateRequestCheckDto.getEmployeeName();
                    index ++;
                }
            }
            workDateRequestInputBean.setEmployeeId(employeeId);
            workDateRequestInputBean.setEmployeeName(employeeName);
            workDateRequestInputBean.setRegisterFlg(false);
            workDateRequestInputBeanList.add(workDateRequestInputBean);
        }
		return workDateRequestInputBeanList;
	}
}