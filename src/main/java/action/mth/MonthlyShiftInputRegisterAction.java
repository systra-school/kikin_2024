/**
 * ファイル名：MonthlyShiftInputRegisterAction.java
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
import form.mth.MonthlyShiftInputBean;
import form.mth.MonthlyShiftInputForm;

/**
 * 説明：月別シフト入力登録アクションクラス
 * @author naraki
 */
public class MonthlyShiftInputRegisterAction extends MonthlyShiftInputAbstractAction{

    /**
     * 説明：月別シフト入力登録アクションクラス
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
        MonthlyShiftInputForm monthlyShiftForm = (MonthlyShiftInputForm) form;

        // 画面のリスト情報
        List<MonthlyShiftInputBean> monthlyShiftBeanList = monthlyShiftForm.getMonthlyShiftInputBeanList();

        // 対象年月
        String yearMonth = monthlyShiftForm.getYearMonth();
        
        // ロジック生成
        MonthlyShiftLogic monthlyShiftLogic = new MonthlyShiftLogic();

        // 対象年月の月情報を取得する。
        List<DateBean> dateBeanList = CommonUtils.getDateBeanList(yearMonth);

        // フォームデータをDtoに変換する
        List<List<MonthlyShiftDto>> monthlyShiftDtoNestedList = this.formToDto(monthlyShiftBeanList, dateBeanList);

        // 登録・更新処理
        monthlyShiftLogic.registerMonthlyShift(monthlyShiftDtoNestedList, loginUserDto);

        // シフトIDを取得する
        Map<String,List<MonthlyShiftDto>> monthlyShiftDtoMap = monthlyShiftLogic.getMonthlyShiftDtoMap(yearMonth, true);

        // セレクトボックスの取得
        ComboListUtilLogic comboListUtils = new ComboListUtilLogic();
        Map<String, String> shiftCmbMap = comboListUtils.getComboShift(true);
       
        Map<String, String> yearMonthCmbMap = comboListUtils.getComboYearMonth(CommonUtils.getFisicalDay(CommonConstant.YEARMONTH_NOSL), 2, ComboListUtilLogic.KBN_YEARMONTH_NEXT, false);

        if (CheckUtils.isEmpty(monthlyShiftDtoMap)) {
            // データなし
            MonthlyShiftInputBean monthlyShiftBean = new MonthlyShiftInputBean();
            monthlyShiftBean.setEmployeeId(loginUserDto.getEmployeeId());
            monthlyShiftBean.setEmployeeName(loginUserDto.getEmployeeName());
            monthlyShiftBean.setRegisterFlg(true);

            monthlyShiftBeanList.add(monthlyShiftBean);
        } else {
            // データあり
            monthlyShiftBeanList = dtoToBean(monthlyShiftDtoMap, loginUserDto);
        }

        // フォームにデータをセットする
        monthlyShiftForm.setShiftCmbMap(shiftCmbMap);
        monthlyShiftForm.setYearMonthCmbMap(yearMonthCmbMap);
        monthlyShiftForm.setMonthlyShiftInputBeanList(monthlyShiftBeanList);
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
    private List<MonthlyShiftInputBean> dtoToBean(Map<String, List<MonthlyShiftDto>> monthlyShiftDtoMap
                                                      , LoginUserDto loginUserDto)
                                                                        throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
        Collection<List<MonthlyShiftDto>> collection = monthlyShiftDtoMap.values();

        List<MonthlyShiftInputBean> monthlyShiftBeanList = new ArrayList<MonthlyShiftInputBean>();

        for (List<MonthlyShiftDto> monthlyShiftDtoList : collection) {

            // 実行するオブジェクトの生成
            MonthlyShiftInputBean monthlyShiftBean = new MonthlyShiftInputBean();

            // メソッドの取得
            Method[] methods = monthlyShiftBean.getClass().getMethods();

            // メソッドのソートを行う
            Comparator<Method> sortAsk = new MethodComparator();
            Arrays.sort(methods, sortAsk); // 配列をソート

            int index = 0;
            int listSize = monthlyShiftDtoList.size();

            String employeeId = "";
            String employeeName = "";

            for (int i = 0; i < methods.length; i++) {
                // "setShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("setShiftId") && listSize > index) {
                    MonthlyShiftDto dto = monthlyShiftDtoList.get(index);
                    // メソッド実行
                    methods[i].invoke(monthlyShiftBean, dto.getShiftId());

                    employeeId = dto.getEmployeeId();
                    employeeName = dto.getEmployeeName();

                    index ++;
                }
            }

            monthlyShiftBean.setEmployeeId(employeeId);
            monthlyShiftBean.setEmployeeName(employeeName);
            monthlyShiftBean.setRegisterFlg(false);

            monthlyShiftBeanList.add(monthlyShiftBean);

        }

        return monthlyShiftBeanList;
    }

    /**
     * DtoからBeanへ変換する
     * @param monthlyShiftBeanList
     * @return DtoList
     * @author naraki
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private List<List<MonthlyShiftDto>> formToDto(List<MonthlyShiftInputBean> monthlyShiftBeanList
                                                      , List<DateBean> dateBeanList) throws IllegalArgumentException,
                                                                        IllegalAccessException,
                                                                        InvocationTargetException {
        // 戻り値
        List<List<MonthlyShiftDto>> monthlyShiftDtoNestedList = new ArrayList<List<MonthlyShiftDto>>();

        for (MonthlyShiftInputBean monthlyShiftBean : monthlyShiftBeanList) {

            List<MonthlyShiftDto> monthlyShiftDtoList = new ArrayList<MonthlyShiftDto>();

            // 登録フラグ
            boolean registerFlg = monthlyShiftBean.getRegisterFlg();

            if (!registerFlg) {
                continue;
            }

            // メソッドの取得
            Method[] methods = monthlyShiftBean.getClass().getMethods();

            // ソートを行う
            Comparator<Method> sortAsk = new MethodComparator();
            Arrays.sort(methods, sortAsk); // 配列をソート

            int listSize = dateBeanList.size();

            int index = 0;

            for (int i = 0; i < methods.length; i++) {
                // "getShiftIdXX" のメソッドを動的に実行する
                if (methods[i].getName().startsWith("getShiftId") && index < listSize) {
                    String yearMonthDay = "";

                    // 対象年月取得
                    yearMonthDay = dateBeanList.get(index).getYearMonthDay();

                    MonthlyShiftDto dto = new MonthlyShiftDto();
                    String shiftId = (String) methods[i].invoke(monthlyShiftBean);

                    if (CommonConstant.BLANK_ID.equals(shiftId)) {
                        // 空白が選択されている場合
                        shiftId = null;
                    }

                    dto.setShiftId(shiftId);
                    dto.setEmployeeId(monthlyShiftBean.getEmployeeId());
                    dto.setYearMonthDay(yearMonthDay);
                    monthlyShiftDtoList.add(dto);

                    index++;
                }
            }

            monthlyShiftDtoNestedList.add(monthlyShiftDtoList);

        }

        return monthlyShiftDtoNestedList;
    }
}
