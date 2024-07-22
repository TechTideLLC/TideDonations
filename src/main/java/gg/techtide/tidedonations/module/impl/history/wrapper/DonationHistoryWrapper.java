package gg.techtide.tidedonations.module.impl.history.wrapper;

import gg.techtide.tidedonations.module.impl.ggwave.event.GGWaveStartEvent;
import gg.techtide.tidedonations.module.impl.history.HistoryModule;
import lombok.Data;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DonationHistoryWrapper {

    private final String timestamp;
    private final String donationItem;
    private final int price;

    public DonationHistoryWrapper(String timestamp, String donationItem, int price) {
        this.timestamp = timestamp;
        this.donationItem = donationItem;
        this.price = price;
    }

    private static String getDayOfMonthSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    public static DonationHistoryWrapper build(final GGWaveStartEvent event, final HistoryModule module) {
        ZonedDateTime currentTimestamp = ZonedDateTime.now(ZoneId.of(module.getTimezone()));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM d");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        String dayOfMonth = currentTimestamp.format(DateTimeFormatter.ofPattern("d"));
        String dayOfMonthSuffix = getDayOfMonthSuffix(Integer.parseInt(dayOfMonth));
        String formattedDate = currentTimestamp.format(dateFormatter) + dayOfMonthSuffix + ", " + currentTimestamp.getYear();
        String formattedTime = currentTimestamp.format(timeFormatter);

        String formattedTimestamp = formattedDate + " " + formattedTime;
        return new DonationHistoryWrapper(formattedTimestamp, event.getInfo(), event.getAmount());
    }
}