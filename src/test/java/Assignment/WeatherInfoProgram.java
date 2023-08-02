package Assignment;

import java.util.Scanner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class WeatherInfoProgram 
{
	private static final String API_URL = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleWeatherOption(scanner);
                    break;
                case 2:
                    handleWindSpeedOption(scanner);
                    break;
                case 3:
                    handlePressureOption(scanner);
                    break;
                case 0:
                    System.out.println("Terminating the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nChoose an option:");
        System.out.println("1. Get weather");
        System.out.println("2. Get Wind Speed");
        System.out.println("3. Get Pressure");
        System.out.println("0. Exit");
    }

    private static void handleWeatherOption(Scanner scanner) {
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String dateInput = scanner.next();
        String weatherData = fetchWeatherData();

        JsonPath jsonPath = new JsonPath(weatherData);
        String temperature = jsonPath.getString("list.find { it.dt_txt.contains('" + dateInput + "') }.main.temp");
        if (temperature == null || temperature.isEmpty()) {
            System.out.println("Data not available for the given date.");
        } else {
            System.out.println("Temperature on " + dateInput + ": " + temperature + "°C");
        }
    }

    private static void handleWindSpeedOption(Scanner scanner) {
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String dateInput = scanner.next();
        String weatherData = fetchWeatherData();

        JsonPath jsonPath = new JsonPath(weatherData);
        String windSpeed = jsonPath.getString("list.find { it.dt_txt.contains('" + dateInput + "') }.wind.speed");
        if (windSpeed == null || windSpeed.isEmpty()) {
            System.out.println("Data not available for the given date.");
        } else {
            System.out.println("Wind Speed on " + dateInput + ": " + windSpeed + " m/s");
        }
    }

    private static void handlePressureOption(Scanner scanner) {
        System.out.print("Enter the date (YYYY-MM-DD HH:MM:SS): ");
        String dateInput = scanner.next();
        String weatherData = fetchWeatherData();

        JsonPath jsonPath = new JsonPath(weatherData);
        String pressure = jsonPath.getString("list.find { it.dt_txt.contains('" + dateInput + "') }.main.pressure");
        if (pressure == null || pressure.isEmpty()) {
            System.out.println("Data not available for the given date.");
        } else {
            System.out.println("Pressure on " + dateInput + ": " + pressure + " hPa");
        }
    }

    private static String fetchWeatherData() {
        return RestAssured.get(API_URL).asString();
    }
}


