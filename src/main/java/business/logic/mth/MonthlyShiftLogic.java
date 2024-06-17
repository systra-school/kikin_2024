/**
 * ファイル名：MonthlyShiftLogic.java
 *
 * 変更履歴
 * 1.0  2010/10/06 Kazuya.Naraki
 * 2.0  2024/05/15 Sho.Kiyota
 */
package business.logic.mth;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bbreak.excella.reports.exporter.ExcelOutputStreamExporter;
import org.bbreak.excella.reports.model.ReportBook;
import org.bbreak.excella.reports.model.ReportSheet;
import org.bbreak.excella.reports.processor.ReportProcessor;
import org.bbreak.excella.reports.tag.ColRepeatParamParser;
import org.bbreak.excella.reports.tag.RowRepeatParamParser;
import org.bbreak.excella.reports.tag.SingleParamParser;

import action.mth.MonthlyShiftCheckPrintAction;
import business.db.dao.mth.MonthlyShiftDao;
import business.dto.LoginUserDto;
import business.dto.mth.MonthlyShiftDto;
import business.logic.utils.CheckUtils;
import business.logic.utils.CommonUtils;
import form.common.DateBean;
import form.mth.MonthlyShiftCheckBean;
import form.mth.MonthlyShiftCheckForm;

/**
 * 説明：希望出勤日入力処理のロジック
 * @author naraki
 *
 */
public class MonthlyShiftLogic {

    /**
     * シフトテーブルより情報を取得する。
     * @param yearMonth 検索対象年月
     * @param shiftFlg true：シフトIDを取得 false：希望シフトIDを取得
     * @return 出勤希望Dtoリスト
     * @author naraki
     */
    public Map<String, List<MonthlyShiftDto>> getMonthlyShiftDtoMap(String yearMonth, boolean shiftFlg) throws SQLException{

        // 戻り値
        Map<String, List<MonthlyShiftDto>> monthlyShiftDtoMap = new LinkedHashMap<String, List<MonthlyShiftDto>>();

        // Dao
        MonthlyShiftDao monthlyShiftDao = new MonthlyShiftDao();

        // シフト情報を取得する。
        List<MonthlyShiftDto> monthlyShiftDtoList = monthlyShiftDao.getShiftTblData(yearMonth, shiftFlg);

        String oldEmployeeId = "";

        // 一時領域
        List<MonthlyShiftDto> tmpList = new ArrayList<MonthlyShiftDto>();

        // DB取得より取得する値を各社員づつ区切る
        for(MonthlyShiftDto dto : monthlyShiftDtoList) {
            if (CheckUtils.isEmpty(oldEmployeeId)) {
            	
                // 社員IDが空のとき（初回）
                oldEmployeeId = dto.getEmployeeId();

                // 取得した値を戻り値のリストにセットする。
                tmpList.add(dto);

            } else {
                if (oldEmployeeId.equals(dto.getEmployeeId())) {
                    // 同一社員のデータ
                    // 取得した値を戻り値のリストにセットする。
                    tmpList.add(dto);
                } else {
                    // 別社員のデータのとき
                    // 前の社員分をマップにつめる
                    monthlyShiftDtoMap.put(oldEmployeeId, tmpList);

                    // oldEmployeeId を入れ替える
                    oldEmployeeId = dto.getEmployeeId();

                    // 新しい社員のデータを追加していく
                    tmpList = new ArrayList<MonthlyShiftDto>();
                    tmpList.add(dto);
                }
            }
        }

        if (!CheckUtils.isEmpty(oldEmployeeId)) {
            // 最後分を追加する
            monthlyShiftDtoMap.put(oldEmployeeId, tmpList);
        }

        return monthlyShiftDtoMap;
    }

    /**
     * シフトテーブルのデータを登録・更新する。
     * @param monthlyShiftDtoNestedList 月別シフト一覧
     * @return 基本シフトマップ
     * @author naraki
     * @throws SQLException
     */
    public void registerMonthlyShift(List<List<MonthlyShiftDto>> monthlyShiftDtoNestedList, LoginUserDto loginUserDto) throws SQLException {

        // Dao
        MonthlyShiftDao dao = new MonthlyShiftDao();
        // コネクション
        Connection connection = dao.getConnection();

        // トランザクション処理
        connection.setAutoCommit(false);

        try {
            for (List<MonthlyShiftDto> monthlyShiftDtoList : monthlyShiftDtoNestedList) {
                // 人数分のループ
                for (MonthlyShiftDto dto : monthlyShiftDtoList) {
                    // 日数分ループ

                    // 社員ID
                    String employeeId = dto.getEmployeeId();
                    // 対象年月
                    String yearMonthDay = dto.getYearMonthDay();

                    // レコードの存在を確認する
                    boolean isData = dao.isData(employeeId, yearMonthDay);

                    if (isData) {
                        // 更新
                    	dao.updateShiftTbl(dto, loginUserDto);
                    } else {
                        // 登録
                    	dao.registerShiftTbl(dto, loginUserDto);
                    }

                }
            }

        } catch (SQLException e) {
            // ロールバック処理
            connection.rollback();
            // 切断
            connection.close();

            throw e;
        }

        // コミット
        connection.commit();
        // 切断
        connection.close();

    }


