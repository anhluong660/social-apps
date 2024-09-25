package com.swordfish.metric.io;

import com.swordfish.utils.common.DateUtil;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MetricWriter {

    @Value("${metric.folder}")
    private String METRIC_FOLDER;

    private final Queue<String> queue = new ConcurrentLinkedQueue<>();

    public void log(String line) {
        queue.add(System.currentTimeMillis() + "|" + line);
    }

    @PostConstruct
    public void init() {
        File file = new File(METRIC_FOLDER);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.SECONDS)
    public void writeFile() {
        try {
            if (queue.isEmpty()) {
                return;
            }

            List<String> lines = new LinkedList<>();

            while (queue.size() > 0) {
                String line = queue.remove();
                lines.add(line);
            }

            String fileName = DateUtil.convertToStr(new Date(), "yyyy-MM-dd");

            Files.write(Paths.get(METRIC_FOLDER + "/" + fileName), lines,
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);

        } catch (Exception ex) {
            log.error("Exception MetricWriter.writeFile ", ex);
        }
    }
}
