import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pegov.DB.TTManager;
import ru.pegov.DB.TTManagerDAO;
import ru.pegov.SchedulingTasksApplication;
import ru.pegov.SigmaParser.SigmaReportParser;
import ru.pegov.reportDownload.TT_ReportDownloader;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MassUpdateTest{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final Logger LOGGER = LoggerFactory.getLogger(MassUpdateTest.class);

    public MassUpdateTest() {
    }

    @Ignore
    @Test
    public void test(){
        SigmaReportParser srp = new SigmaReportParser();
        TT_ReportDownloader ttReportDownloader = new TT_ReportDownloader();
        TTManager ttm = new TTManagerDAO();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,2021);

        for(int i = 2; i <= 2; i++){
            cal.set(Calendar.MONTH,i);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            Date begin =  cal.getTime();
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date end = cal.getTime();

            System.out.println(dateFormat.format(begin));
            System.out.println(dateFormat.format(end));

            ttm.addArrayTT( //Добавляем в базу ....
                    srp.getTTsList( // ... распарсенный отчет ...
                            new ByteArrayInputStream( // ... в виде стрима ...
                                    ttReportDownloader.getReportUral( // ... скачанный,  за период...
                                            dateFormat.format(begin), // ... со вчера
                                            dateFormat.format(end) // ... до сегодня.
                                    )
                            )
                    )
            );

            LOGGER.info(i + "month done");
        }





    }


}