    /**
     * 月別シフト確認の印刷を行う
     * @param monthlyShiftCheckForm 月別シフト確認フォーム
     * @param excelFileExporter
     * @return
     * @author naraki
     */
    @SuppressWarnings("unchecked")
	public void print(MonthlyShiftCheckForm monthlyShiftCheckForm
                            , ExcelOutputStreamExporter excelFileExporter) throws Exception {

        // ①読み込むテンプレートファイルのパス(拡張子含)
        // ②出力先のファイルパス(拡張子はExpoterによって自動的に付与されるため、不要。)
        // ③出力ファイルフォーマット(ConvertConfigurationの配列)
        // を指定し、ReportBookインスタンスを生成します。
        //
        String templateFileName;

        templateFileName = "月別シフト確認テンプレート.xls";

        URL templateFileUrl = MonthlyShiftCheckPrintAction.class.getResource(templateFileName);
        String templateFilePath = URLDecoder.decode(templateFileUrl.getPath(), "UTF-8");

        String outputFileName = "月別シフト確認";
        String outputFileDir = "C:/reports_output/";
        String outputFilePath = outputFileDir.concat(outputFileName);
        ReportBook outputBook = new ReportBook(templateFilePath, outputFilePath, ExcelOutputStreamExporter.FORMAT_TYPE);

        //
        // テンプレートファイル内のシート名と出力シート名を指定し、
        // ReportSheetインスタンスを生成して、ReportBookに追加します。
        //
        ReportSheet outputSheet = new ReportSheet("出勤希望確認");
        outputBook.addReportSheet(outputSheet);

        //
        // 置換パラメータをReportSheetオブジェクトに追加します。
        // (反復置換のパラメータには配列を渡します。)
        //

        List<String> dateList = new ArrayList<String>(31);
        List<String> weekDayList = new ArrayList<String>(31);

        String yearMonth = "";

        List<DateBean> dateBeanList = monthlyShiftCheckForm.getDateBeanList();
        int listSize = dateBeanList.size();
        for (int i = 0; i < 31; i++) {
            DateBean dateBean = null;

            if (i < listSize) {
                dateBean= dateBeanList.get(i);
            }

            if (CheckUtils.isEmpty(dateBean)) {
                dateList.add("");
                weekDayList.add("");
            } else {
                dateList.add(CommonUtils.changeFormat(dateBean.getYearMonthDay(), "yyyyMMdd", "dd"));
                weekDayList.add(dateBean.getWeekDay());
                yearMonth = CommonUtils.changeFormat(dateBean.getYearMonthDay(), "yyyyMMdd", "yyyy/MM");
            }
        }

        outputSheet.addParam(SingleParamParser.DEFAULT_TAG, "年月", yearMonth);
        outputSheet.addParam(ColRepeatParamParser.DEFAULT_TAG, "日付", dateList.toArray());
        outputSheet.addParam(ColRepeatParamParser.DEFAULT_TAG, "曜日", weekDayList.toArray());

        List<MonthlyShiftCheckBean> monthlyShiftCheckBeanList = monthlyShiftCheckForm.getMonthlyShiftCheckBeanList();
        List<String> employeeNameList = new ArrayList<>();

        // 日毎（列ごと）のリストを配列に保持
        List<String>[] shiftIdLists = new List[30]; // 31日分のリスト
        for (int i = 0; i < shiftIdLists.length; i++) {
            shiftIdLists[i] = new ArrayList<>(); // 初期化
        }

        // 日付ごとにリストに加えていく
        for (MonthlyShiftCheckBean shiftRequestCheckBean : monthlyShiftCheckBeanList) {
            // 人毎（行ごと）のデータを追加していく
            employeeNameList.add(shiftRequestCheckBean.getEmployeeName());

         // 日毎（列ごと）のデータを反射を用いてリストに追加
            for (int j = 0; j < shiftIdLists.length; j++) {
                try {
                    Method method = shiftRequestCheckBean.getClass().getMethod("getSymbol" + String.format("%02d", j + 1));
                    String symbol = (String) method.invoke(shiftRequestCheckBean);
                    shiftIdLists[j].add(symbol);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        outputSheet.addParam(RowRepeatParamParser.DEFAULT_TAG, "社員名", employeeNameList.toArray());

        // 日毎（列ごと）にパラメータに値をセットしていく
        for (int i = 0; i < shiftIdLists.length; i++) {
            outputSheet.addParam(RowRepeatParamParser.DEFAULT_TAG, "シフト" + (i + 1), shiftIdLists[i].toArray());
        }
        // ReportProcessorインスタンスを生成し、
        // ReportBookを元にレポート処理を実行します。
        //
        ReportProcessor reportProcessor = new ReportProcessor();

        reportProcessor.addReportBookExporter(excelFileExporter);
        reportProcessor.process(outputBook);
    }
}
