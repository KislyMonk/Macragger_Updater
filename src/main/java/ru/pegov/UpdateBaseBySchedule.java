package ru.pegov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.pegov.DB.TTManager;
import ru.pegov.DB.TTManagerDAO;
import ru.pegov.SigmaParser.SigmaReportParser;
import ru.pegov.reportDownload.TT_ReportDownloader;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@EnableScheduling
@EnableAsync
public class UpdateBaseBySchedule {
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateBaseBySchedule.class);

    public UpdateBaseBySchedule() {
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Async
    //sec min hour dom mon dow year
    @Scheduled(cron = "0 0 6 ? * *") // update at 6 AM everyday
    public void reportCurrentTime() {
        SigmaReportParser srp = new SigmaReportParser();
        TT_ReportDownloader ttReportDownloader = new TT_ReportDownloader();
        TTManager ttm = new TTManagerDAO();

        LOGGER.info("Shedule download start");
        ttm.addArrayTT( //Добавляем в базу ....
                srp.getTTsList( // ... распарсенный отчет ...
                        new ByteArrayInputStream( // ... в виде стрима ...
                                ttReportDownloader.getReportUral( // ... скачанный,  за период...
                                        dateFormat.format(new Date(new Date().getTime() - 86400000*7)), // ... за неделю
                                        dateFormat.format(new Date()) // ... до сегодня.
                                        )
                                )
                        )
                );
        LOGGER.info("Shedule download end");
    }
}