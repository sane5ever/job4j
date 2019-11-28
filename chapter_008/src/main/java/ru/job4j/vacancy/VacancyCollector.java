package ru.job4j.vacancy;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Main executable class to start sql.ru vacancy parser
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-27
 */
public class VacancyCollector {
    private static final Logger LOG = LoggerFactory.getLogger(VacancyCollector.class);

    public static void main(String[] args) {
        JobMainStarter jobMainStarter = new JobMainStarter(args);
        try {
            var message = jobMainStarter.start(VacancyCollectorJob.class);
            LOG.info(message);
        } catch (Exception e) {
            jobMainStarter.handleException(LOG, e);
            try {
                jobMainStarter.shutdown(false);
            } catch (SchedulerException ex) {
                // ignored
            }
        }
    }
}
