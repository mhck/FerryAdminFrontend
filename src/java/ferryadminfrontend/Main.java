package ferryadminfrontend;

import ferry.dto.DepartureDetail;
import ferry.dto.FerryConfigDetail;
import ferry.dto.RouteDetail;
import ferry.dto.ScheduleDetail;
import ferry.eto.DataAccessException;
import ferry.eto.NoSuchHarbourException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author mhck
 */
public class Main {
    @EJB
    private static ferry.contract.AdminContract ferryAdminManager;
    
    public static void main(String[] args) throws NoSuchHarbourException, ParseException, DataAccessException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add a schedule");
        System.out.println("---------------");
        System.out.println("Enter ID for schedule: ");
        int scheduleId = scanner.nextInt();
        System.out.println("Enter start date for schedule: ");
        String startDateStr = scanner.nextLine();
        DateFormat dateFormat = new SimpleDateFormat("DD-MM-YYYY");
        Date startDateSchedule = dateFormat.parse(startDateStr);
        System.out.println("Enter end date for schedule: ");
        String endDateStr = scanner.nextLine();
        Date endDateSchedule = dateFormat.parse(endDateStr);
        System.out.println("Enter departure time, e.g. 1500: ");
        int departureTime = scanner.nextInt();
        System.out.println("Enter departure date (DD-MM-YYYY), e.g. 01-30-2014: ");
        String departureDateStr = scanner.nextLine();
        Date departureDate = dateFormat.parse(departureDateStr);
        System.out.println("Enter departure ID: ");
        int departureId = scanner.nextInt();
        System.out.println("----------------");
        System.out.println("Showing routes...");
        ArrayList<RouteDetail> routes = (ArrayList<RouteDetail>) ferryAdminManager.showRoutes();
        System.out.println(routes);
        System.out.println("----------------");
        System.out.println("Enter index of route to add, eg 0 for first: ");
        int routeIndex = scanner.nextInt();
        RouteDetail routeDetail = routes.get(routeIndex);
        ArrayList<FerryConfigDetail> ferryConfigs = (ArrayList<FerryConfigDetail>) ferryAdminManager.showFerryConfigs();
        System.out.println("----------------");
        System.out.println("Showing routes...");
        System.out.println(ferryConfigs);
        System.out.println("----------------");
        System.out.println("Enter index of ferryconfig to add, eg 0 for first: ");
        int configIndex = scanner.nextInt();
        FerryConfigDetail ferryConfig = ferryConfigs.get(configIndex);
        DepartureDetail departure = new DepartureDetail(departureTime, departureDate, departureId, routeDetail, ferryConfig);
        ArrayList<DepartureDetail> departures = new ArrayList<>();
        departures.add(departure);
        ScheduleDetail schedule = new ScheduleDetail(scheduleId, endDateSchedule, startDateSchedule, departures);
        ferryAdminManager.addSchedule(schedule);
        System.out.println("Schedule added...");
    }
}
