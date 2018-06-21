package com.nuzharukiya.spapp.room;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nuzha Rukiya on 18/06/12.
 */
public interface Collection {

    String DATABASE_SERVICE_PROVIDER = "SERVICE_PROVIDER_DATABASE";

    String TABLE_SERVICE_PROVIDER_INFO = "SERVICE_PROVIDER_INFO_TABLE";
    String TABLE_SERVICES_OFFERED = "SERVICES_OFFERED_TABLE";
    String TABLE_APPOINTMENT_INFO = "APPOINTMENT_INFO_TABLE";

    // SERVICE PROVIDER INFO
    String PHONE_NO = "phoneNumber";
    String FULL_NAME = "fullName";
    String WORKING_DAYS = "workingDays";
    String SERVICES_OFFERED = "servicesOffered";

    String MONDAY = "MONDAY";
    String TUESDAY = "TUESDAY";
    String WEDNESDAY = "WEDNESDAY";
    String THURSDAY = "THURSDAY";
    String FRIDAY = "FRIDAY";
    String SATURDAY = "SATURDAY";
    String SUNDAY = "SUNDAY";

    List<String> DAY_LIST = new ArrayList<String>() {
        {
            add(MONDAY);
            add(TUESDAY);
            add(WEDNESDAY);
            add(THURSDAY);
            add(FRIDAY);
            add(SATURDAY);
            add(SUNDAY);
        }
    };

    // SERVICES OFFERED
    String SERVICE_ID = "serviceId";
    String SERVICE_NAME = "serviceName";
    String SERVICE_PRICE = "servicePrice";
    String SERVICE_AVAILABLE = "serviceAvailable";

    // APPOINTMENT INFO
    String APPOINTMENT_ID = "appointmentId";
    String APPOINTMENT_DATE = "appointmentDate";
    String APPOINTMENT_TIME = "appointmentTime";
    String APPOINTMENT_FOR = "customerName";
    String SERVICE_TYPE = "serviceType"; // Service id list
}
