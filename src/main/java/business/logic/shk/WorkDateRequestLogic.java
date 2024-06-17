/**
 * ファイル名：WorkDateRequestLogic.java
 *
 * 変更履歴
 * 1.0  2010/10/06 Kazuya.Naraki
 */
package business.logic.shk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import business.db.dao.shk.WorkDateRequestDao;
import business.dto.LoginUserDto;
import business.dto.shk.WorkDateRequestCheckDto;
import business.dto.shk.WorkDateRequestInputDto;

/**
 * 説明：希望出勤日入力処理のロジック
 * @author naraki
 *
 */
public class WorkDateRequestLogic {


    /**
     * 出勤希望確認画面に表示するリストを取得する。
     * 戻り値・・・社員分の希望シフトリストのリスト
     * @param yearMonth 年月
     * @return 出勤希望Dtoリストのリスト
     * @author naraki
     */
    public List<List<WorkDateRequestCheckDto>> getWorkDateRequestCheckDtoList(String yearMonth) throws SQLException{

        // Dao
        WorkDateRequestDao dao = new WorkDateRequestDao();

        // シフト情報を取得する。
        List<List<WorkDateRequestCheckDto>> workRequestCheckDtoNestedList = dao.getShiftTblNestedList(yearMonth);

        return workRequestCheckDtoNestedList;
    }
    /**
     * シフトテーブルのデータを登録・更新する。
     * @param requestDtoNestedList 月別シフト一覧
     * @return 基本シフトマップ
     * @author naraki
     * @throws SQLException
     */
    public void registerRequestShift(List<List<WorkDateRequestInputDto>> requestDtoNestedList, LoginUserDto loginUserDto) throws SQLException {

        // Dao
        WorkDateRequestDao dao = new WorkDateRequestDao();
        // コネクション
        Connection connection = dao.getConnection();

        // トランザクション処理
        connection.setAutoCommit(false);

        try {
        	for (List<WorkDateRequestInputDto> workDateRequestInputDtoList : requestDtoNestedList) {
        		
        		for (WorkDateRequestInputDto workDateRequestInputDto : workDateRequestInputDtoList) {
        		// 日数分ループ

        		// 社員ID
        		String employeeId = workDateRequestInputDto.getEmployeeId();
            	// 対象年月
            	String yearMonthDay = workDateRequestInputDto.getYearMonthDay();
            
            	if (employeeId.equals(loginUserDto.getEmployeeId())) {
            		// レコードの存在を確認する
            		boolean isData = dao.isData(employeeId, yearMonthDay);
            		
            		if (isData) {
            			// 更新
            			dao.updateShiftTbl(workDateRequestInputDto, loginUserDto);
            		} else {
            			// 登録
            			dao.insertShiftTbl(workDateRequestInputDto, loginUserDto);
            		}
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
}