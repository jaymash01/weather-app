package com.jaymash.weatherapp.controllers;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.google.gson.Gson;
import com.jaymash.weatherapp.App;
import com.jaymash.weatherapp.dialogs.MessageDialog;
import com.jaymash.weatherapp.models.*;
import com.jaymash.weatherapp.utils.DateUtils;
import com.jaymash.weatherapp.utils.FileUtils;
import com.jaymash.weatherapp.utils.StringUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IndexController implements Initializable {

    private App app;
    private Stage stage;

    @FXML
    private Text date;

    @FXML
    private Text temperature;

    @FXML
    private ImageView weatherIcon;

    @FXML
    private Text weatherDescription;

    @FXML
    private Text location;

    @FXML
    private Text minTemperature;

    @FXML
    private Text maxTemperature;

    @FXML
    private Text feelsLike;

    @FXML
    private Text wind;

    @FXML
    private Text humidity;

    @FXML
    private Text visibility;

    @FXML
    private Text pressure;

    @FXML
    private Pane forecastChart;

    @FXML
    private Button forecastDay0;

    @FXML
    private Button forecastDay1;

    @FXML
    private Button forecastDay2;

    @FXML
    private Text alertMessage;

    @FXML
    private ImageView settingsLink;

    @FXML
    private ImageView refreshLink;

    private WeatherResponse weatherResponse;
    private ForecastResponse forecastResponse;
    private Preferences preferences;
    private String apiToken;

    public void setApp(App app) {
        this.app = app;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadWeather() {
        Runnable runnable = () -> {
            try {
                String[] params = new String[]{
                        "appid=" + apiToken,
                        "units=" + preferences.getUnits(),
                        "id=" + preferences.getCity().getId()
                };

                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?" + String.join("&", params));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                String response = FileUtils.readFromInputStream(connection.getInputStream());
                weatherResponse = new Gson().fromJson(response, WeatherResponse.class);

                Platform.runLater(() -> {
                    showWeather();
                    loadForecast();
                });

                connection.disconnect();
            } catch (Exception ex) {
                Platform.runLater(() -> new MessageDialog(stage, "Error", ex.getMessage()).show());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void loadForecast() {
        Runnable runnable = () -> {
            try {
                String[] params = new String[]{
                        "appid=" + apiToken,
                        "units=" + preferences.getUnits(),
                        "id=" + preferences.getCity().getId()
                };

                URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?" + String.join("&", params));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                String response = FileUtils.readFromInputStream(connection.getInputStream());
                forecastResponse = new Gson().fromJson(response, ForecastResponse.class);

                Platform.runLater(() -> showForecast(0));
                connection.disconnect();
            } catch (Exception ex) {
                Platform.runLater(() -> new MessageDialog(stage, "Error", ex.getMessage()).show());
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void showWeather() {
        if (weatherResponse != null) {
            Main main = weatherResponse.getMain();
            date.setText(DateUtils.formatDate(new Date(weatherResponse.getTimestamp() * 1000L), "EEEEE, MMMMM dd"));
            temperature.setText(main.getTemperature() + " " + StringUtils.fromUnicode(176) + (preferences.getUnits().equals("metric") ? "C" : "F"));

            Weather weather = weatherResponse.getWeatherList().get(0);
            Image icon = new Image(getClass().getResource("/images/" + weather.getIcon() + ".png").toExternalForm());

            weatherIcon.setImage(icon);
            weatherDescription.setText(StringUtils.capitalize(weather.getDescription()));
            location.setText(weatherResponse.getName());
            minTemperature.setText(main.getMinTemperature() + StringUtils.fromUnicode(176));
            maxTemperature.setText(main.getMaxTemperature() + StringUtils.fromUnicode(176));
            feelsLike.setText("Feels like " + main.getFeelsLike() + StringUtils.fromUnicode(176));
            wind.setText(weatherResponse.getWind().getSpeed() + (preferences.getUnits().equals("metric") ? "m/s" : "mph"));
            humidity.setText(main.getHumidity() + "%");
            visibility.setText(((double) (weatherResponse.getVisibility() / 1000)) + (preferences.getUnits().equals("metric") ? "km" : "mi"));
            pressure.setText(main.getGroundLevel() + "hPa");

            Date date1 = new Date(weatherResponse.getTimestamp() * 1000L);
            forecastDay0.setText(DateUtils.getDayNameFromAddedDays(date1, 0));
            forecastDay1.setText(DateUtils.getDayNameFromAddedDays(date1, 1));
            forecastDay2.setText(DateUtils.getDayNameFromAddedDays(date1, 2));
            date.getParent().setVisible(true);
        }
    }

    private void showForecast(int dayOffset) {
        if (weatherResponse != null && forecastResponse != null) {
            List<ForecastListItem> forecastChartItems = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(weatherResponse.getTimestamp() * 1000L));
            calendar.add(Calendar.DAY_OF_MONTH, dayOffset);

            Date date1 = calendar.getTime();
            List<ForecastListItem> forecastListItems = forecastResponse.getList();

            for (ForecastListItem forecastListItem : forecastListItems) {
                Date date2 = new Date(forecastListItem.getTimestamp() * 1000L);
                if (DateUtils.formatDate(date1, "yyyyMMdd").equals(DateUtils.formatDate(date2, "yyyyMMdd"))) {
                    forecastChartItems.add(forecastListItem);
                }
            }

            int size = forecastChartItems.size();

            if (size > 0) {
                double[] temps = new double[size];
                int i = 0;

                for (; i < size; i++) {
                    temps[i] = forecastChartItems.get(i).getForecastMain().getTemperature();
                }

                Arrays.sort(temps);

                double minTemp = Math.ceil(temps[0]);
                double maxTemp = Math.floor(temps[temps.length - 1]);

                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis(minTemp - 1, maxTemp + 1, 1);
                yAxis.setMinorTickCount(0);
                AreaChart<String, Number> chart = new AreaChart<>(xAxis, yAxis);
                XYChart.Series<String, Number> series = new XYChart.Series<>();

                for (ForecastListItem forecastChartItem : forecastChartItems) {
                    String hours = DateUtils.formatDate(new Date(forecastChartItem.getTimestamp() * 1000L), "HH:mm");
                    series.getData().add(new XYChart.Data<String, Number>(hours, forecastChartItem.getForecastMain().getTemperature()));
                }

                series.setName("Temperature");
                chart.getData().add(series);

                forecastChart.getChildren().remove(forecastChart.lookup(".chart"));
                forecastChart.getChildren().add(chart);
                forecastChart.getParent().setVisible(true);
            }
        }
    }

    @FXML
    private void showForecastDay0() {
        showForecast(0);
    }

    @FXML
    private void showForecastDay1() {
        showForecast(1);
    }

    @FXML
    private void showForecastDay2() {
        showForecast(2);
    }

    public void initialize() {
        settingsLink.setOnMouseClicked((MouseEvent ev) -> app.gotoSettings());

        Application.Parameters parameters = app.getParameters();
        if (parameters == null) {
            return;
        }

        List<String> rawParameters = parameters.getRaw();

        if (rawParameters.isEmpty()) {
            return;
        }

        apiToken = rawParameters.get(0);
        preferences = app.getPreferences();

        if (preferences != null) {
            loadWeather();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertMessage.getParent().setVisible(false);
        date.getParent().setVisible(false);
        forecastChart.getParent().setVisible(false);
        refreshLink.setOnMouseClicked((MouseEvent ev) -> loadWeather());
    }

}
