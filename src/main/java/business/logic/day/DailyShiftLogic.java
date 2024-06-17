package business.logic.day;

import java.sql.SQLException;
import java.util.List;

import business.db.dao.day.DailyShiftDao;
import business.dto.day.DailyShiftDto;

public class DailyShiftLogic {
    /**
     * 日別シフトDtoリストを取得する。
     * @return 社員マスタリスト
     * @author naraki
     */
    public  List<DailyShiftDto> getDailyShiftDtoList(String yearMonthDay) throws SQLException{

        // 日別シフトDao
        DailyShiftDao dao = new DailyShiftDao();

        // 日別シフトDtoリストを取得
        List<DailyShiftDto> dailyShiftDtoList = dao.getDailyShiftDtoList(yearMonthDay);

        return dailyShiftDtoList;
    }
}
