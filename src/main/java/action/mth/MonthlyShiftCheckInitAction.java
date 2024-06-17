/**
 * ファイル名：MonthlyShiftCheckInitAction.java
 *
 * 変更履歴
 * 1.0  2010/09/04 Kazuya.Naraki
 */
package action.mth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import business.dto.mth.MonthlyShiftDto;
import business.logic.comparator.MethodComparator;
import business.logic.mth.MonthlyShiftLogic;
import business.logic.utils.CheckUtils;
import business.logic.utils.ComboListUtilLogic;
import business.logic.utils.CommonUtils;
import constant.CommonConstant;
import constant.RequestSessionNameConstant;
import form.common.DateBean;
import form.mth.MonthlyShiftCheckBean;
import form.mth.MonthlyShiftCheckForm;

/**
 * 説明：月別シフト確認初期表示アクションクラス
 * @author naraki
 */
public class MonthlyShiftCheckInitAction extends MonthlyShiftInputAbstractAction{

    /**
     * 説明：月別シフト入力初期表示アクションクラス
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
        MonthlyShiftCheckForm monthlyShiftForm = (MonthlyShiftCheckForm) form;

        // 対象年月
        String yearMonth = CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL);

        // ロジック生成
        MonthlyShiftLogic monthlyShiftLogic = new MonthlyShiftLogic();

        // 対象年月の月情報を取得する。
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);

        // シフトIDを取得する
        Map<String,List<MonthlyShiftDto>> monthlyShiftDtoMap = monthlyShiftLogic.getMonthlyShiftDtoMap(yearMonth, true);

        List<MonthlyShiftCheckBean> monthlyShiftCheckBean = new ArrayList<MonthlyShiftCheckBean>();

        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);
        
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);
      
        if (CheckUtils.isEmpty(monthlyShiftDtoMap)) {
            // データなし
            MonthlyShiftCheckBean monthlyShiftBean = new MonthlyShiftCheckBean();
            monthlyShiftBean.setEmployeeId(loginUserDto.getEmployeeId());
            monthlyShiftBean.setEmployeeName(loginUserDto.getEmployeeName());

            monthlyShiftCheckBean.add(monthlyShiftBean);
        } else {
            // データあり
            monthlyShiftCheckBean = dtoToBean(monthlyShiftDtoMap, loginUserDto);
        }

        // フォームにデータをセットする
        monthlyShiftForm.setShiftCmbMap(shiftCmbMap);
        monthlyShiftForm.setYearMonthCmbMap(yearMonthCmbMap);
        monthlyShiftForm.setMonthlyShiftCheckBeanList(monthlyShiftCheckBean);
        monthlyShiftForm.setDateBeanList(dateBeanList);
        monthlyShiftForm.setYearMonth(yearMonth);
        // ページング用
        monthlyShiftForm.setOffset(0);
        monthlyShiftForm.setCountPage(1);
        monthlyShiftForm.setMaxPage(CommonUtils.getMaxPage(monthlyShiftDtoMap.size(), SHOW_LENGTH));

        return mapping.findForward(forward);
    }

    /**
     * DtoからBeanへ変換する
     * @param monthlyShiftDtoMap
     * @param loginUserDto
     * @return 一覧に表示するリスト
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private List<MonthlyShiftCheckBean> dtoToBean(Map<String, List<MonthlyShiftDto>> monthlyShiftDtoMap
                                                      , LoginUserDto loginUserDto)
                                                                        throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
        Collection<List<MonthlyShiftDto>> collection = monthlyShiftDtoMap.values();

        List<MonthlyShiftCheckBean> monthlyShiftCheckBeanList = new ArrayList<MonthlyShiftCheckBean>();

        for (List<MonthlyShiftDto> monthlyShiftDtoList : collection) {

            // 実行するオブジェクトの生成
            MonthlyShiftCheckBean monthlyShiftCheckBean = new MonthlyShiftCheckBean();

            // メソッドの取得
            Method[] methods = monthlyShiftCheckBean.getClass().getMethods();

            // メソッドのソートを行う
            Comparator<Method> sortAsc = new MethodComparator();
            Arrays.sort(methods, sortAsc); // 配列をソート

            int index = 0;
            int listSize = monthlyShiftDtoList.size();

            String employeeId = "";
            String employeeName = "";

            for (int i = 0; i < methods.length; i++) {
                // "setShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("setSymbol") && listSize > index) {
                    MonthlyShiftDto monthlyShiftDto = monthlyShiftDtoList.get(index);
                    // メソッド実行
                    methods[i].invoke(monthlyShiftCheckBean, monthlyShiftDto.getSymbol());

                    employeeId = monthlyShiftDto.getEmployeeId();
                    employeeName = monthlyShiftDto.getEmployeeName();

                    index ++;
                }
            }

            monthlyShiftCheckBean.setEmployeeId(employeeId);
            monthlyShiftCheckBean.setEmployeeName(employeeName);

            monthlyShiftCheckBeanList.add(monthlyShiftCheckBean);

        }

        return monthlyShiftCheckBeanList;
    }
}
